package io.github.louistsaitszho.erg2000;

import android.app.Application;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Louis on 20/8/2016.
 */

public class application extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    LeakCanary.install(this);
  }

}
