package com.example.mysongs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSongsData();
    }

    private class SongHolder extends RecyclerView.ViewHolder{

        private TextView songTitleTextView;
        private TextView songAuthorsTextView;

        public SongHolder(@NonNull View v) {
            super(v);
            songTitleTextView = v.findViewById(R.id.song_title);
            songAuthorsTextView = v.findViewById(R.id.song_authors);
        }

        public void bind(Song song){
            songTitleTextView.setText(song.getTitle());
            songAuthorsTextView.setText(song.getAuthors());
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

    private void setupSongListView(List<Song> songs){
        RecyclerView recyclerView = (RecyclerView) this.findViewById(R.id.recyclerview);
        final SongAdapter adapter = new SongAdapter();
        adapter.setSongs(songs);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
