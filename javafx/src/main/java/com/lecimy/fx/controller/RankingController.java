package com.lecimy.fx.controller;

import com.lecimy.fx.listener.*;
import com.lecimy.fx.model.Position;
import com.lecimy.fx.net.Client;
import com.lecimy.fx.net.ClientThread;
import com.lecimy.fx.net.handler.AnswerHandler;
import com.lecimy.fx.store.DataStore;
import com.lecimy.fx.viewutils.RankingListViewCell;
import com.lecimy.fx.viewutils.ViewUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import static com.lecimy.fx.net.RequestMessages.OK;

public class RankingController implements Initializable {

    @FXML
    private ListView<Position> listView;

    private ObservableList<Position> ranking;
    private ClientThread clientThread = ClientThread.getInstance();
    private Client client = Client.getInstance();
    private ViewUtils viewUtils = new ViewUtils();

    public RankingController() {
        ranking = FXCollections.observableArrayList();
        clientThread.setRequestHandler(new AnswerHandler());
        clientThread.setEventListeners(new EventListener[]{
            (OnCorrectAnswerListener) points -> {
                System.out.println("correct answer, points: " + points);
                clientThread.run();
            },
            (OnWrongAnswerListener) points -> {
                System.out.println("wrong answer, points: " + points);
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
        clientThread.run();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setItems(ranking);
        listView.setCellFactory(e -> new RankingListViewCell());
        listView.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
    }

    @FXML
    void end() {
        viewUtils.switchScenes("initialPage.fxml");
    }
}
