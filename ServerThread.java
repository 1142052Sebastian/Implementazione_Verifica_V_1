import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ServerThread extends Thread {
    
    private Socket clientSocket;
    private List<Book> books;
    
    public ServerThread(Socket clientSocket, List<Book> books) {
        this.clientSocket = clientSocket;
        this.books = books;
    }
    
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            String clientMessage;
            while ((clientMessage = in.readLine()) != null) {
                String[] tokens = clientMessage.split(",");
                if (tokens[0].equals("cerca")) {
                    String keyword = tokens[1];
                    String result = searchBooks(keyword);
                    out.println(result);
                } else if (tokens[0].equals("prenota")) {
                    int bookIndex = Integer.parseInt(tokens[1]);
                    String result = reserveBook(bookIndex);
                    out.println(result);
                } else if (tokens[0].equals("esci")) {
                    break;
                }
            }
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private String searchBooks(String keyword) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            if (book.getTitle().toLowerCase().contains(keyword.toLowerCase()) || book.getAuthor().toLowerCase().contains(keyword.toLowerCase())) {
                result.append(i).append(": ").append(book.getTitle()).append(" di ").append(book.getAuthor()).append("\n");
            }
        }
        if (result.length() == 0) {
            result.append("Nessun libro trovato.");
        }
        return result.toString();
    }
    
    private String reserveBook(int bookIndex) {
        if (bookIndex < 0 || bookIndex >= books.size()) {
            return "Indice del libro non valido.";
        }
        Book book = books.get(bookIndex);
        if (book.getAvailableCopies() == 0) {
            return "Il libro selezionato non è al momento disponibile.";
        }
        book.reserveCopy();
        return "Prenotazione effettuata con successo.";
    }
}
