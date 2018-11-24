package com.w8er.android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.w8er.android.R;
import com.w8er.android.model.Review;
import com.willy.ratingbar.BaseRatingBar;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.w8er.android.utils.RatingUtils.roundToHalf;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    private List<Review> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;


    // data is passed into the constructor
    public ReviewsAdapter(Context context, List<Review> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext =context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.feed_review_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.msg.setText(mData.get(position).getMessage());
//        holder.userName.setText(mData.get(position).getGiver());
        holder.ratingBar.setRating(mData.get(position).getAmount());

        //Date
        Date date = mData.get(position).getDate();
        if (date != null) {
            Format formatter = new SimpleDateFormat("HH:mm EEE, d MMM yyyy");
            String s = formatter.format(date);
            holder.mDate.setText(s);
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    public List<Review> getmData() {
        return mData;
    }

    public void setmData(List<Review> mData) {
        this.mData = mData;
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView msg;
        TextView userName;
        TextView mDate;
        BaseRatingBar ratingBar;

        ViewHolder(View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.content);
            userName = itemView.findViewById(R.id.user_name);
            mDate = itemView.findViewById(R.id.creation_date);
            ratingBar = itemView.findViewById(R.id.simple_rating_bar);
            ratingBar.setEnabled(false);
            ratingBar.setClickable(false);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }



    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
