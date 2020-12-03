package com.obiwanwheeler;

import com.obiwanwheeler.creators.DeckCreator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateDeckController {

    @FXML private TextField deckNameTextField;
    private MainMenuController mainMenuController;


    @FXML private void onCreateButtonPressed(ActionEvent actionEvent){
        //create deck
        DeckCreator.createNewDeck(deckNameTextField.getText());
        ((Stage) ((Node)(actionEvent.getSource())).getScene().getWindow()).close();
        mainMenuController.refreshDeckList();
    }

    public void initController(MainMenuController mainMenuController){
        this.mainMenuController = mainMenuController;
    }
}
