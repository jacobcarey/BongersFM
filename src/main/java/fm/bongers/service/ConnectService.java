package fm.bongers.service;

import com.github.redouane59.twitter.TwitterClient;
import com.github.redouane59.twitter.signature.TwitterCredentials;

import java.io.File;
import java.io.IOException;

public class ConnectService {

  public TwitterClient connectTwitter() {
    TwitterClient twitterClient = null;
    try {
      twitterClient =
          new TwitterClient(
              TwitterClient.OBJECT_MAPPER.readValue(
                  new File("twitter-config.json"), TwitterCredentials.class));
    } catch (IOException e) {
      e.printStackTrace();
    }

    return twitterClient;
  }

  //  public static Session connectLastFM() {
  //    Session session =
  //        Authenticator.getMobileSession(
  //            v.getL_USERNAME(), v.getL_PASSWORD(), v.getL_API_KEY(), v.getL_CONSUMER_SECRET());
  //
  //    return session;
  //  }
}
