package cn.artwebsandroid.UI.DataParseJSON;

import cn.artwebsandroid.UI.IDataParse;
import cn.artwebsandroid.object.BinMap;
import cn.artwebsandroid.transmit.ITransmit;


public abstract class AbsDataParse implements IDataParse {
	protected String dataStr;
	protected ITransmit transmit;
	protected BinMap para=new BinMap();
	
	@Override
	public void setPara(BinMap para) {
		this.para=para;
		
	}
	
	@Override
	public void setDataStr(String dataStr) {
		this.dataStr=dataStr;
		
	}

	@Override
	public void setTransmit(ITransmit transmit) {
		this.transmit=transmit;
		
	}
}
