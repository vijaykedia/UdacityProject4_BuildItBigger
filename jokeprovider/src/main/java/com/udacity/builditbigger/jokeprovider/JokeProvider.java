package com.udacity.builditbigger.jokeprovider;

import com.udacity.builditbigger.jokeprovider.api.JokeService;
import com.udacity.builditbigger.jokeprovider.model.RandomJokeResult;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Class which will act as interface for client to get a joke
 */
public class JokeProvider {

    private static final String BASE_URL = "http://api.icndb.com";

    private static JokeProvider INSTANCE;

    private final Call<RandomJokeResult> jokeCall;

    private JokeProvider() {

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        final JokeService jokeService = retrofit.create(JokeService.class);

        jokeCall = jokeService.getJoke();
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

        try {
            return jokeCall.clone().execute().body().getValue().getJoke();
        } catch (final IOException e) {
            return null;
        }
    }
}
