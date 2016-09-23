package io.github.louistsaitszho.erg2000.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

import io.github.louistsaitszho.erg2000.Consts;
import io.github.louistsaitszho.erg2000.R;
import io.github.louistsaitszho.erg2000.Utils;
import io.github.louistsaitszho.erg2000.activity.ViewRecordActivity;
import io.github.louistsaitszho.erg2000.realm.realmObject.Record;
import io.github.louistsaitszho.erg2000.viewholder.CardErgoRecordViewHolder;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * TODO update new UI
 * 1. Display tags
 * 2. Better ways to display date time
 * 3. It looks kinda ugly in 2016
 * Created by Louis on 22/9/2016.
 */

public class CardErgoRecordAdapter extends RealmRecyclerViewAdapter<Record, CardErgoRecordViewHolder> {
  public static final String TAG = CardErgoRecordAdapter.class.getSimpleName();

  boolean pressing = false;

  public CardErgoRecordAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<Record> data) {
    super(context, data, true);
  }

  @Override
  public CardErgoRecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new CardErgoRecordViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_ergo_record, parent, false));
  }

  @Override
  public void onBindViewHolder(CardErgoRecordViewHolder holder, int position) {
    if (getData() != null && holder.getAdapterPosition() < getItemCount()-1) {
      final Record record = getItem(holder.getAdapterPosition());
      Log.d(TAG, "inflating: " + record.toString());
      holder.llCard.setVisibility(View.VISIBLE);
      holder.tvEnd.setVisibility(View.GONE);
      holder.tvDuration.setText(Utils.generateDurationString(record));
      holder.tvRating.setText(String.valueOf(record.getAverageRating()));
      holder.tvPace.setText(Utils.generatePaceString(record));
      holder.tvDistance.setText(String.valueOf(record.getTotalDistance()));
      holder.tvStartDateTime.setText(Utils.generateStartDateTimeString(record.getStartDateTime(), Locale.getDefault()));

      //TODO 3d touch details
//      holder.llCard.setOnLongClickListener(new View.OnLongClickListener() {
//        @Override
//        public boolean onLongClick(View view) {
//          Log.d(TAG, "long clicking");
//          pressing = true;
//          return false;
//        }
//      });
//      holder.llCard.setOnTouchListener(new View.OnTouchListener() {
//        @Override
//        public boolean onTouch(View view, MotionEvent motionEvent) {
//          if ((motionEvent.getAction() == MotionEvent.ACTION_UP) && (pressing)) {
//            Log.d(TAG, "release");
//            pressing = false;
//          }
//          return false;
//        }
//      });
      holder.llCard.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          Log.d(TAG, "the record: " + record.toString());
          Intent intent = new Intent(view.getContext(), ViewRecordActivity.class);
          intent.putExtra(Consts.EXTRA_RECORD_ID, record.getId());
          view.getContext().startActivity(intent);
        }
      });
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
