package main.java.com.lecimy.fx.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class WrongNickController implements Initializable {

    @FXML
    private ImageView sendButton;

    @FXML
    private TextField nickText;

    @FXML
    void sendNick() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
