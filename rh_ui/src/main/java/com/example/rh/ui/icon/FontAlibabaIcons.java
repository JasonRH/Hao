package com.example.rh.ui.icon;

import com.joanzapata.iconify.Icon;

/**
 * @author RH
 * @date 2018/8/21
 */
public enum FontAlibabaIcons implements Icon {
    /**
     * 图标unicode编码
     * 此处的名称与IconTextView中的text对应，但下划线需要改成中划线
     */
    icon_shouye('\ue6a5'),
    icon_wenjian('\ue631'),
    icon_wenjian1('\ue617'),
    icon_wode('\ue67b'),
    icon_xiangyou('\ue64a'),
    icon_sd_card('\uec35');

    private char character;

    FontAlibabaIcons(char character) {
        this.character = character;
    }

    /**
     * 将划线改成中划线
     */
    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
