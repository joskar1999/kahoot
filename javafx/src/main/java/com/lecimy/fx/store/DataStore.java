package com.lecimy.fx.store;

import com.lecimy.fx.model.Position;
import com.lecimy.fx.model.Question;
import com.lecimy.fx.model.Quiz;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class DataStore {

    @Getter
    @Setter
    private static Quiz quiz;

    @Getter
    @Setter
    private static List<Question> questions;

    @Getter
    @Setter
    private static int questionsAmount;

    @Getter
    @Setter
    private static int currentQuestion;

    @Getter
    @Setter
    private static List<Position> ranking;
}
