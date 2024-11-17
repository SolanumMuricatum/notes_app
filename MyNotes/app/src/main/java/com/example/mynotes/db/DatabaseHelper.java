package com.example.mynotes.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notes_db.db";
    private static final int DATABASE_VERSION = 3;

    public static final String NOTE_TABLE = "note";

    public static final String NOTE_ID = "id";
    public static final String NOTE_ORDER = "id_order";
    public static final String NOTE_NAME = "name";

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + NOTE_TABLE + " (" +
                    NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NOTE_ORDER + " LONG NOT NULL, " +
                    NOTE_NAME + " TEXT NOT NULL)";

    private static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + NOTE_TABLE;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_TABLE);
        onCreate(sqLiteDatabase);
    }
}
