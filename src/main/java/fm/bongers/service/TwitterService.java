package fm.bongers.service;

import io.github.redouane59.twitter.TwitterClient;

public class TwitterService {

  private final TwitterClient twitterClient;

  public TwitterService(TwitterClient twitterClient) {
    this.twitterClient = twitterClient;
  }

  public void sendTweet(String text) {
    twitterClient.postTweet(text);
  }
}
