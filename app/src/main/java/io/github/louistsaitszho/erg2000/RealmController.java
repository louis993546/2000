package io.github.louistsaitszho.erg2000;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import java.util.List;

import io.github.louistsaitszho.erg2000.realmObject.Record;
import io.github.louistsaitszho.erg2000.realmObject.Tag;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
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
   * Get every records
   * @return
   */
  public RealmResults<Record> allRecords() {
    return realm.where(Record.class).findAll();
  }

  //TODO add records and tags

  /**
   * Get every tags
   * @return list of tags
   */
  public RealmResults<Tag> allTags() {
    return realm.where(Tag.class).findAll();
  }

  /**
   * Get records that contains the list of tags
   * @param tags
   * @return
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
