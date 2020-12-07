package com.obiwanwheeler.creators;

import com.obiwanwheeler.objects.Deck;
import com.obiwanwheeler.utilities.Serializer;


public class DeckCreator {

    public static void createNewDeck(String deckName){
        Deck newDeck = new Deck(deckName, null);
        Serializer.SERIALIZER_SINGLETON.serializeToNew(newDeck);
    }
}
