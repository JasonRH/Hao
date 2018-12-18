package com.example.rh.core.app;

import android.content.Context;
import android.os.Handler;

/**
 * @author RH
 * <p>
 * 对外工具类
 */
public final class MyApp {

    /**
     * 传入Application Context
     * 开始初始化
     */
    public static Configurator init(Context context) {
        getConfigurator().getAppConfigs().put(ConfigType.APPLICATION_CONTEXT, context.getApplicationContext());
        return Configurator.getInstance();
    }

    /**
     * 获取Configurator单例对象
     */
    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    /**
     * 获取特定配置信息
     */
    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getConfiguration(key);
    }

    /**
     * 获取全局Context
     */
    public static Context getApplicationContext() {
        return getConfiguration(ConfigType.APPLICATION_CONTEXT);
    }

    /**获取Handler*/
    public static Handler getHandler() {
        return getConfiguration(ConfigType.HANDLER);
    }
}
