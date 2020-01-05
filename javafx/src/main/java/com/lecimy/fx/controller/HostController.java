package main.java.com.lecimy.fx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import main.java.com.lecimy.fx.model.Quiz;
import main.java.com.lecimy.fx.viewutils.HostListViewCell;

import java.net.URL;
import java.util.ResourceBundle;

public class HostController implements Initializable {

    @FXML
    private ListView<Quiz> listView;

    private ObservableList<Quiz> quizzes;

    public HostController() {
        quizzes = FXCollections.observableArrayList();
        quizzes.addAll(new Quiz("Alkohole", 10, 0),
            new Quiz("Alkohole", 10, 0),
            new Quiz("Alkohole", 10, 0),
            new Quiz("Alkohole", 10, 0),
            new Quiz("Alkohole", 10, 0));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setItems(quizzes);
        listView.setCellFactory(e -> new HostListViewCell());
    }
}
