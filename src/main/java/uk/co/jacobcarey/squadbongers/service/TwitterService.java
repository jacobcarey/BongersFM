package uk.co.jacobcarey.squadbongers.service;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.twitter.clientlib.ApiException;
import com.twitter.clientlib.TwitterCredentialsOAuth2;
import com.twitter.clientlib.api.TwitterApi;
import com.twitter.clientlib.auth.TwitterOAuth20AppOnlyService;
import com.twitter.clientlib.model.TweetCreateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.jacobcarey.squadbongers.infrastructure.Config;

@Service
public class TwitterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterService.class);

    TwitterApi twitterApi;

    @Autowired
    public TwitterService() {

        LOGGER.info("Creating Twitter client.");


        OAuth2AccessToken accessToken = TwitterService.getAccessToken();
        TwitterCredentialsOAuth2 twitterCredentialsOAuth2 = new TwitterCredentialsOAuth2(
                Config.getInstance().getTwitterApiKey(),
                Config.getInstance().getTwitterApiKeySecret(),
                accessToken.getAccessToken(),
                accessToken.getRefreshToken());

        twitterApi = new TwitterApi(twitterCredentialsOAuth2);
    }

    public static OAuth2AccessToken getAccessToken() {
        TwitterOAuth20AppOnlyService service = new TwitterOAuth20AppOnlyService(
                Config.getInstance().getTwitterApiKey(),
                Config.getInstance().getTwitterApiKeySecret());

        OAuth2AccessToken accessToken = null;
        try {
            accessToken = service.getAccessTokenClientCredentialsGrant();

            System.out.println("Access token: " + accessToken.getAccessToken());
            System.out.println("Token type: " + accessToken.getTokenType());
            System.out.println("Refresh token: " + accessToken.getRefreshToken());
        } catch (Exception e) {
            System.err.println("Error while getting the access token:\n " + e);
            e.printStackTrace();
        }
        return accessToken;
    }


    public void sendTweet(String text) throws ApiException {

        TweetCreateRequest tweetCreateRequest = new TweetCreateRequest();
        tweetCreateRequest.setText(text);
        twitterApi.tweets().createTweet(tweetCreateRequest).execute();
    }
}
