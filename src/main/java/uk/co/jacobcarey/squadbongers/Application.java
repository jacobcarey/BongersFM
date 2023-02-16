package uk.co.jacobcarey.squadbongers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

  private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  //  private static void deployedTweet(TwitterService twitterService) {
  //    try {
  //      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
  //      LocalDateTime now = LocalDateTime.now();
  //      twitterService.sendTweet("@JacobCarey Deployed: " + dtf.format(now));
  //    } catch (ApiException e) {
  //      LOGGER.error(e.getResponseBody(), e);
  //      LOGGER.error("Status code: {}", e.getCode());
  //      LOGGER.error("Reason: {}", e.getResponseBody());
  //      LOGGER.error("Response headers: {}", e.getResponseHeaders());
  //      e.printStackTrace();
  //    }
  //  }
}
