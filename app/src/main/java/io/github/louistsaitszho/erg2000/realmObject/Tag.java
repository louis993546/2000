package io.github.louistsaitszho.erg2000.realmObject;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Core concept: This should be self explanatory
 * Created by Louis on 24/8/2016.
 */

public class Tag extends RealmObject implements Serializable{
  @Required @PrimaryKey String id;
  @Required String tag;

  public Tag() {

  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  @Override
  public String toString() {
    return "Tag{" +
        "id='" + id + '\'' +
        ", tag='" + tag + '\'' +
        '}';
  }
}
