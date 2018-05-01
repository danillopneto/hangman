package ufg.go.br.hangman.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import ufg.go.br.hangman.model.GameLevel;
import ufg.go.br.hangman.model.Word;

public class WordsService {
    private List<Word> _words;

    public WordsService() {
        _words = new ArrayList<>();
        _words.add(new Word("Animals", "CÃO", "DOG"));
        _words.add(new Word("Animals", "DRAGÃO", "DRAGON"));
        _words.add(new Word("Animals", "TAMANDUÁ", "ANTEATER"));
        _words.add(new Word("Animals", "CARANGUEJO", "CRAB"));
        _words.add(new Word("Animals", "TARTARUGA", "TURTLE"));
        _words.add(new Word("Animals", "FORMIGA", "ANT"));
        _words.add(new Word("Animals", "BORBOLETA", "BUTTERFLY"));
    }

    public List<GameLevel> getLevels() {
        List<GameLevel> levels = new ArrayList<>();
        levels.add(new GameLevel("Fácil", 90));
        levels.add(new GameLevel("Normal", 60));
        levels.add(new GameLevel("Difícil", 30));
        levels.add(new GameLevel("Muito difícil", 15));
        levels.add(new GameLevel("Nível supremo", 5));
        return levels;
    }

    public Word getNewWord(final String category) {
        return getRandomWord(_words);
    }

    private Word getRandomWord(List<Word> words) {
        Random random = new Random();
        int index = random.nextInt(words.size() - 1);
        return words.get(index);
    }
}
