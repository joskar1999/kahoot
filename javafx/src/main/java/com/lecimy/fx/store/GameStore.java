package main.java.com.lecimy.fx.store;

import main.java.com.lecimy.fx.model.Quiz;

public class GameStore {

    private static Quiz quiz;

    public static Quiz getQuiz() {
        return quiz;
    }

    public static void setQuiz(Quiz quiz) {
        GameStore.quiz = quiz;
    }
}
