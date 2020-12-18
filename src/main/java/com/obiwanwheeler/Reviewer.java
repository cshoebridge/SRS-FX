package com.obiwanwheeler;

import com.obiwanwheeler.utilities.*;
import com.obiwanwheeler.objects.Card;
import com.obiwanwheeler.objects.Deck;

import java.time.LocalDate;
import java.util.*;

public class Reviewer {

    private static String deckFilePath;
    public static String getDeckFilePath() {
        return deckFilePath;
    }
    private static int numberOfCardsLeftToBeReviewed;
    private static List<Card> cardsToReviewToday = new LinkedList<>();
    public static List<Card> getCardsToReviewToday() {
        return cardsToReviewToday;
    }
    private static Deck updatedDeck;
    public static Deck getUpdatedDeck() {
        return updatedDeck;
    }
    private static IntervalHandler intervalHandler;

    //region initialise review
    public static boolean tryInitialiseReview(String deckName){
        deckFilePath = DeckFileParser.DECK_FOLDER_PATH + deckName + FileExtensions.JSON;
        Deck deckToReview = DeckFileParser.deserializeDeck(deckFilePath);
        if (deckToReview == null){
            return false;
        }
        updatedDeck = initialiseUpdatedDeck(deckToReview);
        insertUnchangedCardsInto(deckToReview);

        intervalHandler = new IntervalHandler(deckToReview.getOptionGroup());

        cardsToReviewToday = DeckManipulator.DECK_MANIPULATOR_SINGLETON.getCardsToReviewToday(deckToReview);

        numberOfCardsLeftToBeReviewed = cardsToReviewToday.size();
        return true;
    }

    private static Deck initialiseUpdatedDeck(Deck sourceDeck){
        updatedDeck = new Deck(new LinkedList<>());
        updatedDeck.setDeckName(sourceDeck.getDeckName());
        updatedDeck.setOptionGroupFilePath(sourceDeck.getOptionGroupFilePath());
        updatedDeck.setLastDateReviewed(LocalDate.now());

        if (sourceDeck.getLastDateReviewed() == null){
            updatedDeck.setNewCardsLeft(sourceDeck.getOptionGroup().getNumberOfNewCardsToLearn());
        }
        else{
            if (sourceDeck.getLastDateReviewed().isBefore(LocalDate.now())){
                updatedDeck.setNewCardsLeft(sourceDeck.getOptionGroup().getNumberOfNewCardsToLearn());
            }
            else {
                updatedDeck.setNewCardsLeft(sourceDeck.getNewCardsLeft());
            }
        }
        return updatedDeck;
    }

    private static void insertUnchangedCardsInto(Deck sourceDeck){
        List<Card> unchangedCards = DeckManipulator.DECK_MANIPULATOR_SINGLETON.getCardsNotBeingReviewedToday(sourceDeck);
        updatedDeck.getCards().addAll(unchangedCards);
    }
    //endregion

    public static void processCardMarkedBad(Card markedCard){
        if (markedCard.getState() == Card.CardState.LEARNT){
            intervalHandler.relearnCard(markedCard);
        }
        else {
            intervalHandler.decreaseInterval(markedCard);
        }
    }

    public static void processCardMarkedGood(Card markedCard){
        if (markedCard.getState() == Card.CardState.NEW){
            updatedDeck.setNewCardsLeft(updatedDeck.getNewCardsLeft() - 1);
            intervalHandler.moveFromNewToLearningQueue(markedCard);
        }
        else{
            intervalHandler.increaseCardInterval(markedCard);
        }

        if (checkIfCardIsFinishedForSession(markedCard)){
            finishReviewingCardForSession(markedCard);
        }
    }

    public static boolean sessionIsFinished(){
        return numberOfCardsLeftToBeReviewed == 0;
    }

    private static boolean checkIfCardIsFinishedForSession(Card cardToCheck){
        return cardToCheck.getState() == Card.CardState.LEARNT;
    }

    private static void finishReviewingCardForSession(Card finishedCard){
        cardsToReviewToday.remove(finishedCard);
        numberOfCardsLeftToBeReviewed--;
        updatedDeck.getCards().add(finishedCard);
    }
}
