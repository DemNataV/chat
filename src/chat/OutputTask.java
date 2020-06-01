package chat;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class OutputTask implements Runnable {
    ArrayBlockingQueue<Message> messages;

    public OutputTask(ArrayBlockingQueue<Message> messages) {
        this.messages = messages;
    }

    @Override
    public void run() {
        while (true) {
            try {
             Message message =  messages.poll(5, TimeUnit.SECONDS);
             if (message != null){
                 System.out.println(message.toString());
             }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

    }
}
