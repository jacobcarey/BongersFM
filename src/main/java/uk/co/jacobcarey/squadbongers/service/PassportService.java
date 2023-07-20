package uk.co.jacobcarey.squadbongers.service;

import com.twitter.clientlib.ApiException;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PassportService {

  public static final String URL =
          "https://www.passportappointment.service.gov.uk/outreach/publicbooking.ofml";

  public static final String NO_APPOINTMENT = "Sorry, there are no available appointments";
  public static final String BUSY =
          "Sorry, we're experiencing high demand for this service at the moment and the system is busy."
                  + " Please try again later.";

  private static final Logger LOGGER = LoggerFactory.getLogger(PassportService.class);

  private final TwitterService twitterService;

  @Autowired
  public PassportService(TwitterService twitterService) {
    this.twitterService = twitterService;
  }

  //  @Scheduled(fixedRate = 1000 * 60 * 15)
  public void checkForAppointments() {
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
          LOGGER.info("Unavailable page: {}", mainBody.text());
        }
      }

    } catch (IOException | ApiException e) {
      e.printStackTrace();
    }
  }

  private void sendAppointmentsAvailableTweet(TwitterService twitterService) throws ApiException {
    LOGGER.info("Appointments available.");
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    String timestamp = dtf.format(now);

    twitterService.sendTweet("@JacobCarey Appointments are available! " + timestamp);
  }
}
