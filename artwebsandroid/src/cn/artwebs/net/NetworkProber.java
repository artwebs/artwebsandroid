package cn.artwebs.net;

import java.util.List;  


import android.app.AlertDialog;
import android.content.Context;  
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;  
import android.net.ConnectivityManager;  
import android.net.NetworkInfo;  
import android.provider.Settings;
import android.telephony.TelephonyManager;  
  
public class NetworkProber {  
  
    /** 
     * 网络是否可用 
     *  
     * @param activity 
     * @return 
     */  
    public static boolean isNetworkAvailable(Context context) {  
        ConnectivityManager connectivity = (ConnectivityManager) context  
                .getSystemService(Context.CONNECTIVITY_SERVICE);  
        if (connectivity == null) {  
        } else {  
            NetworkInfo[] info = connectivity.getAllNetworkInfo();  
            if (info != null) {  
                for (int i = 0; i < info.length; i++) {  
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {  
                        return true;  
                    }  
                }  
            }  
        }  
        return false;  
    }  
  
    /** 
     * Gps是否打开 
     *  
     * @param context 
     * @return 
     */  
    public static boolean isGpsEnabled(Context context) {  
        LocationManager locationManager = ((LocationManager) context  
                .getSystemService(Context.LOCATION_SERVICE));  
        List<String> accessibleProviders = locationManager.getProviders(true);  
        return accessibleProviders != null && accessibleProviders.size() > 0;  
    }  
  
    /** 
     * wifi是否打开 
     */  
    public static boolean isWifiEnabled(Context context) {  
        ConnectivityManager mgrConn = (ConnectivityManager) context  
                .getSystemService(Context.CONNECTIVITY_SERVICE);  
        TelephonyManager mgrTel = (TelephonyManager) context  
                .getSystemService(Context.TELEPHONY_SERVICE);  
        return ((mgrConn.getActiveNetworkInfo() != null && mgrConn  
                .getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || mgrTel  
                .getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);  
    }  
  
    /** 
     * 判断当前网络是否是wifi网络 
     * if(activeNetInfo.getType()==ConnectivityManager.TYPE_MOBILE) { //判断3G网 
     *  
     * @param context 
     * @return boolean 
     */  
    public static boolean isWifi(Context context) {  
        ConnectivityManager connectivityManager = (ConnectivityManager) context  
                .getSystemService(Context.CONNECTIVITY_SERVICE);  
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();  
        if (activeNetInfo != null  
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {  
            return true;  
        }  
        return false;  
    }  
  
    /** 
     * 判断当前网络是否是3G网络 
     *  
     * @param context 
     * @return boolean 
     */  
    public static boolean is3G(Context context) {  
        ConnectivityManager connectivityManager = (ConnectivityManager) context  
                .getSystemService(Context.CONNECTIVITY_SERVICE);  
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();  
        if (activeNetInfo != null  
                && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {  
        	switch(activeNetInfo.getType()){  
            case TelephonyManager.NETWORK_TYPE_1xRTT:  
                return false; // ~ 50-100 kbps  
            case TelephonyManager.NETWORK_TYPE_CDMA:  
                return false; // ~ 14-64 kbps  
            case TelephonyManager.NETWORK_TYPE_EDGE:  
                return false; // ~ 50-100 kbps  
            case TelephonyManager.NETWORK_TYPE_EVDO_0:  
                return true; // ~ 400-1000 kbps  
            case TelephonyManager.NETWORK_TYPE_EVDO_A:  
                return true; // ~ 600-1400 kbps  
            case TelephonyManager.NETWORK_TYPE_GPRS:  
                return false; // ~ 100 kbps  
//            case TelephonyManager.NETWORK_TYPE_HSDPA:  
//                return true; // ~ 2-14 Mbps  
//            case TelephonyManager.NETWORK_TYPE_HSPA:  
//                return true; // ~ 700-1700 kbps  
//            case TelephonyManager.NETWORK_TYPE_HSUPA:  
//                return true; // ~ 1-23 Mbps  
            case TelephonyManager.NETWORK_TYPE_UMTS:  
                return true; // ~ 400-7000 kbps  
            // NOT AVAILABLE YET IN API LEVEL 7  
//            case Connectivity.NETWORK_TYPE_EHRPD:  
//                return true; // ~ 1-2 Mbps  
//            case Connectivity.NETWORK_TYPE_EVDO_B:  
//                return true; // ~ 5 Mbps  
//            case Connectivity.NETWORK_TYPE_HSPAP:  
//                return true; // ~ 10-20 Mbps  
//            case Connectivity.NETWORK_TYPE_IDEN:  
//                return false; // ~25 kbps   
//            case Connectivity.NETWORK_TYPE_LTE:  
//                return true; // ~ 10+ Mbps  
            // Unknown  
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:  
                return false;   
            default:  
                return false;  
            }  
        }  
        return false;  
    }  
    
    public static void setEnable(Context context){
    	if(!isNetworkAvailable(context)){
    		setNewWork(context);
    	}
    }
    
    public static void setNewWork(final Context context)
    {
    	AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("当前网络不可用");
        builder.setMessage("设置网络");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 如果没有网络连接，则进入网络设置界面
            	context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create();
        builder.show();
    }
   
}  
