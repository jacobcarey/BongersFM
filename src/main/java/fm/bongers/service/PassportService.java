package fm.bongers.service;

import com.twitter.clientlib.ApiException;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class PassportService {

  public static final String URL =
      "https://www.passportappointment.service.gov.uk/outreach/publicbooking.ofml";

  public static final String NO_APPOINTMENT = "Sorry, there are no available appointments";
  public static final String BUSY =
      "Sorry, we're experiencing high demand for this service at the moment and the system is busy."
          + " Please try again later.";

  private static final Logger LOGGER =
      LoggerFactory.getLogger(LoggerFactory.class); // Required for Logback to work in Vertx

  public static void checkForAppointments(TwitterService twitterService) {
    try {
      LOGGER.info("Checking for appointments.");
      Document passportGov = Jsoup.connect(URL).get();
      boolean unavailable =
          StringUtils.containsIgnoreCase(passportGov.body().text(), NO_APPOINTMENT)
              || StringUtils.containsIgnoreCase(passportGov.body().text(), BUSY);
      if (!unavailable) {
        LOGGER.info("Appointments available!");
        sendAppointmentsAvailableTweet(twitterService);
      } else {
        LOGGER.info("Appointments unavailable!");
        Element mainBody = passportGov.body().getElementsByTag("body").first();
        if (mainBody != null) {
          LOGGER.info("Unavailable page: " + mainBody.text());
        }
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

    //    twitterService.sendTweet(
    //        "@alexgoesfishing @jackbates6277 Appointments are available! " + timestamp);

    //    twitterService.sendTweet(
    //        "@JacobCarey @jackbates6277 @Jamestmf @RyanBaines96 @Shauno_95 Tickets are available,
    // check RA! "
    //            + timestamp);

    //    @JacobCarey @alexgoesfishing @anantarctic @bethrshipley @ellwilson @jackbates6277
    // @Jamestmf @RyanBaines96 @Shauno_95
  }
}
