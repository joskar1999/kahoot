package com.lecimy.fx.net.handler;

import com.lecimy.fx.listener.*;
import com.lecimy.fx.model.Position;
import com.lecimy.fx.net.ClientThread;
import com.lecimy.fx.store.DataStore;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.lecimy.fx.net.ResponseMessages.*;

public class AnswerHandler implements RequestHandler {

    private BufferedReader reader;

    public AnswerHandler() {
        reader = ClientThread.getInstance().getReader();
    }

    @Override
    public void handle(String message, EventListener[] eventListeners) {
        OnCorrectAnswerListener onCorrectAnswerListener = (OnCorrectAnswerListener) eventListeners[0];
        OnWrongAnswerListener onWrongAnswerListener = (OnWrongAnswerListener) eventListeners[1];
        OnAllAnsweredListener onAllAnsweredListener = (OnAllAnsweredListener) eventListeners[2];
        OnRankingReceivedListener onRankingReceivedListener = (OnRankingReceivedListener) eventListeners[3];
        OnFailureListener onFailureListener = (OnFailureListener) eventListeners[4];
        if (ALL.equals(message)) {
            onAllAnsweredListener.onAllAnswered();
        } else if (YES.equals(message)) {
            String pointsAmount = getAmount();
            onCorrectAnswerListener.onCorrectAnswer(Integer.parseInt(pointsAmount));
        } else if (NO.equals(message)) {
            String pointsAmount = getAmount();
            onWrongAnswerListener.onWrongAnswer(Integer.parseInt(pointsAmount));
        } else if (RANKING.equals(message)) {
            String playersAmount = getAmount();
            System.out.println("players amount: " + playersAmount);
            List<Position> ranking = new ArrayList<>();
            for (int i = 0; i < Integer.parseInt(playersAmount); i++) {
                String nick = "";
                String points = "";
                try {
                    nick = reader.readLine();
                    points = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(nick);
                System.out.println(points);
                ranking.add(new Position(0, nick, Integer.parseInt(points)));
            }
            DataStore.setRanking(ranking);
            onRankingReceivedListener.onRankingReceive();
        } else {
            onFailureListener.onFailure();
        }
    }

    private String getAmount() {
        String amount = "";
        try {
            amount = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return amount;
    }
}
