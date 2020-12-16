package com.obiwanwheeler.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.obiwanwheeler.interfaces.Updatable;
import com.obiwanwheeler.interfaces.SerializableObject;

import java.util.List;

public class OptionGroup implements SerializableObject , Updatable {

    private String optionGroupName;
    private int graduatingIntervalInDays;
    private List<Integer> intervalSteps;
    private int numberOfNewCardsToLearn;

    private static final String OPTION_GROUP_FOLDER_PATH = "src/main/resources/com/obiwanwheeler/option-groups/";

    @JsonCreator
    public OptionGroup(@JsonProperty("optionGroupName") String optionGroupName,
                       @JsonProperty("graduatingIntervalInDays") int graduatingIntervalInDays,
                       @JsonProperty("intervalSteps") List<Integer> intervalSteps,
                       @JsonProperty("numberOfNewCardsToLearn") int numberOfNewCardsToLearn)
    {
        this.optionGroupName = optionGroupName;
        this.graduatingIntervalInDays = graduatingIntervalInDays;
        this.intervalSteps = intervalSteps;
        this.numberOfNewCardsToLearn = numberOfNewCardsToLearn;
    }

    public String getOptionGroupName() {
        return optionGroupName;
    }

    public void setOptionGroupName(String optionGroupName) {
        this.optionGroupName = optionGroupName;
    }

    public int getGraduatingIntervalInDays() {
        return graduatingIntervalInDays;
    }

    public void setGraduatingIntervalInDays(int graduatingIntervalInDays) {
        this.graduatingIntervalInDays = graduatingIntervalInDays;
    }

    public List<Integer> getIntervalSteps() {
        return intervalSteps;
    }

    public void setIntervalSteps(List<Integer> intervalSteps) {
        this.intervalSteps = intervalSteps;
    }

    public int getNumberOfNewCardsToLearn() {
        return numberOfNewCardsToLearn;
    }

    public void setNumberOfNewCardsToLearn(int numberOfNewCardsToLearn) {
        this.numberOfNewCardsToLearn = numberOfNewCardsToLearn;
    }

    @Override
    public String getFolderPath() {
        return OPTION_GROUP_FOLDER_PATH;
    }

    @Override
    public String getFileName() {
        return getOptionGroupName();
    }

    @Override
    @JsonIgnore public void setName(String optionGroupName) {
        this.optionGroupName = optionGroupName;
    }

    @Override
    @JsonIgnore public String getName() {
        return optionGroupName;
    }

    @Deprecated
    //region builder class
    public static class Builder{
        private String optionGroupName;
        private int graduatingIntervalInDays;
        private List<Integer> intervalSteps;
        private int numberOfNewCardsToLearn;

        public Builder optionGroupName(String optionGroupName){
            this.optionGroupName = optionGroupName;
            return this;
        }

        public Builder graduatingIntervalInDays(int graduatingIntervalInDays) {
            this.graduatingIntervalInDays = graduatingIntervalInDays;
            return  this;
        }

        public Builder intervalSteps(List<Integer> intervalSteps) {
            this.intervalSteps = intervalSteps;
            return this;
        }

        public Builder numberOfNewCardsToLearn(int numberOfNewCardsToLearn) {
            this.numberOfNewCardsToLearn = numberOfNewCardsToLearn;
            return this;
        }

        public OptionGroup build(){
            return new OptionGroup(optionGroupName, graduatingIntervalInDays, intervalSteps, numberOfNewCardsToLearn);
        }
    }
    //endregion
}
