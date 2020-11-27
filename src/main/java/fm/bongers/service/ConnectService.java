package fm.bongers.service;

import com.github.redouane59.twitter.TwitterClient;
import com.github.redouane59.twitter.signature.TwitterCredentials;
import fm.bongers.infrastructure.Config;

public class ConnectService {

  public static TwitterClient connectTwitter() {

    return new TwitterClient(
        TwitterCredentials.builder()
            .accessToken(Config.getInstance().getTwitterAccessToken())
            .accessTokenSecret(Config.getInstance().getTwitterAccessTokenSecret())
            .apiKey(Config.getInstance().getTwitterApiKey())
            .apiSecretKey(Config.getInstance().getTwitterSecretAccessKey())
            .build());
  }
}
