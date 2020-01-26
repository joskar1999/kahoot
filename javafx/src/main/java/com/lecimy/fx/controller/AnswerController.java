package com.lecimy.fx.controller;

import com.lecimy.fx.viewutils.ViewUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import com.lecimy.fx.model.Position;
import com.lecimy.fx.utils.CountdownTimer;
import com.lecimy.fx.viewutils.RankingListViewCell;

import java.net.URL;
import java.util.ResourceBundle;

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
    private CountdownTimer timer;
    private ViewUtils viewUtils = new ViewUtils();

    public AnswerController() {
        ranking = FXCollections.observableArrayList();
        ranking.addAll(new Position(1, "chuj", 125),
            new Position(2, "dupa", 32),
            new Position(3, "pizda", 12));
        timer = new CountdownTimer(8);
        timer.setOnSecondElapseListener(() -> {
            time.setText((timer.getSeconds() - timer.getElapsedSeconds()) + " sekund");
        });
        timer.setOnCountdownFinishListener(() -> {
            Platform.runLater(() -> viewUtils.switchScenes("questionPage.fxml"));
        });
        timer.startTimer();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setItems(ranking);
        listView.setCellFactory(e -> new RankingListViewCell());
        listView.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
    }
}
