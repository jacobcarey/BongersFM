package uk.co.jacobcarey.squadbongers.infrastructure;

import java.util.HashMap;
import java.util.Map;

public class LastTracksPlayed {

  private static uk.co.jacobcarey.squadbongers.infrastructure.LastTracksPlayed INSTANCE;
  private Map<String, Integer> trackTimes;

  private LastTracksPlayed() {}

  public static uk.co.jacobcarey.squadbongers.infrastructure.LastTracksPlayed getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new uk.co.jacobcarey.squadbongers.infrastructure.LastTracksPlayed();
      Map<String, Integer> trackTimes = new HashMap<>();
      INSTANCE.setTrackTimes(trackTimes);
    }
    return INSTANCE;
  }

  public Map<String, Integer> getTrackTimes() {
    return trackTimes;
  }

  public void setTrackTimes(Map<String, Integer> trackTimes) {
    this.trackTimes = trackTimes;
  }
}
