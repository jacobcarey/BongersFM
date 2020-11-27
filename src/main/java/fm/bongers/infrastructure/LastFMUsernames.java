package fm.bongers.infrastructure;

import java.util.Map;

public final class LastFMUsernames {

  private static LastFMUsernames INSTANCE;
  private Map<String, String> usernames;

  private LastFMUsernames() {}

  public static LastFMUsernames getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new LastFMUsernames();
    }
    return INSTANCE;
  }

  public Map<String, String> getUsernames() {
    return usernames;
  }

  public void setUsernames(Map<String, String> usernames) {
    this.usernames = usernames;
  }

  //  static {
  //    USERNAMES = new HashMap<String, String>();
  //    USERNAMES.put("JOC01", "Jacob");
  //    USERNAMES.put("hazzamc", "Harry");
  //    USERNAMES.put("Jackbates96", "Bates");
  //    USERNAMES.put("BRS01", "Bethany");
  //    USERNAMES.put("ellwilson", "El");
  //    USERNAMES.put("JackJackCullen", "Cullen");
  //    USERNAMES.put("Razzabee", "RazB");
  //  }
}
