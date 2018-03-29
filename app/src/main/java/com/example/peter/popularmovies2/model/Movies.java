package com.example.peter.popularmovies2.model;

public class Movies {
    private int vote_count;
    private int id;
    private boolean video;
    private long vote_average;
    private String title;
    private long popularity;
    private String poster_path;
    private String original_language;
    private String original_title;
    private String backdrop_path;
    private boolean adult;
    private String overview;
    private String release_date;

    public Movies(int vote_count, int id, boolean video, long vote_average, String title,
                  long popularity, String poster_path, String original_language,
                  String original_title, String backdrop_path, boolean adult, String overview,
                  String release_date) {

        this.vote_count = vote_count;
        this.id = id;
        this.video = video;
        this.vote_average = vote_average;
        this.title = title;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.original_language = original_language;
        this.original_title = original_title;
        this.backdrop_path = backdrop_path;
        this.adult = adult;
        this.overview = overview;
        this.release_date = release_date;
    }
}
