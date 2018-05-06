package ufg.go.br.hangman.Util;

import java.util.List;

/**
 * Created by claud on 03/05/2018.
 */

public class GameManager {

    public static final int LIMIT_MISTAKES = 7;
    private String wordToBeGuessed;
    private WordManager wordManager;
    private int numberOfMistakes = 0;

    public GameManager(){
        wordManager = new WordManager();
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

    public char[] getNewWordMasked(String category, String language){
        wordToBeGuessed = wordManager.getNewWord(category).getWord().get(language);

        return wordManager.getWordMasked(wordToBeGuessed);
    }

    public WordResult tryNewLetter(char letter, char[] guess){
        WordResult wordResult = wordManager.tryNewLetter(wordToBeGuessed, guess, letter);
        if(!wordResult.isSuccess()){
            numberOfMistakes++;
        }

        return wordResult;
    }

    public int getNumberOfMistakes(){
        return numberOfMistakes;
    }
}
