package com.example.phillip.journal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.DropBoxManager;

import java.security.KeyStore;
import java.util.Map;

public class EntryDatabase extends SQLiteOpenHelper {
    private static EntryDatabase instance;
    private static final String DB_NAME = "entry";
    private static final int DB_VERSION = 1;


    private EntryDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table entry
        db.execSQL("CREATE TABLE entry (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, content TEXT, mood TEXT, stamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_NAME);
        onCreate(db);
    }

    // Transform into singleton
    public static EntryDatabase getInstance(Context context) {
        if (instance != null) {
            return instance;
        } else {
            return instance = new EntryDatabase(context,DB_NAME, null, DB_VERSION);
        }
    }

    public Cursor selectAll() {
        // Get database, save cursor retrieved through query and return it
        SQLiteDatabase db = getWritableDatabase();
        //Cursor cursor = db.rawQuery("SELECT * FROM entry",null);
        Cursor cursor = db.rawQuery("SELECT _id, title, content, mood, (strftime('%Y-%m-%d %H:%M:%S', stamp)) AS stamp FROM entry",null);
        return cursor;
    }

    public void insert(JournalEntry entry) {

        // Get database connection
        SQLiteDatabase db = getWritableDatabase();

        // Put objects in ContentValues object
        ContentValues insertValues = new ContentValues();
        insertValues.put("title", entry.getTitle());
        insertValues.put("mood", entry.getMood());
        insertValues.put("content", entry.getContent());

        // Insert entry into database
        db.insert(DB_NAME, null, insertValues);

    }

    public void delete(long id) {
        // Parse long to string for db search
        String searchId = String.valueOf(id);

        // Get database connection
        SQLiteDatabase db = getWritableDatabase();

        // Delete entry with id searchId from database
        db.execSQL("DELETE FROM entry WHERE _id == " + searchId);

    }
}
