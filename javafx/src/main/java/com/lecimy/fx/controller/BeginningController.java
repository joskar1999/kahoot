package com.lecimy.fx.controller;

import com.lecimy.fx.listener.EventListener;
import com.lecimy.fx.listener.OnFailureQuestionsReceiveListener;
import com.lecimy.fx.listener.OnSuccessQuestionsReceiveListener;
import com.lecimy.fx.net.Client;
import com.lecimy.fx.net.ClientThread;
import com.lecimy.fx.net.RequestMessages;
import com.lecimy.fx.net.handler.QuestionsReceiveHandler;
import com.lecimy.fx.store.DataStore;
import com.lecimy.fx.utils.CountdownTimer;
import com.lecimy.fx.viewutils.ViewUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

import static com.lecimy.fx.net.RequestMessages.OK;

public class BeginningController implements Initializable {

    @FXML
    private Text seconds;

    private CountdownTimer timer;
    private ViewUtils viewUtils = new ViewUtils();
    private ClientThread clientThread = ClientThread.getInstance();
    private Client client = Client.getInstance();

    public BeginningController() {
        timer = new CountdownTimer(5);
        timer.setOnSecondElapseListener(() -> {
            seconds.setText((timer.getSeconds() - timer.getElapsedSeconds()) + " sekund");
        });
        timer.setOnCountdownFinishListener(() -> {
            System.out.println("game is beginning");
            Platform.runLater(() -> viewUtils.switchScenes("questionPage.fxml"));
        });
        timer.startTimer();
        clientThread.setRequestHandler(new QuestionsReceiveHandler());
        clientThread.setEventListeners(new EventListener[]{
            (OnSuccessQuestionsReceiveListener) () -> {
                client.sendMessage(OK);
                DataStore.setCurrentQuestion(0);
                System.out.println("questions received");
                System.out.println(DataStore.getQuestions());
                System.out.println(DataStore.getQuestionsAmount());
            },
            (OnFailureQuestionsReceiveListener) () -> System.out.println()
        });
        clientThread.run();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
