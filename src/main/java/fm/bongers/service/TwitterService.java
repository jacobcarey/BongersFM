package fm.bongers.service;

import com.twitter.clientlib.ApiException;
import com.twitter.clientlib.TwitterCredentials;
import com.twitter.clientlib.api.TwitterApi;
import com.twitter.clientlib.model.CreateTweetRequest;
import fm.bongers.infrastructure.Config;
import io.github.redouane59.twitter.TwitterClient;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.util.Objects;

public class TwitterService {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(LoggerFactory.class); // Required for Logback to work in Vertx

  TwitterClient v1;
  TwitterApi v2;

  public TwitterService() {

    if (Objects.equals(Config.getInstance().getTwitterAccessToken(), "1")) {

      new TwitterClient(
          io.github.redouane59.twitter.signature.TwitterCredentials.builder()
              .accessToken(Config.getInstance().getTwitterAccessToken())
              .accessTokenSecret(Config.getInstance().getTwitterAccessTokenSecret())
              .apiKey(Config.getInstance().getTwitterApiKey())
              .apiSecretKey(Config.getInstance().getTwitterApiKeySecret())
              .build());
    } else {
      LOGGER.info("Loading config from static values.");
      TwitterCredentials twitterCredentials = new TwitterCredentials();
      twitterCredentials.setTwitterToken(Config.getInstance().getTwitterAccessToken());
      twitterCredentials.setTwitterTokenSecret(Config.getInstance().getTwitterAccessTokenSecret());

      twitterCredentials.setTwitterConsumerKey(Config.getInstance().getTwitterApiKey());
      twitterCredentials.setTwitterConsumerSecret(Config.getInstance().getTwitterApiKeySecret());

      twitterCredentials.setBearerToken(Config.getInstance().getTwitterBearerToken());

      v2 = new TwitterApi(twitterCredentials);
    }
  }

  public void sendTweet(String text) throws ApiException {

    if (Objects.equals(Config.getInstance().getTwitterAccessToken(), "1")) {
      sendTweetV1(text);
    } else {
      sendTweetV2(text);
    }
  }

  private void sendTweetV1(String text) {

    v1.postTweet(text);
  }

  private void sendTweetV2(String text) throws ApiException {

    CreateTweetRequest createTweetRequest = new CreateTweetRequest().text(text);

    v2.createTweet(createTweetRequest);
  }
}
