package com.udacity.gradle.buiditbetter.joke;

/**
 * Created by Mladen Babic <email>info@mladenbabic.com</email> on 2/16/2016.
 */
public class ChuckJoke {

    private String joke;
    private long id;

    public ChuckJoke() {
    }

    public ChuckJoke(String joke) {
        this.joke = joke;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
