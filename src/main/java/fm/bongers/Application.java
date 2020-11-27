package fm.bongers;

import de.umass.lastfm.Track;
import fm.bongers.service.LastFMService;

import java.util.Locale;

public class Application {
  public static void main(String[] args){

    String api = "ef81ef22ff44ef8b4f8e251808d3927a";
    System.out.println("Hello World");
    LastFMService lastFMService = new LastFMService("ef81ef22ff44ef8b4f8e251808d3927a");
    Track track = lastFMService.getLatestTrack("JOC01");
    System.out.println(track.getArtist());

    int userPlaycount = Track.getInfo(track.getArtist(), track.getName(), Locale.UK, "JOC01",
            "ef81ef22ff44ef8b4f8e251808d3927a").getUserPlaycount();
    System.out.println(userPlaycount);
  }
}
