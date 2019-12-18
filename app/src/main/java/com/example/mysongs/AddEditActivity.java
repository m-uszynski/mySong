package com.example.mysongs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEditActivity extends AppCompatActivity {

    private EditText songTitle;
    private EditText songAuthors;
    private EditText songText;
    private EditText songYTur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        songTitle = findViewById(R.id.edit_song_title);
        songAuthors = findViewById(R.id.edit_song_authors);
        songText = findViewById(R.id.edit_song_text);
        songYTur = findViewById(R.id.edit_song_ur);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_edit_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        Intent replyIntent = new Intent();

        switch(id){
            case R.id.action_cancel:
                break;

            case R.id.action_add:
                String title = songTitle.getText().toString();
                String authors = songAuthors.getText().toString();
                String text = songText.getText().toString();
                String YTUr = null;
                if(!TextUtils.isEmpty(songYTur.getText())){
                     YTUr = "https://www.youtube.com/watch?v=" + songYTur.getText().toString();
                }
                if(TextUtils.isEmpty(title) || TextUtils.isEmpty(authors)){
                    setResult(RESULT_CANCELED, replyIntent);
                }
                else{
                    Song song = new Song(0,title,authors,text,YTUr);
                    addSong(song);
                    setResult(RESULT_OK, replyIntent);
                }
                break;

        }
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void addSong(Song song){
        SongService songService = RetrofitInstance.getRetrofitInstance().create(SongService.class);
        Call<Song> call = songService.addSong(song);
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
