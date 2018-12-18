package com.example.rh.bottom.personal.list;

import android.widget.CompoundButton;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.rh.core.base.BaseFragment;

/**
 * @author RH
 * @date 2018/11/5
 */
public class ListBean implements MultiItemEntity {
    private int mItemType = 0;
    private String mImageUrl = null;
    private String mText = null;
    private String mValue = null;
    private int mId = 0;
    private BaseFragment mFragment = null;
    private CompoundButton.OnCheckedChangeListener listener = null;

    public ListBean(int mItemType, String mImageUrl, String mText, String mValue, int mId, BaseFragment mFragment, CompoundButton.OnCheckedChangeListener listener) {
        this.mItemType = mItemType;
        this.mImageUrl = mImageUrl;
        this.mText = mText;
        this.mValue = mValue;
        this.mId = mId;
        this.mFragment = mFragment;
        this.listener = listener;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getText() {
        return mText;
    }

    public String getValue() {
        return mValue;
    }

    public int getId() {
        return mId;
    }

    public BaseFragment getmFragment() {
        return mFragment;
    }

    public CompoundButton.OnCheckedChangeListener getOnCheckedChangeListener() {
        return listener;
    }

    @Override
    public int getItemType() {
        return mItemType;
    }

    public static final class Builder {
        private int id = 0;
        private int itemType = 0;
        private String imageUrl = null;
        private String text = null;
        private String value = null;
        private BaseFragment mFragment = null;
        private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = null;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setItemType(int itemType) {
            this.itemType = itemType;
            return this;
        }

        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setValue(String value) {
            this.value = value;
            return this;
        }

        public Builder setmFragment(BaseFragment mFragment) {
            this.mFragment = mFragment;
            return this;
        }

        public Builder setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
            this.onCheckedChangeListener = onCheckedChangeListener;
            return this;
        }

        public ListBean build() {
            return new ListBean(itemType, imageUrl, text, value, id, mFragment, onCheckedChangeListener);
        }
    }
}
