package main.java.com.lecimy.fx.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import main.java.com.lecimy.fx.model.Quiz;
import main.java.com.lecimy.fx.store.GameStore;

import java.net.URL;
import java.util.ResourceBundle;

public class UserAwaitingController implements Initializable {

    @FXML
    private Text playersAmount;

    @FXML
    private Text quizName;

    private Quiz quiz;

    public UserAwaitingController() {
        quiz = GameStore.getQuiz();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        quizName.setText(quiz.getName());
        playersAmount.setText("Aktualna liczba graczy: " + quiz.getPlayersAmount());
    }
}
