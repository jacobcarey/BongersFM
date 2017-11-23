package c.jacob;
import de.umass.lastfm.Authenticator;
import de.umass.lastfm.Session;
import twitter4j.Twitter;

import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Connect {
	
	private static Values v = new Values();

	public static Twitter connectTwitter() {

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(v.getT_API_KEY())
				.setOAuthConsumerSecret(v.getT_CONSUMER_SECRET())
				.setOAuthAccessToken(v.getT_TOKEN())
				.setOAuthAccessTokenSecret(v.getT_TOKEN_SECRET());
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();

		return twitter;
	}

	public static Session connectLastFM() {
		Session session = Authenticator.getMobileSession(v.getL_USERNAME(),
				v.getL_PASSWORD(), v.getL_API_KEY(),
				v.getL_CONSUMER_SECRET());

		return session;
	}

}