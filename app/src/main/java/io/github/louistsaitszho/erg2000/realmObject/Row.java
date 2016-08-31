package io.github.louistsaitszho.erg2000.realmObject;

import java.io.Serializable;
import java.util.Random;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Louis on 24/8/2016.
 */

public class Row extends RealmObject implements Serializable{
  @Required @PrimaryKey String id;  //8-digit of base. see http://stackoverflow.com/a/5351326/2384934
  boolean isEasy;                   //is it easy-ing/resting
  long order;                       //Order of this row in its record
  long distance;                    //distance in m
  long duration;                    //duration in ms
  Long rating;                      //[Nullable] rating

  public Row() {

  }

  public Row(boolean isEasy, long distance, long duration, Long rating) {
    byte[] r = new byte[8];
    new Random().nextBytes(r);
    this.id = new String(r);
    this.isEasy = isEasy;
    this.distance = distance;
    this.duration = duration;
    this.rating = rating;
  }

  public Row (boolean isEasy, long distance, long duration, int rating) {
    this(isEasy, distance, duration, (long) rating);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public boolean isEasy() {
    return isEasy;
  }

  public void setEasy(boolean easy) {
    isEasy = easy;
  }

  public long getOrder() {
    return order;
  }

  /**
   * It may overflow (very unlikely though)
   * @return
   */
  public int getOrderInt() {
    return (int) order;
  }

  public void setOrder(long order) {
    this.order = order;
  }

  public long getDistance() {
    return distance;
  }

  public void setDistance(long distance) {
    this.distance = distance;
  }

  public long getDuration() {
    return duration;
  }

  public void setDuration(long duration) {
    this.duration = duration;
  }

  public Long getRating() {
    return rating;
  }

  public void setRating(Long rating) {
    this.rating = rating;
  }

  @Override
  public String toString() {
    return "Row{" +
        "id='" + id + '\'' +
        ", isEasy=" + isEasy +
        ", order=" + order +
        ", distance=" + distance +
        ", duration=" + duration +
        ", rating=" + rating +
        '}';
  }
}
