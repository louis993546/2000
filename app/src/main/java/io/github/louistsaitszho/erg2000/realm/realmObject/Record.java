package io.github.louistsaitszho.erg2000.realm.realmObject;

import java.io.Serializable;
import java.util.Date;

import io.github.louistsaitszho.erg2000.Consts;
import io.github.louistsaitszho.erg2000.Utils;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Core concept: The DB is a list of Records, and each record is an event
 * Created by Louis on 24/8/2016.
 */

public class Record extends RealmObject implements Serializable{
  @PrimaryKey String id;
  @Required Date startDateTime;
  String event;
  long totalDuration;
  long totalDistance;
  long averageRating;
  String remark;
  public RealmList<Tag> tags;
  public RealmList<Row> rows;
  public RealmList<Image> images;

  public Record() {

  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setTags(RealmList<Tag> tags) {
    this.tags = tags;
  }

  public void setRows(RealmList<Row> rows) {
    this.rows = rows;
  }

  public void setImages(RealmList<Image> images) {
    this.images = images;
  }

  public Date getStartDateTime() {
    return startDateTime;
  }

  public void setStartDateTime(Date startDateTime) {
    this.startDateTime = startDateTime;
  }

  public long getTotalDuration() {
    return totalDuration;
  }

  public void setTotalDuration(long totalDuration) {
    this.totalDuration = totalDuration;
  }

  public long getTotalDistance() {
    return totalDistance;
  }

  public void setTotalDistance(long totalDistance) {
    this.totalDistance = totalDistance;
  }

  public long getAverageRating() {
    return averageRating;
  }

  public void setAverageRating(long averageRating) {
    this.averageRating = averageRating;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public RealmList<Tag> getTags() {
    return tags;
  }

  public RealmList<Row> getRows() {
    return rows;
  }

  public String getEvent() {
    return event;
  }

  public void setEvent(String event) {
    this.event = event;
  }

  public RealmList<Image> getImages() {
    return images;
  }

  @Override
  public String toString() {
    return "Record{" +
        "id='" + id + '\'' +
        ", startDateTime=" + startDateTime +
        ", event='" + event + '\'' +
        ", totalDuration=" + totalDuration +
        ", totalDistance=" + totalDistance +
        ", averageRating=" + averageRating +
        ", remark='" + remark + '\'' +
        ", tags=" + tags +
        ", rows=" + rows +
        ", images=" + images +
        '}';
  }
}
