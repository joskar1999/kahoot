package com.lecimy.fx.controller;

import com.lecimy.fx.listener.EventListener;
import com.lecimy.fx.listener.OnCorrectAnswerListener;
import com.lecimy.fx.listener.OnWrongAnswerListener;
import com.lecimy.fx.model.Question;
import com.lecimy.fx.net.Client;
import com.lecimy.fx.net.ClientThread;
import com.lecimy.fx.net.RequestMessages;
import com.lecimy.fx.net.handler.AnswerHandler;
import com.lecimy.fx.store.DataStore;
import com.lecimy.fx.utils.CountdownTimer;
import com.lecimy.fx.viewutils.ViewUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

import static com.lecimy.fx.net.RequestMessages.*;

public class QuestionController implements Initializable {

    @FXML
    private Text quizName;

    @FXML
    private Text questionNumber;

    @FXML
    private HBox cDtimer;

    @FXML
    private Text seconds;

    @FXML
    private Text awaiting;

    @FXML
    private Text questionText;

    @FXML
    private Text answerA;

    @FXML
    private Text answerD;

    @FXML
    private Text answerC;

    @FXML
    private Text answerB;

    private ClientThread clientThread = ClientThread.getInstance();
    private Client client = Client.getInstance();
    private CountdownTimer timer;
    private ViewUtils viewUtils = new ViewUtils();
    private boolean answerSelected = false;
    private long start;
    private long stop;

    public QuestionController() {
        timer = new CountdownTimer(10);
        timer.setOnSecondElapseListener(() -> {
            seconds.setText((timer.getSeconds() - timer.getElapsedSeconds()) + " sekund");
        });
        timer.setOnCountdownFinishListener(() -> {
            if (!answerSelected) {
                stop = Calendar.getInstance().getTimeInMillis();
                System.out.println("sending E");
                client.sendMessage(E);
                handleTime();
            }
            Platform.runLater(() -> {
                if (DataStore.getCurrentQuestion() != DataStore.getQuestionsAmount()) {
                    Thread thread = new Thread(() -> {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        AnswerController.callDeferredThread();
                    });
                    thread.setDaemon(true);
                    thread.start();
                    viewUtils.switchScenes("answerPage.fxml");
                } else {
                    viewUtils.switchScenes("rankingPage.fxml");
                }
            });
        });
        start = Calendar.getInstance().getTimeInMillis();
        timer.startTimer();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        awaiting.setVisible(false);
        Question question = DataStore.getQuestions().get(DataStore.getCurrentQuestion());
        questionText.setText(question.getQuestion());
        answerA.setText(question.getA());
        answerB.setText(question.getB());
        answerC.setText(question.getC());
        answerD.setText(question.getD());
        DataStore.setCurrentQuestion(DataStore.getCurrentQuestion() + 1);
        questionNumber.setText("Pytanie " + DataStore.getCurrentQuestion());
    }

    private void handleAnswerClicked() {
        stop = Calendar.getInstance().getTimeInMillis();
        cDtimer.setVisible(false);
        awaiting.setVisible(true);
        answerSelected = true;
    }

    @FXML
    void aClicked() {
        handleAnswerClicked();
        System.out.println("sending A");
        client.sendMessage(A);
        handleTime();
    }

    private void handleTime() {
        long duration = stop - start;
        client.sendMessage(String.valueOf(duration));
        System.out.println("answer time: " + duration);
    }

    @FXML
    void bClicked() {
        handleAnswerClicked();
        System.out.println("sending B");
        client.sendMessage(B);
        handleTime();
    }

    @FXML
    void cClicked() {
        handleAnswerClicked();
        System.out.println("sending C");
        client.sendMessage(C);
        handleTime();
    }

    @FXML
    void dClicked() {
        handleAnswerClicked();
        System.out.println("sending D");
        client.sendMessage(D);
        handleTime();
    }
}
