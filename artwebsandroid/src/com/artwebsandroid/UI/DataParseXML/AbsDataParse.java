package com.artwebsandroid.UI.DataParseXML;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.artwebsandroid.UI.IDataParse;
import com.artwebsandroid.UI.ITransmit;
import com.artwebsandroid.object.BinMap;
import com.artwebsandroid.utils.Utils;

public abstract class AbsDataParse implements IDataParse {
	protected BinMap para=new BinMap();
	protected ContentHandler contentHandler =new DefaultHandler(){
		private StringBuffer sb=new StringBuffer();
		public void startElement(String namespaceURI, String localName,
				String qName, Attributes attr) throws SAXException {
			sb.delete(0, sb.length());
			if(localName.equals("root"))
			AbsDataParse.this.para.put("type", attr.getValue("type"));
		}

		public void endElement(String namespaceURI, String localName, String qName)
				throws SAXException {	
			
			if (AbsDataParse.this.textElement.indexOf(localName)>=0)
				AbsDataParse.this.para.put(localName, sb.toString());
		}
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			sb.append(new String(ch, start, length));
		}
	};
	protected ArrayList<String> textElement=new ArrayList<String>();
	protected String dataStr;
	private ITransmit transmit;
	
	public AbsDataParse()
	{
		this.setTextElement();
	}
	
	public AbsDataParse(String dataStr)
	{
		this.setTextElement();
		this.dataStr=dataStr;
	}
		
	public String getDataStr() {
		return dataStr;
	}

	public void setDataStr(String dataStr) {
		this.dataStr = dataStr;
	}

	public ITransmit getTransmit() {
		return transmit;
	}

	public void setTransmit(ITransmit transmit) {
		this.transmit = transmit;
	}

	
	
	public void setPara(BinMap para) {
		this.para = para;
	}

	public void setTextElement()
	{
		textElement.add("count");
		textElement.add("return");
		textElement.add("returnflag");
		textElement.add("type");
	}
	
	public void appendTextElement(String name)
	{
		textElement.add(name);
	}
	
	public abstract void newInstance();
	
	@Override
	public BinMap parse()
	{
		this.newInstance();
		Utils.parseXMLBySAX(this.dataStr, this.contentHandler);
		return this.para;
	}

}
