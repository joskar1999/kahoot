package main.java.com.lecimy.fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = new File("src/main/java/com/lecimy/fx/fxml/initialPage.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("lecimy");
        primaryStage.setScene(new Scene(root, 1366, 768));
        primaryStage.setResizable(false);
        primaryStage.show();
        Main.primaryStage = primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
