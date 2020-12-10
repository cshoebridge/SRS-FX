package com.obiwanwheeler;

import com.obiwanwheeler.interfaces.SerializableObject;
import com.obiwanwheeler.interfaces.Updatable;
import com.obiwanwheeler.utilities.FileExtensions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;

public class DeleteWarningController<T extends Updatable & SerializableObject>{

    @FXML private Text deleteText;
    private DeckSettingsController deckSettingsController;
    private MainMenuController mainMenuController;
    private T objectToDelete;

    @FXML private void onCancelButtonPressed(ActionEvent actionEvent){
        ((Stage) ((Node)(actionEvent.getSource())).getScene().getWindow()).close();
    }

    @FXML private void onDeleteButtonPressed(ActionEvent actionEvent){
        //create deck
        String deckName = objectToDelete.getName();
        File deckFile = new File(objectToDelete.getFolderPath() + deckName + FileExtensions.JSON);
        if (!deckFile.delete()){
            System.out.println("failed to delete old file");
            return;
        }
        ((Stage) ((Node)(actionEvent.getSource())).getScene().getWindow()).close();
        deckSettingsController.refreshDropdowns();
        mainMenuController.refreshDeckList();
    }

    public void initController(DeckSettingsController deckSettingsController, MainMenuController mainMenuController, T objectToDelete){
        deleteText.setText("Do you really want to delete " + objectToDelete.getName() + "?\n This action cannot be undone.");

        this.deckSettingsController = deckSettingsController;
        this.mainMenuController = mainMenuController;
        this.objectToDelete = objectToDelete;
    }
}
