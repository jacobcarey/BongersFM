package fm.bongers.service;

import com.twitter.clientlib.ApiException;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PassportService {

  public static final String URL =
      "https://www.passportappointment.service.gov.uk/messages/AppointmentsAvailability.html";

  public static final String NO_APPOINTMENT = "Sorry, there are no available appointments";

  private static final Logger LOGGER =
      LoggerFactory.getLogger(LoggerFactory.class); // Required for Logback to work in Vertx

  public static void checkForAppointments(TwitterService twitterService) {
    try {
      LOGGER.info("Checking for appointments.");
      Document passportGov = Jsoup.connect(URL).get();
      if (passportGov != null
          && !StringUtils.containsIgnoreCase(passportGov.body().text(), NO_APPOINTMENT)) {
        LOGGER.info("Appointments available!");
        sendAppointmentsAvailableTweet(twitterService);
      } else {
        LOGGER.info("Appointments unavailable!");
      }

    } catch (IOException | URISyntaxException | ApiException e) {
      e.printStackTrace();
    }
  }

  private static void sendAppointmentsAvailableTweet(TwitterService twitterService)
      throws URISyntaxException, IOException, ApiException {
    LOGGER.info("Appointments available.");
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    String timestamp = dtf.format(now);

    twitterService.sendTweet("@JacobCarey Appointments are available! " + timestamp);
    twitterService.sendTweet(
        "@alexgoesfishing @jackbates6277 Appointments are available! " + timestamp);

    //    twitterService.sendTweet(
    //        "@JacobCarey @jackbates6277 @Jamestmf @RyanBaines96 @Shauno_95 Tickets are available,
    // check RA! "
    //            + timestamp);

    //    @JacobCarey @alexgoesfishing @anantarctic @bethrshipley @ellwilson @jackbates6277
    // @Jamestmf @RyanBaines96 @Shauno_95
  }
}
