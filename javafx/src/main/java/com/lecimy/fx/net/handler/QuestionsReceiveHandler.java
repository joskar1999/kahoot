package com.lecimy.fx.net.handler;

import com.lecimy.fx.listener.EventListener;
import com.lecimy.fx.listener.OnFailureQuestionsReceiveListener;
import com.lecimy.fx.listener.OnSuccessQuestionsReceiveListener;
import com.lecimy.fx.model.Question;
import com.lecimy.fx.model.Quiz;
import com.lecimy.fx.net.ClientThread;
import com.lecimy.fx.store.DataStore;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.lecimy.fx.net.ResponseMessages.QUIZ;

public class QuestionsReceiveHandler implements RequestHandler {

    private BufferedReader reader;

    public QuestionsReceiveHandler() {
        reader = ClientThread.getInstance().getReader();
    }

    @Override
    public void handle(String message, EventListener[] eventListeners) {
        OnSuccessQuestionsReceiveListener onSuccessQuestionsReceiveListener = (OnSuccessQuestionsReceiveListener) eventListeners[0];
        OnFailureQuestionsReceiveListener onFailureQuestionsReceiveListener = (OnFailureQuestionsReceiveListener) eventListeners[1];
        if (QUIZ.equals(message)) {
            String questionsAmount = "";
            List<Question> questions = new ArrayList<>();
            try {
                questionsAmount = reader.readLine();
                DataStore.setQuestionsAmount(Integer.parseInt(questionsAmount));
                System.out.println("questions amount: " + questionsAmount);
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < Integer.parseInt(questionsAmount); i++) {
                String question = "";
                String a = "";
                String b = "";
                String c = "";
                String d = "";
                try {
                    question = reader.readLine();
                    a = reader.readLine();
                    b = reader.readLine();
                    c = reader.readLine();
                    d = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                questions.add(new Question(question, a, b, c, d));
            }
            DataStore.setQuestions(questions);
            onSuccessQuestionsReceiveListener.onSuccess();
        } else {
            onFailureQuestionsReceiveListener.onFailure();
        }
    }
}
