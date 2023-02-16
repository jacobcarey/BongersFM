package uk.co.jacobcarey.squadbongers.service;

import com.twitter.clientlib.ApiException;
import com.twitter.clientlib.TwitterCredentials;
import com.twitter.clientlib.api.TwitterApi;
import com.twitter.clientlib.model.CreateTweetRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uk.co.jacobcarey.squadbongers.infrastructure.Config;

@Service
public class TwitterService {

  private static final Logger LOGGER = LoggerFactory.getLogger(TwitterService.class);

  TwitterApi twitterApi;

  public TwitterService() {

    LOGGER.info("Creating Twitter client.");
    TwitterCredentials twitterCredentials = new TwitterCredentials();
    twitterCredentials.setTwitterToken(Config.getInstance().getTwitterAccessToken());
    twitterCredentials.setTwitterTokenSecret(Config.getInstance().getTwitterAccessTokenSecret());

    twitterCredentials.setTwitterConsumerKey(Config.getInstance().getTwitterApiKey());
    twitterCredentials.setTwitterConsumerSecret(Config.getInstance().getTwitterApiKeySecret());

    twitterCredentials.setBearerToken(Config.getInstance().getTwitterBearerToken());

    twitterApi = new TwitterApi(twitterCredentials);
  }

  public void sendTweet(String text) throws ApiException {
    CreateTweetRequest createTweetRequest = new CreateTweetRequest().text(text);

    twitterApi.createTweet(createTweetRequest);
  }
}
