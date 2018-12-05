package com.w8er.android.adapters;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.w8er.android.model.Pictures;

import net.alhazmy13.mediagallery.library.R;
import net.alhazmy13.mediagallery.library.Utility;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


/**
 * The type Horizontal list adapters.
 */
public class HorizontalListAdapters extends RecyclerView.Adapter<HorizontalListAdapters.ViewHolder> {
    private final int placeHolder;
    private ArrayList<Pictures> mDataset;
    private final Context mContext;
    private int mSelectedItem = -1;
    private final OnImgClick mClickListner;

    /**
     * Instantiates a new Horizontal list adapters.
     *
     * @param activity    the activity
     * @param images      the images
     * @param imgClick    the img click
     * @param placeHolder the place holder
     */
    public HorizontalListAdapters(Context activity, ArrayList<Pictures> images, OnImgClick imgClick, int placeHolder) {
        this.mContext = activity;
        this.mDataset = images;
        this.mClickListner = imgClick;
        this.placeHolder = placeHolder;
    }


    public ArrayList<Pictures> getmDataset() {
        return mDataset;
    }

    public void setmDataset(ArrayList<Pictures> pictures) {
        this.mDataset = pictures;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_horizontal, null));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String o = mDataset.get(holder.getAdapterPosition()).getUrl();
        boolean isValidImage;
        if (Utility.isValidURL(o)) {
            Glide.with(mContext)
                    .load(String.valueOf(o))
                    .placeholder(placeHolder == -1 ? R.drawable.media_gallery_placeholder : placeHolder)
                    .into(holder.image);
            isValidImage =true;
        } else {

            ByteArrayOutputStream stream = Utility.toByteArrayOutputStream(o);
            if (stream != null) {
                Glide.with(mContext)
                        .load(stream.toByteArray())
                        .asBitmap()
                        .placeholder(placeHolder == -1 ? R.drawable.media_gallery_placeholder : placeHolder)
                        .into(holder.image);
                isValidImage = true;
            } else {
                throw new RuntimeException("Image at position: " + position + " it's not valid image");

            }
        }
        if(!isValidImage){
            throw new RuntimeException("Value at position: " + position + " Should be as url string or bitmap object");
        }
        ColorMatrix matrix = new ColorMatrix();
        if (mSelectedItem != holder.getAdapterPosition()) {
            matrix.setSaturation(0);

            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            holder.image.setColorFilter(filter);
            holder.image.setAlpha(0.5f);
        } else {
            matrix.setSaturation(1);

            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            holder.image.setColorFilter(filter);
            holder.image.setAlpha(1f);
        }

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListner.onClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    /**
     * Sets selected item.
     *
     * @param position the position
     */
    public void setSelectedItem(int position) {
        if (position >= mDataset.size()) return;
        mSelectedItem = position;
        notifyDataSetChanged();
    }

    /**
     * The type View holder.
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * The Image.
         */
        public ImageView image;

        /**
         * Instantiates a new View holder.
         *
         * @param itemView the item view
         */
        ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.iv);
        }
    }


    public interface OnImgClick {
        void onClick(int pos);
    }

}
