package com.obiwanwheeler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class BasicCardBackController extends CardBack
{

    @FXML private void onPassButtonPressed(ActionEvent actionEvent) {
        Reviewer.processCardMarkedGood(cardToReview);
        if (Reviewer.sessionIsFinished()){
            //go to review finished screen
            finishReview();
            return;
        }
        toNextCard(actionEvent);
    }

    @FXML private void onFailButtonPressed(ActionEvent actionEvent) {
        Reviewer.processCardMarkedBad(cardToReview);
        if (Reviewer.sessionIsFinished()){
            //go to review finished screen
            finishReview();
            return;
        }
        toNextCard(actionEvent);
    }
}
