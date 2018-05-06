package ufg.go.br.hangman;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import ufg.go.br.hangman.adapter.GameHistoryAdapter;
import ufg.go.br.hangman.db.GameHistoryDbHelper;
import ufg.go.br.hangman.model.GameHistory;

/**
 * Created by claud on 06/05/2018.
 */

public class GameHistoryActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_history);

        List<GameHistory> gameHistoryList = new GameHistoryDbHelper(this).getAllGameHistory();
        ListView gameHistoryListView = (ListView)findViewById(R.id.game_history);

        GameHistoryAdapter adapter = new GameHistoryAdapter(gameHistoryList, this);
        gameHistoryListView.setAdapter(adapter);
    }
}
