package com.lecimy.fx.net.handler;

import com.lecimy.fx.listener.EventListener;
import com.lecimy.fx.listener.OnFailureQuizHeadersReceiveListener;
import com.lecimy.fx.listener.OnSuccessQuizHeadersReceiveListener;
import com.lecimy.fx.model.Quiz;
import com.lecimy.fx.net.Client;
import com.lecimy.fx.net.ClientThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.lecimy.fx.net.ResponseMessages.QUIZ_HEADERS;

public class HostDataFetchHandler implements RequestHandler {

    private Client client;
    private BufferedReader reader;
    private List<Quiz> quizzes = new ArrayList<>();

    public HostDataFetchHandler() {
        this.client = Client.getInstance();
        this.reader = ClientThread.getInstance().getReader();
    }

    @Override
    public void handle(String message, EventListener[] eventListeners) {
        OnSuccessQuizHeadersReceiveListener onSuccessQuizHeadersReceiveListener = (OnSuccessQuizHeadersReceiveListener) eventListeners[0];
        OnFailureQuizHeadersReceiveListener onFailureQuizHeadersReceiveListener = (OnFailureQuizHeadersReceiveListener) eventListeners[1];
        if (QUIZ_HEADERS.equals(message)) {
            String quizzesAmount = "";
            try {
                quizzesAmount = reader.readLine();
                System.out.println("Quizzes amount: " + quizzesAmount);
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < Integer.parseInt(quizzesAmount); i++) {
                String name = "";
                String questionsAmount = "";
                try {
                    name = reader.readLine();
                    questionsAmount = reader.readLine();
                    System.out.println(name + ", " + questionsAmount);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                quizzes.add(new Quiz(name, questionsAmount, ""));
            }
            onSuccessQuizHeadersReceiveListener.onSuccess(quizzes);
        } else {
            onFailureQuizHeadersReceiveListener.onFailure();
            System.out.println("Nie otrzymano QUIZ_HEADERS, zamiast jest: " + message);
        }
    }
}
