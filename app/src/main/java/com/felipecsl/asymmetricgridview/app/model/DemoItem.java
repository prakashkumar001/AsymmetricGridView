package com.felipecsl.asymmetricgridview.app.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.felipecsl.asymmetricgridview.AsymmetricItem;

public class DemoItem implements AsymmetricItem {
  private int columnSpan;
  private int rowSpan;
  private String imageUrl;

  public DemoItem() {
    this(1, 1,"");
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public DemoItem(int columnSpan, int rowSpan, String imageUrl) {
    this.columnSpan = columnSpan;
    this.rowSpan = rowSpan;
    this.imageUrl = imageUrl;

  }

  public DemoItem(Parcel in) {
    readFromParcel(in);
  }

  @Override public int getColumnSpan() {
    return columnSpan;
  }

  @Override public int getRowSpan() {
    return rowSpan;
  }

 /* public int getPosition() {
    return position;
  }

  @Override public String toString() {
    return String.format("%s: %sx%s", position, rowSpan, columnSpan);
  }*/

  @Override public int describeContents() {
    return 0;
  }

  private void readFromParcel(Parcel in) {
    columnSpan = in.readInt();
    rowSpan = in.readInt();
     imageUrl= in.readString();
  }

  @Override public void writeToParcel(@NonNull Parcel dest, int flags) {
    dest.writeInt(columnSpan);
    dest.writeInt(rowSpan);
    dest.writeString(imageUrl);
  }

  /* Parcelable interface implementation */
  public static final Parcelable.Creator<DemoItem> CREATOR = new Parcelable.Creator<DemoItem>() {
    @Override public DemoItem createFromParcel(@NonNull Parcel in) {
      return new DemoItem(in);
    }

    @Override @NonNull public DemoItem[] newArray(int size) {
      return new DemoItem[size];
    }
  };
}
