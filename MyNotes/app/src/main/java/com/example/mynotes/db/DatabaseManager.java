package com.example.mynotes.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mynotes.model.Note;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static DatabaseManager instance;
    private DatabaseHelper dbHelper;
    private final Context context;
    private SQLiteDatabase database;

    public DatabaseManager(Context context) {
        this.context = context.getApplicationContext();
    }

    public static synchronized DatabaseManager getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseManager(context);
        }
        return instance;
    }

    public void open() throws SQLDataException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String noteName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NOTE_NAME, noteName);

        // Получаем максимальный порядковый номер из таблицы
        int maxOrder = getMaxOrder();
        contentValues.put(DatabaseHelper.NOTE_ORDER, maxOrder + 1);

        database.insert(DatabaseHelper.NOTE_TABLE, null, contentValues);
    }

    public List<Note> fetchAllNotes() {
        List<Note> notes = new ArrayList<>();
        String[] columns = { DatabaseHelper.NOTE_ID, DatabaseHelper.NOTE_NAME, DatabaseHelper.NOTE_ORDER };

        Cursor cursor = database.query(DatabaseHelper.NOTE_TABLE, columns, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                long id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.NOTE_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.NOTE_NAME));
                long id_order = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.NOTE_ORDER));
                notes.add(new Note(id, name, id_order));
            }
            cursor.close();
        }

        return notes;
    }

    public void update(long id, long id_order, String noteName) {
        ContentValues contentValues = new ContentValues();
        if (noteName != null) {
            contentValues.put(DatabaseHelper.NOTE_NAME, noteName);
        }
        contentValues.put(DatabaseHelper.NOTE_ORDER, id_order);

        database.update(DatabaseHelper.NOTE_TABLE, contentValues, DatabaseHelper.NOTE_ID + "=" + id, null);
    }

    public void delete(long id_order) {
        database.delete(DatabaseHelper.NOTE_TABLE, DatabaseHelper.NOTE_ORDER + "=" + id_order, null);

        updateNoteIdOrder();
    }

    //для обновления идентификаторов
    private void updateNoteIdOrder() {
        List<Note> notes = fetchAllNotes();

        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            long newIdOrder = i + 1;

            if (note.getId_order() != newIdOrder) { //передаём айдишник + новый порядок + пустое описание
                update(note.getId(), newIdOrder, note.getName());
            }
        }
    }

    private int getMaxOrder() {
        Cursor cursor = database.rawQuery("SELECT MAX(" + DatabaseHelper.NOTE_ORDER + ") FROM " + DatabaseHelper.NOTE_TABLE, null);
        int maxOrder = 0;
        if (cursor.moveToFirst()) {
            maxOrder = cursor.getInt(0);
        }
        cursor.close();
        return maxOrder;
    }

}