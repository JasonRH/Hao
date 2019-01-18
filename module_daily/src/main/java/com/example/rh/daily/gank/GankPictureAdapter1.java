package com.example.rh.daily.gank;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rh.daily.R;
import com.example.rh.daily.activity.ShowImageActivity;

import java.util.List;

/**
 * @author RH
 * @date 2019/1/16
 */
public class GankPictureAdapter1 extends RecyclerView.Adapter<GankPictureAdapter1.ViewHolder> {
    private Context mContext;
    private List<String> mPictureList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView pictureImage;
        TextView pictureName;
        CardView cardView;

        private ViewHolder(View itemView) {
            super(itemView);
            pictureImage = itemView.findViewById(R.id.picture);
            pictureName = itemView.findViewById(R.id.picture_name);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

    public GankPictureAdapter1(List<String> mPictureList) {
        this.mPictureList = mPictureList;
    }

    @Override
    public GankPictureAdapter1.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_picture_item, parent, false);
        final GankPictureAdapter1.ViewHolder holder = new GankPictureAdapter1.ViewHolder(view);
        holder.pictureName.setVisibility(View.GONE);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                String picture = mPictureList.get(position);
                Intent intent = new Intent(mContext, ShowImageActivity.class);
                intent.putExtra("imageUrl", picture);
                mContext.startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(GankPictureAdapter1.ViewHolder holder, int position) {
        String picture = mPictureList.get(position);
        Glide.with(mContext).load(picture).into(holder.pictureImage);

    }

    @Override
    public int getItemCount() {
        return mPictureList.size();
    }


}
