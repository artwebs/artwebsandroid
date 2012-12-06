package com.artwebsandroid.UI;

import com.artwebsandroid.object.BinMap;
import com.artwebsandroid.transmit.ITransmit;

public interface IDataParse {
	public BinMap parse();
	public void setDataStr(String dataStr);
	public void setTransmit(ITransmit transmit);
	public void setPara(BinMap para);
}
