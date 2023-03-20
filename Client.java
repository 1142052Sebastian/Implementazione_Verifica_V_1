import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 1234;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_ADDRESS, PORT);
            System.out.println("Connesso al server.");
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
            String consoleMessage;
            String serverResponse;
            while (true) {
                System.out.print("> ");
                consoleMessage = consoleIn.readLine();
                if (consoleMessage.equals("esci")) {
                    out.println("esci");
                    break;
                }
                out.println(consoleMessage);
                serverResponse = in.readLine();
                System.out.println(serverResponse);
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}