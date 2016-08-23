package io.github.louistsaitszho.erg2000.pojo;

/**
 * Created by Louis on 23/8/2016.
 */

public class RecordVsTag {
  int ID;
  int recordID;
  String tag;

  public RecordVsTag() {
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

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  @Override
  public String toString() {
    return "RecordVsTag{" +
        "ID=" + ID +
        ", recordID=" + recordID +
        ", tag='" + tag + '\'' +
        '}';
  }
}
