package com;

import javafx.scene.control.Alert;

public final class Alerts {

    public static final void giveLoadFailureAlert(){
        Alert fxmlNotFoundAlert = new Alert(Alert.AlertType.ERROR);
        fxmlNotFoundAlert.setHeaderText("Loading error");
        fxmlNotFoundAlert.setContentText("The requested item could not be loaded,\nit is probably missing or corrupted");
        fxmlNotFoundAlert.showAndWait();
    }

}
