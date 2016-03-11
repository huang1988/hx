package com.hxing.hx.common.utils.security;

import java.security.MessageDigest;

public class MD5Encrypt {
	public static String encryptMD5(String instr){
		 MessageDigest md = null;
		 String outstr = null;

		 try {
			 md = MessageDigest.getInstance("MD5");
			 byte[] digest = md.digest(instr.getBytes("UTF-8"));
			 outstr = byte2hex(digest);
		 
		  } 
		  catch (Exception e){
			 e.printStackTrace();
		  }
		  return outstr;
	}

	private static String byte2hex(byte[] b) {
		 String hs = "";
		 String stmp = "";
		 
		 for (int n = 0; n < b.length; n++) {
			 stmp = (Integer.toHexString(b[n] & 0XFF));
			 if (stmp.length() == 1) {
				 hs = hs + "0" + stmp;
			 } 
			 else {
				 hs = hs + stmp;
			 }
		 }
		 return hs;
	 }
}