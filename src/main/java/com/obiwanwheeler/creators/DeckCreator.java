package com.obiwanwheeler.creators;

import com.obiwanwheeler.objects.Deck;
import com.obiwanwheeler.utilities.Serializer;

import java.util.Scanner;

public class DeckCreator {

    Scanner scanner = new Scanner(System.in);

    public static void createNewDeck(String deckName){
        //TODO in FX give option to choose option group
        //String optionGroupName = askForOptionGroupName();
        Deck newDeck = new Deck(deckName, null);
        Serializer.SERIALIZER_SINGLETON.serializeToNew(newDeck);
    }
}
