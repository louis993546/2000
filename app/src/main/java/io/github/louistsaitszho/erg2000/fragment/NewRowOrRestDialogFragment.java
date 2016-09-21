package io.github.louistsaitszho.erg2000.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.carbswang.android.numberpickerview.library.NumberPickerView;
import io.github.louistsaitszho.erg2000.R;
import io.github.louistsaitszho.erg2000.Utils;
import io.github.louistsaitszho.erg2000.realm.realmObject.Row;

/**
 * The input dialog for adding/editing/deleting a row of rowing/rest record
 * Created by Louis on 26/8/2016.
 */

public class NewRowOrRestDialogFragment extends DialogFragment {
  public final static String TAG = NewRowOrRestDialogFragment.class.getSimpleName();

  public final static String EXTRA_IS_REST = "isRest";
  public final static String EXTRA_ROW = "row";

  boolean isRest = false;
  Row thisRow;

  @BindView(R.id.npvHour)       NumberPickerView npvHour;
  @BindView(R.id.npvMinute)     NumberPickerView npvMinute;
  @BindView(R.id.npvSecond)     NumberPickerView npvSecond;
  @BindView(R.id.npvDecisecond) NumberPickerView npvDecisecond;
  @BindView(R.id.etDistance)    EditText etDistance;
  @BindView(R.id.tvRating)      TextView tvRating;
  @BindView(R.id.npvRating)     NumberPickerView npvRating;

  NewRowOrRestDialogFragmentListener listener;

  public NewRowOrRestDialogFragment() {

  }

  public static NewRowOrRestDialogFragment newInstance(boolean isRest) {
    NewRowOrRestDialogFragment f = new NewRowOrRestDialogFragment();
    Bundle bundle = new Bundle();
    bundle.putBoolean("isRest", isRest);
    f.setArguments(bundle);
    return f;
  }

  public static NewRowOrRestDialogFragment newInstance(boolean isRest, Row row) {
    NewRowOrRestDialogFragment f = new NewRowOrRestDialogFragment();
    Bundle bundle = new Bundle();
    bundle.putBoolean(EXTRA_IS_REST, isRest);
    bundle.putSerializable(EXTRA_ROW, row);
    f.setArguments(bundle);
    return f;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle b = getArguments();
    if (b != null) {
      isRest = b.getBoolean(EXTRA_IS_REST);
      Serializable s = b.getSerializable(EXTRA_ROW);
      if (s != null && s instanceof Row)
        thisRow = (Row) s;
    }
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof NewRowOrRestDialogFragmentListener)
      listener = (NewRowOrRestDialogFragmentListener) context;
  }

  @Override
  public void onDetach() {
    super.onDetach();
    if (listener != null)
      listener = null;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    LayoutInflater inflater = getActivity().getLayoutInflater();
    View v = inflater.inflate(R.layout.dialog_new_row_or_rest, null);
    ButterKnife.bind(this, v);

    npvHour.setDisplayedValues(Utils.zeroTo(100));
    npvHour.setMinValue(0);
    npvHour.setMaxValue(99);
    npvHour.setWrapSelectorWheel(false);

    npvMinute.setDisplayedValues(Utils.zeroTo(60));
    npvMinute.setMinValue(0);
    npvMinute.setMaxValue(59);
    npvMinute.setWrapSelectorWheel(false);

    npvSecond.setDisplayedValues(Utils.zeroTo(60));
    npvSecond.setMinValue(0);
    npvSecond.setMaxValue(59);
    npvSecond.setWrapSelectorWheel(false);

    npvDecisecond.setDisplayedValues(Utils.zeroTo(10));
    npvDecisecond.setMinValue(0);
    npvDecisecond.setMaxValue(9);
    npvDecisecond.setWrapSelectorWheel(false);

    if (isRest) {
      tvRating.setVisibility(View.GONE);
      npvRating.setVisibility(View.GONE);
    } else {
      npvRating.setDisplayedValues(Utils.zeroTo(100));
      npvRating.setMinValue(0);
      npvRating.setMaxValue(99);
      npvRating.setValue(20);
      npvRating.setWrapSelectorWheel(false);
    }

    builder.setView(v)
        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            Log.d("qqq", "1");
          }
        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            Log.d("qqq", "2");
          }
        });

    if (thisRow != null) {
      builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
          Log.d("qqq", "3");
        }
      });
    }

    return builder.create();
  }

  @Override
  public void onStart() {
    super.onStart();
    AlertDialog d = (AlertDialog) getDialog();
    if (d != null)
    {
      Button confirm = d.getButton(Dialog.BUTTON_POSITIVE);
      confirm.setTextColor(getResources().getColor(R.color.colorPrimary));
      confirm.setOnClickListener(new View.OnClickListener()
      {
        @Override
        public void onClick(View v)
        {
          Log.d(TAG, String.valueOf(npvHour.getValue()));
          Log.d(TAG, String.valueOf(npvMinute.getValue()));
          Log.d(TAG, String.valueOf(npvSecond.getValue()));
          Log.d(TAG, String.valueOf(npvDecisecond.getValue()));
          Log.d(TAG, String.valueOf(npvRating.getValue()));

          try {
            int val = Integer.parseInt(etDistance.getText().toString());
            Log.d(TAG, String.valueOf(val));

            if (listener != null)
              listener.onAddRow(new Row(isRest, val, Utils.hmsdToLong(npvHour.getValue(), npvMinute.getValue(), npvSecond.getValue(), npvDecisecond.getValue()), npvRating.getValue()));
            dismiss();
          } catch (NumberFormatException e) {
            e.printStackTrace();
            etDistance.setError("Invalid");
          }
        }
      });

      Button cancel = d.getButton(DialogInterface.BUTTON_NEGATIVE);
      cancel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          if (listener != null)
            listener.onCancel();
          dismiss();
        }
      });

      Button delete = d.getButton(DialogInterface.BUTTON_NEUTRAL);
      delete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          new MaterialDialog.Builder(getContext())
              .title("Delete")
              .content("Are you sure? It will be gone forever.")
              .positiveText("Yes")
              .onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                  if (listener != null)
                    listener.onDelete(thisRow);
                  dialog.dismiss();
                  NewRowOrRestDialogFragment.this.dismiss();
                }
              })
              .negativeText("No")
              .show();
        }
      });
    }  }

  public interface NewRowOrRestDialogFragmentListener {
    void onAddRow(Row row);
    void onEditRow(Row oldRow, Row newRow);
    void onCancel();
    void onDelete(@Nullable Row row);
  }
}
