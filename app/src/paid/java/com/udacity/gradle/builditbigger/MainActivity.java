package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

/**
 * Created by vijaykedia on 18/05/16.
 *
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FrameLayout parent = (FrameLayout) findViewById(R.id.parent_container);

        // Add fragment to it
        final FragmentManager manager = getSupportFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        try {
            transaction.add(R.id.parent_container, new MainActivityFragment(), MainActivityFragment.class.getSimpleName());
        } finally {
            transaction.commit();
        }
    }
}
