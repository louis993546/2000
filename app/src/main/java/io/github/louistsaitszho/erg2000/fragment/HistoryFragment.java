package io.github.louistsaitszho.erg2000.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.louistsaitszho.erg2000.R;

public class HistoryFragment extends Fragment {
  public static final String TAG = HistoryFragment.class.getSimpleName();

  @BindView(R.id.recyclerView) RecyclerView recyclerView;

  public static HistoryFragment newInstance() {
    HistoryFragment fragment = new HistoryFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  public HistoryFragment() {

  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
    }
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
    recyclerView.setAdapter(new RVA());
  }

  public class RVVH extends RecyclerView.ViewHolder {
    @BindView(R.id.DurationTV) TextView tvDuration;
    @BindView(R.id.RatingTV) TextView tvRating;
    @BindView(R.id.p5mValueTV) TextView tvP5mValue;
    @BindView(R.id.DistanceTV) TextView tvDistance;
    @BindView(R.id.startDateTimeTV) TextView tvStartDateTime;
    @BindView(R.id.llCard) LinearLayout llCard;
    @BindView(R.id.tvEnd) TextView tvEnd;

    public RVVH(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  /**
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
      if (position >= 10) {
        holder.llCard.setVisibility(View.GONE);
        holder.tvEnd.setVisibility(View.VISIBLE);
      } else {
        holder.llCard.setVisibility(View.VISIBLE);
        holder.tvEnd.setVisibility(View.GONE);
        holder.tvDuration.setText("0:08:00");
        holder.tvRating.setText("20 s/m");
        holder.tvP5mValue.setText("02:00.0");
        holder.tvDistance.setText("2000 m");
        holder.tvStartDateTime.setText(String.valueOf(position));
      }
    }

    @Override
    public int getItemCount() {
      //TODO get db
      return (10 + 1);
    }
  }

}
