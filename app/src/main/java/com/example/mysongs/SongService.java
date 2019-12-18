package com.example.mysongs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SongService {

    @GET("songs")
    @Headers({
            "x-apikey: cbe90756e49360003e811c098343e61c43059",
            "cache-control: no-cache"
    })
    Call<List<Song>> getAllSongs();

    @GET("songs")
    @Headers({
            "x-apikey: cbe90756e49360003e811c098343e61c43059",
            "cache-control: no-cache"
    })
    Call<List<Song>> getSongs(@Query("filter") String filter);

    @POST("songs")
    @Headers({
            "content-type: application/json",
            "x-apikey: cbe90756e49360003e811c098343e61c43059",
            "cache-control: no-cache"
    })
    Call<Song> addSong(@Body Song song);

    @PUT("songs/{id}")
    @Headers({
            "content-type: application/json",
            "x-apikey: cbe90756e49360003e811c098343e61c43059",
            "cache-control: no-cache"
    })
    Call<Song> editSong(@Path("id") String id, @Body Song song);

}
