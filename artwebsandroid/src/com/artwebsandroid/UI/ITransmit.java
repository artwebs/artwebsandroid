package com.artwebsandroid.UI;

import java.io.InputStream;

import android.os.Handler;

public interface ITransmit {
	public void setSkip(String skip);
	
	public String getSkip();
	
	public String getHost();
	
	public void setHost(String host);
	
	
	
	public String download(String commend);
	
	public int downFile(String commend, String path, String fileName);
	
	public int downFile(String commend, String path, String fileName,Handler handler);
	
	public InputStream downStream(String commend);
	
	public static final class Staus
	{
		public static final int DOWN_SUCCEE=0;
		public static final int DOWN_FAIL=-1;
		public static final int DOWN_EXIST=1;
	}
	
}
