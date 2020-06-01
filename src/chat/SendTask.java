package chat;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class SendTask  implements Runnable {

    private HashMap<String, Connection> listClient = new HashMap<String, Connection>();
    ArrayBlockingQueue<Message> messages;

    public SendTask(HashMap<String, Connection> listClient, ArrayBlockingQueue<Message> messages) {
        this.listClient = listClient;
        this.messages = messages;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message message =  messages.poll(5, TimeUnit.SECONDS);
                if (message != null){
                    listClient.entrySet()
                            .stream()
                            .filter(entry -> ! entry.getKey().equals(message.getSender()))
                            .forEach(x -> {
                                try {
                                    x.getValue().sendMessage(message);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

    }
}
