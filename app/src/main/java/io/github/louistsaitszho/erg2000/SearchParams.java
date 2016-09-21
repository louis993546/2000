package io.github.louistsaitszho.erg2000;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Louis on 19/9/2016.
 */

public class SearchParams implements Serializable{
  Long minDuration, maxDuration, minDistance, maxDistance, minRating, maxRating = null;
  String remark = null;
  Date minStartDate, maxStartDate = null;

  public SearchParams(Long minDuration, Long maxDuration, Long minDistance, Long maxDistance, Long minRating, Long maxRating, String remark, Date minStartDate, Date maxStartDate) {
    this.minDuration = minDuration;
    this.maxDuration = maxDuration;
    this.minDistance = minDistance;
    this.maxDistance = maxDistance;
    this.minRating = minRating;
    this.maxRating = maxRating;
    this.remark = remark;
    this.minStartDate = minStartDate;
    this.maxStartDate = maxStartDate;
  }
}
