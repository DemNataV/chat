package chat;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int port;
    private Connection connection;

    public Server(int port) {
        this.port = port;
    }

    public void start() throws IOException, ClassNotFoundException {
        try (ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Сервер запущен...");
            while (true){

                Socket clientSocket = serverSocket.accept();
                connection = new Connection(clientSocket);
                System.out.println(connection.readMessage());
                connection.sendMessage(Message
                        .getInstance("server", "hello"));
            }
        }
    }

    public static void main(String[] args) {
        int port = 8099;
        Server server = new Server(port);
        try {
            server.start();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
