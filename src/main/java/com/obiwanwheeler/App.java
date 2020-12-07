package com.obiwanwheeler;

import com.obiwanwheeler.utilities.FileExtensions;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxmls/mainMenu.fxml"));
        Scene mainMenu = new Scene(root);
        stage.setTitle("Wurmwell SRS");
        stage.setScene(mainMenu);
        stage.show();
    }

    public static void changeSceneOnWindow(Stage targetStage, String targetFXML){
        try {
            Parent cardFrontParent = FXMLLoader.load(App.class.getResource("fxmls/" + targetFXML + FileExtensions.FXML));
            Scene cardFront = new Scene(cardFrontParent);
            targetStage.setScene(cardFront);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Scene getSceneFromPath(String fxmlPath, FXMLLoader loader) throws IOException {
        URL path = App.class.getResource(fxmlPath);
        loader.setLocation(path);

        Parent popupParent = loader.load();
        return new Scene(popupParent);
    }

    public static void createNewStage(Scene scene){
        Stage popupStage = new Stage();
        popupStage.setScene(scene);
        popupStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
