package com.udacity.builditbigger.jokeprovider;

import com.udacity.builditbigger.jokeprovider.api.JokeService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import retrofit.RestAdapter;

/**
 * Class which will act as interface for client to get a joke
 */
public class JokeProvider {

    private static final String BASE_URL = "http://api.icndb.com";

    private static JokeProvider INSTANCE;

    private final JokeService jokeService;

    private JokeProvider() {

        final RestAdapter retrofit = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .build();

        jokeService = retrofit.create(JokeService.class);
    }

    @NotNull
    public static JokeProvider getInstance() {
        if (INSTANCE == null) {
            synchronized (JokeProvider.class) {
                if (INSTANCE == null) {
                    INSTANCE = new JokeProvider();
                }
            }
        }
        return INSTANCE;
    }

    @Nullable
    public String getJoke() {
        return jokeService.getJoke().getValue().getJoke();
    }
}
