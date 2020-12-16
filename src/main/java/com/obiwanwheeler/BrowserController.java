package com.obiwanwheeler;

import com.obiwanwheeler.objects.Card;
import com.obiwanwheeler.objects.Deck;
import com.obiwanwheeler.utilities.DeckFileParser;
import com.obiwanwheeler.utilities.FileExtensions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class BrowserController implements Initializable {
    @FXML private ListView<String> decksListView;
    @FXML private TableView<Card> deckTableView;
    @FXML private TableColumn<Card, String> frontColumn;
    @FXML private TableColumn<Card, String> backColumn;
    @FXML private TableColumn<Card, Card.CardState> stateColumn;
    @FXML private TableColumn<Card, LocalDate> nextDateColumn;
    private Deck selectedDeck;

    private void populateDeckList(){
        for (String deck : DeckFileParser.getAlLDeckNames()) {
            decksListView.getItems().add(deck.replace(".json", ""));
        }

        decksListView.getSelectionModel().selectedItemProperty().addListener((selectedItem, s, t1) -> refreshDeckTable(selectedItem));
    }

    private void refreshDeckTable(javafx.beans.value.ObservableValue<? extends String> selectedItem) {
        selectedDeck = DeckFileParser.deserializeDeck(DeckFileParser.DECK_FOLDER_PATH + selectedItem.getValue() + FileExtensions.JSON);
        deckTableView.getItems().clear();
        ObservableList<Card> cardsObservableList = FXCollections.observableArrayList();
        cardsObservableList.addAll(selectedDeck.getCards());
        deckTableView.setItems(cardsObservableList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        frontColumn.setCellValueFactory(new PropertyValueFactory<>("targetLanguageSentence"));
        backColumn.setCellValueFactory(new PropertyValueFactory<>("nativeLanguageTranslation"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
        nextDateColumn.setCellValueFactory(new PropertyValueFactory<>("nextReviewDate"));
        populateDeckList();
    }
}
