package fm.bongers.service;

import de.umass.lastfm.Track;
import de.umass.lastfm.User;

import java.net.http.HttpRequest;
import java.util.Locale;
import java.util.Optional;

public class LastFMService {

    private final String apiKey;

    private String GET_RECENT_TRACK = "https://ws.audioscrobbler.com/2.0/?method=user.getrecenttracks&user=%s&api_key=%s&format=json";

    public LastFMService(String apiKey) {
        this.apiKey = apiKey;
    }

    public Track getLatestTrack(String user) {
//        https://ws.audioscrobbler.com/2.0/?method=user.getrecenttracks&user=JOC01&api_key=ef81ef22ff44ef8b4f8e251808d3927a&format=json
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://httpbin.org/get"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        Optional<Track> track = User.getRecentTracks(user, apiKey).getPageResults().stream().findFirst();
        return track.orElse(null);
    }

    public int getPlayCount(Track track, String user) {
        return Track.getInfo(track.getArtist(), track.getName(), Locale.UK, user,
                apiKey).getUserPlaycount();
    }
}
