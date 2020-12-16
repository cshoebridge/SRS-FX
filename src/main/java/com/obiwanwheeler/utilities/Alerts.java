package com.obiwanwheeler.utilities;

import javafx.scene.control.Alert;

public final class Alerts {

    public static void giveLoadFailureAlert(){
        Alert fxmlNotFoundAlert = new Alert(Alert.AlertType.ERROR);
        fxmlNotFoundAlert.setHeaderText("Loading error");
        fxmlNotFoundAlert.setContentText("The requested scene could not be loaded,\nit is probably missing or corrupted");
        fxmlNotFoundAlert.showAndWait();
    }

    public static void giveDeleteFailureAlert(){
        Alert deleteFailureAlert = new Alert(Alert.AlertType.ERROR);
        deleteFailureAlert.setHeaderText("Delete error");
        deleteFailureAlert.setContentText("The requested item could not be deleted");
        deleteFailureAlert.showAndWait();
    }

    public static void giveDeckUnfoundAlert() {
        Alert unfoundItemAlert = new Alert(Alert.AlertType.ERROR);
        unfoundItemAlert.setHeaderText("Unfound item error");
        unfoundItemAlert.setContentText("The requested deck could not be found, \nit is probably missing or corrupted");
        unfoundItemAlert.showAndWait();
    }
}
