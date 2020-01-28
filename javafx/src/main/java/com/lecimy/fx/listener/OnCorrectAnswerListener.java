package com.lecimy.fx.listener;

@FunctionalInterface
public interface OnCorrectAnswerListener extends EventListener {

    void onCorrectAnswer(int points);
}
