package com.example.phillip.journal;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.io.Serializable;

public class MainActivity extends Activity {
    Intent intent;
    ListView list;
    private EntryDatabase db;
    private EntryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get list-view to pair adapter with
        list = findViewById(R.id.listView);

        // Get database
        db = EntryDatabase.getInstance(getApplicationContext());

        // Get cursor
        Cursor cursor = db.selectAll();

        // Prepare adapter
        adapter = new EntryAdapter(this, cursor);

        // Pair adapter and list-view#
        list.setAdapter(adapter);

        // Pair list-view with listener
        list.setOnItemClickListener(new ListClickListener());
        list.setOnItemLongClickListener(new ListLongClickListener());
    }

    public void floatClick(View view) {
        intent = new Intent(MainActivity.this, InputActivity.class);

        // Move to input activity
        startActivity(intent);
    }

    private class ListClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intent = new Intent(MainActivity.this, DetailActivity.class);

            Cursor cursor = (Cursor) parent.getItemAtPosition(position);
            JournalEntry clickedEntry = new JournalEntry();

            String mood = cursor.getString(cursor.getColumnIndex("mood"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String stamp = cursor.getString(cursor.getColumnIndex("stamp"));

            clickedEntry.setMood(cursor.getString(cursor.getColumnIndex("mood")));
            clickedEntry.setContent(cursor.getString(cursor.getColumnIndex("content")));
            clickedEntry.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            clickedEntry.setTimestamp(cursor.getString(cursor.getColumnIndex("stamp")));

            intent.putExtra("clickedEntry", clickedEntry);
            startActivity(intent);
        }
    }

    private class ListLongClickListener implements ListView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            // Get appropriate Journal Entry
            Cursor clickedEntry = (Cursor) parent.getItemAtPosition(position);

            // Get index, then id, the cast to long
            int titleIndex = clickedEntry.getColumnIndex("_id");
            String stringId = clickedEntry.getString(titleIndex);
            Long deleteId = Long.parseLong(stringId);

            // Get instance of database and delete entry, update list-view
            db = EntryDatabase.getInstance(getApplicationContext());
            db.delete(deleteId);
            updateData();

            return true;
        }
    }

    public void updateData() {

        // Get cursor and swap it
        Cursor cursor = db.selectAll();
        adapter.swapCursor(cursor);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateData();
    }
}
