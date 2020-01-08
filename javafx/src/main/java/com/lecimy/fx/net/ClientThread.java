package main.java.com.lecimy.fx.net;

import main.java.com.lecimy.fx.listener.OnFailureNickCreationListener;
import main.java.com.lecimy.fx.listener.OnSuccessNickCreationListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientThread implements Runnable {

    private Client client;
    private BufferedReader reader;
    private String message;
    private static final String OK = "OK";
    private static final String NO = "NO";

    private OnSuccessNickCreationListener onSuccessNickCreationListener;
    private OnFailureNickCreationListener onFailureNickCreationListener;

    private ClientThread() {

    }

    public void initClientThread(Client client) {
        this.client = client;
        try {
            reader = new BufferedReader(new InputStreamReader(client.getSocket().getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unable to read from the socket!");
        }
    }

    @Override
    public void run() {
        try {
            message = reader.readLine();
            System.out.printf("message : " + message);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unable to read line!");
        }
        if (OK.equals(message)) {
            onSuccessNickCreationListener.onSuccess();
        } else {
            onFailureNickCreationListener.onFailure();
        }
        System.out.println("End of thread");
    }

    public static ClientThread getInstance() {
        return ClientThreadSingleton.INSTANCE;
    }

    private static class ClientThreadSingleton {
        private static final ClientThread INSTANCE = new ClientThread();
    }

    public void setOnSuccessNickCreationListener(OnSuccessNickCreationListener onSuccessNickCreationListener) {
        this.onSuccessNickCreationListener = onSuccessNickCreationListener;
    }

    public void setOnFailureNickCreationListener(OnFailureNickCreationListener onFailureNickCreationListener) {
        this.onFailureNickCreationListener = onFailureNickCreationListener;
    }
}
