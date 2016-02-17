package com.udacity.gradle.builditbigger;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.udacity.gradle.buiditbetter.joke.ChuckJoke;
import com.udacity.gradle.buiditbetter.joke.JokeTellService;

import java.util.logging.Logger;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "chuckJokeApi",
        version = "v1",
        resource = "chuckJoke",
        namespace = @ApiNamespace(
                ownerDomain = "builditbigger.gradle.udacity.com",
                ownerName = "builditbigger.gradle.udacity.com",
                packagePath = ""
        )
)
public class ChuckJokeEndpoint {

    private static final Logger logger = Logger.getLogger(ChuckJokeEndpoint.class.getName());

    /**
     * This method gets the <code>ChuckJoke</code> object associated with the specified <code>id</code>.
     * @return The <code>ChuckJoke</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getChuckJoke")
    public ChuckJoke getChuckJoke() {
        return JokeTellService.getChuckJoke();
    }

    /**
     * This inserts a new <code>ChuckJoke</code> object.
     *
     * @param chuckJoke The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertChuckJoke")
    public ChuckJoke insertChuckJoke(ChuckJoke chuckJoke) {
        // TODO: Implement this function
        logger.info("Calling insertChuckJoke method");
        return chuckJoke;
    }
}