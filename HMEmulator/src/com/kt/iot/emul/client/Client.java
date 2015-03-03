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
import com.kt.iot.emul.func.vo.DevInfoRetvRqtVO;
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
	
	private ScheduledJob job = new ScheduledJob();
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
	public void sendData(byte[] header, byte[] body, short mthdCode) throws IOException{
		byte[] packet = JsonPacketMaker.getTcpPacket(header, body);
		try {
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
		System.arraycopy(dataBuffer, 2, headerSizeBuf, 0, 2);
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
		
		if(MsgType.RESPONSE.equals(msgType)){ // 요청(Request)에 대한 수신
			if(MthdType.ATHN_COMMCHATHN_EXTRSYS_TCP.equals(mthd)){
				CommChAthnRespVO commChAthnRespVO = gson.fromJson(new String(data), CommChAthnRespVO.class);
				
				String respMsg = commChAthnRespVO.getRespMsg();
				Main.report("RespMsg : " + respMsg, true);
				Main.report(new String(data), true);
				
				/** 통신채널 인증 토큰 */
				String commChAthnNo = commChAthnRespVO.getMsgHeadVO().getCommChAthnNo();
				Main.athnNo = commChAthnNo;
				
				/** 통신채널 인증 후 keepalive 주기적 요청 */
				jobScheduler.scheduleAtFixedRate(job, 30000, 30000);
				
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
			byte[] resHeader = null;
			byte[] resBody = null; 
			String strBody = null;
			if(MthdType.INITA_DEV_RETV.equals(mthd)){//333 장치정보조회 임시code
				Main.report(new String(data), true);

				//서버 요청에 대한 회신 데이터작성
				strBody = Main.getResBody(mthd.getValue(), data);
				resBody = strBody.getBytes();
			}
			else if(MthdType.INITA_DEV_UDATERPRT.equals(mthd)){ //334 장치정보 갱신보고 임시code
				Main.report(new String(data), true);
				strBody = Main.getResBody(mthd.getValue(), data);
				resBody = strBody.getBytes();
			}
			else if(MthdType.CONTL_ITGCNVY_DATA.equals(mthd)){ //525 데이터 전달 report -> VO없음
				Main.report(new String(data), true);
				strBody = Main.getResBody(mthd.getValue(), data);
				resBody = strBody.getBytes();
				
				/** report GW 송신 */
				//VO없음
				
			}
			else if(MthdType.INITA_DEV_UDATERPRT.equals(mthd)){//711 최종값 쿼리 임시(code없음)
				LastValQueryRqtVO lastValQueryRqtVO = gson.fromJson(new String(data), LastValQueryRqtVO.class);
				
				Main.report(new String(data), true);
				strBody = Main.getResBody(mthd.getValue(), data);
				resBody = strBody.getBytes();
			}
			
			/** 서버요청에 대한 응답발신 */
			resHeader = Main.getHeader(mthd, 1).toPacket();
			try {
				this.sendData(resHeader, resBody, mthd.getValue());
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	class ScheduledJob extends TimerTask {
		public void run() {
			Main.keepAlive();
		}
	}
}