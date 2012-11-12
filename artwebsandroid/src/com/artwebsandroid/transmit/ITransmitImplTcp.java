package com.artwebsandroid.transmit;

import java.io.InputStream;

import com.artwebsandroid.socket.ClientTCP;
import com.artwebsandroid.transmit.ITransmit;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

public class ITransmitImplTcp implements ITransmit {
	private String skip="";
	private ClientTCP sok;
	private String host="";
	private int prot=3456;

	
	public ITransmitImplTcp(String host,String skip)
	{
		this.host=host;
		this.skip=skip;
	}
	
	public ITransmitImplTcp(String host,int port,String skip)
	{
		this.host=host;
		this.prot=port;
		this.skip=skip;
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
	public String getHost() {
		return host;
	}

	@Override
	public void setHost(String host) {
		this.host = host;
	}


	@Override
	public String download(String commend) {
		Log.i("trans",commend);
		sok=new ClientTCP(host,prot);	
		String rs= sok.download(this.skip+commend);
		Log.i("trans",rs);
		return rs;
	}

	@Override
	public int downFile(String commend, String path, String fileName) {
		sok=new ClientTCP(host,prot);	
		Log.i("Trans",this.skip+commend);
		return sok.downFile(this.skip+commend, path, fileName);
	}

	@Override
	public int downFile(String commend, String path, String fileName,
			Handler handler) {
		sok=new ClientTCP(host,prot);	
		return sok.downFile(this.skip+commend, path, fileName,handler);
	}

	@Override
	public InputStream downStream(String commend) {
		sok=new ClientTCP(host,prot);	
		return sok.downStream(this.skip+commend);
	}

	
}
