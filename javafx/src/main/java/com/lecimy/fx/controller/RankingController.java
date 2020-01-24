package com.lecimy.fx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import com.lecimy.fx.model.Position;
import com.lecimy.fx.viewutils.RankingListViewCell;

import java.net.URL;
import java.util.ResourceBundle;

public class RankingController implements Initializable {

    @FXML
    private ListView<Position> listView;

    private ObservableList<Position> ranking;

    public RankingController() {
        ranking = FXCollections.observableArrayList();
        ranking.addAll(new Position(1, "chuj", 2137),
            new Position(2, "dupa", 1945),
            new Position(3, "pizda", 1918));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setItems(ranking);
        listView.setCellFactory(e -> new RankingListViewCell());
        listView.getStylesheets().add(getClass().getResource("../css/styles.css").toExternalForm());
    }
}
