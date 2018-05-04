package ufg.go.br.hangman;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.List;
import ufg.go.br.hangman.model.GameLevel;
import ufg.go.br.hangman.services.WordsService;

public class MainActivity extends AppCompatActivity {
    TextView mLanguageLabel;
    TextView mCategoryLabel;
    TextView mLevelLabel;
    WordsService wordsService;
    List<String> languages;
    List<GameLevel> levels;
    List<String> categories;
    int selectedLanguage;
    int selectedCategory;
    int selectedLevel;



    @SuppressLint("WrongViewCast")
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
        if (selectedCategory == categories.size() - 1) {
            selectedCategory = 0;
        } else {
            selectedCategory++;
        }

        mCategoryLabel.setText(categories.get(selectedCategory));
    }

    public void nextLanguage(View v) {
        if (selectedLanguage == languages.size() - 1) {
            selectedLanguage = 0;
        } else {
            selectedLanguage++;
        }

        mLanguageLabel.setText(languages.get(selectedLanguage));
    }

    public void nextLevel(View v) {
        if (selectedLevel == levels.size() - 1) {
            selectedLevel = 0;
        } else {
            selectedLevel++;
        }

        mLevelLabel.setText(levels.get(selectedLevel).getName());
    }

    public void previousCategory(View v) {
        if (selectedCategory == 0) {
            selectedCategory = categories.size() - 1;
        } else {
            selectedCategory--;
        }

        mCategoryLabel.setText(categories.get(selectedLevel));
    }

    public void previousLanguage(View v) {
        if (selectedLanguage == 0) {
            selectedLanguage = languages.size() - 1;
        } else {
            selectedLanguage--;
        }

        mLanguageLabel.setText(languages.get(selectedLanguage));
    }

    public void previousLevel(View v) {
        if (selectedLevel == 0) {
            selectedLevel = levels.size() - 1;
        } else {
            selectedLevel--;
        }

        mLevelLabel.setText(levels.get(selectedLevel).getName());
    }

    public void startGame(View v) {
        Intent i = new Intent(this, GameActivity.class);
        i.putExtra(getString(R.string.language), languages.get(selectedLanguage));
        i.putExtra(getString(R.string.category), categories.get(selectedCategory));
        i.putExtra(getString(R.string.total_time), levels.get(selectedLevel).getTime());
        startActivity(i);
    }

    public void showSettings() {
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }

    private void setStartValues() {
        mLanguageLabel = findViewById(R.id.mLanguageLabel);
        mCategoryLabel = findViewById(R.id.mCategoryLabel);
        mLevelLabel = findViewById(R.id.mLevelLabel);
        wordsService = new WordsService();
        languages = wordsService.getLanguages();
        levels = wordsService.getLevels();
        categories = wordsService.getCategories();

        selectedLanguage = 0;
        mLanguageLabel.setText(languages.get(selectedLanguage));
        selectedCategory = 0;
        mCategoryLabel.setText(categories.get(selectedCategory));
        selectedLevel = 0;
        mLevelLabel.setText(levels.get(selectedLevel).getName());
    }
}
