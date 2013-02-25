package cn.artwebsandroid.UI;

import cn.artwebsandroid.object.BinMap;
import cn.artwebsandroid.transmit.ITransmit;


import android.app.Activity;
import android.view.View;

public interface IUIFactory {
	public void setMainActity(Activity mainActity);
	public void setTransmit(ITransmit transmit);
	public void uiIntance();
	public void setUiobj(CodeUI uiobj);
	public void parseData();
	public void parseData(String commend);
	public BinMap getMap();
	public View dranView(Activity activity);
	public View dranView(Activity activity,String commend);
}
