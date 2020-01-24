package main.java.com.lecimy.fx.net.handler;

import main.java.com.lecimy.fx.listener.EventListener;
import main.java.com.lecimy.fx.listener.OnFailureQuizHeadersReceiveListener;
import main.java.com.lecimy.fx.listener.OnSuccessQuizHeadersReceiveListener;
import main.java.com.lecimy.fx.model.Quiz;
import main.java.com.lecimy.fx.net.Client;
import main.java.com.lecimy.fx.net.ClientThread;
import main.java.com.lecimy.fx.net.ResponseMessages;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static main.java.com.lecimy.fx.net.ResponseMessages.GAMES_HEADERS;

public class UserDataFetchHandler implements RequestHandler {

    private BufferedReader reader;
    private List<Quiz> games = new ArrayList<>();

    public UserDataFetchHandler() {
        reader = ClientThread.getInstance().getReader();
    }

    @Override
    public void handle(String message, EventListener[] eventListeners) {
        OnSuccessQuizHeadersReceiveListener onSuccessQuizHeadersReceiveListener = (OnSuccessQuizHeadersReceiveListener) eventListeners[0];
        OnFailureQuizHeadersReceiveListener onFailureQuizHeadersReceiveListener = (OnFailureQuizHeadersReceiveListener) eventListeners[1];
        if (GAMES_HEADERS.equals(message)) {
            String gamesAmount = "";
            try {
                gamesAmount = reader.readLine();
                System.out.println("games amount: " + gamesAmount);
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < Integer.parseInt(gamesAmount); i++) {
                String name = "";
                String questionsAmount = "";
                String playersAmount = "";
                String id = "";
                try {
                    name = reader.readLine();
                    questionsAmount = reader.readLine();
                    playersAmount = reader.readLine();
                    id = reader.readLine();
                    System.out.println(name + ", " + questionsAmount + ", " + playersAmount + ", " + id);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                games.add(new Quiz(name, questionsAmount, playersAmount, Integer.parseInt(id)));
            }
            onSuccessQuizHeadersReceiveListener.onSuccess(games);
        } else {
            onFailureQuizHeadersReceiveListener.onFailure();
            System.out.println("Nie otrzymano GAMES_HEADERS, zamiast jest: " + message);
        }
    }
}
