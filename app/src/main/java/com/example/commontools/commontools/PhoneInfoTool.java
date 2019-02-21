package com.example.commontools.commontools;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import static com.example.commontools.BuildConfig.VERSION_CODE;
import static com.example.commontools.BuildConfig.VERSION_NAME;

public class PhoneInfoTool {
    //是否为手机
    public static boolean isPhone(Context context) {
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephony.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
            return false ;
        } else {
            return true ;
        }
    }

    //获取手机IMEI
    @TargetApi(Build.VERSION_CODES.CUPCAKE)

    public static String getDeviceIMEI(Context context) {
        String deviceId = null;
        if (isPhone(context)) {
            TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephony != null) {
                deviceId = telephony.getDeviceId();
            }
        } else {
            deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceId;
    }
    //获取MAC地址
    public static String getMacAddress(Context context) {

        String macAddress;
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        macAddress = info.getMacAddress();
        if ( null == macAddress) {
            return "" ;
        }
        macAddress = macAddress.replace( ":" , "" );
        return macAddress;

    }



    /*
    收集设备信息，用于信息统计分析
     */
    public static Properties collectDeviceInfo(Context context) {
        Properties mDeviceCrashInfo = new Properties();
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null ) {
                mDeviceCrashInfo.put(VERSION_NAME,
                        pi.versionName == null ? "not set" : pi.versionName);
                mDeviceCrashInfo.put(VERSION_CODE, pi.versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("TAG", "Error while collect package info" , e);
        }
        Field[] fields = Build. class .getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible( true );
                mDeviceCrashInfo.put(field.getName(), field.get( null ));
            } catch (Exception e) {
                Log.e("TAG", "Error while collect crash info" , e);
            }
        }
        return mDeviceCrashInfo;
    }
    public static String collectDeviceInfoStr(Context context) {
        Properties prop = collectDeviceInfo(context);
        Set deviceInfos = prop.keySet();
        StringBuilder deviceInfoStr = new StringBuilder( "{\n" );
        for (Iterator iter = deviceInfos.iterator(); iter.hasNext();) {
            Object item = iter.next();
            deviceInfoStr.append( "\t\t\t" + item + ":" + prop.get(item) + ", \n" );
        }
        deviceInfoStr.append( "}" );
        return deviceInfoStr.toString();
    }



    //是否有SDCard
    public static boolean haveSDCard() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }




}
