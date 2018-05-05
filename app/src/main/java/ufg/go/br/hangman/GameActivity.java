package ufg.go.br.hangman;

import android.content.Intent;
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
import java.util.Random;
import ufg.go.br.hangman.Util.SoundGame;
import ufg.go.br.hangman.Util.WordManager;

import static android.content.ContentValues.TAG;

public class GameActivity extends AppCompatActivity {
    FirebaseDatabase database;
    int mistakes = 0;
    int timeLimit;
    String category;

    CountDownTimer countDownTimer;
    List<String> words;
    char[] guess;
    WordManager wordManager;
    String wordToBeGuessed;
    String normalizedWord;

    ImageView mHangImage;
    TextView mWord;
    TextView mCategoryLabel;
    Button mNewGameButton;
    LinearLayout mLettersContainer;
    ImageButton mMusicOnButton;
    ImageButton mMusicOffButton;
    SoundGame sg;
    TextView mGameCountdown;

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
        database.getReference("portuguese").child("words").child(category).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                words = new ArrayList<>();
                for (DataSnapshot adSnapshot: dataSnapshot.getChildren()) {
                    words.add(adSnapshot.getValue(String.class).toUpperCase());
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
        if (words.size() == 0) {
            return;
        }

        Random random = new Random();
        int index = random.nextInt((words.size() - 1) * 1000);
        wordToBeGuessed = words.get(index / 1000);

        normalizedWord = wordManager.getNormalizedWord(wordToBeGuessed);
        guess = wordManager.getWordMasked(wordToBeGuessed);
        mWord.setText(String.valueOf(guess));
        mNewGameButton.setVisibility(View.GONE);

        sg = new SoundGame(GameActivity.this);
        mMusicOnButton.setVisibility(View.VISIBLE);
        mMusicOffButton.setVisibility(View.GONE);
        sg.playMusicBehind();
        setHangDraw();

        countDownTimer = new CountDownTimer(timeLimit * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                mGameCountdown.setText("" + seconds);

                if (seconds <= 5) {
                    mGameCountdown.setTextColor(getResources().getColor(R.color.colorError));
                }
            }

            public void onFinish() {
                mistakes = wordManager.LIMIT_MISTAKES;
                mGameCountdown.setVisibility(View.GONE);
                setEndGameLayout();
            }
        }.start();
    }

    private void setEndGameLayout() {
        if (wordToBeGuessed.equals(String.valueOf(guess))
                || mistakes >= wordManager.LIMIT_MISTAKES) {
            mNewGameButton.setVisibility(View.VISIBLE);
            mLettersContainer.setVisibility(View.GONE);
            mGameCountdown.setVisibility(View.GONE);
            countDownTimer.cancel();
            sg.stopMusicBehind();
            setHangDraw();
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

    private void setStartValues() {
        database = FirebaseDatabase.getInstance();
        wordManager = new WordManager();

        mHangImage = findViewById(R.id.mHangImage);
        mWord = findViewById(R.id.mWord);
        mCategoryLabel = findViewById(R.id.mCategoryLabel);
        mNewGameButton = findViewById(R.id.mNewGameButton);
        mLettersContainer = findViewById(R.id.mLettersContainer);
        mMusicOnButton = findViewById(R.id.mMusicOnButton);
        mMusicOffButton = findViewById(R.id.mMusicOffButton);
        mGameCountdown = findViewById(R.id.mGameCountdown);

        category = getIntent().getStringExtra(getString(R.string.category));
        timeLimit = getIntent().getIntExtra(getString(R.string.total_time), 0);
        mCategoryLabel.setText(category);
    }
}
