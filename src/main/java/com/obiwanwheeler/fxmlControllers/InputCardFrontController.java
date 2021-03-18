package com.obiwanwheeler.fxmlControllers;

import com.obiwanwheeler.App;
import com.obiwanwheeler.CardFront;
import com.obiwanwheeler.Reviewer;
import com.obiwanwheeler.utilities.Thesaurus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class InputCardFrontController extends CardFront
{

    @FXML
    private TextField responseTextField;
    @FXML
    private Button nextButton;
    @FXML
    private void onResponseGiven(ActionEvent actionEvent)
    {
        
        String response = responseTextField.getText();
        nativeLanguageSentenceText.setText(cardToReview.getNativeLanguageTranslation());

        nextButton.setVisible(true);
        nextButton.setDisable(false);

        if (isCloseEnough(response, cardToReview.getNativeLanguageTranslation()))
        {
            Reviewer.processCardMarkedGood(cardToReview);
            if (Reviewer.sessionIsFinished())
            {
                //go to review finished screen
                finishReview(actionEvent);
            }
        } else
        {
            Reviewer.processCardMarkedBad(cardToReview);
        }
    }

    @FXML
    private void onNextButtonPress(ActionEvent actionEvent) {
        nextButton.setVisible(false);
        nextButton.setDisable(true);
        toNextCard(actionEvent);
    }

    private boolean isCloseEnough(String response, String answer)
    {
        return typoCheck(response, answer) || thesaurusCheck(response, answer) || matchWithoutSyntax(response, answer);
    }

    private boolean matchWithoutSyntax(String response, String answer)
    {
        answer = answer.toLowerCase().
                replaceAll("\\p{Punct}", "")
                .replaceAll(" ", "");
        response = response.toLowerCase()
                .replaceAll(" ", "")
                .replaceAll("\\p{Punct}", "");
        return answer.equals(response);
    }

    private boolean thesaurusCheck(String response, String answer)
    {
        String[] ansWords = answer.split(" ");

        for (int i = 0; i < ansWords.length; i++)
        {
            List<String> synonyms = Thesaurus.getSynonyms(ansWords[i]);

            for (String synonym : synonyms)
            {
                if (response.contains(synonym))
                {
                    ansWords[i] = synonym;
                }
            }
        }
        String updatedAns = String.join(" ", ansWords);
        return response.equals(updatedAns);
    }

    private boolean typoCheck(String response, String answer)
    {
        return false;
        //TODO add this check
    }

    private void finishReview(ActionEvent actionEvent)
    {
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        App.setRoot(currentStage.getScene(), "sessionFinished");
    }

    private void toNextCard(ActionEvent actionEvent)
    {
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        App.setRoot(currentStage.getScene(), "inputCardFront");
    }
}
