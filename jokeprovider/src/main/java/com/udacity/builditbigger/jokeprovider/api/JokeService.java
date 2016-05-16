package com.udacity.builditbigger.jokeprovider.api;

import com.udacity.builditbigger.jokeprovider.model.RandomJokeResult;

import retrofit.http.GET;


/**
 * Created by vijaykedia on 15/05/16.
 * Interface for mapping http rest api with java object
 */
public interface JokeService {

    @GET("/jokes/random")
    RandomJokeResult getJoke();

}
