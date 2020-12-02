package com.obiwanwheeler;

import com.obiwanwheeler.creators.DeckCreator;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateDeckController {

    @FXML private TextField deckNameTextField;

    @FXML private void onCreateButtonPressed(){
        //create deck
        DeckCreator.createNewDeck(deckNameTextField.getText());
        ((Stage) (deckNameTextField.getScene().getWindow())).close();
    }
}
