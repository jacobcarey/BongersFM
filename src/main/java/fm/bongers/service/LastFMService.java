package fm.bongers.service;

import fm.bongers.model.Track;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LastFMService {

  private static final int FIRST = 1;
  private static final String LAST_FM_API = "https://ws.audioscrobbler.com/2.0/";
  private final String apiKey;
  private final HttpClient httpClient;

  public LastFMService(String apiKey) {
    this.apiKey = apiKey;
    this.httpClient = HttpClient.newHttpClient();
  }

  public Track getLatestTrack(String user)
      throws URISyntaxException, IOException, InterruptedException, NullPointerException,
          ClassCastException {
    Track track = new Track();

    URIBuilder recentTrackURI = buildBaseURI("user.getrecenttracks", user);

    HttpResponse<String> recentTrackResponse = sendRequest(recentTrackURI);

    extractLatestTrack(track, recentTrackResponse);

    URIBuilder trackInfoURI = buildBaseURI("track.getInfo", user);
    trackInfoURI.addParameter("track", track.getName());
    trackInfoURI.addParameter("artist", track.getArtist());

    HttpResponse<String> trackInfoResponse = sendRequest(trackInfoURI);

    extractPlayCount(track, trackInfoResponse);

    return track;
  }

  private void extractPlayCount(Track track, HttpResponse<String> trackInfoResponse) {
    if (trackInfoResponse.statusCode() == HttpURLConnection.HTTP_OK) {
      JsonObject jsonObject = new JsonObject(trackInfoResponse.body());
      JsonObject trackJson = jsonObject.getJsonObject("track");
      String userPlayCount = trackJson.getString("userplaycount");
      track.setPlayCount(userPlayCount);
    }
  }

  // Will not pull the "Now Playing" track.
  private void extractLatestTrack(Track track, HttpResponse<String> recentTrackResponse) {
    if (recentTrackResponse.statusCode() == HttpURLConnection.HTTP_OK) {
      JsonObject jsonObject = new JsonObject(recentTrackResponse.body());
      JsonObject recentTracks = jsonObject.getJsonObject("recenttracks");
      JsonArray tracks = recentTracks.getJsonArray("track");
      if (!tracks.isEmpty()) {
        JsonObject firstTrack = tracks.getJsonObject(FIRST);
        String artist = firstTrack.getJsonObject("artist").getString("#text");
        String time = firstTrack.getJsonObject("date").getString("uts");
        String name = firstTrack.getString("name");
        track.setArtist(artist);
        track.setName(name);
        track.setTime(Integer.parseInt(time));
      }
    }
  }

  private URIBuilder buildBaseURI(String method, String user) throws URISyntaxException {
    URIBuilder recentTrackURI = new URIBuilder(LAST_FM_API);
    recentTrackURI.addParameter("method", method);
    recentTrackURI.addParameter("user", user);
    recentTrackURI.addParameter("api_key", apiKey);
    recentTrackURI.addParameter("format", "json");
    return recentTrackURI;
  }

  private HttpResponse<String> sendRequest(URIBuilder recentTrackURI)
      throws URISyntaxException, IOException, InterruptedException {
    HttpRequest trackInfoRequest =
        HttpRequest.newBuilder().GET().uri(recentTrackURI.build()).build();

    return httpClient.send(trackInfoRequest, HttpResponse.BodyHandlers.ofString());
  }
}
