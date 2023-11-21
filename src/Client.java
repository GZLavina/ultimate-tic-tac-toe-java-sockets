import java.io.*;
import java.net.*;

public class Client {

	public static void main(String[] argv) throws Exception {
            Client client = new Client();
            client.runClient();
    }

    private void runClient() {
            try {
                    Socket socket = new Socket("localhost", 6789);
                    System.out.println("Connected to server.");

                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

                    String serverResponse;
                    String userInputStr;

                    while (true) {
                            serverResponse = in.readLine();

                            if (serverResponse.equals(Constants.BAD_INPUT)) {
                                    System.out.println("Invalid input!");
                            } else if (serverResponse.equals(Constants.MAKE_MOVE)) {
                                    System.out.println("Enter your move (input format: <Row:Letter><Column:Number>):");
                                    userInputStr = userInput.readLine();
                                    out.writeBytes(userInputStr + '\n');
                                    out.flush();
                            } else if (serverResponse.equals(Constants.GAME_STARTED)) {
                                    System.out.println("Game Started!");
                            } else if (serverResponse.equals(Constants.TEST_CONNECTION)) {
                                    System.out.println("Connection working!");
                            } else if (serverResponse.startsWith(Constants.GAME_STATE)) {
                                    System.out.println("Current game state:");
                                    System.out.println(serverResponse.split(Constants.SEPARATOR)[1]);
                            } else if (serverResponse.equals(Constants.CHOOSE_GAME)) {
                                    System.out.println("Pick a game to play in (input format: <Row:Letter><Column:Number>):");
                                    userInputStr = userInput.readLine();
                                    out.writeBytes(userInputStr + '\n');
                                    out.flush();
                            } else if (serverResponse.startsWith(Constants.GAME_ENDED)) {
                                    char winner = (serverResponse.split(Constants.SEPARATOR))[1].charAt(0);
                                    if (winner == Constants.TIE) {
                                            System.out.println("The game ended in a draw!");
                                    } else {
                                            System.out.println("Player " + winner + " wins the game!");
                                    }
                                    break;
                            } else {
                                    System.out.println(serverResponse);
                            }
                    }
                    socket.close();
                    in.close();
                    out.close();
            } catch (IOException e) {
                    e.printStackTrace();
            }
    }

}
