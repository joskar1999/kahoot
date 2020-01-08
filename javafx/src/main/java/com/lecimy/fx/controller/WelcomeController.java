package main.java.com.lecimy.fx.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import main.java.com.lecimy.fx.viewutils.ViewUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {

    private ViewUtils utils;

    public WelcomeController() {
        this.utils = new ViewUtils();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void sendToAdminPage() {
        utils.switchScenes("adminView.fxml");
    }

    @FXML
    void sendToHostPage() {
        utils.switchScenes("hostPage.fxml");
    }

    @FXML
    void sendToUserPage() {
        utils.switchScenes("userPage.fxml");
    }
}
