package com.example.phillip.journal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends Activity {
    public EntryDatabase retrievedEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        retrievedEntry = (EntryDatabase) intent.getSerializableExtra("clickedEntry");

        TextView title = findViewById(R.id.title_detailed);

        TextView mood = findViewById(R.id.mood_detailed);

        TextView content = findViewById(R.id.content_detailed);
    }
}
