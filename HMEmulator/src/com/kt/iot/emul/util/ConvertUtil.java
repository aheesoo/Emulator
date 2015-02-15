/**
 * <PRE>
 *  Project : GWCommAgent
 *  Package : com.kt.smcp.gw.ca.util
 * </PRE>
 * @file   : ConvertUtil.java
 * @date   : 2013. 12. 20. 오후 6:00:23
 * @author : 추병조
 * @brief  :
 *  변경이력    :
 *        이름     : 일자          : 근거자료   : 변경내용
 *       ------------------------------------
 *        추병조  : 2013. 12. 20.       :            : 신규 개발.
 */
package com.kt.iot.emul.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;
/**
 * <PRE>
 *  ClassName : ConvertUtil
 * </PRE>
 * @version : 1.0
 * @date    : 2013. 12. 20. 오후 6:00:23
 * @author  : 추병조
 * @brief   :
 */

public class ConvertUtil
{
	public static byte[] doubleTobytes(double value)
	{
		return ByteBuffer.allocate(8).putDouble(value).array();
	}

	public static byte[] doubleTobytes(double value, boolean bigEndian)
	{
		if(bigEndian)
		{
			return ByteBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN).putDouble(value).array();
		}
		else
		{
			return ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putDouble(value).array();
		}
	}

	public static byte[] floatTobytes(float value)
	{
		return ByteBuffer.allocate(4).putFloat(value).array();
	}

	public static byte[] floatTobytes(float value, boolean bigEndian)
	{
		if(bigEndian)
		{
			return ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putFloat(value).array();
		}
		else
		{
			return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putFloat(value).array();
		}
	}

	public static float bytesTofloat(byte[] value)
	{
		return ByteBuffer.wrap(value).getFloat();
	}

	public static double bytesTodouble(byte[] value)
	{
		return ByteBuffer.wrap(value).getDouble();
	}

	public static float bytesTofloat(byte[] value, boolean bigEndian)
	{
		if(bigEndian)
		{
			return ByteBuffer.wrap(value).order(ByteOrder.BIG_ENDIAN).getFloat();
		}
		else
		{
			return ByteBuffer.wrap(value).order(ByteOrder.LITTLE_ENDIAN).getFloat();
		}
	}

	public static byte[] longTobytes(long value)
	{
		return ByteBuffer.allocate(8).putLong(value).array();
	}

	public static byte[] intTobytes(int value)
	{
		return ByteBuffer.allocate(4).putInt(value).array();
	}

	public static byte[] intTobytes(int value, boolean bigEndian)
	{
		if(bigEndian)
		{
			return ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(value).array();
		}
		else
		{
			return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(value).array();
		}
	}

	public static long bytesTolong(byte[] value)
	{
		return ByteBuffer.wrap(value).getLong();
	}

	public static long bytesTolong(byte[] value, int index)
	{
		//TODO 업데이트
		byte[] arrValue = new byte[8];
		System.arraycopy(value, index, arrValue, 0, 8);
		return bytesTolong(arrValue);
	}

	public static int bytesToint(byte[] value)
	{
		return ByteBuffer.wrap(value).getInt();
	}

	public static int bytesToint(byte[] value, int index)
	{
		//TODO 업데이트
		byte[] arrValue = new byte[4];
		System.arraycopy(value, index, arrValue, 0, 4);
		return bytesToint(arrValue);
	}

	public static int bytesToint(byte[] value, boolean bigEndian)
	{
		if(bigEndian)
		{
			return ByteBuffer.wrap(value).order(ByteOrder.BIG_ENDIAN).getInt();
		}
		else
		{
			return ByteBuffer.wrap(value).order(ByteOrder.LITTLE_ENDIAN).getInt();
		}
	}

	public static byte[] shortTobytes(short value)
	{
		return ByteBuffer.allocate(2).putShort(value).array();
	}

	public static byte[] shortTobytes(short value, boolean bigEndian)
	{
		if(bigEndian)
		{
			return ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN).putShort(value).array();
		}
		else
		{
			return ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(value).array();
		}
	}

	public static short bytesToshort(byte[] value)
	{
		return ByteBuffer.wrap(value).getShort();
	}

	public static short bytesToshort(byte[] value, int index)
	{
		//TODO 업데이트
		byte[] arrValue = new byte[2];
		System.arraycopy(value, index, arrValue, 0, 2);
		return bytesToshort(arrValue);
	}

	public static short bytesToshort(byte[] value, boolean bigEndian)
	{
		if(bigEndian)
		{
			return ByteBuffer.wrap(value).order(ByteOrder.BIG_ENDIAN).getShort();
		}
		else
		{
			return ByteBuffer.wrap(value).order(ByteOrder.LITTLE_ENDIAN).getShort();
		}
	}

	public static UUID bytesToUUID(byte[] src, int index)
	{
		byte[] arrValue = new byte[16];
		System.arraycopy(src, index, arrValue, 0, 16);
		return bytesToUUID(arrValue);

	}

	public static UUID bytesToUUID(byte[] src)
	{
		ByteBuffer buffer = ByteBuffer.wrap(src);
		buffer.order(ByteOrder.BIG_ENDIAN);
		long firstLong = buffer.getLong();
		long secondLong = buffer.getLong();

		return new UUID(firstLong, secondLong);
	}

	public static byte[] UUIDToBytes(UUID uuid)
	{
		Long mostValue  = uuid.getMostSignificantBits();
		Long leastValue = uuid.getLeastSignificantBits();
		byte[] arrMostValue = ConvertUtil.longTobytes(mostValue);
		byte[] arrLeastValue = ConvertUtil.longTobytes(leastValue);
		byte[] arrUUID = new byte[16];
		System.arraycopy(arrMostValue, 0, arrUUID, 0, 8);
		System.arraycopy(arrLeastValue, 0, arrUUID, 8, 8);

		return arrUUID;
	}
}
