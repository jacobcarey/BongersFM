package fm.bongers.infrastructure;

import java.util.HashMap;
import java.util.Map;

public final class LastFMUsernames {

  private static LastFMUsernames INSTANCE;
  private Map<String, String> usernames;

  private LastFMUsernames() {}

  public static LastFMUsernames getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new LastFMUsernames();
      Map<String, String> users = new HashMap<>();
      users.put("JOC01", "Jacob");
      users.put("hazzamc", "Harry");
      users.put("Jackbates96", "Bates");
      users.put("BRS01", "Beth");
      users.put("ellwilson", "El");
      users.put("JackJackCullen", "Cullen");
      users.put("Razzabee", "RazB");
      users.put("alderson1996", "Aidan");
      users.put("KrisTaylor197", "Kris");
      users.put("Lucywhitham", "Lucy");
      users.put("alexfisher2244", "Al");
      users.put("Pat_McCaff", "Pat");
      users.put("Jamestmf", "Flem");
      users.put("KTaylor121", "Kurt");
      users.put("Mollyjeffs", "Molly");
      INSTANCE.setUsernames(users);
    }
    return INSTANCE;
  }

  public Map<String, String> getUsernames() {
    return usernames;
  }

  public void setUsernames(Map<String, String> usernames) {
    this.usernames = usernames;
  }
}
