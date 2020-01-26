package com.lecimy.fx.viewutils;

import com.lecimy.fx.controller.AwaitingController;
import com.lecimy.fx.controller.HostController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import com.lecimy.fx.model.Quiz;
import com.lecimy.fx.listener.EventListener;
import com.lecimy.fx.listener.OnFailureGameInitializationListener;
import com.lecimy.fx.listener.OnSuccessGameInitializationListener;
import com.lecimy.fx.model.Quiz;
import com.lecimy.fx.net.Client;
import com.lecimy.fx.net.ClientThread;
import com.lecimy.fx.net.handler.GameInitializationHandler;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class HostListViewCell extends ListCell<Quiz> {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Text quizName;

    @FXML
    private Text questionsAmount;

    private FXMLLoader loader;
    private Quiz quiz;
    private ViewUtils viewUtils = new ViewUtils();
    private OnSuccessGameInitializationListener onSuccessGameInitializationListener;
    private OnFailureGameInitializationListener onFailureGameInitializationListener;

    @Override
    protected void updateItem(Quiz item, boolean empty) {
        super.updateItem(item, empty);
        this.quiz = item;

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getResource("/fxml/hostListViewCell.fxml"));
                loader.setController(this);
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            quizName.setText(item.getName());
            questionsAmount.setText(item.getQuestionsAmount() + " pytań");
            setText(null);
            setGraphic(anchorPane);
        }
    }

    @FXML
    public void getCellClicked() {
        ClientThread clientThread = ClientThread.getInstance();
        clientThread.setRequestHandler(new GameInitializationHandler());
        clientThread.setEventListeners(new EventListener[]{
            (OnSuccessGameInitializationListener) () -> {
                Thread thread = new Thread(() -> {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    AwaitingController.callDeferredThread();
                });
                thread.setDaemon(true);
                thread.start();
                viewUtils.switchScenes("awaitingPage.fxml");
            },
            (OnFailureGameInitializationListener) () -> System.out.println("nie można utworzyć gry")
        });
        Client.getInstance().sendMessage(quiz.getName());
        clientThread.run();

    }

    public void setOnSuccessGameInitializationListener(OnSuccessGameInitializationListener onSuccessGameInitializationListener) {
        this.onSuccessGameInitializationListener = onSuccessGameInitializationListener;
    }

    public void setOnFailureGameInitializationListener(OnFailureGameInitializationListener onFailureGameInitializationListener) {
        this.onFailureGameInitializationListener = onFailureGameInitializationListener;
    }
}
