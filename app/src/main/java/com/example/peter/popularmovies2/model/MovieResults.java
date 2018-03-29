package com.example.peter.popularmovies2.model;

import java.util.List;

public class MovieResults {
    List<Movies> movies;
    private String page;
    private int total_results;
    private int total_pages;

    public MovieResults(String page, int total_results, int total_pages, List<Movies> movies) {
        this.page = page;
        this.total_results = total_results;
        this.total_pages = total_pages;
    }

}
