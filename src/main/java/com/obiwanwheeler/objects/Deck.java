package com.obiwanwheeler.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.obiwanwheeler.interfaces.Renamable;
import com.obiwanwheeler.interfaces.SerializableObject;
import com.obiwanwheeler.utilities.OptionGroupFileParser;

import java.util.LinkedList;
import java.util.List;

public class Deck implements SerializableObject, Renamable {

    private String deckName;

    private List<Card> cards;
    private String optionGroupFilePath;
    @JsonIgnore private OptionGroup optionGroup;

    public static final String DECK_FOLDER_PATH = "src/main/resources/com/obiwanwheeler/decks/";

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
                @JsonProperty("optionGroupFilePath") String optionGroupFilePath)
                {
        this.deckName = deckName;
        this.cards = cards;
        this.optionGroup = OptionGroupFileParser.deserializeOptionGroup(optionGroupFilePath);
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

    @Override
    public String getFolderPath() {
        return DECK_FOLDER_PATH;
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
