package io.github.louistsaitszho.erg2000;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.louistsaitszho.erg2000.realmObject.Row;

public class AddRecordActivity extends AppCompatActivity {
  public static final String TAG = AddRecordActivity.class.getSimpleName();
  List<Row> rowList = new ArrayList<>();

  long averageRating = 0;
  long totalDistance = 0;
  long totalDuration = 0;

  @BindView(R.id.toolbar) Toolbar toolbar;

  @BindView(R.id.tvDuration)      TextView tvDuration;
  @BindView(R.id.tvRating)        TextView tvRating;
  @BindView(R.id.p5mValueTV)      TextView tvPer500m;
  @BindView(R.id.DistanceTV)      TextView tvDistance;
  @BindView(R.id.llDateTime)      LinearLayout llDateTime;
  @BindView(R.id.tvStartDateTime) TextView tvStartDateTime;
  @BindView(R.id.tietRemark)      TextInputEditText tietRemark;
  @BindView(R.id.rows)            LinearLayout llRowsContainer;
  @BindView(R.id.fam)             FloatingActionsMenu fam;
  @BindView(R.id.fabRow)          FloatingActionButton fabRow;
  @BindView(R.id.fabRest)         FloatingActionButton fabRest;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_record);
    ButterKnife.bind(this);

    toolbar.setTitle("New Record");

    //TODO back button

    fabRow.setImageDrawable(new IconicsDrawable(this).color(ContextCompat.getColor(AddRecordActivity.this, R.color.colorPrimary)).icon(CommunityMaterial.Icon.cmd_rowing).actionBar());
    fabRow.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        new MaterialDialog.Builder(AddRecordActivity.this)
            .title("Row")
            .positiveText("Add")
            .positiveColor(getResources().getColor(R.color.colorPrimary))
            .onPositive(new MaterialDialog.SingleButtonCallback() {
              @Override
              public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                Row row = ((NewRowOrRestCustomView) dialog.getCustomView()).getData();
                Log.d(TAG, row.toString());
                rowList.add(row);
                llRowsContainer.addView(new CardCustomView(AddRecordActivity.this, row));
                calculateNewAverages();
                updateTotals();
              }
            })
            .negativeColor(getResources().getColor(android.R.color.darker_gray))
            .negativeText("Discard")
            .customView(
                new NewRowOrRestCustomView(AddRecordActivity.this, false, rowList.size()-1),
                false)
            .show();
        fam.collapse();
      }
    });

    fabRest.setImageDrawable(new IconicsDrawable(this).color(ContextCompat.getColor(AddRecordActivity.this, R.color.colorPrimary)).icon(CommunityMaterial.Icon.cmd_cup_water).actionBar());
    fabRest.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        new MaterialDialog.Builder(AddRecordActivity.this)
            .title("Rest")
            .positiveText("Add")
            .positiveColor(getResources().getColor(R.color.colorPrimary))
            .onPositive(new MaterialDialog.SingleButtonCallback() {
              @Override
              public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                Row row = ((NewRowOrRestCustomView) dialog.getCustomView()).getData();
                Log.d(TAG, row.toString());
                rowList.add(row);
                llRowsContainer.addView(new CardCustomView(AddRecordActivity.this, row));
                calculateNewAverages();
                updateTotals();
              }
            })
            .negativeColor(getResources().getColor(android.R.color.darker_gray))
            .negativeText("Discard")
            .customView(
                new NewRowOrRestCustomView(AddRecordActivity.this, true, rowList.size()-1),
                false)
            .show();
        fam.collapse();
      }
    });
  }

  private void updateTotals() {
    tvRating.setText(String.valueOf(averageRating));
    tvDistance.setText(String.valueOf(totalDistance));
    tvDuration.setText(Utils.msToDurationString(totalDuration));
    if (totalDistance > 0)
      tvPer500m.setText(Utils.generatePer500String(totalDuration, totalDistance));
  }

  private void calculateNewAverages() {
    averageRating = 0;
    double totalStrokes = 0;
    totalDistance = 0;
    totalDuration = 0;
    for (Row r: rowList) {
      if (!r.isEasy()) {
        totalDistance += r.getDistance();
        totalDuration += r.getDuration();
        totalStrokes += (r.getDuration() / Consts.MS_IN_MINUTE) * r.getRating();
      }
    }
    averageRating = Math.round(totalStrokes / (totalDuration / Consts.MS_IN_MINUTE));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    return super.onCreateOptionsMenu(menu);
    //TODO confirm button
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    return super.onOptionsItemSelected(item);
    //TODO
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    //TODO confirm discard dialog
  }

  public class CardCustomView extends CardView {
    @BindView(R.id.tvDuration)    TextView tvDuration;
    @BindView(R.id.tvRating)      TextView tvRating;
    @BindView(R.id.tvRatingUnit)  TextView tvRatingUnit;
    @BindView(R.id.p5mValueTV)    TextView tvP500m;
    @BindView(R.id.DistanceTV)    TextView tvDistance;

    @BindView(R.id.llPer500m)     LinearLayout llPer500m;
    @BindView(R.id.viewLine)      View viewLine;

    public CardCustomView(Context context, Row row) {
      super(context);
      LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      View view = inflater.inflate(R.layout.card_ergo_record, this, true);
      ButterKnife.bind(this, view);

      //TODO turn long to time/distance/etc string
      tvDuration.setText(Utils.msToDurationString(row.getDuration()));
      if (row.isEasy()) {
        tvRatingUnit.setText("Rest");
        tvRating.setVisibility(INVISIBLE);
        llPer500m.setVisibility(GONE);
        viewLine.setVisibility(GONE);
      } else {
        tvRating.setText(String.valueOf(row.getRating()));
        tvP500m.setText(Utils.generatePer500String(row.getDuration(), row.getDistance()));
      }
      tvDistance.setText(String.valueOf(row.getDistance()));
    }

  }
}
