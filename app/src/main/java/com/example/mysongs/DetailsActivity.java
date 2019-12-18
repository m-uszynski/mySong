package com.example.mysongs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {


    private TextView songTitle;
    private TextView songAuthors;
    private TextView songText;
    private TextView songYTur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        songTitle = findViewById(R.id.titleValue);
        songAuthors = findViewById(R.id.authorsValue);
        songText = findViewById(R.id.text_value);

        if(getIntent().hasExtra(MainActivity.EXTRA_EDIT_SONG_TITLE)){
            songTitle.setText(getIntent().getExtras().getString(MainActivity.EXTRA_EDIT_SONG_TITLE));
        }

        if(getIntent().hasExtra(MainActivity.EXTRA_EDIT_SONG_AUTHORS)){
            songAuthors.setText(getIntent().getExtras().getString(MainActivity.EXTRA_EDIT_SONG_AUTHORS));
        }

        if(getIntent().hasExtra(MainActivity.EXTRA_EDIT_SONG_TEXT)){
            songText.setText(getIntent().getExtras().getString(MainActivity.EXTRA_EDIT_SONG_TEXT));
        }

    }
}
