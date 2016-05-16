package com.udacity.builditbigger.cloudJokeProvider;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.udacity.builditbigger.jokeprovider.JokeProvider;

/** An endpoint class we are exposing */
@Api(
  name = "getJoke",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "cloudJokeProvider.builditbigger.udacity.com",
    ownerName = "cloudJokeProvider.builditbigger.udacity.com",
    packagePath=""
  )
)
public class JokeProviderEndpoint {

    /** A simple endpoint method that takes a name and says Hi back */
    @ApiMethod(name = "provideJoke")
    public Joke provideJoke() {

        final String joke = JokeProvider.getInstance().getJoke();
        final Joke result = new Joke();
        result.setValue(joke);
        return result;
    }

}
