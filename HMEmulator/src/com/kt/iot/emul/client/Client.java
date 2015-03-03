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
import com.kt.iot.emul.func.vo.LastValQueryRqtVO;
import com.kt.iot.emul.vo.MsgHeadVO;
import com.kt.iot.emul.vo.TcpHdrVO;
import com.kt.iot.emul.func.vo.CommChAthnRespVO;
import com.kt.iot.emul.util.ConvertUtil;
import com.kt.iot.emul.util.JsonPacketMaker;
import com.kt.iot.emul.util.TCPUtil;
import com.kt.iot.emul.code.StdSysTcpCode;
import com.kt.iot.emul.code.StdSysTcpCode.MthdType;
import com.kt.iot.emul.code.StdSysTcpCode.MsgType;

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
				Main.report("\n send header(String) : " + new String(header), true);
				Main.report("\n send header(Hex Code) : " + Util.byte2Hex(header), true);
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
					/*int offset = 0;
					int wanted = maxRecvLength;
					int len = 0;
					int totlen = 0;
					while(wanted > 0){
					//while((len = inputStream.read( buffer, offset, wanted )) != -1){
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
				        System.out.println("len : "+len+" / offset : "+offset+" / totlen : "+totlen+" / wanted : "+wanted);
					}
					System.out.println("while out len : "+len);
					byte[] result = new byte[totlen];
					System.arraycopy(buffer, 0, result, 0, totlen );
					processRcvPacket(result);*/
					
					
					/******** read packet *********/
					byte[] packetArr = new byte[4];
					int readPacketSize = 0;
					int totalPacketRead = 0;
					int offset = 4;
					do {
						readPacketSize = inputStream.read(packetArr, totalPacketRead, offset-totalPacketRead);
						totalPacketRead += readPacketSize;
					} while(totalPacketRead < 4 && readPacketSize != -1);
					
//					int packetSize = Integer.parseInt(new String(packetArr));
					int packetSize = Util.byte2Int(packetArr);
					int readSize = 0;
					int totalRead = 0;
					byte[] result = new byte[packetSize];
					
					do {
						readSize = inputStream.read(result, totalRead, packetSize-totalRead);
						totalRead += readSize;
					} while(totalRead < packetSize && readSize != -1);
					processRcvPacket(result, packetSize);
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
	
	private void processRcvPacket(byte[] dataBuffer, int packLenValue) throws Exception{
		Main.report("receive data - hex code : "+Util.byte2Hex(dataBuffer), true);
		System.out.println("receive data - String : "+new String(dataBuffer));
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").setPrettyPrinting().create();
		
		short headerLen = MSG_HEADER_SIZE;
		byte[] headerSizeBuf = new byte[2];
		System.arraycopy(dataBuffer, 2, headerSizeBuf, 0, 1);
		short headerSize = ConvertUtil.bytesToshort(headerSizeBuf);
		if(headerLen < headerSize){
			headerLen = headerSize;
		}
		
//		byte[] header = new byte[MSG_HEADER_SIZE];
		byte[] header = new byte[headerLen];
		
//		System.arraycopy(dataBuffer, 0, header, 0, MSG_HEADER_SIZE);
		System.arraycopy(dataBuffer, 0, header, 0, headerLen);
		
		TcpHdrVO tcpHdrVO = new TcpHdrVO();
		tcpHdrVO.setPacket(header);
		MthdType mthd = tcpHdrVO.getMthdType();
		MsgType msgType = tcpHdrVO.getMsgType();
		
		int dataLength = packLenValue - header.length;
		byte[] data = new byte[dataLength];
		System.arraycopy(dataBuffer, header.length, data, 0, packLenValue-header.length); // body data
		
		if(MsgType.RESPONSE.equals(msgType)){ // 요청에 대한 수신
			if(MthdType.ATHN_COMMCHATHN_EXTRSYS_TCP.equals(mthd)){
				CommChAthnRespVO commChAthnRespVO = gson.fromJson(new String(data), CommChAthnRespVO.class);
//				CommChAthnRespVO commChAthnRespVO = gson.fromJson(new String(dataBuffer), CommChAthnRespVO.class);
				
				String respMsg = commChAthnRespVO.getRespMsg();
				Main.report("RespMsg : " + respMsg, true);
				Main.report(new String(data), true);
				
				/** 통신채널 인증 토큰 */
				String commChAthnNo = commChAthnRespVO.getMsgHeadVO().getCommChAthnNo();
				Main.athnNo = commChAthnNo;
				
			} 
			else if(MthdType.KEEP_ALIVE_COMMCHATHN_TCP.equals(mthd)){
				KeepAliveRespVO keepAliveRespVO = gson.fromJson(new String(data), KeepAliveRespVO.class);
				
				String respMsg = keepAliveRespVO.getRespMsg();
				Main.report("RespMsg : " + respMsg, true);
				
				Main.report(new String(data), true);
			}
			else if(MthdType.INITA_DEV_RETV.equals(mthd)){//331
				System.out.println(" data ----> "+new String(data));
				DevInfoRetvRespVO devInfoRetvRespVO = gson.fromJson(new String(data), DevInfoRetvRespVO.class);
				
				String respMsg = devInfoRetvRespVO.getRespMsg();
				Main.report("RespMsg : " + respMsg, true);
				Main.report(" Receive Msg : "+new String(data), true);
				
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
			else if(MthdType.INITA_DEV_UDATERPRT.equals(mthd)){//332
				ComnRespVO comnRespVO = gson.fromJson(new String(data), ComnRespVO.class);
				
				/** 메세지헤더 */
				MsgHeadVO msgHeadVO = comnRespVO.getMsgHeadVO();
				/** 응답코드 */
				String respCd = comnRespVO.getRespCd();
				/** 응답메시지 */
				String respMsg = comnRespVO.getRespMsg();
				
				Main.report("RespMsg : " +respMsg , true);
				Main.report(new String(data), true);
			}
			else if(MthdType.COLEC_ITGDATA_RECV.equals(mthd)){
				ComnRespVO comnRespVO = gson.fromJson(new String(data), ComnRespVO.class);
				
				/** 메세지헤더 */
				MsgHeadVO msgHeadVO = comnRespVO.getMsgHeadVO();
				/** 응답코드 */
				String respCd = comnRespVO.getRespCd();
				/** 응답메시지 */
				String respMsg = comnRespVO.getRespMsg();
				
				Main.report("RespMsg : " +respMsg , true);
				Main.report(new String(data), true);
			}
		}else{ // 서버 수신
			if(MthdType.INITA_DEV_RETV.equals(mthd)){//333 장치정보조회 임시code
				DevInfoRetvRespVO devInfoRetvRespVO = gson.fromJson(new String(data), DevInfoRetvRespVO.class);
				
				String respMsg = devInfoRetvRespVO.getRespMsg();
				Main.report("RespMsg : " + respMsg, true);
				
				Main.report(new String(data), true);
			}
			else if(MthdType.INITA_DEV_UDATERPRT.equals(mthd)){ //334 장치정보 갱신보고 임시code
				ComnRespVO comnRespVO = gson.fromJson(new String(data), ComnRespVO.class);
				
				/** 메세지헤더 */
				MsgHeadVO msgHeadVO = comnRespVO.getMsgHeadVO();
				/** 응답코드 */
				String respCd = comnRespVO.getRespCd();
				/** 응답메시지 */
				String respMsg = comnRespVO.getRespMsg();
				
				Main.report("RespMsg : " +respMsg , true);
				Main.report(new String(data), true);
			}else if(MthdType.INITA_DEV_UDATERPRT.equals(mthd)){//711 최종값 쿼리 임시(code없음)
				LastValQueryRqtVO lastValQueryRqtVO = gson.fromJson(new String(data), LastValQueryRqtVO.class);
				
				/** 메세지헤더 */
				MsgHeadVO msgHeadVO = lastValQueryRqtVO.getMsgHeadVO();
				
				Main.report("== Success==\n", true);
				Main.report(new String(data), true);
			}
		}
	}
	
	class ScheduledJob extends TimerTask {
		public void run() {
			Main.keepAlive();
		}
	}
}