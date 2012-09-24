package com.artwebsandroid.socket;
public abstract class Client {
	protected String host;
	protected Integer port;
	
	public Client()
	{}
	
	public Client(String host,Integer port)
	{
		this.host=host;
		this.port=port;
	}
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	
	public abstract void getConnetion();
	
	public abstract String download(String msg);
	
	public abstract int downFile(String meg, String path, String fileName);
	
	public abstract void closeConnetion();
	
}
