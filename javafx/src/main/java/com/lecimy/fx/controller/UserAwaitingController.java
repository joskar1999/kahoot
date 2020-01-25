package main.java.com.lecimy.fx.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import main.java.com.lecimy.fx.listener.EventListener;
import main.java.com.lecimy.fx.listener.OnGameStartFailureListener;
import main.java.com.lecimy.fx.listener.OnGameStartListener;
import main.java.com.lecimy.fx.listener.OnNewPlayerListener;
import main.java.com.lecimy.fx.model.Quiz;
import main.java.com.lecimy.fx.net.ClientThread;
import main.java.com.lecimy.fx.net.handler.UserGameAwaitingHandler;
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
        ClientThread clientThread = ClientThread.getInstance();
        clientThread.setRequestHandler(new UserGameAwaitingHandler());
        clientThread.setEventListeners(new EventListener[]{
            (OnNewPlayerListener) amount -> {
                playersAmount.setText("Aktualna liczba graczy: " + amount);
                clientThread.run();
            },
            (OnGameStartListener) () -> {
                System.out.println("on start");
            },
            (OnGameStartFailureListener) () -> {
                System.out.println("on start failure");
            }
        });
        clientThread.run();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        quizName.setText(quiz.getName());
        playersAmount.setText("Aktualna liczba graczy: " + quiz.getPlayersAmount());
    }
}
