package com.kt.iot.emul.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

public class Util {
	public static byte[] int2Byte(int i) {
		ByteBuffer bb = ByteBuffer.allocate(Integer.SIZE / 8);
		bb.putInt(i);
		bb.order(ByteOrder.BIG_ENDIAN);
		return bb.array();
	}
	
	public static int byte2Int(byte[] bytes) {
		final int size = Integer.SIZE / 8;
		ByteBuffer bb = ByteBuffer.allocate(size);
		final byte[] newBytes = new byte[size];
		
		for(int i = 0; i < size; i++){
			if(i+ bytes.length < size){
				newBytes[i] = (byte) 0x00;				
			} else{
				newBytes[i] = bytes[i + bytes.length - size];
			}
			
		}
		bb = ByteBuffer.wrap(newBytes);
		bb.order(ByteOrder.BIG_ENDIAN);
		return bb.getInt();
	}
	
	public static byte[] long2Byte(long l){
		ByteBuffer bb = ByteBuffer.allocate(8);
		bb.putLong(l);
		bb.order(ByteOrder.BIG_ENDIAN);
		return bb.array();
	}
	
	public static long byte2Long(byte[] bytes) {
		ByteBuffer bb = ByteBuffer.allocate(8);
		final byte[] change = new byte[8];
		for (int i = 0; i < 8; i++) {
			change[i] = (byte) 0x00;
		}

		for (int i = 0; i < bytes.length; i++) {
			change[7 - i] = bytes[bytes.length - 1 - i];
		}

		bb = ByteBuffer.wrap(change);
		bb.order(ByteOrder.BIG_ENDIAN);

		return bb.getLong();
	}
	
	public static byte[] uuid2Byte(String s) {
		UUID uid = UUID.fromString(s);
		
		Long svcTgtSeq  = uid.getMostSignificantBits();
		Long spotDevSeq = uid.getLeastSignificantBits();
		
		byte[] arrSvcTgtSeq = long2Byte(svcTgtSeq);
		byte[] arrSpotDevSeq = long2Byte(spotDevSeq);
		byte[] _uid = new byte[16];
		
		System.arraycopy(arrSvcTgtSeq, 0, _uid, 0, 8);
		System.arraycopy(arrSpotDevSeq, 0, _uid, 8, 8);
		
		return _uid;
	}
	
	public static String byte2UUID(byte[] b) {
		byte[] _b1 = new byte[8];
		byte[] _b2 = new byte[8];
		
		System.arraycopy(b, 0, _b1, 0, 8);
		System.arraycopy(b, 8, _b2, 0, 8);
		
		Long svcTgtSeq1 = byte2Long(_b1);
		Long spotDevSeq1= byte2Long(_b2);
		
		UUID uid2 = new UUID(svcTgtSeq1, spotDevSeq1);
		String uidKey2 = uid2.toString().toUpperCase();
		
		return uidKey2;
	}
	
	public static String byte2Str(byte[] b) {
		return new java.math.BigInteger(b).toString(16); 
	}
	
	public static byte[] hex2Byte(String hex) {
	    if (hex == null || hex.length() == 0) {
	        return null;
	    }
	 
	    byte[] ba = new byte[hex.length() / 2];
	    for (int i = 0; i < ba.length; i++) {
	        ba[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
	    }
	    return ba;
	}
	
	public static String byte2Hex(byte[] b) {
	    if (b == null || b.length == 0) {
	        return null;
	    }
	 
	    StringBuffer sb = new StringBuffer(b.length * 2);
	    String hexNumber;
	    for (int x = 0; x < b.length; x++) {
	        hexNumber = "0" + Integer.toHexString(0xff & b[x]);
	 
	        sb.append(hexNumber.substring(hexNumber.length() - 2));
	    }
	    return sb.toString().toUpperCase();
	}	
}


