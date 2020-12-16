package com.obiwanwheeler;

import com.Alerts;
import com.obiwanwheeler.utilities.DeckFileParser;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @FXML private VBox deckNamesVbox;

    @FXML
    private void onCreateDeckButtonPressed() {
        FXMLLoader loader = new FXMLLoader();
        URL path = getClass().getResource("fxmls/createDeck.fxml");
        loader.setLocation(path);

        Parent deckCreateParent;
        try {
            deckCreateParent = loader.load();
        } catch (IOException e) {
            Alerts.giveLoadFailureAlert();
            return;
        }

        Scene deckCreateScene = new Scene(deckCreateParent);

        CreateDeckController createDeckController = loader.getController();
        createDeckController.initController(this);
        Stage deckCreateStage = new Stage();
        deckCreateStage.setScene(deckCreateScene);
        deckCreateStage.show();
    }

    @FXML
    private void onAddCardButtonPressed() {
        Scene addCardScene = App.getSceneFromFXML("createCard", new FXMLLoader());
        if (addCardScene != null)
            App.createNewStage(addCardScene);
    }

    @FXML
    private void onEditButtonPressed() {
        FXMLLoader loader = new FXMLLoader();
        Scene editScene = App.getSceneFromFXML("deckSettings", loader);

        if (editScene == null)
            return;

        DeckSettingsController deckSettingsController = loader.getController();
        deckSettingsController.initData(this);
        App.createNewStage(editScene);
    }

    @FXML
    private void onBrowseButtonPressed() {
        Scene addCardScene = App.getSceneFromFXML("browser", new FXMLLoader());
        if (addCardScene != null)
            App.createNewStage(addCardScene);
    }

    private void onDeckSelected(ActionEvent actionEvent) {
        String deckName = ((Button) actionEvent.getSource()).getText();
        if (Reviewer.tryInitialiseReview(deckName)) {
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            App.setRoot(currentStage.getScene(), "reviewStart");
        }
    }

    public void refreshDeckList(){
        deckNamesVbox.getChildren().clear();
        for(String name : DeckFileParser.getAlLDeckNames()){
            Button deckButton = new Button(name.replace(".json", ""));
            deckButton.setOnAction(this::onDeckSelected);
            deckButton.setId("deckButton");
            deckNamesVbox.getChildren().add(deckButton);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshDeckList();
    }
}
