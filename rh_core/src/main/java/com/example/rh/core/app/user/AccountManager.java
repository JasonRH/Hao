package com.example.rh.core.app.user;

import com.example.rh.core.utils.storage.MySharedPreferences;

/**
 * @author RH
 * @date 2018/10/22
 */
public class AccountManager {
    private enum SignTag {
        SIGN_TAG
    }

    //保存用户登录状态，登录后调用
    public static void setSignStatus(boolean status) {
        MySharedPreferences.setAppFlag(SignTag.SIGN_TAG.name(), status);
    }

    private static boolean isSignIn() {
        return MySharedPreferences.getAppFlag(SignTag.SIGN_TAG.name());
    }

    public static void checkAccount(IUserChecker iChecker) {
        if (isSignIn()) {
            iChecker.onSignIn();
        } else {
            iChecker.onNotSignIn();
        }
    }
}
