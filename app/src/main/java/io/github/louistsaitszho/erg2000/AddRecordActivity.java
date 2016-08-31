package io.github.louistsaitszho.erg2000;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.louistsaitszho.erg2000.fragment.NewRowOrRestDialogFragment;
import io.github.louistsaitszho.erg2000.realmObject.Row;

public class AddRecordActivity extends AppCompatActivity implements NewRowOrRestDialogFragment.DialogListener{
  public static final String TAG = AddRecordActivity.class.getSimpleName();
  SparseArray<Row> rowSparseArray;

  long averageRating = 0;
  long totalDistance = 0;
  long totalDuration = 0;

  @BindView(R.id.toolbar)             Toolbar toolbar;

  @BindView(R.id.tvTime)        TextView tvTotalTime;
  @BindView(R.id.tvMeter)       TextView tvTotalDistance;
  @BindView(R.id.tvPace)        TextView tvAveragePace;
  @BindView(R.id.tvRating)      TextView tvAverageRating;
  @BindView(R.id.rlRows)        RecyclerView recyclerView;
  @BindView(R.id.bRest)         Button bRest;
  @BindView(R.id.bRow)          Button bRow;

  @BindView(R.id.ivEventDescription)  ImageView ivEventDescription;
  @BindView(R.id.ivStartDateTime)     ImageView ivStartDateTime;
  @BindView(R.id.ivTags)              ImageView ivTags;
  @BindView(R.id.ivRemark)            ImageView ivRemark;
  @BindView(R.id.ivImages)            ImageView ivImages;
  @BindView(R.id.rlImage)             RelativeLayout rlImage;

  RVA adapter;

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
    adapter = new RVA();
    recyclerView.setAdapter(adapter);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    return super.onCreateOptionsMenu(menu);
    //TODO confirm button
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home)
      this.onBackPressed();
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
    double totalStrokes = 0;
    totalDistance = 0;
    totalDuration = 0;
    for(int i = 0; i < rowSparseArray.size(); i++) {
      int key = rowSparseArray.keyAt(i);
      Row r = rowSparseArray.get(key);
      if (!r.isEasy()) {
        totalDistance += r.getDistance();
        totalDuration += r.getDuration();
        totalStrokes += (r.getDuration() / Consts.MS_IN_MINUTE) * r.getRating();
      }
    }
    averageRating = Math.round(totalStrokes / (totalDuration / Consts.MS_IN_MINUTE));
    Log.d(TAG, "new averages: " + averageRating + " " + totalDistance + " " + totalDuration);

    tvTotalTime.setText(Utils.generateDurationString(totalDuration, false));
    tvTotalDistance.setText(String.valueOf(totalDistance));
    tvAveragePace.setText(Utils.generatePaceString(totalDuration, totalDistance));
    tvAverageRating.setText(String.valueOf(averageRating));
  }

  @Override
  public void onAddRow(Row row) {
    Log.d(TAG, "onAddRow: " + row.toString());
    row.setOrder(rowSparseArray.size());
    rowSparseArray.put(row.getOrderInt(), row);
    calculateNewAverages();
    adapter.notifyDataSetChanged();
  }

  @Override
  public void onEditRow(Row oldRow, Row newRow) {
    Log.d(TAG, "onEditRow;");

  }

  @Override
  public void onCancel() {
    Log.d(TAG, "onCancel;");
  }

  @Override
  public void onDelete(Row row) {
    Log.d(TAG, "onDelete;");

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
