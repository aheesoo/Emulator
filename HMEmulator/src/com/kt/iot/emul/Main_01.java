package com.kt.iot.emul;

import java.awt.color.CMMException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kt.iot.emul.client.Client;
import com.kt.iot.emul.model.Header;
import com.kt.iot.emul.util.ConvertUtil;
import com.kt.iot.emul.util.Util;
import com.kt.iot.emul.code.StdSysTcpCode;
import com.kt.iot.emul.code.StdSysTcpCode.HdrType;
import com.kt.iot.emul.code.StdSysTcpCode.MsgExchPtrn;
import com.kt.iot.emul.code.StdSysTcpCode.MsgType;
import com.kt.iot.emul.code.StdSysTcpCode.MthdType;
import com.kt.iot.emul.code.GwCode.UseYn;
import com.kt.iot.emul.code.StdSysTcpCode.EncdngType;
import com.kt.iot.emul.vo.CmdDataInfoVO;
import com.kt.iot.emul.func.vo.CommChAthnRespVO;
import com.kt.iot.emul.func.vo.ComnRespVO;
import com.kt.iot.emul.func.vo.ComnRqtVO;
import com.kt.iot.emul.func.vo.DevCommChAthnRqtVO;
import com.kt.iot.emul.func.vo.DevInfoRetvRespVO;
import com.kt.iot.emul.func.vo.ItgCnvyDataVO;
import com.kt.iot.emul.func.vo.ItgColecDataVO;
import com.kt.iot.emul.func.vo.ItgColecDataVO.ColecRowVO;
import com.kt.iot.emul.func.vo.ItgColecDataVO.DevColecDataVO;
import com.kt.iot.emul.func.vo.ItgColecDataVO.SysColecDataVO;
import com.kt.iot.emul.func.vo.LastValQueryRespVO;
import com.kt.iot.emul.func.vo.LastValQueryRqtVO;
import com.kt.iot.emul.vo.DevBasVO;
import com.kt.iot.emul.func.vo.DevInfoUdateRprtRqtVO;
import com.kt.iot.emul.vo.BinDataInfoVO;
import com.kt.iot.emul.vo.BinSetupDataInfoVO;
import com.kt.iot.emul.vo.DtDataInfoVO;
import com.kt.iot.emul.vo.EvDataInfoVO;
import com.kt.iot.emul.vo.GenlSetupDataInfoVO;
import com.kt.iot.emul.vo.LoDataInfoVO;
import com.kt.iot.emul.vo.MsgHeadVO;
import com.kt.iot.emul.vo.SclgDataInfoVO;
import com.kt.iot.emul.vo.SclgSetupDataInfoVO;
import com.kt.iot.emul.vo.SclgTimeDataInfoVO;
import com.kt.iot.emul.vo.SnsnDataInfoVO;
import com.kt.iot.emul.vo.StrDataInfoVO;
import com.kt.iot.emul.vo.SttusDataInfoVO;
import com.kt.iot.emul.vo.TcpHdrVO;
//import com.kt.iot.emul.func.vo.CommChAthnRqtVO;
import com.kt.iot.emul.func.vo.DevInfoRetvRqtVO;

public class Main_01 {
	private static Client client = new Client();
	
	private static Display display;
	
	private static Combo comboVersion;
	private Combo combo;
	private Combo comboDev;
	
	private static Text textName;
	
//	private Combo textHost;
	private Text textHost;
	private Text textPort; 
	private static Text authNum;
	private static Text extrSysId;
	private static Text deviceId;
	private static Text textRes;
	
	private static Group initData;
	private static Group groupDevice;
	private Group groupHeader; 
	private Group groupBody;
	
	private static Button buttonInit;
	private static Button buttonSend;
	
	private static String report;
	private static boolean isAppend;
	private static Shell shell;
	
	public static String athnNo;
	public static String extrSystemId;
	public static String devId;
	public static PacketUtil packetUtil;
	
	public Main_01() {
        
//        String s = System.getProperty("user.dir");
//        System.out.println("현재 디렉토리는 " + s + " 입니다");
        
        Properties properties = new Properties();
		try {
		      properties.load(new FileInputStream("C:\\emulator_server.properties"));
//			properties.load(new FileInputStream(s+"\\emulator_server.properties"));
		} catch (IOException e) {
		
		}
		String ip = properties.getProperty("ip");
		String port = properties.getProperty("port");
		
		display = Display.getDefault();
		
		shell = new Shell(display);
		shell.setLayout(new GridLayout(1, true));
		shell.setSize(650, 700);
		shell.setText("EC Emulator - v0.01");

		Group groupProxy = new Group(shell, SWT.NULL);
		groupProxy.setText("연결 정보");
		groupProxy.setLayout(new GridLayout(2, false));
		groupProxy.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label proxyNameLabel = new Label(groupProxy, SWT.NULL);
		proxyNameLabel.setLayoutData(new GridData(85, 0));
		proxyNameLabel.setVisible(false);
		
		Label proxyInputLabel = new Label(groupProxy, SWT.NULL);
		proxyInputLabel.setLayoutData(new GridData(85, 0));
		proxyInputLabel.setVisible(false);		
		
		new Label(groupProxy, SWT.NULL).setText("Host");
//		textHost = new Combo(groupProxy, SWT.BORDER);
		textHost = new Text(groupProxy, SWT.BORDER);
		textHost.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
//		textHost.setText("192.168.0.117");//192.168.0.63으로 하면 response는 내려와
		textHost.setText(ip);
//		textHost.add("127.0.0.1", 0);
//		textHost.select(0);
		
		new Label(groupProxy, SWT.NULL).setText("Port");
		textPort = new Text(groupProxy, SWT.SINGLE | SWT.BORDER);
		textPort.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textPort.setText(port);//9081, 9075, 9077
		
		new Label(groupProxy, SWT.NULL).setText("Version");
		comboVersion = new Combo(groupProxy, SWT.BORDER);
		comboVersion.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		comboVersion.add("0.73", 0);
		comboVersion.add("0.76", 1);
		comboVersion.add("0.79", 2);
		comboVersion.select(1);
		
		
		
//		initData = new Group(shell, SWT.NULL);
//		initData.setText("기본 정보");
//		initData.setLayout(new GridLayout(2, false));
//		initData.setLayoutData(new GridData(615, 100));
//		
//		Label initLabel = new Label(initData, SWT.NULL);
//		initLabel.setLayoutData(new GridData(85, 0));
//		initLabel.setVisible(false);
//		
//		Label initInputLabel = new Label(initData, SWT.NULL);
//		initInputLabel.setLayoutData(new GridData(85, 0));
//		initInputLabel.setVisible(false);
		
		/*new Label(initData, SWT.NULL).setText("authNum");
		authNum = new Text(initData, SWT.BORDER);
		authNum.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		authNum.setText("1001");
		
		new Label(initData, SWT.NULL).setText("systemId");
		extrSysId = new Text(initData, SWT.BORDER);
		extrSysId.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		extrSysId.setText("GiGA_Home_IoT");
		
		new Label(initData, SWT.NULL).setText("deviceId");
		deviceId = new Text(initData, SWT.BORDER);
		deviceId.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		deviceId.setText("HGD_00112233_KT_IOT_GATEWAY1");*/
		
		groupDevice = new Group(shell, SWT.NULL);
		groupDevice.setText("");
		groupDevice.setLayout(new GridLayout(2, false));
		groupDevice.setLayoutData(new GridData(615, 100));
		
		Label deviceNameLabel = new Label(groupDevice, SWT.NULL);
		deviceNameLabel.setLayoutData(new GridData(85, 0));
		deviceNameLabel.setVisible(false);
		
		Label deviceInputLabel = new Label(groupDevice, SWT.NULL);
		deviceInputLabel.setLayoutData(new GridData(85, 0));
		deviceInputLabel.setVisible(false);
		
		Group groupReport = new Group(shell, SWT.NULL);
		groupReport.setText("리포트");
		groupReport.setLayout(new GridLayout(1, false));
		groupReport.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		textRes = new Text(groupReport, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textRes.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		buttonInit = new Button(shell, SWT.PUSH);
		buttonInit.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		buttonInit.setText("Connect");
		
		buttonSend = new Button(shell, SWT.PUSH);
		buttonSend.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		buttonSend.setText("Send");
		buttonSend.setEnabled(false);
		
		shell.addListener(SWT.Close, new Listener() {
			public void handleEvent(Event event) {
				try {
					client.closeClient();
				} catch (Exception e) {
					// e.printStackTrace();
				}
				
				System.exit(-1);
			}
		});
		
		buttonInit.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				switch (event.type) {
				case SWT.Selection:
					if("Connect".equals(buttonInit.getText())) {
						client = new Client(textHost.getText(), Integer.parseInt(textPort.getText()));
						client.start();
						
						setDevice(0);
						groupDevice.layout();
						buttonInit.setText("TCP 채널 인증");
//						initSetDevice();
					} else if("TCP 채널 인증".equals(buttonInit.getText())){
						initSendData();
						setDevice(1);
						groupDevice.layout();
						buttonInit.setText("Disconnect");
						buttonSend.setEnabled(true);
					} else {
						buttonInit.setText("Disconnect");
						buttonSend.setEnabled(false);
						groupDevice.setVisible(false);
						
						client.closeClient();
					}
					
					break;
				}
			}
		});
		
		buttonSend.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				switch (event.type) {
				case SWT.Selection:
					try {
						short methType = 0;
						int isRequest = 0;
						StdSysTcpCode.MthdType methcode = null;
						
						if(comboDev.getSelectionIndex() == 0) {
							methType = MthdType.KEEP_ALIVE_COMMCHATHN_TCP.getValue(); //TCP채널 KeepAlive
							methcode = MthdType.KEEP_ALIVE_COMMCHATHN_TCP;
    	            	} else if(comboDev.getSelectionIndex() == 1) {
    	            		methType = MthdType.INITA_DEV_RETV.getValue();//장치정보조회
    	            		methcode = MthdType.INITA_DEV_RETV;
    	            	} else if(comboDev.getSelectionIndex() == 2) {
    	            		methType = MthdType.INITA_DEV_UDATERPRT.getValue();// 장치정보 갱신보고
    	            		methcode = MthdType.INITA_DEV_UDATERPRT;
    	            	} else if(comboDev.getSelectionIndex() == 3) {
    	            		methType = MthdType.COLEC_ITGDATA_RECV.getValue();// 데이터 수집
    	            		methcode = MthdType.COLEC_ITGDATA_RECV;
    	            	}
						
						byte[] header = packetUtil.getHeader(methcode, isRequest).toPacket();
//						byte[] header = getHeader(methcode).getBytes();

						String strBody = packetUtil.getBody(methType, 0, 0);
						byte[] body = strBody.getBytes();
						
                		client.sendData(header, body, methType);
	                		
					} catch(Exception e) {
						report(e.toString(), true);
	                	e.printStackTrace();
	                }
					
					break;
				}
			}
		});

		shell.open();
		shell.layout();
				
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				// If no more entries in event queue
				display.sleep();
			}
		}

		display.dispose();
	}
	
	private void setDevice(int isFunc){

		Control[] controls = groupDevice.getChildren();
		for(int i = 0; i < controls.length; i++) {
			if(controls[i].getVisible()) {
				controls[i].dispose();
			}
		}
		
		if(groupDevice.getVisible() == false){
			groupDevice.setVisible(true);
		}
		
		groupDevice.setText("세부 내용");
		groupDevice.setLayout(new GridLayout(2, false));
		
		if(isFunc == 0){
			new Label(groupDevice, SWT.NULL).setText("authNum");
			authNum = new Text(groupDevice, SWT.SINGLE | SWT.BORDER);
			authNum.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			authNum.setText("1001");
			
			new Label(groupDevice, SWT.NULL).setText("systemId");
			extrSysId = new Text(groupDevice, SWT.SINGLE | SWT.BORDER);
			extrSysId.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			extrSysId.setText("GiGA_Home_IoT");
			
			new Label(groupDevice, SWT.NULL).setText("deviceId");
			deviceId = new Text(groupDevice, SWT.SINGLE | SWT.BORDER);
			deviceId.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			deviceId.setText("HGD_00112233_KT_IOT_GATEWAY1");
			
		}else if(isFunc == 1){
			new Label(groupDevice, SWT.NULL).setText("Function");
			comboDev = new Combo(groupDevice, SWT.BORDER);
			comboDev.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			comboDev.add("KeepAlive", 0);
			comboDev.add("장치정보 조회", 1);
			comboDev.add("장치정보 갱신보고", 2);
			comboDev.add("데이터 수집");
			comboDev.select(0);
			
			comboDev.setVisible(true);
		}
		
		
	}
		
	public static void initSendData(){
		StdSysTcpCode.MthdType mthType = MthdType.ATHN_COMMCHATHN_DEV_TCP;
		
		athnNo = authNum.getText();
		extrSystemId = extrSysId.getText();
		devId = deviceId.getText();
		
		try {
			byte[] header = packetUtil.getHeader(MthdType.ATHN_COMMCHATHN_DEV_TCP, 0).toPacket();
			
			StdSysTcpCode.MthdType mthdType = MthdType.ATHN_COMMCHATHN_DEV_TCP;
			String strBody = packetUtil.getBody(mthdType.getValue(), 0, 0);
			byte[] body = strBody.getBytes();
			
			System.out.println(" body : "+ new String(body) +" \n header : "+ new String(header));
    		client.sendData(header, body, mthdType.getValue());

		} catch(Exception e) {
			report(e.toString(), true);
        	e.printStackTrace();
        }
	}
	
	public static void report(String s, boolean isAppend) {
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		
		Main_01.report = format.format(now) + " : " + s;
		Main_01.isAppend = isAppend;
		display.syncExec(new Runnable() {
			public void run() {
				if(Main_01.isAppend) {
					textRes.append(new String(report) + "\n");
				} else {
					textRes.setText(report);
				}
			}
		});
	}
	
	public static void btnInit() {
		display.syncExec(new Runnable() {
			public void run() {
				buttonInit.setText("Connect");
				buttonSend.setEnabled(false);
				groupDevice.setVisible(false);
			}
		});
	}

	public static void main(String[] args) {		
		new Main_01();
	}
	
	public static void keepAlive(){
		display.syncExec(new Runnable() {
			public void run() {
				try {
					StdSysTcpCode.MthdType mthdType = MthdType.KEEP_ALIVE_COMMCHATHN_TCP;
					byte[] header = packetUtil.getHeader(mthdType, 0).toPacket();
//					byte[] header = getHeader(mthdType).getBytes();
					
					String strBody = packetUtil.getBody(mthdType.getValue(), 0,0);
					byte[] body = strBody.getBytes();
					
		    		client.sendData(header, body, mthdType.getValue());
					Main_01.report("init voiceCnt", true);
					client.voiceCnt = 0;
				} catch(Exception e) {
					report(e.toString(), true);
		        	e.printStackTrace();
		        } 
			}
		});
	}
	
	public static String replace(String src, String oldstr, String newstr)
	{
		if (src == null)
		return null;
	
		StringBuffer dest = new StringBuffer("");
		int len = oldstr.length();
		int srclen = src.length();
		int pos = 0;
		int oldpos = 0;
	
		while ((pos = src.indexOf(oldstr, oldpos)) >= 0) {
		dest.append(src.substring(oldpos, pos));
		dest.append(newstr);
		oldpos = pos + len;
		}
	
		if (oldpos < srclen)
		dest.append(src.substring(oldpos, srclen));
	
		return dest.toString();
	}
}
