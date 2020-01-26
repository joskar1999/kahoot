package com.lecimy.fx.controller;

import com.lecimy.fx.listener.EventListener;
import com.lecimy.fx.listener.OnGameStartFailureListener;
import com.lecimy.fx.listener.OnGameStartListener;
import com.lecimy.fx.listener.OnNewPlayerListener;
import com.lecimy.fx.model.Quiz;
import com.lecimy.fx.net.ClientThread;
import com.lecimy.fx.net.handler.UserGameAwaitingHandler;
import com.lecimy.fx.store.GameStore;
import com.lecimy.fx.viewutils.ViewUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class UserAwaitingController implements Initializable {

    @FXML
    private Text playersAmount;

    @FXML
    private Text quizName;

    private Quiz quiz;
    private static ClientThread clientThread = ClientThread.getInstance();
    private ViewUtils viewUtils = new ViewUtils();

    public UserAwaitingController() {
        quiz = GameStore.getQuiz();
        clientThread.setRequestHandler(new UserGameAwaitingHandler());
        clientThread.setEventListeners(new EventListener[]{
            (OnNewPlayerListener) amount -> {
                System.out.println("new player, amount: " + amount);
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
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        quizName.setText(quiz.getName());
        playersAmount.setText("Aktualna liczba graczy: " + quiz.getPlayersAmount());
    }

    public static void callDeferredThread() {
        System.out.println("calling deferred thread");
        clientThread.run();
    }
}
