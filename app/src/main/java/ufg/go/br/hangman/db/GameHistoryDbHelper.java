package ufg.go.br.hangman.db;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ufg.go.br.hangman.model.GameHistory;

import static ufg.go.br.hangman.db.GameHistoryContract.GameHistoryEntry;
/**
 * Created by claud on 03/05/2018.
 */

public class GameHistoryDbHelper extends SQLiteOpenHelper {
    private static final String TEXT_TYPE = "TEXT";
    private static final String INTEGER_TYPE = "INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_GAME_HISTORY = "CREATE TABLE " + GameHistoryEntry.TABLE_NAME
            + "(" + GameHistoryEntry.COLUMN_NAME_WORD + " " + TEXT_TYPE + COMMA_SEP
            + GameHistoryEntry.COLUMN_NAME_LEVEL + " " + TEXT_TYPE + COMMA_SEP
            + GameHistoryEntry.COLUMN_NAME_DATE + " " + TEXT_TYPE + COMMA_SEP
            + GameHistoryEntry.COLUMN_NAME_TIME + " " +INTEGER_TYPE + ")";

    private static final String SQL_DELETE_POSTS =
            "DROP TABLE IF EXISTS " + GameHistoryEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DBGameHistory.db";

    public GameHistoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_GAME_HISTORY);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_POSTS);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void deleteAllGameHistory(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + GameHistoryEntry.TABLE_NAME);
        db.close();
    }

    public long createGameHistory(GameHistory gameHistory){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(GameHistoryEntry.COLUMN_NAME_WORD, gameHistory.getWord());
        values.put(GameHistoryEntry.COLUMN_NAME_LEVEL, gameHistory.getLevel());
        values.put(GameHistoryEntry.COLUMN_NAME_TIME, gameHistory.getTime());
        values.put(GameHistoryEntry.COLUMN_NAME_DATE, getDateTime());

        long gameHistoryId = db.insertOrThrow(GameHistoryEntry.TABLE_NAME, null, values);

        return gameHistoryId;
    }

    public List<GameHistory> getAllGameHistory(){
        List<GameHistory> allHistory = new ArrayList<GameHistory>();
        String selectQuery = "SELECT * FROM " + GameHistoryEntry.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                GameHistory gameHistory = new GameHistory();
                gameHistory.setLevel(cursor.getString(cursor.getColumnIndex(GameHistoryEntry.COLUMN_NAME_LEVEL)));
                gameHistory.setDate(cursor.getString(cursor.getColumnIndex(GameHistoryEntry.COLUMN_NAME_DATE)));
                gameHistory.setTime(cursor.getInt(cursor.getColumnIndex(GameHistoryEntry.COLUMN_NAME_TIME)));
                gameHistory.setWord(cursor.getString(cursor.getColumnIndex(GameHistoryEntry.COLUMN_NAME_WORD)));

                allHistory.add(gameHistory);
            }while(cursor.moveToNext());
        }

        cursor.close();

        return allHistory;
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    private Date getDate(String dateTime) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.parse(dateTime);
    }
}

