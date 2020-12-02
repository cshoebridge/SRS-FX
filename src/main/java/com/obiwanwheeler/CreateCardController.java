package com.obiwanwheeler;

import com.obiwanwheeler.creators.CardCreator;
import com.obiwanwheeler.utilities.DeckFileParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateCardController implements Initializable {

    @FXML private ComboBox<String> deckDropDown;
    @FXML private TextArea frontSideTextArea;
    @FXML private TextArea backSideTextArea;

    @FXML private void onAddButtonPressed(ActionEvent actionEvent){
        if (deckDropDown.getValue() != null){
            CardCreator.createNewCard(deckDropDown.getValue(), frontSideTextArea.getText(), backSideTextArea.getText());
            ((Stage) ((Node)(actionEvent.getSource())).getScene().getWindow()).close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deckDropDown.getItems().clear();
        for(String name : DeckFileParser.DECK_FILE_PARSER_SINGLETON.getAlLDeckNames()){
            deckDropDown.getItems().add(name.replace(".json", ""));
        }
    }
}
