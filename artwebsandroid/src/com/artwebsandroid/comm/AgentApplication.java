package com.artwebsandroid.comm;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

public class AgentApplication extends Application {
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
}
