package cn.artwebsandroid.comm;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
}
