package io.github.louistsaitszho.erg2000.realmObject;

import java.io.Serializable;
import java.util.Arrays;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Because I can't have RealmList of byte array (images)
 * Created by Louis on 26/8/2016.
 */

public class Image extends RealmObject implements Serializable{
  @Required @PrimaryKey String id;
  byte[] image;
  String remark;

  public Image() {

  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  @Override
  public String toString() {
    return "Image{" +
        "id='" + id + '\'' +
        ", image=" + Arrays.toString(image) +
        ", remark='" + remark + '\'' +
        '}';
  }
}
