package com.kt.iot.emul.client;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.io.IOUtils;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import com.kt.iot.emul.Main;
import com.kt.iot.emul.model.Header;
import com.kt.iot.emul.util.Util;

public class Client extends Thread {
	public String SERVERIP = "211.42.137.221";												
	public int SERVERPORT = 9077;
	public int version = 1;
	private Socket socket;
	
	public static final int MSG_HEADER_SIZE = 24;
	
	private boolean mRun = false;
	
	BufferedOutputStream out;
	BufferedInputStream in;
	
	public String deviceId = "00000000-0000-0000-0000-000000000000";
	public int status = 1;
	public int resolution = 1;
	public int detection = 1;
	public int sdcard = 1;
	
	public int detectionmode = 1;
	public int msensitivity = 1;
	public int vsensitivity = 1;
	public int reverted = 0;
	public int movingpns = 10;
	public int storagepns = 10;
	public int savemode = 1;
	public String firmVersion = "1.00000000";
	public String apName = "homecctv wifi-ssid  ";
	public String apPower = "wifipower  ";
	public String ucloudapikey;
	public String ucloudapisecret;
	public String ucloudtoken;
	public String ucloudsecret;
	public String ucloudpath;	
	
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
	
	public Client() {
		
	}
	
	/**
	 * Constructor of the class. OnMessagedReceived listens for the messages
	 * received from server
	 */
	public Client(String ip, int port) {
		this.SERVERIP = ip;
		this.SERVERPORT = port;
	}

	/**
	 * Sends the message entered by client to the server
	 * 
	 * @param message
	 *            text entered by client
	 */
	public void sendData(byte[] header, byte[] body, int version) throws IOException{
		if (out != null) {
			out.write(header);
			out.write(body);
			out.flush();
			
			Main.report("send : " + Util.byte2Hex(body), true);
		}
	}	
	
	public Socket getSocket() {
		return this.socket;
	}
	
	public void stopClient() {
		mRun = false;
	}
	
	public void closeClient() {
		try {
			jobScheduler.cancel();
			stopClient();
			if(out != null) out.close();
			if(in != null) in.close();
			if(socket != null) socket.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		mRun = true;

		try {			
			socket = new Socket(SERVERIP, SERVERPORT);
			
			Main.report("connected : " + SERVERIP + ":" + SERVERPORT, true);

			try {

				// send Authentication Data  to the server
				out = new BufferedOutputStream(socket.getOutputStream());
				in = new BufferedInputStream(socket.getInputStream());

				// in this while the client listens for the messages sent by the server
				while (mRun) {
					byte[] headerbuffer = new byte[MSG_HEADER_SIZE];
					
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					
					in.read(headerbuffer, 0, MSG_HEADER_SIZE);		
					baos.write(headerbuffer, 0, MSG_HEADER_SIZE);

					ByteArrayInputStream bais = new ByteArrayInputStream( baos.toByteArray() );  
					DataInputStream dais = new DataInputStream(bais);
				
					Header header = new Header(dais);
					
					byte[] databuffer = new byte[header.getDataSize()];

					ByteArrayOutputStream baosBody = new ByteArrayOutputStream();
					
					in.read(databuffer, 0, header.getDataSize());					
					baosBody.write(databuffer, 0, header.getDataSize());

					ByteArrayInputStream baisBody = new ByteArrayInputStream( baosBody.toByteArray() );  
					DataInputStream daisBody = new DataInputStream(baisBody);
					
					processRcvPacket(header, headerbuffer, databuffer);
				}


			} catch (SocketException e) {
				Main.btnInit();
				Main.report("disconnected : " + this.SERVERIP + ":" + this.SERVERPORT, true);
				e.printStackTrace();
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
	
	private void processRcvPacket(Header _header, byte[] headerbuffer, byte[] databuffer) {	
		Main.report("receive head : " + Util.byte2Hex(headerbuffer), true);
		Main.report("receive body : " + Util.byte2Hex(databuffer), true);
		
		int cmd = _header.getCommand();
		
		if((cmd & Header.CMD_RESPONSE) == Header.CMD_RESPONSE) { // 요청에 대한 응답 수신
			byte[] result = new byte[4];
			System.arraycopy(databuffer, 0, result, 0, 4);
			
			if(Util.byte2Int(result) == Header.RESULT_SUCCESS) {
				if(cmd == (Header.CMD_RESPONSE | Header.CMD_REG)) {
					if(this.version >= 1) { // 0.76
						byte[] deviceId = new byte[16];					
						System.arraycopy(databuffer, 4, deviceId, 0, 16);
						
						this.deviceId = Util.byte2UUID(deviceId);
						
						Main.report("result : " + Util.byte2Int(result), true);
						Main.report("deviceID : " + this.deviceId, true);
					} else {
						byte[] deviceId = new byte[16];					
						System.arraycopy(databuffer, 4, deviceId, 0, 16);
						
						this.deviceId = Util.byte2UUID(deviceId);
						
						byte[] ucloudPath = new byte[50];					
						System.arraycopy(databuffer, 20, ucloudPath, 0, 50);
						
						Main.report("result : " + Util.byte2Int(result), true);
						Main.report("deviceID : " + this.deviceId, true);						
						Main.report("ucloudPath : " + Util.byte2Str(ucloudPath), true);
					}
					
					
				} else if(cmd == (Header.CMD_RESPONSE | Header.CMD_KEEPALIVE)) {
					Main.report("result : " + Util.byte2Int(result), true);
					
				} else if(cmd == (Header.CMD_RESPONSE | Header.CMD_DETECT)) {
					Main.report("result : " + Util.byte2Int(result), true);
					
				} else if(cmd == (Header.CMD_RESPONSE | Header.CMD_UCLOUD_STORE)) {
					Main.report("result : " + Util.byte2Int(result), true);
					
				} else if(cmd == (Header.CMD_RESPONSE | Header.CMD_UCLOUD_TOKEN)) {
					if(this.version >= 2) { // 0.79
						byte[] ucloudAPIKey = new byte[36];
						System.arraycopy(databuffer, 4, ucloudAPIKey, 0, 36);
						
						byte[] ucloudAPISecret = new byte[36];
						System.arraycopy(databuffer, 40, ucloudAPISecret, 0, 36);
						
						byte[] ucloudToken = new byte[25];
						System.arraycopy(databuffer, 61, ucloudToken, 0, 25);
						
						byte[] ucloudSecret = new byte[45];
						System.arraycopy(databuffer, 86, ucloudSecret, 0, 45);
						
						byte[] ucloudPath = new byte[50];					
						System.arraycopy(databuffer, 131, ucloudPath, 0, 50);
						
						Main.report("result : " + Util.byte2Int(result), true);
						Main.report("ucloudAPIKey : " + Util.byte2Str(ucloudAPIKey), true);
						Main.report("ucloudAPISecret : " + Util.byte2Str(ucloudAPISecret), true);
						Main.report("ucloudToken : " + Util.byte2Str(ucloudToken), true);
						Main.report("ucloudSecret : " + Util.byte2Str(ucloudSecret), true);
						Main.report("ucloudPath : " + Util.byte2Str(ucloudPath), true);
					} else {
						byte[] ucloudToken = new byte[40];
						System.arraycopy(databuffer, 4, ucloudToken, 0, 40);
						
						byte[] ucloudPath = new byte[50];					
						System.arraycopy(databuffer, 44, ucloudPath, 0, 50);
						
						Main.report("result : " + Util.byte2Int(result), true);
						Main.report("ucloudToken : " + Util.byte2Str(ucloudToken), true);
						Main.report("ucloudPath : " + Util.byte2Str(ucloudPath), true);						
					}
					
				} else if(cmd == (Header.CMD_RESPONSE | Header.CMD_SDCARD)) {
					Main.report("result : " + Util.byte2Int(result), true);	
				}
			} else {
				Main.report("result : " + Util.byte2Int(result), true);
			}
			
			
		} else { // 서버의 요청 수신
			Main.report("command : " + Integer.toHexString(_header.getCommand()), true);
			Main.report("commandID : " + Integer.toHexString(_header.getCommandId()), true);
			
			byte[] _name = new byte[8];
			System.arraycopy(_header.getName().getBytes(), 0, _name, 0, _header.getName().getBytes().length);
					
			byte[] _version = new byte[4];
			System.arraycopy(Util.int2Byte(_header.getVersion()), 0, _version, 0, 4);
			
			byte[] _cmd = new byte[4];
			System.arraycopy(Util.int2Byte(_header.getCommand() | Header.CMD_RESPONSE), 0, _cmd, 0, 4);
			
			byte[] _cid = new byte[4];
			System.arraycopy(Util.int2Byte(_header.getCommandId()), 0, _cid, 0, 4);
			
			byte[] _size = new byte[4];
			System.arraycopy(Util.int2Byte(_header.getDataSize()), 0, _size, 0, 4);
			
			int bodySize = 0;
			byte[] _bodySize = new byte[4];
			byte[] body = null; 
			
			if(cmd == Header.CMD_REQ_CONFIG_SEARCH) {
				// 1. 응답 데이터 설정
				byte[] _result = new byte[4];
				System.arraycopy(Util.int2Byte(Header.RESULT_SUCCESS), 0, _result, 0, 4);
				
				byte[] _status = new byte[4];
				System.arraycopy(Util.int2Byte(status), 0, _result, 0, 4);
				
				byte[] _resolution = new byte[4];
				System.arraycopy(Util.int2Byte(resolution), 0, _resolution, 0, 4);
				
				byte[] _detection = new byte[4];
				System.arraycopy(Util.int2Byte(detection), 0, _detection, 0, 4);
				
				byte[] _detectionmode = new byte[4];
				System.arraycopy(Util.int2Byte(detectionmode), 0, _detectionmode, 0, 4);
				
				byte[] _sdcard = new byte[4];
				System.arraycopy(Util.int2Byte(sdcard), 0, _sdcard, 0, 4);
				
				byte[] _reverted = new byte[4];
				System.arraycopy(Util.int2Byte(reverted), 0, _reverted, 0, 4);
				
				byte[] _movingpns = new byte[4];
				System.arraycopy(Util.int2Byte(movingpns), 0, _movingpns, 0, 4);
				
				byte[] _storagepns = new byte[4];
				System.arraycopy(Util.int2Byte(storagepns), 0, _storagepns, 0, 4);
				
				byte[] _savemode = new byte[4];
				System.arraycopy(Util.int2Byte(savemode), 0, _savemode, 0, 4);
				
				byte[] _msensitivity = new byte[4];
				System.arraycopy(Util.int2Byte(msensitivity), 0, _msensitivity, 0, 4);
				
				byte[] _vsensitivity = new byte[4];
				System.arraycopy(Util.int2Byte(vsensitivity), 0, _vsensitivity, 0, 4);
				
				byte[] _scheduled = new byte[4];
				System.arraycopy(Util.int2Byte(scheduled), 0, _scheduled, 0, 4);
				
				byte[] _firmVersion = new byte[10];
				System.arraycopy(firmVersion.getBytes(), 0, _firmVersion, 0, 10);
				
				byte[] _apName = new byte[20];
				System.arraycopy(apName.getBytes(), 0, _apName, 0, 20);
				
				byte[] _apPower = new byte[10];
				System.arraycopy(apPower.getBytes(), 0, _apPower, 0, 10);
				
				// 2. 응답 바디 데이터 생성
				bodySize = 92;
				System.arraycopy(Util.int2Byte(bodySize), 0, _bodySize, 0, 4);
				
				body = new byte[bodySize];
				System.arraycopy(_result, 0, body, 0, 4);
				System.arraycopy(_status, 0, body, 4, 4);
				System.arraycopy(_resolution, 0, body, 8, 4);
				System.arraycopy(_detection, 0, body, 12, 4);
				System.arraycopy(_detectionmode, 0, body, 16, 4);
				System.arraycopy(_sdcard, 0, body, 20, 4);
				System.arraycopy(_reverted, 0, body, 24, 4);
				System.arraycopy(_movingpns, 0, body, 28, 4);
				System.arraycopy(_storagepns, 0, body, 32, 4);
				System.arraycopy(_savemode, 0, body, 36, 4);
				System.arraycopy(_msensitivity, 0, body, 40, 4);
				System.arraycopy(_vsensitivity, 0, body, 44, 4);
				System.arraycopy(_scheduled, 0, body, 48, 4);
				System.arraycopy(_firmVersion, 0, body, 52, 10);
				System.arraycopy(_apName, 0, body, 62, 20);
				System.arraycopy(_apPower, 0, body, 82, 10);
				
        		
			} else if(cmd == Header.CMD_REQ_CONFIG_SET) {
				if(this.version >= 2) { // 0.79
					// 1. 제어 서버의 요청 정보 파싱
					byte[] _status = new byte[4];
					System.arraycopy(databuffer, 16, _status, 0, 4);
					status = Util.byte2Int(_status);
					
					byte[] _resolution = new byte[4];
					System.arraycopy(databuffer, 20, _resolution, 0, 4);
					resolution = Util.byte2Int(_resolution);
					
					byte[] _detection = new byte[4];
					System.arraycopy(databuffer, 24, _detection, 0, 4);
					detection = Util.byte2Int(_detection);
					
					byte[] _detectionmode = new byte[4];
					System.arraycopy(databuffer, 28, _detectionmode, 0, 4);
					detectionmode = Util.byte2Int(_detectionmode);
					
					byte[] _msensitivity = new byte[4];
					System.arraycopy(databuffer, 32, _msensitivity, 0, 4);
					msensitivity = Util.byte2Int(_msensitivity);
					
					byte[] _vsensitivity = new byte[4];
					System.arraycopy(databuffer, 36, _vsensitivity, 0, 4);
					vsensitivity = Util.byte2Int(_vsensitivity);
					
					byte[] _reverted = new byte[4];
					System.arraycopy(databuffer, 40, _reverted, 0, 4);
					reverted = Util.byte2Int(_reverted);
					
					byte[] _movingpns = new byte[4];
					System.arraycopy(databuffer, 44, _movingpns, 0, 4);
					movingpns = Util.byte2Int(_movingpns);
					
					byte[] _storagepns = new byte[4];
					System.arraycopy(databuffer, 48, _storagepns, 0, 4);
					storagepns = Util.byte2Int(_storagepns);
					
					byte[] _savemode = new byte[4];
					System.arraycopy(databuffer, 52, _savemode, 0, 4);
					savemode = Util.byte2Int(_savemode);					
					
					byte[] _ucloudapikey = new byte[36];
					System.arraycopy(databuffer, 56, _ucloudapikey, 0, 36);
					ucloudapikey = Util.byte2Str(_ucloudapikey);
					
					byte[] _ucloudapisecret = new byte[36];
					System.arraycopy(databuffer, 92, _ucloudapisecret, 0, 36);
					ucloudapisecret = Util.byte2Str(_ucloudapisecret);
					
					byte[] _ucloudtoken = new byte[25];
					System.arraycopy(databuffer, 128, _ucloudtoken, 0, 25);
					ucloudtoken = Util.byte2Str(_ucloudtoken);
					
					byte[] _ucloudsecret = new byte[45];
					System.arraycopy(databuffer, 153, _ucloudsecret, 0, 45);
					ucloudsecret = Util.byte2Str(_ucloudsecret);
					
					byte[] _ucloudpath = new byte[50];
					System.arraycopy(databuffer, 198, _ucloudpath, 0, 50);
					ucloudpath = Util.byte2Str(_ucloudpath);
									
					// 2. 응답 데이터 설정
					byte[] _result = new byte[4];
					System.arraycopy(Util.int2Byte(Header.RESULT_SUCCESS), 0, _result, 0, 4);
									
					// 3. 응답 바디 데이터 생성
					bodySize = 236;
					System.arraycopy(Util.int2Byte(bodySize), 0, _bodySize, 0, 4);
					
					body = new byte[bodySize];
					System.arraycopy(_result, 0, body, 0, 4);
					System.arraycopy(_status, 0, body, 4, 4);
					System.arraycopy(_resolution, 0, body, 8, 4);
					System.arraycopy(_detection, 0, body, 12, 4);				
					System.arraycopy(_detectionmode, 0, body, 16, 4);
					System.arraycopy(_msensitivity, 0, body, 20, 4);
					System.arraycopy(_vsensitivity, 0, body, 24, 4);
					System.arraycopy(_reverted, 0, body, 28, 4);
					System.arraycopy(_movingpns, 0, body, 32, 4);
					System.arraycopy(_storagepns, 0, body, 36, 4);
					System.arraycopy(_savemode, 0, body, 40, 4);
					System.arraycopy(_ucloudapikey, 0, body, 44, 36);
					System.arraycopy(_ucloudapisecret, 0, body, 80, 36);
					System.arraycopy(_ucloudtoken, 0, body, 116, 25);
					System.arraycopy(_ucloudsecret, 0, body, 141, 45);
					System.arraycopy(_ucloudpath, 0, body, 186, 50);
					
				} else {
					// 1. 제어 서버의 요청 정보 파싱
					byte[] _status = new byte[4];
					System.arraycopy(databuffer, 16, _status, 0, 4);
					status = Util.byte2Int(_status);
					
					byte[] _resolution = new byte[4];
					System.arraycopy(databuffer, 20, _resolution, 0, 4);
					resolution = Util.byte2Int(_resolution);
					
					byte[] _detection = new byte[4];
					System.arraycopy(databuffer, 24, _detection, 0, 4);
					detection = Util.byte2Int(_detection);
					
					byte[] _detectionmode = new byte[4];
					System.arraycopy(databuffer, 28, _detectionmode, 0, 4);
					detectionmode = Util.byte2Int(_detectionmode);
					
					byte[] _msensitivity = new byte[4];
					System.arraycopy(databuffer, 32, _msensitivity, 0, 4);
					msensitivity = Util.byte2Int(_msensitivity);
					
					byte[] _vsensitivity = new byte[4];
					System.arraycopy(databuffer, 36, _vsensitivity, 0, 4);
					vsensitivity = Util.byte2Int(_vsensitivity);
					
					byte[] _reverted = new byte[4];
					System.arraycopy(databuffer, 40, _reverted, 0, 4);
					reverted = Util.byte2Int(_reverted);
					
					byte[] _movingpns = new byte[4];
					System.arraycopy(databuffer, 44, _movingpns, 0, 4);
					movingpns = Util.byte2Int(_movingpns);
					
					byte[] _storagepns = new byte[4];
					System.arraycopy(databuffer, 48, _storagepns, 0, 4);
					storagepns = Util.byte2Int(_storagepns);
					
					byte[] _savemode = new byte[4];
					System.arraycopy(databuffer, 52, _savemode, 0, 4);
					savemode = Util.byte2Int(_savemode);
					
					byte[] _ucloudtoken = new byte[40];
					System.arraycopy(databuffer, 56, _ucloudtoken, 0, 40);
					ucloudtoken = Util.byte2Str(_ucloudtoken);
					
					byte[] _ucloudpath = new byte[50];
					System.arraycopy(databuffer, 96, _ucloudpath, 0, 50);
					ucloudpath = Util.byte2Str(_ucloudpath);
									
					// 2. 응답 데이터 설정
					byte[] _result = new byte[4];
					System.arraycopy(Util.int2Byte(Header.RESULT_SUCCESS), 0, _result, 0, 4);
									
					// 3. 응답 바디 데이터 생성
					bodySize = 134;
					System.arraycopy(Util.int2Byte(bodySize), 0, _bodySize, 0, 4);
					
					body = new byte[bodySize];
					System.arraycopy(_result, 0, body, 0, 4);
					System.arraycopy(_status, 0, body, 4, 4);
					System.arraycopy(_resolution, 0, body, 8, 4);
					System.arraycopy(_detection, 0, body, 12, 4);				
					System.arraycopy(_detectionmode, 0, body, 16, 4);
					System.arraycopy(_msensitivity, 0, body, 20, 4);
					System.arraycopy(_vsensitivity, 0, body, 24, 4);
					System.arraycopy(_reverted, 0, body, 28, 4);
					System.arraycopy(_movingpns, 0, body, 32, 4);
					System.arraycopy(_storagepns, 0, body, 36, 4);
					System.arraycopy(_savemode, 0, body, 40, 4);
					System.arraycopy(_ucloudtoken, 0, body, 44, 40);
					System.arraycopy(_ucloudpath, 0, body, 84, 50);
				}				
        		
			} else if(cmd == Header.CMD_REQ_SCH_SEARCH) {
				// 1. 응답 데이터 설정
				byte[] _result = new byte[4];
				System.arraycopy(Util.int2Byte(Header.RESULT_SUCCESS), 0, _result, 0, 4);
				
				byte[] _scheduled = new byte[4];
				System.arraycopy(Util.int2Byte(scheduled), 0, _scheduled, 0, 4);
				
				byte[] _detectionmode = new byte[4];
				System.arraycopy(Util.int2Byte(detectionmode), 0, _detectionmode, 0, 4);
				
				byte[] _timeFrom = new byte[4];
				System.arraycopy(timeFrom.getBytes(), 0, _timeFrom, 0, 4);
				
				byte[] _timeTo = new byte[4];
				System.arraycopy(timeTo.getBytes(), 0, _timeTo, 0, 4);
				
				byte[] _day = new byte[4];
				System.arraycopy(Util.int2Byte(day), 0, _day, 0, 4);
				
				byte[] _timeRec = new byte[4];
				System.arraycopy(timeRec.getBytes(), 0, _timeRec, 0, 4);
				
				byte[] _modeRec = new byte[4];
				System.arraycopy(Util.int2Byte(modeRec), 0, _modeRec, 0, 4);
				
				byte[] _durationRec = new byte[4];
				System.arraycopy(Util.int2Byte(durationRec), 0, _durationRec, 0, 4);
				
				byte[] _dayRec = new byte[4];
				System.arraycopy(Util.int2Byte(dayRec), 0, _dayRec, 0, 4);
				
				// 2. 응답 바디 데이터 생성
				bodySize = 40;
				System.arraycopy(Util.int2Byte(bodySize), 0, _bodySize, 0, 4);
				
				body = new byte[bodySize];
				System.arraycopy(_result, 0, body, 0, 4);
				System.arraycopy(_scheduled, 0, body, 4, 4);
				System.arraycopy(_detectionmode, 0, body, 8, 4);
				System.arraycopy(_timeFrom, 0, body, 12, 4);				
				System.arraycopy(_timeTo, 0, body, 16, 4);
				System.arraycopy(_day, 0, body, 20, 4);
				System.arraycopy(_timeRec, 0, body, 24, 4);
				System.arraycopy(_modeRec, 0, body, 28, 4);
				System.arraycopy(_durationRec, 0, body, 32, 4);
				System.arraycopy(_dayRec, 0, body, 36, 4);
				
			} else if(cmd == Header.CMD_REQ_SCH_SET) {
				// 1. 제어 서버의 요청 정보 파싱
				byte[] _scheduled = new byte[4];
				System.arraycopy(databuffer, 16, _scheduled, 0, 4);
				scheduled = Util.byte2Int(_scheduled);
				
				byte[] _detectionmode = new byte[4];
				System.arraycopy(databuffer, 20, _detectionmode, 0, 4);
				detectionmode = Util.byte2Int(_detectionmode);
				
				byte[] _timeFrom = new byte[4];
				System.arraycopy(databuffer, 24, _timeFrom, 0, 4);
				timeFrom = Util.byte2Str(_timeFrom);
				
				byte[] _timeTo = new byte[4];
				System.arraycopy(databuffer, 28, _timeTo, 0, 4);
				timeTo = Util.byte2Str(_timeTo);
				
				byte[] _day = new byte[4];
				System.arraycopy(databuffer, 32, _day, 0, 4);
				day = Util.byte2Int(_day);
				
				byte[] _timeRec = new byte[4];
				System.arraycopy(databuffer, 36, _timeRec, 0, 4);
				timeRec = Util.byte2Str(_timeRec);
				
				byte[] _modeRec = new byte[4];
				System.arraycopy(databuffer, 40, _modeRec, 0, 4);
				modeRec = Util.byte2Int(_modeRec);
				
				byte[] _durationRec = new byte[4];
				System.arraycopy(databuffer, 44, _durationRec, 0, 4);
				durationRec = Util.byte2Int(_durationRec);
				
				byte[] _dayRec = new byte[4];
				System.arraycopy(databuffer, 48, _dayRec, 0, 4);
				dayRec = Util.byte2Int(_dayRec);
				
				// 2. 응답 데이터 설정
				byte[] _result = new byte[4];
				System.arraycopy(Util.int2Byte(Header.RESULT_SUCCESS), 0, _result, 0, 4);
				
				// 3. 응답 바디 데이터 생성		
				bodySize = 40;
				System.arraycopy(Util.int2Byte(bodySize), 0, _bodySize, 0, 4);
				
				body = new byte[bodySize];
				System.arraycopy(_result, 0, body, 0, 4);
				System.arraycopy(_scheduled, 0, body, 4, 4);
				System.arraycopy(_detectionmode, 0, body, 8, 4);
				System.arraycopy(_timeFrom, 0, body, 12, 4);				
				System.arraycopy(_timeTo, 0, body, 16, 4);
				System.arraycopy(_day, 0, body, 20, 4);
				System.arraycopy(_timeRec, 0, body, 24, 4);
				System.arraycopy(_modeRec, 0, body, 28, 4);
				System.arraycopy(_durationRec, 0, body, 32, 4);
				System.arraycopy(_dayRec, 0, body, 36, 4);
				
			} else if(cmd == Header.CMD_VOICE) {
				// 1. 제어 서버의 요청 정보 파싱
				byte[] _voice = new byte[Util.byte2Int(_size) - 16];
				System.arraycopy(databuffer, 16, _voice, 0, Util.byte2Int(_size) - 16);
				
				Main.report("voice count : " + (++voiceCnt), true);
				Main.report("voice size : " + _voice.length, true);
				
				// 2. 응답 데이터 설정
				byte[] _result = new byte[4];
				System.arraycopy(Util.int2Byte(Header.RESULT_SUCCESS), 0, _result, 0, 4);
				
				// 3. 응답 바디 데이터 생성
				bodySize = 4;
				System.arraycopy(Util.int2Byte(bodySize), 0, _bodySize, 0, 4);
				
				body = new byte[bodySize];
				System.arraycopy(_result, 0, body, 0, 4);
				
			} else if(cmd == Header.CMD_REQ_ACTIVE_SET) {
				// 1. 제어 서버의 요청 정보 파싱
				byte[] _status = new byte[4];
				System.arraycopy(databuffer, 16, _status, 0, 4);
				status = Util.byte2Int(_status);
				
				// 2. 응답 데이터 설정
				byte[] _result = new byte[4];
				System.arraycopy(Util.int2Byte(Header.RESULT_SUCCESS), 0, _result, 0, 4);
				
				// 3. 응답 바디 데이터 생성
				bodySize = 8;
				System.arraycopy(Util.int2Byte(bodySize), 0, _bodySize, 0, 4);
				
				body = new byte[bodySize];
				System.arraycopy(_result, 0, body, 0, 4);				
				System.arraycopy(_status, 0, body, 4, 4);
				
			} else if(cmd == Header.CMD_REQ_RESOL_SET) {
				// 1. 제어 서버의 요청 정보 파싱
				byte[] _resolution = new byte[4];
				System.arraycopy(databuffer, 16, _resolution, 0, 4);
				resolution = Util.byte2Int(_resolution);
				
				// 2. 응답 데이터 설정
				byte[] _result = new byte[4];
				System.arraycopy(Util.int2Byte(Header.RESULT_SUCCESS), 0, _result, 0, 4);
				
				// 3. 응답 바디 데이터 생성
				bodySize = 8;
				System.arraycopy(Util.int2Byte(bodySize), 0, _bodySize, 0, 4);
				
				body = new byte[bodySize];
				System.arraycopy(_result, 0, body, 0, 4);
				System.arraycopy(_resolution, 0, body, 4, 4);
				
			} else if(cmd == Header.CMD_REQ_DETECT_SET) {
				// 1. 제어 서버의 요청 정보 파싱
				byte[] _detection = new byte[4];
				System.arraycopy(databuffer, 16, _detection, 0, 4);
				detection = Util.byte2Int(_detection);
				
				byte[] _detectionmode = new byte[4];
				System.arraycopy(databuffer, 20, _detectionmode, 0, 4);
				detectionmode = Util.byte2Int(_detectionmode);
				
				// 2. 응답 데이터 설정
				byte[] _result = new byte[4];
				System.arraycopy(Util.int2Byte(Header.RESULT_SUCCESS), 0, _result, 0, 4);
				
				// 3. 응답 바디 데이터 생성
				bodySize = 12;
				System.arraycopy(Util.int2Byte(bodySize), 0, _bodySize, 0, 4);
				
				body = new byte[bodySize];
				System.arraycopy(_result, 0, body, 0, 4);
				System.arraycopy(_detection, 0, body, 4, 4);
				System.arraycopy(_detectionmode, 0, body, 8, 4);
				
			} else if(cmd == Header.CMD_REQ_SENSE_SET) {
				// 1. 제어 서버의 요청 정보 파싱
				byte[] _msensitivity = new byte[4];
				System.arraycopy(databuffer, 16, _msensitivity, 0, 4);
				msensitivity = Util.byte2Int(_msensitivity);
				
				byte[] _vsensitivity = new byte[4];
				System.arraycopy(databuffer, 20, _vsensitivity, 0, 4);
				vsensitivity = Util.byte2Int(_vsensitivity);
				
				// 2. 응답 데이터 설정
				byte[] _result = new byte[4];
				System.arraycopy(Util.int2Byte(Header.RESULT_SUCCESS), 0, _result, 0, 4);
				
				// 3. 응답 바디 데이터 생성
				bodySize = 12;
				System.arraycopy(Util.int2Byte(bodySize), 0, _bodySize, 0, 4);
				
				body = new byte[bodySize];
				System.arraycopy(_result, 0, body, 0, 4);				
				System.arraycopy(_msensitivity, 0, body, 4, 4);
				System.arraycopy(_vsensitivity, 0, body, 8, 4);
				
			} else if(cmd == Header.CMD_REQ_REVERT_SET) {
				// 1. 제어 서버의 요청 정보 파싱
				byte[] _reverted = new byte[4];
				System.arraycopy(databuffer, 16, _reverted, 0, 4);
				reverted = Util.byte2Int(_reverted);
				
				// 2. 응답 데이터 설정
				byte[] _result = new byte[4];
				System.arraycopy(Util.int2Byte(Header.RESULT_SUCCESS), 0, _result, 0, 4);
				
				// 3. 응답 바디 데이터 생성
				bodySize = 8;
				System.arraycopy(Util.int2Byte(bodySize), 0, _bodySize, 0, 4);
				
				body = new byte[bodySize];
				System.arraycopy(_result, 0, body, 0, 4);				
				System.arraycopy(_reverted, 0, body, 4, 4);
				
			} else if(cmd == Header.CMD_REQ_MOVEPNS_SET) {
				// 1. 제어 서버의 요청 정보 파싱
				byte[] _movingpns = new byte[4];
				System.arraycopy(databuffer, 16, _movingpns, 0, 4);
				movingpns = Util.byte2Int(_movingpns);
				
				// 2. 응답 데이터 설정
				byte[] _result = new byte[4];
				System.arraycopy(Util.int2Byte(Header.RESULT_SUCCESS), 0, _result, 0, 4);
				
				// 3. 응답 바디 데이터 생성
				bodySize = 8;
				System.arraycopy(Util.int2Byte(bodySize), 0, _bodySize, 0, 4);
				
				body = new byte[bodySize];
				System.arraycopy(_result, 0, body, 0, 4);				
				System.arraycopy(_movingpns, 0, body, 4, 4);
				
			} else if(cmd == Header.CMD_REQ_STORAGEPNS_SET) {
				// 1. 제어 서버의 요청 정보 파싱
				byte[] _storagepns = new byte[4];
				System.arraycopy(databuffer, 16, _storagepns, 0, 4);
				storagepns = Util.byte2Int(_storagepns);
				
				// 2. 응답 데이터 설정
				byte[] _result = new byte[4];
				System.arraycopy(Util.int2Byte(Header.RESULT_SUCCESS), 0, _result, 0, 4);
				
				// 3. 응답 바디 데이터 생성
				bodySize = 8;
				System.arraycopy(Util.int2Byte(bodySize), 0, _bodySize, 0, 4);
				
				body = new byte[bodySize];
				System.arraycopy(_result, 0, body, 0, 4);				
				System.arraycopy(_storagepns, 0, body, 4, 4);
				
			} else if(cmd == Header.CMD_REQ_SAVE_SET) {
				if(this.version >= 2) { // 0.79
					// 1. 제어 서버의 요청 정보 파싱
					byte[] _savemode = new byte[4];
					System.arraycopy(databuffer, 16, _savemode, 0, 4);
					savemode = Util.byte2Int(_savemode);					
					
					byte[] _ucloudapikey = new byte[36];
					System.arraycopy(databuffer, 20, _ucloudapikey, 0, 36);
					ucloudapikey = Util.byte2Str(_ucloudapikey);
					
					byte[] _ucloudapisecret = new byte[36];
					System.arraycopy(databuffer, 56, _ucloudapisecret, 0, 36);
					ucloudapisecret = Util.byte2Str(_ucloudapisecret);
					
					byte[] _ucloudtoken = new byte[25];
					System.arraycopy(databuffer, 92, _ucloudtoken, 0, 25);
					ucloudtoken = Util.byte2Str(_ucloudtoken);
					
					byte[] _ucloudsecret = new byte[45];
					System.arraycopy(databuffer, 117, _ucloudsecret, 0, 45);
					ucloudsecret = Util.byte2Str(_ucloudsecret);					
					
					byte[] _ucloudpath = new byte[50];
					System.arraycopy(databuffer, 162, _ucloudpath, 0, 50);
					ucloudpath = Util.byte2Str(_ucloudpath);
					
					// 2. 응답 데이터 설정
					byte[] _result = new byte[4];
					System.arraycopy(Util.int2Byte(Header.RESULT_SUCCESS), 0, _result, 0, 4);
					
					// 3. 응답 바디 데이터 생성
					bodySize = 8;
					System.arraycopy(Util.int2Byte(bodySize), 0, _bodySize, 0, 4);
					
					body = new byte[bodySize];
					System.arraycopy(_result, 0, body, 0, 4);				
					System.arraycopy(_savemode, 0, body, 4, 4);
				} else {
					// 1. 제어 서버의 요청 정보 파싱
					byte[] _savemode = new byte[4];
					System.arraycopy(databuffer, 16, _savemode, 0, 4);
					savemode = Util.byte2Int(_savemode);
					
					byte[] _ucloudtoken = new byte[40];
					System.arraycopy(databuffer, 20, _ucloudtoken, 0, 40);
					ucloudtoken = Util.byte2Str(_ucloudtoken);
					
					byte[] _ucloudpath = new byte[50];
					System.arraycopy(databuffer, 60, _ucloudpath, 0, 50);
					ucloudpath = Util.byte2Str(_ucloudpath);
					
					// 2. 응답 데이터 설정
					byte[] _result = new byte[4];
					System.arraycopy(Util.int2Byte(Header.RESULT_SUCCESS), 0, _result, 0, 4);
					
					// 3. 응답 바디 데이터 생성
					bodySize = 8;
					System.arraycopy(Util.int2Byte(bodySize), 0, _bodySize, 0, 4);
					
					body = new byte[bodySize];
					System.arraycopy(_result, 0, body, 0, 4);				
					System.arraycopy(_savemode, 0, body, 4, 4);
				}
				
			} else if(cmd == Header.CMD_REQ_DETECT_SCH_SEARCH) {
				// 1. 응답 데이터 설정
				byte[] _result = new byte[4];
				System.arraycopy(Util.int2Byte(Header.RESULT_SUCCESS), 0, _result, 0, 4);
				
				byte[] _scheduled = new byte[4];
				System.arraycopy(Util.int2Byte(scheduled), 0, _scheduled, 0, 4);
				
				byte[] _detectionmode = new byte[4];
				System.arraycopy(Util.int2Byte(detectionmode), 0, _detectionmode, 0, 4);
				
				byte[] _timeFrom = new byte[4];
				System.arraycopy(timeFrom.getBytes(), 0, _timeFrom, 0, 4);
				
				byte[] _timeTo = new byte[4];
				System.arraycopy(timeTo.getBytes(), 0, _timeTo, 0, 4);
				
				byte[] _day = new byte[4];
				System.arraycopy(Util.int2Byte(day), 0, _day, 0, 4);
				
				// 2. 응답 바디 데이터 생성
				bodySize = 24;
				System.arraycopy(Util.int2Byte(bodySize), 0, _bodySize, 0, 4);
				
				body = new byte[bodySize];
				System.arraycopy(_result, 0, body, 0, 4);
				System.arraycopy(_scheduled, 0, body, 4, 4);
				System.arraycopy(_detectionmode, 0, body, 8, 4);
				System.arraycopy(_timeFrom, 0, body, 12, 4);				
				System.arraycopy(_timeTo, 0, body, 16, 4);
				System.arraycopy(_day, 0, body, 20, 4);
				
			} else if(cmd == Header.CMD_REQ_DETECT_SCH_SET) {
				// 1. 제어 서버의 요청 정보 파싱
				byte[] _scheduled = new byte[4];
				System.arraycopy(databuffer, 16, _scheduled, 0, 4);
				int scheduled = Util.byte2Int(_scheduled);
				if(scheduled != 0x04 && scheduled != 0x05) {
					this.scheduled = scheduled; 
				}
								
				byte[] _detectionmode = new byte[4];
				System.arraycopy(databuffer, 20, _detectionmode, 0, 4);
				int detectionmode = Util.byte2Int(_detectionmode);
				if(detectionmode != 0x04) {
					this.detectionmode = detectionmode; 
				}
				
				byte[] _timeFrom = new byte[4];
				System.arraycopy(databuffer, 24, _timeFrom, 0, 4);
				String timeFrom = Util.byte2Str(_timeFrom);
				if(!"".equals(timeFrom.trim())) {
					this.timeFrom = timeFrom;
				}
				
				byte[] _timeTo = new byte[4];
				System.arraycopy(databuffer, 28, _timeTo, 0, 4);
				String timeTo = Util.byte2Str(_timeTo);
				if(!"".equals(timeFrom.trim())) {
					this.timeTo = timeTo;
				}
				
				byte[] _day = new byte[4];
				System.arraycopy(databuffer, 32, _day, 0, 4);
				int day = Util.byte2Int(_day);
				if(day != 0xFF) {
					this.day = day;
				}
				
				// 2. 응답 데이터 설정
				byte[] _result = new byte[4];
				System.arraycopy(Util.int2Byte(Header.RESULT_SUCCESS), 0, _result, 0, 4);
				
				// 3. 응답 바디 데이터 생성
				bodySize = 24;
				System.arraycopy(Util.int2Byte(bodySize), 0, _bodySize, 0, 4);
				
				body = new byte[bodySize];
				System.arraycopy(_result, 0, body, 0, 4);
				System.arraycopy(_scheduled, 0, body, 4, 4);
				System.arraycopy(_detectionmode, 0, body, 8, 4);
				System.arraycopy(_timeFrom, 0, body, 12, 4);
				System.arraycopy(_timeTo, 0, body, 16, 4);
				System.arraycopy(_day, 0, body, 20, 4);
				
			} else if(cmd == Header.CMD_REQ_RECORD_SCH_SEARCH) {
				// 1. 응답 데이터 설정
				byte[] _result = new byte[4];
				System.arraycopy(Util.int2Byte(Header.RESULT_SUCCESS), 0, _result, 0, 4);
				
				byte[] _scheduled = new byte[4];
				System.arraycopy(Util.int2Byte(scheduled), 0, _scheduled, 0, 4);
				
				byte[] _timeRec = new byte[4];
				System.arraycopy(timeRec.getBytes(), 0, _timeRec, 0, 4);
				
				byte[] _modeRec = new byte[4];
				System.arraycopy(Util.int2Byte(modeRec), 0, _modeRec, 0, 4);
				
				byte[] _durationRec = new byte[4];
				System.arraycopy(Util.int2Byte(durationRec), 0, _durationRec, 0, 4);
				
				byte[] _dayRec = new byte[4];
				System.arraycopy(Util.int2Byte(dayRec), 0, _dayRec, 0, 4);
				
				// 2. 응답 바디 데이터 생성
				bodySize = 24;
				System.arraycopy(Util.int2Byte(bodySize), 0, _bodySize, 0, 4);
				
				body = new byte[bodySize];
				System.arraycopy(_result, 0, body, 0, 4);
				System.arraycopy(_scheduled, 0, body, 4, 4);
				System.arraycopy(_timeRec, 0, body, 8, 4);
				System.arraycopy(_modeRec, 0, body, 12, 4);
				System.arraycopy(_durationRec, 0, body, 16, 4);
				System.arraycopy(_dayRec, 0, body, 20, 4);
				
			} else if(cmd == Header.CMD_REQ_RECORD_SCH_SET) {
				// 1. 제어 서버의 요청 정보 파싱
				byte[] _scheduled = new byte[4];
				System.arraycopy(databuffer, 16, _scheduled, 0, 4);
				scheduled = Util.byte2Int(_scheduled);
				
				byte[] _timeRec = new byte[4];
				System.arraycopy(databuffer, 20, _timeRec, 0, 4);
				timeRec = Util.byte2Str(_timeRec);
				
				byte[] _modeRec = new byte[4];
				System.arraycopy(databuffer, 24, _modeRec, 0, 4);
				modeRec = Util.byte2Int(_modeRec);
				
				byte[] _durationRec = new byte[4];
				System.arraycopy(databuffer, 28, _durationRec, 0, 4);
				durationRec = Util.byte2Int(_durationRec);
				
				byte[] _dayRec = new byte[4];
				System.arraycopy(databuffer, 32, _dayRec, 0, 4);
				dayRec = Util.byte2Int(_dayRec);
				
				// 2. 응답 데이터 설정
				byte[] _result = new byte[4];
				System.arraycopy(Util.int2Byte(Header.RESULT_SUCCESS), 0, _result, 0, 4);
				
				// 3. 응답 바디 데이터 생성
				bodySize = 24;
				System.arraycopy(Util.int2Byte(bodySize), 0, _bodySize, 0, 4);
				
				body = new byte[bodySize];
				System.arraycopy(_result, 0, body, 0, 4);
				System.arraycopy(_scheduled, 0, body, 4, 4);
				System.arraycopy(_timeRec, 0, body, 8, 4);
				System.arraycopy(_modeRec, 0, body, 12, 4);
				System.arraycopy(_durationRec, 0, body, 16, 4);
				System.arraycopy(_dayRec, 0, body, 20, 4);
				
			} else if(cmd == Header.CMD_REQ_MSENSE_SET) {
				// 1. 제어 서버의 요청 정보 파싱
				byte[] _msensitivity = new byte[4];
				System.arraycopy(databuffer, 16, _msensitivity, 0, 4);
				msensitivity = Util.byte2Int(_msensitivity);
				
				// 2. 응답 데이터 설정
				byte[] _result = new byte[4];
				System.arraycopy(Util.int2Byte(Header.RESULT_SUCCESS), 0, _result, 0, 4);
				
				// 3. 응답 바디 데이터 생성
				bodySize = 8;
				System.arraycopy(Util.int2Byte(bodySize), 0, _bodySize, 0, 4);
				
				body = new byte[bodySize];
				System.arraycopy(_result, 0, body, 0, 4);				
				System.arraycopy(_msensitivity, 0, body, 4, 4);
				
			} else if(cmd == Header.CMD_REQ_VSENSE_SET) {
				// 1. 제어 서버의 요청 정보 파싱
				byte[] _vsensitivity = new byte[4];
				System.arraycopy(databuffer, 16, _vsensitivity, 0, 4);
				vsensitivity = Util.byte2Int(_vsensitivity);
				
				// 2. 응답 데이터 설정
				byte[] _result = new byte[4];
				System.arraycopy(Util.int2Byte(Header.RESULT_SUCCESS), 0, _result, 0, 4);
				
				// 3. 응답 바디 데이터 생성
				bodySize = 8;
				System.arraycopy(Util.int2Byte(bodySize), 0, _bodySize, 0, 4);
				
				body = new byte[bodySize];
				System.arraycopy(_result, 0, body, 0, 4);				
				System.arraycopy(_vsensitivity, 0, body, 4, 4);
				
			} else {
				// 1. 응답 데이터 설정
				byte[] _result = new byte[4];
				System.arraycopy(Util.int2Byte(Header.RESULT_SUCCESS), 0, _result, 0, 4);
				
				// 2. 응답 바디 데이터 생성
				bodySize = 4;
				System.arraycopy(Util.int2Byte(bodySize), 0, _bodySize, 0, 4);
				
				body = new byte[bodySize];
				System.arraycopy(_result, 0, body, 0, 4);				
			}
			
			// 서버 요청에 대한 응답 발신
			byte[] header = new byte[24];
			System.arraycopy(_name, 0, header, 0, 8);
			System.arraycopy(_version, 0, header, 8, 4);
			System.arraycopy(_cmd, 0, header, 12, 4);
			System.arraycopy(_cid, 0, header, 16, 4);
			System.arraycopy(_bodySize, 0, header, 20, 4);
			
    		try {
				this.sendData(header, body, this.version);
			} catch (IOException e) {
				Main.report(e.toString(), true);
				e.printStackTrace();
			}
		}
	}
	
}