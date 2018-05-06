package ufg.go.br.hangman.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ufg.go.br.hangman.R;
import ufg.go.br.hangman.model.GameHistory;

/**
 * Created by claud on 06/05/2018.
 */

public class GameHistoryAdapter extends BaseAdapter {

    private List<GameHistory> gameHistoryList;
    private final Activity activity;

    public GameHistoryAdapter(List<GameHistory> gameHistoryList, Activity activity){
        this.gameHistoryList = gameHistoryList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return this.gameHistoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.gameHistoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.activity_game_history, parent, false);
        GameHistory gameHistory = gameHistoryList.get(position);

        TextView wordField = (TextView)view.findViewById(R.id.game_history_word);
        TextView timeField = (TextView)view.findViewById(R.id.game_history_time);
        TextView levelField = (TextView)view.findViewById(R.id.game_history_level);

        wordField.setText(gameHistory.getWord());
        timeField.setText(gameHistory.getTime());
        levelField.setText(gameHistory.getLevel());

        return view;
    }
}
