package ufg.go.br.hangman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

import ufg.go.br.hangman.Util.AttemptResult;
import ufg.go.br.hangman.Util.SoundGame;
import ufg.go.br.hangman.Util.VibrationGame;
import ufg.go.br.hangman.Util.WordManager;
import ufg.go.br.hangman.model.DictionaryItem;
import static android.content.ContentValues.TAG;
import ufg.go.br.hangman.Util.GameManager;
import ufg.go.br.hangman.db.GameHistoryDbHelper;
import ufg.go.br.hangman.model.GameHistory;

public class GameActivity extends AppCompatActivity {
    private int timeLimit;
    private String category;
    FirebaseDatabase database;
    CountDownTimer countDownTimer;
    List<DictionaryItem> dictionary;
    char[] guess;

    ImageView mHangImage;
    TextView mWord;
    TextView mMeaning;
    TextView mCategoryLabel;
    Button mNewGameButton;
    LinearLayout mLettersContainer;
    SoundGame sg;
    VibrationGame vg;
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

    public void setPageSettings(View v) {
        Intent myIntent = new Intent(GameActivity.this, SettingsActivity.class);
        GameActivity.this.startActivity(myIntent);
    }
	
    public void letterPressed(View v) {

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("settings", android.content.Context.MODE_PRIVATE);
        boolean audio = preferences.getBoolean("switchsound", false);
        boolean vibration = preferences.getBoolean("switchvibrate", false);
        if(audio==true) {
            sg.playMusicButton();
        }



        if(vibration==true) {
            vg.startVibrateBehind();
        }


        final int id = v.getId();
        Button letterButton = findViewById(id);
        removeButton(letterButton);
        char letter = getNextLetter(letterButton);

        AttemptResult attemptResult = mGameManager.tryNewLetter(letter, guess);

        if (attemptResult.isSuccess()) {
            guess = attemptResult.getNewWord();
            mWord.setText(String.valueOf(attemptResult.getNewWord()));
        } else {
            setHangDraw();
        }

        setEndGameLayout(0);
    }

    public void startNewGame(View v) {
        sg.stopMusicBehind();
        recreate();
    }

    private void newGame() {


        database.getReference("portuguese").child("words").child(category).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dictionary = new ArrayList<>();
                for (DataSnapshot adSnapshot: dataSnapshot.getChildren()) {
                    dictionary.add(adSnapshot.getValue(DictionaryItem.class));
                }

                setDataForNewWord();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "" + databaseError);
            }
        });
    }

    private void setDataForNewWord() {
        if (dictionary.size() == 0) {
            return;
        }

        mGameManager = new GameManager(dictionary);
        guess = mGameManager.getNewWordMasked();

        mWord.setText(String.valueOf(guess));
        mNewGameButton.setVisibility(View.GONE);

        sg = new SoundGame(GameActivity.this);
        vg = new VibrationGame(GameActivity.this);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("settings", android.content.Context.MODE_PRIVATE);
        boolean value = preferences.getBoolean("switchsound", false);
        if(value==true) {
            sg.playMusicBehind();
        }

        setHangDraw();

        countDownTimer = new CountDownTimer(timeLimit * 1000, 1000) {
            long seconds = 0;
            public void onTick(long millisUntilFinished) {
                seconds = millisUntilFinished / 1000;
                mGameCountdown.setText("" + seconds);
                mGameManager.setCurrentTime(seconds);
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
            updateScreenOfGameOver();
            updateGameHistory(seconds);
        }
    }

    private void updateScreenOfGameOver(){
        mNewGameButton.setVisibility(View.VISIBLE);
        mLettersContainer.setVisibility(View.GONE);
        mGameCountdown.setVisibility(View.GONE);
        countDownTimer.cancel();
        sg.stopMusicBehind();
        setHangDraw();
        mWord.setText(mGameManager.getWordToBeGuessed());
        mMeaning.setText(getString(R.string.meaning) + " " + mGameManager.getMeaning());
        mMeaning.setVisibility(View.VISIBLE);
    }

    private void updateGameHistory(long seconds){
        if(mGameManager.isVictory(guess)){

            GameHistory gameHistory = new GameHistory();
            gameHistory.setWord(mGameManager.getWordToBeGuessed());
            gameHistory.setTime(mGameManager.getCurrentTime());
            gameHistory.setLevel("Fácil");

            GameHistoryDbHelper gameHistoryDbHelper = new GameHistoryDbHelper(getBaseContext());
            gameHistoryDbHelper.createGameHistory(gameHistory);
            gameHistoryDbHelper.close();
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
        database = FirebaseDatabase.getInstance();

        mHangImage = findViewById(R.id.mHangImage);
        mWord = findViewById(R.id.mWord);
        mMeaning = findViewById(R.id.mMeaning);
        mCategoryLabel = findViewById(R.id.mCategoryLabel);
        mNewGameButton = findViewById(R.id.mNewGameButton);
        mLettersContainer = findViewById(R.id.mLettersContainer);
        mGameCountdown = findViewById(R.id.mGameCountdown);
        category = getIntent().getStringExtra(getString(R.string.category));
        timeLimit = getIntent().getIntExtra(getString(R.string.total_time), 0);
        mCategoryLabel.setText(category);

    }

    private void removeButton(Button letterButton){
        letterButton.setEnabled(false);
        letterButton.setBackgroundColor(Color.TRANSPARENT);
    }

    private char getNextLetter(Button letterButton){
        return letterButton.getText().toString().toCharArray()[0];
    }
}
