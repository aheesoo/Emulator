package com.kt.iot.emul;

import java.awt.color.CMMException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
//import org.eclipse.swt.widgets.TabFolder;
//import org.eclipse.swt.widgets.TabItem;





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
	private Combo comboFun2;
	private Combo comboDev;
	private Combo comboReqRes;
	private Combo comboLev;
	private Combo comboLev2;
	
	private static Text textName;
	
//	private Combo textHost;
	private Text textHost;
	private Text textPort; 
	private static Text authNum;
	private static Text extrSysId;
	private static Text deviceId;
	private static Text textRes;
	private static Text textSnsn;
	private static Text textUserId;
	private static Text textUserPW;
	private static Text textTagSeq;
	private static Text textTagVal;
	
	private static Group initData;
	private static Group groupDevice;
	private static Group groupFunction;
	private static Group groupSnsn;
	private static Group groupSetting;
	private Group groupHeader; 
	private Group groupBody;
	
	private static Button buttonInit;
	private static Button buttonSend;
	private static Button buttonTagVal;
	
	private static String report;
	private static boolean isAppend;
	private static Shell shell;
	
	public static String athnNo;
	public static String athnRqtNo;
	public static String extrSystemId;
	public static String devId;
	public static int batteryLev = 0;
	public static int isDoorLock = 0;
	public static int isDoorEc1 = 0;
	public static int isDoorEc2 = 0;
	public static int isGasSt = 0;
	public static String userId = "";
	public static String userPW = "";
	public static PacketUtil packetUtil;
	
	/**************************************************************************/
//	public static String tag50000008 = "";
	public static String tag31000008 = "";
	public static String tag6202 = "";
	public static String tag8002 = "";
	public static String tag2502 = "";
//	public static String tag7005 = "";
//	public static String tag7006 = "";
	/**************************************************************************/
	
	String athnRqtNoHub = "";
	String athnRqtNoDev01 = "";
	String athnRqtNoDev02 = "";
	String athnRqtNoDev03 = "";
	                       
	String deviceIdHub = "";
	String deviceIdDev01 = "";
	String deviceIdDev02 = "";
	String deviceIdDev03 = "";
	
	public Main() {
        
        Properties properties = new Properties();
//        Properties devInfoProps = new Properties();
		try {
//		      properties.load(new FileInputStream("C:\\emulator_server.properties"));
			properties.load(new FileInputStream(".\\emulator_server.properties"));//이클립스에서는 루트 경로 / jar에서는 같은 프로젝트 디렉토리
//		      devInfoProps.load(new FileInputStream(reDir+"deviceInfo.properties"));
		} catch (IOException e) {
		
		}
		String ip = properties.getProperty("ip");
		String port = properties.getProperty("port");
		
		display = Display.getDefault();
		
		shell = new Shell(display);
		shell.setLayout(new GridLayout(1, true));
		shell.setSize(650, 900);
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
		comboVersion.add("0.01", 0);
//		comboVersion.add("0.76", 1);
//		comboVersion.add("0.79", 2);
		comboVersion.select(0);
		
		groupDevice = new Group(shell, SWT.NULL);
		groupDevice.setText("장치 정보");
		groupDevice.setLayout(new GridLayout(2, false));
		groupDevice.setLayoutData(new GridData(615, 140));
		
		Label deviceNameLabel = new Label(groupDevice, SWT.NULL);
		deviceNameLabel.setLayoutData(new GridData(85, 0));
		deviceNameLabel.setVisible(false);
		
		Label deviceInputLabel = new Label(groupDevice, SWT.NULL);
		deviceInputLabel.setLayoutData(new GridData(85, 0));
		deviceInputLabel.setVisible(false);
		
		new Label(groupDevice, SWT.NULL).setText("authNum");
		authNum = new Text(groupDevice, SWT.SINGLE | SWT.BORDER);
		authNum.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		authNum.setText("F02641FD-C9A7-4F34-96F7-85C0DF65E551");
		
		new Label(groupDevice, SWT.NULL).setText("systemId");
		extrSysId = new Text(groupDevice, SWT.SINGLE | SWT.BORDER);
		extrSysId.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		extrSysId.setText("HOME_IOT");
//		extrSysId.setText("500101");
		
		new Label(groupDevice, SWT.NULL).setText("deviceId");
		deviceId = new Text(groupDevice, SWT.SINGLE | SWT.BORDER);
		deviceId.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		deviceId.setText("iothub");
		
		new Label(groupDevice, SWT.NULL).setText("Request/Response");
		comboReqRes = new Combo(groupDevice, SWT.BORDER);
		comboReqRes.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		comboReqRes.add("Request", 0);
		comboReqRes.add("Response(333/711)", 1);
		comboReqRes.select(0);
		comboReqRes.setVisible(false);

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
		groupFunction.setLayoutData(new GridData(615, 40));
		
		Label funcNameLabel = new Label(groupFunction, SWT.NULL);
		funcNameLabel.setLayoutData(new GridData(85, 0));
		funcNameLabel.setVisible(false);
		
		Label funcInputLabel = new Label(groupFunction, SWT.NULL);
		funcInputLabel.setLayoutData(new GridData(85, 0));
		funcInputLabel.setVisible(false);
		
		groupSetting = new Group(shell, SWT.NULL);
		groupSetting.setText("");
		groupSetting.setLayout(new GridLayout(2, false));
		groupSetting.setLayoutData(new GridData(615, 100));
		
		Label settingNameLabel = new Label(groupSetting, SWT.NULL);
		settingNameLabel.setLayoutData(new GridData(85, 0));
		settingNameLabel.setVisible(false);
		
		Label settingInputLabel = new Label(groupSetting, SWT.NULL);
		settingInputLabel.setLayoutData(new GridData(85, 0));
		settingInputLabel.setVisible(false);
		
		groupSnsn = new Group(shell, SWT.NULL);
		groupSnsn.setText("SnsnTag");
		groupSnsn.setLayout(new GridLayout(1, false));
		groupSnsn.setLayoutData(new GridData(615, 80));
		
		textSnsn = new Text(groupSnsn, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textSnsn.setLayoutData(new GridData(GridData.FILL_BOTH));
		
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
						setTag(true, 0,0);
						comboDev.setVisible(true);
						comboReqRes.setVisible(true);
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
		
		comboReqRes.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				comboDev.select(0);
				if(comboReqRes.getSelectionIndex() == 0) {
					setFunction(0);
					setTag(true, 0,0);
					if(buttonSend.getEnabled()== false){
						buttonSend.setEnabled(true);
					}
				}else if(comboReqRes.getSelectionIndex() == 1) {
					setFunction2(0);
					setTag(false, 0,0);
				}
				groupFunction.layout();
			}
			
			public void widgetDefaultSelected(SelectionEvent event) {
				
			}

		});
		
		comboDev.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				if(comboDev.getSelectionIndex() == 0 && comboReqRes.getSelectionIndex() == 0) {
					deviceId.setText("iothub");
					authNum.setText("F02641FD-C9A7-4F34-96F7-85C0DF65E551");
					setFunction(0);
					setTag(true, 0, 0);
				}else if(comboDev.getSelectionIndex() == 1 && comboReqRes.getSelectionIndex() == 0) {
//					deviceId.setText("iotdevice01");
					authNum.setText("1001");
					deviceId.setText("C_B479A7171CAD");
					setFunction(1);
					setTag(true, 1, 0);
				}else if(comboDev.getSelectionIndex() == 2 && comboReqRes.getSelectionIndex() == 0) {
					deviceId.setText("iotdevice02");
					setFunction(2);
					setTag(true, 2, 0);
				}else if(comboDev.getSelectionIndex() == 3 && comboReqRes.getSelectionIndex() == 0) {
					deviceId.setText("iotdevice03");
					setFunction(3);
					setTag(true, 3, 0);
				}
				else if(comboDev.getSelectionIndex() == 0 && comboReqRes.getSelectionIndex() == 1) {
					deviceId.setText("iothub");
					setFunction2(0);
					setTag(false, 0, 0);
//					setTagSeqVal("50000008",tag50000008);
				}else if(comboDev.getSelectionIndex() == 1 && comboReqRes.getSelectionIndex() == 1) {
					deviceId.setText("C_B479A7171CAD");
					setFunction2(1);
					setTag(false, 1, 0);
//					setTagSeqVal("6202",tag6202);
				}else if(comboDev.getSelectionIndex() == 2 && comboReqRes.getSelectionIndex() == 1) {
					deviceId.setText("iotdevice02");
					setFunction2(2);
					setTag(false, 2, 0);
				}else if(comboDev.getSelectionIndex() == 3 && comboReqRes.getSelectionIndex() == 1) {
					deviceId.setText("iotdevice03");
					setFunction2(3);
					setTag(false, 3, 0);
//					setTagSeqVal("2502",tag2502);
				}
				
				groupFunction.layout();
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {}

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
							if(comboFun.getSelectionIndex() == 0 || comboFun.getSelectionIndex() == 1){
								methType = MthdType.INITA_DEV_UDATERPRT.getValue();// 장치정보 갱신보고 332
	    	            		methcode = MthdType.INITA_DEV_UDATERPRT;
							}else if(comboFun.getSelectionIndex() == 2){
								methType = MthdType.FRMWR_UDATE_STTUS.getValue();// 장치정보 갱신보고 332
	    	            		methcode = MthdType.FRMWR_UDATE_STTUS;
							}
    	            	} else if(comboDev.getSelectionIndex() == 1) {
    	            		if(comboFun.getSelectionIndex() == 0 || comboFun.getSelectionIndex() == 1){
								methType = MthdType.INITA_DEV_UDATERPRT.getValue();// 장치정보 갱신보고 332
	    	            		methcode = MthdType.INITA_DEV_UDATERPRT;
							}else if(comboFun.getSelectionIndex() == 2 || comboFun.getSelectionIndex() == 3){
								methType = MthdType.INITA_DEV_UDATERPRT.getValue();// 장치정보 갱신보고 332
	    	            		methcode = MthdType.INITA_DEV_UDATERPRT;
							}else if(comboFun.getSelectionIndex() == 4 || comboFun.getSelectionIndex() == 5 || comboFun.getSelectionIndex() == 6
									|| comboFun.getSelectionIndex() == 7 || comboFun.getSelectionIndex() == 8 || comboFun.getSelectionIndex() == 10
									|| comboFun.getSelectionIndex() == 11 || comboFun.getSelectionIndex() == 13){
								methType = MthdType.COLEC_ITGDATA_RECV.getValue();// 데이터 수집 411
	    	            		methcode = MthdType.COLEC_ITGDATA_RECV;
							}else if(comboFun.getSelectionIndex() == 9){
								methType = MthdType.INITA_DEV_UDATERPRT.getValue();// 장치정보 갱신보고 332
	    	            		methcode = MthdType.INITA_DEV_UDATERPRT;
							}else if(comboFun.getSelectionIndex() == 12){
								methType = MthdType.INITA_DEV_UDATERPRT.getValue();// 장치정보 갱신보고 332
	    	            		methcode = MthdType.INITA_DEV_UDATERPRT;
							}
    	            	} else if(comboDev.getSelectionIndex() == 2) {
    	            		if(comboFun.getSelectionIndex() == 0 || comboFun.getSelectionIndex() == 1 || comboFun.getSelectionIndex() == 5){
								methType = MthdType.INITA_DEV_UDATERPRT.getValue();// 장치정보 갱신보고 332
	    	            		methcode = MthdType.INITA_DEV_UDATERPRT;
							}else if(comboFun.getSelectionIndex() == 2 || comboFun.getSelectionIndex() == 3 || comboFun.getSelectionIndex() == 4){
								methType = MthdType.COLEC_ITGDATA_RECV.getValue();// 데이터 수집 411
	    	            		methcode = MthdType.COLEC_ITGDATA_RECV;
							}
    	            	} else if(comboDev.getSelectionIndex() == 3) {
    	            		if(comboFun.getSelectionIndex() == 0 || comboFun.getSelectionIndex() == 1 ||
    	            				comboFun.getSelectionIndex() == 4 || comboFun.getSelectionIndex() == 5 ||comboFun.getSelectionIndex() == 6){
								methType = MthdType.INITA_DEV_UDATERPRT.getValue();// 장치정보 갱신보고 332
	    	            		methcode = MthdType.INITA_DEV_UDATERPRT;
							}else if(comboFun.getSelectionIndex() == 2 || comboFun.getSelectionIndex() == 3 || comboFun.getSelectionIndex() == 7){
								methType = MthdType.COLEC_ITGDATA_RECV.getValue();// 데이터 수집 411
	    	            		methcode = MthdType.COLEC_ITGDATA_RECV;
							}
    	            	}
						
						byte[] header = packetUtil.getHeader(methcode, isRequest).toPacket();
//						byte[] header = getHeader(methcode).getBytes();

						String tagSeq = "";
						String tagVal = "";
						if(textTagSeq.getText() != null){
							tagSeq = textTagSeq.getText();
							tagVal = textTagVal.getText();
						}
						athnRqtNo = authNum.getText();
						devId = deviceId.getText();
						extrSystemId = extrSysId.getText();
						String strBody = packetUtil.getBody(methType, comboDev.getSelectionIndex(),  comboFun.getSelectionIndex(), tagSeq, tagVal);
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
		
		if(groupSetting.getVisible() == true){
			groupSetting.setVisible(false);
		}
		
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
			comboFun.add("펌웨어 업그레이드 결과 전송", 1);//813
			comboFun.select(0);
			comboFun.setVisible(true);
		}else if(isFunc == 1){
			new Label(groupFunction, SWT.NULL).setText("Function");
			comboFun = new Combo(groupFunction, SWT.BORDER);
			comboFun.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			comboFun.add("도어락 등록", 0);//332
			comboFun.add("도어락 삭제(추가)", 1);//332
//			comboFun.add("사용자 등록 통보", 2);//332
//			comboFun.add("사용자 삭제 통보", 3);//332
			comboFun.add("도어 출입 통보(unlock)", 2);//411
			comboFun.add("도어 출입 통보(lock)", 3);//411
			comboFun.add("도어락 상태 통보", 4);//411
			comboFun.add("도어락 비상통보 1", 5);//411
			comboFun.add("도어락 비상통보 2", 6);//411
//			comboFun.add("도어락 초기화통보", 9);//332
			comboFun.add("도어락 PW 입력 오류 통보", 7);//411
			comboFun.add("도어락 장시간 문열림 통보", 8);//411
			comboFun.add("도어락 방범 모드 설정/해제 통보", 9);//332
			comboFun.add("Low battery 통보", 10);//411
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
//			comboFun.add("Open/Close sensor 초기화 통보(추가", 5);//332
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
//			comboFun.add("Gav valve 초기화 통보(추가)", 4);//332
//			comboFun.add("Timeout 보고", 5);//332
//			comboFun.add("Remainning 보고", 6);//332
			comboFun.add("과열(Overheat) 보고", 4);//411
			comboFun.select(0);
			
			comboFun.setVisible(true);
			
		}
		
		comboFun.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				setTag(true, comboDev.getSelectionIndex(), comboFun.getSelectionIndex());
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				setTag(true, 0, 0);
			}

		});

	}
	
	private void setFunction2(int isFunc){
		if(buttonSend.getEnabled() == true){
			buttonSend.setEnabled(false);
		}
		
		Control[] controls = groupFunction.getChildren();
		for(int i = 0; i < controls.length; i++) {
			if(controls[i].getVisible()) {
				controls[i].dispose();
			}
		}
		
		if(groupSetting.getVisible() == true){
			groupSetting.setVisible(false);
		}
		
		if(groupFunction.getVisible() == false){
			groupFunction.setVisible(true);
		}
		
		groupFunction.setText("세부 기능");
		groupFunction.setLayout(new GridLayout(2, false));
		
		if(isFunc == 0){
			new Label(groupFunction, SWT.NULL).setText("Function");
			comboFun2 = new Combo(groupFunction, SWT.BORDER);
			comboFun2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
//			comboFun2.add("WiFI 상태 조회", 0);//711
			comboFun2.add("IoT 단말 연결상태 조회", 0);//333
			comboFun2.select(0);
			comboFun2.setVisible(true);
		}else if(isFunc == 1){
			new Label(groupFunction, SWT.NULL).setText("Function");
			comboFun2 = new Combo(groupFunction, SWT.BORDER);
			comboFun2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			comboFun2.add("도어상태 확인", 0);//711
//			comboFun2.add("도어상태 확인", 1);//711
			comboFun2.select(0);
			comboFun2.setVisible(true);
		}else if(isFunc == 2){
			new Label(groupFunction, SWT.NULL).setText("Function");
			comboFun2 = new Combo(groupFunction, SWT.BORDER);
			comboFun2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			
			comboFun2.setVisible(true);
		}else if(isFunc == 3){
			new Label(groupFunction, SWT.NULL).setText("Function");
			comboFun2 = new Combo(groupFunction, SWT.BORDER);
			comboFun2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			comboFun2.add("Gas valve 상태확인", 0);//711
//			comboFun2.add("Gas valve 상태확인", 1);//711
//			comboFun2.add("TimeOut 조회", 2);//333
//			comboFun2.add("Remainning 조회", 3);//333
			comboFun2.select(0);
			
			comboFun2.setVisible(true);
			
		}
		
		comboFun2.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				setTag(false, comboDev.getSelectionIndex(), comboFun2.getSelectionIndex());
			}
			public void widgetDefaultSelected(SelectionEvent event) {
				setTag(false, comboDev.getSelectionIndex(), comboFun2.getSelectionIndex());
			}

		});

	}
	
	private void setTag(boolean isReq, int comDevSt, int comFunSt){
		Control[] controls = groupSetting.getChildren();
		for(int i = 0; i < controls.length; i++) {
			if(controls[i].getVisible()) {
				controls[i].dispose();
			}
		}
		
		if(groupSetting.getVisible() == false){
			groupSetting.setVisible(true);
		}
		
		groupSetting.setText("세부 설정");
		groupSetting.setLayout(new GridLayout(2, false));
	
		String tagSeq = "";
		String tagVal = "";
	    if(isReq){
	    	tagSeq = "reqTagSeq"+Integer.toString(comDevSt)+Integer.toString(comFunSt);
	    	tagVal = "reqTagVal"+Integer.toString(comDevSt)+Integer.toString(comFunSt);
	    	
	    }else{
	    	tagSeq = "resTagSeq"+Integer.toString(comDevSt)+Integer.toString(comFunSt);
	    	tagVal = "resTagVal"+Integer.toString(comDevSt)+Integer.toString(comFunSt);
	    }
		
		new Label(groupSetting, SWT.NULL).setText("Tag Seq");
		textTagSeq = new Text(groupSetting, SWT.SINGLE | SWT.BORDER);
		textTagSeq.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		if(isReq){
			textTagSeq.setText(getPropValue(tagSeq));
		}else{
			if("resTagSeq20".equals(tagSeq)){
				textTagSeq.setText("");
			}else{
				textTagSeq.setText(getSavePropValue(tagSeq));
			}
		}
		
		new Label(groupSetting, SWT.NULL).setText("Tag Value(Hex)");
		textTagVal = new Text(groupSetting, SWT.SINGLE | SWT.BORDER);
		textTagVal.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		if(isReq){
			textTagVal.setText(getPropValue(tagVal));//save the tagSeq and tagVal in properties at send data
		}else{
			if("resTagSeq20".equals(tagSeq)){
				textTagVal.setText("");
			}else{
				textTagVal.setText(getSavePropValue(tagVal));
			}
		}
		
		
		if(!isReq){
			new Label(groupSetting, SWT.NULL).setText("");
			buttonTagVal = new Button(groupSetting, SWT.PUSH);
			buttonTagVal.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			buttonTagVal.setText("Save TagValue");
			buttonTagVal.setEnabled(true);
			
			buttonTagVal.addSelectionListener(new SelectionListener() {
				
				  public void widgetSelected(SelectionEvent event) {
					  if(textTagSeq != null){
							if(textTagSeq.getText() != null){
								try {
									if("31000008".equals(textTagSeq.getText())){
										tag31000008 = textTagVal.getText();
									}else if("6202/8002".equals(textTagSeq.getText())){
										if(!"".equals(textTagVal.getText()) && textTagVal.getText().indexOf("/") > -1 ){
											String[] values = textTagVal.getText().split("/");
											tag6202 = values[0];
											tag8002 = values[1];
										}else{
											tag6202 = textTagVal.getText();
										}
									}else if("2502/8002".equals(textTagSeq.getText())){
										if(!"".equals(textTagVal.getText()) && textTagVal.getText().indexOf("/") > -1 ){
											String[] values = textTagVal.getText().split("/");
											tag2502 = values[0];
											tag8002 = values[1];
										}else{
											tag2502 = textTagVal.getText();
										}
									}
									saveTag();
								} catch (Exception e) {
									// TODO: handle exception
								}
							}
						}  
				  }
				  public void widgetDefaultSelected(SelectionEvent event) {}
				});
		}
		
		groupSetting.layout();
	}
	
	public static void initSendData(){
		StdSysTcpCode.MthdType mthType = MthdType.ATHN_COMMCHATHN_DEV_TCP;
		
		athnRqtNo = authNum.getText();
		extrSystemId = extrSysId.getText();
		devId = deviceId.getText();
		
		try {
			byte[] header = packetUtil.getHeader(MthdType.ATHN_COMMCHATHN_DEV_TCP, 0).toPacket();
			
			StdSysTcpCode.MthdType mthdType = MthdType.ATHN_COMMCHATHN_DEV_TCP;
			String strBody = packetUtil.getBody(mthdType.getValue(), 100, 100, "", "");
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
					
					String strBody = packetUtil.getBody(mthdType.getValue(), 200,200,"","");
					byte[] body = strBody.getBytes();
					System.out.println("=====================keep alive=====================");
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
	
	public static void setTagVal(String tag, byte[] val){
		Main.report = tag+" : "+Util.byte2Hex(val);
		Main.isAppend = isAppend;
		display.syncExec(new Runnable() {
			public void run() {
				if(Main.isAppend) {
					textSnsn.append(new String(report) + "\n");
				} else {
					textSnsn.setText(report);
				}
			}
		});
		
	}

	
	public String getPropValue(String key) {
		Properties tagProperties = new Properties();

		String dir = this.getClass().getResource("").getPath();
		String reDir = dir.substring(1,dir.length()-20);
		reDir.replaceAll("/", "\\\\");
		String value = null;
		InputStream is = null;
		try {			
//			is = new FileInputStream(reDir+"tag.properties");
			is = new FileInputStream(".\\tag.properties");
			tagProperties.load(is);
			value = tagProperties.getProperty(key);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {is.close();} catch (IOException e) {}
		}
		return value;
	}
	
	public String getSavePropValue(String key) {
		Properties tagProperties = new Properties();

		String dir = this.getClass().getResource("").getPath();
		String reDir = dir.substring(1,dir.length()-20);
		reDir.replaceAll("/", "\\\\");
		String value = null;
		InputStream is = null;
		try {			
//			is = new FileInputStream(reDir+"saveTag.properties");
			is = new FileInputStream(".\\saveTag.properties");
			tagProperties.load(is);
			value = tagProperties.getProperty(key);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if("resTagVal00".equals(key)){
				tag31000008 = value;
			} else if("resTagVal10".equals(key)){
				if(value.indexOf("/") > -1){
					String[] values = value.split("/");
					tag6202 = values[0];
					tag8002 = values[1];
				}else{
					tag6202 = value;
				}
			} else if("resTagVal30".equals(key)){
				if(value.indexOf("/") > -1){
					String[] values = value.split("/");
					tag2502 = values[0];
					tag8002 = values[1];
				}else{
					tag2502 = value;
				}
			}
			try {is.close();} catch (IOException e) {}
		}
		return value;
	}
	
	public void saveTag() throws FileNotFoundException, IOException{
		Properties saveTagProperties = new Properties();
		OutputStream output = null;
		try {
			
			String dir = this.getClass().getResource("").getPath();
			String reDir = dir.substring(1,dir.length()-20);
			reDir.replaceAll("/", "\\\\");
//			output = new FileOutputStream(reDir+"saveTag.properties");
			output = new FileOutputStream("\\saveTag.properties");
//			input = new FileInputStream(reDir+"saveTag.properties");
//			saveTagProperties.load(output);
			saveTagProperties.putAll(saveMap());
			saveTagProperties.store(output, "#####################tag properties#####################");
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				output.close();
			} catch (IOException e) {
			}
		}
	}
	
	public Map saveMap(){
		String addStr = "";
		if(tag8002 != null && !"".equals(tag8002)){
			addStr = "/"+tag8002;
		}
		Map paramMap = new HashMap();
		paramMap.put("resTagSeq00", "31000008");
		paramMap.put("resTagVal00", tag31000008);
		paramMap.put("resTagSeq10", "6202/8002");
		paramMap.put("resTagVal10", tag6202+addStr);
		paramMap.put("resTagSeq30", "2502/8002");
		paramMap.put("resTagVal30", tag2502+addStr);
		
		return paramMap;
	}
}
