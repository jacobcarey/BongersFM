package uk.co.jacobcarey.squadbongers.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class PingService {

  private static final Logger LOGGER = LoggerFactory.getLogger(PingService.class);

  @Scheduled(initialDelay = 1000 * 60 * 5, fixedRate = 1000 * 60 * 15)
  public void keepServiceAline() throws URISyntaxException, IOException, InterruptedException {
    LOGGER.info("Pinging service to check if alive.");
    HttpClient httpClient = HttpClient.newHttpClient();
    HttpRequest trackInfoRequest =
            HttpRequest.newBuilder().GET().uri(new URI("https://bongers-fm.herokuapp.com/")).build();

    HttpResponse<String> response =
            httpClient.send(trackInfoRequest, HttpResponse.BodyHandlers.ofString());
    LOGGER.info("Service status: {}", response.statusCode());
  }
}
