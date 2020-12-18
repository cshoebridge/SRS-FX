package com.obiwanwheeler;

import com.obiwanwheeler.objects.Card;
import com.obiwanwheeler.utilities.CardSelector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CardFrontController implements Initializable {

    @FXML private Text targetLanguageSentence;
    private Card cardToReview;

    @FXML private void onShowAnswerButtonPressed(ActionEvent actionEvent) throws IOException {
        //switch to back side
        FXMLLoader loader = new FXMLLoader();
        URL path = getClass().getResource("fxmls/cardBack.fxml");
        loader.setLocation(path);

        Parent cardBackParent = loader.load();

        CardBackController cardBackController = loader.getController();
        cardBackController.initialiseCardGUI(cardToReview);

        Stage currentStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        currentStage.getScene().setRoot(cardBackParent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        if (Reviewer.getCardsToReviewToday().isEmpty()){
            return;
        }
        cardToReview = CardSelector.chooseACard(Reviewer.getCardsToReviewToday());
        targetLanguageSentence.setText(cardToReview.getTargetLanguageSentence());
    }
}
