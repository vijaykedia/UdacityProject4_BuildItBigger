package com.udacity.builditbigger.cloudJokeProvider;

/**
 * The object model for the data we are sending through endpoints
 */
public class Joke {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}
