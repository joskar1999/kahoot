package main.java.com.lecimy.fx.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import main.java.com.lecimy.fx.listener.EventListener;
import main.java.com.lecimy.fx.listener.OnFailureHostCreationListener;
import main.java.com.lecimy.fx.listener.OnSuccessHostCreationListener;
import main.java.com.lecimy.fx.net.Client;
import main.java.com.lecimy.fx.net.ClientThread;
import main.java.com.lecimy.fx.net.handler.HostInitializationHandler;
import main.java.com.lecimy.fx.viewutils.ViewUtils;

import java.net.URL;
import java.util.ResourceBundle;

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
        utils.switchScenes("userPage.fxml");
    }
}
