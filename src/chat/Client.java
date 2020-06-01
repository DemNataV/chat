package chat;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Client {
    private String ip;
    private int port;
    private Scanner scanner;
    private Connection connection;
    private ExecutorService executorService;
    private ArrayBlockingQueue<Message> receivedMessages;


    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
        scanner = new Scanner(System.in);
        try {
            connection = new Connection(getSocket());
        } catch (IOException e) {
            e.printStackTrace();
        }
        executorService = Executors.newFixedThreadPool(3);
        receivedMessages = new ArrayBlockingQueue<Message>(10);

    }

    private Socket getSocket() throws IOException {
        Socket socket = new Socket(ip, port);
        return socket;
    }

    private void sendMessage(Message message) throws IOException {

        connection.sendMessage(message);

    }


    public void start() throws Exception {
        executorService.execute(new ReceiveTask(connection, receivedMessages));
        executorService.execute(new OutputTask(receivedMessages));
        System.out.println("Ведите имя");
        String name = scanner.nextLine();
        String text;
        while (true) {
            System.out.println("Введите сообщение");
            text = scanner.nextLine();
            sendMessage(
                    Message.getInstance(name, text));
        }
    }

    public static void main(String[] args) {
        int port = 8099;
        String ip = "127.0.0.1";
        try {
            new Client(ip, port).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

