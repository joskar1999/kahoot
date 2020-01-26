package com.lecimy.fx.net.handler;

import com.lecimy.fx.listener.EventListener;
import com.lecimy.fx.listener.OnCorrectAnswerListener;
import com.lecimy.fx.listener.OnWrongAnswerListener;
import com.lecimy.fx.net.ClientThread;

import java.io.BufferedReader;

import static com.lecimy.fx.net.ResponseMessages.NO;
import static com.lecimy.fx.net.ResponseMessages.YES;

public class AnswerHandler implements RequestHandler {

    private BufferedReader reader;

    public AnswerHandler() {
        reader = ClientThread.getInstance().getReader();
    }

    @Override
    public void handle(String message, EventListener[] eventListeners) {
        OnCorrectAnswerListener onCorrectAnswerListener = (OnCorrectAnswerListener) eventListeners[0];
        OnWrongAnswerListener onWrongAnswerListener = (OnWrongAnswerListener) eventListeners[1];
        if (YES.equals(message)) {

        } else if (NO.equals(message)) {

        }
    }
}
