package fm.bongers.infrastructure;

public final class Config {

  private static fm.bongers.infrastructure.Config INSTANCE;
  private String lastFmApi;
  private String twitterApiKey;
  private String twitterSecretAccessKey;
  private String twitterAccessToken;
  private String twitterAccessTokenSecret;

  private Config() {}

  public static fm.bongers.infrastructure.Config getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new fm.bongers.infrastructure.Config();
      INSTANCE.setLastFmApi(System.getenv("LASTFM_API"));
      INSTANCE.setTwitterAccessToken(System.getenv("T_ACCESS_TOKEN"));
      INSTANCE.setTwitterAccessTokenSecret(System.getenv("T_ACCESS_TOKEN_SECRET"));
      INSTANCE.setTwitterApiKey(System.getenv("T_API_KEY"));
      INSTANCE.setTwitterSecretAccessKey(System.getenv("T_SECRET_ACCESS_KEY"));
    }
    return INSTANCE;
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

  public String getTwitterSecretAccessKey() {
    return twitterSecretAccessKey;
  }

  public void setTwitterSecretAccessKey(String twitterSecretAccessKey) {
    this.twitterSecretAccessKey = twitterSecretAccessKey;
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
}
