package chat;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;

public class ReceiveTask implements Runnable {

    Connection connection;
    ArrayBlockingQueue<Message> messages;

    public ReceiveTask(Connection connection, ArrayBlockingQueue<Message> messages) {
        this.connection = connection;
        this.messages = messages;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message message = connection.readMessage();
                messages.add(message);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

    }
}
