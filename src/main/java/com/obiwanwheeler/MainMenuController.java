package com.obiwanwheeler;

import com.obiwanwheeler.utilities.DeckFileParser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @FXML private VBox deckNamesVbox;

    @FXML private void onCreateDeckButtonPressed() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL path = getClass().getResource("fxmls/createDeck.fxml");
        loader.setLocation(path);

        Parent deckCreateParent = loader.load();
        Scene deckCreateScene = new Scene(deckCreateParent);

        CreateDeckController createDeckController = loader.getController();
        createDeckController.initController(this);
        Stage deckCreateStage = new Stage();
        deckCreateStage.setScene(deckCreateScene);
        deckCreateStage.show();
    }

    @FXML private void onAddCardButtonPressed() throws IOException {
        Parent cardCreateParent = FXMLLoader.load(getClass().getResource("fxmls/createCard.fxml"));
        Stage cardCreateStage = new Stage();
        cardCreateStage.setScene(new Scene(cardCreateParent));
        cardCreateStage.show();
    }

    public void refreshList(){
        deckNamesVbox.getChildren().clear();
        for(String name : DeckFileParser.DECK_FILE_PARSER_SINGLETON.getAlLDeckNames()){
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
