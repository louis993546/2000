package io.github.louistsaitszho.erg2000.pojo;

/**
 * Created by Louis on 23/8/2016.
 */

public class Tag {
  int ID;
  String tag;

  public Tag(int ID, String tag) {
    this.ID = ID;
    this.tag = tag;
  }

  public int getID() {
    return ID;
  }

  public void setID(int ID) {
    this.ID = ID;
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
        "ID=" + ID +
        ", tag='" + tag + '\'' +
        '}';
  }
}
