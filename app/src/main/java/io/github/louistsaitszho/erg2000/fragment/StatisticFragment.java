package io.github.louistsaitszho.erg2000.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.db.chart.model.LineSet;
import com.db.chart.model.Point;
import com.db.chart.view.LineChartView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.louistsaitszho.erg2000.R;

public class StatisticFragment extends Fragment {

  @BindView(R.id.recyclerView) RecyclerView recyclerView;

  public StatisticFragment() {
    // Required empty public constructor
  }

  public static StatisticFragment newInstance() {
    StatisticFragment fragment = new StatisticFragment();
    Bundle args = new Bundle();
//    args.putString(ARG_PARAM1, param1);
//    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
//      mParam1 = getArguments().getString(ARG_PARAM1);
//      mParam2 = getArguments().getString(ARG_PARAM2);
    }

    //TODO get list of best of to get
    //TODO generate the data
    //set adapter
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_statistic, container, false);
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
    @BindView(R.id.tvChartTitle) TextView tvTitle;
    @BindView(R.id.flChartContainer) FrameLayout flContainer;

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
      return new RVVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_best_of_chart, parent, false));
    }

    @Override
    public void onBindViewHolder(RVVH holder, int position) {
      holder.tvTitle.setText("Best 500m");
      LineChartView lineChartView = new LineChartView(getContext());
      RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500);
      LineSet dataset = new LineSet(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"}, new float[]{1.0f, 1.0f, 2.0f, 3.0f, 5.0f, 8.0f, 8.13f, 8.21f, 8f, 3f, 1f});
      dataset.addPoint(new Point("12", 2f));
      dataset.addPoint("13", 3f);
      dataset.setSmooth(true);
      lineChartView.setLayoutParams(params);
      holder.flContainer.addView(lineChartView);
    }

    @Override
    public int getItemCount() {
      //TODO get sharepreference
      //TODO an extra bottom one (end of page + avoid things hide underneath fab)
      return 3;
    }
  }

}
