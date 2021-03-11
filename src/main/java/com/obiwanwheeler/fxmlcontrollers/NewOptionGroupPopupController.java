package com.obiwanwheeler.fxmlcontrollers;

import com.obiwanwheeler.creators.OptionGroupCreator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewOptionGroupPopupController {

    @FXML private TextField optionGroupNameField;
    private DeckSettingsController deckSettingsController;

    @FXML private void onCreateButtonPressed(ActionEvent actionEvent){
        OptionGroupCreator.createNewOptionGroup(optionGroupNameField.getText());
        ((Stage) ((Node)(actionEvent.getSource())).getScene().getWindow()).close();
        deckSettingsController.refreshDropdowns();
    }

    public void initController(DeckSettingsController deckSettingsController){
        this.deckSettingsController = deckSettingsController;
    }
}
