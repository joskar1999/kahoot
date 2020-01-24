package com.lecimy.fx.listener;

import com.lecimy.fx.model.Quiz;

import java.util.List;

public interface OnSuccessQuizHeadersReceiveListener extends EventListener {

    void onSuccess(List<Quiz> quizzes);
}
