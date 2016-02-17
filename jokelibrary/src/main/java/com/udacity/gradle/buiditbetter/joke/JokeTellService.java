package com.udacity.gradle.buiditbetter.joke;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

public class JokeTellService {

    private static final Logger log = Logger.getLogger(JokeTellService.class.getName());

    public static ChuckJoke getChuckJoke() {

        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = null;

        try {
            URL url = new URL("http://api.icndb.com/jokes/random");
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            char[] charBuffer = new char[128];
            int bytesRead = -1;
            while ((bytesRead = reader.read(charBuffer)) > 0) {
                stringBuilder.append(charBuffer, 0, bytesRead);
            }
            reader.close();

        } catch (MalformedURLException e) {
            log.warning("MalformedURLException: " + e);
        } catch (IOException e) {
            log.warning("IOException: " + e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {

                }
            }
        }
        log.info("joke json: " + stringBuilder.toString());
        JokeResult jokeResult = new Gson().fromJson(stringBuilder.toString(), JokeResult.class);
        if (jokeResult != null) {
            return jokeResult.getValue();
        } else {
            return new ChuckJoke("Chuck Norris once kicked a horse in the chin. Its decendants are known today as Giraffes.");
        }
    }

}
