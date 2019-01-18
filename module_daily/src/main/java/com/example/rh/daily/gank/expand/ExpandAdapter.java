package com.example.rh.daily.gank.expand;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.rh.core.fragment.web.WebFragmentImpl;
import com.example.rh.core.utils.log.MyLogger;
import com.example.rh.daily.R;
import com.example.rh.daily.activity.WebActivity;

import java.util.List;

/**
 * @author RH
 * @date 2019/1/17
 */
public class ExpandAdapter extends BaseQuickAdapter<ExpandBean, BaseViewHolder> {

    public ExpandAdapter(@Nullable List<ExpandBean> data) {
        super(R.layout.adapter_expand_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExpandBean item) {
        helper.setText(R.id.expand_adapter_item_tv_author, "作者：" + item.getWho());
        helper.setText(R.id.expand_adapter_item_tv_content, item.getDesc());
        helper.setText(R.id.expand_adapter_item_tv_category, "#" + item.getType() + "#");
        String time = item.getPublishedAt().substring(0, 10);
        helper.setText(R.id.expand_adapter_item_tv_time, time);

        CardView cardView = helper.getView(R.id.expand_adapter_item_card_view);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebActivity.class);
                intent.putExtra("url", item.getUrl());
                MyLogger.i("ExpandAdapter",item.getUrl());
                mContext.startActivity(intent);
            }
        });
    }
}
