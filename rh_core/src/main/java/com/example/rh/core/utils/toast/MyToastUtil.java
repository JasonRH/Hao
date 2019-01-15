package com.example.rh.core.utils.toast;

import android.content.Context;
import android.widget.Toast;

/**
 * @author RH
 * @date 2019/1/14
 */
public class MyToastUtil {
    public static void showShortToast(Context context,String string){
        Toast.makeText(context,string,Toast.LENGTH_SHORT).show();
    }
}
