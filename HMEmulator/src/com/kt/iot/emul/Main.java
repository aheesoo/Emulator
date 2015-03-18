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

public class Main {
	private static Client client = new Client();
	
	private static Display display;
	
	private static Combo comboVersion;
	private Combo comboFun;
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
	private static Group groupFunction;
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
	
	public Main() {
        
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
		
		groupDevice = new Group(shell, SWT.NULL);
		groupDevice.setText("장치 정보");
		groupDevice.setLayout(new GridLayout(2, false));
		groupDevice.setLayoutData(new GridData(615, 120));
		
		Label deviceNameLabel = new Label(groupDevice, SWT.NULL);
		deviceNameLabel.setLayoutData(new GridData(85, 0));
		deviceNameLabel.setVisible(false);
		
		Label deviceInputLabel = new Label(groupDevice, SWT.NULL);
		deviceInputLabel.setLayoutData(new GridData(85, 0));
		deviceInputLabel.setVisible(false);
		
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
		
		new Label(groupDevice, SWT.NULL).setText("IoT End Device");
		comboDev = new Combo(groupDevice, SWT.BORDER);
		comboDev.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		comboDev.add("IoT GW", 0);
		comboDev.add("Door Lock", 1);
		comboDev.add("Open/Close sensor", 2);
		comboDev.add("Gas valve", 3);
		comboDev.select(0);
		comboDev.setVisible(false);
		
		groupFunction = new Group(shell, SWT.NULL);
		groupFunction.setText("");
		groupFunction.setLayout(new GridLayout(2, false));
		groupFunction.setLayoutData(new GridData(615, 50));
		
		Label funcNameLabel = new Label(groupFunction, SWT.NULL);
		funcNameLabel.setLayoutData(new GridData(85, 0));
		funcNameLabel.setVisible(false);
		
		Label funcInputLabel = new Label(groupFunction, SWT.NULL);
		funcInputLabel.setLayoutData(new GridData(85, 0));
		funcInputLabel.setVisible(false);
		
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
						
//						setDevice(0);
//						groupDevice.layout();
						buttonInit.setText("TCP 채널 인증");
//						initSetDevice();
					} else if("TCP 채널 인증".equals(buttonInit.getText())){
						initSendData();
						setFunction(0);
						comboDev.setVisible(true);
						groupFunction.layout();
						buttonInit.setText("Disconnect");
						buttonSend.setEnabled(true);
					} else {
						buttonInit.setText("Disconnect");
						buttonSend.setEnabled(false);
//						groupDevice.setVisible(false);
//						groupFunction.setVisible(false);
						client.closeClient();
					}
					
					break;
				}
			}
		});
		
		comboDev.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				if(comboDev.getSelectionIndex() == 0) {
					setFunction(0);
				}else if(comboDev.getSelectionIndex() == 1) {
					setFunction(1);
				}else if(comboDev.getSelectionIndex() == 2) {
					setFunction(2);
				}else if(comboDev.getSelectionIndex() == 3) {
					setFunction(3);
				}
				groupFunction.layout();
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				
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
//						methType = MthdType.KEEP_ALIVE_COMMCHATHN_TCP.getValue(); //TCP채널 KeepAlive
//						methcode = MthdType.KEEP_ALIVE_COMMCHATHN_TCP;
//						methType = MthdType.INITA_DEV_RETV.getValue();//장치정보조회 331
//	            		methcode = MthdType.INITA_DEV_RETV;
	            		
						if(comboDev.getSelectionIndex() == 0) {
							if(comboFun.getSelectionIndex() == 0){
								methType = MthdType.INITA_DEV_UDATERPRT.getValue();// 장치정보 갱신보고 332
	    	            		methcode = MthdType.INITA_DEV_UDATERPRT;
							}else if(comboFun.getSelectionIndex() == 1){
								methType = MthdType.INITA_DEV_UDATERPRT.getValue();// 장치정보 갱신보고 332
	    	            		methcode = MthdType.INITA_DEV_UDATERPRT;
							}
    	            	} else if(comboDev.getSelectionIndex() == 1) {
    	            		if(comboFun.getSelectionIndex() == 0){
								methType = MthdType.INITA_DEV_UDATERPRT.getValue();// 장치정보 갱신보고 332
	    	            		methcode = MthdType.INITA_DEV_UDATERPRT;
							}else if(comboFun.getSelectionIndex() == 1){
								methType = MthdType.INITA_DEV_UDATERPRT.getValue();// 장치정보 갱신보고 332
	    	            		methcode = MthdType.INITA_DEV_UDATERPRT;
							}else if(comboFun.getSelectionIndex() == 2){
								methType = MthdType.INITA_DEV_UDATERPRT.getValue();// 장치정보 갱신보고 332
	    	            		methcode = MthdType.INITA_DEV_UDATERPRT;
							}else if(comboFun.getSelectionIndex() == 3){
								methType = MthdType.INITA_DEV_UDATERPRT.getValue();// 장치정보 갱신보고 332
	    	            		methcode = MthdType.INITA_DEV_UDATERPRT;
							}else if(comboFun.getSelectionIndex() == 4){
								methType = MthdType.COLEC_ITGDATA_RECV.getValue();// 데이터 수집 411
	    	            		methcode = MthdType.COLEC_ITGDATA_RECV;
							}else if(comboFun.getSelectionIndex() == 5){
								methType = MthdType.COLEC_ITGDATA_RECV.getValue();// 데이터 수집 411
	    	            		methcode = MthdType.COLEC_ITGDATA_RECV;
							}else if(comboFun.getSelectionIndex() == 6){
								methType = MthdType.COLEC_ITGDATA_RECV.getValue();// 데이터 수집 411
	    	            		methcode = MthdType.COLEC_ITGDATA_RECV;
							}else if(comboFun.getSelectionIndex() == 7){
								methType = MthdType.COLEC_ITGDATA_RECV.getValue();// 데이터 수집 411
	    	            		methcode = MthdType.COLEC_ITGDATA_RECV;
							}else if(comboFun.getSelectionIndex() == 8){
								methType = MthdType.COLEC_ITGDATA_RECV.getValue();// 데이터 수집 411
	    	            		methcode = MthdType.COLEC_ITGDATA_RECV;
							}else if(comboFun.getSelectionIndex() == 9){
								methType = MthdType.INITA_DEV_UDATERPRT.getValue();// 장치정보 갱신보고 332
	    	            		methcode = MthdType.INITA_DEV_UDATERPRT;
							}else if(comboFun.getSelectionIndex() == 10){
								methType = MthdType.COLEC_ITGDATA_RECV.getValue();// 데이터 수집 411
	    	            		methcode = MthdType.COLEC_ITGDATA_RECV;
							}else if(comboFun.getSelectionIndex() == 11){
								methType = MthdType.COLEC_ITGDATA_RECV.getValue();// 데이터 수집 411
	    	            		methcode = MthdType.COLEC_ITGDATA_RECV;
							}else if(comboFun.getSelectionIndex() == 12){
								methType = MthdType.INITA_DEV_UDATERPRT.getValue();// 장치정보 갱신보고 332
	    	            		methcode = MthdType.INITA_DEV_UDATERPRT;
							}else if(comboFun.getSelectionIndex() == 13){
								methType = MthdType.COLEC_ITGDATA_RECV.getValue();// 데이터 수집 411
	    	            		methcode = MthdType.COLEC_ITGDATA_RECV;
							}
    	            	} else if(comboDev.getSelectionIndex() == 2) {
    	            		if(comboFun.getSelectionIndex() == 0){
								methType = MthdType.INITA_DEV_UDATERPRT.getValue();// 장치정보 갱신보고 332
	    	            		methcode = MthdType.INITA_DEV_UDATERPRT;
							}else if(comboFun.getSelectionIndex() == 1){
								methType = MthdType.INITA_DEV_UDATERPRT.getValue();// 장치정보 갱신보고 332
	    	            		methcode = MthdType.INITA_DEV_UDATERPRT;
							}else if(comboFun.getSelectionIndex() == 2){
								methType = MthdType.COLEC_ITGDATA_RECV.getValue();// 데이터 수집 411
	    	            		methcode = MthdType.COLEC_ITGDATA_RECV;
							}else if(comboFun.getSelectionIndex() == 3){
								methType = MthdType.COLEC_ITGDATA_RECV.getValue();// 데이터 수집 411
	    	            		methcode = MthdType.COLEC_ITGDATA_RECV;
							}else if(comboFun.getSelectionIndex() == 4){
								methType = MthdType.COLEC_ITGDATA_RECV.getValue();// 데이터 수집 411
	    	            		methcode = MthdType.COLEC_ITGDATA_RECV;
							}else if(comboFun.getSelectionIndex() == 5){
								methType = MthdType.INITA_DEV_UDATERPRT.getValue();// 장치정보 갱신보고 332
	    	            		methcode = MthdType.INITA_DEV_UDATERPRT;
							}
    	            	} else if(comboDev.getSelectionIndex() == 3) {
    	            		if(comboFun.getSelectionIndex() == 0){
								methType = MthdType.INITA_DEV_UDATERPRT.getValue();// 장치정보 갱신보고 332
	    	            		methcode = MthdType.INITA_DEV_UDATERPRT;
							}else if(comboFun.getSelectionIndex() == 1){
								methType = MthdType.INITA_DEV_UDATERPRT.getValue();// 장치정보 갱신보고 332
	    	            		methcode = MthdType.INITA_DEV_UDATERPRT;
							}else if(comboFun.getSelectionIndex() == 2){
								methType = MthdType.COLEC_ITGDATA_RECV.getValue();// 데이터 수집 411
	    	            		methcode = MthdType.COLEC_ITGDATA_RECV;
							}else if(comboFun.getSelectionIndex() == 3){
								methType = MthdType.COLEC_ITGDATA_RECV.getValue();// 데이터 수집 411
	    	            		methcode = MthdType.COLEC_ITGDATA_RECV;
							}else if(comboFun.getSelectionIndex() == 4){
								methType = MthdType.INITA_DEV_UDATERPRT.getValue();// 장치정보 갱신보고 332
	    	            		methcode = MthdType.INITA_DEV_UDATERPRT;
							}else if(comboFun.getSelectionIndex() == 5){
								methType = MthdType.INITA_DEV_UDATERPRT.getValue();// 장치정보 갱신보고 332
	    	            		methcode = MthdType.INITA_DEV_UDATERPRT;
							}else if(comboFun.getSelectionIndex() == 6){
								methType = MthdType.INITA_DEV_UDATERPRT.getValue();// 장치정보 갱신보고 332
	    	            		methcode = MthdType.INITA_DEV_UDATERPRT;
							}else if(comboFun.getSelectionIndex() == 7){
								methType = MthdType.COLEC_ITGDATA_RECV.getValue();// 데이터 수집 411
	    	            		methcode = MthdType.COLEC_ITGDATA_RECV;
							}
    	            	}
						
						byte[] header = packetUtil.getHeader(methcode, isRequest).toPacket();
//						byte[] header = getHeader(methcode).getBytes();

						String strBody = packetUtil.getBody(methType, comboDev.getSelectionIndex(),  comboFun.getSelectionIndex());
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
	
	private void setFunction(int isFunc){

		Control[] controls = groupFunction.getChildren();
		for(int i = 0; i < controls.length; i++) {
			if(controls[i].getVisible()) {
				controls[i].dispose();
			}
		}
		
		/*Control[] comboCon = comboFun.getChildren();
		for(int i = 0; i < comboCon.length; i++) {
			if(comboCon[i].getVisible()) {
				comboCon[i].dispose();
			}
		}*/
		
		if(groupFunction.getVisible() == false){
			groupFunction.setVisible(true);
		}
		
		groupFunction.setText("세부 기능");
		groupFunction.setLayout(new GridLayout(2, false));
		
		if(isFunc == 0){
			new Label(groupFunction, SWT.NULL).setText("Function");
			comboFun = new Combo(groupFunction, SWT.BORDER);
			comboFun.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			comboFun.add("repair 진행 후 연결상태 전달", 0);//332
			comboFun.add("공장초기화", 1);//332
			comboFun.select(0);
			comboFun.setVisible(true);
		}else if(isFunc == 1){
			new Label(groupFunction, SWT.NULL).setText("Function");
			comboFun = new Combo(groupFunction, SWT.BORDER);
			comboFun.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			comboFun.add("도어락 등록", 0);//332
			comboFun.add("도어락 삭제(추가)", 1);//332
			comboFun.add("사용자 등록 통보", 2);//332
			comboFun.add("사용자 삭제 통보", 3);//332
			comboFun.add("도어 출입 통보(unlock)", 4);//411
			comboFun.add("도어 출입 통보(lock)", 5);//411
			comboFun.add("도어락 상태 통보", 6);//411
			comboFun.add("도어락 비상통보 1", 7);//411
			comboFun.add("도어락 비상통보 2", 8);//411
			comboFun.add("도어락 초기화통보", 9);//332
			comboFun.add("도어락 PW 입력 오류 통보", 10);//411
			comboFun.add("도어락 장시간 문열림 통보", 11);//411
			comboFun.add("도어락 방범 모드 설정/해제 통보", 12);//332
			comboFun.add("Low battery 통보", 13);//411
			comboFun.select(0);
			comboFun.setVisible(true);
		}else if(isFunc == 2){
			new Label(groupFunction, SWT.NULL).setText("Function");
			comboFun = new Combo(groupFunction, SWT.BORDER);
			comboFun.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			comboFun.add("Open/Close sensor 등록", 0);//332
			comboFun.add("Open/Close sensor 삭제", 1);//332
			comboFun.add("Open/Close sensor 상태 통보", 2);//411
			comboFun.add("Open/Close sensor 감지 통보", 3);//411
			comboFun.add("Low battery 통보", 4);//411
			comboFun.add("Open/Close sensor 초기화 통보(추가", 5);//332
			comboFun.select(0);
			
			comboFun.setVisible(true);
		}else if(isFunc == 3){
			new Label(groupFunction, SWT.NULL).setText("Function");
			comboFun = new Combo(groupFunction, SWT.BORDER);
			comboFun.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			comboFun.add("Gas valve 등록", 0);//332
			comboFun.add("Gas valve 삭제", 1);//332
			comboFun.add("Gas valve 상태 통보", 2);//411
			comboFun.add("Gav valve 동작 통보", 3);//411
			comboFun.add("Gav valve 초기화 통보(추가)", 4);//332
			comboFun.add("Timeout 보고", 5);//332
			comboFun.add("Remainning 보고", 6);//332
			comboFun.add("과열(Overheat) 보고", 7);//411
			comboFun.select(0);
			
			comboFun.setVisible(true);
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
			String strBody = packetUtil.getBody(mthdType.getValue(), 100,100);
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
		
		Main.report = format.format(now) + " : " + s;
		Main.isAppend = isAppend;
		display.syncExec(new Runnable() {
			public void run() {
				if(Main.isAppend) {
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
//				groupDevice.setVisible(false);
				groupFunction.setVisible(false);
			}
		});
	}

	public static void main(String[] args) {		
		new Main();
	}
	
	public static void keepAlive(){
		display.syncExec(new Runnable() {
			public void run() {
				try {
					StdSysTcpCode.MthdType mthdType = MthdType.KEEP_ALIVE_COMMCHATHN_TCP;
					byte[] header = packetUtil.getHeader(mthdType, 0).toPacket();
//					byte[] header = getHeader(mthdType).getBytes();
					
					String strBody = packetUtil.getBody(mthdType.getValue(), 200,200);
					byte[] body = strBody.getBytes();
					
		    		client.sendData(header, body, mthdType.getValue());
					Main.report("init voiceCnt", true);
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
