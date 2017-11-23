package c.jacob;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.umass.lastfm.Track;
import de.umass.lastfm.User;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;

public class SquadBongers {

	private Twitter twitter;
	private Map<String, Track> trackMap;
	private Values v;

	public SquadBongers() {
		twitter = Connect.connectTwitter();
		trackMap = lastestTracks();
		v = new Values();
	}

	public Map<String, Track> lastestTracks() {
		Map<String, Track> trackMap = new HashMap<String, Track>();
		for (String user : Values.USERNAMES.keySet()) {

			Track track = (Track) ((List<Track>) User
					.getRecentTracks(user, 1, 2, v.getL_API_KEY())
					.getPageResults()).get(1);

			trackMap.put(user, track);
		}

		return trackMap;
	}

	public int playcount(Track t, String user) {
		return Track.getInfo(t.getArtist(), t.getName(), Locale.UK, user,
				v.getL_API_KEY()).getUserPlaycount();
	}

	public List<StatusUpdate> checkUpdates() {
		Map<String, Track> newTrackMap = lastestTracks();
		List<StatusUpdate> updates = new ArrayList<StatusUpdate>();

		for (String user : trackMap.keySet()) {
			Track track = newTrackMap.get(user);
			if (trackMap.get(user).getPlayedWhen()
					.before(track.getPlayedWhen())) {
				updates.add(new StatusUpdate(Values.USERNAMES.get(user)
						+ " played: " + track.getName() + " - "
						+ track.getArtist() + ", for the "
						+ ordinal(playcount(track, user)) + " + time."));
			}
		}
		setTrackMap(newTrackMap);
		return updates;
	}

	public void tweetUpdates(List<StatusUpdate> updates) {
		try {
			for (StatusUpdate status : updates) {
				twitter.updateStatus(status);
			}
		} catch (Exception e) {
			// Error.
		}
	}

	public static String ordinal(int i) {
		String[] sufixes = new String[]{"th", "st", "nd", "rd", "th", "th",
				"th", "th", "th", "th"};
		switch (i % 100) {
			case 11 :
			case 12 :
			case 13 :
				return i + "th";
			default :
				return i + sufixes[i % 10];

		}
	}
	
	public Map<String, Track> getTrackMap() {
		return trackMap;
	}

	public void setTrackMap(Map<String, Track> trackMap) {
		this.trackMap = trackMap;
	}

	public Twitter getTwitter() {
		return twitter;
	}
}
