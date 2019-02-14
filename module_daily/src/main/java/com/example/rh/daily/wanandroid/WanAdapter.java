package com.example.rh.daily.wanandroid;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.rh.core.utils.log.MyLogger;
import com.example.rh.daily.R;
import com.example.rh.daily.activity.WebActivity;

import java.util.List;

/**
 * @author RH
 * @date 2019/1/17
 */
public class WanAdapter extends BaseQuickAdapter<WanBean, BaseViewHolder> {

    public WanAdapter(@Nullable List<WanBean> data) {
        super(R.layout.adapter_wan_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WanBean item) {
        helper.setText(R.id.wan_adapter_item_tv_author, item.getAuthor());
        helper.setText(R.id.wan_adapter_item_tv_content, item.getTitle());
        helper.setText(R.id.wan_adapter_item_tv_category, item.getCategory() + "/" + item.getType());
        helper.setText(R.id.wan_adapter_item_tv_time, "时间：" + item.getTime());

        CardView cardView = helper.getView(R.id.wan_adapter_item_card_view);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebActivity.class);
                intent.putExtra("url", item.getUrl());
                mContext.startActivity(intent);
            }
        });

        TextView tag = helper.getView(R.id.wan_adapter_item_tv_tag);
        String tagName = item.getTagName();

        if (TextUtils.isEmpty(tagName)) {
            tag.setVisibility(View.GONE);
        } else {
            tag.setVisibility(View.VISIBLE);
            tag.setText(item.getTagName());
        }
        tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebActivity.class);
                intent.putExtra("url", "http://www.wanandroid.com"+item.getTagUrl());
                mContext.startActivity(intent);
            }
        });

        TextView fresh = helper.getView(R.id.wan_adapter_item_tv_fresh);
        if (item.getFresh()) {
            fresh.setVisibility(View.VISIBLE);
        } else {
            fresh.setVisibility(View.GONE);
        }

    }
}
