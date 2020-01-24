package main.java.com.lecimy.fx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import main.java.com.lecimy.fx.listener.EventListener;
import main.java.com.lecimy.fx.listener.OnFailureQuizHeadersReceiveListener;
import main.java.com.lecimy.fx.listener.OnSuccessQuizHeadersReceiveListener;
import main.java.com.lecimy.fx.model.Quiz;
import main.java.com.lecimy.fx.net.ClientThread;
import main.java.com.lecimy.fx.net.handler.UserDataFetchHandler;
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
        ClientThread clientThread = ClientThread.getInstance();
        clientThread.setRequestHandler(new UserDataFetchHandler());
        clientThread.setEventListeners(new EventListener[]{
            (OnSuccessQuizHeadersReceiveListener) quizzes -> this.quizzes.addAll(quizzes),
            (OnFailureQuizHeadersReceiveListener) () -> System.out.println("nie udało się odebrać nagłówków gier")
        });
        clientThread.run();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setItems(quizzes);
        listView.setCellFactory(e -> new UserListViewCell());
        listView.getStylesheets().add(getClass().getResource("../css/styles.css").toExternalForm());
    }
}
