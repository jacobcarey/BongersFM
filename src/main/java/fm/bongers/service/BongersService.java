package fm.bongers.service;

import com.twitter.clientlib.ApiException;
import fm.bongers.infrastructure.Config;
import fm.bongers.infrastructure.LastFMUsernames;
import fm.bongers.infrastructure.LastTracksPlayed;
import fm.bongers.model.Track;
import fm.bongers.util.StringUtil;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BongersService {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(LoggerFactory.class); // Required for Logback to work in Vertx

  public static void checkForUpdates(TwitterService twitterService)
      throws URISyntaxException, IOException, ApiException {
    System.out.println("Let's start checking for bongers...");
    LOGGER.info("Let's start checking for bongers...");

    List<String> tweets = new ArrayList<>();
    LastFMService lastFMService = new LastFMService(Config.getInstance().getLastFmApi());
    for (Map.Entry<String, String> user : LastFMUsernames.getInstance().getUsernames().entrySet()) {
      try {
        Track latestTrack = lastFMService.getLatestTrack(user.getKey());
        LOGGER.info("Track found: " + latestTrack);
        if (LastTracksPlayed.getInstance().getTrackTimes().containsKey(user.getKey())) {
          Integer time = LastTracksPlayed.getInstance().getTrackTimes().get(user.getKey());
          if (time < latestTrack.getTime()) {
            if (latestTrack.getPlayCount() != null
                && Integer.parseInt(latestTrack.getPlayCount()) != 0) { // 0th bug.
              String tweet = StringUtil.buildTweet(user, latestTrack);
              tweets.add(tweet);
              LOGGER.info("Adding tweet: " + tweet);
              LastTracksPlayed.getInstance()
                  .getTrackTimes()
                  .put(user.getKey(), latestTrack.getTime());
            }
          }
        } else {
          LastTracksPlayed.getInstance().getTrackTimes().put(user.getKey(), latestTrack.getTime());
        }

      } catch (Exception e) {
        LOGGER.error(e);
        //    twitterService.sendTweet("@JacobCarey I'm fucked and need to temporarily shut down.
        // Beep bop.");
        e.printStackTrace();
      }
    }
    for (String tweet : tweets) {
      twitterService.sendTweet(tweet);
    }
  }
}
