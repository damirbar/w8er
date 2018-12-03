package com.w8er.android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.w8er.android.R;
import com.w8er.android.model.RestItem;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.ViewHolder> {

    private List<RestItem> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;


    // data is passed into the constructor
    public CartItemsAdapter(Context context, List<RestItem> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext =context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.feed_menu_item_cart, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(mData.get(position).getName());

        String amountStr = String.valueOf((mData.get(position).getAmount()));
        holder.amount.setText(amountStr);

        NumberFormat nf = new DecimalFormat("#.####");
        String s = nf.format(mData.get(position).getPrice());
        String strPrice = "₪" + s;

        holder.price.setText(strPrice);

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    public List<RestItem> getmData() {
        return mData;
    }

    public void setmData(List<RestItem> mData) {
        this.mData = mData;
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView amount;
        TextView price;
        RelativeLayout relativeLayout;

        ViewHolder(View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.relative_layout);
            name = itemView.findViewById(R.id.item_name);
            amount = itemView.findViewById(R.id.textViewCartAmount);
            price = itemView.findViewById(R.id.item_price);
            itemView.setOnClickListener(this);
            relativeLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getItemID(int id) {
        return mData.get(id).get_id();
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
