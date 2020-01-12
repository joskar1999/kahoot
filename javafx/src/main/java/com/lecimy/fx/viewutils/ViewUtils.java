package main.java.com.lecimy.fx.viewutils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.com.lecimy.fx.Main;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ViewUtils {

    public void switchScenes(String fileName) {
        Parent root = null;
        Stage primaryStage = Main.getPrimaryStage();
        try {
            URL url = new File("src/main/java/com/lecimy/fx/fxml/" + fileName).toURI().toURL();
            root = FXMLLoader.load(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1366, 768));
    }
}
