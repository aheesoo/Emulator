/**
 * <PRE>
 *  Project : KTSystemIfEmulator
 *  Package : com.kt.smcp.gw.ca.gwfrwk.stdsys.test
 * </PRE>
 * @file   : PacketMaker.java
 * @date   : 2014. 5. 1. 오전 4:06:13
 * @author : CBJ
 * @brief  :
 *  변경이력    :
 *        이름     : 일자          : 근거자료   : 변경내용
 *       ------------------------------------
 *        CBJ  : 2014. 5. 1.       :            : 신규 개발.
 */
package com.kt.iot.emul.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kt.iot.emul.util.JsonPacketMaker;
import com.kt.iot.emul.util.ConvertUtil;
import com.kt.iot.emul.vo.TcpHdrVO;
import com.kt.iot.emul.func.vo.DevCommChAthnRqtVO;
import com.kt.iot.emul.func.vo.ComnRqtVO;
import com.kt.iot.emul.vo.MsgHeadVO;

/**
 * <PRE>
 *  ClassName : PacketMaker
 * </PRE>
 * @version : 1.0
 * @date    : 2014. 5. 1. 오전 4:06:13
 * @author  : CBJ
 * @brief   :
 */

public class JsonPacketMaker
{
	private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();
	
	public static byte[] getTcpPacket(byte[] header, byte[] body)
	{
		int packetlength =  header.length + body.length;
		System.out.println("packetLength : "+packetlength);
		byte[] arrPacketlength =  ConvertUtil.intTobytes(packetlength);
		System.out.println(" arrPacketlngth ===> "+new String(arrPacketlength));
		byte[] packet = new byte[packetlength + 4];

		System.arraycopy(arrPacketlength, 0, packet, 0, 4);
		System.arraycopy(header, 0, packet, 4, header.length);
		System.arraycopy(body, 0, packet, 4 + header.length, body.length);
System.out.println(" packet : "+new String(packet));
		return packet;
	}
	
	public static byte[] getCommChAthnRqtVOPacket(TcpHdrVO tcpHdrVO , DevCommChAthnRqtVO commChAthnRqtVO) throws Exception
	{
		byte[] header = tcpHdrVO.toPacket();
		String strBody = JsonPacketMaker.gson.toJson(commChAthnRqtVO);
		byte[] body = strBody.getBytes();
		return JsonPacketMaker.getTcpPacket(header, body);
	}

}
