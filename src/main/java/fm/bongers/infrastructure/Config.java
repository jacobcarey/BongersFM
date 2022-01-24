package fm.bongers.infrastructure;

import java.util.Map;

public final class Config {

  private static fm.bongers.infrastructure.Config INSTANCE;
  private String lastFmApi;
  private String twitterApiKey;
  private String twitterApiKeySecret;
  private String twitterAccessToken;
  private String twitterAccessTokenSecret;
  private String twitterBearerToken;
  private String port;

  private Config() {}

  public static fm.bongers.infrastructure.Config getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new fm.bongers.infrastructure.Config();
      Map<String, String> env = System.getenv();
      INSTANCE.setLastFmApi(env.getOrDefault("LASTFM_API", "x"));
      INSTANCE.setTwitterAccessToken(env.getOrDefault("T_ACCESS_TOKEN", "x"));
      INSTANCE.setTwitterAccessTokenSecret(env.getOrDefault("T_ACCESS_TOKEN_SECRET", "x"));
      INSTANCE.setTwitterApiKey(env.getOrDefault("T_API_KEY", "x"));
      INSTANCE.setTwitterApiKeySecret(env.getOrDefault("T_SECRET_ACCESS_KEY", "x"));
      INSTANCE.setPort(env.getOrDefault("PORT", env.getOrDefault("$PORT", "8080")));
      INSTANCE.setTwitterBearerToken(env.getOrDefault("T_BEARER", "x"));
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
