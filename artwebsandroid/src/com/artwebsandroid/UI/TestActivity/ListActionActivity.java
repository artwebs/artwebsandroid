package com.artwebsandroid.UI.TestActivity;

import java.util.HashMap;

import com.artwebs.R;
import com.artwebsandroid.UI.UIFactory;
import com.artwebsandroid.transmit.TransmitExample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;

public class ListActionActivity extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        LinearLayout main=(LinearLayout)getLayoutInflater().inflate(R.layout.binlist, null);
        setContentView(main);
//        MobileNet net=new MobileNet(this);
//        net.setEnable();       
        
		UIFactory factory=new UIFactory();
		factory.setTransmit(new TransmitExample("test/"));
		
		factory.setOnItemClickListener(new OnItemClickListener() {			 
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {	
					String id=((HashMap)arg0.getAdapter().getItem(arg2)).get("second").toString();
					Intent intent=new Intent();
					intent.setClass(ListActionActivity.this, InfoActionActivity.class);
					intent.putExtra("id", id);
					ListActionActivity.this.startActivity(intent);
//						Toast.makeText(ListActionActivity.this, arg3+"Item clicked, position is:" + arg0.getAdapter().getItem(arg2),
//							       Toast.LENGTH_SHORT).show();
					
				}				 
			  });
			View view=factory.dranView(this, "LHBSystem_1/index.php?mod=examplexml&act=glist&source=1");
			view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT));
			
			main.addView(view);
    }
}
