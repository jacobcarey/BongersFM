package fm.bongers;

import fm.bongers.service.ConnectService;
import fm.bongers.service.PingService;
import io.github.redouane59.twitter.TwitterClient;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.logging.SLF4JLogDelegateFactory;

import java.io.IOException;
import java.net.URISyntaxException;

import static fm.bongers.service.BongersService.checkForUpdates;
import static io.vertx.core.logging.LoggerFactory.LOGGER_DELEGATE_FACTORY_CLASS_NAME;
import static java.lang.System.setProperty;

public class Application {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(LoggerFactory.class); // Required for Logback to work in Vertx

  private static TwitterClient twitterClient;

  static void keepServerAlive() {
    PingService pingService = new PingService();
    try {
      LOGGER.warn("Attempting to ping server.");
      pingService.keepServiceAline();
    } catch (URISyntaxException | IOException | InterruptedException e) {
      LOGGER.error("Server couldn't be pinged: " + e);
    }
  }

  public static void main(String[] args) {

    System.out.println("Starting...");
    VertxOptions vertxOptions =
        new VertxOptions().setBlockedThreadCheckInterval(1000 * 60 * 2); // Two minutes...
    Vertx vertx = Vertx.vertx(vertxOptions);

    setProperty(LOGGER_DELEGATE_FACTORY_CLASS_NAME, SLF4JLogDelegateFactory.class.getName());

    LOGGER.info("Deploying verticle...");
    vertx.deployVerticle("fm.bongers.verticle.MainVerticle");
    LOGGER.info("Deployed verticle...");

    twitterClient = ConnectService.connectTwitter();

    vertx.setPeriodic(1000 * 60 * 4, (l) -> checkForUpdates(twitterClient)); // 4 minutes...

    vertx.setPeriodic(1000 * 60 * 15, (l) -> keepServerAlive()); // 15 minutes...

    //vertx.setPeriodic(1000 * 30, (l) -> checkForTickets(twitterClient)); // 30 seconds...
  }
}
