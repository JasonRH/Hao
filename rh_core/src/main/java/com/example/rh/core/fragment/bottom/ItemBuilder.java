package com.example.rh.core.fragment.bottom;

import java.util.LinkedHashMap;

/**
 * @author RH
 * @date 2018/10/24
 * <p>
 * 简单工厂模式
 */
public final class ItemBuilder {
    /**
     * 保证底部导航栏有序
     */
    private LinkedHashMap<BottomTabBean, BottomItemFragment> ITEMS = new LinkedHashMap<>();

    static ItemBuilder builder() {
        return new ItemBuilder();
    }

    public final ItemBuilder addItem(BottomTabBean tabBean, BottomItemFragment itemFragment) {
        ITEMS.put(tabBean, itemFragment);
        return this;
    }

    public final ItemBuilder addItem(LinkedHashMap<BottomTabBean, BottomItemFragment> items) {
        ITEMS.putAll(items);
        return this;
    }

    public final LinkedHashMap<BottomTabBean, BottomItemFragment> build() {
        return ITEMS;
    }
}
