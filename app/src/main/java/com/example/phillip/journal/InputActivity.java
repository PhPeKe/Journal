package com.example.phillip.journal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InputActivity extends Activity {
    public JournalEntry entry = new JournalEntry();
    EntryDatabase db;
    public String mood = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        db = EntryDatabase.getInstance(getApplicationContext());

    }
    public void addEntry(View view) {
        // Get text from input...
        TextView title = findViewById(R.id.title_input);
        String titleText = title.getText().toString();
        entry.setTitle(titleText);

        // ...and content
        TextView content = findViewById(R.id.content_input);
        String contentText = content.getText().toString();
        entry.setContent(contentText);
        System.out.println(entry.getTitle());

        // If mood buttons weren't clicked it is set to not specified
        if (mood != null) {
            entry.setMood(mood);
        } else {
            mood = "not specified";
        }

        // Get instance of database
        EntryDatabase db = EntryDatabase.getInstance(getApplicationContext());

        // Insert entry
        db.insert(entry);

        // Go back to main
        Intent intent = new Intent(InputActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void moodClicked(View view) {
        // On click listener for mood buttons that stores appropriate mood in variable mood
        String buttonText = (String)((Button)view).getText();

        Button happy = findViewById(R.id.happy);
        Button sad = findViewById(R.id.sad);
        Button indifferent = findViewById(R.id.indifferent);

        // Reset all colors
        happy.setTextColor(Color.BLACK);
        sad.setTextColor(Color.BLACK);
        indifferent.setTextColor(Color.BLACK);

        switch(buttonText) {
            case ":-)":
                mood = "happy";
                happy.setTextColor(Color.RED);
                break;
            case ":-|":
                mood = "indifferent";
                indifferent.setTextColor(Color.RED);
                break;
            case ":-(":
                mood = "sad";
                sad.setTextColor(Color.RED);
                break;
        }
    }
}
