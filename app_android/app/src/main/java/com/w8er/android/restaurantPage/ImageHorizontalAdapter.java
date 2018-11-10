package com.w8er.android.restaurantPage;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.w8er.android.R;

public class ImageHorizontalAdapter extends RecyclerView.Adapter<ImageHorizontalAdapter.ViewHolder> {


    private String[] titles;

    public ImageHorizontalAdapter(String[] titles) {
        this.titles = titles;
    }

    @Override
    public ImageHorizontalAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.restaurant_image_item, viewGroup, false);
        return new ImageHorizontalAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageHorizontalAdapter.ViewHolder holder, int position) {
        String title = titles[position];
//        holder.title.setText(title);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
//        private TextView title;

        ViewHolder(final View itemView) {
            super(itemView);
//            this.title = (TextView) itemView.findViewById(R.id.title);
        }
    }

}

