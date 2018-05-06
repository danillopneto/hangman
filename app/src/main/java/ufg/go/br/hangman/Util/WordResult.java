package ufg.go.br.hangman.Util;

/**
 * Created by claud on 06/05/2018.
 */

public class WordResult {
    private boolean success;
    private char[] newWord;

    public WordResult(boolean success, char[] newWord) {
        this.success = success;
        this.newWord = newWord;
    }

    public boolean isSuccess() {
        return success;
    }

    public char[] getNewWord() {
        return newWord;
    }
}
