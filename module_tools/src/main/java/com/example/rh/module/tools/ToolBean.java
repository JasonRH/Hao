package com.example.rh.module.tools;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.rh.core.base.BaseFragment;

/**
 * @author RH
 * @date 2019/1/4
 */
public class ToolBean implements MultiItemEntity {
    private int mItemType = 0;
    private String text = null;
    private String icon = null;
    private BaseFragment delegate = null;
    private int mId = 0;


    public ToolBean(int mItemType, int mId, String icon, String text, BaseFragment delegate) {
        this.mItemType = mItemType;
        this.mId = mId;
        this.icon = icon;
        this.text = text;
        this.delegate = delegate;
    }

    public int getId() {
        return mId;
    }

    public String getIcon() {
        return icon;
    }

    public String getText() {
        return text;
    }

    public BaseFragment getDelegate() {
        return delegate;
    }

    @Override
    public int getItemType() {
        return mItemType;
    }

    public static final class Builder {
        private int id = 0;
        private int itemType = 0;
        private String icon = null;
        private String text = null;
        private BaseFragment delegate = null;

        public Builder setItemType(int itemType) {
            this.itemType = itemType;
            return this;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setIcon(String icon) {
            this.icon = icon;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setDelegate(BaseFragment delegate) {
            this.delegate = delegate;
            return this;
        }

        public ToolBean build() {
            return new ToolBean(itemType, id, icon, text, delegate);
        }
    }
}
