package com.example.mysongs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface SongService {

    @GET("songs")
    @Headers({
            "x-apikey: cbe90756e49360003e811c098343e61c43059",
            "cache-control: no-cache"
    })
    Call<List<Song>> getAllSongs();

}
