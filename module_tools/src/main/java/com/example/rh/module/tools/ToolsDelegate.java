package com.example.rh.module.tools;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.rh.core.base.BasePresenter;
import com.example.rh.core.fragment.bottom.BottomItemFragment;
import com.example.rh.module.tools.qrcode.ScannerDelegate;
import com.example.rh.module.tools.translate.TranslateDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author RH
 * @date 2019/1/3
 */
public class ToolsDelegate extends BottomItemFragment {
    @BindView(R2.id.delegate_tools_recycler)
    RecyclerView recyclerView;

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected Object setLayout() {
        return R.layout.delegate_tools;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        final ToolBean qrcode = new ToolBean.Builder()
                .setItemType(ToolItemType.ITEM_NORMAL)
                .setId(1)
                .setIcon("{icon-erweima}")
                .setText("二维码")
                .setDelegate(new ScannerDelegate())
                .build();

        final ToolBean qrcode1 = new ToolBean.Builder()
                .setItemType(ToolItemType.ITEM_NORMAL)
                .setId(2)
                .setIcon("{icon-jiancai}")
                .setText("图片剪裁")
                .build();
        final ToolBean translate = new ToolBean.Builder()
                .setItemType(ToolItemType.ITEM_NORMAL)
                .setId(3)
                .setIcon("{icon-translate}")
                .setText("翻译")
                .setDelegate(new TranslateDelegate())
                .build();

        final List<ToolBean> data = new ArrayList<>();
        data.add(qrcode);
        data.add(qrcode1);
        data.add(translate);

        final GridLayoutManager manager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(manager);
        final ToolsAdapter adapter = new ToolsAdapter(data);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new ToolsClickListener(this));
    }
}
