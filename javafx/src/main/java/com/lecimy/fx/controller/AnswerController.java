package com.lecimy.fx.controller;

import com.lecimy.fx.listener.*;
import com.lecimy.fx.model.Position;
import com.lecimy.fx.net.Client;
import com.lecimy.fx.net.ClientThread;
import com.lecimy.fx.net.handler.AnswerHandler;
import com.lecimy.fx.store.DataStore;
import com.lecimy.fx.utils.CountdownTimer;
import com.lecimy.fx.viewutils.RankingListViewCell;
import com.lecimy.fx.viewutils.ViewUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import static com.lecimy.fx.net.RequestMessages.OK;

public class AnswerController implements Initializable {

    @FXML
    private Text answerType;

    @FXML
    private Text points;

    @FXML
    private Text time;

    @FXML
    private ListView<Position> listView;

    private ObservableList<Position> ranking;
    private static ClientThread clientThread = ClientThread.getInstance();
    private static Client client = Client.getInstance();
    private CountdownTimer timer;
    private ViewUtils viewUtils = new ViewUtils();

    public AnswerController() {
        ranking = FXCollections.observableArrayList();
        timer = new CountdownTimer(5);
        timer.setOnSecondElapseListener(() -> {
            time.setText((timer.getSeconds() - timer.getElapsedSeconds()) + " sekund");
        });
        timer.setOnCountdownFinishListener(() -> {
            Platform.runLater(() -> viewUtils.switchScenes("questionPage.fxml"));
        });
        timer.startTimer();
        clientThread.setRequestHandler(new AnswerHandler());
        clientThread.setEventListeners(new EventListener[]{
            (OnCorrectAnswerListener) points -> {
                System.out.println("correct answer, points: " + points);
                answerType.setText("Poprawna odpowiedź");
                this.points.setText("Punkty: " + points);
                clientThread.run();
            },
            (OnWrongAnswerListener) points -> {
                System.out.println("wrong answer, points: " + points);
                answerType.setText("Błędna odpowiedź");
                this.points.setText("Punkty: " + points);
                clientThread.run();
            },
            (OnAllAnsweredListener) () -> {
                client.sendMessage(OK);
                System.out.println("all answered");
                clientThread.run();
            },
            (OnRankingReceivedListener) () -> {
                System.out.println("on ranking received");
                Platform.runLater(() -> {
                    List<Position> positions = DataStore.getRanking();
                    ranking.removeAll(ranking);
                    Collections.sort(positions);
                    for (int i = 0; i < positions.size(); i++) {
                        positions.get(i).setPlace(i + 1);
                    }
                    ranking.addAll(positions);
                });
            },
            (OnFailureListener) () -> {
                System.out.println("on failure");
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setItems(ranking);
        listView.setCellFactory(e -> new RankingListViewCell());
        listView.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
    }

    public static void callDeferredThread() {
        System.out.println("calling deferred thread");
        clientThread.run();
    }
}
