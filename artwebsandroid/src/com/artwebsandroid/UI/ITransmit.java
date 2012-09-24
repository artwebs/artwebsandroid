package com.artwebsandroid.UI;

public interface ITransmit {
	public void setSkip(String skip);
	
	public String getSkip();
	
	public String download(String commend);
	
	public int downFile(String commend, String path, String fileName);
	
	public static final class Staus
	{
		public static final int DOWN_SUCCEE=0;
		public static final int DOWN_FAIL=-1;
		public static final int DOWN_EXIST=1;
	}
	
}
