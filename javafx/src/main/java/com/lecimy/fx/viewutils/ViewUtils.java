package com.lecimy.fx.viewutils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.lecimy.fx.Main;

import java.io.File;
import java.io.IOException;
import java.net.URL;

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
