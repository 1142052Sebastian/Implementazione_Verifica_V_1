import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Server {
    
    private static final int PORT = 1234;
    private static List<Book> books = new ArrayList<Book>();

    public static void main(String[] args) {
        loadBooks();
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server in ascolto sulla porta " + PORT + "...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ServerThread serverThread = new ServerThread(clientSocket, books);
                serverThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void loadBooks() {
        try {
            // Creazione del parser XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            // Parsing del file xml dei libri
            Document document = builder.parse(new File("Sistema_bibliotecario.xml"));
            // Creazione della lista dei libri
            NodeList nodeList = document.getElementsByTagName("Libro");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String title = element.getElementsByTagName("Titolo").item(0).getTextContent();
                    String author = element.getElementsByTagName("Autore").item(0).getTextContent();
                    String code = element.getElementsByTagName("ISBN").item(0).getTextContent();
                    int availableCopies = Integer.parseInt(element.getElementsByTagName("Copie").item(0).getTextContent());
                    Book book = new Book(title, author, availableCopies, code);
                    books.add(book);
                }
            }
            System.out.println("Caricati " + books.size() + " libri.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
