package com.obiwanwheeler.fxmlControllers;

import com.obiwanwheeler.App;
import com.obiwanwheeler.CardBack;
import com.obiwanwheeler.Reviewer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class BasicCardBackController extends CardBack
{

    @FXML private void onPassButtonPressed(ActionEvent actionEvent) {
        Reviewer.processCardMarkedGood(cardToReview);
        if (Reviewer.sessionIsFinished()){
            //go to review finished screen
            finishReview(actionEvent);
            return;
        }
        toNextCard(actionEvent);
    }

    @FXML private void onFailButtonPressed(ActionEvent actionEvent) {
        Reviewer.processCardMarkedBad(cardToReview);
        if (Reviewer.sessionIsFinished()){
            //go to review finished screen
            finishReview(actionEvent);
            return;
        }
        toNextCard(actionEvent);
    }

    @Override
    protected void toNextCard(ActionEvent eventSource)
    {
        Stage currentStage = (Stage)((Node)eventSource.getSource()).getScene().getWindow();
        App.setRoot(currentStage.getScene(), "basicCardFront");
    }
}
