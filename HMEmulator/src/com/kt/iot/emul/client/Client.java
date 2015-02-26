package com.kt.iot.emul.client;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.io.IOUtils;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kt.iot.emul.Main;
import com.kt.iot.emul.model.Header;
import com.kt.iot.emul.util.Util;
import com.kt.iot.emul.vo.CmdDataInfoVO;
import com.kt.iot.emul.func.vo.ComnRespVO;
import com.kt.iot.emul.vo.DataTypeVO;
import com.kt.iot.emul.vo.DevBasVO;
import com.kt.iot.emul.vo.DevCommChDtlVO;
import com.kt.iot.emul.vo.DevDtlVO;
import com.kt.iot.emul.func.vo.DevInfoRetvRespVO;
import com.kt.iot.emul.func.vo.KeepAliveRespVO;
import com.kt.iot.emul.vo.MsgHeadVO;
import com.kt.iot.emul.vo.TcpHdrVO;
import com.kt.iot.emul.func.vo.CommChAthnRespVO;
import com.kt.iot.emul.util.JsonPacketMaker;
import com.kt.iot.emul.util.TCPUtil;
import com.kt.iot.emul.code.StdSysTcpCode;
import com.kt.iot.emul.code.StdSysTcpCode.MthdType;

public class Client extends Thread {
	public String SERVERIP = "127.0.0.1";//"211.42.137.221";												
	public int SERVERPORT = 9077;
	public int version = 1;
	private Socket socket;
	private OutputStream outputStream;
	private InputStream inputStream;
	
	private boolean mRun = false;
	private int maxRecvLength = 4096;
	
	public static final int MSG_HEADER_SIZE = 35;
	public static final int MSG_PACKLEN_SIZE = 4;
	
	
	public int scheduled = 1;
	public String timeFrom = "1530";
	public String timeTo = "1500";
	public int day = 1;
	public String timeRec = "1100";
	public int modeRec = 1;
	public int durationRec = 1;
	public int dayRec = 1;
	
	public int voiceCnt = 0;
	
	private Timer jobScheduler = new Timer();	
	
	public Client() {}
	
	public Client(String ip, int port) {
		this.SERVERIP = ip;
		this.SERVERPORT = port;
	}
	
	public Socket getSocket() {
		return this.socket;
	}
	
	public void stopClient() {
		mRun = false;
	}
	
	/**
	 * Sends the message entered by client to the server
	 * 
	 * @param message
	 *            text entered by client
	 */
	/*public void sendData(byte[] header, byte[] body) throws IOException{
		try {
			byte[] packet = JsonPacketMaker.getTcpPacket(header, body);
			byte[] recvPacket =TCPUtil.sendAndRecv(SERVERIP, SERVERPORT, packet, 3000, 4096);
			
			processRcvPacket(recvPacket);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}*/	
	
	public void sendData(byte[] header, byte[] body, short mthdCode) throws IOException{
		byte[] packet = JsonPacketMaker.getTcpPacket(header, body);
		try {
//			outputStream = socket.getOutputStream();
//			inputStream = socket.getInputStream();
			if(outputStream != null){
				outputStream.write(packet);
				outputStream.flush();
				
				Main.report("send hexcode : " + Util.byte2Hex(packet), true);
//				Main.report("\n send header : " + new String(header), true);
				Main.report("\n send header : " + Util.byte2Hex(header), true);
				Main.report("\n send body : " + new String(body), true);
			}
		} catch (SocketException e) {
			Main.btnInit();
			Main.report("disconnected : " + this.SERVERIP + ":" + this.SERVERPORT, true);
			e.printStackTrace();
		}
		
	}
	
	public void run(){
		mRun = true;
		
		byte[] buffer = new byte[maxRecvLength];
		
		try {
			socket = new Socket(SERVERIP, SERVERPORT);
			Main.report("connected : " + SERVERIP + ":" + SERVERPORT, true);
			try {
				outputStream = socket.getOutputStream();
				inputStream = socket.getInputStream();
				while(mRun){
					int offset = 0;
					int wanted = maxRecvLength;
					int len = 0;
					int totlen = 0;
					while(wanted > 0){
//					while((len = inputStream.read( buffer, offset, wanted )) != -1)
//					for(;;){
					try{
				    		len = inputStream.read( buffer, offset, wanted );
					        if( len <= 0)
					        {
					        	break;
					        }
				    	}catch(Exception e)
				    	{
				    		break;
				    	}
				        wanted -= len;
				        offset += len;
				        totlen += len;
					}
					byte[] result = new byte[totlen];
					System.arraycopy(buffer, 0, result, 0, totlen );
					processRcvPacket(result);
					
					/*ByteArrayOutputStream lByteArrayOutputStream = new ByteArrayOutputStream(wanted * 2);
					len=inputStream.read(buffer);
					offset += len;
					lByteArrayOutputStream.write(buffer, 0, len);
					System.out.println(" len : "+len);
					System.out.println(" offset : "+offset);
					byte[] result = lByteArrayOutputStream.toByteArray();
					processRcvPacket(result);*/
				}
			    
			} catch (Exception e) {
				Main.btnInit();
				Main.report(e.toString(), true);
				e.printStackTrace();
			} finally {
				closeClient();
			}
			
		} catch (Exception e) {
			Main.btnInit();
			Main.report(e.toString(), true);
			e.printStackTrace();
		}
	}

	
	public void closeClient() {
		try {
			jobScheduler.cancel();
			stopClient();
			if(outputStream != null) outputStream.close();
			if(inputStream != null) inputStream.close();
			if(socket != null) socket.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void processRcvPacket(byte[] dataBuffer){
		System.out.println(" receive data hexcode : "+Util.byte2Hex(dataBuffer));
		Main.report("receive data : "+Util.byte2Hex(dataBuffer), true);
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
			Main.report("RespMsg : " + respMsg, true);
			
			Main.report(new String(data), true);
			
			/** 통신채널 인증 토큰 */
			String commChAthnNo = commChAthnRespVO.getMsgHeadVO().getCommChAthnNo();
			Main.athnNo = commChAthnNo;
			
		} 
		else if(MthdType.KEEP_ALIVE_COMMCHATHN_TCP.equals(mthd)){
			KeepAliveRespVO keepAliveRespVO = gson.fromJson(new String(dataBuffer), KeepAliveRespVO.class);
			
			String respMsg = keepAliveRespVO.getRespMsg();
			Main.report("RespMsg : " + respMsg, true);
			
			Main.report(new String(data), true);
		}
		else if(MthdType.INITA_DEV_RETV.equals(mthd)){
			DevInfoRetvRespVO devInfoRetvRespVO = gson.fromJson(new String(dataBuffer), DevInfoRetvRespVO.class);
			
			String respMsg = devInfoRetvRespVO.getRespMsg();
			Main.report("RespMsg : " + respMsg, true);
			
			/** 명령데이터리스트(31) *//*
			List<CmdDataInfoVO> cmdDataInfoVOs = new ArrayList<CmdDataInfoVO>();
			*//** 장치정보목록 *//*
			List<DevBasVO> devBasVOs = new ArrayList<DevBasVO>();
			
			cmdDataInfoVOs = devInfoRetvRespVO.getCmdDataInfoVOs();
			for(int i=0; i<cmdDataInfoVOs.size(); i++){
				Main.report("dataTypeCd : " + cmdDataInfoVOs.get(i).getDataTypeCd(), true);
				Main.report("athnNo : " + cmdDataInfoVOs.get(i).getCmdData(), true);			}
			devBasVOs = devInfoRetvRespVO.getDevBasVOs();
			for(int j=0; j< devBasVOs.size(); j++){
				Main.report("extrSysId : " + devBasVOs.get(j).getExtrSysId(), true);
				Main.report("devId : " + devBasVOs.get(j).getDevId(), true);
				Main.report("devNm : " + devBasVOs.get(j).getDevNm(), true);
				Main.report("modelNm : " + devBasVOs.get(j).getModelNm(), true);
				
				List<DevDtlVO> devDtlVOs = devBasVOs.get(j).getDevDtlVOs();
				for(int a=0; a<devDtlVOs.size(); a++){
					Main.report("atribNm : " + devDtlVOs.get(a).getAtribNm(), true);
					Main.report("atribVal : " + devDtlVOs.get(a).getAtribVal(), true);
				}
				List<DataTypeVO> dataTypeVOs = devBasVOs.get(j).getDataTypeVOs();
				for(int b=0; b<dataTypeVOs.size(); b++){
					Main.report("dataTypeCtgCd : " + dataTypeVOs.get(b).getDataTypeCtgCd(), true);
					Main.report("dataTypeCd : " + dataTypeVOs.get(b).getDataTypeCd(), true);
				}
				List<DevCommChDtlVO> devCommChDtlVOs = devBasVOs.get(j).getDevCommChDtlVOs();
				for(int c=0; c<devCommChDtlVOs.size(); c++){
					Main.report("comChId : " + devCommChDtlVOs.get(c).getCommChId(), true);
					Main.report("comChCd : " + devCommChDtlVOs.get(c).getCommChCd(), true);
					Main.report("ipadr : " + devCommChDtlVOs.get(c).getIpadr(), true);
					Main.report("ifTypeCd : " + devCommChDtlVOs.get(c).getIfTypeCd(), true);
					Main.report("cnctTypeCd : " + devCommChDtlVOs.get(c).getCnctTypeCd(), true);
					Main.report("portNo : " + devCommChDtlVOs.get(c).getPortNo(), true);
				}
			}*/
			
			Main.report(new String(data), true);
		}
		else if(MthdType.INITA_DEV_UDATERPRT.equals(mthd)){
			ComnRespVO comnRespVO = gson.fromJson(new String(dataBuffer), ComnRespVO.class);
			
			/** 메세지헤더 */
			MsgHeadVO msgHeadVO = comnRespVO.getMsgHeadVO();
			/** 응답코드 */
			String respCd = comnRespVO.getRespCd();
			/** 응답메시지 */
			String respMsg = comnRespVO.getRespMsg();
			
			Main.report("RespMsg : " +respMsg , true);
		}
		else if(MthdType.COLEC_ITGDATA_RECV.equals(mthd)){
			ComnRespVO comnRespVO = gson.fromJson(new String(dataBuffer), ComnRespVO.class);
			
			/** 메세지헤더 */
			MsgHeadVO msgHeadVO = comnRespVO.getMsgHeadVO();
			/** 응답코드 */
			String respCd = comnRespVO.getRespCd();
			/** 응답메시지 */
			String respMsg = comnRespVO.getRespMsg();
			
			Main.report("RespMsg : " +respMsg , true);
		}
	}
	
	class ScheduledJob extends TimerTask {
		public void run() {
			Main.keepAlive();
		}
	}
}