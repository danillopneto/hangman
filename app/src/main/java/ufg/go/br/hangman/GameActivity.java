package ufg.go.br.hangman;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ufg.go.br.hangman.Util.GameManager;
import ufg.go.br.hangman.Util.SoundGame;
import ufg.go.br.hangman.Util.WordManager;
import ufg.go.br.hangman.db.GameHistoryDbHelper;
import ufg.go.br.hangman.model.GameHistory;
import ufg.go.br.hangman.model.Word;
import ufg.go.br.hangman.Util.WordResult;

public class GameActivity extends AppCompatActivity {
    private int timeLimit;
    private String language;
    private String category;

    private CountDownTimer countDownTimer;
    private char[] guess;
    private WordManager wordManager;
    private Word dataWordToBeGuessed;
    private String wordToBeGuessed;
    private String normalizedWord;

    ImageView mHangImage;
    TextView mWord;
    TextView mCategoryLabel;
    Button mNewGameButton;
    LinearLayout mLettersContainer;
    ImageButton mMusicOnButton;
    ImageButton mMusicOffButton;
    SoundGame sg;
    TextView mGameCountdown;
    GameManager mGameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setStartValues();
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

        WordResult wordResult = mGameManager.tryNewLetter(letter, guess);

        if (wordResult.isSuccess()) {
            mWord.setText(String.valueOf(wordResult.getNewWord()));
        } else {
            setHangDraw();
        }

        setEndGameLayout(0);
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
        guess = mGameManager.getNewWordMasked(category, language);

        mWord.setText(String.valueOf(guess));
        mNewGameButton.setVisibility(View.GONE);

        sg = new SoundGame(GameActivity.this);
        mMusicOnButton.setVisibility(View.VISIBLE);
        mMusicOffButton.setVisibility(View.GONE);
        sg.playMusicBehind();
        setHangDraw();

        countDownTimer = new CountDownTimer(timeLimit * 1000, 1000) {
            long seconds = 0;
            public void onTick(long millisUntilFinished) {
                seconds = millisUntilFinished / 1000;
                mGameCountdown.setText("" + seconds);

                if (seconds <= 5) {
                    mGameCountdown.setTextColor(getResources().getColor(R.color.colorError));
                }
            }

            public void onFinish() {
                mGameManager.finish();
                mGameCountdown.setVisibility(View.GONE);
                setEndGameLayout(seconds);
            }
        }.start();
    }

    private void setEndGameLayout(long seconds) {
        if (mGameManager.isDefeat() || mGameManager.isVictory(guess)) {
            mNewGameButton.setVisibility(View.VISIBLE);
            mLettersContainer.setVisibility(View.GONE);
            mGameCountdown.setVisibility(View.GONE);
            countDownTimer.cancel();
            sg.stopMusicBehind();
            setHangDraw();

            if(mGameManager.isVictory(guess)){

                GameHistory gameHistory = new GameHistory();
                gameHistory.setWord(guess.toString());
                gameHistory.setTime((int)seconds);
                gameHistory.setLevel("facil");

                GameHistoryDbHelper gameHistoryDbHelper = new GameHistoryDbHelper(getBaseContext());
                gameHistoryDbHelper.createGameHistory(gameHistory);
            }
        }
    }

    private void setHangDraw() {
        int fileName;
        switch (mGameManager.getNumberOfMistakes()) {
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

    private void setStartValues() {
        wordManager = new WordManager();
        mGameManager = new GameManager();
        mHangImage = findViewById(R.id.mHangImage);
        mWord = findViewById(R.id.mWord);
        mCategoryLabel = findViewById(R.id.mCategoryLabel);
        mNewGameButton = findViewById(R.id.mNewGameButton);
        mLettersContainer = findViewById(R.id.mLettersContainer);
        mMusicOnButton = findViewById(R.id.mMusicOnButton);
        mMusicOffButton = findViewById(R.id.mMusicOffButton);
        mGameCountdown = findViewById(R.id.mGameCountdown);

        language = getIntent().getStringExtra(getString(R.string.language));
        category = getIntent().getStringExtra(getString(R.string.category));
        timeLimit = getIntent().getIntExtra(getString(R.string.total_time), 0);
        mCategoryLabel.setText(category);
    }
}
