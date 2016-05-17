package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.jakewharton.rxbinding.view.RxView;
import com.udacity.builditbigger.cloudjokeprovider.getJoke.GetJoke;
import com.udacity.builditbigger.messagedisplayer.MessageDisplayActivity;

import java.io.IOException;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final String LOG_TAG = MainActivityFragment.class.getSimpleName();

    private static final String FAILURE_MESSAGE = "Failed to get Joke";

    private final GetJoke service;

    // Views
    private ProgressBar progressBar;

    // Intent
    private Intent intent;

    public MainActivityFragment() {

        GetJoke.Builder builder = new GetJoke.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                .setRootUrl("https://udacityproject4-builditbigger.appspot.com/_ah/api/");

        service = builder.build();
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_main, container, false);

        final Button button = (Button) root.findViewById(R.id.button_display_joke);
        progressBar = (ProgressBar) root.findViewById(R.id.progressBar);

        // Observes button clicks
        final Observable<Void> buttonObservable = RxView.clicks(button);

        // Observes new jokes fetched from GCE
        final Observable<String> jokeObservable = Observable.defer(() -> {
            String result;
            try {
                result = service.provideJoke().execute().getValue();
            } catch (final IOException e) {
                Log.e(LOG_TAG, "Failed to get Joke from cloud engine", e);
                result = FAILURE_MESSAGE;
            }
            return Observable.just(result);
        });

        intent = new Intent(getContext(), MessageDisplayActivity.class);

        // Action defined when new joke is retrieved
        final Observer<String> jokeObserver = new Observer<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Log.e(LOG_TAG, "-------Failure ------", e);
                processJoke(FAILURE_MESSAGE);
            }

            @Override
            public void onNext(@NonNull final String joke) {
                processJoke(joke);
            }
        };

        buttonObservable.subscribe(aVoid -> {
            progressBar.setVisibility(View.VISIBLE);
            jokeObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(jokeObserver);
        });

        return root;
    }

    private void processJoke(@NonNull final String data) {
        progressBar.setVisibility(View.GONE);
        intent.putExtra(MessageDisplayActivity.MESSAGE_KEY, data);
        startActivity(intent);
    }
}
