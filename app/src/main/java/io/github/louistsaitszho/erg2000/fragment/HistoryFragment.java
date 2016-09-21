package io.github.louistsaitszho.erg2000.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.louistsaitszho.erg2000.R;
import io.github.louistsaitszho.erg2000.adapter.CardErgoRecordAdapter;
import io.github.louistsaitszho.erg2000.interfaces.ScrollToTop;
import io.github.louistsaitszho.erg2000.realm.RealmController;
import io.realm.Sort;

public class HistoryFragment extends Fragment implements ScrollToTop {
  public static final String TAG = HistoryFragment.class.getSimpleName();

  @BindView(R.id.recyclerView) RecyclerView recyclerView;

  CardErgoRecordAdapter recyclerViewAdapter;

  public static HistoryFragment newInstance() {
    HistoryFragment fragment = new HistoryFragment();
    return fragment;
  }

  public HistoryFragment() {

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
    recyclerViewAdapter = new CardErgoRecordAdapter(getContext(), RealmController.with(this).allRecords().sort("startDateTime", Sort.DESCENDING));
    recyclerView.setAdapter(recyclerViewAdapter);
  }

  @Override
  public boolean scrollToTop() {
    if (recyclerView != null) {
      recyclerView.smoothScrollToPosition(0);
      return true;
    }
    return false;
  }
}
