package com.kt.iot.emul.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtil
{
	private static String defaultFormat = "yyyy-MM-dd HH:mm:ss.SSS";
	private static String yyyyMMddHHmmssFormat = "yyyy-MM-dd HH:mm:ss";
	private static String HHmmssFormat = "HH:mm:ss";

	public static String doubleToString(double value, String pattern)
	{
		DecimalFormat myFormatter = new DecimalFormat(pattern);
	    String output = myFormatter.format(value);
		return output;
	}

	// hex to byte[]
	public static byte[] hexToByteArray(String hex)
	{
		//new java.math.BigInteger(packet, 16).toByteArray();
	    if (hex == null || hex.length() == 0) {
	        return null;
	    }

	    byte[] ba = new byte[hex.length() / 2];
	    for (int i = 0; i < ba.length; i++) {
	        ba[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
	    }
	    return ba;
	}

	// byte[] to hex
	public static String byteArrayToHex(byte[] ba)
	{
	    if (ba == null || ba.length == 0) {
	        return null;
	    }

	    StringBuffer sb = new StringBuffer(ba.length * 2);
	    String hexNumber;
	    for (int x = 0; x < ba.length; x++)
	    {
	        hexNumber = "0" + Integer.toHexString(0xff & ba[x]);
	        sb.append(hexNumber.substring(hexNumber.length() - 2));
	    }
	    return sb.toString();
	}

	// byte[] to hex
	public static String byteArrayToHex(byte[] ba, int lineDivCount)
	{
	    if (ba == null || ba.length == 0) {
	        return null;
	    }

	    StringBuffer sb = new StringBuffer(ba.length * 2);
	    String hexNumber;
	    for (int x = 0; x < ba.length; x++)
	    {
	    	if(x != 0 && (x % lineDivCount == 0) )
	        {
	        	sb.append("\n");
	        }
	        hexNumber = "0" + Integer.toHexString(0xff & ba[x]);
	        sb.append(hexNumber.substring(hexNumber.length() - 2));
	    }
	    return sb.toString();
	}

	// byte[] to hex
	public static String byteArrayToHex(byte[] ba, boolean upperCase)
	{
	    if (ba == null || ba.length == 0) {
	        return null;
	    }

	    StringBuffer sb = new StringBuffer(ba.length * 2);
	    String hexNumber;
	    for (int x = 0; x < ba.length; x++)
	    {
	        hexNumber = "0" + Integer.toHexString(0xff & ba[x]);
	        sb.append(hexNumber.substring(hexNumber.length() - 2));
	    }
	    if(upperCase)
	    {
	    	return sb.toString().toUpperCase();
	    }
	    else
	    {
	    	return sb.toString();
	    }
	}

	// byte[] to hex
	public static String byteArrayToHex(byte[] byteArray, String prefix, String delimiter, boolean upperCase)
	{
	    if (byteArray == null || byteArray.length == 0) {
	        return null;
	    }

	    StringBuffer sb = new StringBuffer(byteArray.length * 2);
	    String hexNumber;
	    for (int x = 0; x < byteArray.length; x++)
	    {
	        hexNumber = "0" + Integer.toHexString(0xff & byteArray[x]);
	        hexNumber = hexNumber.substring(hexNumber.length() - 2);
	        if(upperCase)
	        {
	        	hexNumber = hexNumber.toUpperCase();
	        }
	        if(x >= byteArray.length -1)
	        {
	        	sb.append(prefix+hexNumber);
	        }
	        else
	        {
	        	sb.append(prefix+hexNumber+delimiter);
	        }
	    }

	    return sb.toString();
	}

	public static String getPaddingString(String source, int length, String fillString)
	{
		return getPaddingString(source, length, fillString, true, false);
	}

	public static String getPaddingString(String source, int length, String fillString, boolean leftCut)
	{
		return getPaddingString(source, length, fillString, true, leftCut);
	}

	public static String getPaddingString(String source, int length, String fillString, boolean leftPadding, boolean leftCut)
	{
		if(source.length() < length)
		{
			StringBuilder sb = new StringBuilder(source);
			while(true)
			{
				if (sb.length() >= length)
				{
					break;
				}

				if(leftPadding)
				{
					sb.insert(0, fillString);
				}
				else
				{
					sb.append(fillString);
				}
			}
			source = sb.toString();
		}

		if (source.length() > length)
		{
			if(leftCut)
			{
				source = source.substring(source.length()-length);
			}
			else
			{
				source = source.substring(0, length);
			}
		}
		return source;
	}

	public static String DateToString(Date date)
	{
		return DateToString(date, defaultFormat);
	}

	public static String DateToStringyyyyMMddHHmmss(Date date)
	{
		return DateToString(date, yyyyMMddHHmmssFormat);
	}

	public static String DateToStringHHmmss(Date date)
	{
		return DateToString(date, HHmmssFormat);
	}

	public static String DateToString(Date date, String format)
	{
		SimpleDateFormat nowSimpleDateFormat = new SimpleDateFormat(format);
		return nowSimpleDateFormat.format(date);
	}

	public static Date StringToDate(String time) throws ParseException
	{
		return StringtoDate(time, defaultFormat);
	}

	public static Date StringtoDate(String time, String format) throws ParseException
	{
		SimpleDateFormat nowSimpleDateFormat = new SimpleDateFormat(format);
		return nowSimpleDateFormat.parse(time);
	}
}
