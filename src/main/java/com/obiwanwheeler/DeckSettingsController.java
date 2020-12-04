package com.obiwanwheeler;

import com.obiwanwheeler.creators.OptionGroupCreator;
import com.obiwanwheeler.interfaces.Renamable;
import com.obiwanwheeler.interfaces.SerializableObject;
import com.obiwanwheeler.objects.Deck;
import com.obiwanwheeler.objects.OptionGroup;
import com.obiwanwheeler.utilities.DeckFileParser;
import com.obiwanwheeler.utilities.FileExtensions;
import com.obiwanwheeler.utilities.OptionGroupFileParser;
import com.obiwanwheeler.utilities.Serializer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
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

    @FXML private void onDeckSelected(){
        //TODO PLEASE MAN get rid of the repetition me and the homies only write DRY code
        selectedDeck = DeckFileParser.deserializeDeck(DeckFileParser.DECK_FOLDER_PATH + deckDropdown.getValue() + FileExtensions.JSON);
        if (selectedDeck == null){
            return;
        }
        StringBuilder stepsFieldText = new StringBuilder();
        for (int step : selectedDeck.getOptionGroup().getIntervalSteps()){
            stepsFieldText.append(step).append(" ");
        }
        stepsField.setText(stepsFieldText.toString());
        newCardsField.setText(String.valueOf(selectedDeck.getOptionGroup().getNumberOfNewCardsToLearn()));
        graduatingIntervalField.setText(String.valueOf(selectedDeck.getOptionGroup().getGraduatingIntervalInDays()));
        optionGroupDropdown.setValue(selectedDeck.getOptionGroup().getOptionGroupName());
    }

    @FXML private void onOptionGroupSelected(){
        selectedOptionGroup = OptionGroupFileParser.deserializeOptionGroup(OptionGroupFileParser.OPTION_GROUP_FOLDER_PATH + optionGroupDropdown.getValue() + FileExtensions.JSON);
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
        selectedDeck.setOptionGroupFilePath(OptionGroupFileParser.OPTION_GROUP_FOLDER_PATH + selectedOptionGroup.getName() + FileExtensions.JSON);
    }

    @FXML private void onMakeNewGroupButtonPressed() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL path = getClass().getResource("fxmls/newOptionGroupPopup.fxml");
        loader.setLocation(path);

        Parent popupParent = loader.load();
        Scene popupScene = new Scene(popupParent);

        NewOptionGroupPopupController popupController = loader.getController();
        popupController.initController(this);
        Stage popupStage = new Stage();
        popupStage.setScene(popupScene);
        popupStage.show();
    }

    @FXML private void onRenameGroupButtonPressed() throws IOException {
        if (selectedOptionGroup != null && !optionGroupDropdown.getValue().equals("default")){
            createRenamePopup(selectedOptionGroup);
        }
    }

    @FXML private void onRenameDeckButtonPressed() throws IOException{
        if (selectedDeck != null){
            createRenamePopup(selectedDeck);
        }
    }

    private <T extends Renamable & SerializableObject> void createRenamePopup(T objectToRename) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL path = getClass().getResource("fxmls/renamePopup.fxml");
        loader.setLocation(path);

        Parent popupParent = loader.load();
        Scene popupScene = new Scene(popupParent);

        RenamePopupController<T> popupController = loader.getController();
        popupController.initController(this, objectToRename);
        Stage popupStage = new Stage();
        popupStage.setScene(popupScene);
        popupStage.show();
    }

    @FXML private void onSaveButtonPressed(){
        if (selectedDeck == null){
            return;
        }
        if (selectedOptionGroup == null){
            return;
        }
        //TODO abstract getting file path to a function
        OptionGroupCreator.editOptionsGroup(optionGroupDropdown.getValue(), stepsField.getText() ,Integer.parseInt(newCardsField.getText()), Integer.parseInt(graduatingIntervalField.getText()));
        selectedDeck.setOptionGroupFilePath(OptionGroupFileParser.OPTION_GROUP_FOLDER_PATH + optionGroupDropdown.getValue() + FileExtensions.JSON);
        Serializer.SERIALIZER_SINGLETON.serializeToExisting(DeckFileParser.DECK_FOLDER_PATH + deckDropdown.getValue() + FileExtensions.JSON, selectedDeck);
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshDropdowns();
    }
}