package com.example.phillip.journal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Map;

public class EntryDatabase extends SQLiteOpenHelper {
    private static EntryDatabase instance;
    private String testTitle = "Title";
    private String testContent = "Title";
    private String testMood = "Title";
    private String testStamp = "30-03-1993";
    public int version;


    private EntryDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        version = db.getVersion();
        db.execSQL("CREATE TABLE entry (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, content TEXT, mood TEXT, stamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP);");
        db.execSQL("INSERT INTO entry (title, content, mood) VALUES ('itsaTitle', 'testContent', 'testMood');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "entry");
        onCreate(db);
    }

    // Transform into singleton
    public static EntryDatabase getInstance(Context context) {
        if (instance != null) {
            return instance;
        } else {
            return instance = new EntryDatabase(context,"entry", null, 1); //------->KLOPT misschien!!!
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

        // Initialize content and fill it with data from the entry
        //ContentValues content = null;
        String title = entry.getTitle();
        String mood = entry.getMood();
        String content = entry.getContent();
        /*
        content.put("title", entry.getTitle());
        content.put("mood", entry.getMood());
        content.put("content", entry.getContent());
*/
        // Insert entry into database
        db.execSQL("INSERT INTO entry (title, content, mood) VALUES (title = ?, content = ?, mood = ?)", new String[] {title, content, mood});
    }

}
