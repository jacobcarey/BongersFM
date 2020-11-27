package fm.bongers.infrastructure;

import java.util.HashMap;
import java.util.Map;

public class LastTracksPlayed {

  private static fm.bongers.infrastructure.LastTracksPlayed INSTANCE;
  private Map<String, Integer> trackTimes;

  private LastTracksPlayed() {}

  public static fm.bongers.infrastructure.LastTracksPlayed getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new fm.bongers.infrastructure.LastTracksPlayed();
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
