package io.github.louistsaitszho.erg2000;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.github.louistsaitszho.erg2000.realmObject.Row;

/**
 * Created by Louis on 24/8/2016.
 */

public class NewRowOrRestCustomView extends LinearLayout {
  TextInputEditText tietHours;
  TextInputEditText tietMinutes;
  TextInputEditText tietSeconds;
  TextInputEditText tietDistance;
  TextInputEditText tietRating;

  boolean isRest;
  int order;

  /**
   * TODO listen to each tiet for invalid input
   * @param context
   * @param isRest
   * @param pos
   */
  public NewRowOrRestCustomView(Context context, boolean isRest, int pos) {
    super(context, null, R.style.AppTheme);
    this.setOrientation(VERTICAL);
    this.setPadding(48, 0, 48, 48);
    this.isRest = isRest;
    this.order = pos;

    TextView tvDuration = new TextView(context);
    tvDuration.setPadding(16, 16, 16, 16);
    tvDuration.setText("Duration");
    this.addView(tvDuration);

    LinearLayout llDuration = new LinearLayout(context);
    llDuration.setGravity(Gravity.CENTER_VERTICAL);
    llDuration.setOrientation(HORIZONTAL);

    LayoutParams lpHours = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 3f);

    TextView tv1 = new TextView(context);
    tv1.setText(":");

    TextView tv2 = new TextView(context);
    tv2.setText(":");

    TextInputLayout tilSeconds = new TextInputLayout(context);
    tilSeconds.setLayoutParams(lpHours);
    tietSeconds = new TextInputEditText(context);
    tietSeconds.setId(3);
    tietSeconds.setHint("Second");
    tietSeconds.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
    tilSeconds.addView(tietSeconds);

    TextInputLayout tilMinutes = new TextInputLayout(context);
    tilMinutes.setLayoutParams(lpHours);
    tietMinutes = new TextInputEditText(context);
    tietMinutes.setId(2);
    tietMinutes.setHint("Minute");
    tietMinutes.setInputType(InputType.TYPE_CLASS_NUMBER);
    tietMinutes.setNextFocusDownId(tietSeconds.getId());
    tilMinutes.addView(tietMinutes);

    TextInputLayout tilHours = new TextInputLayout(context);
    tilHours.setLayoutParams(lpHours);
    tietHours = new TextInputEditText(context);
    tietHours.setHint("Hour");
    tietHours.setInputType(InputType.TYPE_CLASS_NUMBER);
    tietHours.setNextFocusDownId(tietMinutes.getId());
    tilHours.addView(tietHours);

    llDuration.addView(tilHours);
    llDuration.addView(tv1);
    llDuration.addView(tilMinutes);
    llDuration.addView(tv2);
    llDuration.addView(tilSeconds);
    this.addView(llDuration);

    TextInputLayout tilDistance = new TextInputLayout(context);
    LayoutParams lpDistance = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    tilDistance.setLayoutParams(lpDistance);
    tietDistance = new TextInputEditText(context);
    tietDistance.setInputType(InputType.TYPE_CLASS_NUMBER);
    tietDistance.setHint("Distance (m)");
    tilDistance.addView(tietDistance);
    this.addView(tilDistance);

    if (!isRest) {
      TextInputLayout tilRating = new TextInputLayout(context);
      tilDistance.setLayoutParams(lpDistance);
      tietRating = new TextInputEditText(context);
      tietRating.setHint("Rating (s/m)");
      tietRating.setInputType(InputType.TYPE_CLASS_NUMBER);
      tilRating.addView(tietRating);
      this.addView(tilRating);
    }

  }

  /**
   * TODO throw sth when input is invalid
   * @return
   */
  public Row getData() {
    int hours = 0;
    int minutes = 0;
    double seconds = 0;
    long distance = 0;
    Long rating = null;
    try {
      hours = Integer.valueOf(tietHours.getText().toString());
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
    try {
      minutes = Integer.valueOf(tietMinutes.getText().toString());
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
    try {
      seconds = Integer.valueOf(tietSeconds.getText().toString());
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
    try {
      distance = Long.valueOf(tietDistance.getText().toString());
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
    if (!isRest) {
      try {
        rating = Long.valueOf(tietRating.getText().toString());
      } catch (NumberFormatException e) {
        e.printStackTrace();
      }
    }
    return new Row(isRest, order, distance, Utils.hmsTomillisecond(hours, minutes, seconds), rating);
  }

}
