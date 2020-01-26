package com.lecimy.fx.viewutils;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import com.lecimy.fx.model.Position;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class RankingListViewCell extends ListCell<Position> {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Text place;

    @FXML
    private Text nick;

    @FXML
    private Text score;

    private FXMLLoader loader;

    @Override
    protected void updateItem(Position item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getResource("/fxml/rankingListViewCell.fxml"));
                loader.setController(this);
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            place.setText("Miejsce: " + item.getPlace() + ", ");
            nick.setText(item.getName() + ", ");
            score.setText("punkty: " + item.getScore());
            setText(null);
            setGraphic(anchorPane);
        }
    }
}
