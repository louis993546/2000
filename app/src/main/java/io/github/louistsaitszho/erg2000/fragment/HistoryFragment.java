package io.github.louistsaitszho.erg2000.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.louistsaitszho.erg2000.R;
import io.github.louistsaitszho.erg2000.RealmController;
import io.github.louistsaitszho.erg2000.ScrollToTop;
import io.github.louistsaitszho.erg2000.Utils;
import io.github.louistsaitszho.erg2000.realmObject.Record;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.Sort;

public class HistoryFragment extends Fragment implements ScrollToTop {
  public static final String TAG = HistoryFragment.class.getSimpleName();

  @BindView(R.id.recyclerView) RecyclerView recyclerView;

  RealmResults<Record> records;

  public static HistoryFragment newInstance() {
    HistoryFragment fragment = new HistoryFragment();
    return fragment;
  }

  public HistoryFragment() {

  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    records = RealmController.with(this).allRecords().sort("startDateTime", Sort.DESCENDING);
    Log.d(TAG, "size: " + String.valueOf(records.size()));
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_history, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onResume() {
    super.onResume();
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setAdapter(new RRVA(getContext(), records, true));
//    recyclerView.setAdapter(new RVA());
  }

  @Override
  public boolean scrollToTop() {
    if (recyclerView != null) {
      recyclerView.smoothScrollToPosition(0);
      return true;
    }
    return false;
  }

  public class RVVH extends RecyclerView.ViewHolder {
    @BindView(R.id.tvDuration) TextView tvDuration;
    @BindView(R.id.tvRating) TextView tvRating;
    @BindView(R.id.tvPace) TextView tvPace;
    @BindView(R.id.DistanceTV) TextView tvDistance;
    @BindView(R.id.startDateTimeTV) TextView tvStartDateTime;
    @BindView(R.id.llCard) LinearLayout llCard;
    @BindView(R.id.tvEnd) TextView tvEnd;

    public RVVH(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  private class RRVA extends RealmRecyclerViewAdapter<Record, RVVH> {

    public RRVA(@NonNull Context context, @Nullable OrderedRealmCollection<Record> data, boolean autoUpdate) {
      super(context, data, autoUpdate);
    }

    @Override
    public RVVH onCreateViewHolder(ViewGroup parent, int viewType) {
      return new RVVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_ergo_record, parent, false));
    }

    @Override
    public void onBindViewHolder(RVVH holder, int position) {
      if (records != null && holder.getAdapterPosition() < records.size()) {
        Record record = records.get(holder.getAdapterPosition());
        Log.d(TAG, "inflating: " + record.toString());
        holder.llCard.setVisibility(View.VISIBLE);
        holder.tvEnd.setVisibility(View.GONE);
        holder.tvDuration.setText(Utils.generateDurationString(record));
        holder.tvRating.setText(String.valueOf(record.getAverageRating()));
        holder.tvPace.setText(Utils.generatePaceString(record));
        holder.tvDistance.setText(String.valueOf(record.getTotalDistance()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy/M/d kk:mm");
        holder.tvStartDateTime.setText(simpleDateFormat.format(record.getStartDateTime()));
      } else {
        holder.llCard.setVisibility(View.GONE);
        holder.tvEnd.setVisibility(View.VISIBLE);
        if (position == 0) {
          holder.tvEnd.setText(R.string.no_records);
        } else {
          holder.tvEnd.setText(R.string.end_of_list);
        }
      }
    }

    @Override
    public int getItemCount() {
      return super.getItemCount()+1;
    }
  }

  /**
   * TODO encourage user to add first item
   * TODO add "No more item" at the bottom
   * TODO update the card UI
   */
  private class RVA extends RecyclerView.Adapter<RVVH> {

    public RVA() {
    }

    @Override
    public RVVH onCreateViewHolder(ViewGroup parent, int viewType) {
      return new RVVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_ergo_record, parent, false));
    }

    @Override
    public void onBindViewHolder(RVVH holder, int position) {
      if (records != null && holder.getAdapterPosition() < records.size()) {
        Record record = records.get(holder.getAdapterPosition());
        Log.d(TAG, "inflating: " + record.toString());
        holder.llCard.setVisibility(View.VISIBLE);
        holder.tvEnd.setVisibility(View.GONE);
        holder.tvDuration.setText(Utils.generateDurationString(record));
        holder.tvRating.setText(String.valueOf(record.getAverageRating()));
        holder.tvPace.setText(Utils.generatePaceString(record));
        holder.tvDistance.setText(String.valueOf(record.getTotalDistance()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy/M/d kk:mm");
        holder.tvStartDateTime.setText(simpleDateFormat.format(record.getStartDateTime()));
      } else {
        holder.llCard.setVisibility(View.GONE);
        holder.tvEnd.setVisibility(View.VISIBLE);
        if (position == 0) {
          holder.tvEnd.setText(R.string.no_records);
        } else {
          holder.tvEnd.setText(R.string.end_of_list);
        }
      }
    }

    @Override
    public int getItemCount() {
      if (records == null)
        return 1;             //The nothing card
      else
        return (records.size() + 1);
    }
  }

}
