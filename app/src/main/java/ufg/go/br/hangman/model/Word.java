package ufg.go.br.hangman.model;

import java.util.HashMap;

public class Word {
    private String category;
    private HashMap<String, String> word;

    public Word(String category, HashMap<String, String> word) {
        this.category = category;
        this.word = word;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public HashMap<String, String> getWord() {
        return word;
    }

    public void setWord(HashMap<String, String> word) {
        this.word = word;
    }
}
