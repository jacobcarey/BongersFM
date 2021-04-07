package fm.bongers.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PingService {

  public void keepServiceAline() throws URISyntaxException, IOException, InterruptedException {
    System.out.println("Pinging service to check if aline...");
    HttpClient httpClient = HttpClient.newHttpClient();
    HttpRequest trackInfoRequest =
        HttpRequest.newBuilder().GET().uri(new URI("https://bongers-fm.herokuapp.com/")).build();

    HttpResponse<String> response =
        httpClient.send(trackInfoRequest, HttpResponse.BodyHandlers.ofString());
    System.out.println("Service status: " + response.statusCode());
  }
}
