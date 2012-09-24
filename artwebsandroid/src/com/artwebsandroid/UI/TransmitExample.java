package com.artwebsandroid.UI;

import android.util.Log;

import com.artwebsandroid.socket.ClientTCP;

public class TransmitExample implements ITransmit {
	private String skip;
	private ClientTCP sok;
	
	public TransmitExample(String skip)
	{
		this.setSkip(skip);
	}
	
	@Override
	public void setSkip(String skip) {
		this.skip=skip;
	}

	@Override
	public String getSkip() {
		return this.skip;
	}

	@Override
	public String download(String commend) {
		sok=new ClientTCP("116.55.248.21",8000);		
		return sok.download(this.skip+commend);
	}

	@Override
	public int downFile(String commend, String path, String fileName) {
		sok=new ClientTCP("116.55.248.21",8000);	
		Log.i("Trans",this.skip+commend);
		return sok.downFile(this.skip+commend, path, fileName);
	}
	


}
