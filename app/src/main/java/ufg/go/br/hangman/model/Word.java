package ufg.go.br.hangman.model;

public class Word {
    private String category;
    private String portuguese;
    private String english;

    public Word(String category, String portuguese, String english) {
        this.category = category;
        this.portuguese = portuguese;
        this.english = english;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPortuguese() {
        return portuguese;
    }

    public void setPortuguese(String portuguese) {
        this.portuguese = portuguese;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }
}
