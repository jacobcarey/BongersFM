package c.jacob;

import twitter4j.StatusUpdate;
import twitter4j.TwitterException;

public class LFJob implements Runnable {

	// @Override
	public void run() {
		try {
			BackgroundJobManager.squadBongers.getTwitter().updateStatus(
					new StatusUpdate("Test FLJOB" + Math.random()));
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}