package com.example.mysongs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {


    private TextView songTitle;
    private TextView songAuthors;
    private TextView songText;
    private ImageView songThumbnails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        songTitle = findViewById(R.id.titleValue);
        songAuthors = findViewById(R.id.authorsValue);
        songText = findViewById(R.id.text_value);
        songThumbnails = findViewById(R.id.img_thumbnail);

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

            String uri = getIntent().getExtras().getString(MainActivity.EXTRA_EDIT_SONG_YTUR);

            if(checkNullOrEmpty(uri)){
            String url = "https://img.youtube.com/vi/"+ getIntent().getExtras().getString(MainActivity.EXTRA_EDIT_SONG_YTUR) +"/0.jpg";
            Picasso.with(this)
                    .load(url)
                    .placeholder(R.drawable.ic_music_note_black)
                    .into(songThumbnails);
            }
            else{
                songThumbnails.setImageResource(R.drawable.ic_music_note_black);
            }
        }
        else{
            songThumbnails.setImageResource(R.drawable.ic_music_note_black);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.details_menu,menu);

        MenuItem item = menu.findItem(R.id.action_play);

        if(getIntent().hasExtra(MainActivity.EXTRA_EDIT_SONG_YTUR)){
            String uri = getIntent().getExtras().getString(MainActivity.EXTRA_EDIT_SONG_YTUR);
            if(checkNullOrEmpty(uri)){
                item.setEnabled(true);
                item.setVisible(true);
            }
            else{
                item.setEnabled(false);
                item.setVisible(false);
            }
        }
        else{
            item.setEnabled(false);
            item.setVisible(false);
        }

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
            case R.id.action_play:
                String ytUrl = getIntent().getExtras().getString(MainActivity.EXTRA_EDIT_SONG_YTUR);
                watchYoutubeVideo(this,ytUrl);
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

    private boolean checkNullOrEmpty(String text){
        return text != null && !TextUtils.isEmpty(text);
    }

    public static void watchYoutubeVideo(Context context, String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + id));
        try{
            context.startActivity(appIntent);
        }
        catch(ActivityNotFoundException ex){
            Log.d("DetailsActivity",ex.toString());
            context.startActivity(webIntent);
        }
    }
}
