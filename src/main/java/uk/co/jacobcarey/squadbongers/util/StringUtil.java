package uk.co.jacobcarey.squadbongers.util;

import uk.co.jacobcarey.squadbongers.model.Track;

import java.util.Map;

public class StringUtil {

    private StringUtil() {
    }

    public static String ordinal(int i) {
        int mod100 = i % 100;
        int mod10 = i % 10;
        if (mod10 == 1 && mod100 != 11) {
            return i + "st";
        } else if (mod10 == 2 && mod100 != 12) {
            return i + "nd";
        } else if (mod10 == 3 && mod100 != 13) {
            return i + "rd";
        } else {
            return i + "th";
        }
    }

    public static String buildTweet(Map.Entry<String, String> user, Track latestTrack) {
        return user.getValue()
                + " played: "
                + latestTrack.getName()
                + " - "
                + latestTrack.getArtist()
                + " for the "
                + StringUtil.ordinal(Integer.parseInt(latestTrack.getPlayCount()))
                + " time.";
    }
}
