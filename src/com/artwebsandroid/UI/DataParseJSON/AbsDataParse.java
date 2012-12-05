package com.artwebsandroid.UI.DataParseJSON;

import com.artwebsandroid.UI.IDataParse;
import com.artwebsandroid.object.BinMap;
import com.artwebsandroid.transmit.ITransmit;

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
