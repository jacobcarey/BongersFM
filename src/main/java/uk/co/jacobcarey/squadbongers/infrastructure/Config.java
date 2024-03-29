package uk.co.jacobcarey.squadbongers.infrastructure;

import java.util.Map;

public final class Config {

    private static uk.co.jacobcarey.squadbongers.infrastructure.Config INSTANCE;
    private String lastFmApi;
    private String twitterApiKey;
    private String twitterApiKeySecret;
    private String twitterAccessToken;
    private String twitterAccessTokenSecret;
    private String twitterBearerToken;
    private String port;

    private Config() {
    }

    public static uk.co.jacobcarey.squadbongers.infrastructure.Config getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new uk.co.jacobcarey.squadbongers.infrastructure.Config();
            Map<String, String> env = System.getenv();
            INSTANCE.setLastFmApi(env.getOrDefault("LAST_FM_KEY", "x"));
            INSTANCE.setTwitterAccessToken(env.getOrDefault("TWITTER_TOKEN", "x"));
            INSTANCE.setTwitterAccessTokenSecret(env.getOrDefault("TWITTER_TOKEN_SECRET", "x"));

            INSTANCE.setTwitterApiKey(env.getOrDefault("TWITTER_CONSUMER_KEY", "x"));
            INSTANCE.setTwitterApiKeySecret(env.getOrDefault("TWITTER_CONSUMER_SECRET", "x"));

            INSTANCE.setPort(env.getOrDefault("PORT", env.getOrDefault("PORT", "8080")));
            INSTANCE.setTwitterBearerToken(env.getOrDefault("TWITTER_BEARER_TOKEN", "x"));
        }
        return INSTANCE;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getLastFmApi() {
        return lastFmApi;
    }

    public void setLastFmApi(String lastFmApi) {
        this.lastFmApi = lastFmApi;
    }

    public String getTwitterApiKey() {
        return twitterApiKey;
    }

    public void setTwitterApiKey(String twitterApiKey) {
        this.twitterApiKey = twitterApiKey;
    }

    public String getTwitterApiKeySecret() {
        return twitterApiKeySecret;
    }

    public void setTwitterApiKeySecret(String twitterApiKeySecret) {
        this.twitterApiKeySecret = twitterApiKeySecret;
    }

    public String getTwitterAccessToken() {
        return twitterAccessToken;
    }

    public void setTwitterAccessToken(String twitterAccessToken) {
        this.twitterAccessToken = twitterAccessToken;
    }

    public String getTwitterAccessTokenSecret() {
        return twitterAccessTokenSecret;
    }

    public void setTwitterAccessTokenSecret(String twitterAccessTokenSecret) {
        this.twitterAccessTokenSecret = twitterAccessTokenSecret;
    }

    public String getTwitterBearerToken() {
        return twitterBearerToken;
    }

    public void setTwitterBearerToken(String twitterBearerToken) {
        this.twitterBearerToken = twitterBearerToken;
    }
}
