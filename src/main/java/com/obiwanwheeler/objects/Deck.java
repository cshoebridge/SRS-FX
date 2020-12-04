package com.obiwanwheeler.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.obiwanwheeler.interfaces.Updatable;
import com.obiwanwheeler.interfaces.SerializableObject;
import com.obiwanwheeler.utilities.DeckFileParser;
import com.obiwanwheeler.utilities.OptionGroupFileParser;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Deck implements SerializableObject, Updatable {

    private String deckName;

    private List<Card> cards;
    private String optionGroupFilePath;
    @JsonIgnore private OptionGroup optionGroup;

    private int newCardsLeft;

    private LocalDate lastDateReviewed;

    //used in IDE
    public Deck(List<Card> cards) {
        this.cards = cards;
        optionGroup = null;
    }

    //used by DeckCreator
    public Deck(String deckName, String optionGroupFilePath){
        this.deckName = deckName;
        this.cards = new LinkedList<>();
        this.optionGroupFilePath = optionGroupFilePath;
    }

    //used by Jackson
    @JsonCreator
    public Deck(@JsonProperty("deckName") String deckName,
                @JsonProperty("cards") List<Card> cards,
                @JsonProperty("optionGroupFilePath") String optionGroupFilePath,
                @JsonProperty("newCardsLeft") int cardsLeftToReviewToday,
                @JsonProperty("lastDateReviewed") LocalDate lastDateReviewed)
                {
        this.deckName = deckName;
        this.cards = cards;
        this.optionGroupFilePath = optionGroupFilePath;
        this.optionGroup = OptionGroupFileParser.deserializeOptionGroup(optionGroupFilePath);
        this.newCardsLeft = cardsLeftToReviewToday;
        this.lastDateReviewed = lastDateReviewed;
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) { this.deckName = deckName; }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public String getOptionGroupFilePath() {
        return optionGroupFilePath;
    }

    public void setOptionGroupFilePath(String optionGroupFilePath) {
        this.optionGroupFilePath = optionGroupFilePath;
    }

    public OptionGroup getOptionGroup() {
        if (optionGroup == null) {
            return OptionGroupFileParser.DEFAULT_OPTION_GROUP;
        }
        return optionGroup;
    }

    public int getNewCardsLeft() {
        return newCardsLeft;
    }

    public void setNewCardsLeft(int newCardsLeft) {
        this.newCardsLeft = newCardsLeft;
    }

    public LocalDate getLastDateReviewed() {
        return lastDateReviewed;
    }

    public void setLastDateReviewed(LocalDate lastDateReviewed) {
        this.lastDateReviewed = lastDateReviewed;
    }

    @Override
    public String getFolderPath() {
        return DeckFileParser.DECK_FOLDER_PATH;
    }

    @Override
    public String getFileName() {
        return getDeckName();
    }

    @Override
    @JsonIgnore public void setName(String deckName) {
        this.deckName = deckName;
    }

    @Override
    @JsonIgnore public String getName() {
        return deckName;
    }
}
