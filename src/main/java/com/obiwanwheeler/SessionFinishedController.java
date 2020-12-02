package com.obiwanwheeler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class SessionFinishedController {

    @FXML private void onBackButtonPressed(ActionEvent actionEvent){
        Stage currentStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        App.changeSceneOnWindow(currentStage, "mainMenu");
    }
}
