package io.github.louistsaitszho.erg2000.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.louistsaitszho.erg2000.Consts;
import io.github.louistsaitszho.erg2000.R;
import io.github.louistsaitszho.erg2000.Utils;
import io.github.louistsaitszho.erg2000.adapter.ItemRowRowAdapter;
import io.github.louistsaitszho.erg2000.realm.RealmController;
import io.github.louistsaitszho.erg2000.realm.realmObject.Record;
import io.realm.RealmResults;

public class ViewRecordActivity extends AppCompatActivity {
  public final static String TAG = ViewRecordActivity.class.getSimpleName();

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.tvRemark) TextView tvRemark;
  @BindView(R.id.tvStartDateTime) TextView tvStartDateTime;
  @BindView(R.id.tvEventDescription) TextView tvEventDescription;
  @BindView(R.id.rlRows) RecyclerView rvRows;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_record);
    ButterKnife.bind(this);

    toolbar.setTitle("View Detail");
    setSupportActionBar(toolbar);
    ActionBar supportActionBar = getSupportActionBar();
    if (supportActionBar != null) {
      supportActionBar.setDisplayHomeAsUpEnabled(true);
      supportActionBar.setDisplayShowHomeEnabled(true);
    }

    Intent intent = getIntent();
    if (intent != null) {
      String recordId = intent.getStringExtra(Consts.EXTRA_RECORD_ID);
      if (recordId != null) {
        RealmResults<Record> records = RealmController.with(this).aRecord(recordId);
        if (records.size() != 1) {
          Log.d(TAG, "size of records = " + records.size());
        } else {
          Record thisRecord = records.first();
          Log.d(TAG, thisRecord.toString());
          tvRemark.setText(thisRecord.getRemark());
          tvRemark.setText(thisRecord.getEvent());
          tvStartDateTime.setText(Utils.generateStartDateTimeString(thisRecord.getStartDateTime(), Locale.getDefault()));
          //TODO inflate UI
          rvRows.setLayoutManager(new LinearLayoutManager(ViewRecordActivity.this, LinearLayoutManager.VERTICAL, false));
          rvRows.setAdapter(new ItemRowRowAdapter(thisRecord));
        }
      } else {
        Log.d(TAG, "null recordid");
      }
    } else {
      Log.d(TAG, "null intent");
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_view_record, menu);
    MenuItem edit = menu.findItem(R.id.action_edit);
    edit.setIcon(new IconicsDrawable(ViewRecordActivity.this).icon(CommunityMaterial.Icon.cmd_pencil).actionBar().colorRes(R.color.colorAccent));
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home)
      this.onBackPressed();
    return super.onOptionsItemSelected(item);
  }
}
