package com.obiwanwheeler.utilities;

import com.obiwanwheeler.objects.Card;

import java.util.List;
import java.util.Random;

public final class CardSelector {

    private static final Random random = new Random();

    public static Card chooseACard(List<Card> cardsToChooseFrom){
        //get lowest interval cards
        List<Card> lowestIntervalCards = DeckManipulator.DECK_MANIPULATOR_SINGLETON.getLowestIntervalCards(cardsToChooseFrom);
        //chooses a card from those
        return lowestIntervalCards.get(random.nextInt(lowestIntervalCards.size()));
    }
}
