package fm.bongers.service;

import com.twitter.clientlib.ApiException;
import com.twitter.clientlib.TwitterCredentials;
import com.twitter.clientlib.api.TwitterApi;
import com.twitter.clientlib.model.CreateTweetRequest;
import fm.bongers.infrastructure.Config;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class TwitterService {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(LoggerFactory.class); // Required for Logback to work in Vertx

  TwitterApi apiInstance;

  public TwitterService() {

    if (System.getenv("TWITTER_BEARER_TOKEN") == null) {
      LOGGER.info("Loading config from static values.");
      TwitterCredentials twitterCredentials = new TwitterCredentials();
      twitterCredentials.setTwitterToken(Config.getInstance().getTwitterAccessToken());
      twitterCredentials.setTwitterTokenSecret(Config.getInstance().getTwitterAccessTokenSecret());

      twitterCredentials.setTwitterConsumerKey(Config.getInstance().getTwitterApiKey());
      twitterCredentials.setTwitterConsumerSecret(Config.getInstance().getTwitterApiKeySecret());

      twitterCredentials.setBearerToken(Config.getInstance().getTwitterBearerToken());

      apiInstance = new TwitterApi(twitterCredentials);
    } else {
      LOGGER.info("Loading config from environment variables.");

      apiInstance = new TwitterApi();
    }
  }

  public void sendTweet(String text) throws ApiException {

    CreateTweetRequest createTweetRequest = new CreateTweetRequest().text(text);

    apiInstance.createTweet(createTweetRequest);
  }
}
