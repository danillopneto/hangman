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

import java.util.List;

import ufg.go.br.hangman.Util.SoundGame;
import ufg.go.br.hangman.Util.WordManager;
import ufg.go.br.hangman.model.Word;

public class GameActivity extends AppCompatActivity {
    private int mistakes = 0;
    private char[] guess;
    private WordManager wordManager;
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
        setContentView(R.layout.activity_game);
        wordManager = new WordManager();
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

        mCategoryLabel.setText(category);
        newGame();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sg.stopMusicBehind();
    }

    public void letterPressed(View v) {
        if (mMusicOnButton.getVisibility() == View.VISIBLE) {
            sg.playMusicButton();
        }

        final int id = v.getId();
        Button letterButton = findViewById(id);
        letterButton.setEnabled(false);
        letterButton.setBackgroundColor(Color.TRANSPARENT);

        char letter = letterButton.getText().toString().toCharArray()[0];
        List<Integer> positions = wordManager.getLetterPositions(normalizedWord, letter);
        if (positions.size() > 0) {
            guess = wordManager.replaceLetter(wordToBeGuessed, guess, positions);
            mWord.setText(String.valueOf(guess));
        } else {
            mistakes++;
            setHangDraw();
        }

        setEndGameLayout();
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
        dataWordToBeGuessed = wordManager.getNewWord(category);
        wordToBeGuessed = dataWordToBeGuessed.getPortuguese();

        normalizedWord = wordManager.getNormalizedWord(wordToBeGuessed);
        guess = wordManager.getWordMasked(wordToBeGuessed);
        mWord.setText(String.valueOf(guess));
        mNewGameButton.setVisibility(View.GONE);

        sg = new SoundGame(GameActivity.this);
        mMusicOnButton.setVisibility(View.VISIBLE);
        mMusicOffButton.setVisibility(View.GONE);
        sg.playMusicBehind();

        setHangDraw();
    }

    private void setEndGameLayout() {
        if (wordToBeGuessed.equals(String.valueOf(guess))
                || mistakes >= wordManager.LIMIT_MISTAKES) {
            mNewGameButton.setVisibility(View.VISIBLE);
            mLettersContainer.setVisibility(View.GONE);
        }
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
