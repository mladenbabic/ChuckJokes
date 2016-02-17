package com.mladenbabic.joketellinglib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    public static final String JOKE = "joke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        TextView jokeTextView = (TextView) findViewById(R.id.joke_activity_text);
        if (getIntent().hasExtra(JOKE)) {
            jokeTextView.setText(getIntent().getStringExtra(JOKE));
        }
    }
}
