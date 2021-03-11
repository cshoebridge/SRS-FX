package com.obiwanwheeler.fxmlcontrollers;

import com.obiwanwheeler.objects.Card;
import com.obiwanwheeler.utilities.DeckFileParser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateCardController implements Initializable {

    @FXML private ComboBox<String> deckDropDown;
    @FXML private TextArea frontSideTextArea;
    @FXML private TextArea backSideTextArea;
    @FXML private ImageView imageView;
    private String imagePath;

    @FXML private void onAddButtonPressed(){
        if (canCreate()){
            Card.Builder builder = new Card.Builder();
            Card newCard = builder.frontSide(frontSideTextArea.getText()).backSide(backSideTextArea.getText()).imagePath(imagePath)
                    .build();
            newCard.writeNewCardToFile(deckDropDown.getValue());
            frontSideTextArea.setText("");
            backSideTextArea.setText("");
            imageView.setImage(null);
        }
    }

    @FXML private void onAddImageButtonPressed(){
        FileChooser fileChooser = new FileChooser();
        File selectedImage = fileChooser.showOpenDialog(null);
        Image image = new Image(selectedImage.toURI().toString());
        imageView.setImage(image);
        imagePath = selectedImage.getAbsolutePath();
    }

    private boolean canCreate(){
        return deckDropDown.getValue() != null
                && !frontSideTextArea.getText().isEmpty()
                && !backSideTextArea.getText().isEmpty();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deckDropDown.getItems().clear();
        for(String name : DeckFileParser.getAlLDeckNames()){
            deckDropDown.getItems().add(name.replace(".json", ""));
        }
    }
}
