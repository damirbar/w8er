package com.w8er.android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.w8er.android.R;
import com.w8er.android.model.MenuItem;
import com.w8er.android.model.Review;
import com.willy.ratingbar.BaseRatingBar;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    private List<MenuItem> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;


    // data is passed into the constructor
    public ItemsAdapter(Context context, List<MenuItem> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext =context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.feed_menu_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(mData.get(position).getName());
        holder.info.setText(mData.get(position).getDescription());
        String strPrice = "₪" + mData.get(position).getPrice();
        holder.price.setText(strPrice);

        String pic = mData.get(position).getPicture();
        if (pic != null && !(pic.isEmpty()))
            Picasso.with(mContext)
                    .load(pic)
                    .error(R.drawable.default_user_image)
                    .into(holder.image);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    public List<MenuItem> getmData() {
        return mData;
    }

    public void setmData(List<MenuItem> mData) {
        this.mData = mData;
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;
        TextView name;
        TextView info;
        TextView price;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_pic);
            name = itemView.findViewById(R.id.item_name);
            info = itemView.findViewById(R.id.item_info);
            price = itemView.findViewById(R.id.item_price);
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