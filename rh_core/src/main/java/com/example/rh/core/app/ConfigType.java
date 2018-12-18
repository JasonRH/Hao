package com.example.rh.core.app;

/**
 * @author RH
 * @date 2018/8/20
 * <p>
 * 枚举类是应用程序中唯一的单例，只会被初始化一次，线程安全
 */
public enum ConfigType {
    /**
     * API host
     */
    API_HOST,
    /**
     * Context
     */
    APPLICATION_CONTEXT,
    /**
     * 是否初始化
     */
    CONFIG_READY,
    /**
     * 拦截器
     */
    INTERCEPTOR,
    /**
     * 微信
     */
    WE_CHAT_APP_ID,
    WE_CHAT_APP_SECRET,
    ACTIVITY,
    /**
     * Handler
     */
    HANDLER,
    /***/
    JAVASCRIPT_INTERFACE,
    /**
     * 浏览器host
     */
    WEB_HOST
}
