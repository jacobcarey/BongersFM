package fm.bongers.service;

import com.twitter.clientlib.ApiException;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ElementBasedTicketsService {


      LoggerFactory.getLogger(ElementBasedTicketsService.class); // Required for Logback to work in Vertx


  public static void checkForTickets(TwitterService twitterService) {
    try {
      LOGGER.info("Checking for tickets.");
      Document residentAdvisor =
          Jsoup.connect(URL)
              .header(
                  "Accept",
                  "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
              .header("Accept-Encoding", "gzip, deflate, br")
              .header("Accept-Language", "en-GB,en-US;q=0.9,en;q=0.8")
              .header("User-Agent", USER_AGENT)
              .timeout(5000)
              .get();
      if (residentAdvisor != null) {
        Elements ticketsOnSale = residentAdvisor.getElementsByClass("onsale");
        if (ticketsOnSale != null && ticketsOnSale.size() > TICKETS_ON_SALE_CURRENTLY) {
          sendTicketsAvailableTweet(twitterService);
          logTicketsFound(ticketsOnSale);
        } else {
          LOGGER.info("Ticket types available has not gone above {}.", TICKETS_ON_SALE_CURRENTLY);
          if (ticketsOnSale != null && ticketsOnSale.size() > 0) {
            for (org.jsoup.nodes.Element element : ticketsOnSale) {
              if (element.toString() != null
                  && element.toString().toUpperCase().contains(TICKET_TYPE.toUpperCase())) {
                sendTicketsAvailableTweet(twitterService);
                logTicketsFound(ticketsOnSale);
              } else {
                LOGGER.info("Ticket type is not '" + TICKET_TYPE + "'.");
              }
            }
          }
        }
      }

    } catch (IOException | URISyntaxException | ApiException e) {
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

  private static void sendTicketsAvailableTweet(TwitterService twitterService)
      throws URISyntaxException, IOException, ApiException {
    LOGGER.info("Tickets available.");
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    String timestamp = dtf.format(now);

    twitterService.sendTweet("@JacobCarey @Jamestmf are available, check RA! " + timestamp);

    //    twitterService.sendTweet(
    //        "@JacobCarey @jackbates6277 @Jamestmf @RyanBaines96 @Shauno_95 Tickets are available,
    // check RA! "
    //            + timestamp);

    //    @JacobCarey @alexgoesfishing @anantarctic @bethrshipley @ellwilson @jackbates6277
    // @Jamestmf @RyanBaines96 @Shauno_95
  }
}
