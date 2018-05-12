package ufg.go.br.hangman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import ufg.go.br.hangman.Util.SoundGame;
import ufg.go.br.hangman.Util.VibrationGame;
import ufg.go.br.hangman.adapter.GameHistoryAdapter;
import ufg.go.br.hangman.db.GameHistoryDbHelper;
import ufg.go.br.hangman.model.GameHistory;

/**
 * Created by claud on 06/05/2018.
 */

public class GameHistoryActivity extends AppCompatActivity {

    SoundGame sg;
    VibrationGame vg;
    SharedPreferences interacoes;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_history);

        List<GameHistory> gameHistoryList = new GameHistoryDbHelper(this).getAllGameHistory();
        ListView gameHistoryListView = (ListView)findViewById(R.id.game_history);

        GameHistoryAdapter adapter = new GameHistoryAdapter(gameHistoryList, this);
        gameHistoryListView.setAdapter(adapter);
    }

    public void clearRecords(View v){
        sg = new SoundGame(GameHistoryActivity.this);
        vg = new VibrationGame(GameHistoryActivity.this);
        interacoes = getApplicationContext().getSharedPreferences("settings", android.content.Context.MODE_PRIVATE);
        boolean audio = interacoes.getBoolean("switchsound", false);
        boolean vibration = interacoes.getBoolean("switchvibrate", false);

        if(audio==true) {
            sg.playMusicButton();
        }
        if(vibration==true) {
            vg.startVibrateBehind();
        }
        GameHistoryDbHelper dbHelper = new GameHistoryDbHelper(this);
        dbHelper.deleteAllGameHistory();

        recreate();
    }
}
