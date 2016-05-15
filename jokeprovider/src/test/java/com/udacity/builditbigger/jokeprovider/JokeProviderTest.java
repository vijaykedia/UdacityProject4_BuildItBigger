package com.udacity.builditbigger.jokeprovider;

import org.junit.Test;

/**
 * Created by vijaykedia on 15/05/16.
 * Test class to test if we we are getting joke properly or not
 */
public class JokeProviderTest {

    public JokeProviderTest() {
    }

    @Test
    public void testJokeIsNotNull() {
        final JokeProvider jokeProvider = JokeProvider.getInstance();
        final String joke = jokeProvider.getJoke();

        assert joke != null;
        System.out.println(joke);

    }
}
