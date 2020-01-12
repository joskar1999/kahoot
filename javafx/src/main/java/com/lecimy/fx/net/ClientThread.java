package main.java.com.lecimy.fx.net;

import main.java.com.lecimy.fx.listener.EventListener;
import main.java.com.lecimy.fx.net.handler.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientThread implements Runnable {

    private Client client;
    private BufferedReader reader;
    private String message;
    private RequestHandler requestHandler;
    private EventListener[] eventListeners;

    private ClientThread() {

    }

    public void initClientThread(Client client) {
        this.client = client;
        try {
            reader = new BufferedReader(new InputStreamReader(client.getSocket().getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unable to read from the socket!\n");
        }
    }

    @Override
    public void run() {
        try {
            message = reader.readLine();
            System.out.println("message : " + message);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unable to read line!");
        }
        requestHandler.handle(message, eventListeners);
        System.out.println("End of thread");
    }

    public static ClientThread getInstance() {
        return ClientThreadSingleton.INSTANCE;
    }

    private static class ClientThreadSingleton {
        private static final ClientThread INSTANCE = new ClientThread();
    }

    public BufferedReader getReader() {
        return reader;
    }

    public void setRequestHandler(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    public void setEventListeners(EventListener[] eventListeners) {
        this.eventListeners = eventListeners;
    }
}
