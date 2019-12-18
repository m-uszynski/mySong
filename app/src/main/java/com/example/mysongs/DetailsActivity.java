package com.example.mysongs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.details_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent replyIntent = new Intent();
        switch(item.getItemId()){
            case R.id.action_delete:
                String idd = getIntent().getExtras().getString(MainActivity.EXTRA_EDIT_SONG_IDD);
                deleteSong(idd);
                setResult(RESULT_OK,replyIntent);
                finish();
                return true;
            case R.id.action_cancel:
                setResult(RESULT_CANCELED,replyIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteSong(String id){
        SongService songService = RetrofitInstance.getRetrofitInstance().create(SongService.class);
        Call<Song> call = songService.deleteSong(id);
        call.enqueue(new Callback<Song>() {
            @Override
            public void onResponse(Call<Song> call, Response<Song> response) {
                Song song1 = response.body();
            }

            @Override
            public void onFailure(Call<Song> call, Throwable t) {

            }
        });
    }
}
