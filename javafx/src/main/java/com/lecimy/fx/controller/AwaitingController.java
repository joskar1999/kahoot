package com.lecimy.fx.controller;

import com.lecimy.fx.listener.EventListener;
import com.lecimy.fx.listener.OnGameStartFailureListener;
import com.lecimy.fx.listener.OnGameStartListener;
import com.lecimy.fx.listener.OnNewPlayerListener;
import com.lecimy.fx.net.Client;
import com.lecimy.fx.net.ClientThread;
import com.lecimy.fx.net.handler.HostGameAwaitingHandler;
import com.lecimy.fx.viewutils.ViewUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

import static com.lecimy.fx.net.RequestMessages.START;

public class AwaitingController implements Initializable {

    @FXML
    private ImageView startButton;

    @FXML
    private Text playersAmountText;

    private static ClientThread clientThread = ClientThread.getInstance();
    private Client client = Client.getInstance();
    private ViewUtils viewUtils = new ViewUtils();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clientThread.setRequestHandler(new HostGameAwaitingHandler());
        clientThread.setEventListeners(new EventListener[]{
            (OnNewPlayerListener) amount -> {
                System.out.println("aktualna liczba graczy: " + amount);
                playersAmountText.setText("Aktualna liczba graczy: " + amount);
                clientThread.run();
            },
            (OnGameStartListener) () -> {
                System.out.println("on game start");
                Platform.runLater(() -> viewUtils.switchScenes("beginningPage.fxml"));
            },
            (OnGameStartFailureListener) () -> System.out.println("on game start failure")
        });
    }

    @FXML
    void startGame() {
        System.out.println("start game");
        client.sendMessage(START);
    }

    public static void callDeferredThread() {
        System.out.println("calling deferred thread");
        clientThread.run();
    }
}
