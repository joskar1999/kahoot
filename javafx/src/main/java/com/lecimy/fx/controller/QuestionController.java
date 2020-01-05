package main.java.com.lecimy.fx.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class QuestionController implements Initializable {

    @FXML
    private Text quizName;

    @FXML
    private Text questionNumber;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void aClicked() {
        System.out.println("a");
    }

    @FXML
    void bClicked() {
        System.out.println("b");
    }

    @FXML
    void cClicked() {
        System.out.println("c");
    }

    @FXML
    void dClicked() {
        System.out.println("d");
    }
}
