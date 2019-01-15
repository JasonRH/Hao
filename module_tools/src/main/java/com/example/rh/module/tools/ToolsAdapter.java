package com.example.rh.module.tools;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.List;

/**
 * @author RH
 * @date 2019/1/3
 */
public class ToolsAdapter extends BaseMultiItemQuickAdapter<ToolBean, BaseViewHolder> {

    public ToolsAdapter(List<ToolBean> data) {
        super(data);
        addItemType(ToolItemType.ITEM_NORMAL, R.layout.list_item_normal);
    }

    @Override
    protected void convert(BaseViewHolder helper, ToolBean item) {
        switch (helper.getItemViewType()) {
            case ToolItemType.ITEM_NORMAL:
                helper.setText(R.id.list_item_normal_tv, item.getText());
                IconTextView iconTextView = helper.getView(R.id.list_item_normal_icon);
                iconTextView.setText(item.getIcon());
                break;
            default:
                break;
        }
    }
}
