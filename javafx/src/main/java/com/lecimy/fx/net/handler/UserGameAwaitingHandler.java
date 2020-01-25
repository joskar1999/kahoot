package main.java.com.lecimy.fx.net.handler;

import main.java.com.lecimy.fx.listener.EventListener;
import main.java.com.lecimy.fx.listener.OnGameStartFailureListener;
import main.java.com.lecimy.fx.listener.OnGameStartListener;
import main.java.com.lecimy.fx.listener.OnNewPlayerListener;
import main.java.com.lecimy.fx.net.ClientThread;

import java.io.BufferedReader;
import java.io.IOException;

import static main.java.com.lecimy.fx.net.ResponseMessages.NEW_USER;
import static main.java.com.lecimy.fx.net.ResponseMessages.START;

public class UserGameAwaitingHandler implements RequestHandler {

    private BufferedReader reader;

    public UserGameAwaitingHandler() {
        this.reader = ClientThread.getInstance().getReader();
    }

    @Override
    public void handle(String message, EventListener[] eventListeners) {
        OnNewPlayerListener onNewPlayerListener = (OnNewPlayerListener) eventListeners[0];
        OnGameStartListener onGameStartListener = (OnGameStartListener) eventListeners[1];
        OnGameStartFailureListener onGameStartFailureListener = (OnGameStartFailureListener) eventListeners[2];
        if (NEW_USER.equals(message)) {
            int playersAmount = 0;
            try {
                String value = reader.readLine();
                playersAmount = Integer.parseInt(value);
            } catch (IOException e) {
                e.printStackTrace();
            }
            onNewPlayerListener.onNewPlayer(playersAmount);
        } else if (START.equals(message)) {
            onGameStartListener.onGameStart();
        } else {
            onGameStartFailureListener.onGameStartFailure();
        }
    }
}
