package com.lecimy.fx.viewutils;

import com.lecimy.fx.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewUtils {

    public void switchScenes(String fileName) {
        Parent root = null;
        Stage primaryStage = Main.getPrimaryStage();
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/" + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1366, 768));
    }
}
