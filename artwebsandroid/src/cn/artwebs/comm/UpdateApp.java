package cn.artwebs.comm;

import cn.artwebs.utils.FileUtils;
import cn.artwebs.utils.HttpDownloader;
import cn.artwebs.utils.Utils;
import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;


public class UpdateApp {
	private final static String tag="UpdateApp";
	private ProgressDialog downPd;
	private FileUtils fileUtils;
	private int downLoadFileSize=0;
	private static UpdateApp obj;
	private Version version;
	private Activity activity;
	private static String path="artintall";
	private UpdateApp()
	{
		
	}
	
	public static void install(Activity activity)
	{
		installWithString(activity,getCtlContent());
	}
	
	public static void installWithString(Activity activity,String content)
	{
		Version localVersion=getLocalVersion();
		Version ctlVersion=getControlVersionWithString(content);
		if(ctlVersion==null)return;
		obj=new UpdateApp();
		obj.version=ctlVersion;
		obj.activity=activity;
		obj.fileUtils=new FileUtils(path);
		if(obj.fileUtils.isFileExist(obj.version.getAppName()+".apk"))obj.fileUtils.deleteSDFile(obj.version.getAppName()+".apk");
		if(localVersion.getVersion()<ctlVersion.getVersion())
		{
			Dialog dialog=new AlertDialog.Builder(obj.activity)
						 .setIcon(R.drawable.ic_dialog_alert)
						 .setTitle("软件升级？")
						 .setMessage("您确定进行软件升级吗？")
						 .setPositiveButton("升级", new DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								if(!android.os.Environment.getExternalStorageState().equals(
										android.os.Environment.MEDIA_MOUNTED))
								{
									Toast.makeText(AppApplication.getAppContext(), "设备无SDCard，无法完成自动升级",Toast.LENGTH_LONG).show();
									return;
								}
								obj.downApk();
							}
							 
						 })
						 .setNegativeButton("以后再说", new DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								
							}
							 
						 }).create();
			dialog.show();
		}
	}
	
	private void downApk()
	{
		downPd=new ProgressDialog(activity);
		downPd.setTitle("正在更新...");
		downPd.setMessage("下载更新文件可能需要几分钟，请稍后...");
		downPd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		downPd.setMax(this.version.getApkSize());
		downPd.show();
		new Thread(new Runnable(){

			@Override
			public void run() {	
				HttpDownloader obj=new HttpDownloader();
				obj.downFile(version.getUpdateUrl(),path, version.getAppName()+".apk", downHandler);
			}}).start();
	}
	
	public static Version getLocalVersion()
    {
    	Version obj=new Version();
    	obj.setAppName(AppApplication.getPKG().applicationInfo.packageName.substring(AppApplication.getPKG().applicationInfo.packageName.lastIndexOf(".")+1));
    	obj.setVersion(Float.valueOf(AppApplication.getPKG().versionName));
    	obj.setUpdateUrl("");
    	return obj;
    }
	
	public static Version getControlVersionWithString(String content)
	{
		Version ctlVersion=new Version();
		try{
			ctlVersion.setAppName(Utils.getMarkString(content, "<appName>", "</appName>"));
	    	ctlVersion.setUpdateUrl(Utils.getMarkString(content, "<updateUrl>", "</updateUrl>"));
	    	ctlVersion.setVersion(Float.valueOf(Utils.getMarkString(content, "<version>", "</version>")));
	    	ctlVersion.setApkSize(Integer.parseInt(Utils.getMarkString(content, "<apkSize>", "</apkSize>")));
		}catch(Exception e)
		{
			ctlVersion=null;
		} finally
		{}
    	return ctlVersion;
	}
    
    public static String getCtlContent()
    {
    	
    	Version localVersion=getLocalVersion();
    	String rs="";
    	try{
	    	HttpDownloader httpobj=new HttpDownloader();
	    	String url="http://artwebsapp.duapp.com/appversion/appupdate/%s/%f";
	    	rs=httpobj.download(String.format(url, Utils.UrlEncode(localVersion.getAppName(), "utf-8"),localVersion.getVersion()));
	    	
    	}catch(Exception e){
    		
    	}
    	finally{}
		return rs;
    }
	
	private Handler downHandler=new Handler()
	{

		@Override
		public void handleMessage(Message msg) {
			if (!Thread.currentThread().isInterrupted())
		      {
		        switch (msg.what)
		        {
		          case 1:
		        	downLoadFileSize++;
		            downPd.incrementProgressBy(1);
		            break;
		          case 2:
		        	downPd.dismiss();
		            Toast.makeText(activity, "文件下载完成", Toast.LENGTH_LONG).show();		            
					Log.i(tag,fileUtils.getSDPATH()+version.getAppName()+".apk");
					fileUtils.installApk(activity, fileUtils.getSDPATH()+version.getAppName()+".apk");
		            break;
	 
		          case -1:
		            String error = msg.getData().getString("error");
		            Toast.makeText(AppApplication.getAppContext(), error,Toast.LENGTH_LONG).show();
		         
		            break;
		        }
		      }
		      super.handleMessage(msg);
		}
		
	};
}
