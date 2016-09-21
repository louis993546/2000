package io.github.louistsaitszho.erg2000.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.louistsaitszho.erg2000.R;

/**
 * Created by Louis on 21/9/2016.
 */

public class ItemRowRowViewHolder extends RecyclerView.ViewHolder {
  @BindView(R.id.tvTime)    public TextView tvTime;
  @BindView(R.id.tvMeter)   public TextView tvMeter;
  @BindView(R.id.tvPace)    public TextView tvPace;
  @BindView(R.id.tvRating)  public TextView tvRating;

  public ItemRowRowViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }
}
