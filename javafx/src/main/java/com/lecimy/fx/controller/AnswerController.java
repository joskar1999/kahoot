package main.java.com.lecimy.fx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import main.java.com.lecimy.fx.model.Position;
import main.java.com.lecimy.fx.viewutils.RankingListViewCell;

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

    public AnswerController() {
        ranking = FXCollections.observableArrayList();
        ranking.addAll(new Position(1, "chuj", 125),
            new Position(2, "dupa", 32),
            new Position(3, "pizda", 12));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setItems(ranking);
        listView.setCellFactory(e -> new RankingListViewCell());
        listView.getStylesheets().add(getClass().getResource("../css/styles.css").toExternalForm());
    }
}
