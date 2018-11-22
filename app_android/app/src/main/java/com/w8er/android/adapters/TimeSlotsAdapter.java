package com.w8er.android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.w8er.android.R;
import com.w8er.android.model.TimeSlot;

import java.util.List;

public class TimeSlotsAdapter extends RecyclerView.Adapter<TimeSlotsAdapter.ViewHolder> {

    private List<TimeSlot> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public TimeSlotsAdapter(Context context, List<TimeSlot> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.restaurant_time_slot_item, parent, false);

        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TimeSlot slot = mData.get(position);
        holder.mDay.setText(slot.getDays());
        holder.mFrom.setText(slot.getOpen());
        holder.mTo.setText(slot.getClose());

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mDay;
        TextView mFrom;
        TextView mTo;
        TextView mRemove;



        ViewHolder(View itemView) {
            super(itemView);
            mDay = itemView.findViewById(R.id.textViewDay);
            mFrom = itemView.findViewById(R.id.textViewFrom);
            mTo = itemView.findViewById(R.id.textViewTo);
            mRemove = itemView.findViewById(R.id.textViewRemove);
            mRemove.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            long viewId = view.getId();

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
