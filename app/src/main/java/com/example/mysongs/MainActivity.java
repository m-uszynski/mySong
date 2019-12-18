package com.example.mysongs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_SONG_ACTIVITY_REQUEST_CODE = 1;
    public static final int EDIT_SONG_ACTIVITY_REQUEST_CODE = 2;
    public static final int DETAILS_SONG_ACTIVITY_REQUEST_CODE = 3;

    public static final String EXTRA_EDIT_SONG_IDD = "editIdd";
    public static final String EXTRA_EDIT_SONG_TITLE = "editTitle";
    public static final String EXTRA_EDIT_SONG_AUTHORS = "editAuthors";
    public static final String EXTRA_EDIT_SONG_TEXT = "editText";
    public static final String EXTRA_EDIT_SONG_YTUR = "editYTur";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSongsData();

        FloatingActionButton addSongButton = findViewById(R.id.add_button);
        addSongButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddEditActivity.class);
                startActivityForResult(intent,NEW_SONG_ACTIVITY_REQUEST_CODE);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getSongsData(query);
                searchView.setQuery("",false);
                searchView.setIconified(true);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_item_clear:
                getSongsData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NEW_SONG_ACTIVITY_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                Snackbar.make(findViewById(R.id.main_view),"Song added!",Snackbar.LENGTH_LONG).show();
                getSongsData();
            }
            else if(resultCode == 2){
                Toast.makeText(getApplicationContext(),"Title and authors can't be empty",Toast.LENGTH_LONG).show();
            }
            else{

            }
        }
        if(requestCode == EDIT_SONG_ACTIVITY_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                Snackbar.make(findViewById(R.id.main_view),"Song edited!",Snackbar.LENGTH_LONG).show();
                getSongsData();
            }
            else if(resultCode == 2){
                Toast.makeText(getApplicationContext(), "Title and authors can't be empty",Toast.LENGTH_LONG).show();
            }
            else{

            }
        }
    }

    private class SongHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        private Song song;
        private TextView songTitleTextView;
        private TextView songAuthorsTextView;

        public SongHolder(@NonNull View v) {
            super(v);
            songTitleTextView = v.findViewById(R.id.song_title);
            songAuthorsTextView = v.findViewById(R.id.song_authors);
            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
        }

        public void bind(Song song){
            this.song = song;
            songTitleTextView.setText(song.getTitle());
            songAuthorsTextView.setText(song.getAuthors());
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
            intent.putExtra(EXTRA_EDIT_SONG_IDD,song.get_id());
            if(song.getTitle()!=null) intent.putExtra(EXTRA_EDIT_SONG_TITLE,song.getTitle());
            if(song.getAuthors()!=null) intent.putExtra(EXTRA_EDIT_SONG_AUTHORS,song.getAuthors());
            if(song.getText()!=null) intent.putExtra(EXTRA_EDIT_SONG_TEXT,song.getText());
            if(song.getYtlink()!=null) intent.putExtra(EXTRA_EDIT_SONG_YTUR,song.getYtlink());
            startActivityForResult(intent, DETAILS_SONG_ACTIVITY_REQUEST_CODE);
        }

        @Override
        public boolean onLongClick(View v) {
            Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
            intent.putExtra(EXTRA_EDIT_SONG_IDD,song.get_id());
            if(song.getTitle()!=null) intent.putExtra(EXTRA_EDIT_SONG_TITLE,song.getTitle());
            if(song.getAuthors()!=null) intent.putExtra(EXTRA_EDIT_SONG_AUTHORS,song.getAuthors());
            if(song.getText()!=null) intent.putExtra(EXTRA_EDIT_SONG_TEXT,song.getText());
            if(song.getYtlink()!=null) intent.putExtra(EXTRA_EDIT_SONG_YTUR,song.getYtlink());
            startActivityForResult(intent, EDIT_SONG_ACTIVITY_REQUEST_CODE);
            return true;
        }
    }

    private class SongAdapter extends RecyclerView.Adapter<SongHolder>{

        private List<Song> songs;

        @NonNull
        @Override
        public SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            return new SongHolder(layoutInflater.inflate(R.layout.song_list_item,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull SongHolder holder, int position) {
            if(songs!=null){
                Song song = songs.get(position);
                holder.bind(song);
            }
            else{
                Log.d("MainActivity","No songs");
            }
        }

        @Override
        public int getItemCount() {
            if(songs!=null){
                return songs.size();
            }
            else{
                return 0;
            }
        }

        void setSongs(List<Song> songs){
            this.songs = songs;
            notifyDataSetChanged();
        }
    }

    private void getSongsData(){
        SongService songService = RetrofitInstance.getRetrofitInstance().create(SongService.class);
        Call<List<Song>> bookApiCall = songService.getAllSongs();
        bookApiCall.enqueue(new Callback<List<Song>>() {

            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                setupSongListView(response.body());
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                Snackbar.make(findViewById(R.id.main_view),"Something went wrong...",Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void getSongsData(String filter){
        String finalQuery = prepareQuery(filter);
        SongService songService = RetrofitInstance.getRetrofitInstance().create(SongService.class);
        Call<List<Song>> bookApiCall = songService.getSongs(finalQuery);

        bookApiCall.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                setupSongListView(response.body());
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                Snackbar.make(findViewById(R.id.main_view),"Something went wrong...",Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void setupSongListView(List<Song> songs){
        RecyclerView recyclerView = (RecyclerView) this.findViewById(R.id.recyclerview);
        final SongAdapter adapter = new SongAdapter();
        adapter.setSongs(songs);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private String prepareQuery(String query){
        String[] queryParts = query.split("\\s+");
        return TextUtils.join("+",queryParts);
    }

}
