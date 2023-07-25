package info.hccis.islandartstore.ui.social;

import android.os.AsyncTask;

import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TweetTask extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... params) {
        String message = params[0];
        Twitter twitter = Twitter.newBuilder()
                .oAuthConsumer("1N6JWk8llq1OoK3hj3IRQ1RYn", "iLao92LrBV7CgF79eix0jVb7CsqtwUTHyINY30RT5LhGS6hhFx")
                .oAuthAccessToken("1621207890462736394-NHlf5CupsVFxoCpThhpkB3pH3FlZJs", "n6OEnpep7csgHA6HaiM4mNSIf0gz6FrsPZyeMvUTSbRXa")
                .build();
        try {
            twitter.v1().tweets().updateStatus(message);
        } catch (TwitterException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}

