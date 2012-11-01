package com.artwebsandroid.utils;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class Utils {
	public static void parseXMLBySAX(String xmlStr,ContentHandler handler)
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		XMLReader reader;
		try {
			reader = factory.newSAXParser().getXMLReader();
			reader.setContentHandler(handler);
			//开始解析文件
			reader.parse(new InputSource(new StringReader(xmlStr)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally{}
	}
	
	
	public static String UrlEncode(String code, String charset)
	  {
	    String rs = "";
	    try {
	      rs = URLEncoder.encode(code, charset);
	    }
	    catch (UnsupportedEncodingException e) {
	      e.printStackTrace();
	    }
	    return rs;
	  }

	  public static String UrlDecode(String code, String charset) {
	    String rs = "";
	    try {
	      rs = URLDecoder.decode(code, charset);
	    }
	    catch (UnsupportedEncodingException e) {
	      e.printStackTrace();
	    }
	    return rs;
	  }
}
