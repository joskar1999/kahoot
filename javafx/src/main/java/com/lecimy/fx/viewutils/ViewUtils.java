package main.java.com.lecimy.fx.viewutils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.com.lecimy.fx.Main;

import java.io.IOException;

public class ViewUtils {

    public void switchScenes(String fileName) {
        Parent root = null;
        Stage primaryStage = Main.getPrimaryStage();
        String filePath = "../fxml/" + fileName;
        try {
            root = FXMLLoader.load(getClass().getResource(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1366, 768));
    }
}
