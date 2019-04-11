package com.atguigu.util;

import java.security.MessageDigest;

import org.springframework.util.StringUtils;

public class MD5Utils {
	
	public static String digestPassword(String pwd) {
		for(int i=0;i<7;i++) {
			pwd = digest(pwd);
		}
		return pwd;
	}
	
	public static String digest16(String inStr) {
		return digest(inStr, 16);
	}

	public static String digest(String inStr) {
		return digest(inStr, 32);
	}

	private static String digest(String inStr, int rang) {
		MessageDigest md5 = null;
		if (StringUtils.isEmpty(inStr)) {
			return "";
		}

		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}

		byte[] md5Bytes = md5.digest(byteArray);

		StringBuilder hexValue = new StringBuilder();

		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		if (rang == 32) {
			return hexValue.toString();
		} else {
			return hexValue.toString().substring(8, 24);
		}
	}

	public static void main(String args[]) {
		String s = new String("123456");
		//e10adc3949ba59abbe56e057f20f883e
		for(int i=0;i<20;i++) {
			s = digest(s);
		}
		System.out.println(digest(s));
	}

}
