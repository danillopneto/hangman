package ufg.go.br.hangman.Util;

import java.util.List;
import java.util.Random;

import ufg.go.br.hangman.model.DictionaryItem;

/**
 * Created by claud on 03/05/2018.
 */

public class GameManager {

    public static final int LIMIT_MISTAKES = 7;
    private String wordToBeGuessed;
    private WordManager wordManager;
    private int numberOfMistakes = 0;
    private List<DictionaryItem> dictionary;
    private DictionaryItem dataWordToBeGuessed;
    private int currentTime;

    public GameManager(List<DictionaryItem> dictionary){

        wordManager = new WordManager();
        this.dictionary = dictionary;
    }

    public boolean isDefeat(){
        return numberOfMistakes >= LIMIT_MISTAKES;
    }

    public boolean isVictory(char[] guess){
        return wordToBeGuessed.equals(String.valueOf(guess));
    }

    public void finish(){
        numberOfMistakes = LIMIT_MISTAKES;
    }

    public char[] getNewWordMasked(){
        Random random = new Random();
        int index = random.nextInt((dictionary.size() - 1) * 1000);
        dataWordToBeGuessed = dictionary.get(index / 1000);
        wordToBeGuessed = dataWordToBeGuessed.getWord().toUpperCase();

        return wordManager.getWordMasked(wordToBeGuessed);
    }

    public AttemptResult tryNewLetter(char letter, char[] guess){
        AttemptResult wordResult = wordManager.tryNewLetter(wordToBeGuessed, guess, letter);
        if(!wordResult.isSuccess()){
            numberOfMistakes++;
        }

        return wordResult;
    }

    public int getNumberOfMistakes(){
        return numberOfMistakes;
    }

    public String getWordToBeGuessed(){
        return wordToBeGuessed;
    }

    public void setCurrentTime(long currentTime){
        this.currentTime = (int)currentTime;
    }

    public int getCurrentTime(){
        return this.currentTime;
    }

    public String getMeaning(){
        return dataWordToBeGuessed == null ? "" : dataWordToBeGuessed.getMeaning();
    }
}
