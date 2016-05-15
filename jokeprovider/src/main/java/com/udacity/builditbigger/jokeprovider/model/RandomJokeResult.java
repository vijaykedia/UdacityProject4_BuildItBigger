package com.udacity.builditbigger.jokeprovider.model;

import com.udacity.builditbigger.jokeprovider.api.JokeService;

/**
 * Created by vijaykedia on 15/05/16.
 * Java object which will be mapped with response from {@link JokeService#getJoke()}
 */
public class RandomJokeResult {

    private Value value;

    public Value getValue() {
        return value;
    }

    public static class Value {

        private String joke;

        public String getJoke() {
            return joke;
        }

    }
}
