package fm.bongers.service;

import com.github.redouane59.twitter.TwitterClient;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TicketsService {

  public static final String URL = "https://ra.co/widget/event/1326673/embedtickets";
  public static final int TICKETS_ON_SALE_CURRENTLY = 2;
  public static final String GENERAL_ADMISSION_WEEKEND = "General Admission Weekend";

  private static final Logger LOGGER =
      LoggerFactory.getLogger(LoggerFactory.class); // Required for Logback to work in Vertx

  public static void checkForTickets(TwitterClient twitterClient) {
    try {
      LOGGER.info("Checking for tickets.");
      TwitterService twitterService = new TwitterService(twitterClient);
      Document residentAdvisor = Jsoup.connect(URL).get();
      if (residentAdvisor != null) {
        Elements ticketsOnSale = residentAdvisor.getElementsByClass("onsale");
        if (ticketsOnSale != null && ticketsOnSale.size() > TICKETS_ON_SALE_CURRENTLY) {
          sendTicketsAvailableTweet(twitterService);
          logTicketsFound(ticketsOnSale);
        } else {
          LOGGER.info("Ticket types available has not gone above 2.");
          if (ticketsOnSale != null && ticketsOnSale.size() > 0) {
            for (org.jsoup.nodes.Element element : ticketsOnSale) {
              if (element.toString() != null
                  && element
                      .toString()
                      .toUpperCase()
                      .contains(GENERAL_ADMISSION_WEEKEND.toUpperCase())) {
                sendTicketsAvailableTweet(twitterService);
                logTicketsFound(ticketsOnSale);
              } else {
                LOGGER.info("Ticket type is not '" + GENERAL_ADMISSION_WEEKEND + "'.");
              }
            }
          }
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void logTicketsFound(Elements ticketsOnSale) {
    LOGGER.info("Tickets found:");
    for (Element element : ticketsOnSale) {
      if (element.hasText()) {
        LOGGER.info("Ticket: " + element.text());
      }
    }
    LOGGER.info("Tickets found end.");
  }

  private static void sendTicketsAvailableTweet(TwitterService twitterService) {
    LOGGER.info("Tickets available.");
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    String timestamp = dtf.format(now);
    twitterService.sendTweet(
        "@JacobCarey @Jamestmf @RyanBaines96 Tickets are available, check RA! " + timestamp);
  }
}
