package cn.artwebs.service;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

public abstract class ArtService extends Service {
	private static Activity activity;
	private static Intent serviceIntent;
	private static AlarmManager manager;
	private static PendingIntent pendingIntent;
	private static LocalBroadcastManager broadcaster;
	
	private static String SERVICE_RESULT="cn.artwebs.service.ArtService";
	public final static String RESULT_TAG="tag";
	private static boolean isSendbroad=false;
	private static long intervalTime=15*1000;
	private static ArtService self;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void start(Activity obj)
	{
		start(obj,0,null);
	}
	
	public static void start(Activity obj,long iTime)
	{
		start(obj,iTime,null);
	}
	
	public static void start(Activity obj,long iTime,String serviceSign)
	{
		if(activity==null)
		{
			activity=obj;
			SERVICE_RESULT=serviceSign;
			if(iTime!=0)intervalTime=iTime;
			if(SERVICE_RESULT==null)isSendbroad=false;
			broadcaster=LocalBroadcastManager.getInstance(activity);
			serviceIntent=new Intent(obj,new Object(){
				public Class getToClass()
				{
					return this.getClass();
				}
			}.getToClass());
    		if(manager==null)manager=(AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
     		if(pendingIntent==null)pendingIntent=PendingIntent.getService(activity, 0, serviceIntent, 0);
     		manager.setRepeating(AlarmManager.RTC, 0, intervalTime,pendingIntent);
    		
		}
	}
	
	public static void stop()
	{
		if(activity!=null)
		{
			manager.cancel(pendingIntent);
			activity.stopService(serviceIntent);
			activity=null;
		}
	}
	
	public  static String getserviceSign()
	{
		return SERVICE_RESULT;
	}
	
	
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		serviceRun();
	}
	
	public abstract void serviceRun();
	
	public void sendResult(String message)
	{
		 Intent intent = new Intent(SERVICE_RESULT);
		 if(message != null&&isSendbroad)
		        intent.putExtra(RESULT_TAG,message);
		    if(broadcaster!=null)
		    	broadcaster.sendBroadcast(intent);
	}
	
	class ToClass {
		
		public Class getToClass()
		{
			return this.getClass();
		}
	}

}
