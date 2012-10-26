package com.artwebsandroid.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.util.Log;

public class SerializeFile {
	private final static String tag="SerializeFile";
	private final static String rootPath="/";
	public boolean saveObject(String path,String filename,Object obj)
	{
		boolean flag=false;
		FileOutputStream fs;
		try {
			File dir = new File(rootPath + path);
			dir.mkdirs();
			fs = new FileOutputStream(rootPath+path+filename);
			ObjectOutputStream os =  new ObjectOutputStream(fs);     
	        os.writeObject(obj);     
	        os.close(); 
	        flag=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     
        return flag;  
	}
	
	public Object readObject(String path,String filename)
	{
		Object obj=null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			
			fis = new FileInputStream(rootPath+path+filename);
			Log.d(tag,"SerializeFile");
			ois = new ObjectInputStream(fis);
			
			obj = ois.readObject();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj;		
	}
}
