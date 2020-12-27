package com.obiwanwheeler.fxmlControllers;

import com.obiwanwheeler.App;
import com.obiwanwheeler.Reviewer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ReviewStartController implements Initializable
{

    @FXML private ComboBox<String> studyStyleDropdown;

    @FXML private void onStartButtonPressed(ActionEvent actionEvent) {
        if (studyStyleDropdown.getValue() == null)
            return;

        String style = studyStyleDropdown.getValue();
        Stage currentStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        if (Reviewer.getCardsToReviewToday().isEmpty()){
            finishSession(currentStage);
        }
        else{
            String styleFxml;
            switch (style.toLowerCase()){
                case "flash cards": styleFxml = "basic";
                    break;
                case "read and respond": styleFxml = "input";
                    break;
                default: finishSession(currentStage);
                         return;
            }
            App.setRoot(currentStage.getScene(), styleFxml + "CardFront");
        }
    }

    private void finishSession(Stage stage){
        App.setRoot(stage.getScene(), "sessionFinished");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        studyStyleDropdown.getItems().add("Flash Cards");
        studyStyleDropdown.getItems().add("Read and Respond");
    }
}
