package cn.artwebs.control;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ArtWebView extends WebView {
	private final static String tag="ArtWebView";
	private Context context;
	ProgressDialog pd;
	Handler handler;
	
	public ArtWebView(Context context) {
		super(context);
		this.context=context;
		init();//执行初始化函数
        handler=new Handler(){
        	public void handleMessage(Message msg)
    	    {//定义一个Handler，用于处理下载线程与UI间通讯
    	      if (!Thread.currentThread().isInterrupted())
    	      {
    	        switch (msg.what)
    	        {
	    	        case 0:
	    	        	pd.show();//显示进度对话框        	
	    	        	break;
	    	        case 1:
	    	        	pd.hide();//隐藏进度对话框，不可使用dismiss()、cancel(),否则再次调用show()时，显示的对话框小圆圈不会动。
	    	        	break;
    	        }
    	      }
    	      super.handleMessage(msg);
    	    }
        };
	}
	
	public void openUrl(String url)
	{
		loadurl(this,url);
	}
	
	public void init(){//初始化
        this.getSettings().setJavaScriptEnabled(true);//可用JS
        this.setScrollBarStyle(0);
        this.setWebViewClient(new WebViewClient(){   
            public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
            	Log.d(tag,url);
            	handler.sendEmptyMessage(0);
                return super.shouldOverrideUrlLoading(view, url);   
            }
 
        });
        this.setWebChromeClient(new WebChromeClient(){
        	public void onProgressChanged(WebView view,int progress){//载入进度改变而触发 
             	if(progress==100){
            		handler.sendEmptyMessage(1);//如果全部载入,隐藏进度对话框
            	}   
                super.onProgressChanged(view, progress);   
            }   
        });
 
    	pd=new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("数据载入中，请稍候！");
    }

    public void loadurl(final WebView view,final String url){
    	new Thread(){
        	public void run(){
        		Log.d(tag,"loadurl="+url);
        		handler.sendEmptyMessage(0);
        		view.loadUrl(url);
        	}
        }.start();
    }
	 
}
