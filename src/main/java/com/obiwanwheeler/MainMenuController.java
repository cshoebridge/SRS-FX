package com.obiwanwheeler;

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
        Parent deckSettingsParent = FXMLLoader.load(getClass().getResource("fxmls/createCard.fxml"));
        Stage deckSettingsStage = new Stage();
        deckSettingsStage.setScene(new Scene(deckSettingsParent));
        deckSettingsStage.show();
    }

    @FXML private void onEditButtonPressed() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Scene editScene = App.getSceneFromFXML("deckSettings", loader);
        DeckSettingsController deckSettingsController = loader.getController();
        deckSettingsController.initData(this);
        App.createNewStage(editScene);
    }

    private void onDeckSelected(ActionEvent actionEvent) {
        String deckName = ((Button) actionEvent.getSource()).getText();
        if (Reviewer.tryInitialiseReview(deckName)) {
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            try {
                App.setRoot(currentStage.getScene(), "reviewStart");
            } catch (IOException e) {
                //TODO error handling 1
                e.printStackTrace();
            }
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
