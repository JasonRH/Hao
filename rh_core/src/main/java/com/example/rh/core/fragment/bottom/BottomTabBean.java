package com.example.rh.core.fragment.bottom;

/**
 * @author RH
 * @date 2018/10/24
 */
public final class BottomTabBean {
    private final CharSequence Icon;
    private final CharSequence Title;

    public BottomTabBean(CharSequence icon, CharSequence title) {
        Icon = icon;
        Title = title;
    }

    public CharSequence getIcon() {
        return Icon;
    }

    public CharSequence getTitle() {
        return Title;
    }
}
