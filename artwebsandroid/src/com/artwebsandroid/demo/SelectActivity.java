package com.artwebsandroid.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.artwebsandroid.R;
import com.artwebsandroid.UI.UIFactory;

public class SelectActivity extends Activity {
	private String conurl="";
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        LinearLayout main=(LinearLayout)getLayoutInflater().inflate(R.layout.binlist, null);
        setContentView(main);
        UIFactory factory=new UIFactory();
		factory.setTransmit(new TransmitExample("test/"));
		
		Intent intent=this.getIntent();
        if(intent.hasExtra("conurl"))
        {
        	conurl=intent.getStringExtra("conurl");
        }
		Log.i("Form",conurl.replace("#and", "&"));
		View view=factory.dranView(this,conurl.replace("#and", "&"));
		view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT));

		main.addView(view);
 }
}
