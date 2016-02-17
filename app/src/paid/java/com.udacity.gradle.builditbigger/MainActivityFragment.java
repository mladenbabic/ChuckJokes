package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mladenbabic.joketellinglib.JokeActivity;
import com.udacity.gradle.buiditbetter.joke.ChuckJoke;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements EndpointsAsyncTask.OnRetrieveJokeListener {

    private static final String TAG = "MPaidFragment";
    private View mRoot;
    private ProgressBar progressBar;
    private Button mJokeButton;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_main, container, false);
        mJokeButton = (Button) mRoot.findViewById(R.id.tell_joke_btn);
        progressBar = (ProgressBar) mRoot.findViewById(R.id.progressBar);
        mJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tellJoke();
            }
        });
        return mRoot;
    }

    private void tellJoke() {
        progressBar.setVisibility(View.VISIBLE);
        mJokeButton.setEnabled(false);
        new EndpointsAsyncTask(getContext()).execute(this);
    }

    @Override
    public void onComplete(ChuckJoke joke) {
        progressBar.setVisibility(View.GONE);
        mJokeButton.setEnabled(true);
        Intent jokeIntent = new Intent(getContext(), JokeActivity.class);
        jokeIntent.putExtra(JokeActivity.JOKE, joke.getJoke());
        getContext().startActivity(jokeIntent);
    }

    @Override
    public void onError(Exception e) {
        progressBar.setVisibility(View.GONE);
        mJokeButton.setEnabled(true);
        Toast.makeText(getContext(), "Failed to retrieve joke!", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "onError: ", e);
    }
}
