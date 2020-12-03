package com.obiwanwheeler.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.obiwanwheeler.objects.Deck;
import com.obiwanwheeler.objects.OptionGroup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class OptionGroupFileParser {

    public static final String OPTION_GROUP_FOLDER_PATH = "src/main/resources/com/obiwanwheeler/option-groups/";
    public static final OptionGroup DEFAULT_OPTION_GROUP = new OptionGroup("default", 1, List.of(1, 10), 15);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private OptionGroupFileParser(){}

    public static OptionGroup deserializeOptionGroup(String optionGroupFilePath){
        if (optionGroupFilePath == null){
            return DEFAULT_OPTION_GROUP;
        }
        File optionGroupFile = new File(optionGroupFilePath);
        OptionGroup deserializedOptionGroup;
        try{
            deserializedOptionGroup = OBJECT_MAPPER.readValue(optionGroupFile, new TypeReference<>() {});
            return deserializedOptionGroup;
        } catch (IOException e) {
            return DEFAULT_OPTION_GROUP;
        }
    }

    public static String[] getAllOptionGroupNames(){
        File optionGroupFolder = new File(OPTION_GROUP_FOLDER_PATH);
        String[] optionGroupNames = optionGroupFolder.list();
        return Objects.requireNonNullElseGet(optionGroupNames, () -> new String[]{});
    }
}
