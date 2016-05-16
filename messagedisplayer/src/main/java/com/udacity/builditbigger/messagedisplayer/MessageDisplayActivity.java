package com.udacity.builditbigger.messagedisplayer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by vijaykedia on 16/05/16.
 * This activity is used to display
 */
public class MessageDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle bundle = getIntent().getExtras();
        final String message = bundle.getString("message");

        Toast.makeText(MessageDisplayActivity.this, message, Toast.LENGTH_SHORT).show();

        finish();
    }
}
