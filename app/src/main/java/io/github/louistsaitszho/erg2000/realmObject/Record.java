package io.github.louistsaitszho.erg2000.realmObject;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Louis on 24/8/2016.
 */

public class Record extends RealmObject {
  @Required @PrimaryKey String id;
  @Required Date startDateTime;
  long totalDuration;
  double totalDistance;
  long averageRating;
  String remark;
  RealmList<Tag> tags;
  RealmList<Row> rows;

  public Record() {

  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public double getTotalDistance() {
    return totalDistance;
  }

  public void setTotalDistance(double totalDistance) {
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

  public void setTags(RealmList<Tag> tags) {
    this.tags = tags;
  }

  public RealmList<Row> getRows() {
    return rows;
  }

  public void setRows(RealmList<Row> rows) {
    this.rows = rows;
  }

  @Override
  public String toString() {
    return "Record{" +
        "id='" + id + '\'' +
        ", startDateTime=" + startDateTime +
        ", totalDuration=" + totalDuration +
        ", totalDistance=" + totalDistance +
        ", averageRating=" + averageRating +
        ", remark='" + remark + '\'' +
        ", tags=" + tags +
        ", rows=" + rows +
        '}';
  }
}
