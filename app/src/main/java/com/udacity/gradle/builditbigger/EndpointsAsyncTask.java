package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.buiditbetter.joke.ChuckJoke;
import com.udacity.gradle.builditbigger.chuckJokeApi.ChuckJokeApi;

import java.io.IOException;
import java.io.InterruptedIOException;

/**
 * Created by Mladen Babic <email>info@mladenbabic.com</email> on 2/16/2016.
 */

public class EndpointsAsyncTask extends AsyncTask<EndpointsAsyncTask.OnRetrieveJokeListener, Void, ChuckJoke> {
    private static final String TAG = "EndpointsAsyncTask";
    private static ChuckJokeApi myApiService = null;
    private OnRetrieveJokeListener mListener;
    private Context context;

    public EndpointsAsyncTask(Context context) {
        this.context = context;
    }

    public interface OnRetrieveJokeListener {
        public void onComplete(ChuckJoke cJoke);
        public void onError(Exception e);
    }

    @Override
    protected ChuckJoke doInBackground(OnRetrieveJokeListener... params) {
        if (myApiService == null) {  // Only do this once
            ChuckJokeApi.Builder builder = new ChuckJokeApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl(context.getString(R.string.app_engine_project_url))
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            myApiService = builder.build();
        }

        mListener = params[0];

        try {
            return new ChuckJoke(myApiService.getChuckJoke().execute().getJoke());
        } catch (IOException e) {
            Log.d(TAG, "Error occurred in doInBackground: " + e);
            return null;
        }
    }

    @Override
    protected void onCancelled() {
        if (this.mListener != null) {
            this.mListener.onError(new InterruptedIOException("Task canceled"));
        }
    }

    @Override
    protected void onPostExecute(ChuckJoke cJoke) {
        if (cJoke != null) {
            if (mListener != null) {
                mListener.onComplete(cJoke);
            }
        } else {
            mListener.onError(new IllegalArgumentException("Joke is empty"));
        }
    }

    public EndpointsAsyncTask setListener(OnRetrieveJokeListener onRetrieveJokeListener) {
        this.mListener = onRetrieveJokeListener;
        return this;
    }
}
