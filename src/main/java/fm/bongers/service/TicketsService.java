package fm.bongers.service;

import com.github.redouane59.twitter.TwitterClient;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TicketsService {

  public static final String URL = "https://ra.co/widget/event/1326673/embedtickets";
  public static final int TICKETS_ON_SALE_CURRENTLY = 2;
  private static final Logger LOGGER =
      LoggerFactory.getLogger(LoggerFactory.class); // Required for Logback to work in Vertx

  public static void checkForTickets(TwitterClient twitterClient) {
    try {
      TwitterService twitterService = new TwitterService(twitterClient);
      Document residentAdvisor = Jsoup.connect(URL).get();
      if (residentAdvisor != null) {
        Elements ticketsOnSale = residentAdvisor.getElementsByClass("onsale");
        if (ticketsOnSale != null && ticketsOnSale.size() > TICKETS_ON_SALE_CURRENTLY) {
          LOGGER.info("Tickets available.");
          DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
          LocalDateTime now = LocalDateTime.now();
          String timestamp = dtf.format(now);
          twitterService.sendTweet(
              "@JacobCarey @Jamestmf @RyanBaines96 Tickets are available, check RA! " + timestamp);
        } else {
          LOGGER.info("Tickets not available.");
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
