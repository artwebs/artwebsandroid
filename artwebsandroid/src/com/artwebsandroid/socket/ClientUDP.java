package com.artwebsandroid.socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import android.util.Log;

public class ClientUDP extends Client {
	private DatagramPacket packet;
	private DatagramSocket socket;
	@Override
	public void getConnetion() {
		if(packet!=null)	
		{
			try {

				this.socket=new DatagramSocket();
				packet.setAddress(InetAddress.getByName(this.getHost()));
				packet.setPort(this.port);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public String download(String meg) {
		String rs="";
		byte data[] =meg.getBytes();
		packet=new DatagramPacket(data,data.length);
		this.getConnetion();
		try {			
			this.socket.send(packet);
			Log.i("port", this.socket.getLocalPort()+"");
			
			byte rData[]=new byte[2048];
			DatagramPacket rPacket=new DatagramPacket(rData,rData.length);
			this.socket.receive(rPacket);
			rs=new String(rData,0,rPacket.getLength());			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.closeConnetion();			
		return rs;
	}



	@Override
	public int downFile(String meg, String path, String fileName) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	@Override
	public void closeConnetion() {
		this.socket.close();
		this.socket=null;		
	}

}
