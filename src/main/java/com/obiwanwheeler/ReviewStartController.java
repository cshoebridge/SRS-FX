package com.obiwanwheeler;

import com.obiwanwheeler.utilities.FileExtensions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ReviewStartController{

    @FXML private void onStartButtonPressed(ActionEvent actionEvent){
        Stage currentStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        if (Reviewer.getCardsToReviewToday().isEmpty()){
            App.changeSceneOnWindow(currentStage, "sessionFinished");
        }
        else{
            App.changeSceneOnWindow(currentStage, "cardFront");
        }
    }
}
