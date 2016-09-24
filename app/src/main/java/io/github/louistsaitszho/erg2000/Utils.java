package io.github.louistsaitszho.erg2000;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.github.louistsaitszho.erg2000.realm.realmObject.Record;
import io.github.louistsaitszho.erg2000.realm.realmObject.Row;

/**
 * Utilities
 * Created by Louis on 23/8/2016.
 */

public class Utils {
  public static final String TAG = Utils.class.getSimpleName();

  public static long hmsTomillisecond(int h, int m, double s) {
    return (long) (TimeUnit.HOURS.toMillis(h) + TimeUnit.MINUTES.toMillis(m) + s * 1000);
  }

  public static String generatePaceString(Record record) {
    return generatePaceString(record.getTotalDuration(), record.getTotalDistance());
  }

  public static String generatePaceString(Row row) {
    return generatePaceString(row.getDuration(), row.getDistance());
  }

  /**
   * TODO
   * @param duration
   * @param distance
   * @return
   */
  public static String generatePaceString(long duration, long distance) {
    Log.d(TAG, "dur = " + duration + ", dis = " + distance);
    double sections = (double)distance / 500;
    Log.d(TAG, "sections = " + sections);
    long per500MS;
    long mins;
    long secs;
    int mils;
//    if (sections > 0) {
      per500MS = (long) (duration / sections);
//    } else {
//      per500MS = duration / distance * 500;
//    }
    mins = TimeUnit.MILLISECONDS.toMinutes(per500MS);
    secs = TimeUnit.MILLISECONDS.toSeconds(per500MS) - TimeUnit.MINUTES.toSeconds(mins);
    mils = Math.round((per500MS - TimeUnit.MINUTES.toMillis(mins) - TimeUnit.SECONDS.toMillis(secs)) / 100);
    return String.format("%d:%02d.%d", mins, secs, mils);
  }

  /**
   * TODO if minute == 0, hide it
   * @param duration
   * @param isRest
   * @return
   */
  public static String generateDurationString(long duration, boolean isRest) {
    long hours = TimeUnit.MILLISECONDS.toHours(duration);
    long minutes = TimeUnit.MILLISECONDS.toMinutes(duration) - TimeUnit.HOURS.toMinutes(hours);
    long seconds = TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.HOURS.toSeconds(hours) - TimeUnit.MINUTES.toSeconds(minutes);
    int decisecond = Math.round((duration - TimeUnit.HOURS.toMillis(hours) - TimeUnit.MINUTES.toMillis(minutes) - TimeUnit.SECONDS.toMillis(seconds)) / 100);
    Log.d(TAG, "ds = " + decisecond);

    String output;
    if (hours == 0) {
      output = String.format("%d:%02d.%d", minutes, seconds, decisecond);
    } else {
      output = String.format("%d:%02d:%02d.%d", hours, minutes, seconds, decisecond);
    }

    if (isRest)
      output = "r" + output;

    return output;
  }

  public static String generateDurationString(Record record) {
    return generateDurationString(record.getTotalDuration(), false);
  }

  /**
   * If the record is rest, it will put a "r" in front
   * @param row
   * @return
   */
  public static String generateDurationString(Row row) {
    return generateDurationString(row.getDuration(), row.isEasy());
  }

  public static String generateDistanceString(long theDistance, List<Long> previousDistances) {
    long previously = 0;
    for (long l:previousDistances) {
      previously += l;
    }
    previously += theDistance;
    return String.valueOf(theDistance) + "|" + String.valueOf(previously);
  }

  public static String generateStartDateTimeString(Date date, Locale locale) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy/M/d kk:mm", locale);
    return simpleDateFormat.format(date);
  }

  //TODO calculate just now/s/m/h/d/months/y ago

  public static long hmsdToLong(int h, int m, int s, int d) {
    return TimeUnit.HOURS.toMillis(h) + TimeUnit.MINUTES.toMillis(m) + TimeUnit.SECONDS.toMillis(s) + d*100;
  }

  /**
   * Generate a String array of "1", "2", "3" ... to whatever number(exclusively) you want
   * @param smallerThan e.g. = 100 => "1", "2", "3" ... "98", "99"
   * @return a string array
   */
  public static String[] zeroTo(int smallerThan) {
    String[] output = new String[smallerThan];
    for (int i = 0; i < smallerThan; i++) {
      output[i] = String.valueOf(i);
    }
    return output;
  }

  public static String randomString(int length) {
    return new RandomString(length).nextString();
  }

  public static class RandomString {

    private static final char[] symbols;

    static {
      StringBuilder tmp = new StringBuilder();
      for (char ch = '0'; ch <= '9'; ++ch)
        tmp.append(ch);
      for (char ch = 'a'; ch <= 'z'; ++ch)
        tmp.append(ch);
      symbols = tmp.toString().toCharArray();
    }

    private final Random random = new Random();

    private final char[] buf;

    public RandomString(int length) {
      if (length < 1)
        throw new IllegalArgumentException("length < 1: " + length);
      buf = new char[length];
    }

    public String nextString() {
      for (int idx = 0; idx < buf.length; ++idx)
        buf[idx] = symbols[random.nextInt(symbols.length)];
      return new String(buf);
    }
  }

  public static class TimeAgo {
    public final static long MILLISECOND = 1;
    public final static long SECOND = Consts.MS_IN_SECOND * MILLISECOND;
    public final static long MINUTE = Consts.S_IN_MIN * SECOND;
    public final static long HOUR = Consts.MIN_IN_HOUR * MINUTE;
    public final static long DAY = Consts.HOUR_IN_DAY * HOUR;
    public final static long WEEK = Consts.DAY_IN_WEEK * DAY;
    public final static long MONTH = (long) (30.4375 * DAY);
    public final static long YEAR = (long) (365.25 * DAY);

    public static String toTimeAgo(long dateMsDiff) {
      if (dateMsDiff < MINUTE)
        return "Just now";
      else if (dateMsDiff < 2 * MINUTE)
        return "A minute ago";
      else if (dateMsDiff < 50 * MINUTE)
        return (dateMsDiff / MINUTE + " minutes ago");
      else if (dateMsDiff < 80 * MINUTE)
        return "An hour ago";
      else if (dateMsDiff < 24 * HOUR)
        return (dateMsDiff / HOUR + " hours ago");
      else if (dateMsDiff < 36 * HOUR)
        return ("A day ago");
      else if (dateMsDiff < WEEK)
        return (dateMsDiff / DAY + " days ago");
      else if (dateMsDiff < 2 * WEEK)
        return "A week ago";
      else if (dateMsDiff < MONTH)
        return (dateMsDiff / WEEK + " weeks ago");
      else if (dateMsDiff < 2 * MONTH)
        return "A month ago";
      else if (dateMsDiff < YEAR)
        return (dateMsDiff / MONTH + " months ago");
      else if (dateMsDiff < 2 * YEAR)
        return "A year ago";
      else return (dateMsDiff / YEAR + " years ago");
    }
  }

  /**
   * TODO generate (list of) suggestion(s) for record description
   * e.g.
   * "30:00"
   * "7000m"
   * "42195m"
   * "4x2000m/1:00r"
   * "10x500m/:30r"
   *
   * @param rows
   * @return
   */
//  public static List<String> eventDescriptionGenerator(List<Row> rows) {
//    if (rows == null) {
//      return null;
//    } else {
//      List<String> outputs = new ArrayList<>();
//      List<Row> rowRows = new ArrayList<>();
//      long firstDistance;
//      long firstDuration;
//      long totalDistance;
//      long totalDuration;
//      long totalRestDistance;
//      long totalRestDuration;
//
//      for (Row r: rows) {
//        if (!r.isEasy()) {
//          rowRows.add(r);
//          totalDistance += r.getDistance();
//          totalDuration += r.getDuration();
//        } else {
//          totalRestDistance += r.getDistance();
//          totalRestDuration += r.getDuration();
//        }
//      }
//      if (rows.size() == 1) {
//        Row r = rows.get(0);
//        if (r.getDistance()%100 == 0) {                         //distance can be divided by 100 (e.g. 500m, 2000m, 8000m)
//          outputs.add(String.valueOf(r.getDistance()) + ",");
//        }
//
//        if (r.getDistance() == 42195) {                         //Marathon
//          outputs.add("42195m");
//        }
//
//        if (r.getDuration()%60000 == 0) {                       //duration can be divided by 60000 (e.g. 1 minute, 10 minutes, 30 minutes)
//          outputs.add(String.valueOf(r.getDuration()%60000) + ":00");
//        }
//      } else {
//
//      }
//    }
//
//
//
//
//
//    //1. if only 1 row of "row"
//      //1. if distance can be divided by 100m
//      //2. if distance is 42195 (marathon)
//      //3. if duration can be divided by 60000ms
//    //2. if the whole list only consists of 1 row of "row"
//      //1. see 1.1-1.3
//    //3
//  }

}
