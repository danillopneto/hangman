package ufg.go.br.hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import ufg.go.br.hangman.Util.SoundGame;
import ufg.go.br.hangman.model.Word;
import ufg.go.br.hangman.services.WordsService;

public class MainActivity extends AppCompatActivity {
    private final int LIMIT_OF_MISTAKES = 7;
    private int mistakes = 0;
    private char[] guess;
    private Word dataWordToBeGuessed;
    private String wordToBeGuessed;
    private String normalizedWord;
    private String category;
    TextView mWord;
    TextView mCategoryLabel;
    Button mNewGameButton;
    SoundGame sg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWord = findViewById(R.id.mWord);
        mCategoryLabel = findViewById(R.id.mCategoryLabel);
        mNewGameButton = findViewById(R.id.mNewGameButton);
        category = getIntent().getStringExtra("category");
        if (category == null || category.equals("")) {
            category = String.valueOf(getText(R.string.random));
        }

        newGame();
    }

    public void letterPressed(View v) {
        //Som do botão
        sg.playMusicButton();

        final int id = v.getId();
        Button letterButton = findViewById(id);
        letterButton.setEnabled(false);
        String letter = letterButton.getText().toString();

        if (normalizedWord.contains(letter)) {
            replaceCorrectLetter(letter.toCharArray()[0]);
            if (wordToBeGuessed.equals(String.valueOf(guess))) {
                sg.stopMusichBehind();
                mNewGameButton.setVisibility(View.VISIBLE);
            }
        } else {
            mistakes++;
        }

        if (mistakes >= LIMIT_OF_MISTAKES) {
            sg.stopMusichBehind();
            mNewGameButton.setVisibility(View.VISIBLE);
        }
    }

    public void startNewGame(View v) {
        recreate();
    }

    private void newGame() {
        guess = getWordMasked();
        mWord.setText(String.valueOf(guess));
        mCategoryLabel.setText(category);
        mNewGameButton.setVisibility(View.GONE);
        sg = new SoundGame(MainActivity.this);

        //iniciar musica de fundo
        sg.playMusicBehind();
    }

    private char[] getWordMasked() {
        WordsService service = new WordsService();
        dataWordToBeGuessed = service.getNewWord("");
        wordToBeGuessed = dataWordToBeGuessed.getPortuguese();
        normalizedWord = getNormalizedWord(wordToBeGuessed);
        char[] word = new char[wordToBeGuessed.length()];
        for (int i = 0; i < wordToBeGuessed.length(); i++) {
            word[i] = '―';
        }

        return word;
    }

    private String getNormalizedWord(String word) {
        return Normalizer
                .normalize(word, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }

    private void replaceCorrectLetter(char letter) {
        List<Integer> lettersFound = new ArrayList<>();
        for (int i = 0; i < normalizedWord.length(); i++) {
            if (normalizedWord.toCharArray()[i] == letter) {
                lettersFound.add(i);
            }
        }

        for (Integer index : lettersFound) {
            guess[index] = wordToBeGuessed.toCharArray()[index];
        }

        mWord.setText(String.valueOf(guess));
    }
}
