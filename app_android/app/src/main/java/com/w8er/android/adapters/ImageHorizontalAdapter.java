package com.w8er.android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.w8er.android.R;
import com.w8er.android.model.Pictures;

import java.util.List;

public class ImageHorizontalAdapter extends RecyclerView.Adapter<ImageHorizontalAdapter.ViewHolder> {

    private Context mContext;
    private List<Pictures> pics;
    private ItemClickListener mClickListener;

    public List<Pictures> getPics() {
        return pics;
    }

    public void setPics(List<Pictures> pics) {
        this.pics = pics;
    }

    public ImageHorizontalAdapter(Context context, List<Pictures> titles) {
        this.pics = titles;
        this.mContext =context;
    }

    @Override
    public ImageHorizontalAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feed_restaurant_image_item, viewGroup, false);
        return new ImageHorizontalAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageHorizontalAdapter.ViewHolder holder, int position) {

        int pos = position % pics.size();

        String url = pics.get(pos).getUrl();

        if (url != null && !(url.isEmpty()))
            Picasso.with(mContext)
                    .load(url)
                    .error(R.drawable.default_user_image)
                    .into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return pics == null ? 0 : pics.size() * 2;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView pic;

        ViewHolder(final View itemView) {
            super(itemView);
            this.pic =  itemView.findViewById(R.id.rest_pic);
            pic.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
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

