package com.w8er.android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.w8er.android.R;
import com.w8er.android.model.RestPictures;

import java.util.List;

public class ImageHorizontalAdapter extends RecyclerView.Adapter<ImageHorizontalAdapter.ViewHolder> {

    private Context mContext;
    private List<RestPictures> pics;

    public ImageHorizontalAdapter(Context context, List<RestPictures> titles) {
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
        String url = pics.get(position).getUrl();

        if (url != null && !(url.isEmpty()))
            Picasso.with(mContext)
                    .load(url)
                    .error(R.drawable.default_user_image)
                    .into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return pics.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView pic;

        ViewHolder(final View itemView) {
            super(itemView);
            this.pic =  itemView.findViewById(R.id.rest_pic);
        }
    }

}

