package io.github.louistsaitszho.erg2000.pojo;

/**
 * Created by Louis on 23/8/2016.
 */

public class Section {
  int ID;               //dah
  int recordID;         //FK to records.id
  int order;            //the order of this section in its record
  boolean restOrEasy;   //true if it is rest or easy
  long duration;        //duration of this section in millisecond
  double distance;      //distance of this section in m
  int rating;           //dah

  public Section() {
  }

  public int getID() {
    return ID;
  }

  public void setID(int ID) {
    this.ID = ID;
  }

  public int getRecordID() {
    return recordID;
  }

  public void setRecordID(int recordID) {
    this.recordID = recordID;
  }

  public int getOrder() {
    return order;
  }

  public void setOrder(int order) {
    this.order = order;
  }

  public boolean isRestOrEasy() {
    return restOrEasy;
  }

  public void setRestOrEasy(boolean restOrEasy) {
    this.restOrEasy = restOrEasy;
  }

  public long getDuration() {
    return duration;
  }

  public void setDuration(long duration) {
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

  @Override
  public String toString() {
    return "Section{" +
        "ID=" + ID +
        ", recordID=" + recordID +
        ", order=" + order +
        ", restOrEasy=" + restOrEasy +
        ", duration=" + duration +
        ", distance=" + distance +
        ", rating=" + rating +
        '}';
  }
}
