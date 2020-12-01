package com.obiwanwheeler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @FXML private VBox deckNamesVbox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File deckFolder = new File("src/main/resources/com/obiwanwheeler/decks");
        String[] deckNames = deckFolder.list();
        if (deckNames == null){
            return;
        }
        for(String name : deckNames){
            Button deckButton = new Button(name.replace(".json", ""));
            deckButton.setOnAction(actionEvent -> {
                String deckName = ((Button) actionEvent.getSource()).getText();
                if (Reviewer.tryInitialiseReview(deckName)) {
                    Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    App.changeSceneOnWindow(currentStage, "cardFront");
                }
            });
            deckNamesVbox.getChildren().add(deckButton);
        }
    }
}
