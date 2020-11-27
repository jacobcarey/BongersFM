package fm.bongers;

public class SquadBongers {

//	private Twitter twitter;
//	private Map<String, Track> trackMap;
//	private LastFMUsernames v;
//
//	public SquadBongers() {
//		twitter = ConnectService.connectTwitter();
//		trackMap = getLatestTracks();
//		v = new LastFMUsernames();
//	}
//
//
//
//	public List<StatusUpdate> checkUpdates() {
//		Map<String, Track> newTrackMap = getLatestTracks();
//		List<StatusUpdate> updates = new ArrayList<StatusUpdate>();
//
//		for (String user : trackMap.keySet()) {
//			Track track = newTrackMap.get(user);
//			if (trackMap.get(user).getPlayedWhen()
//					.before(track.getPlayedWhen())) {
//				updates.add(new StatusUpdate(LastFMUsernames.USERNAMES.get(user)
//						+ " played: " + track.getName() + " - "
//						+ track.getArtist() + ", for the "
//						+ ordinal(getPlayCount(track, user)) + " + time."));
//			}
//		}
//		setTrackMap(newTrackMap);
//		return updates;
//	}
//
//	public void tweetUpdates(List<StatusUpdate> updates) {
//		try {
//			for (StatusUpdate status : updates) {
//				twitter.updateStatus(status);
//			}
//		} catch (Exception e) {
//			// Error.
//		}
//	}
//
//	public static String ordinal(int i) {
//		String[] sufixes = new String[]{"th", "st", "nd", "rd", "th", "th",
//				"th", "th", "th", "th"};
//		switch (i % 100) {
//			case 11 :
//			case 12 :
//			case 13 :
//				return i + "th";
//			default :
//				return i + sufixes[i % 10];
//
//		}
//	}
//
//	public Map<String, Track> getTrackMap() {
//		return trackMap;
//	}
//
//	public void setTrackMap(Map<String, Track> trackMap) {
//		this.trackMap = trackMap;
//	}
//
//	public Twitter getTwitter() {
//		return twitter;
//	}
}
