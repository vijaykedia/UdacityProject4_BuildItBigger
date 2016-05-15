package com.udacity.builditbigger.jokeprovider;

import com.udacity.builditbigger.jokeprovider.model.RandomJokeResult;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Class which will act as interface from client to get a joke
 */
public class JokeProvider {

    private static final String BASE_URL = "http://api.icndb.com";

    private static JokeProvider INSTANCE;

    private final Call<RandomJokeResult> jokeCall;

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

    private JokeProvider() {

        final Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        final com.udacity.builditbigger.jokeprovider.api.JokeService jokeService = retrofit.create(com.udacity.builditbigger.jokeprovider.api.JokeService.class);

        jokeCall = jokeService.getJoke();
    }

    @Nullable
    public String getJoke() {
        try {
            return jokeCall.execute().body().getValue().getJoke();
        } catch (final IOException e) {
            return null;
        }
    }
}
