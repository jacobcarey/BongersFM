package fm.bongers.model;

public class Track {

  private String artist;
  private String name;
  private String playCount;
  private int time;

  public Track(String artist, String name, String playCount) {
    this.artist = artist;
    this.name = name;
    this.playCount = playCount;
  }

  public Track() {}

  public String getArtist() {
    return artist;
  }

  public void setArtist(String artist) {
    this.artist = artist;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPlayCount() {
    return playCount;
  }

  public void setPlayCount(String playCount) {
    this.playCount = playCount;
  }

  public int getTime() {
    return time;
  }

  public void setTime(int time) {
    this.time = time;
  }

  @Override
  public String toString() {
    return "Track{"
        + "artist='"
        + artist
        + '\''
        + ", name='"
        + name
        + '\''
        + ", playCount='"
        + playCount
        + '\''
        + ", time="
        + time
        + '}';
  }
}
