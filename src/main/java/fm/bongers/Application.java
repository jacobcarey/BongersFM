package fm.bongers;

import com.github.redouane59.twitter.TwitterClient;
import fm.bongers.infrastructure.Config;
import fm.bongers.infrastructure.LastFMUsernames;
import fm.bongers.infrastructure.LastTracksPlayed;
import fm.bongers.model.Track;
import fm.bongers.service.ConnectService;
import fm.bongers.service.LastFMService;
import fm.bongers.service.TwitterService;
import fm.bongers.util.StringUtil;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Application {

  private static TwitterClient twitterClient;

  static void scheduling() {

    List<String> tweets = new ArrayList<>();
    LastFMService lastFMService = new LastFMService(Config.getInstance().getLastFmApi());
    for (Map.Entry<String, String> user : LastFMUsernames.getInstance().getUsernames().entrySet()) {
      try {
        Track latestTrack = lastFMService.getLatestTrack(user.getKey());

        if (LastTracksPlayed.getInstance().getTrackTimes().containsKey(user.getKey())) {
          Integer time = LastTracksPlayed.getInstance().getTrackTimes().get(user.getKey());
          if (time < latestTrack.getTime()) {
            tweets.add(StringUtil.buildTweet(user, latestTrack));
            LastTracksPlayed.getInstance()
                .getTrackTimes()
                .put(user.getKey(), latestTrack.getTime());
          }
        } else {
          LastTracksPlayed.getInstance().getTrackTimes().put(user.getKey(), latestTrack.getTime());
        }

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    for (String tweet : tweets) {
      new TwitterService(twitterClient).sendTweet(tweet);
    }
  }

  public static void main(String[] args) {
    VertxOptions vertxOptions =
        new VertxOptions().setBlockedThreadCheckInterval(1000 * 60 * 2); // Two minutes...
    Vertx vertx = Vertx.vertx(vertxOptions);
    System.out.println("Deploying verticle...");
    vertx.deployVerticle("fm.bongers.verticle.MainVerticle");
    System.out.println("Deployed verticle...");

    twitterClient = ConnectService.connectTwitter();
    vertx.setPeriodic(1000 * 60 * 4, (l) -> scheduling()); // Four minutes...
  }
}
