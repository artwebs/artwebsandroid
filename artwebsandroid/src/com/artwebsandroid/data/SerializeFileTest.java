package com.artwebsandroid.data;

import com.artwebsandroid.object.BinMap;

import android.test.AndroidTestCase;
import android.util.Log;

public class SerializeFileTest extends AndroidTestCase {
	private final static String tag="SerializeFileTest";
	public void testSerialize()
	{
		BinMap para=new BinMap();
		para.put("id", "00001");
		para.put("name", "ÕÅÈý");
		para.put("sex", "ÄÐ");
		
		SerializeFile file=new SerializeFile();
		assertEquals(file.saveObject("com_artwebsandroid_data","info", para), true);
		
		BinMap rs=(BinMap)file.readObject("com_artwebsandroid_data","info");
		Log.d(tag, rs.getItem().toString());
	}
}
