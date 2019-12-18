package com.example.mysongs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

        // if edit

        String idd;

        if(getIntent().hasExtra(MainActivity.EXTRA_EDIT_SONG_IDD)){
            idd = getIntent().getExtras().getString(MainActivity.EXTRA_EDIT_SONG_IDD);
            Log.d("abc",idd.toString());
        }

        if(getIntent().hasExtra(MainActivity.EXTRA_EDIT_SONG_TITLE)){
            songTitle.setText(getIntent().getExtras().getString(MainActivity.EXTRA_EDIT_SONG_TITLE));
        }

        if(getIntent().hasExtra(MainActivity.EXTRA_EDIT_SONG_AUTHORS)){
            songAuthors.setText(getIntent().getExtras().getString(MainActivity.EXTRA_EDIT_SONG_AUTHORS));
        }

        if(getIntent().hasExtra(MainActivity.EXTRA_EDIT_SONG_TEXT)){
            songText.setText(getIntent().getExtras().getString(MainActivity.EXTRA_EDIT_SONG_TEXT));
        }

        if(getIntent().hasExtra(MainActivity.EXTRA_EDIT_SONG_YTUR)){
            songYTur.setText(getIntent().getExtras().getString(MainActivity.EXTRA_EDIT_SONG_YTUR));
        }

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
                setResult(RESULT_CANCELED,replyIntent);
                break;

            case R.id.action_add:
                String title = songTitle.getText().toString();
                String authors = songAuthors.getText().toString();
                String text = songText.getText().toString();
                String YTUr = null;
                if(!TextUtils.isEmpty(songYTur.getText())){
                     YTUr = songYTur.getText().toString();
                }

                if(TextUtils.isEmpty(title) || TextUtils.isEmpty(authors)){
                    setResult(2, replyIntent);
                }
                else{

                    if(getIntent().hasExtra(MainActivity.EXTRA_EDIT_SONG_IDD)){
                        String idd = getIntent().getExtras().getString(MainActivity.EXTRA_EDIT_SONG_IDD);
                        Song song = new Song(0,title,authors,text,YTUr);
                        editSong(idd,song);
                        setResult(RESULT_OK, replyIntent);
                    }
                    else {
                        Song song = new Song(0, title, authors, text, YTUr);
                        addSong(song);
                        setResult(RESULT_OK, replyIntent);
                    }
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

    private void editSong(String id, Song song){
        SongService songService = RetrofitInstance.getRetrofitInstance().create(SongService.class);
        Call<Song> call = songService.editSong(id,song);
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
