package com.example.mysongs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SongService {

    @GET("songs")
    Call<List<Song>> getAllSongs();
    
}
