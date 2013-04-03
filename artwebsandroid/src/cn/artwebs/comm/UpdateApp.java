package cn.artwebs.comm;

import cn.artwebs.utils.FileUtils;
import cn.artwebs.utils.HttpDownloader;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;


public class UpdateApp {
	private ProgressDialog downPd;
	private FileUtils fileUtils;
	private int downLoadFileSize=0;
	private static UpdateApp obj;
	private Version version;
	private Activity activity;
	private UpdateApp()
	{
		
	}
	
	public static void install(Activity activity)
	{
		Version localVersion=AppApplication.getLocalVersion();
		Version ctlVersion=AppApplication.getControlVersion();
		if(localVersion.getVersion()<ctlVersion.getVersion())
		{
			obj=new UpdateApp();
			obj.version=ctlVersion;
			obj.activity=activity;
			obj.fileUtils=new FileUtils("artintall");
			obj.updateApk();
		}
	}
	private void updateApk()
	{
		downPd=new ProgressDialog(AppApplication.getAppContext());
		downPd.setTitle("正在更新...");
		downPd.setMessage("下载更新文件可能需要几分钟，请稍后...");
		downPd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		downPd.setMax(this.version.getApkSize());
		downPd.show();
		new Thread(new Runnable(){

			@Override
			public void run() {	
				HttpDownloader obj=new HttpDownloader();
				obj.downFile(version.getUpdateUrl(),fileUtils.getSDPATH(), version.getAppName()+".apk", downHandler);
			}}).start();
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
		            Toast.makeText(AppApplication.getAppContext(), "文件下载完成", 1).show();		            
					Log.i("Install",fileUtils.getSDPATH()+version.getAppName()+".apk");
					fileUtils.installApk(activity, fileUtils.getSDPATH()+version.getAppName()+".apk");
		            break;
	 
		          case -1:
		            String error = msg.getData().getString("error");
		            Toast.makeText(AppApplication.getAppContext(), error, 1).show();
		         
		            break;
		        }
		      }
		      super.handleMessage(msg);
		}
		
	};
}
