package com.example.rh.daily.bing;

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
import com.example.rh.daily.activity.PictureActivity;
import com.example.rh.ui.image.MyGlideOptions;

import java.util.List;

/**
 * @author RH
 * @date 2017/11/3
 */

public class BingPictureAdapter1 extends RecyclerView.Adapter<BingPictureAdapter1.ViewHolder> {
    private Context mContext;
    private List<BingDailyBean> mPictureList;

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

    public BingPictureAdapter1(List<BingDailyBean> mPictureList) {
        this.mPictureList = mPictureList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_bing_picture_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                BingDailyBean bingDailyBean = mPictureList.get(position);
                Intent intent = new Intent(mContext, PictureActivity.class);
                intent.putExtra("BingDaily_data", bingDailyBean);
                mContext.startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BingDailyBean picture = mPictureList.get(position);
        holder.pictureName.setText(picture.getDate());
        Glide.with(mContext).load(picture.getUrl()).apply(MyGlideOptions.OPTIONS).into(holder.pictureImage);

    }

    @Override
    public int getItemCount() {
        return mPictureList.size();
    }


}
