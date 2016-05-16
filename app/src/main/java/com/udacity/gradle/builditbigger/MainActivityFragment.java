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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.jakewharton.rxbinding.view.RxView;
import com.udacity.builditbigger.jokeprovider.JokeProvider;
import com.udacity.builditbigger.messagedisplayer.MessageDisplayActivity;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final String LOG_TAG = MainActivityFragment.class.getSimpleName();
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_main, container, false);

        final Button button = (Button) root.findViewById(R.id.button_display_joke);

        final Observable<Void> buttonObservable = RxView.clicks(button);

        final Observable<String> jokeObservable = Observable.defer(() -> Observable.just(JokeProvider.getInstance().getJoke()));
        final Observer<String> jokeObserver = new Observer<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Log.e(LOG_TAG, "-------Failure ------", e);
                final Intent intent = new Intent(getContext(), MessageDisplayActivity.class);
                intent.putExtra("message", "Failed to get joke.");
                startActivity(intent);
            }

            @Override
            public void onNext(@NonNull final String joke) {
                final Intent intent = new Intent(getContext(), MessageDisplayActivity.class);
                intent.putExtra("message", joke);
                startActivity(intent);
            }
        };

        buttonObservable.subscribe(aVoid -> {
            jokeObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(jokeObserver);
        });

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        return root;
    }
}
