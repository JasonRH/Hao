package com.example.rh.daily.bing;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.rh.daily.R;
import com.example.rh.daily.activity.PictureActivity;
import com.example.rh.ui.image.MyGlideOptions;

import java.util.List;

/**
 * @author RH
 * @date 2019/1/17
 */
public class BingPictureAdapter extends BaseQuickAdapter<BingDailyBean, BaseViewHolder> {

    public BingPictureAdapter(@Nullable List<BingDailyBean> data) {
        super(R.layout.adapter_bing_picture_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BingDailyBean item) {
        ImageView pictureImage = helper.getView(R.id.picture);
        TextView pictureName = helper.getView(R.id.picture_name);
        CardView cardView = helper.getView(R.id.cardView);

        pictureName.setText(item.getDate());
        Glide.with(mContext).load(item.getUrl()).apply(MyGlideOptions.OPTIONS).into(pictureImage);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PictureActivity.class);
                intent.putExtra("BingDaily_data", item);
                mContext.startActivity(intent);
            }
        });
    }
}
