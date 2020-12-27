package com.obiwanwheeler;

import com.obiwanwheeler.objects.Card;
import com.obiwanwheeler.utilities.CardSelector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class CardFront implements Initializable
{
    @FXML private Text targetLanguageSentenceText;
    protected Card cardToReview;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        if (Reviewer.getCardsToReviewToday().isEmpty()){
            return;
        }
        cardToReview = CardSelector.chooseACard(Reviewer.getCardsToReviewToday());
        targetLanguageSentenceText.setText(cardToReview.getTargetLanguageSentence());
    }
}
