package io.github.louistsaitszho.erg2000.pojo;

import java.util.List;

/**
 * Created by Louis on 23/8/2016.
 */

public class Record {
  int ID;                       //autoincrement primary key
  long startTimestamp;          //start timestamp in millisecond (long)
  int duration;                 //total duration in millisecond (e.g. 30 minutes = 1 800 000 ms)
  double distance;              //total distance in m (e.g. 7031.4 m)
  int rating;                   //dah
  String remark;                //dah

  List<Tag> tags;

  public Record() {

  }

  public Record(int ID, long startTimestamp, int duration, double distance, int rating, String remark) {
    this.ID = ID;
    this.startTimestamp = startTimestamp;
    this.duration = duration;
    this.distance = distance;
    this.rating = rating;
    this.remark = remark;
  }

  public int getID() {
    return ID;
  }

  public void setID(int ID) {
    this.ID = ID;
  }

  public long getStartTimestamp() {
    return startTimestamp;
  }

  public void setStartTimestamp(long startTimestamp) {
    this.startTimestamp = startTimestamp;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public double getDistance() {
    return distance;
  }

  public void setDistance(double distance) {
    this.distance = distance;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public List<Tag> getTags() {
    return tags;
  }

  public void setTags(List<Tag> tags) {
    this.tags = tags;
  }

  @Override
  public String toString() {
    return "Record{" +
        "ID='" + ID + '\'' +
        ", startTimestamp=" + startTimestamp +
        ", duration=" + duration +
        ", distance=" + distance +
        ", rating=" + rating +
        ", remark='" + remark + '\'' +
        '}';
  }
}
