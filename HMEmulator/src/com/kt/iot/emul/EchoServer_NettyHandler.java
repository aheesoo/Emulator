package com.kt.iot.emul;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kt.iot.emul.code.StdSysTcpCode.MthdType;
import com.kt.iot.emul.func.vo.CommChAthnRespVO;
import com.kt.iot.emul.func.vo.ComnRespVO;
import com.kt.iot.emul.func.vo.DevInfoRetvRespVO;
import com.kt.iot.emul.func.vo.KeepAliveRespVO;
import com.kt.iot.emul.util.Util;
import com.kt.iot.emul.vo.MsgHeadVO;
import com.kt.iot.emul.vo.TcpHdrVO;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * Handler implementation for the echo server.
 */
@Sharable
public class EchoServer_NettyHandler extends ChannelInboundHandlerAdapter {

	public static final int MSG_HEADER_SIZE = 35;
	public static final int MSG_PACKLEN_SIZE = 4;
	
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
    	ByteBuf in = (ByteBuf) msg;
        try {
        	byte[] dataBuffer = new byte[in.readableBytes()];
        	int toLen = 0;
        	int len = 0;
            while (in.isReadable()) { // (1)
                
//            	dataBuffer = in.array();
            	System.out.print((char) in.readByte());
            	System.out.flush();
            }
//            processRcvPacket(dataBuffer);
            
        } finally {
            ReferenceCountUtil.release(msg); // (2)
        }
    }

	@Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
    
    private void processRcvPacket(byte[] dataBuffer){
		System.out.println(" receive data hexcode : "+Util.byte2Hex(dataBuffer));
		System.out.println("receive data : "+Util.byte2Hex(dataBuffer));
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").setPrettyPrinting().create();
		
		byte[] packLen = new byte[MSG_PACKLEN_SIZE];
		byte[] header = new byte[MSG_HEADER_SIZE];
		
		System.arraycopy(dataBuffer, 0, packLen, 0, MSG_PACKLEN_SIZE);
		int packLenValue = Util.byte2Int(packLen);
		
		System.arraycopy(dataBuffer, 0, header, MSG_PACKLEN_SIZE, MSG_PACKLEN_SIZE+MSG_HEADER_SIZE);
		
		TcpHdrVO tcpHderVO = gson.fromJson(new String(header), TcpHdrVO.class);
		MthdType mthd = tcpHderVO.getMthdType();
		
		int dataLength = packLenValue - header.length;
		byte[] data = new byte[dataLength];
		System.arraycopy(dataBuffer, 0, data, header.length + packLen.length, packLenValue); // body data
		
		if(MthdType.ATHN_COMMCHATHN_EXTRSYS_TCP.equals(mthd)){
//			CommChAthnRespVO commChAthnRespVO = gson.fromJson(new String(data), CommChAthnRespVO.class);
			CommChAthnRespVO commChAthnRespVO = gson.fromJson(new String(dataBuffer), CommChAthnRespVO.class);
			
			String respMsg = commChAthnRespVO.getRespMsg();
			System.out.println("RespMsg : " + respMsg);
			
			System.out.println(new String(data));
			
			/** 통신채널 인증 토큰 */
			String commChAthnNo = commChAthnRespVO.getMsgHeadVO().getCommChAthnNo();
			Main.athnNo = commChAthnNo;
		} 
		else if(MthdType.KEEP_ALIVE_COMMCHATHN_TCP.equals(mthd)){
			KeepAliveRespVO keepAliveRespVO = gson.fromJson(new String(dataBuffer), KeepAliveRespVO.class);
			
			String respMsg = keepAliveRespVO.getRespMsg();
			System.out.println("RespMsg : " + respMsg);
			
			System.out.println(new String(data));
		}
		else if(MthdType.INITA_DEV_RETV.equals(mthd)){
			DevInfoRetvRespVO devInfoRetvRespVO = gson.fromJson(new String(dataBuffer), DevInfoRetvRespVO.class);
			
			String respMsg = devInfoRetvRespVO.getRespMsg();
			System.out.println("RespMsg : " + respMsg);
			System.out.println(new String(data));
		}
		else if(MthdType.INITA_DEV_UDATERPRT.equals(mthd)){
			ComnRespVO comnRespVO = gson.fromJson(new String(dataBuffer), ComnRespVO.class);
			
			/** 메세지헤더 */
			MsgHeadVO msgHeadVO = comnRespVO.getMsgHeadVO();
			/** 응답코드 */
			String respCd = comnRespVO.getRespCd();
			/** 응답메시지 */
			String respMsg = comnRespVO.getRespMsg();
			
			System.out.println("RespMsg : " +respMsg);
		}
	}
}
