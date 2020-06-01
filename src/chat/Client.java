package chat;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private String ip;
    private int port;
    private Scanner scanner;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
        scanner = new Scanner(System.in);
    }

    private Socket getSocket() throws IOException {
        Socket socket = new Socket(ip, port);
        return socket;
    }

    private void sendAndPrintMessage(Message message) throws Exception {
        try (Connection connection = new Connection(getSocket())){
            connection.sendMessage(message);

            Message fromServer = connection.readMessage();
            System.out.println("ответ от сервера " + fromServer);
        }
    }

    public void start() throws Exception {
        System.out.println("Ведите имя");
        String name = scanner.nextLine();
        String text;
        while (true){
            System.out.println("Введите сообщение");
            text = scanner.nextLine();
            sendAndPrintMessage(
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
