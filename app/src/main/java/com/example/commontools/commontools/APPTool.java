package com.example.commontools.commontools;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.io.File;
import java.util.List;

/**
 * 跟App相关的辅助类
 *
 *
 *
 */
public class APPTool {

    private APPTool() {
        /**cannot be instantiated **/
        throw new UnsupportedOperationException("cannot be instantiated");

    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**  ？？？？？？？？？？？？？？？？？？？？？版本号 or 版本名称
     * [获取应用程序版本号]
     *
     * @param context
     * @return 当前应用的版本号
     */
    public static String getAppVersion(Context context) {
        String version = "0" ;
        try {
            version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0 ).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }


    /*
    安装apk
     */
    public static void installApk(Context context, File file) {
        Intent intent = new Intent();
        intent.setAction( "android.intent.action.VIEW" );
        intent.addCategory( "android.intent.category.DEFAULT" );
        intent.setType( "application/vnd.android.package-archive" );
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive" );
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }




}