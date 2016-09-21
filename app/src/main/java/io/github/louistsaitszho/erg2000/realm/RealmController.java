package io.github.louistsaitszho.erg2000.realm;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;

import java.util.Date;
import java.util.List;

import io.github.louistsaitszho.erg2000.realm.realmObject.Record;
import io.github.louistsaitszho.erg2000.realm.realmObject.Row;
import io.github.louistsaitszho.erg2000.realm.realmObject.Tag;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Use this controller to access the Realm db
 * Created by Louis on 24/8/2016.
 */

public class RealmController {

  private static RealmController controller;
  private final Realm realm;

  public RealmController(Application application) {
    realm = Realm.getDefaultInstance();
  }

  public static RealmController with(Fragment fragment) {
    if (controller == null) {
      controller = new RealmController(fragment.getActivity().getApplication());
    }
    return controller;
  }

  public static RealmController with(Activity activity) {
    if (controller == null) {
      controller = new RealmController(activity.getApplication());
    }
    return controller;
  }

  public static RealmController with(Application application) {
    if (controller == null) {
      controller = new RealmController(application);
    }
    return controller;
  }

  public static RealmController getInstance() {
    return controller;
  }

  /**
   * @return every records
   */
  public RealmResults<Record> allRecords() {
    return realm.where(Record.class).findAll();
  }

  /**
   *
   * @param startDateTime
   * @param remark
   * @param totalDistance
   * @param totalDuration
   * @param averageRating
   * @param eventDescription
   * @param tags
   * @param rowSparseArray
   */
  public void addRecord(long startDateTime, @Nullable CharSequence remark, long totalDistance, long totalDuration, long averageRating, @Nullable CharSequence eventDescription, List<String> tags, SparseArray<Row> rowSparseArray) {
    realm.beginTransaction();
    Record newRecord = realm.createObject(Record.class);
    newRecord.setStartDateTime(new Date(startDateTime));
    if (remark != null)
      newRecord.setRemark(remark.toString());
    newRecord.setTotalDistance(totalDistance);
    newRecord.setTotalDuration(totalDuration);
    newRecord.setAverageRating(averageRating);
    if (eventDescription != null)
      newRecord.setEvent(eventDescription.toString());
    if (tags != null && tags.size() > 0) {
      for (String tag:tags) {
        Tag newTag = realm.createObject(Tag.class);
        newTag.setTag(tag);
        newRecord.tags.add(newTag);
      }
    }
    for (int i = 0; i < rowSparseArray.size(); i++) {
      Row newRow = realm.createObject(Row.class);
      Row r = rowSparseArray.get(rowSparseArray.keyAt(i));
      newRow.setEasy(r.isEasy());
      newRow.setDistance(r.getDistance());
      newRow.setDuration(r.getDuration());
      newRow.setOrder(r.getOrder());
      newRow.setRating(r.getRating());
      newRecord.rows.add(newRow);
    }
    //TODO images
    realm.commitTransaction();
  }

  /**
   * Get every tags
   * @return list of tags
   */
  public RealmResults<Tag> allTags() {
    return realm.where(Tag.class).findAll();
  }

  /**
   * Get records that contains the list of tags
   * @param tags in a List
   * @return every record with those tags
   */
  public RealmResults<Record> recordsWithTags(List<Tag> tags) {
    RealmQuery<Record> query = realm.where(Record.class);
    for (int i = 0; i < tags.size()-1; i++) {
      Tag t = tags.get(i);
      query.contains("tags.tag", t.getTag()).or();
    }
    query.contains("tags.tag", tags.get(tags.size()-1).getTag());
    return query.findAll();
  }

}