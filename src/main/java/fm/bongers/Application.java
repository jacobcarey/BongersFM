package fm.bongers;

import com.github.redouane59.twitter.TwitterClient;
import fm.bongers.infrastructure.Config;
import fm.bongers.infrastructure.LastFMUsernames;
import fm.bongers.infrastructure.LastTracksPlayed;
import fm.bongers.model.Track;
import fm.bongers.service.ConnectService;
import fm.bongers.service.LastFMService;
import fm.bongers.service.PingService;
import fm.bongers.service.TwitterService;
import fm.bongers.util.StringUtil;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.logging.SLF4JLogDelegateFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.vertx.core.logging.LoggerFactory.LOGGER_DELEGATE_FACTORY_CLASS_NAME;
import static java.lang.System.setProperty;

public class Application {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(LoggerFactory.class); // Required for Logback to work in Vertx

  private static TwitterClient twitterClient;

  static void checkForUpdates() {
    System.out.println("Let's check for bongers...");
    LOGGER.info("Let's check for bongers...");

    List<String> tweets = new ArrayList<>();
    LastFMService lastFMService = new LastFMService(Config.getInstance().getLastFmApi());
    for (Map.Entry<String, String> user : LastFMUsernames.getInstance().getUsernames().entrySet()) {
      try {
        Track latestTrack = lastFMService.getLatestTrack(user.getKey());
        LOGGER.info("Track found: " + latestTrack);
        if (LastTracksPlayed.getInstance().getTrackTimes().containsKey(user.getKey())) {
          Integer time = LastTracksPlayed.getInstance().getTrackTimes().get(user.getKey());
          if (time < latestTrack.getTime()) {
            String tweet = StringUtil.buildTweet(user, latestTrack);
            tweets.add(tweet);
            LOGGER.info("Adding tweet: " + tweet);
            LastTracksPlayed.getInstance()
                .getTrackTimes()
                .put(user.getKey(), latestTrack.getTime());
          }
        } else {
          LastTracksPlayed.getInstance().getTrackTimes().put(user.getKey(), latestTrack.getTime());
        }

      } catch (Exception e) {
        LOGGER.error(e);
        e.printStackTrace();
      }
    }
    for (String tweet : tweets) {
      new TwitterService(twitterClient).sendTweet(tweet);
    }
  }

  static void keepServerAlive() {
    PingService pingService = new PingService();
    try {
      pingService.keepServiceAline();
    } catch (URISyntaxException | IOException | InterruptedException e) {
      LOGGER.error("Server couldn't be pinged: " + e);
    }
  }

  public static void main(String[] args) {
    System.err.println("Starting...");
    System.out.println("Starting...");
    VertxOptions vertxOptions =
        new VertxOptions().setBlockedThreadCheckInterval(1000 * 60 * 2); // Two minutes...
    Vertx vertx = Vertx.vertx(vertxOptions);

    setProperty(LOGGER_DELEGATE_FACTORY_CLASS_NAME, SLF4JLogDelegateFactory.class.getName());

    LOGGER.info("Deploying verticle...");
    vertx.deployVerticle("fm.bongers.verticle.MainVerticle");
    LOGGER.info("Deployed verticle...");

    twitterClient = ConnectService.connectTwitter();
    vertx.setPeriodic(1000 * 60 * 4, (l) -> checkForUpdates()); // Four minutes...

    vertx.setPeriodic(1000 * 60 * 15, (l) -> keepServerAlive()); // 30 minutes
  }
}
