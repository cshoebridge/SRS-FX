package com.obiwanwheeler;

import com.obiwanwheeler.objects.Card;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;

public class CardBackController {

    @FXML private Text targetLanguageSentence;
    @FXML private Text nativeLanguageTranslation;
    @FXML private ImageView descriptionImage;
    private Card cardToReview;

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

    private void toNextCard(ActionEvent eventSource) {
        Stage currentStage = (Stage)((Node)eventSource.getSource()).getScene().getWindow();
        App.setRoot(currentStage.getScene(), "cardFront");
    }

    private void finishReview() {
        Stage currentStage = (Stage)targetLanguageSentence.getScene().getWindow();
        App.setRoot(currentStage.getScene(), "sessionFinished");
    }

    public void initialiseCardGUI(Card card){
        this.cardToReview = card;
        targetLanguageSentence.setText(card.getTargetLanguageSentence());
        nativeLanguageTranslation.setText(card.getNativeLanguageTranslation());
        String imagePath;
        if ((imagePath = card.getImagePath()) != null){
            File selectedImage = new File(imagePath);
            Image image = new Image(selectedImage.toURI().toString());
            descriptionImage.setImage(image);
        }

    }
}
