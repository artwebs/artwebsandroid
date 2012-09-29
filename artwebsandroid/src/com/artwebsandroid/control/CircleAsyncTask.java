package com.artwebsandroid.control;

import com.artwebsandroid.object.BinMap;
import com.artwebsandroid.utils.AndroidUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public abstract class CircleAsyncTask {
	private ProgressDialog progressDialog;
	private Context context;
	
	
	public ProgressDialog getProgressDialog() {
		return progressDialog;
	}

	private AsyncTask<Void,Void,BinMap> task=new AsyncTask<Void,Void,BinMap>(){

		@Override
		protected BinMap doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return doRun();
		}
		@Override
		protected void onPostExecute(BinMap result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);	
			doUpdate(result);
		}
	};
	
	public CircleAsyncTask(Context context)
	{
		this(context, "正在加载...");
	}
	public CircleAsyncTask(Context context,String message)
	{
		this.context=context;
		progressDialog=new ProgressDialog(this.context);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setMessage(message);	
		progressDialog.setCancelable(false);//不可被返回键取消对话框
	}
	public abstract BinMap  doRun();
	
	public void  doUpdate(BinMap result)
	{
		AndroidUtils.commDialog(context, "",result.getValue("message").toString());
		this.progressDialog.dismiss();
	}
	
	public void start()
	{
		this.progressDialog.show();
		this.task.execute();
	}
}
