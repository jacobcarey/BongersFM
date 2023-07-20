package uk.co.jacobcarey.squadbongers.service;

import com.twitter.clientlib.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uk.co.jacobcarey.squadbongers.infrastructure.Config;
import uk.co.jacobcarey.squadbongers.infrastructure.LastFMUsernames;
import uk.co.jacobcarey.squadbongers.infrastructure.LastTracksPlayed;
import uk.co.jacobcarey.squadbongers.model.Track;
import uk.co.jacobcarey.squadbongers.util.StringUtil;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BongersService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BongersService.class);

    private final TwitterService twitterService;

    @Autowired
    public BongersService(TwitterService twitterService) {
        this.twitterService = twitterService;
    }

    @Scheduled(initialDelay = 1000 * 30, fixedRate = 1000 * 60 * 4)
    public void checkForUpdates() throws ApiException {
        LOGGER.info("Let's start checking for bongers...");

        List<String> tweets = new ArrayList<>();
        LastFMService lastFMService = new LastFMService(Config.getInstance().getLastFmApi());
        for (Map.Entry<String, String> user : LastFMUsernames.getInstance().getUsernames().entrySet()) {

            Track latestTrack = null;
            try {
                latestTrack = lastFMService.getLatestTrack(user.getKey());

                LOGGER.info("Track found: {}", latestTrack);
                if (LastTracksPlayed.getInstance().getTrackTimes().containsKey(user.getKey())) {
                    Integer time = LastTracksPlayed.getInstance().getTrackTimes().get(user.getKey());
                    if (time < latestTrack.getTime()
                            && latestTrack.getPlayCount() != null
                            && Integer.parseInt(latestTrack.getPlayCount()) != 0) {

                        String tweet = StringUtil.buildTweet(user, latestTrack);
                        tweets.add(tweet);
                        LOGGER.info("Adding tweet: {}", tweet);
                        LastTracksPlayed.getInstance()
                                .getTrackTimes()
                                .put(user.getKey(), latestTrack.getTime());
                    }
                } else {
                    LastTracksPlayed.getInstance().getTrackTimes().put(user.getKey(), latestTrack.getTime());
                }
            } catch (URISyntaxException | IOException | InterruptedException e) {
                LOGGER.error("@JacobCarey I'm fucked and need to temporarily shut down. Beep bop.", e);
            }
        }
        for (String tweet : tweets) {
            twitterService.sendTweet(tweet);
        }
    }
}
