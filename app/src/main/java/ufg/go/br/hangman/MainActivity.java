package ufg.go.br.hangman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import ufg.go.br.hangman.model.GameLevel;
import ufg.go.br.hangman.services.WordsService;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database;
    TextView mCategoryLabel;
    TextView mLevelLabel;
    WordsService wordsService;
    List<GameLevel> levels;
    List<String> categories;
    int selectedCategory;
    int selectedLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);

        setStartValues();
    }


    @Override
    public void onBackPressed() {

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("welcome", android.content.Context.MODE_PRIVATE);
        boolean screen = preferences.getBoolean("screen", false);
        if(screen==true){
            this.finishAffinity();
        }
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

        mCategoryLabel.setText(categories.get(selectedCategory).toUpperCase());
    }

    public void nextLevel(View v) {
        if (selectedLevel == levels.size() - 1) {
            selectedLevel = 0;
        } else {
            selectedLevel++;
        }

        mLevelLabel.setText(levels.get(selectedLevel).getName().toUpperCase());
    }

    public void previousCategory(View v) {
        if (selectedCategory == 0) {
            selectedCategory = categories.size() - 1;
        } else {
            selectedCategory--;
        }

        mCategoryLabel.setText(categories.get(selectedCategory).toUpperCase());
    }

    public void previousLevel(View v) {
        if (selectedLevel == 0) {
            selectedLevel = levels.size() - 1;
        } else {
            selectedLevel--;
        }

        mLevelLabel.setText(levels.get(selectedLevel).getName().toUpperCase());
    }

    public void startGame(View v) {
        Intent i = new Intent(this, GameActivity.class);
        i.putExtra(getString(R.string.category), categories.get(selectedCategory));
        i.putExtra(getString(R.string.total_time),levels.get(selectedLevel).getTime());
        i.putExtra("level", levels.get(selectedLevel).getName());
        startActivity(i);
        //
    }

    public void openGameHistory(View v){
        Intent i =new Intent(this, GameHistoryActivity.class);
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

        database.getReference("portuguese").child("categories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                categories = new ArrayList<>();
                for (DataSnapshot adSnapshot: dataSnapshot.getChildren()) {
                    categories.add(adSnapshot.getValue(String.class));
                }

                selectedCategory = 0;
                mCategoryLabel.setText(categories.get(selectedCategory));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "" + databaseError);
            }
        });

        database.getReference("portuguese").child("levels").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                levels = new ArrayList<>();
                for (DataSnapshot adSnapshot: dataSnapshot.getChildren()) {
                    levels.add(adSnapshot.getValue(GameLevel.class));
                }

                selectedLevel = 0;
                mLevelLabel.setText(levels.get(selectedLevel).getName().toUpperCase());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "" + databaseError);
            }
        });
    }
}
