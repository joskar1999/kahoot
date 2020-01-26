package com.lecimy.fx.controller;

import com.lecimy.fx.listener.EventListener;
import com.lecimy.fx.listener.OnGameStartFailureListener;
import com.lecimy.fx.listener.OnGameStartListener;
import com.lecimy.fx.listener.OnNewPlayerListener;
import com.lecimy.fx.net.Client;
import com.lecimy.fx.net.ClientThread;
import com.lecimy.fx.net.handler.HostGameAwaitingHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class AwaitingController implements Initializable {

    @FXML
    private ImageView startButton;

    @FXML
    private Text playersAmountText;

    private static ClientThread clientThread = ClientThread.getInstance();
    private Client client = Client.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clientThread.setRequestHandler(new HostGameAwaitingHandler());
        clientThread.setEventListeners(new EventListener[]{
            (OnNewPlayerListener) amount -> {
                System.out.println("aktualna liczba graczy: " + amount);
                playersAmountText.setText("Aktualna liczba graczy: " + amount);
                clientThread.run();
            },
            (OnGameStartListener) () -> System.out.println("on game start"),
            (OnGameStartFailureListener) () -> System.out.println("on game start failure")
        });
    }

    @FXML
    void startGame() {
        System.out.println("start game");
    }

    public static void callDeferredThread() {
        System.out.println("calling deferred thread");
        clientThread.run();
    }
}
