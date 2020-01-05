package main.java.com.lecimy.fx.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import main.java.com.lecimy.fx.net.Client;

import java.net.URL;
import java.util.ResourceBundle;

public class InitialPageController implements Initializable {

    @FXML
    private TextField ipTextField;

    @FXML
    private TextField portTextField;

    @FXML
    private ImageView loginButton;

    @FXML
    private TextField nickTextField;

    private Client client;

    public InitialPageController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void loginToKahoot() {
        client = new Client(ipTextField.getText(), Integer.parseInt(portTextField.getText()), nickTextField.getText());
        client.sendMessage("chuj do dupy psom bo to kurwy są");
    }
}
