package main.java.com.lecimy.fx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import main.java.com.lecimy.fx.model.Quiz;
import main.java.com.lecimy.fx.viewutils.HostListViewCell;
import main.java.com.lecimy.fx.viewutils.UserListViewCell;

import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    @FXML
    private ListView<Quiz> listView;

    private ObservableList<Quiz> quizzes;

    public UserController() {
        quizzes = FXCollections.observableArrayList();
        quizzes.addAll(new Quiz("Alkohole", "10", "2"),
            new Quiz("Alkohole2", "10", "2"),
            new Quiz("Alkohole3", "10", "3"),
            new Quiz("Alkohole4", "10", "4"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setItems(quizzes);
        listView.setCellFactory(e -> new UserListViewCell());
        listView.getStylesheets().add(getClass().getResource("../css/styles.css").toExternalForm());
    }
}
