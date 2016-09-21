package io.github.louistsaitszho.erg2000.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.louistsaitszho.erg2000.R;
import io.github.louistsaitszho.erg2000.realm.realmObject.Record;

/**
 * Created by Louis on 21/9/2016.
 */

public class FloatingRecordDetailsDialogFragment extends DialogFragment {
  public final static String TAG = FloatingRecordDetailsDialogFragment.class.getSimpleName();

  public final static String EXTRA_RECORD = "THE_RECORD";

  Record thisRecord;

  @BindView(R.id.tvTime)    TextView tvTotalTime;
  @BindView(R.id.tvMeter)   TextView tvTotalDistance;
  @BindView(R.id.tvPace)    TextView tvAveragePace;
  @BindView(R.id.tvRating)  TextView tvAverageRating;
  @BindView(R.id.rlRows)    RecyclerView recyclerView;

  public FloatingRecordDetailsDialogFragment() {

  }

  public static FloatingRecordDetailsDialogFragment newInstance(Record record) {
    FloatingRecordDetailsDialogFragment instance = new FloatingRecordDetailsDialogFragment();
    Bundle bundle = new Bundle();
    bundle.putSerializable(EXTRA_RECORD, record);
    instance.setArguments(bundle);
    return instance;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle bundle = getArguments();
    if (bundle != null) {
      Serializable serializable = bundle.getSerializable(EXTRA_RECORD);
      if (serializable instanceof Record)
        thisRecord = (Record) serializable;
    }
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    LayoutInflater inflater = getActivity().getLayoutInflater();
    View v = inflater.inflate(R.layout.dialog_detailed_record, null);
    ButterKnife.bind(this, v);

    //TODO inflate recyclerview >> TODO extract that recyclerview out from add record activity

    return builder.create();
  }

  @Override
  public void onStart() {
    super.onStart();
  }
}
