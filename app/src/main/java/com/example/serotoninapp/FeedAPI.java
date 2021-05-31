package com.example.serotoninapp;

import com.example.serotoninapp.model.Feed;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FeedAPI {

     String BASE_URL = "https://www.reddit.com/r/";

    @GET("UpliftingNews/.rss")
    Call<Feed> getFeed();
}
