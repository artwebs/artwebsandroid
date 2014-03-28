package cn.artwebs.utils;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.widget.TextView;
import android.widget.Toast;

public class AndroidUtils {
	public static void setEditTextReadOnly(TextView view){  
	      view.setTextColor(Color.GRAY);   //设置只读时的文字颜色  
	      if (view instanceof android.widget.EditText){  
	          view.setCursorVisible(false);      //设置输入框中的光标不可见  
	          view.setFocusable(false);           //无焦点  
	          view.setFocusableInTouchMode(false);     //触摸时也得不到焦点  
	      }  
	}  
	
	public static void commDialog(Context context,String title,String message)
	{
		new AlertDialog.Builder(context)
		.setTitle(title)
        .setMessage(message)   
        .setPositiveButton("确定",  
                new DialogInterface.OnClickListener() {  
                    public void onClick(DialogInterface dialog,  
                            int whichButton) {  
                    }  
                }).show();
	}
	
	public static void toastShow(final Activity activity,final String msg)
	{
		toastShow(activity,msg,Toast.LENGTH_LONG);
	}
	
	public static void toastShow(final Activity activity,final String msg, final int duration)
	{
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(activity, msg, duration).show();
				
			}
		});
	}
	
	

}
