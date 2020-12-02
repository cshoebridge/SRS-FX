package com.obiwanwheeler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @FXML private VBox deckNamesVbox;

    @FXML private void onCreateDeckButtonPressed(ActionEvent actionEvent) throws IOException {
        Stage deckCreateStage = new Stage();
        Parent deckCreateParent = FXMLLoader.load(getClass().getResource("fxmls/createDeck.fxml"));
        deckCreateStage.setScene(new Scene(deckCreateParent));
        deckCreateStage.show();
    }

    private void refreshList(){
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
                    App.changeSceneOnWindow(currentStage, "reviewStart");
                }
            });
            deckButton.setId("deckButton");
            deckNamesVbox.getChildren().add(deckButton);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshList();
    }
}
