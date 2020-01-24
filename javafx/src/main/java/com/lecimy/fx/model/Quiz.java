package com.lecimy.fx.model;

public class Quiz {

    private String name;
    private String questionsAmount;
    private String playersAmount;

    public Quiz(String name, String questionsAmount, String playersAmount) {
        this.name = name;
        this.questionsAmount = questionsAmount;
        this.playersAmount = playersAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestionsAmount() {
        return questionsAmount;
    }

    public void setQuestionsAmount(String questionsAmount) {
        this.questionsAmount = questionsAmount;
    }

    public String getPlayersAmount() {
        return playersAmount;
    }

    public void setPlayersAmount(String playersAmount) {
        this.playersAmount = playersAmount;
    }
}
