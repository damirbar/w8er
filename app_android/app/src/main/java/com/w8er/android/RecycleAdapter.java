package com.w8er.android;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


//should be removed
public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {

    private Activity activity;

    public RecycleAdapter(Activity activity_) {
        activity = activity_;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(activity.getBaseContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txt.setText(String.format("Navigation Item #%d", position));
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txt;

        public ViewHolder(final View itemView) {
            super(itemView);
            txt = (TextView) itemView.findViewById(R.id.txt_vp_item_list);
        }
    }
}
