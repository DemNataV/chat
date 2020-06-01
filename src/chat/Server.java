package chat;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private int port;
    private HashMap<String, Connection> listClient = new HashMap<String, Connection>();
    private ExecutorService executorService;
    private ArrayBlockingQueue<Message> receivedMessages;


    public Server(int port) {
        this.port = port;
        executorService = Executors.newScheduledThreadPool(10);
        receivedMessages = new ArrayBlockingQueue<Message>(10);

    }

    public void start() throws IOException, ClassNotFoundException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен...");
            executorService.execute(new SendTask(listClient, receivedMessages));
            while (true) {

                Socket clientSocket = serverSocket.accept();
                Connection connection = new Connection(clientSocket);
                Message message = connection.readMessage();
                listClient.put(message.getSender(), connection);
                //System.out.println(message);
                System.out.println(listClient);
                connection.sendMessage(Message
                        .getInstance("server", "соединение установлено"));
                executorService.execute(new ReceiveTask(connection, receivedMessages));
                try {
                    receivedMessages.put(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

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
