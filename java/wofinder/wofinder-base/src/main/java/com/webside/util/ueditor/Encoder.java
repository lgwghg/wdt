package com.webside.util.ueditor;

import java.io.UnsupportedEncodingException;

public class Encoder {

	public static String toUnicode ( String input ) {
		
		StringBuilder builder = new StringBuilder();
		char[] chars = input.toCharArray();
		
		for ( char ch : chars ) {
			
			if ( ch < 256 ) {
				builder.append( ch );
			} else {
				builder.append( "\\u" +  Integer.toHexString( ch& 0xffff ) );
			}
			
		}
		
		return builder.toString();
		
	}
	
	public static String encodeStr(String str) {  
        try {  
            return new String(str.getBytes("ISO-8859-1"), "UTF-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
            return null;  
        }  
    }
	public static String encodeStr(String str, String encode) {  
        try {  
            return new String(str.getBytes(), encode);  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
            return null;  
        }  
    } 
}