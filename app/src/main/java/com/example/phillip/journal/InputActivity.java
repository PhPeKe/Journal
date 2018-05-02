package com.example.phillip.journal;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class InputActivity extends Activity {
    JournalEntry entry;
    EntryDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        db = EntryDatabase.getInstance(getApplicationContext());

    }
    public void addEntry(View view) {
        TextView title = findViewById(R.id.title_input);
        String titleText = (String) title.getText();
        entry.setMood(titleText);

        TextView content = findViewById(R.id.content_input);
        String contentText = (String) content.getText();
        entry.setMood(contentText);
    }

    public void moodClicked(View view) {
        System.out.println("WORKS");
    }
}
