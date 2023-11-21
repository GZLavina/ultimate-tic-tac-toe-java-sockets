import java.io.*;
import java.net.*;

public class Server {

	public UltimateTicTacToe game;
	public volatile char playerToMove;

    public static void main(String[] argv) throws Exception {
		Server server = new Server();
		server.runServer();
    }

	public Server() {
		try (ServerSocket serverSocket = new ServerSocket(6789)) {
			this.game = new UltimateTicTacToe();
			this.playerToMove = Constants.PLAYER_X;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void runServer() {
		try (ServerSocket serverSocket = new ServerSocket(6789)) {
			System.out.println("Server is running. Waiting for players...");

			Socket xSocket = serverSocket.accept();
			System.out.println("Player X connected.");
			ClientHandler xHandler = new ClientHandler(xSocket, 'X');

			Socket oSocket = serverSocket.accept();
			System.out.println("Player O connected.");
			ClientHandler oHandler = new ClientHandler(oSocket, 'O');

			xHandler.start();
			oHandler.start();

			System.out.println("All players connected. Starting Ultimate Tic-tac-toe match.");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public class ClientHandler extends Thread {
		public Socket socket;
		public DataOutputStream out;
		public BufferedReader in;
		public char symbol;

		public ClientHandler(Socket socket, char symbol) {
			try {
				this.socket = socket;
				this.out = new DataOutputStream(socket.getOutputStream());
				this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				this.symbol = symbol;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			try {
				// Test
//				System.out.println("Testing client connection.");
//				sendMessageToClient(Constants.TEST_CONNECTION);

				sendMessageToClient(Constants.GAME_STARTED);

				// Game loop
				while (true) {
					if (playerToMove == symbol) {
						System.out.println("Player " + symbol + " to move.");
						System.out.println("Current game state:");
						System.out.println(game.getBoardView());
						sendMessageToClient(Constants.GAME_STATE + Constants.SEPARATOR + game.getBoardView());
						if (game.currentGame == null) {
							System.out.println("Player " + symbol + " must pick a game.");
							gameSelectionLoop();
						}
						moveValidationLoop();
						if (game.winner != Constants.NO_PLAYER) {
							sendMessageToClient(Constants.GAME_ENDED + Constants.SEPARATOR + game.winner);
							sleep(1000);
							break;
						} else {
							playerToMove = symbol == Constants.PLAYER_X ? Constants.PLAYER_O : Constants.PLAYER_X;
						}
						System.out.println("Player " + symbol + " wins the game!");
					}
					if (game.winner != Constants.NO_PLAYER) {
						sendMessageToClient(Constants.GAME_ENDED + Constants.SEPARATOR + game.winner);
						sleep(1000);
						break;
					} else {
						sleep(500);
					}
				}

			} catch (IOException | InterruptedException e) {
				throw new RuntimeException(e);
			}
		}

		public void sendMessageToClient(String message) throws IOException {
			out.writeBytes(message + '\n');
			out.flush();
		}

		public void gameSelectionLoop() throws IOException {
			int[] cell = cellValidationLoop(Constants.CHOOSE_GAME);
			boolean legalPick = game.pickGame(cell);
			while (!legalPick) {
				sendMessageToClient(Constants.BAD_INPUT);
				cell = cellValidationLoop(Constants.CHOOSE_GAME);
				legalPick = game.pickGame(cell);
			}
		}

		// Coordinate validation method (returns null if not between A1 to C3)
		public int[] validateCell(String cell) {
			try {
				char[] indexes = cell.toUpperCase().toCharArray();
				int[] number_indexes = new int[2];
				number_indexes[0] = indexes[0] - 65;
				number_indexes[1] = indexes[1] - 49;
				if (number_indexes[0] >= 0 && number_indexes[0] <= 2 &&
						number_indexes[1] >= 0 && number_indexes[1] <= 2) {
					return number_indexes;
				}
				return null;
			} catch(Exception e) {
				return null;
			}
		}

		// Loops coordinate validation while selection is invalid
		public int[] cellValidationLoop(String message) throws IOException {
			sendMessageToClient(message);
			int[] cell = validateCell(in.readLine());
			while (cell == null) {
				sendMessageToClient(Constants.BAD_INPUT);
				sendMessageToClient(message);
				cell = validateCell(in.readLine());
			}

			return cell;
		}

		// Loops move validation (checks if cell was unoccupied)
		public void moveValidationLoop() throws IOException {
			int[] cell = cellValidationLoop(Constants.MAKE_MOVE);
			boolean legalMove = game.makeMove(symbol, cell);
			while (!legalMove) {
				sendMessageToClient(Constants.BAD_INPUT);
				cell = cellValidationLoop(Constants.MAKE_MOVE);
				legalMove = game.makeMove(symbol, cell);
			}
		}
	}
}