package ufg.go.br.hangman.db;

/**
 * Created by claud on 03/05/2018.
 */

public class GameHistoryContract {
    private GameHistoryContract() {}

    public static class GameHistoryEntry   {
        public static final String TABLE_NAME = "hm_game_history";
        public static final String COLUMN_NAME_WORD = "word";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_LEVEL = "level";
        public static final String COLUMN_NAME_DATE= "date";
    }
}
