package com.obiwanwheeler;

import com.obiwanwheeler.objects.Deck;
import com.obiwanwheeler.objects.OptionGroup;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RenameDeckPopupController {

    @FXML private TextField newNameTextField;
    private DeckSettingsController deckSettingsController;
    private Deck deckToRename;

    @FXML private void onRenameButtonPressed(ActionEvent actionEvent){
        //create deck
        deckToRename.setDeckName(newNameTextField.getText());
        ((Stage) ((Node)(actionEvent.getSource())).getScene().getWindow()).close();
        deckSettingsController.refreshDropdowns();
    }

    public void initController(DeckSettingsController deckSettingsController, Deck deckToRename){
        this.deckSettingsController = deckSettingsController;
        this.deckToRename = deckToRename;
    }
}
