package com.lecimy.fx.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class AwaitingController implements Initializable {

    @FXML
    private ImageView startButton;

    @FXML
    private Text playersAmountText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void startGame() {
        System.out.println("start game");
    }
}
