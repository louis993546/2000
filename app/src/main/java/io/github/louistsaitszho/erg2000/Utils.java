package io.github.louistsaitszho.erg2000;

import java.util.concurrent.TimeUnit;

/**
 * Created by Louis on 23/8/2016.
 */

public class Utils {
  //TODO convert millisecond to date

  public static long hmsTomillisecond(int h, int m, double s) {
    return (long) (TimeUnit.HOURS.toMillis(h) + TimeUnit.MINUTES.toMillis(m) + s * 1000);
  }

  /**
   * TODO
   * @param duration
   * @param distance
   * @return
   */
  public static String generatePer500String(long duration, long distance) {
    long sections = distance / 500;
    long per500MS = duration / sections;
    long mins = TimeUnit.MILLISECONDS.toMinutes(per500MS);
    long secs = TimeUnit.MILLISECONDS.toSeconds(per500MS) - TimeUnit.MINUTES.toSeconds(mins);
    int mils = Math.round((per500MS - TimeUnit.MINUTES.toMillis(mins) - TimeUnit.SECONDS.toMillis(secs)) / 100);

    return String.format("%02d:%02d.%02d", mins, secs, mils);
  }

  public static String msToDurationString(long duration) {
    long hours = TimeUnit.MILLISECONDS.toHours(duration);
    long minutes = TimeUnit.MILLISECONDS.toMinutes(duration) - TimeUnit.HOURS.toMinutes(hours);
    long seconds = TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.HOURS.toSeconds(hours) - TimeUnit.MINUTES.toSeconds(minutes);
    int decisecond = Math.round((duration - TimeUnit.HOURS.toMillis(hours) - TimeUnit.MINUTES.toMillis(minutes) - TimeUnit.SECONDS.toMillis(seconds)) / 100);
    return String.format("%02d:%02d:%02d.%02d", hours, minutes, seconds, decisecond);
//    return (new SimpleDateFormat("hh:mm:ss.SS")).format(new Date(duration));
  }

}
