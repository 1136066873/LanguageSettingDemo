package com.hegd.languagesettingdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.Locale;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import android.content.res.Configuration;
import android.app.backup.BackupManager;

/**
 * Created by 01438511 on 2019/8/13.
 */

public class SystemLanguageUpdateReciver extends BroadcastReceiver {

    private final String TAG = "heguodong";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.i(TAG, "receive action:" + action);

        if ("android.intent.action.USER_SET_LANGUAGE".equals(action)) {
            String language = intent.getStringExtra("type");
            if (TextUtils.isEmpty(language)) {
                return;
            }
            String langue_less = language.toLowerCase();
            if ("uk".equals(langue_less)) {
                Log.i(TAG, "set langue uk");
                //英语 ENGLISH-->US
                updateLanguage(Locale.US);
            } else if ("cn".equals(langue_less)) {
                Log.i(TAG, "set langue cn");
                //中文简体
                updateLanguage(Locale.CHINA);
            } else if ("de".equals(langue_less)) {
                Log.i(TAG, "set langue de");
                //德语GERMAN-->GERMANY
                updateLanguage(Locale.GERMANY);
            } else if ("fr".equals(langue_less)) {
                Log.i(TAG, "set langue fr");
                //法语 French-->FRANCE
                updateLanguage(Locale.FRANCE);
            } else if ("ru".equals(langue_less)) {
                Log.i(TAG, "set langue Russian");
                //俄语 Russian-->ru-RU
                updateLanguage(new Locale("ru", "RU"));
            } else if ("in".equals(langue_less) || "pm".equals(langue_less) || "tha".equals(langue_less)) {//设置语言为印度英语（无论传递的参数是 印度or巴基斯坦or泰国）
                Log.i(TAG, "set langue ENGLISH(en_IN) --- (parameter is " + langue_less + " )");
                //设置语言为印度英语（无论传递的参数是 印度or巴基斯坦or泰国）
                updateLanguage(new Locale("en", "IN"));
            }
        }
    }

    private void updateLanguage(Locale locale) {
        try {
            Object objIActMag, objActMagNative;
            Class clzIActMag = Class.forName("android.app.IActivityManager");
            Class clzActMagNative = Class.forName("android.app.ActivityManagerNative");
            Method mtdActMagNative$getDefault = clzActMagNative.getDeclaredMethod("getDefault");
            objIActMag = mtdActMagNative$getDefault.invoke(clzActMagNative);
            Method mtdIActMag$getConfiguration = clzIActMag.getDeclaredMethod("getConfiguration");
            Configuration config = (Configuration) mtdIActMag$getConfiguration.invoke(objIActMag);
            config.locale = locale;
            Class clzConfig = Class.forName("android.content.res.Configuration");
            java.lang.reflect.Field userSetLocale = clzConfig.getField("userSetLocale");
            userSetLocale.set(config, true);
            // 此处需要声明权限:android.permission.CHANGE_CONFIGURATION
            // 会重新调用 onCreate();
            Class[] clzParams = {Configuration.class};
            Method mtdIActMag$updateConfiguration = clzIActMag.getDeclaredMethod("updateConfiguration", clzParams);
            mtdIActMag$updateConfiguration.invoke(objIActMag, config);
            BackupManager.dataChanged("com.android.providers.settings");
            Log.i(TAG, "updateLanguage end");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
