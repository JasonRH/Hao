package com.example.rh.core.app;

import android.app.Activity;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.example.rh.core.fragment.web.event.Event;
import com.example.rh.core.fragment.web.event.EventManager;
import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * @author RH
 * @date 2018/8/20
 * <p>
 * 配置文件的存储和获取
 */
public class Configurator {
    /**
     * 存储全局信息
     */
    private static final HashMap<Object, Object> APP_CONFIGS = new HashMap<>();
    /**
     * 存储图标
     */
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();
    /**
     * 拦截器
     */
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    /**使用静态，避免内存泄露*/
    private static final Handler HANDLER = new Handler();

    private Configurator() {
        APP_CONFIGS.put(ConfigType.CONFIG_READY, false);
        APP_CONFIGS.put(ConfigType.HANDLER, HANDLER);
    }

    /**
     * 静态内部类实现懒汉单例模式
     */
    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    /**
     * 获取 HashMap 对象
     */
    final HashMap<Object, Object> getAppConfigs() {
        return APP_CONFIGS;
    }

    /**
     * 传入host
     */
    public final Configurator withApiHost(String host) {
        APP_CONFIGS.put(ConfigType.API_HOST, host);
        return this;
    }

    /**
     * 添加图标库
     */
    public final Configurator withIcon(IconFontDescriptor descriptor) {
        ICONS.add(descriptor);
        return this;
    }

    /**
     * 添加拦截器
     */
    public final Configurator withInterceptor(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        APP_CONFIGS.put(ConfigType.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    public final Configurator withInterceptor(ArrayList<Interceptor> interceptors) {
        INTERCEPTORS.addAll(interceptors);
        APP_CONFIGS.put(ConfigType.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    /**
     * 微信AppId
     */
    public final Configurator withWeChatAppId(String appId) {
        APP_CONFIGS.put(ConfigType.WE_CHAT_APP_ID, appId);
        return this;
    }

    public final Configurator withWeChatAppSecret(String appSecret) {
        APP_CONFIGS.put(ConfigType.WE_CHAT_APP_SECRET, appSecret);
        return this;
    }

    public final Configurator withActivity(Activity activity) {
        APP_CONFIGS.put(ConfigType.ACTIVITY, activity);
        return this;
    }

    /**
     * Javascript注入名称
     */
    public Configurator withJavascriptInterface(@NonNull String name) {
        APP_CONFIGS.put(ConfigType.JAVASCRIPT_INTERFACE, name);
        return this;
    }

    public Configurator withWebEvent(@NonNull String name, @NonNull Event event) {
        final EventManager manager = EventManager.getInstance();
        manager.addEvent(name, event);
        return this;
    }

    /**
     * 传入浏览器host
     */
    public final Configurator withWebHost(String host) {
        APP_CONFIGS.put(ConfigType.WEB_HOST, host);
        return this;
    }

    /**
     * 完成初始化
     */
    public final void configure() {
        initIcons();
        APP_CONFIGS.put(ConfigType.CONFIG_READY, true);
    }

    /**
     * 初始化图标库
     */
    private void initIcons() {
        if (ICONS.size() > 0) {
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i = 1; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }

    /**
     * 获取特定的配置信息
     */
    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object key) {
        //检查是否初始化
        checkConfiguration();
        final Object value = APP_CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + " IS NULL");
        }
        return (T) value;
    }

    private void checkConfiguration() {
        final boolean isReady = (boolean) APP_CONFIGS.get(ConfigType.CONFIG_READY);
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready ,call configure");
        }
    }
}
