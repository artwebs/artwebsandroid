package com.artwebsandroid.UI;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.artwebsandroid.object.BinList;

public class CodeUIInfo extends CodeUI {
	
	
	@Override
	public View drawnView(Activity activity,Integer parentid,Integer id) {
		 TextView text=new TextView(activity);
		 if(id!=null)text.setId(id);
		 Log.i("Info",this.para.getItem().toString());
		 if("-1".equals(this.para.getValue("return").toString()))
		 {
			 text.setText(this.para.getValue("returnflag").toString());			 
		 }
		 else if(Integer.parseInt(this.para.getValue("count").toString())>0)
		 {
			 text.setText(((BinList)(this.para.getValue("rows"))).getValue(0, "row").toString());	
		 }
		 else
		 {
			 text.setText("查询结果为零");
		 }
		 
		 
		return text;
	}


}
