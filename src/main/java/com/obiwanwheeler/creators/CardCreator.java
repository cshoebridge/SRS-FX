package com.obiwanwheeler.creators;

import com.obiwanwheeler.objects.Card;
import com.obiwanwheeler.objects.Deck;
import com.obiwanwheeler.utilities.DeckFileParser;
import com.obiwanwheeler.utilities.FileExtensions;
import com.obiwanwheeler.utilities.Serializer;

import java.util.Scanner;

public class CardCreator {

    public static void createNewCard(String deckToAddToName, String front, String back){
        String deckToAddToPath = DeckFileParser.DECK_FOLDER_PATH + deckToAddToName + FileExtensions.JSON;
        Deck tempDeck = getDeckToAddTo(deckToAddToPath);
        if (tempDeck == null){
            return;
        }
        Card.Builder cardBuilder = new Card.Builder();
        Card newCard = cardBuilder.frontSide(front).backSide(back).state(Card.CardState.NEW).build();
        tempDeck.getCards().add(newCard);
        rewriteDeck(deckToAddToPath, tempDeck);
    }

    private static Deck getDeckToAddTo(String deckFilePath){
        return DeckFileParser.DECK_FILE_PARSER_SINGLETON.deserializeDeck(deckFilePath);
    }

    private static void rewriteDeck(String deckToAddToPath, Deck deckToRewrite){
        Serializer.SERIALIZER_SINGLETON.serializeToExisting(deckToAddToPath, deckToRewrite);
    }
}
