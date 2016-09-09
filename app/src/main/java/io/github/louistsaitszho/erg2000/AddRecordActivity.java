package io.github.louistsaitszho.erg2000;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.louistsaitszho.erg2000.fragment.NewRowOrRestDialogFragment;
import io.github.louistsaitszho.erg2000.realmObject.Row;
import io.github.louistsaitszho.erg2000.realmObject.Tag;
import io.realm.Realm;
import io.realm.RealmResults;
import mabbas007.tagsedittext.TagsEditText;

public class AddRecordActivity extends AppCompatActivity implements NewRowOrRestDialogFragment.DialogListener{
  public static final String TAG = AddRecordActivity.class.getSimpleName();

  SparseArray<Row> rowSparseArray;
  long startDateTime;
  HashMap<String, Tag> tagHashMap;
  List<String> tagsString;

  long averageRating = 0;
  long totalDistance = 0;
  long totalDuration = 0;
  boolean fixTotals = false;

  @BindView(R.id.toolbar)               Toolbar toolbar;

  @BindView(R.id.tvTime)                TextView tvTotalTime;
  @BindView(R.id.tvMeter)               TextView tvTotalDistance;
  @BindView(R.id.tvPace)                TextView tvAveragePace;
  @BindView(R.id.tvRating)              TextView tvAverageRating;
  @BindView(R.id.rlRows)                RecyclerView recyclerView;
  @BindView(R.id.bRest)                 Button bRest;
  @BindView(R.id.bRow)                  Button bRow;

  @BindView(R.id.ivEventDescription)    ImageView ivEventDescription;
  @BindView(R.id.actvEventDescription)  AutoCompleteTextView actvEventDescription;
  @BindView(R.id.ivStartDateTime)       ImageView ivStartDateTime;
  @BindView(R.id.tvStartDateTime)       TextView tvStartDateTime;
  @BindView(R.id.ivTags)                ImageView ivTags;
  @BindView(R.id.etTags)                TagsEditText tetTags;
  @BindView(R.id.ivRemark)              ImageView ivRemark;
  @BindView(R.id.etRemark)              EditText etRemark;
  @BindView(R.id.ivImages)              ImageView ivImages;
  @BindView(R.id.rlImage)               RelativeLayout rlImage;
  @BindView(R.id.tvImages)              TextView tvImages;
  
  @BindView(R.id.fab)                   FloatingActionButton floatingActionButton;

  RVA recyclerViewAdapter;
  ArrayAdapter eventDescriptionAdapter;
  ArrayAdapter tagsAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_record);
    ButterKnife.bind(this);

    toolbar.setTitle("New Record");
    setSupportActionBar(toolbar);

    ActionBar supportActionBar = getSupportActionBar();
    if (supportActionBar != null) {
      supportActionBar.setDisplayHomeAsUpEnabled(true);
      supportActionBar.setDisplayShowHomeEnabled(true);
    }

    ivEventDescription.setImageDrawable(new IconicsDrawable(this).color(ContextCompat.getColor(this, R.color.colorIconGray)).sizeDp(16).icon(CommunityMaterial.Icon.cmd_rowing));
    ivStartDateTime.setImageDrawable(new IconicsDrawable(this).color(ContextCompat.getColor(this, R.color.colorIconGray)).sizeDp(16).icon(CommunityMaterial.Icon.cmd_clock));
    ivTags.setImageDrawable(new IconicsDrawable(this).color(ContextCompat.getColor(this, R.color.colorIconGray)).sizeDp(16).icon(CommunityMaterial.Icon.cmd_label));
    ivRemark.setImageDrawable(new IconicsDrawable(this).color(ContextCompat.getColor(this, R.color.colorIconGray)).sizeDp(16).icon(CommunityMaterial.Icon.cmd_clipboard_text));
    ivImages.setImageDrawable(new IconicsDrawable(this).color(ContextCompat.getColor(this, R.color.colorIconGray)).sizeDp(16).icon(CommunityMaterial.Icon.cmd_image_multiple));

    bRest.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        NewRowOrRestDialogFragment dialog = NewRowOrRestDialogFragment.newInstance(true);
        dialog.show(getSupportFragmentManager(), "AddRow");
      }
    });

    bRow.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        NewRowOrRestDialogFragment dialog = NewRowOrRestDialogFragment.newInstance(false);
        dialog.show(getSupportFragmentManager(), "AddRow");
      }
    });

    //TODO getExtra (edit record)
    rowSparseArray = new SparseArray<>();
    recyclerView.setLayoutManager(new LinearLayoutManager(AddRecordActivity.this, LinearLayoutManager.VERTICAL, false));
    recyclerViewAdapter = new RVA();
    recyclerView.setAdapter(recyclerViewAdapter);

    RealmResults<Tag> tags = Realm.getDefaultInstance().where(Tag.class).findAll();
    tagHashMap = new HashMap<>(tags.size());
    List<String> tagStringList = new ArrayList<>(tags.size());
    for (Tag t: tags) {
      tagStringList.add(t.getTag());
      tagHashMap.put(t.getTag(), t);
    }
    tagsAdapter = new ArrayAdapter(AddRecordActivity.this, android.R.layout.simple_list_item_1, tagStringList);
    tetTags.setAdapter(tagsAdapter);
    tetTags.setTagsListener(new TagsEditText.TagsEditListener() {
      @Override
      public void onTagsChanged(Collection<String> collection) {
        if (collection != null && collection.size() > 0) {
          Log.d(TAG, collection.toString());
          //TODO
        }
      }

      @Override
      public void onEditingFinished() {
        Log.d(TAG, "OnEditingFinished");
      }
    });

    tvStartDateTime.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        final Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
            new DatePickerDialog.OnDateSetListener() {
              @Override
              public void onDateSet(DatePickerDialog view, final int year, final int monthOfYear, final int dayOfMonth) {
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                    new TimePickerDialog.OnTimeSetListener() {
                      @Override
                      public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                        Log.d(TAG, "datetime: " + String.format("%d/%d/%d %d:%d:%d", year, monthOfYear, dayOfMonth, hourOfDay, minute, second));
                        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth, hourOfDay, minute);
                        startDateTime = calendar.getTimeInMillis();
                        if (DateFormat.is24HourFormat(AddRecordActivity.this)) {
                          tvStartDateTime.setText(String.format("%d/%d/%d %d:%d", year, monthOfYear + 1, dayOfMonth, hourOfDay, minute));
                        } else {
                          if (hourOfDay >= 12)
                            tvStartDateTime.setText(String.format("%d/%d/%d %d:%d PM", year, monthOfYear + 1, dayOfMonth, hourOfDay - 12, minute));
                          else
                            tvStartDateTime.setText(String.format("%d/%d/%d %d:%d AM", year, monthOfYear + 1, dayOfMonth, hourOfDay - 12, minute));
                        }
                      }
                    },
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    DateFormat.is24HourFormat(AddRecordActivity.this));
                tpd.setAccentColor(ContextCompat.getColor(AddRecordActivity.this, R.color.colorPrimary));
                tpd.show(getFragmentManager(), "Timepickerdialog");
              }
            },
            now.get(Calendar.YEAR),
            now.get(Calendar.MONTH),
            now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setAccentColor(ContextCompat.getColor(AddRecordActivity.this, R.color.colorPrimary));
        dpd.show(getFragmentManager(), "Datepickerdialog");
      }
    });
    
    floatingActionButton.setImageDrawable(new IconicsDrawable(AddRecordActivity.this).color(ContextCompat.getColor(AddRecordActivity.this, R.color.colorPrimary)).icon(CommunityMaterial.Icon.cmd_check));
    floatingActionButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Log.d(TAG, "submit?");
        /**
         * TODO
         * 1. validate everything
         *    1. startdatetime cannot be empty
         *    2. need to have at least 1 row of "row"
         * 2. use hashmap to turn strings to list of old and new tags
         * 3. images to byte[]
         * 4. realm transaction
         *    1. create new tags
         *    2. create new rows
         *    3. create new images
         *    4. create new record
         *    5. connecting everything to record
         */
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_add_record, menu);
    //TODO get global "Fix totals" default from SharePreference
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home)
      this.onBackPressed();
    else if (item.getItemId() == R.id.action_fix_totals) {
      Log.d(TAG, String.valueOf(item.isChecked()));
      item.setChecked(!item.isChecked());
      fixTotals = item.isChecked();
      if (!fixTotals)
        calculateNewAverages();
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onBackPressed() {
    new MaterialDialog.Builder(AddRecordActivity.this)
        .title("Leave")
        .content("Are you sure? Everything will be gone forever")
        .positiveText("Yes")
        .onAny(new MaterialDialog.SingleButtonCallback() {
          @Override
          public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            dialog.dismiss();
            if (which == DialogAction.POSITIVE) {
              AddRecordActivity.super.finish();
            }
          }
        })
        .negativeText("No")
        .show();
  }

  private void calculateNewAverages() {
    averageRating = 0;
    long totalStrokes = 0;
    totalDistance = 0;
    totalDuration = 0;
    for(int i = 0; i < rowSparseArray.size(); i++) {
      int key = rowSparseArray.keyAt(i);
      Row r = rowSparseArray.get(key);
      if (!r.isEasy()) {
        totalDistance += r.getDistance();
        totalDuration += r.getDuration();
        totalStrokes += r.getRating() * r.getDuration();
      }
    }

    try {
      averageRating = totalStrokes / totalDuration;
      Log.d(TAG, "new averages: " + averageRating + " " + totalDistance + " " + totalDuration);

      tvTotalTime.setText(Utils.generateDurationString(totalDuration, false));
      tvTotalDistance.setText(String.valueOf(totalDistance));
      tvAveragePace.setText(Utils.generatePaceString(totalDuration, totalDistance));
      tvAverageRating.setText(String.valueOf(averageRating));
    } catch (ArithmeticException e) {
      e.printStackTrace();    //Usually: divide by zero @ totalDuration
      tvTotalTime.setText(Utils.generateDurationString(0, false));
      tvTotalDistance.setText(String.valueOf(0));
    }
  }

  @Override
  public void onAddRow(Row row) {
    Log.d(TAG, "onAddRow: " + row.toString());
    row.setOrder(rowSparseArray.size());
    rowSparseArray.put(row.getOrderInt(), row);
    if (!fixTotals)
      calculateNewAverages();
    recyclerViewAdapter.notifyDataSetChanged();
  }

  @Override
  public void onEditRow(Row oldRow, Row newRow) {
    Log.d(TAG, "onEditRow;");
    rowSparseArray.put(oldRow.getOrderInt(), newRow);
    if (!fixTotals)
      calculateNewAverages();
    recyclerViewAdapter.notifyDataSetChanged();
  }

  @Override
  public void onCancel() {
    Log.d(TAG, "onCancel;");
  }

  @Override
  public void onDelete(Row row) {
    Log.d(TAG, "onDelete;");
    rowSparseArray.remove(row.getOrderInt());
    if (!fixTotals)
      calculateNewAverages();
    recyclerViewAdapter.notifyDataSetChanged();
  }

  public class RVVH extends RecyclerView.ViewHolder {
    @BindView(R.id.tvTime)    TextView tvTime;
    @BindView(R.id.tvMeter)   TextView tvMeter;
    @BindView(R.id.tvPace)    TextView tvPace;
    @BindView(R.id.tvRating)  TextView tvRating;

    public RVVH(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  public class RVA extends RecyclerView.Adapter<RVVH> {

    public RVA() {
    }

    @Override
    public RVVH onCreateViewHolder(ViewGroup parent, int viewType) {
      return new RVVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_row, parent, false));
    }

    @Override
    public void onBindViewHolder(RVVH holder, int position) {
      Log.d(TAG, "binding: " + position);
      Row row = rowSparseArray.get(holder.getAdapterPosition());

      holder.tvTime.setText(Utils.generateDurationString(row));
      ArrayList<Long> previousDistances = new ArrayList<>();
      for (int i = holder.getAdapterPosition()-1; i >= 0; i--) {
        previousDistances.add(rowSparseArray.get(i).getDistance());
      }
      holder.tvMeter.setText(Utils.generateDistanceString(row.getDistance(), previousDistances));
      if (!row.isEasy()) {
        holder.tvPace.setText(Utils.generatePaceString(row));
        holder.tvRating.setText(String.valueOf(row.getRating()));
      }
    }

    @Override
    public int getItemCount() {
      return rowSparseArray.size();
    }
  }

}
