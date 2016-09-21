package io.github.louistsaitszho.erg2000.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.db.chart.Tools;
import com.db.chart.model.LineSet;
import com.db.chart.view.AxisController;
import com.db.chart.view.LineChartView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.louistsaitszho.erg2000.R;
import io.github.louistsaitszho.erg2000.interfaces.ScrollToTop;

public class StatisticFragment extends Fragment implements ScrollToTop{

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

  @Override
  public boolean scrollToTop() {
    if (recyclerView != null) {
      recyclerView.smoothScrollToPosition(0);
      return true;
    }
    return false;
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

      LineChartView mChart = new LineChartView(getContext());
      final String[] mLabels = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep"};
      final float[][] mValues = {{0f, 2f, 1.4f, 4.f, 3.5f, 4.3f, 2f, 4f, 6.f},
          {1.5f, 2.5f, 1.5f, 5f, 4f, 5f, 4.3f, 2.1f, 1.4f}};
      LineSet dataset = new LineSet(mLabels, mValues[0]);
      dataset
          .setColor(Color.parseColor("#53c1bd"))
          .setFill(Color.parseColor("#3d6c73"))
          .setGradientFill(new int[]{Color.parseColor("#364d5a"), Color.parseColor("#3f7178")}, null);
      mChart.addData(dataset);

      mChart.setBorderSpacing(1)
          .setXLabels(AxisController.LabelPosition.NONE)
          .setYLabels(AxisController.LabelPosition.NONE)
          .setXAxis(true)
          .setYAxis(false)
          .setBorderSpacing(Tools.fromDpToPx(5));
      mChart.show();
      holder.flContainer.addView(mChart);
      holder.flContainer.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          //TODO
          Toast.makeText(getContext(), "Coming soon!", Toast.LENGTH_LONG).show();
        }
      });
    }

    @Override
    public int getItemCount() {
      //TODO get sharepreference
      //TODO an extra bottom one (end of page + avoid things hide underneath fab)
      return 3;
    }
  }

}
