package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.text.TextUtils;

import com.udacity.gradle.buiditbetter.joke.ChuckJoke;

import java.util.concurrent.CountDownLatch;

/**
 * AsyncTask class for testing server connection
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 * Implemented based on Sunghun's blog http://marksunghunpark.blogspot.co.uk/2015/05/how-to-test-asynctask-in-android.html
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    ChuckJoke mChuckJoke = null;
    Exception mError = null;
    CountDownLatch signal = null;

    public ApplicationTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        signal = new CountDownLatch(1);
    }

    @Override
    protected void tearDown() throws Exception {
        signal.countDown();
    }

    public void testGetJokeTask() throws InterruptedException {

        EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask(getContext());
        endpointsAsyncTask.execute(new EndpointsAsyncTask.OnRetrieveJokeListener() {

            @Override
            public void onComplete(ChuckJoke cJoke) {
                mChuckJoke = cJoke;
                signal.countDown();
            }

            @Override
            public void onError(Exception e) {
                mError = e;
            }
        });

        signal.await();
        assertNull(mError);
        assertNotNull(mChuckJoke);
        assertFalse(TextUtils.isEmpty(mChuckJoke.getJoke()));
    }
}