package com.example.rh.ui.icon;

import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.IconFontDescriptor;

/**
 * @author RH
 * @date 2018/8/21
 */
public class MyFontAlibabaModule implements IconFontDescriptor {
    @Override
    public String ttfFileName() {
        //加载ttf文件，放在assets目录下
        return "iconfont.ttf";
    }

    @Override
    public Icon[] characters() {
        //遍历所有的枚举值
        return FontAlibabaIcons.values();
    }
}
