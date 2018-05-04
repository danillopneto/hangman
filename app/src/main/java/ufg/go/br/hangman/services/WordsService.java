package ufg.go.br.hangman.services;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import ufg.go.br.hangman.model.GameLevel;
import ufg.go.br.hangman.model.Word;

public class WordsService {
    public static final String PORTUGUESE = "Português";
    private final String ENGLISH = "Inglês";
    private List<Word> _words;

    public WordsService() {
        _words = new ArrayList<>();
        _words.add(new Word("Animais",  createWord(PORTUGUESE,"CÃO", ENGLISH, "DOG")));
        _words.add(new Word("Comida",  createWord(PORTUGUESE,"SORVETE", ENGLISH, "ICECREAM")));
        _words.add(new Word("Corpo",  createWord(PORTUGUESE,"PESCOÇO", ENGLISH, "NECK")));
        _words.add(new Word("Cozinha",  createWord(PORTUGUESE,"COLHER", ENGLISH, "SPOON")));
        _words.add(new Word("Educação",  createWord(PORTUGUESE,"LIVRO", ENGLISH, "BOOK")));
        _words.add(new Word("Esportes",  createWord(PORTUGUESE,"LUVA", ENGLISH, "GLOVE")));
        _words.add(new Word("Países",  createWord(PORTUGUESE,"IRLANDA", ENGLISH, "IRELAND")));
        _words.add(new Word("Profissões",  createWord(PORTUGUESE,"PROGRAMADOR", ENGLISH, "PROGRAMMER")));
        _words.add(new Word("Roupas",  createWord(PORTUGUESE,"SAIA", ENGLISH, "SKIRT")));
        _words.add(new Word("Transporte",  createWord(PORTUGUESE,"ÔNIBUS", ENGLISH, "BUS")));
    }

    public List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        categories.add("Animals");
        categories.add("Comida");
        categories.add("Corpo");
        categories.add("Cozinha");
        categories.add("Educação");
        categories.add("Esportes");
        categories.add("Países");
        categories.add("Profissões");
        categories.add("Roupas");
        categories.add("Transporte");
        return categories;
    }

    public List<String> getLanguages() {
        List<String> languages = new ArrayList<>();
        languages.add(PORTUGUESE);
        languages.add(ENGLISH);
        return languages;
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
        List<Word> wordsToBeFilter = _words;
        CollectionUtils.filter(wordsToBeFilter, new Predicate<Word>() {
            @Override
            public boolean evaluate(Word o) {
                return o.getCategory().equals(category);
            }
        });

        return getRandomWord(wordsToBeFilter);
    }

    private Word getRandomWord(List<Word> words) {
        if (words.size() > 1) {
            Random random = new Random();
            int index = random.nextInt(words.size() - 1);
            return words.get(index);
        }

        return words.get(0);
    }

    private HashMap<String, String> createWord(String... languageOrWordInSequence) {
        HashMap<String, String> words = new HashMap<>();
        for (int i = 0; i < languageOrWordInSequence.length; i = i + 2) {
            words.put(languageOrWordInSequence[i], languageOrWordInSequence[i + 1]);
        }

        return words;
    }
}
