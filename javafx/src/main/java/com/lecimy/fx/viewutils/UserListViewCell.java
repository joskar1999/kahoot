package com.lecimy.fx.viewutils;

import com.lecimy.fx.controller.UserAwaitingController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import com.lecimy.fx.model.Quiz;
import com.lecimy.fx.listener.EventListener;
import com.lecimy.fx.listener.OnFailureJoinGameListener;
import com.lecimy.fx.listener.OnSuccessJoinGameListener;
import com.lecimy.fx.net.Client;
import com.lecimy.fx.net.ClientThread;
import com.lecimy.fx.net.handler.JoinGameHandler;
import com.lecimy.fx.store.DataStore;

import java.io.IOException;

import static com.lecimy.fx.net.RequestMessages.JOIN;

public class UserListViewCell extends ListCell<Quiz> {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Text quizName;

    @FXML
    private Text questionsAmount;

    @FXML
    private Text playersAmount;

    private FXMLLoader loader;
    private Quiz quiz;
    private ViewUtils viewUtils = new ViewUtils();
    private OnSuccessJoinGameListener onSuccessJoinGameListener;
    private OnFailureJoinGameListener onFailureJoinGameListener;

    @Override
    protected void updateItem(Quiz item, boolean empty) {
        super.updateItem(item, empty);
        this.quiz = item;

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getResource("/fxml/userListViewCell.fxml"));
                loader.setController(this);
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            quizName.setText(item.getName() + item.getId());
            questionsAmount.setText(item.getQuestionsAmount() + " pytań");
            playersAmount.setText("gracze: " + item.getPlayersAmount());
            setText(null);
            setGraphic(anchorPane);
        }
    }

    @FXML
    public void getCellClicked() {
        System.out.println("dołączanie do gry " + quiz.getName() + quiz.getId());
        ClientThread clientThread = ClientThread.getInstance();
        Client client = Client.getInstance();
        clientThread.setRequestHandler(new JoinGameHandler());
        clientThread.setEventListeners(new EventListener[]{
            (OnSuccessJoinGameListener) () -> {
                DataStore.setQuiz(quiz);
                Thread thread = new Thread(() -> {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    UserAwaitingController.callDeferredThread();
                });
                thread.setDaemon(true);
                thread.start();
                viewUtils.switchScenes("userAwaitingPage.fxml");
            },
            (OnFailureJoinGameListener) () -> System.out.println("nie można dołączyć do gry")
        });
        client.sendMessage(JOIN);
        client.sendMessage(String.valueOf(quiz.getId()));
        clientThread.run();
    }

    public void setOnSuccessJoinGameListener(OnSuccessJoinGameListener onSuccessJoinGameListener) {
        this.onSuccessJoinGameListener = onSuccessJoinGameListener;
    }

    public void setOnFailureJoinGameListener(OnFailureJoinGameListener onFailureJoinGameListener) {
        this.onFailureJoinGameListener = onFailureJoinGameListener;
    }
}
