package com.obiwanwheeler.fxmlControllers;

import com.obiwanwheeler.interfaces.Updatable;
import com.obiwanwheeler.interfaces.SerializableObject;
import com.obiwanwheeler.utilities.FileExtensions;
import com.obiwanwheeler.utilities.Serializer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;

public class RenamePopupController <T extends Updatable & SerializableObject>{

    @FXML private TextField newNameTextField;
    private DeckSettingsController deckSettingsController;
    private MainMenuController mainMenuController;
    private T objectToRename;

    @FXML private void onRenameButtonPressed(ActionEvent actionEvent){
        //create deck
        String originalName = objectToRename.getName();
        File oldDeckFile = new File(objectToRename.getFolderPath() + originalName + FileExtensions.JSON);
        if (!oldDeckFile.delete()){
            System.out.println("failed to delete old file");
            return;
        }
        objectToRename.setName(newNameTextField.getText());
        Serializer.SERIALIZER_SINGLETON.serializeToNew(objectToRename);
        ((Stage) ((Node)(actionEvent.getSource())).getScene().getWindow()).close();
        deckSettingsController.refreshDropdowns();
        mainMenuController.refreshDeckList();
    }

    public void initController(DeckSettingsController deckSettingsController, MainMenuController mainMenuController, T objectToRename){
        this.deckSettingsController = deckSettingsController;
        this.mainMenuController = mainMenuController;
        this.objectToRename = objectToRename;
    }
}
