package com.example.mysongs;

import com.google.gson.annotations.SerializedName;

public class Song {
    @SerializedName("id")
    private Integer id;
    @SerializedName("title")
    private String title;
    @SerializedName("authors")
    private String authors;
    @SerializedName("text")
    private String text;
    @SerializedName("ytlink")
    private String ytlink;

    public Song(Integer id, String title, String authors, String text, String ytlink){
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.text = text;
        this.ytlink = ytlink;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public String getText() {
        return text;
    }

    public String getYtlink() {
        return ytlink;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setAuthors(String authors){
        this.authors = authors;
    }

    public void setText(String text){
        this.text = text;
    }

    public void setYtlink(String ytlink){
        this.ytlink = ytlink;
    }
}
