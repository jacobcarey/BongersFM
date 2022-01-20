package fm.bongers.service;

import fm.bongers.infrastructure.Config;
import io.github.redouane59.twitter.TwitterClient;
import io.github.redouane59.twitter.signature.TwitterCredentials;

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
