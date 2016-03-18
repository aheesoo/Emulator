package com.kt.iot.emul;

import java.awt.color.CMMException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;
import java.util.Vector;

import org.apache.commons.io.IOUtils;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
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
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
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
import com.kt.iot.emul.vo.DevDtlVO;
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
	private static Combo comboDev;
	// private Combo comboReqRes;
	private Combo comboLev;
	private Combo comboLev2;

	private static Text textName;

	// private Combo textHost;
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
	private static Text textTag;

	private static Group initData;
	// private static Group groupDevice;
	private static Group groupFunction;
	private static Group groupSnsn;
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
	// public static String tag50000008 = "";
	public static String tag31000008 = "";
	public static String tag6202 = "";
	public static String tag8002 = "";
	public static String tag2502 = "";
	// public static String tag7005 = "";
	// public static String tag7006 = "";
	/**************************************************************************/
	
	//".\\Info.properties";  "C:\\Info.properties";    ".\\emulator_server.properties";  "C:\\emulator_server.properties"; getPath("Info.properties"); getPath("emulator_server.properties");
	public String infoDir = ".\\Info.properties";
	public String serverDir = ".\\emulator_server.properties";
//	public String infoDir = getPath("Info.properties");
//	public String serverDir = getPath("emulator_server.properties");

	String athnRqtNoHub = "";//F02641FD-C9A7-4F34-96F7-85C0DF65E551
	String athnRqtNoDev01 = "";//1001
	String athnRqtNoDev02 = "";
	String athnRqtNoDev03 = "";

	String deviceIdHub = "";// "B479A717108702";//"iohub";
	String deviceIdDev01 = "";
	String deviceIdDev02 = "";
	String deviceIdDev03 = "";
	
	public String getPath(String path){
		String dir = this.getClass().getResource(path).getPath();
		if (dir.startsWith("file:/")) dir = dir.substring(6, dir.length());
		else if (dir.startsWith("/")) dir = dir.substring(1, dir.length());
		
		String[] dirArr = dir.split("ECEmulator");
		return dirArr[0]+path;
	}

	public Main() {

		Properties properties = new Properties();
		try {
//			String dir = this.getClass().getResource("emulator_server.properties").getPath();
			
			properties.load(new FileInputStream(serverDir));
//			properties.load(new FileInputStream(serverDir));
			// / jar에서는 같은 프로젝트 디렉토리
			// FileInputStream(reDir+"deviceInfo.properties"));
			athnRqtNoHub = properties.getProperty("devHubAuthNum");
			athnRqtNoDev01 = properties.getProperty("devDoorAuthNum");
			athnRqtNoDev02 = properties.getProperty("devSensorAuthNum");
			athnRqtNoDev03 = properties.getProperty("devGasAuthNum");
					               
			deviceIdHub = properties.getProperty("devHubDevId");
			deviceIdDev01 = properties.getProperty("devDoorDevId");
			deviceIdDev02 = properties.getProperty("devSensorDevId");
			deviceIdDev03 = properties.getProperty("devGasDevId");
			
		} catch (IOException e) {

		}
		String ip = properties.getProperty("ip");
		String port = properties.getProperty("port");

		display = Display.getDefault();

		shell = new Shell(display);
		shell.setLayout(new GridLayout(1, true));
		shell.setSize(650, 900);
		shell.setText("EC Emulator - v0.01");

		// make tab
		TabFolder folder = new TabFolder(shell, SWT.NONE);
		TabItem tab1 = new TabItem(folder, SWT.NONE);
		tab1.setText("Send");
		TabItem tab2 = new TabItem(folder, SWT.NONE);
		tab2.setText("Tag");

		Group group = new Group(folder, SWT.NONE);
		group.setLayout(new GridLayout(1, true));
		group.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.VERTICAL_ALIGN_FILL));

		Group group2 = new Group(folder, SWT.NONE);
		group2.setLayout(new GridLayout(1, true));
		group2.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.VERTICAL_ALIGN_FILL));

		Group groupProxy = new Group(group, SWT.NULL);
		groupProxy.setText("연결 정보");
		groupProxy.setLayout(new GridLayout(2, false));
		// groupProxy.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		groupProxy.setLayoutData(new GridData(595, 100));

		Label proxyNameLabel = new Label(groupProxy, SWT.NULL);
		proxyNameLabel.setLayoutData(new GridData(85, 0));
		proxyNameLabel.setVisible(false);

		Label proxyInputLabel = new Label(groupProxy, SWT.NULL);
		proxyInputLabel.setLayoutData(new GridData(85, 0));
		proxyInputLabel.setVisible(false);

		new Label(groupProxy, SWT.NULL).setText("Host");
		textHost = new Text(groupProxy, SWT.BORDER);
		textHost.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		// textHost.setText("192.168.0.117");//192.168.0.63으로 하면 response는 내려와
		textHost.setText(ip);

		new Label(groupProxy, SWT.NULL).setText("Port");
		textPort = new Text(groupProxy, SWT.SINGLE | SWT.BORDER);
		textPort.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textPort.setText(port);// 9081, 9075, 9077

		new Label(groupProxy, SWT.NULL).setText("Version");
		comboVersion = new Combo(groupProxy, SWT.BORDER);
		comboVersion.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		comboVersion.add("0.01", 0);
		comboVersion.select(0);

		Group groupDevice = new Group(group, SWT.NONE);
		groupDevice.setText("장치 정보");
		groupDevice.setLayout(new GridLayout(2, false));
		groupDevice.setLayoutData(new GridData(595, 140));

		Label deviceNameLabel = new Label(groupDevice, SWT.NULL);
		deviceNameLabel.setLayoutData(new GridData(85, 0));
		deviceNameLabel.setVisible(false);

		Label deviceInputLabel = new Label(groupDevice, SWT.NULL);
		deviceInputLabel.setLayoutData(new GridData(85, 0));
		deviceInputLabel.setVisible(false);

		new Label(groupDevice, SWT.NULL).setText("authNum");
		authNum = new Text(groupDevice, SWT.SINGLE | SWT.BORDER);
		authNum.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		// authNum.setText("F02641FD-C9A7-4F34-96F7-85C0DF65E551");
		authNum.setText(athnRqtNoHub);

		new Label(groupDevice, SWT.NULL).setText("systemId");
		extrSysId = new Text(groupDevice, SWT.SINGLE | SWT.BORDER);
		extrSysId.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		extrSysId.setText("HOME_IOT");
		// extrSysId.setText("500101");

		new Label(groupDevice, SWT.NULL).setText("deviceId");
		deviceId = new Text(groupDevice, SWT.SINGLE | SWT.BORDER);
		deviceId.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		deviceId.setText(deviceIdHub);
		// deviceId.setText("B479A717108702");

		new Label(groupDevice, SWT.NULL).setText("IoT End Device");
		comboDev = new Combo(groupDevice, SWT.BORDER);
		comboDev.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		comboDev.add("IoT GW", 0);
		comboDev.add("Door Lock", 1);
		comboDev.add("Open/Close sensor", 2);
		comboDev.add("Gas valve", 3);
		comboDev.select(0);
		comboDev.setVisible(false);

		groupFunction = new Group(group, SWT.NULL);
		groupFunction.setText("");
		groupFunction.setLayout(new GridLayout(2, false));
		groupFunction.setLayoutData(new GridData(595, 40));

		Label funcNameLabel = new Label(groupFunction, SWT.NULL);
		funcNameLabel.setLayoutData(new GridData(85, 0));
		funcNameLabel.setVisible(false);

		Label funcInputLabel = new Label(groupFunction, SWT.NULL);
		funcInputLabel.setLayoutData(new GridData(85, 0));
		funcInputLabel.setVisible(false);

		groupSnsn = new Group(group, SWT.NULL);
		groupSnsn.setText("SnsnTag");
		groupSnsn.setLayout(new GridLayout(1, false));
		groupSnsn.setLayoutData(new GridData(595, 80));

		textSnsn = new Text(groupSnsn, SWT.BORDER | SWT.MULTI | SWT.WRAP
				| SWT.V_SCROLL);
		textSnsn.setLayoutData(new GridData(GridData.FILL_BOTH));

		Group groupReport = new Group(group, SWT.NULL);
		groupReport.setText("리포트");
		groupReport.setLayout(new GridLayout(1, false));
		// groupReport.setLayoutData(new GridData(GridData.FILL_BOTH));
		groupReport.setLayoutData(new GridData(595, 270));

		textRes = new Text(groupReport, SWT.BORDER | SWT.MULTI | SWT.WRAP
				| SWT.V_SCROLL);
		textRes.setLayoutData(new GridData(GridData.FILL_BOTH));

		buttonInit = new Button(group, SWT.PUSH);
		buttonInit.setLayoutData(new GridData(GridData.FILL_BOTH));
		buttonInit.setText("Connect");

		buttonSend = new Button(group, SWT.PUSH);
		buttonSend.setLayoutData(new GridData(GridData.FILL_BOTH));
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
					if ("Connect".equals(buttonInit.getText())) {
						client = new Client(textHost.getText(), Integer
								.parseInt(textPort.getText()));
						client.start();

						// setDevice(0);
						// groupDevice.layout();
						buttonInit.setText("TCP 채널 인증");
						// initSetDevice();
					} else if ("TCP 채널 인증".equals(buttonInit.getText())) {
						initSendData();
						setFunction(0);
						comboDev.setVisible(true);
						groupFunction.layout();
						buttonInit.setText("Disconnect");
						buttonSend.setEnabled(true);
					} else {
						buttonInit.setText("Disconnect");
						buttonSend.setEnabled(false);
						// groupDevice.setVisible(false);
						// groupFunction.setVisible(false);
						client.closeClient();
					}

					break;
				}
			}
		});
		comboDev.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				if (comboDev.getSelectionIndex() == 0) {
					authNum.setText(athnRqtNoHub);
					deviceId.setText(deviceIdHub);
					setFunction(0);
				} else if (comboDev.getSelectionIndex() == 1) {
					// deviceId.setText("iotdevice01");
					authNum.setText(athnRqtNoDev01);
					deviceId.setText(deviceIdDev01);
					setFunction(1);
				} else if (comboDev.getSelectionIndex() == 2) {
					authNum.setText(athnRqtNoDev02);
					deviceId.setText(deviceIdDev02);
					setFunction(2);
				} else if (comboDev.getSelectionIndex() == 3) {
					authNum.setText(athnRqtNoDev03);
					deviceId.setText(deviceIdDev03);
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

						if (comboDev.getSelectionIndex() == 0) {
							if (comboFun.getSelectionIndex() == 0
									|| comboFun.getSelectionIndex() == 1) {
								methType = MthdType.INITA_DEV_UDATERPRT
										.getValue();// 장치정보 갱신보고 332
								methcode = MthdType.INITA_DEV_UDATERPRT;
							} else if (comboFun.getSelectionIndex() == 2) {
								methType = MthdType.FRMWR_UDATE_STTUS
										.getValue();// 펌웨어 업데이트상태 전송 813
								methcode = MthdType.FRMWR_UDATE_STTUS;
							} else if (comboFun.getSelectionIndex() == 3) {
								methType = MthdType.LOG_ITG_LOG
										.getValue();// 로그파일 전송
								methcode = MthdType.LOG_ITG_LOG;
							}
						} else if (comboDev.getSelectionIndex() == 1) {
							if (comboFun.getSelectionIndex() == 0
									|| comboFun.getSelectionIndex() == 1
									|| comboFun.getSelectionIndex() == 9) {
								methType = MthdType.INITA_DEV_UDATERPRT
										.getValue();// 장치정보 갱신보고 332
								methcode = MthdType.INITA_DEV_UDATERPRT;
							} else if (comboFun.getSelectionIndex() == 2
									|| comboFun.getSelectionIndex() == 3
									|| comboFun.getSelectionIndex() == 4
									|| comboFun.getSelectionIndex() == 5
									|| comboFun.getSelectionIndex() == 6
									|| comboFun.getSelectionIndex() == 7
									|| comboFun.getSelectionIndex() == 8
									|| comboFun.getSelectionIndex() == 10) {
								methType = MthdType.COLEC_ITGDATA_RECV
										.getValue();// 데이터 수집 411
								methcode = MthdType.COLEC_ITGDATA_RECV;
							}
						} else if (comboDev.getSelectionIndex() == 2) {
							if (comboFun.getSelectionIndex() == 0
									|| comboFun.getSelectionIndex() == 1) {
								methType = MthdType.INITA_DEV_UDATERPRT
										.getValue();// 장치정보 갱신보고 332
								methcode = MthdType.INITA_DEV_UDATERPRT;
							} else if (comboFun.getSelectionIndex() == 2
									|| comboFun.getSelectionIndex() == 3
									|| comboFun.getSelectionIndex() == 4) {
								methType = MthdType.COLEC_ITGDATA_RECV
										.getValue();// 데이터 수집 411
								methcode = MthdType.COLEC_ITGDATA_RECV;
							}
						} else if (comboDev.getSelectionIndex() == 3) {
							if (comboFun.getSelectionIndex() == 0
									|| comboFun.getSelectionIndex() == 1) {
								methType = MthdType.INITA_DEV_UDATERPRT
										.getValue();// 장치정보 갱신보고 332
								methcode = MthdType.INITA_DEV_UDATERPRT;
							} else if (comboFun.getSelectionIndex() == 2
									|| comboFun.getSelectionIndex() == 3
									|| comboFun.getSelectionIndex() == 4 || comboFun.getSelectionIndex() == 5) {
								methType = MthdType.COLEC_ITGDATA_RECV
										.getValue();// 데이터 수집 411
								methcode = MthdType.COLEC_ITGDATA_RECV;
							}
						}

						byte[] header = packetUtil.getHeader(methcode,
								isRequest).toPacket();
						// byte[] header = getHeader(methcode).getBytes();

						String tagSeq = "";
						String tagVal = "";

						athnRqtNo = authNum.getText();
						devId = deviceId.getText();
						extrSystemId = extrSysId.getText();
						String[] keyValArr = new String[2];
						keyValArr = getPropKeyVals(comboFun.getText());
						tagSeq = keyValArr[0];
						tagVal = keyValArr[1];
						// String strBody = packetUtil.getBody(methType,
						// comboDev.getSelectionIndex(),
						// comboFun.getSelectionIndex(), tagSeq, tagVal);
						String strBody = packetUtil.getBody(methType,
								comboDev.getSelectionIndex(),
								comboFun.getSelectionIndex(), tagSeq, tagVal);
						byte[] body = strBody.getBytes();

						client.sendData(header, body, methType);

					} catch (Exception e) {
						report(e.toString(), true);
						e.printStackTrace();
					}

					break;
				}
			}
		});

		Group groupTag = new Group(group2, SWT.NULL);
		groupTag.setText("샌싱태그 key & value(hex)");
		groupTag.setLayout(new GridLayout(1, false));
		groupTag.setLayoutData(new GridData(595, 770));

		textTag = new Text(groupTag, SWT.BORDER | SWT.MULTI | SWT.WRAP
				| SWT.V_SCROLL);
		textTag.setLayoutData(new GridData(GridData.FILL_BOTH));
		textTag.setText(getPropKeyVals());

		Button buttonSave = new Button(groupTag, SWT.PUSH);
		buttonSave.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		buttonSave.setText("Save Tag");

		/*
		 * buttonSave.addSelectionListener(new SelectionAdapter() { public void
		 * widgetSelected(SelectionEvent event) { boolean b =
		 * MessageDialog.openConfirm(shell, "Confirm", "수정하시겠습니까?"); if(b){ try
		 * { saveTags(); } catch (Exception e) { // TODO: handle exception }
		 * finally { textTag.setText(getPropKeyVals()); } } }
		 */

		buttonSave.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				if (textTag != null) {
					if (textTag.getText() != null) {
						try {
							saveTags();
						} catch (Exception e) {
							// TODO: handle exception
						} finally {
							textTag.setText(getPropKeyVals());
						}
					}
				}
			}

			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});

		tab1.setControl(group);
		tab2.setControl(group2);

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

	private void setFunction(int isFunc) {

		Control[] controls = groupFunction.getChildren();
		for (int i = 0; i < controls.length; i++) {
			if (controls[i].getVisible()) {
				controls[i].dispose();
			}
		}

		if (groupFunction.getVisible() == false) {
			groupFunction.setVisible(true);
		}

		groupFunction.setText("세부 기능");
		groupFunction.setLayout(new GridLayout(2, false));

		if (isFunc == 0) {
			new Label(groupFunction, SWT.NULL).setText("Function");
			comboFun = new Combo(groupFunction, SWT.BORDER);
			comboFun.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			comboFun.add("repair 진행 후 연결상태 전달", 0);// 332
			comboFun.add("공장초기화", 1);// 332
			comboFun.add("펌웨어 업그레이드 결과 전송", 2);// 813
			comboFun.add("로그파일", 3);// 821
			comboFun.select(0);
			comboFun.setVisible(true);
		} else if (isFunc == 1) {
			new Label(groupFunction, SWT.NULL).setText("Function");
			comboFun = new Combo(groupFunction, SWT.BORDER);
			comboFun.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			comboFun.add("도어락 등록", 0);// 332
			comboFun.add("도어락 삭제(추가)", 1);// 332
			// comboFun.add("사용자 등록 통보", 2);//332
			// comboFun.add("사용자 삭제 통보", 3);//332
			comboFun.add("도어 출입 통보(unlock)", 2);// 411
			comboFun.add("도어 출입 통보(lock)", 3);// 411
			comboFun.add("도어락 상태 통보", 4);// 411
			comboFun.add("도어락 비상통보 1", 5);// 411
			comboFun.add("도어락 비상통보 2", 6);// 411
			// comboFun.add("도어락 초기화통보", 9);//332
			comboFun.add("도어락 PW 입력 오류 통보", 7);// 411
			comboFun.add("도어락 장시간 문열림 통보", 8);// 411
			comboFun.add("도어락 방범 모드 설정/해제 통보", 9);// 332
			comboFun.add("도어락 Low battery 통보", 10);// 411
			comboFun.select(0);
			comboFun.setVisible(true);
		} else if (isFunc == 2) {
			new Label(groupFunction, SWT.NULL).setText("Function");
			comboFun = new Combo(groupFunction, SWT.BORDER);
			comboFun.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			comboFun.add("Open/Close sensor 등록", 0);// 332
			comboFun.add("Open/Close sensor 삭제", 1);// 332
			comboFun.add("Open/Close sensor 상태 통보", 2);// 411
			comboFun.add("Open/Close sensor 감지 통보", 3);// 411
			comboFun.add("Open/Closesensor Low battery 통보", 4);// 411
			// comboFun.add("Open/Close sensor 초기화 통보(추가", 5);//332
			comboFun.select(0);

			comboFun.setVisible(true);
		} else if (isFunc == 3) {
			new Label(groupFunction, SWT.NULL).setText("Function");
			comboFun = new Combo(groupFunction, SWT.BORDER);
			comboFun.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			comboFun.add("Gas valve 등록", 0);// 332
			comboFun.add("Gas valve 삭제", 1);// 332
			comboFun.add("Gas valve 상태 통보", 2);// 411
			comboFun.add("Gas valve 동작 통보", 3);// 411
			// comboFun.add("Gav valve 초기화 통보(추가)", 4);//332
			// comboFun.add("Timeout 보고", 5);//332
			// comboFun.add("Remainning 보고", 6);//332
			comboFun.add("과열(Overheat) 보고", 4);// 411
			comboFun.add("Gas valve Low battery 통보", 5);// 411
			comboFun.select(0);

			comboFun.setVisible(true);

		}

	}

	public static void initSendData() {
		StdSysTcpCode.MthdType mthType = MthdType.ATHN_COMMCHATHN_DEV_TCP;

		athnRqtNo = authNum.getText();
		extrSystemId = extrSysId.getText();
		devId = deviceId.getText();

		try {
			byte[] header = packetUtil.getHeader(
					MthdType.ATHN_COMMCHATHN_DEV_TCP, 0).toPacket();

			StdSysTcpCode.MthdType mthdType = MthdType.ATHN_COMMCHATHN_DEV_TCP;
			String strBody = packetUtil.getBody(mthdType.getValue(), 100, 100,
					"", "");
			byte[] body = strBody.getBytes();

			System.out.println(" body : " + new String(body) + " \n header : "
					+ new String(header));
			client.sendData(header, body, mthdType.getValue());

		} catch (Exception e) {
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
				if (Main.isAppend) {
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
				comboDev.select(0);
				// groupDevice.setVisible(false);
				groupFunction.setVisible(false);
			}
		});
	}

	public static void main(String[] args) {
		new Main();
	}

	public static void keepAlive() {
		display.syncExec(new Runnable() {
			public void run() {
				try {
					StdSysTcpCode.MthdType mthdType = MthdType.KEEP_ALIVE_COMMCHATHN_TCP;
					byte[] header = packetUtil.getHeader(mthdType, 0)
							.toPacket();
					// byte[] header = getHeader(mthdType).getBytes();

					String strBody = packetUtil.getBody(mthdType.getValue(),
							200, 200, "", "");
					byte[] body = strBody.getBytes();
					System.out
							.println("=====================keep alive=====================");
					client.sendData(header, body, mthdType.getValue());
					Main.report("init voiceCnt", true);
					client.voiceCnt = 0;
				} catch (Exception e) {
					report(e.toString(), true);
					e.printStackTrace();
				}
			}
		});
	}

	public static String replace(String src, String oldstr, String newstr) {
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

	public static void setTagVal(String tag, byte[] val) {
		Main.report = tag + " : " + Util.byte2Hex(val);
		Main.isAppend = isAppend;
		display.syncExec(new Runnable() {
			public void run() {
				if (Main.isAppend) {
					textSnsn.append(new String(report) + "\n");
				} else {
					textSnsn.setText(report);
				}
			}
		});

	}

	public String getPropKeyVals() {
		Properties tagProperties = new Properties();

		String key = null;
		String value = null;
		StringBuffer result = new StringBuffer();
		InputStream is = null;
		StringBuilder responseData = new StringBuilder();
		try {
//			String dir = this.getClass().getResource("Info.properties").getPath();
			is = new FileInputStream(infoDir); 
			InputStreamReader inputStreamReader = new InputStreamReader(is,	"UTF-8");
			BufferedReader in = new BufferedReader(inputStreamReader);
			String line = null;

			while ((line = in.readLine()) != null) {
				responseData.append(line + "\n");
			}

			tagProperties.load(inputStreamReader);

			if (tagProperties.getProperty("IoT단말연결상태조회_value") != null) {
				tag31000008 = tagProperties.getProperty("IoT단말연결상태조회_value");
			}
			if (tagProperties.getProperty("도어락상태확인_value") != null) {
				String tags1 = tagProperties.getProperty("도어락상태확인_value");
				if (tags1.indexOf("/") > -1) {
					String[] values = tags1.split("/");
					tag6202 = values[0];
					tag8002 = values[1];
				} else {
					tag6202 = tags1;
				}
			}

			if (tagProperties.getProperty("Gasvalve상태확인_value") != null) {
				String tags2 = tagProperties.getProperty("Gasvalve상태확인_value");
				if (tags2.indexOf("/") > -1) {
					String[] values = tags2.split("/");
					tag2502 = values[0];
					tag8002 = values[1];
				} else {
					tag2502 = tags2;
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
			}
		}
		// return result.toString();
		return responseData.toString();
	}

	// / get tag name / value
	public String[] getPropKeyVals(String funNm) {
		Properties tagProperties = new Properties();

		InputStream is = null;
		String key = funNm.replaceAll(" ", "") + "_key";
		String value = funNm.replaceAll(" ", "") + "_value";
		String[] resultStr = new String[2];
		try {
//			String dir = this.getClass().getResource("Info.properties").getPath();
			is = new FileInputStream(infoDir);
			InputStreamReader inputStreamReader = new InputStreamReader(is,	"UTF-8");
			// BufferedReader in = new BufferedReader(inputStreamReader);

			tagProperties.load(inputStreamReader);

			String resultKey = "";
			String resultVal = "";

			if (tagProperties.getProperty(key) != null) {
				resultKey = tagProperties.getProperty(key);
			}
			if (tagProperties.getProperty(value) != null) {
				resultVal = tagProperties.getProperty(value);
			}
			resultStr[0] = resultKey;
			resultStr[1] = resultVal;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
			}
		}
		return resultStr;
	}

	public void saveTags() throws FileNotFoundException, IOException {
		Properties saveTags = new Properties();
		OutputStream output = null;
		try {

			File targetFile = new File(infoDir);
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile.getPath()), "UTF-8"));
			
			Map saveMap = new HashMap();
			saveMap = sortByKey(saveTagsMap());
			Iterator iterator = saveMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry entry = (Entry) iterator.next();
				String key = (String) entry.getKey();// (String)iterator.next();
				String val = (String) entry.getValue();// (String)saveMap.get(key);
				String keyVal = "";
				if(key.contains("#")){
					keyVal = key+"\n";
				}else{
					keyVal = key + " = " + val + "\n";
				}
				System.out.println("keyVal : " + keyVal);
				if ("IoT단말연결상태조회_value".equals(key)) {
					tag31000008 = val;
				}
				if ("도어락상태확인_value".equals(key)) {
					if (val.indexOf("/") > -1) {
						String[] values = val.split("/");
						tag6202 = values[0];
						tag8002 = values[1];
					} else {
						tag6202 = val;
					}
				}
				if ("Gasvalve상태확인_value".equals(key)) {
					if (val.indexOf("/") > -1) {
						String[] values = val.split("/");
						tag2502 = values[0];
						tag8002 = values[1];
					} else {
						tag2502 = val;
					}
				}
				out.write(keyVal);
			}

			out.close();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			/*
			 * try { // output.close(); } catch (IOException e) {
			 * 
			 * }
			 */
		}
	}

	public HashMap saveTagsMap() {
		String text = textTag.getText();
		String[] texts = text.split("\n");
		HashMap paramMap = new HashMap();
		for (int i = 0; i < texts.length; i++) {
			if (texts[i].length() > 1) {
				String str = texts[i].replaceAll(" ", "");
				if(str.contains("=")){
					String[] textArry = str.split("=");
					paramMap.put(textArry[0].trim(), textArry[1].trim());
				}else if(str.contains("#")){
					paramMap.put(str.trim(), "");
				}
			}
		}
		return paramMap;
	}

	public Map sortByKey(Map unsorted) {
		Map sorted = new TreeMap(sortByKeyComparator);
		sorted.putAll(unsorted);
		return sorted;
	}

	public Comparator sortByKeyComparator = new Comparator() {
		public int compare(Object o1, Object o2) {
			String str1 = (String) o1;
			String str2 = (String) o2;
			return str1.compareTo(str2);
		}
	};
	
	public static void logFileTest() {
		display.syncExec(new Runnable() {
			public void run() {
				try {
					StdSysTcpCode.MthdType mthdType = MthdType.LOG_ITG_LOG;
					byte[] header = packetUtil.getHeader(mthdType, 0).toPacket();

					String strBody = packetUtil.getBody(mthdType.getValue(), 200, 200, "", "");
					byte[] body = strBody.getBytes();
					client.sendData(header, body, mthdType.getValue());
				} catch (Exception e) {
					report(e.toString(), true);
					e.printStackTrace();
				}
			}
		});
	}
}
