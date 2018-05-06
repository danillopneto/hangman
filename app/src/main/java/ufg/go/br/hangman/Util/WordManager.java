package ufg.go.br.hangman.Util;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import ufg.go.br.hangman.model.Word;
import ufg.go.br.hangman.services.WordsService;

public class WordManager {
    public final int LIMIT_MISTAKES = 7;

    public Word getNewWord(String category) {
        WordsService service = new WordsService();
        return service.getNewWord(category);
    }

    public String getNormalizedWord(String word) {
        return Normalizer
                .normalize(word, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }

    public List<Integer> getLetterPositions(String word, char letter) {
        List<Integer> lettersFound = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            if (word.toCharArray()[i] == letter) {
                lettersFound.add(i);
            }
        }

        return lettersFound;
    }

    public char[] getWordMasked(String wordToBeMasked) {
        char[] word = new char[wordToBeMasked.length()];
        for (int i = 0; i < wordToBeMasked.length(); i++) {
            word[i] = '―';
        }

        return word;
    }

    public char[] replaceLetter(String originalWord, char[] guess, List<Integer> positions) {
        for (Integer index : positions) {
            guess[index] = originalWord.toCharArray()[index];
        }

        return guess;
    }

    public WordResult tryNewLetter(String wordToBeGuessed, char[] guess, char letter) {
        boolean letterFounded = false;

        String normalizedWord = getNormalizedWord(wordToBeGuessed);
        for (int index = 0; index < normalizedWord.length(); index++) {
            if (normalizedWord.toCharArray()[index] == letter) {
                guess[index] = wordToBeGuessed.toCharArray()[index];
                letterFounded = true;
            }
        }

        return new WordResult(letterFounded, guess);
    }
}
