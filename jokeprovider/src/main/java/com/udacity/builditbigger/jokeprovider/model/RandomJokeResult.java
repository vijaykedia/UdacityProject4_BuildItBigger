package com.udacity.builditbigger.jokeprovider.model;

/**
 * Created by vijaykedia on 15/05/16.
 * Java object which will be mapped with response from "http://api.icndb.com/jokes/random
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
