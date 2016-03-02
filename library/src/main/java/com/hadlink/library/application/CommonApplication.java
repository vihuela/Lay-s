package com.hadlink.library.application;

import android.app.Application;
import android.text.TextUtils;

import com.hadlink.library.base.BaseAppManager;
import com.hadlink.library.util.SystemTool;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.IoniconsModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.MeteoconsModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.joanzapata.iconify.fonts.TypiconsModule;
import com.joanzapata.iconify.fonts.WeathericonsModule;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Created by lyao on 2015/8/31.
 * <p>
 * appLogTag = "chehu";
 * appDebug = BuildConfig.LYAO_DEBUG;
 */
public abstract class CommonApplication extends Application {
    protected static CommonApplication sInstance;
    protected String processName;
    protected boolean defaultProcess;
    /**
     * 需要子类自行配置
     */
    protected boolean appDebug = true;
    protected String appLogTag = "chehu";

    /**
     * open method
     */

    public static CommonApplication getInstance() {
        return sInstance;
    }

    protected abstract boolean isDebugLog();

    protected abstract String getLogTag();

    @Override
    public void onCreate() {
        super.onCreate();
        init();

        /**
         * default process
         */
        if (defaultProcess) {
            appDebug = isDebugLog();
            appLogTag = getLogTag();
            initStorage();
            initLogger();
            initIcon();
        }
    }

    private void init() {
        sInstance = this;
        processName = SystemTool.getProcessName(this, android.os.Process.myPid());
        defaultProcess = !TextUtils.isEmpty(processName) && processName.equals(this.getPackageName());
    }

    private void initIcon() {
        Iconify
                .with(new FontAwesomeModule())
                .with(new EntypoModule())
                .with(new TypiconsModule())
                .with(new MaterialModule())
                .with(new MaterialCommunityModule())
                .with(new MeteoconsModule())
                .with(new WeathericonsModule())
                .with(new SimpleLineIconsModule())
                .with(new IoniconsModule());
    }


    private void initStorage() {
        Hawk
                .init(this)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.NO_ENCRYPTION)
                .setStorage(HawkBuilder.newSharedPrefStorage(this))
                .setLogLevel(appDebug ? LogLevel.FULL : LogLevel.NONE)
                .build();
    }

    private void initLogger() {
        Logger
                .init(appLogTag)
                .methodCount(0)//method invoke level
                .methodOffset(0)
                .hideThreadInfo()
                .logLevel(appDebug ? com.orhanobut.logger.LogLevel.FULL : com.orhanobut.logger.LogLevel.NONE);
    }

    public boolean getAppDebug() {
        return appDebug;
    }

    public String getAppLogTag() {
        return appLogTag;
    }

    public void exitApp() {
        BaseAppManager.getInstance().clear();
        System.gc();
        /*MobclickAgent.onKillProcess(this);*/
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
