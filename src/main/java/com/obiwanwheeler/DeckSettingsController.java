package com.obiwanwheeler;

import com.obiwanwheeler.creators.OptionGroupCreator;
import com.obiwanwheeler.interfaces.Updatable;
import com.obiwanwheeler.interfaces.SerializableObject;
import com.obiwanwheeler.objects.Deck;
import com.obiwanwheeler.objects.OptionGroup;
import com.obiwanwheeler.utilities.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DeckSettingsController implements Initializable {

    @FXML private TextField stepsField;
    @FXML private TextField newCardsField;
    @FXML private TextField graduatingIntervalField;
    @FXML private ComboBox<String> optionGroupDropdown;
    @FXML private ComboBox<String> deckDropdown;
    private Deck selectedDeck;
    private OptionGroup selectedOptionGroup;
    private MainMenuController mainMenuController;

    @FXML private void onDeckSelected(){
        selectedDeck = DeckFileParser.deserializeDeck(getFilePath(DeckFileParser.DECK_FOLDER_PATH, deckDropdown.getValue()));
        if (selectedDeck == null){
            return;
        }
        StringBuilder stepsFieldText = new StringBuilder();
        for (int step : selectedDeck.getOptionGroup().getIntervalSteps()){
            stepsFieldText.append(step).append(" ");
        }
        OptionGroup deckOptionGroup = selectedDeck.getOptionGroup();
        stepsField.setText(stepsFieldText.toString());
        newCardsField.setText(String.valueOf(deckOptionGroup.getNumberOfNewCardsToLearn()));
        graduatingIntervalField.setText(String.valueOf(deckOptionGroup.getGraduatingIntervalInDays()));
        optionGroupDropdown.setValue(deckOptionGroup.getOptionGroupName());
    }

    @FXML private void onOptionGroupSelected(){
        selectedOptionGroup = OptionGroupFileParser.deserializeOptionGroup(getFilePath(OptionGroupFileParser.OPTION_GROUP_FOLDER_PATH, optionGroupDropdown.getValue()));
        if (selectedOptionGroup == null){
            return;
        }
        StringBuilder stepsFieldText = new StringBuilder();
        for (int step : selectedOptionGroup.getIntervalSteps()){
            stepsFieldText.append(step).append(" ");
        }
        stepsField.setText(stepsFieldText.toString());
        newCardsField.setText(String.valueOf(selectedOptionGroup.getNumberOfNewCardsToLearn()));
        graduatingIntervalField.setText(String.valueOf(selectedOptionGroup.getGraduatingIntervalInDays()));
        optionGroupDropdown.setValue(selectedOptionGroup.getOptionGroupName());
        if (selectedDeck != null)
            selectedDeck.setOptionGroupFilePath(OptionGroupFileParser.OPTION_GROUP_FOLDER_PATH + selectedOptionGroup.getName() + FileExtensions.JSON);
    }

    @FXML private void onMakeNewGroupButtonPressed() {
        FXMLLoader loader = new FXMLLoader();
        Scene popupScene = App.getSceneFromFXML("newOptionGroupPopup", loader);

        if (popupScene == null)
            return;

        NewOptionGroupPopupController popupController = loader.getController();
        popupController.initController(this);
        App.createNewStage(popupScene);
    }

    @FXML private void onRenameGroupButtonPressed() {
        if (selectedOptionGroup != null && !optionGroupDropdown.getValue().equals("default")){
            createRenamePopup(selectedOptionGroup);
        }
    }

    @FXML private void onRenameDeckButtonPressed() {
        if (selectedDeck != null){
            createRenamePopup(selectedDeck);
        }
    }

    private <T extends Updatable & SerializableObject> void createRenamePopup(T objectToRename) {
        FXMLLoader loader = new FXMLLoader();
        Scene popupScene = App.getSceneFromFXML("renamePopup", loader);

        if (popupScene == null)
            return;

        RenamePopupController<T> popupController = loader.getController();
        popupController.initController(this, mainMenuController, objectToRename);
        App.createNewStage(popupScene);
    }

    @FXML private void onSaveButtonPressed(){
        mainMenuController.refreshDeckList();
        //must have an option group selected
        if (selectedOptionGroup != null){
            try{
                OptionGroupCreator.editOptionsGroup(optionGroupDropdown.getValue(), stepsField.getText()
                        ,Integer.parseInt(newCardsField.getText()), Integer.parseInt(graduatingIntervalField.getText()));
            }
            catch (NumberFormatException e){
                Alerts.giveInvalidStepEntryAlert();
                return;
            }
            if (selectedDeck != null){
                selectedDeck.setOptionGroupFilePath(getFilePath(OptionGroupFileParser.OPTION_GROUP_FOLDER_PATH, optionGroupDropdown.getValue()));
                Serializer.SERIALIZER_SINGLETON.serializeToExisting(getFilePath(DeckFileParser.DECK_FOLDER_PATH, deckDropdown.getValue()), selectedDeck);
            }
        }
    }

    @FXML private void onDeleteDeckButtonPressed() {
        if (selectedDeck != null){
            createDeletePopup(selectedDeck);
        }
    }

    @FXML private void onDeleteOptionGroupButtonPressed() {
        if (selectedOptionGroup != null && !optionGroupDropdown.getValue().equals("default")){
            createDeletePopup(selectedOptionGroup);
        }
    }

    private <T extends Updatable & SerializableObject> void createDeletePopup(T objectToDelete) {
        FXMLLoader loader = new FXMLLoader();
        Scene deletionWarningScene = App.getSceneFromFXML("deletePopup", loader);

        if (deletionWarningScene == null)
            return;

        DeleteWarningController<T> deleteWarningController = loader.getController();
        deleteWarningController.initController(this, mainMenuController, objectToDelete);
        App.createNewStage(deletionWarningScene);
    }

    private String getFilePath(String folderPath, String name) {
        return folderPath + name + FileExtensions.JSON;
    }

    public void refreshDropdowns(){
        deckDropdown.getItems().clear();
        for(String name : DeckFileParser.getAlLDeckNames()){
            deckDropdown.getItems().add(name.replace(".json", ""));
        }

        optionGroupDropdown.getItems().clear();
        for(String name : OptionGroupFileParser.getAllOptionGroupNames()){
            optionGroupDropdown.getItems().add(name.replace(".json", ""));
        }
    }

    public void initData(MainMenuController mainMenuController){
        this.mainMenuController = mainMenuController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshDropdowns();
    }
}
