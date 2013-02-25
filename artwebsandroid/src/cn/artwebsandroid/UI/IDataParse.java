package cn.artwebsandroid.UI;

import cn.artwebsandroid.object.BinMap;
import cn.artwebsandroid.transmit.ITransmit;


public interface IDataParse {
	public BinMap parse();
	public void setDataStr(String dataStr);
	public void setTransmit(ITransmit transmit);
	public void setPara(BinMap para);
}
