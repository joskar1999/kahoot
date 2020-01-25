package com.lecimy.fx.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import com.lecimy.fx.listener.EventListener;
import com.lecimy.fx.listener.OnFailureHostCreationListener;
import com.lecimy.fx.listener.OnSuccessHostCreationListener;
import com.lecimy.fx.net.Client;
import com.lecimy.fx.net.ClientThread;
import com.lecimy.fx.net.handler.HostInitializationHandler;
import com.lecimy.fx.viewutils.ViewUtils;
import com.lecimy.fx.listener.*;
import com.lecimy.fx.net.Client;
import com.lecimy.fx.net.ClientThread;
import com.lecimy.fx.net.handler.HostInitializationHandler;
import com.lecimy.fx.net.handler.UserInitializationHandler;
import com.lecimy.fx.viewutils.ViewUtils;

import java.net.URL;
import java.util.ResourceBundle;

import static com.lecimy.fx.net.RequestMessages.USER;

public class WelcomeController implements Initializable {

    private static final String HOST = "HOST";
    private Client client = Client.getInstance();
    private ClientThread clientThread = ClientThread.getInstance();
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
        clientThread.setRequestHandler(new HostInitializationHandler());
        clientThread.setEventListeners(new EventListener[]{
            (OnSuccessHostCreationListener) () -> utils.switchScenes("hostPage.fxml"),
            (OnFailureHostCreationListener) () -> System.out.println("Nie udało się utworzyć hosta")
        });
        client.sendMessage(HOST);
        clientThread.run();
    }

    @FXML
    void sendToUserPage() {
        clientThread.setRequestHandler(new UserInitializationHandler());
        clientThread.setEventListeners(new EventListener[]{
            (OnSuccessUserCreationListener) () -> utils.switchScenes("userPage.fxml"),
            (OnFailureUserCreationListener) () -> System.out.println("nie można utworzyć gracza")
        });
        client.sendMessage(USER);
        clientThread.run();
    }
}
