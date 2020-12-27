package com.obiwanwheeler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class BasicCardFrontController extends CardFront {

    @FXML private void onShowAnswerButtonPressed(ActionEvent actionEvent) throws IOException {
        //switch to back side
        FXMLLoader loader = new FXMLLoader();
        URL path = getClass().getResource("fxmls/cardBack.fxml");
        loader.setLocation(path);

        Parent cardBackParent = loader.load();

        BasicCardBackController basicCardBackController = loader.getController();
        basicCardBackController.initialiseCardGUI(cardToReview);

        Stage currentStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        currentStage.getScene().setRoot(cardBackParent);
    }
}
