package io.github.louistsaitszho.erg2000.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.github.louistsaitszho.erg2000.R;
import io.github.louistsaitszho.erg2000.Utils;
import io.github.louistsaitszho.erg2000.realm.realmObject.Row;
import io.github.louistsaitszho.erg2000.viewholder.ItemRowRowViewHolder;

/**
 * Created by Louis on 22/9/2016.
 */

public class ItemRowRowAdapter extends RecyclerView.Adapter<ItemRowRowViewHolder> {
  public final static String TAG = ItemRowRowAdapter.class.getSimpleName();

  SparseArray<Row> rowSparseArray;

  @Override
  public ItemRowRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ItemRowRowViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_row, parent, false));
  }

  @Override
  public void onBindViewHolder(ItemRowRowViewHolder holder, int position) {
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

  public SparseArray<Row> getRowSparseArray() {
    return rowSparseArray;
  }

  public void setRowSparseArray(SparseArray<Row> rowSparseArray) {
    this.rowSparseArray = rowSparseArray;
  }

  public void putToRowSparseArray(int orderInt, Row row) {
    this.rowSparseArray.put(orderInt, row);
  }

  public void removeFromRowSparseArray(int orderInt) {
    this.rowSparseArray.remove(orderInt);
  }
}
