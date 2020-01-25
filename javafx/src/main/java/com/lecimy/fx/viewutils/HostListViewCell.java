package com.lecimy.fx.viewutils;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import com.lecimy.fx.model.Quiz;

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

    @Override
    protected void updateItem(Quiz item, boolean empty) {
        super.updateItem(item, empty);
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
        System.out.println(quizName.getText());
    }
}
