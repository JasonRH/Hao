package com.example.rh.daily.gank;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.rh.daily.R;
import com.example.rh.daily.activity.ShowImageActivity;
import com.example.rh.ui.image.MyGlideOptions;

import java.util.List;

/**
 * @author RH
 * @date 2019/1/17
 */
public class GankPictureAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public GankPictureAdapter(@Nullable List<String> data) {
        super(R.layout.adapter_picture_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView pictureImage = helper.getView(R.id.picture);
        TextView pictureName = helper.getView(R.id.picture_name);
        CardView cardView = helper.getView(R.id.cardView);

        pictureName.setVisibility(View.GONE);
        Glide.with(mContext).load(item).apply(MyGlideOptions.OPTIONS1).into(pictureImage);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ShowImageActivity.class);
                intent.putExtra("imageUrl", item);
                mContext.startActivity(intent);
            }
        });
    }
}
