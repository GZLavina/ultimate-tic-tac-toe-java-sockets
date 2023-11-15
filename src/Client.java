import java.io.*;
import java.net.*;

public class Client {

	public static void main(String[] argv) throws Exception {

        BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));    
        Socket clientSocket = new Socket("127.0.0.1", 6789);
        DataOutputStream toServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        while(true) {

        }
    }

}
