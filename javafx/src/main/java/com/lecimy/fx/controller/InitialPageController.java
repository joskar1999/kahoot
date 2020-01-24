package com.lecimy.fx.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
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

public class InitialPageController implements Initializable {

    @FXML
    private TextField ipTextField;

    @FXML
    private TextField portTextField;

    @FXML
    private ImageView loginButton;

    @FXML
    private TextField nickTextField;

    private ViewUtils utils;

    public InitialPageController() {
        this.utils = new ViewUtils();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void loginToKahoot() {
        if (!StringUtils.isAnyEmpty(ipTextField.getText(), portTextField.getText(), nickTextField.getText())) {
            Client client = Client.getInstance();
            client.setConnectionParams(ipTextField.getText(), Integer.parseInt(portTextField.getText()), nickTextField.getText());
            client.sendMessage(nickTextField.getText());

            System.out.println("wysłano nick : " + nickTextField.getText());

            ClientThread clientThread = ClientThread.getInstance();
            clientThread.initClientThread(client);
            clientThread.setRequestHandler(new NickInitializationHandler());
            clientThread.setEventListeners(new EventListener[]{
                (OnSuccessNickCreationListener) () -> utils.switchScenes("welcomePage.fxml"),
                (OnFailureNickCreationListener) () -> utils.switchScenes("wrongNickPage.fxml")
            });
            clientThread.run();
        } else {
            System.out.println("All fields have to be filled out"); //todo popup
        }
    }

}
