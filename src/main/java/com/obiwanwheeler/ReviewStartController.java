package com.obiwanwheeler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;

public class ReviewStartController{

    @FXML private void onStartButtonPressed(ActionEvent actionEvent) throws IOException {
        Stage currentStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        if (Reviewer.getCardsToReviewToday().isEmpty()){
            App.setRoot(currentStage.getScene(), "sessionFinished");
        }
        else{
            App.setRoot(currentStage.getScene(), "cardFront");
        }
    }
}
