package io.github.louistsaitszho.erg2000;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.squareup.leakcanary.LeakCanary;

import org.greenrobot.greendao.database.Database;

import java.io.File;

import io.github.louistsaitszho.erg2000.db.DaoMaster;
import io.github.louistsaitszho.erg2000.db.DaoSession;

/**
 * Created by Louis on 20/8/2016.
 */

public class application extends Application {

  SQLiteDatabase db;
  DaoMaster daoMaster;
  DaoSession daoSession;

  @Override
  public void onCreate() {
    super.onCreate();
    LeakCanary.install(this);

    File path = new File(Environment.getExternalStorageDirectory(), "erg2000/db");
    path.getParentFile().mkdirs();

    DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, path.getAbsolutePath(), null);
    db = helper.getWritableDatabase();
    daoMaster = new DaoMaster(db);
    daoSession = daoMaster.newSession();
  }

  public SQLiteDatabase getDb() {
    return db;
  }

  public DaoMaster getDaoMaster() {
    return daoMaster;
  }

  public DaoSession getDaoSession() {
    return daoSession;
  }
}
