package com.example.phillip.journal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class EntryDatabase extends SQLiteOpenHelper {
    private static EntryDatabase instance;
    private String testTitle = "Title";
    private String testContent = "Title";
    private String testMood = "Title";
    private String testStamp = "30-03-1993";

    private EntryDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table entry (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, content TEXT, mood TEXT, stamp TEXT);");
        db.execSQL("INSERT INTO entry (title, content, mood, stamp) VALUES ('testTitle', 'testContent', 'testMood', 'testStamp');");
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
            return instance = EntryDatabase(context, name, factory, version); //------->KLOPT NIET!!!
        }
    }
}
