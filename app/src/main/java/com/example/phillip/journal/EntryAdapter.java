package com.example.phillip.journal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class EntryAdapter extends ResourceCursorAdapter {


    public EntryAdapter(Context context, Cursor cursor) {
        super(context, R.layout.entry_row, cursor);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Get handlers
        TextView title = view.findViewById(R.id.title_entry);
        TextView mood = view.findViewById(R.id.mood_entry);
        TextView timestamp = view.findViewById(R.id.timestamp_entry);

        // Set text
        setText(title, "title", cursor);
        setText(mood, "mood", cursor);

        int titleIndex = cursor.getColumnIndex("stamp");
        Long millis = cursor.getLong(titleIndex);
        Date date = new Date(millis);
        timestamp.setText(date.toString());

        System.out.println("Date:");
        System.out.println(date);

    }

    // Method to prevent redundancy in code
    public void setText(TextView tView, String colName, Cursor cursor) {

        int titleIndex = cursor.getColumnIndex(colName);
        String titleText = cursor.getString(titleIndex);
        tView.setText(titleText);
    }

}