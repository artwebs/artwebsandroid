package cn.artwebs.control;

import cn.artwebs.comm.DialogStyle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

public class ArtCircleDialog {
	protected ProgressDialog progressDialog = null;
	private Activity window;
	private static ArtCircleDialog dialogObj;
	private ArtCircleDialog(Activity window)
	{
		this.window=window;
	}
	
	public static ArtCircleDialog instance(Activity window)
	{
		if(dialogObj==null)
			dialogObj=new ArtCircleDialog(window);
		return dialogObj;
	}
	
	public void show(DialogStyle style)
	{
		this.show(style,null);
	}
	
	public void show(DialogStyle style,OnCancelListener cancel)
	{
		if(progressDialog==null)
		{
			progressDialog = ProgressDialog.show(window,style.getTitle(), style.getContent(), true);
			progressDialog.setCancelable(true);
			progressDialog.setCanceledOnTouchOutside(false);
			if(cancel==null)
				progressDialog.setOnCancelListener(selfcancel);
			else
				progressDialog.setOnCancelListener(cancel);
			window.runOnUiThread(new Runnable(){

				@Override
				public void run() {
					progressDialog.show();
				}});
		}
		
	}
	
	public void close()
	{
		if(progressDialog!=null)
		{
			window.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					progressDialog.dismiss();
				}
			});
			progressDialog=null;
		}
	}
	
	private OnCancelListener selfcancel = new OnCancelListener() 
	{
		@Override
		public void onCancel(DialogInterface dialog) 
		{
			
		}
	};
}
