package com.obiwanwheeler.fxmlcontrollers;

import com.obiwanwheeler.App;
import com.obiwanwheeler.Reviewer;
import com.obiwanwheeler.utilities.DeckFileParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @FXML private VBox deckNamesVbox;

    @FXML
    private void onCreateDeckButtonPressed() {
        FXMLLoader loader = new FXMLLoader();
        Scene createScene = App.getSceneFromFXMLWithLoader("createDeck", loader);

        if (createScene == null)
            return;


        CreateDeckController createDeckController = loader.getController();
        createDeckController.initController(this);
        App.createNewStage(createScene);
    }

    @FXML
    private void onAddCardButtonPressed() {
        Scene addCardScene = App.getSceneFromFXMLWithLoader("createCard", new FXMLLoader());
        if (addCardScene != null)
            App.createNewStage(addCardScene);
    }

    @FXML
    private void onEditButtonPressed() {
        FXMLLoader loader = new FXMLLoader();
        Scene editScene = App.getSceneFromFXMLWithLoader("deckSettings", loader);

        if (editScene == null)
            return;


        DeckSettingsController deckSettingsController = loader.getController();
        deckSettingsController.initData(this);
        App.createNewStage(editScene);
    }

    @FXML
    private void onBrowseButtonPressed() {
        Scene addCardScene = App.getSceneFromFXMLWithLoader("browser", new FXMLLoader());
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
