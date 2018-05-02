package ufg.go.br.hangman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import java.util.List;
import ufg.go.br.hangman.model.GameLevel;
import ufg.go.br.hangman.services.WordsService;

public class MainActivity extends AppCompatActivity {
    TextView mCategoryLabel;
    TextView mLevelLabel;
    WordsService wordsService;
    List<GameLevel> levels;
    List<String> categories;
    int levelSelected;
    int categorySelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setStartValues();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mSettingButton) {
            showSettings();
        }

        return super.onOptionsItemSelected(item);
    }

    public void nextCategory(View v) {
        if (categorySelected == categories.size() - 1) {
            categorySelected = 0;
        } else {
            categorySelected++;
        }

        mCategoryLabel.setText(categories.get(categorySelected));
    }

    public void nextLevel(View v) {
        if (levelSelected == levels.size() - 1) {
            levelSelected = 0;
        } else {
            levelSelected++;
        }

        mLevelLabel.setText(levels.get(levelSelected).getName());
    }

    public void previousCategory(View v) {
        if (categorySelected == 0) {
            categorySelected = categories.size() - 1;
        } else {
            categorySelected--;
        }

        mCategoryLabel.setText(categories.get(levelSelected));
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
        i.putExtra(getString(R.string.category), categories.get(categorySelected));
        i.putExtra(getString(R.string.total_time), levels.get(levelSelected).getTime());
        startActivity(i);
    }

    public void showSettings() {
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }

    private void setStartValues() {
        mCategoryLabel = findViewById(R.id.mCategoryLabel);
        mLevelLabel = findViewById(R.id.mLevelLabel);
        wordsService = new WordsService();
        levels = wordsService.getLevels();
        categories = wordsService.getCategories();
        if (levels.size() > 0) {
            categorySelected = 0;
            mCategoryLabel.setText(categories.get(categorySelected));
            levelSelected = 0;
            mLevelLabel.setText(levels.get(levelSelected).getName());
        }
    }
}
