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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get list-view to pair adapter with
        list = findViewById(R.id.listView);

        // Get database
        EntryDatabase db = EntryDatabase.getInstance(getApplicationContext());

        // Get cursor
        Cursor cursor = db.selectAll();

        // Prepare adapter
        EntryAdapter adapter = new EntryAdapter(this, cursor);

        // Pair adapter and list-view#
        list.setAdapter(adapter);

        // Pair list-view with listener
        list.setOnItemClickListener(new ListClickListener());
    }

    public void floatClick(View view) {
        intent = new Intent(MainActivity.this, InputActivity.class);

        // Move to input activity
        startActivity(intent);
    }

    private class ListClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            JournalEntry clickedEntry = (JournalEntry) parent.getItemAtPosition(position);
            intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("clickedEntry", clickedEntry);
            startActivity(intent);
        }
    }

}
