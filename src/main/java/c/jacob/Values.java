package c.jacob;
import java.util.HashMap;
import java.util.Map;

public class Values {
	private final String T_API_KEY = "eWLl9IJEQ37VRMsRKUoLWt1MI";
	private final String T_CONSUMER_SECRET = "CFc4TXHsEYDZnNhyaINKX4g0ZRcHLNnWl8tAWY4Oij0N1j6row";
	private final String T_TOKEN = "716326189632249856-eeXKmBzvMCInUEaGkdwA9DqO8qUuKw0";
	private final String T_TOKEN_SECRET = "EpzWF3i8YTk4eOorEWuknCvpAwlEMXKGFMAzJVvk6ipcO";

	private final String L_API_KEY = "67b399a1da6654ae75273102edc9cf69";
	private final String L_CONSUMER_SECRET = "81848b97d64c413c65f8a28bed3889a0";
	private final String L_USERNAME = "Username";
	private final String L_PASSWORD = "Password";
	
	public static final Map<String, String> USERNAMES;
	static {
		USERNAMES = new HashMap<String, String>();
		USERNAMES.put("JOC01", "Jacob");
		USERNAMES.put("hazzamc", "Harry");
		USERNAMES.put("Jackbates96", "Bates");
		USERNAMES.put("BRS01", "Bethany");
		USERNAMES.put("ellwilson", "El");
		USERNAMES.put("JackJackCullen", "Cullen");
		USERNAMES.put("Razzabee", "RazB");

	}

	public String getT_API_KEY() {
		return T_API_KEY;
	}
	public String getT_CONSUMER_SECRET() {
		return T_CONSUMER_SECRET;
	}
	public String getT_TOKEN() {
		return T_TOKEN;
	}
	public String getT_TOKEN_SECRET() {
		return T_TOKEN_SECRET;
	}
	public String getL_API_KEY() {
		return L_API_KEY;
	}
	public String getL_CONSUMER_SECRET() {
		return L_CONSUMER_SECRET;
	}
	public String getL_USERNAME() {
		return L_USERNAME;
	}
	public String getL_PASSWORD() {
		return L_PASSWORD;
	}
	public static Map<String, String> getUsernames() {
		return USERNAMES;
	}
}
