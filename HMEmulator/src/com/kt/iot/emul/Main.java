package com.kt.iot.emul;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

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

import com.kt.iot.emul.client.Client;
import com.kt.iot.emul.model.Header;
import com.kt.iot.emul.util.Util;


public class Main {
	private static Client client = new Client();
	
	private static Display display;
	
	private static Combo comboVersion;
	private Combo combo;
	private Combo comboDev;
	
	private static Text textName;
	
	private Combo textHost;
	private Text textPort; 
	private static Text textRes;
	
	private Group groupDevice;
	private Group groupHeader; 
	private Group groupBody;
	
	private static Button buttonInit;
	private static Button buttonSend;
	
	private static String report;
	private static boolean isAppend;
	
	public Main() {
		display = Display.getDefault();
		
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(1, true));
		shell.setSize(650, 700);
		shell.setText("EC Emulator - v0.52");

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
		textHost = new Combo(groupProxy, SWT.BORDER);
		textHost.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textHost.add("211.42.137.221", 0);
		textHost.add("121.156.46.132", 1);
		textHost.select(0);
		
		new Label(groupProxy, SWT.NULL).setText("Port");
		textPort = new Text(groupProxy, SWT.SINGLE | SWT.BORDER);
		textPort.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textPort.setText("9077");
		
		new Label(groupProxy, SWT.NULL).setText("Version");
		comboVersion = new Combo(groupProxy, SWT.BORDER);
		comboVersion.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		comboVersion.add("0.73", 0);
		comboVersion.add("0.76", 1);
		comboVersion.add("0.79", 2);
		comboVersion.select(1);
		
		groupDevice = new Group(shell, SWT.NULL);
		groupDevice.setText("IoT End Device");
		groupDevice.setLayout(new GridLayout(2, false));
		groupDevice.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Label deviceNameLabel = new Label(groupDevice, SWT.NULL);
		deviceNameLabel.setLayoutData(new GridData(85, 0));
		deviceNameLabel.setVisible(false);
		
		Label deviceInputLabel = new Label(groupDevice, SWT.NULL);
		deviceInputLabel.setLayoutData(new GridData(85, 0));
		deviceInputLabel.setVisible(false);
		
		new Label(groupDevice, SWT.NULL).setText("IoT Device");
		comboDev = new Combo(groupDevice, SWT.BORDER);
		comboDev.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		comboDev.add("Door Lock", 0);
		comboDev.add("Open/Close Sensor", 1);
		comboDev.add("Gas Valve", 2);
		comboDev.select(0);
		
		groupHeader = new Group(shell, SWT.NULL);
		groupHeader.setText("헤더 정보");
		groupHeader.setLayout(new GridLayout(2, false));
		groupHeader.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Label headerNameLabel = new Label(groupHeader, SWT.NULL);
		headerNameLabel.setLayoutData(new GridData(85, 0));
		headerNameLabel.setVisible(false);
		
		Label headerInputLabel = new Label(groupHeader, SWT.NULL);
		headerInputLabel.setLayoutData(new GridData(85, 0));
		headerInputLabel.setVisible(false);

		new Label(groupHeader, SWT.NULL).setText("상세기능\n(Command)");
		combo = new Combo(groupHeader, SWT.BORDER);
		combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		combo.add("도어락 등록", 0);
		combo.add("도어락 삭제", 1);
		combo.add("사용자 등록 통보", 2);
		combo.add("사용자 삭제 통보", 3);
		combo.add("도어 출입 통보(unlock)", 4);
		combo.add("도어 출입 통보(lock) ", 5);
		combo.add("도어락 상태 통보 ", 6);
		combo.add("도어락 비상 통보 Ⅰ", 7);
		combo.add("도어락 비상 통보 Ⅱ", 8);
		combo.add("도어락 초기화  통보", 9);
		combo.add("도어락 PW입력 오류 통보", 10);
		combo.add("도어락 장시간 문열림 통보", 11);
		combo.add("도어락 방범모드 설정/해제 통보", 12);
		combo.add("도어락 삭제", 13);
		combo.select(2);

		new Label(groupHeader, SWT.NULL).setText("트랜잭션아이디");
		textName = new Text(groupHeader, SWT.SINGLE | SWT.BORDER);
		textName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textName.setText("20140505195220_EXAMPLE_LOWSYSTEM");
		textName.setData("size", 8);
		
		
		groupBody = new Group(shell, SWT.NULL);
		groupBody.setText("바디 정보");
		groupBody.setLayout(new GridLayout(2, false));
		groupBody.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Label bodyNameLabel = new Label(groupBody, SWT.NULL);
		bodyNameLabel.setLayoutData(new GridData(85, 0));
		bodyNameLabel.setVisible(false);
		
		Label bodyInputLabel = new Label(groupBody, SWT.NULL);
		bodyInputLabel.setLayoutData(new GridData(85, 0));
		bodyInputLabel.setVisible(false);
		
		new Label(groupBody, SWT.NULL).setText("External System ID");
		Text textModel = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
		textModel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textModel.setText("EXAMPLE_LOWSYSTEM");
		textModel.setData("size", 20);
		textModel.setData("type", "char");
		
		new Label(groupBody, SWT.NULL).setText("Device ID");
		Text textDeviceID = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
		textDeviceID.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textDeviceID.setText("D901CCTV01");
		textDeviceID.setData("size", 40);
		textDeviceID.setData("type", "char");
		
		new Label(groupBody, SWT.NULL).setText("Device Type");
		Text textDeviceType = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
		textDeviceType.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textDeviceType.setText("현관 출입문");
		textDeviceType.setData("size", 40);
		textDeviceType.setData("type", "char");
		
		new Label(groupBody, SWT.NULL).setText("Model Name\n(product type ID)");
		Text textMAC = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
		textMAC.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textMAC.setText("SNB-6004");
		textMAC.setData("size", 30);
		textMAC.setData("type", "char");
		
		new Label(groupBody, SWT.NULL).setText("User ID");
		Text textSAID = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
		textSAID.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textSAID.setText("Test Everything");
		textSAID.setData("size", 11);
		textSAID.setData("type", "char");
		
		new Label(groupBody, SWT.NULL).setText("User PW");
		Text textSecret = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
		textSecret.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textSecret.setText("Test Everything");
		textSecret.setData("size", 40);
		textSecret.setData("type", "char");
		
		Group groupReport = new Group(shell, SWT.NULL);
		groupReport.setText("리포트");
		groupReport.setLayout(new GridLayout(1, false));
		groupReport.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Label reportInputLabel = new Label(groupReport, SWT.NULL);
		reportInputLabel.setLayoutData(new GridData(85, 0));
		reportInputLabel.setVisible(false);
		
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
		
		comboDev.addSelectionListener(new SelectionListener(){
			public void widgetSelected(SelectionEvent e){
				setFunction();
				groupHeader.layout();
			}
			
			public void widgetDefaultSelected(SelectionEvent e){
				
			}
		});
		
		combo.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {				
				setInput();
				groupBody.layout();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
		
		comboVersion.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {				
				setInput();
				groupBody.layout();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
		
		buttonInit.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				switch (event.type) {
				case SWT.Selection:
					if("Connect".equals(buttonInit.getText())) {
						client = new Client(textHost.getText(), Integer.parseInt(textPort.getText()));
						client.start();
						
						buttonInit.setText("Disconnect");
						buttonSend.setEnabled(true);
					} else {
						buttonInit.setText("Connect");
						buttonSend.setEnabled(false);
						
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
						int cmd = 0;
						if(combo.getSelectionIndex() == 0) {
    	            		cmd = Header.CMD_REG;
    	            	} else if(combo.getSelectionIndex() == 1) {
    	            		cmd = Header.CMD_KEEPALIVE;
    	            	} else if(combo.getSelectionIndex() == 2) {
    	            		cmd = Header.CMD_DETECT;
    	            	} else if(combo.getSelectionIndex() == 3) {
    	            		cmd = Header.CMD_UCLOUD_STORE;
    	            	} else if(combo.getSelectionIndex() == 4) {
    	            		cmd = Header.CMD_UCLOUD_TOKEN;
    	            	} else if(combo.getSelectionIndex() == 5) {
    	            		cmd = Header.CMD_SDCARD;
    	            	}
						
						byte[] _name = new byte[8];
						System.arraycopy(textName.getText().getBytes(), 0, _name, 0, textName.getText().getBytes().length);
						
						byte[] _tempVersion = new byte[4];
						if(comboVersion.getSelectionIndex() == 0) { // 0.73 
							_tempVersion[2] = 73;
						} else if(comboVersion.getSelectionIndex() == 1) { // 0.76
							_tempVersion[2] = 76;
						} else if(comboVersion.getSelectionIndex() == 2) { // 0.79
							_tempVersion[2] = 79;
						}

						byte[] _version = new byte[4];												
						System.arraycopy(_tempVersion, 0, _version, 0, 4);
						
						byte[] _cmd = new byte[4];
						System.arraycopy(Util.int2Byte(cmd), 0, _cmd, 0, 4);
						
						byte[] _cid = new byte[4];
						System.arraycopy(Util.int2Byte(new Random().nextInt()), 0, _cid, 0, 4);								
						
						
						Control[] controls = groupBody.getChildren();
						
						int bodySize = 0;
						for(int i = 0; i < controls.length; i++) {
							if(controls[i] instanceof Text) {
								Text text = (Text)controls[i];
								String value = text.getText();
								
								int size = ((Integer)text.getData("size")).intValue();
								if(size < 0) {
									byte[] b = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("1.jpg"));
									size = b.length;
								}
								
								bodySize += size;
							}
						}
						
						int offset = 0;
						byte[] body = new byte[bodySize];						
						for(int i = 0; i < controls.length; i++) {
							if(controls[i] instanceof Text) {
								Text text = (Text)controls[i];
								String value = text.getText();
								int size = ((Integer)text.getData("size")).intValue();
								String type = text.getData("type").toString();
								
								byte[] b = null;
								if(size > 0) {
									b = new byte[size];
									
									if("char".equals(type)) {
										System.arraycopy(value.getBytes(), 0, b, 0, value.getBytes().length);
									} else if("uuid".equals(type)) {
										System.arraycopy(Util.uuid2Byte(value), 0, b, 0, Util.uuid2Byte(value).length);
									} else {
										System.arraycopy(Util.int2Byte(Integer.parseInt(value)), 0, b, 0, Util.int2Byte(Integer.parseInt(value)).length);
									}
								} else {
									b = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("1.jpg"));
								}
								
								System.arraycopy(b, 0, body, offset, b.length);
								
								offset += b.length;
							}
						}
						
						
						byte[] _size = new byte[4];
						System.arraycopy(Util.int2Byte(bodySize), 0, _size, 0, 4);
						
						byte[] header = new byte[24];
						System.arraycopy(_name, 0, header, 0, 8);
						System.arraycopy(_version, 0, header, 8, 4);
						System.arraycopy(_cmd, 0, header, 12, 4);
						System.arraycopy(_cid, 0, header, 16, 4);
						System.arraycopy(_size, 0, header, 20, 4);
						
                		client.sendData(header, body, comboVersion.getSelectionIndex());
	                		
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
	
	private void setFunction() {
		Control[] controls = groupHeader.getChildren();
		for(int i = 0; i < controls.length; i++) {
			if(controls[i].getVisible()) {
				controls[i].dispose();
			}
		}
		
		if(comboDev.getSelectionIndex() == 0) { 
			new Label(groupHeader, SWT.NULL).setText("상세기능\n(Command)");
			combo = new Combo(groupHeader, SWT.BORDER);
			combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			combo.add("도어락 등록", 0);
			combo.add("도어락 삭제", 1);
			combo.add("사용자 등록 통보", 2);
			combo.add("사용자 삭제 통보", 3);
			combo.add("도어 출입 통보(unlock)", 4);
			combo.add("도어 출입 통보(lock) ", 5);
			combo.add("도어락 상태 통보 ", 6);
			combo.add("도어락 비상 통보 Ⅰ", 7);
			combo.add("도어락 비상 통보 Ⅱ", 8);
			combo.add("도어락 초기화  통보", 9);
			combo.add("도어락 PW입력 오류 통보", 10);
			combo.add("도어락 장시간 문열림 통보", 11);
			combo.add("도어락 방범모드 설정/해제 통보", 12);
			combo.add("도어락 삭제", 13);
			combo.select(0);

			new Label(groupHeader, SWT.NULL).setText("트랜잭션아이디");
			textName = new Text(groupHeader, SWT.SINGLE | SWT.BORDER);
			textName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			textName.setText("20140505195220_EXAMPLE_LOWSYSTEM");
			textName.setData("size", 8);
			
		} else if(comboDev.getSelectionIndex() == 1) { 
			new Label(groupHeader, SWT.NULL).setText("상세기능\n(Command)");
			combo = new Combo(groupHeader, SWT.BORDER);
			combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			combo.add("Open/Close sensor 등록", 0);
			combo.add("Open/Close sensor 삭제", 1);
			combo.add("Open/Close sensor 상태 통보", 2);
			combo.add("Open/Close sensor 감지 통보", 3);
			combo.add("Open/Close sensor 초기화 통보(추가)", 4);
			combo.add("wake-up timer 설정", 5);
			combo.select(0);

			new Label(groupHeader, SWT.NULL).setText("트랜잭션아이디");
			textName = new Text(groupHeader, SWT.SINGLE | SWT.BORDER);
			textName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			textName.setText("20140505195220_EXAMPLE_LOWSYSTEM");
			textName.setData("size", 8);
			
		} else if(comboDev.getSelectionIndex() == 2) {
			new Label(groupHeader, SWT.NULL).setText("상세기능\n(Command)");
			combo = new Combo(groupHeader, SWT.BORDER);
			combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			combo.add("Gas valve 등록", 0);
			combo.add("Gas valve 삭제", 1);
			combo.add("Gas valve 상태 통보", 2);
			combo.add("Gav valve 동작 통보", 3);
			combo.select(0);

			new Label(groupHeader, SWT.NULL).setText("트랜잭션아이디");
			textName = new Text(groupHeader, SWT.SINGLE | SWT.BORDER);
			textName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			textName.setText("20140505195220_EXAMPLE_LOWSYSTEM");
			textName.setData("size", 8);
			
		} 
	}
	
	private void setInput() {
		Control[] controls = groupBody.getChildren();
		for(int i = 0; i < controls.length; i++) {
			if(controls[i].getVisible()) {
				controls[i].dispose();
			}
		}
		
		if(combo.getSelectionIndex() == 0) { // 홈카메라 등록
			if(comboVersion.getSelectionIndex() == 0) {
				new Label(groupBody, SWT.NULL).setText("Model");
				Text textModel = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
				textModel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				textModel.setText("HOMECCTV");
				textModel.setData("size", 20);
				textModel.setData("type", "char");
				
				new Label(groupBody, SWT.NULL).setText("DeviceType");
				Text textDeviceType = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
				textDeviceType.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				textDeviceType.setText("K9093133");
				textDeviceType.setData("size", 40);
				textDeviceType.setData("type", "char");
				
				new Label(groupBody, SWT.NULL).setText("MAC");
				Text textMAC = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
				textMAC.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				textMAC.setText("00:1E:B2:1B:2F:BE");
				textMAC.setData("size", 30);
				textMAC.setData("type", "char");
				
				new Label(groupBody, SWT.NULL).setText("S/N");
				Text textSAID = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
				textSAID.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				textSAID.setText("1234567980123456798012345679801234567980");
				textSAID.setData("size", 40);
				textSAID.setData("type", "char");
				
				new Label(groupBody, SWT.NULL).setText("Secret");
				Text textSecret = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
				textSecret.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				textSecret.setText("827CCB0EEA8A706C4C34A16891F04001");
				textSecret.setData("size", 40);
				textSecret.setData("type", "char");
				
				new Label(groupBody, SWT.NULL).setText("Location");
				Text textLocation = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
				textLocation.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				textLocation.setText("79");
				textLocation.setData("size", 4);
				textLocation.setData("type", "int");
				
				new Label(groupBody, SWT.NULL).setText("Reseved1");
				Text textReseved1 = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
				textReseved1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				textReseved1.setText("1");
				textReseved1.setData("size", 4);
				textReseved1.setData("type", "int");
				
				new Label(groupBody, SWT.NULL).setText("Reseved2");
				Text textReseved2 = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
				textReseved2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				textReseved2.setText("1");
				textReseved2.setData("size", 4);
				textReseved2.setData("type", "int");
				
			} else {
				new Label(groupBody, SWT.NULL).setText("Model");
				Text textModel = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
				textModel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				textModel.setText("K9093133");
				textModel.setData("size", 20);
				textModel.setData("type", "char");
				
				new Label(groupBody, SWT.NULL).setText("DeviceType");
				Text textDeviceType = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
				textDeviceType.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				textDeviceType.setText("IP_CCTV");
				textDeviceType.setData("size", 40);
				textDeviceType.setData("type", "char");
				
				new Label(groupBody, SWT.NULL).setText("MAC");
				Text textMAC = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
				textMAC.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				textMAC.setText("00:1E:B2:1B:2F:BE");
				textMAC.setData("size", 30);
				textMAC.setData("type", "char");
				
				new Label(groupBody, SWT.NULL).setText("CameraSAID");
				Text textSAID = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
				textSAID.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				textSAID.setText("camsai04001");
				textSAID.setData("size", 11);
				textSAID.setData("type", "char");
				
				new Label(groupBody, SWT.NULL).setText("Secret");
				Text textSecret = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
				textSecret.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				textSecret.setText("827CCB0EEA8A706C4C34A16891F04001");
				textSecret.setData("size", 40);
				textSecret.setData("type", "char");
				
				new Label(groupBody, SWT.NULL).setText("Location");
				Text textLocation = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
				textLocation.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				textLocation.setText("79");
				textLocation.setData("size", 4);
				textLocation.setData("type", "int");
				
				new Label(groupBody, SWT.NULL).setText("Reseved1");
				Text textReseved1 = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
				textReseved1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				textReseved1.setText("1");
				textReseved1.setData("size", 4);
				textReseved1.setData("type", "int");
				
				new Label(groupBody, SWT.NULL).setText("Reseved2");
				Text textReseved2 = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
				textReseved2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				textReseved2.setText("1");
				textReseved2.setData("size", 4);
				textReseved2.setData("type", "int");
				
			}
			
		} else if(combo.getSelectionIndex() == 1) { // keep-alive
			new Label(groupBody, SWT.NULL).setText("DeviceID");
			Text textDeviceID = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
			textDeviceID.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			textDeviceID.setText(client.deviceId);
			textDeviceID.setData("size", 16);
			textDeviceID.setData("type", "uuid");
			
		} else if(combo.getSelectionIndex() == 2) { // 사용자 등록 통보
			new Label(groupBody, SWT.NULL).setText("External System ID");
			Text textModel = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
			textModel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			textModel.setText("EXAMPLE_LOWSYSTEM");
			textModel.setData("size", 20);
			textModel.setData("type", "char");
			
			new Label(groupBody, SWT.NULL).setText("Device ID");
			Text textDeviceID = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
			textDeviceID.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			textDeviceID.setText("D901CCTV01");
			textDeviceID.setData("size", 40);
			textDeviceID.setData("type", "char");
			
			new Label(groupBody, SWT.NULL).setText("Device Type");
			Text textDeviceType = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
			textDeviceType.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			textDeviceType.setText("현관 출입문");
			textDeviceType.setData("size", 40);
			textDeviceType.setData("type", "char");
			
			new Label(groupBody, SWT.NULL).setText("Model Name\n(product type ID)");
			Text textMAC = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
			textMAC.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			textMAC.setText("SNB-6004");
			textMAC.setData("size", 30);
			textMAC.setData("type", "char");
			
			new Label(groupBody, SWT.NULL).setText("User ID");
			Text textSAID = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
			textSAID.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			textSAID.setText("Test Everything");
			textSAID.setData("size", 11);
			textSAID.setData("type", "char");
			
			new Label(groupBody, SWT.NULL).setText("User PW");
			Text textSecret = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
			textSecret.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			textSecret.setText("Test Everything");
			textSecret.setData("size", 40);
			textSecret.setData("type", "char");
			
		} else if(combo.getSelectionIndex() == 3) { // ucloud 공간 부족
			if(comboVersion.getSelectionIndex() == 2) {
				new Label(groupBody, SWT.NULL).setText("DeviceID");
				Text textDeviceID = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
				textDeviceID.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				textDeviceID.setText(client.deviceId);
				textDeviceID.setData("size", 16);
				textDeviceID.setData("type", "uuid");
				
				Date now = new Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
				
				new Label(groupBody, SWT.NULL).setText("DateTime");
				Text textDateTime = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
				textDateTime.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				textDateTime.setText(format.format(now));
				textDateTime.setData("size", 14);
				textDateTime.setData("type", "char");
				
				new Label(groupBody, SWT.NULL).setText("EventType");
				Text textEventType = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
				textEventType.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				textEventType.setText("1");
				textEventType.setData("size", 4);
				textEventType.setData("type", "int");
				
				new Label(groupBody, SWT.NULL).setText("SpareStorage");
				Text textSpareStorage = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
				textSpareStorage.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				textSpareStorage.setText("1024");
				textSpareStorage.setData("size", 8);
				textSpareStorage.setData("type", "int");
				
				new Label(groupBody, SWT.NULL).setText("ErrorCode");
				Text textErrorMsg = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
				textErrorMsg.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				textErrorMsg.setText("");
				textErrorMsg.setData("size", 4);
				textErrorMsg.setData("type", "int");
			} else {
				new Label(groupBody, SWT.NULL).setText("DeviceID");
				Text textDeviceID = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
				textDeviceID.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				textDeviceID.setText(client.deviceId);
				textDeviceID.setData("size", 16);
				textDeviceID.setData("type", "uuid");
				
				Date now = new Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
				
				new Label(groupBody, SWT.NULL).setText("DateTime");
				Text textDateTime = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
				textDateTime.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				textDateTime.setText(format.format(now));
				textDateTime.setData("size", 14);
				textDateTime.setData("type", "char");
				
				new Label(groupBody, SWT.NULL).setText("EventType");
				Text textEventType = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
				textEventType.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				textEventType.setText("1");
				textEventType.setData("size", 4);
				textEventType.setData("type", "int");
				
				new Label(groupBody, SWT.NULL).setText("SpareStorage");
				Text textSpareStorage = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
				textSpareStorage.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				textSpareStorage.setText("1024");
				textSpareStorage.setData("size", 8);
				textSpareStorage.setData("type", "int");
				
				new Label(groupBody, SWT.NULL).setText("ErrorMsg");
				Text textErrorMsg = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
				textErrorMsg.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				textErrorMsg.setText("");
				textErrorMsg.setData("size", 40);
				textErrorMsg.setData("type", "char");
			}
			
		} else if(combo.getSelectionIndex() == 4) {
			new Label(groupBody, SWT.NULL).setText("DeviceID");
			Text textDeviceID = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
			textDeviceID.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			textDeviceID.setText(client.deviceId);
			textDeviceID.setData("size", 16);
			textDeviceID.setData("type", "uuid");
			
		} else if(combo.getSelectionIndex() == 5) {
			new Label(groupBody, SWT.NULL).setText("DeviceID");
			Text textDeviceID = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
			textDeviceID.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			textDeviceID.setText(client.deviceId);
			textDeviceID.setData("size", 16);
			textDeviceID.setData("type", "uuid");
			
			Date now = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			
			new Label(groupBody, SWT.NULL).setText("DateTime");
			Text textDateTime = new Text(groupBody, SWT.SINGLE | SWT.BORDER);
			textDateTime.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			textDateTime.setText(format.format(now));
			textDateTime.setData("size", 14);
			textDateTime.setData("type", "char");
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
			}
		});
	}

	public static void main(String[] args) {		
		new Main();
	}
}
