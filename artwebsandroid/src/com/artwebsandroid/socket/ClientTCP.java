package com.artwebsandroid.socket;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import com.artwebsandroid.utils.FileUtils;

import android.os.Handler;
import android.util.Log;

public class ClientTCP extends Client {
	private Socket socket;
	
	public ClientTCP(){}
	
	public ClientTCP(String host,Integer port){
		super(host, port);
	}
	
	@Override
	public void getConnetion() {
		if(this.socket==null)
		{
			try {
				this.socket=new Socket(this.host,this.port);				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public String download(String meg) {
		this.getConnetion();
		String rs="";
		try {
			BufferedReader in=new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			OutputStream outputstream =this.socket.getOutputStream();
			outputstream.write(meg.getBytes());
			String line=null;
			while((line=in.readLine())!=null)
			{
				rs=rs+line+"\n";
			}
			this.closeConnetion();
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * 该函数返回整形 -1：代表下载文件出错 0：代表下载文件成功 1：代表文件已经存在
	 */
	public int downFile(String meg, String path, String fileName) {		
				
		InputStream inputStream = null;
		try {
			FileUtils fileUtils = new FileUtils();
			
			if (fileUtils.isFileExist(path + fileName)) {
				return 1;
			} else {
				this.getConnetion();
				inputStream = this.socket.getInputStream();
				OutputStream outputstream =this.socket.getOutputStream();
				outputstream.write(meg.getBytes());
				File resultFile = fileUtils.write2SDFromInput(path,fileName, inputStream);
				if (resultFile == null) {
					return -1;
				}
				inputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			
		}
		return 0;		
	}
	
	@Override
	public void closeConnetion() {
		try {
			this.socket.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.socket=null;
		
	}

	@Override
	public int downFile(String meg, String path, String fileName,
			Handler handler) {
		InputStream inputStream = null;
		try {
			FileUtils fileUtils = new FileUtils();
			
			if (fileUtils.isFileExist(path + fileName)) {
				return 1;
			} else {
				this.getConnetion();
				inputStream = this.socket.getInputStream();
				OutputStream outputstream =this.socket.getOutputStream();
				outputstream.write(meg.getBytes());
				File resultFile = fileUtils.write2SDFromInput(path,fileName, inputStream,handler);
				if (resultFile == null) {
					return -1;
				}
				inputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			
		}
		return 0;
	}

	@Override
	public InputStream downStream(String meg) {
		InputStream inputStream = null;
		try {
			
				this.getConnetion();
				inputStream = this.socket.getInputStream();
				OutputStream outputstream =this.socket.getOutputStream();
				outputstream.write(meg.getBytes());
				return inputStream;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		return null;
	}

}
