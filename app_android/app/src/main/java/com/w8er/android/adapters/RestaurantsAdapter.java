package com.w8er.android.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.w8er.android.R;
import com.w8er.android.model.Restaurant;
import com.willy.ratingbar.BaseRatingBar;

import java.util.List;

import static com.w8er.android.utils.DataFormatter.getRestStatus;
import static com.w8er.android.utils.DataFormatter.roundToHalf;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.ViewHolder> {
    private List<Restaurant> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;


    // data is passed into the constructor
    public RestaurantsAdapter(Context context, List<Restaurant> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.feed_restaurant_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = mData.get(position).getName();
        holder.mTextViewName.setText(name);
        float r = roundToHalf(mData.get(position).getRating());
        holder.ratingBar.setRating(r);

        holder.mTextViewAddress.setText(mData.get(position).getAddress());

        String strStatus = getRestStatus(mData.get(position).getHours());
        holder.mTextViewsStatus.setText(strStatus);
        if (strStatus.equals("Open"))
            holder.mTextViewsStatus.setTextColor(Color.GREEN);
        else
            holder.mTextViewsStatus.setTextColor(Color.RED);

        initReviews(holder, position);


        String pic = mData.get(position).getProfile_img();
        if (pic != null && !(pic.isEmpty()))
            Picasso.with(mContext)
                    .load(pic)
                    .error(R.drawable.default_user_image)
                    .into(holder.mResImage);

    }

    private void initReviews(ViewHolder holder, int position) {
        int reviewsSize = mData.get(position).getReviews().size();

        String reviewsSizeStr = Integer.toString(reviewsSize);
        holder.mTextViewNum.setText(reviewsSizeStr);

        if (reviewsSize == 1) {
            String strReview = "Review";
            holder.mTextViewReviews.setText(strReview);
            holder.mTextViewReviews.setVisibility(View.VISIBLE);
            holder.mTextViewNum.setVisibility(View.VISIBLE);
        } else {
            String strReviews = "Reviews";
            holder.mTextViewReviews.setText(strReviews);

            if (reviewsSize == 0) {
                holder.mTextViewNum.setVisibility(View.GONE);
                holder.mTextViewReviews.setVisibility(View.GONE);
            } else {
                holder.mTextViewReviews.setVisibility(View.VISIBLE);
                holder.mTextViewNum.setVisibility(View.VISIBLE);
            }
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // convenience method for getting data at click position
    public String getItemID(int id) {
        return mData.get(id).get_id();
    }

    public List<Restaurant> getmData() {
        return mData;
    }

    public void setmData(List<Restaurant> mData) {
        this.mData = mData;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTextViewName;
        ImageView mResImage;
        BaseRatingBar ratingBar;
        RelativeLayout relativeLayout;

        TextView mTextViewAddress;
        TextView mTextViewsStatus;
        TextView mTextViewNum;
        TextView mTextViewReviews;

        ViewHolder(View itemView) {
            super(itemView);
            mTextViewAddress = itemView.findViewById(R.id.textViewAddress);
            mTextViewsStatus = itemView.findViewById(R.id.status);
            mTextViewNum = itemView.findViewById(R.id.textViewNum);
            mTextViewReviews = itemView.findViewById(R.id.textViewReviews);

            relativeLayout = itemView.findViewById(R.id.relative_layout);
            mTextViewName = itemView.findViewById(R.id.title_res);
            mResImage = itemView.findViewById(R.id.imageView);
            ratingBar = itemView.findViewById(R.id.simple_rating_bar);
            ratingBar.setEnabled(false);
            ratingBar.setClickable(false);
            relativeLayout.setOnClickListener(this);
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
