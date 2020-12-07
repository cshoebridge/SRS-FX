package com.obiwanwheeler.creators;

import com.obiwanwheeler.objects.OptionGroup;
import com.obiwanwheeler.utilities.FileExtensions;
import com.obiwanwheeler.utilities.OptionGroupFileParser;
import com.obiwanwheeler.utilities.Serializer;

import java.util.*;
import java.util.stream.Collectors;

public class OptionGroupCreator {

    public static void createNewOptionGroup(String name){
        OptionGroup newOptionGroup = OptionGroupFileParser.DEFAULT_OPTION_GROUP;
        Objects.requireNonNull(newOptionGroup).setOptionGroupName(name);
        Serializer.SERIALIZER_SINGLETON.serializeToNew(newOptionGroup);
    }

    public static void editOptionsGroup(String name, String newSteps, int newCards, int newGradInterval){
        OptionGroup groupToEdit = OptionGroupFileParser.deserializeOptionGroup(OptionGroupFileParser.OPTION_GROUP_FOLDER_PATH + name + FileExtensions.JSON);
        if (groupToEdit == null){
            return;
        }
        groupToEdit.setOptionGroupName(name);
        List<String> splitStepsString = Arrays.asList(newSteps.split(" "));
        List<Integer> steps = splitStepsString.stream().map(Integer::parseInt).collect(Collectors.toList());
        groupToEdit.setIntervalSteps(steps);
        groupToEdit.setNumberOfNewCardsToLearn(newCards);
        groupToEdit.setGraduatingIntervalInDays(newGradInterval);
        Serializer.SERIALIZER_SINGLETON.serializeToExisting(OptionGroupFileParser.OPTION_GROUP_FOLDER_PATH + name + FileExtensions.JSON, groupToEdit);
    }
}
