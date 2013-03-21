package cn.artwebs.utils;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Calendar;

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
	  
	  /* Convert byte[] to hex string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。  
	     * @param src byte[] data  
	     * @return hex string  
	     */     
	    public static String bytesToHexString(byte[] src){  
	        StringBuilder stringBuilder = new StringBuilder("");  
	        if (src == null || src.length <= 0) {  
	            return null;  
	        }  
	        for (int i = 0; i < src.length; i++) {  
	            int v = src[i] & 0xFF;  
	            String hv = Integer.toHexString(v);  
	            if (hv.length() < 2) {  
	                stringBuilder.append(0);  
	            }  
	            stringBuilder.append(hv);  
	        }  
	        return stringBuilder.toString();  
	    }  
	    /** 
	     * Convert hex string to byte[] 
	     * @param hexString the hex string 
	     * @return byte[] 
	     */  
	    public static byte[] hexStringToBytes(String hexString) {  
	        if (hexString == null || hexString.equals("")) {  
	            return null;  
	        }  
	        hexString = hexString.toUpperCase();  
	        int length = hexString.length() / 2;  
	        char[] hexChars = hexString.toCharArray();  
	        byte[] d = new byte[length];  
	        for (int i = 0; i < length; i++) {  
	            int pos = i * 2;  
	            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
	        }  
	        return d;  
	    }  
	    /** 
	     * Convert char to byte 
	     * @param c char 
	     * @return byte 
	     */  
	     private static byte charToByte(char c)
	     {  
	        return (byte) "0123456789ABCDEF".indexOf(c);  
	     }  
	     
	     public static String GetEnoughLenStr(int num,String basestr){
	 		String rs=basestr;
	 		int baseint=rs.length();
	 		if(baseint<num){
	 			for(int i=baseint;i<num;i++)
	 			{
	 				rs="0"+rs;
	 			}
	 		}
	 		return rs;
	 	}
	     
	     /**
		    * 获取当前时间
		    * @param type yyyy MM dd ww hh mm ss
		    * @return
		    */
		   public static String getNowStr(String type){
			   	String rsStr=type;
			      Calendar ca = Calendar.getInstance(); 
			      String year = Utils.GetEnoughLenStr(4, ""+ca.get(Calendar.YEAR));//获取年份 
			      String month=Utils.GetEnoughLenStr(2, ""+(ca.get(Calendar.MONTH)+1));//获取月份  
			      String day=Utils.GetEnoughLenStr(2, ""+ca.get(Calendar.DATE));//获取日 
			      String minute=Utils.GetEnoughLenStr(2, ""+ca.get(Calendar.MINUTE));//分  
			      String hour=Utils.GetEnoughLenStr(2, ""+ca.get(Calendar.HOUR_OF_DAY));//小时  
			      String second=Utils.GetEnoughLenStr(2, ""+ca.get(Calendar.SECOND));//秒 
			      String WeekOfYear =Utils.GetEnoughLenStr(2, ""+ca.get(Calendar.DAY_OF_WEEK));  
			      rsStr=rsStr.replace("yyyy", year+"");
			      rsStr=rsStr.replace("MM", month+"");
			      rsStr=rsStr.replace("dd", day+"");
			      rsStr=rsStr.replace("ww", WeekOfYear+"");
			      rsStr=rsStr.replace("hh", hour+"");
			      rsStr=rsStr.replace("mm", minute+"");
			      rsStr=rsStr.replace("ss", second+"");		      
			      return rsStr;
		   }
		   
		   
		
}
