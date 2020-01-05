package main.java.com.lecimy.fx.model;

public class Quiz {

    private String name;
    private int questionsAmount;
    private int playersAmount;

    public Quiz(String name, int questionsAmount, int playersAmount) {
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

    public int getQuestionsAmount() {
        return questionsAmount;
    }

    public void setQuestionsAmount(int questionsAmount) {
        this.questionsAmount = questionsAmount;
    }

    public int getPlayersAmount() {
        return playersAmount;
    }

    public void setPlayersAmount(int playersAmount) {
        this.playersAmount = playersAmount;
    }
}
