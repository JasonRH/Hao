package com.example.rh.ui.recycler;

import java.util.ArrayList;

/**
 * @author RH
 * @date 2018/10/25
 */
public abstract class DataConverter {
    /**
     * 各个Item的数据集合
     */
    protected final ArrayList<MultipleItemEntity> ENTITIES = new ArrayList<>();
    private String mJsonData = null;

    public abstract ArrayList<MultipleItemEntity> convert();

    public DataConverter setJsonData(String json) {
        this.mJsonData = json;
        return this;
    }

    protected String getJsonData() {
        if (mJsonData == null || mJsonData.isEmpty()) {
            throw new NullPointerException("DATA IS NULL!");
        }
        return mJsonData;
    }

    public void clearData() {
        ENTITIES.clear();
    }
}
