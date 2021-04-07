package fm.bongers.service;

import com.github.redouane59.twitter.TwitterClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class TicketsService {

  public static final String URL = "https://ra.co/widget/event/1326673/embedtickets";
  public static final int TICKETS_ON_SALE_CURRENTLY = 2;

  public static void checkForTickets(TwitterClient twitterClient) {
    try {
      TwitterService twitterService = new TwitterService(twitterClient);
      Document residentAdvisor = Jsoup.connect(URL).get();
      if (residentAdvisor != null) {
        Elements ticketsOnSale = residentAdvisor.getElementsByClass("onsale");
        if (ticketsOnSale != null && ticketsOnSale.size() > TICKETS_ON_SALE_CURRENTLY) {
          twitterService.sendTweet(
              "@JacobCarey @Jamestmf @RyanBaines96 Tickets are available, check RA!");
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
