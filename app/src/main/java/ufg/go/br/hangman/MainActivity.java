package ufg.go.br.hangman;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
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
    ImageView mHangImage;
    TextView mWord;
    TextView mCategoryLabel;
    Button mNewGameButton;
    LinearLayout mLettersContainer;
    ImageButton mMusicOnButton;
    ImageButton mMusicOffButton;
    SoundGame sg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHangImage = findViewById(R.id.mHangImage);
        mWord = findViewById(R.id.mWord);
        mCategoryLabel = findViewById(R.id.mCategoryLabel);
        mNewGameButton = findViewById(R.id.mNewGameButton);
        mLettersContainer = findViewById(R.id.mLettersContainer);
        mMusicOnButton = findViewById(R.id.mMusicOnButton);
        mMusicOffButton = findViewById(R.id.mMusicOffButton);

        category = getIntent().getStringExtra("category");
        if (category == null || category.equals("")) {
            category = String.valueOf(getText(R.string.random));
        }

        newGame();
    }

    public void letterPressed(View v) {
        //Som do botão
        if (mMusicOnButton.getVisibility() == View.VISIBLE) {
            sg.playMusicButton();
        }

        final int id = v.getId();
        Button letterButton = findViewById(id);
        letterButton.setEnabled(false);
        String letter = letterButton.getText().toString();

        if (normalizedWord.contains(letter)) {
            replaceCorrectLetter(letter.toCharArray()[0]);
            if (wordToBeGuessed.equals(String.valueOf(guess))) {
                mNewGameButton.setVisibility(View.VISIBLE);
            }
        } else {
            mistakes++;
            setHangDraw();
        }

        if (mistakes >= LIMIT_OF_MISTAKES) {
            mNewGameButton.setVisibility(View.VISIBLE);
            mLettersContainer.setVisibility(View.GONE);
        }

        letterButton.setBackgroundColor(Color.TRANSPARENT);
    }

    public void setMusicOff(View v) {
        sg.stopMusicBehind();
        mMusicOffButton.setVisibility(View.VISIBLE);
        mMusicOnButton.setVisibility(View.GONE);
    }

    public void setMusicOn(View v) {
        sg.playMusicBehind();
        mMusicOffButton.setVisibility(View.GONE);
        mMusicOnButton.setVisibility(View.VISIBLE);
    }

    public void startNewGame(View v) {
        sg.stopMusicBehind();
        recreate();
    }

    private void newGame() {
        guess = getWordMasked();
        mWord.setText(String.valueOf(guess));
        mCategoryLabel.setText(category);
        mNewGameButton.setVisibility(View.GONE);
        sg = new SoundGame(MainActivity.this);

        //iniciar musica de fundo
        mMusicOnButton.setVisibility(View.VISIBLE);
        mMusicOffButton.setVisibility(View.GONE);
        sg.playMusicBehind();
        setHangDraw();
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

    private void setHangDraw() {
        int fileName;
        switch (mistakes) {
            case 1:
                fileName = R.drawable.first;
                break;
            case 2:
                fileName = R.drawable.second;
                break;
            case 3:
                fileName = R.drawable.third;
                break;
            case 4:
                fileName = R.drawable.fourth;
                break;
            case 5:
                fileName = R.drawable.fifth;
                break;
            case 6:
                fileName = R.drawable.sixth;
                break;
            case 7:
                fileName = R.drawable.dead;
                break;
            default:
                fileName = R.drawable.zero;
        }

        Drawable image = getDrawable(fileName);
        mHangImage.setImageDrawable(image);
    }
}
