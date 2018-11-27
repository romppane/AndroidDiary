package com.example.romppane.diary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import static com.example.romppane.diary.DatabaseAdapter.CURRENTDATE;
import static com.example.romppane.diary.DatabaseAdapter.DESCRIPTION;
import static com.example.romppane.diary.DatabaseAdapter.DIARY;
import static com.example.romppane.diary.DatabaseAdapter.DIARYDATE;
import static com.example.romppane.diary.DatabaseAdapter.ID;
import static com.example.romppane.diary.DatabaseAdapter.TITLE;

/**
 * Created by romppane on 21.8.2017.
 */

public class DatabaseAdapter {

    private SQLiteDatabase db;
    private Context context;
    private BaseDBOpenHelper dbHelper;

    static final String DIARY = "uusi";
    static final String ID = "_id";
    static final String DIARYDATE = "diarydate";
    static final String TITLE = "title";
    static final String DESCRIPTION = "description";
    static final String CURRENTDATE = "currentdate";

    public DatabaseAdapter (Context _context) {
        context = _context;
        dbHelper = new BaseDBOpenHelper(_context);
    }

    public void close() {
        db.close();
    }

    public void open() throws SQLiteException {
        try {
            db = dbHelper.getWritableDatabase();

        } catch (SQLiteException e) {
            db = dbHelper.getReadableDatabase();
        }
    }

    public long insertDiary(String diarydate, String title, String description, String currentdate) {

        ContentValues diary = new ContentValues();
        diary.put(DIARYDATE, diarydate);
        diary.put(TITLE, title);
        diary.put(DESCRIPTION, description);
        diary.put(CURRENTDATE, currentdate);
        return db.insert(DIARY, null, diary);
        }

    public void updateDiary(long id, String title, String description, String currentdate) {

        ContentValues diary = new ContentValues();
        diary.put(TITLE, title);
        diary.put(DESCRIPTION, description);
        diary.put(CURRENTDATE, currentdate);
        db.update(DIARY, diary, "_id="+id, null);
        }

    public void deleteDiary(long id) {
        db.delete(DIARY, "_id="+id, null);
        }

    public ArrayList getDiaryByTitle (String table){
        ArrayList<String[]>rows = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+table+" ORDER BY "+TITLE+" COLLATE NOCASE ASC ", null);

        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    String [] content = new String[5];
                    content[0] = Integer.toString(cursor.getInt(cursor.getColumnIndex(ID)));
                    content[1] = cursor.getString(cursor.getColumnIndex(DIARYDATE));
                    content[2] = cursor.getString(cursor.getColumnIndex(TITLE));
                    content[3] = cursor.getString(cursor.getColumnIndex(DESCRIPTION));
                    content[4] = cursor.getString(cursor.getColumnIndex(CURRENTDATE));
                    rows.add(content);
                }while (cursor.moveToNext());
            }
        }

        return rows;
    }

    public ArrayList getDiaryByDate (String table){
        ArrayList<String[]>rows = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+table+" ORDER BY "+DIARYDATE+"  ASC ", null);

        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    String [] content = new String[5];
                    content[0] = Integer.toString(cursor.getInt(cursor.getColumnIndex(ID)));
                    content[1] = cursor.getString(cursor.getColumnIndex(DIARYDATE));
                    content[2] = cursor.getString(cursor.getColumnIndex(TITLE));
                    content[3] = cursor.getString(cursor.getColumnIndex(DESCRIPTION));
                    content[4] = cursor.getString(cursor.getColumnIndex(CURRENTDATE));
                    rows.add(content);
                }while (cursor.moveToNext());
            }
        }

        return rows;
    }



    public int countRows(String table) {
        Cursor cursor = db.query(table,  null, null, null, null, null, null);
        return cursor.getCount();
        }

    public ArrayList get_all_from_table(String table) {
        ArrayList<String[]>rows = new ArrayList<>();
        Cursor cursor = db.query(table,  null, null, null, null, null, null);

        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    String [] content = new String[5];
                    content[0] = Integer.toString(cursor.getInt(cursor.getColumnIndex(ID)));
                    content[1] = cursor.getString(cursor.getColumnIndex(DIARYDATE));
                    content[2] = cursor.getString(cursor.getColumnIndex(TITLE));
                    content[3] = cursor.getString(cursor.getColumnIndex(DESCRIPTION));
                    content[4] = cursor.getString(cursor.getColumnIndex(CURRENTDATE));
                    rows.add(content);
                    }while (cursor.moveToNext());
                }
            }

        return rows;
        }

    public static String getDIARY() {
        return DIARY;
    }
}


class BaseDBOpenHelper extends SQLiteOpenHelper {

    public BaseDBOpenHelper(Context context) {
        super(context, "uusi.db", null, 1);
        }

    private static final String CREATE_DIARY = "CREATE TABLE " + DIARY + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DIARYDATE + " DATE, " + TITLE + " TEXT, " + DESCRIPTION + " TEXT, " + CURRENTDATE + " DATE)";

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(CREATE_DIARY);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
            _db.execSQL("DROP TABLE IF EXISTS " +DIARY);
            onCreate(_db);
        }
}