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
    private static int DB_VERSION = 1;


    private EntryDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE entry (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, content TEXT, mood TEXT, stamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP);");
        db.execSQL("INSERT INTO entry (title, content, mood) VALUES ('itsaTitle', 'testContent', 'testMood');");
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
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM entry",null);
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

/*
        // Initialize content and fill it with data from the entry
        //ContentValues content = null;
        String title = entry.getTitle();
        String mood = entry.getMood();
        String contents = entry.getContent();
*/
        // Make String array for input
        //String[] input = new String[] {title, contents, mood};

        //db.execSQL("INSERT INTO entry (title, content, mood) VALUES (title = ?, content = ?, mood = ?)", input);
        }

}
