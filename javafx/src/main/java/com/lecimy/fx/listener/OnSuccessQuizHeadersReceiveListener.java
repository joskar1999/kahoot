package main.java.com.lecimy.fx.listener;

import main.java.com.lecimy.fx.model.Quiz;

import java.util.List;

public interface OnSuccessQuizHeadersReceiveListener extends EventListener {

    void onSuccess(List<Quiz> quizzes);
}
