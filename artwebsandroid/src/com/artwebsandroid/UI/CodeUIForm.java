package com.artwebsandroid.UI;

import java.lang.reflect.Constructor;
import java.util.ArrayList;


import android.app.Activity;
import android.view.View;
import android.widget.TableLayout;

import com.artwebsandroid.UI.Form.AbsControl;
import com.artwebsandroid.object.BinList;
import com.artwebsandroid.object.BinMap;

public class CodeUIForm extends CodeUI {
	private TableLayout formLayout;
	private ArrayList<AbsControl> ctlList=new ArrayList<AbsControl>();

	
	@Override
	public View drawnView(Activity activity,Integer parentid,Integer id) {
		this.formLayout=new TableLayout(activity);
//		this.formLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT));
		this.formLayout.setColumnStretchable(1, true);
		this.drawnControl(activity);		
		return this.formLayout;
		
		
	}
	
	private void drawnControl(Activity activity)
	{
		if("00".equals(this.para.getValue("return").toString()))
		{
			BinList rows=(BinList)(this.para.getValue("rows"));
			for(int i=0;i<rows.size();i++)
			{
				BinMap ctlpara= new BinMap();
				ctlpara.setItemByHashMap(rows.getItem(i));
				
				Class<CodeUI> objclass=null;
				AbsControl ctlobj=null;
				String type=ctlpara.getValue("CONMETHOD").toString().toLowerCase();
				try {
					objclass=(Class<CodeUI>)Class.forName("com.artcode.UI.Form.Control"+type.substring(0,1).toUpperCase()+type.substring(1));
					Constructor constructor = objclass.getConstructor(TableLayout.class,Activity.class,BinMap.class); 
					ctlobj=(AbsControl)constructor.newInstance(this.formLayout,activity,ctlpara);
					ctlobj.setTransmit(this.transmit);
					ctlobj.setUiFactory(this.uiFactory);
					ctlobj.setCtlList(ctlList);
					ctlobj.create();
					ctlobj.display();
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					
				}
			}
		}
	}

	
	


}
