package ufg.go.br.hangman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.List;
import ufg.go.br.hangman.model.GameLevel;
import ufg.go.br.hangman.services.WordsService;

public class MainActivity extends AppCompatActivity {
    TextView mLevelLabel;
    WordsService wordsService;
    List<GameLevel> levels;
    int levelSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLevelLabel = findViewById(R.id.mLevelLabel);
        wordsService = new WordsService();
        levels = wordsService.getLevels();
        if (levels.size() > 0) {
            levelSelected = 0;
            mLevelLabel.setText(levels.get(levelSelected).getName());
        }
    }

    public void nextLevel(View v) {
        if (levelSelected == levels.size() - 1) {
            levelSelected = 0;
        }
        else {
            levelSelected++;
        }

        mLevelLabel.setText(levels.get(levelSelected).getName());
    }

    public void previousLevel(View v) {
        if (levelSelected == 0) {
            levelSelected = levels.size() - 1;
        } else {
            levelSelected--;
        }

        mLevelLabel.setText(levels.get(levelSelected).getName());
    }

    public void startGame(View v) {
        Intent i = new Intent(this, GameActivity.class);
        i.putExtra(getString(R.string.total_time), levels.get(levelSelected).getTime());
        startActivity(i);
    }

    public void showSettings(View v) {
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }
}
