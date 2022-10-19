package fm.bongers;

import com.twitter.clientlib.ApiException;
import fm.bongers.service.PingService;
import fm.bongers.service.TwitterService;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.logging.SLF4JLogDelegateFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static fm.bongers.service.BongersService.checkForUpdates;
import static io.vertx.core.logging.LoggerFactory.LOGGER_DELEGATE_FACTORY_CLASS_NAME;
import static java.lang.System.setProperty;

public class Application {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(LoggerFactory.class); // Required for Logback to work in Vertx

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
                new VertxOptions().setBlockedThreadCheckInterval(1000 * 60 * 1); // One minute...
        Vertx vertx = Vertx.vertx(vertxOptions);

        setProperty(LOGGER_DELEGATE_FACTORY_CLASS_NAME, SLF4JLogDelegateFactory.class.getName());

        LOGGER.info("Deploying verticle...");
        vertx.deployVerticle("fm.bongers.verticle.MainVerticle");
        LOGGER.info("Deployed verticle...");

        TwitterService twitterService = new TwitterService();

        vertx.setPeriodic(
                1000 * 60 * 4,
                (l) -> {
                    try {
                        checkForUpdates(twitterService);
                    } catch (ApiException e) {
                        LOGGER.error(e.getResponseBody(), e);
                        e.printStackTrace();
                    }
                }); // 4 minutes...

        vertx.setPeriodic(1000 * 60 * 15, (l) -> keepServerAlive()); // 15 minutes...

        //    vertx.setPeriodic(1000 * 45, (l) -> checkForAppointments(twitterService)); // 45
        // seconds...

        // vertx.setPeriodic(1000 * 15, (l) -> checkForTickets(twitterService)); // 30 seconds...

        LOGGER.info("Deployed.");

        // deployedTweet(twitterService, dtf, now);
    }

    private static void deployedTweet(TwitterService twitterService) {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            twitterService.sendTweet("@JacobCarey Deployed: " + dtf.format(now));
        } catch (ApiException e) {
            LOGGER.error(e.getResponseBody(), e);
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
