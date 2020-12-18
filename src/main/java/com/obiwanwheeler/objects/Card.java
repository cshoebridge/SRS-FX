package com.obiwanwheeler.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.obiwanwheeler.utilities.DeckFileParser;
import com.obiwanwheeler.utilities.FileExtensions;
import com.obiwanwheeler.utilities.Serializer;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class Card {

    private String targetLanguageSentence;
    private String nativeLanguageTranslation;

    private String imagePath;

    private CardState state;

    private Period daysFromFirstSeenToNextReview;

    private LocalDate initialViewDate;
    @JsonIgnore private LocalDate nextReviewDate;
    @JsonIgnore private Duration minutesUntilNextReviewInThisSession;
    @JsonIgnore private boolean shouldBeReviewed;

    @JsonCreator
    public Card(@JsonProperty("targetLanguageSentence") String targetLanguageSentence,
                @JsonProperty("nativeLanguageSentence") String nativeLanguageTranslation,
                @JsonProperty("imagePath") String imagePath,
                @JsonProperty("state") CardState state ,
                @JsonProperty("initialViewDate") LocalDate initialViewDate,
                @JsonProperty ("daysFromFirstSeenToNextReview") Period daysFromFirstSeenToNextReview) {
        this.targetLanguageSentence = targetLanguageSentence;
        this.nativeLanguageTranslation = nativeLanguageTranslation;
        this.imagePath = imagePath;
        this.state = Objects.requireNonNullElse(state, CardState.NEW);
        this.initialViewDate = initialViewDate;

        if (state != CardState.NEW){
            this.daysFromFirstSeenToNextReview = Objects.requireNonNullElse(daysFromFirstSeenToNextReview, Period.ZERO);
            minutesUntilNextReviewInThisSession = daysFromFirstSeenToNextReview.getDays() >= 1 ? Duration.ofMinutes(-1) : Duration.ZERO;
            nextReviewDate = initialViewDate.plus(daysFromFirstSeenToNextReview);
            shouldBeReviewed = LocalDate.now().isAfter(nextReviewDate) || LocalDate.now().equals(nextReviewDate);
        }
        else{
            shouldBeReviewed = true;
            minutesUntilNextReviewInThisSession = Duration.ZERO;
        }
    }

    public void writeNewCardToFile(String deckToAddToName){
        String deckToAddToPath = DeckFileParser.DECK_FOLDER_PATH + deckToAddToName + FileExtensions.JSON;
        Deck tempDeck = DeckFileParser.deserializeDeck(deckToAddToPath);
        if (tempDeck == null){
            return;
        }
        tempDeck.getCards().add(this);
        Serializer.SERIALIZER_SINGLETON.serializeToExisting(deckToAddToPath, tempDeck);
    }

    //region getters and setters
    public String getTargetLanguageSentence() {
        return targetLanguageSentence;
    }

    public void setTargetLanguageSentence(String targetLanguageSentence) {
        this.targetLanguageSentence = targetLanguageSentence;
    }

    public String getNativeLanguageTranslation() {
        return nativeLanguageTranslation;
    }

    public void setNativeLanguageTranslation(String nativeLanguageTranslation) {
        this.nativeLanguageTranslation = nativeLanguageTranslation;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public CardState getState() {
        return state;
    }

    public void setState(CardState state) {
        this.state = state;
    }

    public LocalDate getInitialViewDate() {
        return initialViewDate;
    }

    public void setInitialViewDate(LocalDate initialViewDate){
        this.initialViewDate = initialViewDate;
    }

    public LocalDate getNextReviewDate() {
        return nextReviewDate;
    }

    public Duration getMinutesUntilNextReviewInThisSession() {
        return minutesUntilNextReviewInThisSession;
    }

    public void setMinutesUntilNextReviewInThisSession(Duration minutesUntilNextReviewInThisSession) {
        this.minutesUntilNextReviewInThisSession = minutesUntilNextReviewInThisSession;
    }

    public Period getDaysFromFirstSeenToNextReview() {
        return daysFromFirstSeenToNextReview;
    }

    public void setDaysFromFirstSeenToNextReview(Period daysFromFirstSeenToNextReview) {
        this.daysFromFirstSeenToNextReview = daysFromFirstSeenToNextReview;
    }

    public boolean getShouldBeReviewed() {
        return shouldBeReviewed;
    }

    public void setShouldBeReviewed(boolean shouldBeReviewed){
        this.shouldBeReviewed = shouldBeReviewed;
    }
    //endregion

    //region builder class
    public static class Builder{
        private String frontSide;
        private String backSide;
        private String imagePath;

        private CardState state;

        public Builder frontSide(String frontSide) {
            this.frontSide = frontSide;
            return this;
        }

        public Builder backSide(String backSide) {
            this.backSide = backSide;
            return this;
        }

        public Builder imagePath(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }

        public Builder state(CardState state) {
            this.state = state;
            return this;
        }

        public Card build(){
            return new Card(frontSide, backSide, imagePath, state, LocalDate.now(), Period.ZERO);
        }
    }
    //endregion

    public enum CardState{
        NEW, LEARNING, LEARNT
    }
}
