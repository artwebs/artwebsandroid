package cn.artwebs.comm;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.artwebs.UI.DataParseXML.DataFlag;
import cn.artwebs.utils.HttpDownloader;
import cn.artwebs.utils.Utils;


import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.Log;

public class AppApplication extends Application {
	private final static String TAG="AgentApplication";
	private List<Activity> activities = new ArrayList<Activity>();  
	private String loginName="";
	private String password="";
	public static String loginKey="";
	private static PackageInfo pkg;
    public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void addActivity(Activity activity) {  
        activities.add(activity);  
    }  
  
    @Override  
    public void onTerminate() {  
        super.onTerminate();  
          
        for (Activity activity : activities) {  
            activity.finish();  
        }  
        System.exit(0);  
    }
    
    private static AppApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        // Are we using advanced debugging - locale?
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String p = pref.getString("set_locale", "");
        if (p != null && !p.equals("")) {
            Locale locale;
            // workaround due to region code
            if(p.startsWith("zh")) {
                locale = Locale.CHINA;
            } else {
                locale = new Locale(p);
            }
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
        }
        try {
			pkg= getPackageManager().getPackageInfo(this.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        instance = this;
    }

    /**
     * Called when the overall system is running low on memory
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.w(TAG, "System is running low on memory");

//        BitmapCache.getInstance().clear();
    }

    /**
     * @return the main context of the Application
     */
    public static Context getAppContext()
    {
        return instance;
    }

    /**
     * @return the main resources from the Application
     */
    public static Resources getAppResources()
    {
        return instance.getResources();
    }
    
    
    
    
    public static void showNotification(int id,int icon,String title,String head,String content,Class obj)
	{
		NotificationManager notificationManager=(NotificationManager)getAppContext().getSystemService(NOTIFICATION_SERVICE);
		Notification notification=new Notification(icon,title,System.currentTimeMillis());
		Intent intent=new Intent();
		intent.setClass(getAppContext(), obj);
		PendingIntent contentIntent=PendingIntent.getActivity(getAppContext(), 0, intent, 0);
		notification.defaults=Notification.DEFAULT_SOUND;
		notification.setLatestEventInfo(getAppContext(), head,  content, contentIntent);
		notificationManager.notify(id, notification);
	}
    
    
    
    public static void cancelNotification(int id)
    {
    	NotificationManager notificationManager=(NotificationManager)getAppContext().getSystemService(NOTIFICATION_SERVICE);
    	notificationManager.cancel(id);
    }
    
    public static Version getLocalVersion()
    {
    	Version obj=new Version();
    	obj.setAppName(pkg.applicationInfo.packageName.substring(pkg.applicationInfo.packageName.lastIndexOf(".")+1));
    	obj.setVersion(Float.valueOf(pkg.versionName));
    	obj.setUpdateUrl("");
    	return obj;
    }
    
    public static Version getControlVersion()
    {
    	
    	Version localVersion=getLocalVersion();
    	HttpDownloader httpobj=new HttpDownloader();
    	String url="http://artwebsapp.duapp.com/appversion/appupdate/%s/%f";
    	String rs=httpobj.download(String.format(url, Utils.UrlEncode(localVersion.getAppName(), "utf-8"),localVersion.getVersion()));
    	Version ctlVersion=new Version();
    	ctlVersion.setAppName(Utils.getMarkString(rs, "<appName>", "</appName>"));
    	ctlVersion.setUpdateUrl(Utils.getMarkString(rs, "<updateUrl>", "</updateUrl>"));
    	ctlVersion.setVersion(Float.valueOf(Utils.getMarkString(rs, "<version>", "</version>")));
    	ctlVersion.setApkSize(Integer.parseInt(Utils.getMarkString(rs, "<apkSize>", "</apkSize>")));
		return ctlVersion;
    	
    }
}

class Version
{
	private float version;
	private String updateUrl;
	private String appName;
	private int apkSize;
	
	public float getVersion() {
		return version;
	}
	public void setVersion(float version) {
		this.version = version;
	}
	public String getUpdateUrl() {
		return updateUrl;
	}
	public void setUpdateUrl(String updateUrl) {
		this.updateUrl = updateUrl;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public int getApkSize() {
		return apkSize;
	}
	public void setApkSize(int apkSize) {
		this.apkSize = apkSize;
	}
	
	
}

