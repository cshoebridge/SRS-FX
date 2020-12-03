package com.obiwanwheeler;

import com.obiwanwheeler.interfaces.Renamable;
import com.obiwanwheeler.interfaces.SerializableObject;
import com.obiwanwheeler.utilities.FileExtensions;
import com.obiwanwheeler.utilities.Serializer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;

public class RenamePopupController <T extends Renamable & SerializableObject>{

    @FXML private TextField newNameTextField;
    private DeckSettingsController deckSettingsController;
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
    }

    public void initController(DeckSettingsController deckSettingsController, T objectToRename){
        this.deckSettingsController = deckSettingsController;
        this.objectToRename = objectToRename;
    }
}
