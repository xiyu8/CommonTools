package com.example.commontools.commontools;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.PowerManager;
import android.telephony.TelephonyManager;

import java.util.List;

public class PhoneStatusTool {

    /*
判断当前App处于前台还是后台状态
 */
    public static boolean isApplicationBackground( final Context context) {

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        @SuppressWarnings ( "deprecation" )
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks( 1 );
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get( 0 ).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true ;
            }
        }
        return false ;
    }

    /*
    唤醒屏幕并解锁
     */
    public static void wakeUpAndUnlock(Context context){
        KeyguardManager km= (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock kl = km.newKeyguardLock( "unLock" );
        //解锁
        kl.disableKeyguard();
        //获取电源管理器对象
        PowerManager pm=(PowerManager) context.getSystemService(Context.POWER_SERVICE);
        //获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "bright" );

        //点亮屏幕
        wl.acquire();
        //释放
        wl.release();
    }


    /*
    手机是否锁屏
     */
    public static boolean isSleeping(Context context) {
        KeyguardManager kgMgr = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        boolean isSleeping = kgMgr.inKeyguardRestrictedInputMode();
        return isSleeping;

    }

    /*
    当前设备是否为手机
     */
    public static boolean isPhone(Context context) {
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephony.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
            return false ;
        } else {
            return true ;
        }
    }



}
