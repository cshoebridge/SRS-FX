package com.obiwanwheeler;

import com.obiwanwheeler.interfaces.SerializableObject;
import com.obiwanwheeler.interfaces.Updatable;
import com.obiwanwheeler.utilities.FileExtensions;
import com.obiwanwheeler.utilities.Serializer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;

public class DeleteWarningController<T extends Updatable & SerializableObject>{

    private DeckSettingsController deckSettingsController;
    private T objectToRename;

    @FXML private void onDeleteButtonPressed(ActionEvent actionEvent){
        //create deck
        String originalName = objectToRename.getName();
        File oldDeckFile = new File(objectToRename.getFolderPath() + originalName + FileExtensions.JSON);
        if (!oldDeckFile.delete()){
            System.out.println("failed to delete old file");
            return;
        }
        Serializer.SERIALIZER_SINGLETON.serializeToNew(objectToRename);
        ((Stage) ((Node)(actionEvent.getSource())).getScene().getWindow()).close();
        deckSettingsController.refreshDropdowns();
    }

    public void initController(DeckSettingsController deckSettingsController, T objectToDelete){
        this.deckSettingsController = deckSettingsController;
        this.objectToRename = objectToDelete;
    }
}
