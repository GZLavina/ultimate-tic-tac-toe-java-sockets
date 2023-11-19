import java.io.*;
import java.net.*;

public class Client {

	public static void main(String[] argv) throws Exception {

            try {
                    Socket socket = new Socket("localhost", 6789);
                    System.out.println("Connected to server.");

                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

                    String serverResponse;
                    String userInputStr;

                    while ((serverResponse = in.readLine()) != null) {
                            System.out.println("Server: " + serverResponse);

                            if (serverResponse.equals("YOUR_TURN")) {
                                    System.out.println("Enter your move (row column): ");
                                    userInputStr = userInput.readLine();
                                    out.println(userInputStr);
                            } else if (serverResponse.startsWith("RESULT")) {
                                    // Process and display game result
                                    System.out.println("Game result: " + serverResponse.substring(7));
                                    break; // Game ended
                            } else {
                                    // Display other server messages
                                    System.out.println(serverResponse);
                            }
                    }

                    socket.close();
            } catch (IOException e) {
                    e.printStackTrace();
            }
    }

}
