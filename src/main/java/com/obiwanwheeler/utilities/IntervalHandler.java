package com.obiwanwheeler.utilities;

import com.obiwanwheeler.objects.Card;
import com.obiwanwheeler.objects.OptionGroup;

import java.time.Duration;
import java.time.Period;
import java.util.List;

public final class IntervalHandler {

    private final List<Integer> intervalSteps;
    private final int graduatingIntervalInDays;

    public IntervalHandler(OptionGroup config) {
        this.intervalSteps = config.getIntervalSteps();
        this.graduatingIntervalInDays = config.getGraduatingIntervalInDays();
    }

    //region when correct answer given

    public void moveFromNewToLearningQueue(Card cardToMove){
        cardToMove.setMinutesUntilNextReviewInThisSession(Duration.ofMinutes(intervalSteps.get(0)));
        cardToMove.setState(Card.CardState.LEARNING);
    }

    public void increaseCardInterval(Card cardToIncrease){
        if (cardToIncrease.getState() == Card.CardState.LEARNT){
            updateLearntCardInterval(cardToIncrease);
            return;
        }

        if (canGraduate(cardToIncrease)){
            graduateCard(cardToIncrease);
            return;
        }

        int nextStepIndex = intervalSteps.indexOf(cardToIncrease.getMinutesUntilNextReviewInThisSession().toMinutesPart()) + 1;
        changeMinutesIntervalStep(cardToIncrease, intervalSteps.get(nextStepIndex));
    }

    private void updateLearntCardInterval(Card cardToUpdate){
        //TODO make algorithm for calculating next interval better
        int correctAnswerIncreaseInDays = 3;
        cardToUpdate.setDaysFromFirstSeenToNextReview(cardToUpdate.getDaysFromFirstSeenToNextReview().plusDays(correctAnswerIncreaseInDays));
    }

    private boolean canGraduate(Card cardToCheck){
        return cardToCheck.getMinutesUntilNextReviewInThisSession().toMinutesPart() == intervalSteps.get(intervalSteps.size() - 1);
    }

    private void graduateCard(Card cardToGraduate){
        cardToGraduate.setDaysFromFirstSeenToNextReview(Period.ofDays(graduatingIntervalInDays));
        cardToGraduate.setState(Card.CardState.LEARNT);
    }

    //endregion

    //region when wrong answer given

    public void decreaseInterval(Card cardToDecrease) {
        if (canDecreaseInterval(cardToDecrease)){
            int nextIntervalIndex = intervalSteps.indexOf(cardToDecrease.getMinutesUntilNextReviewInThisSession().toMinutesPart()) - 1;
            changeMinutesIntervalStep(cardToDecrease, nextIntervalIndex);
        }
    }

    private boolean canDecreaseInterval(Card cardToCheck){
        return cardToCheck.getState() != Card.CardState.NEW && cardToCheck.getMinutesUntilNextReviewInThisSession().toMinutesPart() != intervalSteps.get(0);
    }

    public void relearnCard(Card relapsedCard){
        relapsedCard.setMinutesUntilNextReviewInThisSession(Duration.ofMinutes(10));
        int relapseDecreaseInDays = 1;
        relapsedCard.setDaysFromFirstSeenToNextReview(relapsedCard.getDaysFromFirstSeenToNextReview().minusDays(relapseDecreaseInDays));
        if (relapsedCard.getDaysFromFirstSeenToNextReview().isNegative()) relapsedCard.setDaysFromFirstSeenToNextReview(Period.ofDays(1));
    }

    //endregion

    private void changeMinutesIntervalStep(Card cardToUpdate, int nextStep){
        cardToUpdate.setMinutesUntilNextReviewInThisSession(Duration.ofMinutes(nextStep));
    }
}
