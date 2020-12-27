package com.obiwanwheeler.utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Thesaurus {

    final static int SYNONYM_COUNT = 10;

    public static void main(String[] args) throws IOException
    {
        Scanner sc = new Scanner(System.in);

        System.out.println("Write a word...");
        String wordToSearch = sc.next();

        getSynonyms(wordToSearch);
    }

    public static List<String> getSynonyms(String wordToSearch) {
        HttpURLConnection connection;
        try
        {
            connection = setupConnection(wordToSearch);
        } catch (IOException e)
        {
            return Collections.emptyList();
        }

        StringBuilder response = orderResponse(connection);

        ObjectMapper mapper = new ObjectMapper();

        List<Word> words = getWordList(wordToSearch, response, mapper);
        List<String> output = new ArrayList<>();
        for (Word word : words)
        {
            output.add(word.getWord());
        }
        return output;

    }

    private static HttpURLConnection setupConnection(String wordToSearch) throws IOException
    {
        String url = "https://api.datamuse.com/words?rel_syn=" + wordToSearch;

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");
        String USER_AGENT = "Mozilla/5.0";
        connection.setRequestProperty("User-Agent", USER_AGENT);

        return connection;
    }

    private static StringBuilder orderResponse(HttpURLConnection con)
    {
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        catch (IOException ignore){ }
        return response;
    }

    private static List<Word> getWordList(String wordToSearch, StringBuilder response, ObjectMapper mapper)
    {
        List<Word> words;
        try
        {
            words = mapper.readValue(
                    response.toString(),
                    mapper.getTypeFactory().constructCollectionType(ArrayList.class, Word.class)
            );
        } catch (JsonProcessingException e)
        {
            return Collections.emptyList();
        }

        System.out.println("Synonyms of '" + wordToSearch + "':");
        if(words.size() > 0) {
            for(int i = 0; i < SYNONYM_COUNT && i < words.size(); i++) {
                System.out.println(words.get(i).getWord());
            }
            if (words.size() < SYNONYM_COUNT)
                return words;
            else
                return words.subList(0, SYNONYM_COUNT);
        }
        else {
            return Collections.emptyList();
        }

    }

    // word and score attributes are from DataMuse API
    static class Word {
        private String word;
        private int score;

        public String getWord() {return this.word;}
        public int getScore() {return this.score;}
    }
}
