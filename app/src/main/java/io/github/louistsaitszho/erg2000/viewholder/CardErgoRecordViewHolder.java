package io.github.louistsaitszho.erg2000.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.louistsaitszho.erg2000.R;

/**
 * TODO split this into 2 ViewHolders: Record & EOL
 * Created by Louis on 22/9/2016.
 */

public class CardErgoRecordViewHolder extends RecyclerView.ViewHolder {
  @BindView(R.id.tvDuration)      public TextView tvDuration;
  @BindView(R.id.tvRating)        public TextView tvRating;
  @BindView(R.id.tvPace)          public TextView tvPace;
  @BindView(R.id.DistanceTV)      public TextView tvDistance;
  @BindView(R.id.startDateTimeTV) public TextView tvStartDateTime;
  @BindView(R.id.llCard)          public LinearLayout llCard;
  @BindView(R.id.tvEnd)           public TextView tvEnd;

  public CardErgoRecordViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }
}
