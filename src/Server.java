import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] argv) throws Exception {

		try {
			ServerSocket serverSocket = new ServerSocket(6789);
			System.out.println("Server is running. Waiting for players...");

			Socket player1Socket = serverSocket.accept();
			System.out.println("Player 1 connected.");

			Socket player2Socket = serverSocket.accept();
			System.out.println("Player 2 connected.");

			// Start a new thread for the game
			new Game(player1Socket, player2Socket).start();

		} catch (IOException e) {
			e.printStackTrace();
		}
    }

//	public void stop() throws IOException {
//		serverSocket.close();
//	}
//
//	public static class TicTacToeThread extends Thread {
//		Socket socket;
//
//		public TicTacToeThread(Socket clientSocket) {
//			this.socket = clientSocket;
//		}
//
//		public void run() {
//			try {
//				BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//				DataOutputStream toClient = new DataOutputStream(socket.getOutputStream());
//
//
//
//			} catch(IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	public int[] validateCell(String cell) {
//		try {
//
//			char[] indexes = cell.toCharArray();
//			int[] number_indexes = new int[2];
//			number_indexes[0] = ((int) indexes[0]) - 65;
//			number_indexes[1] = (int) indexes[1];
//
//			if (number_indexes[0] >= 1 && number_indexes[0] <= 3 &&
//					number_indexes[1] >= 1 && number_indexes[1] <= 3) {
//				return number_indexes;
//			}
//
//			return null;
//
//		} catch(Exception e) {
//			return null;
//		}
//	}
}