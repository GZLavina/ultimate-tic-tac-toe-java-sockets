import java.io.*;
import java.net.*;

public class Game extends Thread {
	private Socket player1Socket;
	private Socket player2Socket;
	private PrintWriter player1Out;
	private PrintWriter player2Out;
	private BufferedReader player1In;
	private BufferedReader player2In;

	// Initialize game board and other necessary variables

	public Game(Socket player1Socket, Socket player2Socket) {
		this.player1Socket = player1Socket;
		this.player2Socket = player2Socket;

		try {
			player1Out = new PrintWriter(player1Socket.getOutputStream(), true);
			player2Out = new PrintWriter(player2Socket.getOutputStream(), true);

			player1In = new BufferedReader(new InputStreamReader(player1Socket.getInputStream()));
			player2In = new BufferedReader(new InputStreamReader(player2Socket.getInputStream()));

			player1Out.println("You are Player 1");
			player2Out.println("You are Player 2");

			player1Out.println("START_GAME");
			player2Out.println("START_GAME");

			// Initialize game state, send initial messages to players, etc.

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {

		char[][][] gameBoard = initializeGameBoard();

		try {
			// Game loop
			String currentPlayer = "PLAYER1"; // Keeps track of current player

			while (true) {
				if (currentPlayer.equals("PLAYER1")) {
					player1Out.println("YOUR_TURN");
					player2Out.println("WAIT");
					String move = player1In.readLine();
					// Process the move and update the game state
					// Send game state updates to both players
					currentPlayer = "PLAYER2"; // Switch turn
				} else {
					player2Out.println("YOUR_TURN");
					player1Out.println("WAIT");
					String move = player2In.readLine();
					// Process the move and update the game state
					// Send game state updates to both players
					currentPlayer = "PLAYER1"; // Switch turn
				}

				// Check for win/draw conditions and send appropriate messages
				// Update game state and continue the loop until the game ends
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private char[][][] initializeGameBoard() {
		// Initialize the ultimate game board
		char[][][] ultimateBoard = new char[3][3][3]; // [Ultimate Board Row][Ultimate Board Column][Small Board]

		// Initialize all cells of the ultimate game board and set as empty (' ')
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < 3; k++) {
					ultimateBoard[i][j][k] = ' ';
				}
			}
		}
		return ultimateBoard;
	}
}
