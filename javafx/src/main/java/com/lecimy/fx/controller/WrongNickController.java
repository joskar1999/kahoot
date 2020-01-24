package com.lecimy.fx.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import com.lecimy.fx.listener.EventListener;
import com.lecimy.fx.listener.OnFailureNickCreationListener;
import com.lecimy.fx.listener.OnSuccessNickCreationListener;
import com.lecimy.fx.net.Client;
import com.lecimy.fx.net.ClientThread;
import com.lecimy.fx.net.handler.NickInitializationHandler;
import com.lecimy.fx.viewutils.ViewUtils;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class WrongNickController implements Initializable {

    @FXML
    private ImageView sendButton;

    @FXML
    private TextField nickText;

    @FXML
    private Text nickInfo;

    private ViewUtils utils;

    public WrongNickController() {
        utils = new ViewUtils();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void sendNick() {
        if (StringUtils.isNotEmpty(nickText.getText())) {
            Client client = Client.getInstance();
            ClientThread clientThread = ClientThread.getInstance();
            clientThread.setRequestHandler(new NickInitializationHandler());
            clientThread.setEventListeners(new EventListener[]{
                (OnSuccessNickCreationListener) () -> utils.switchScenes("welcomePage.fxml"),
                (OnFailureNickCreationListener) () -> {
                    nickInfo.setText("Ponownie podano zajęty nick!");
                    System.out.printf("Nick zajęty");
                }
            });
            client.sendMessage(nickText.getText());
            clientThread.run();
        } else {
            System.out.printf("Podaj nick!");
        }
    }
}
