package com.kt.iot.emul;

import java.awt.color.CMMException;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kt.iot.emul.client.Client;
import com.kt.iot.emul.model.Header;
import com.kt.iot.emul.util.Util;
import com.kt.iot.emul.code.StdSysTcpCode;
import com.kt.iot.emul.code.StdSysTcpCode.HdrType;
import com.kt.iot.emul.code.StdSysTcpCode.MsgExchPtrn;
import com.kt.iot.emul.code.StdSysTcpCode.MsgType;
import com.kt.iot.emul.code.StdSysTcpCode.MthdType;
import com.kt.iot.emul.code.GwCode.UseYn;
import com.kt.iot.emul.code.StdSysTcpCode.EncdngType;
import com.kt.iot.emul.vo.ComnRqtVO;
import com.kt.iot.emul.vo.MsgHeadVO;
import com.kt.iot.emul.vo.TcpHdrVO;
import com.kt.iot.emul.vo.CommChAthnRqtVO;

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
	private static Shell shell;
	
	public Main() {
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
//		groupDevice.setText("Function");
//		groupDevice.setLayout(new GridLayout(2, false));
		groupDevice.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
//		Label deviceNameLabel = new Label(groupDevice, SWT.NULL);
//		deviceNameLabel.setLayoutData(new GridData(85, 0));
//		deviceNameLabel.setVisible(false);
//		
//		Label deviceInputLabel = new Label(groupDevice, SWT.NULL);
//		deviceInputLabel.setLayoutData(new GridData(85, 0));
//		deviceInputLabel.setVisible(false);
		
		buttonInit = new Button(shell, SWT.PUSH);
		buttonInit.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		buttonInit.setText("TCP 채널 인증 Connect");
		
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
					if("TCP 채널 인증 Connect".equals(buttonInit.getText())) {
						
						client = new Client(textHost.getText(), Integer.parseInt(textPort.getText()));
						client.start();
						
						initSendData();
						
						setDevice();
						groupDevice.layout();
						
						buttonInit.setText("Disconnect");
						buttonSend.setEnabled(true);
					} else {
						buttonInit.setText("TCP 채널 인증 Connect");
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
						
						byte[] packLen = new byte[4];
						
                		client.sendData(header, body);
	                		
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
	
	private void setDevice(){

		Control[] controls = groupDevice.getChildren();
		for(int i = 0; i < controls.length; i++) {
			if(controls[i].getVisible()) {
				controls[i].dispose();
			}
		}
		
		groupDevice.setText("IoT Deviece");
		groupDevice.setLayout(new GridLayout(2, false));
		
		Label deviceNameLabel = new Label(groupDevice, SWT.NULL);
		deviceNameLabel.setLayoutData(new GridData(85, 0));
		deviceNameLabel.setVisible(false);
		
		Label deviceInputLabel = new Label(groupDevice, SWT.NULL);
		deviceInputLabel.setLayoutData(new GridData(85, 0));
		deviceInputLabel.setVisible(false);
		
		new Label(groupDevice, SWT.NULL).setText("Function");
		comboDev = new Combo(groupDevice, SWT.BORDER);
		comboDev.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		comboDev.add("KeepAlive", 0);
		comboDev.add("외부시스템 조회", 1);
		comboDev.add("갱신보고", 2);
		comboDev.select(0);
		
	}
		
	public void initSendData(){
		StdSysTcpCode.MthdType mthType = MthdType.ATHN_COMMCHATHN_EXTRSYS_TCP; 
		try {
			byte[] header = getHeader().toPacket();

			StdSysTcpCode.MthdType mthdType = MthdType.ATHN_COMMCHATHN_EXTRSYS_TCP;
			
			String strBody = getBody(mthdType.getValue());
			byte[] body = strBody.getBytes();
			
    		client.sendData(header, body);
        		
		} catch(Exception e) {
			report(e.toString(), true);
        	e.printStackTrace();
        }
	}
	
	private TcpHdrVO getHeader() {
		Long trmTransactionId = System.currentTimeMillis();
		TcpHdrVO tcpHdrVO = new TcpHdrVO();
		tcpHdrVO.setMainVer( (byte) 1 );
		tcpHdrVO.setSubVer( (byte) 1 );
		tcpHdrVO.setHdrType(HdrType.BASIC);
		tcpHdrVO.setMsgType(MsgType.REQUEST);
		tcpHdrVO.setMsgExchPtrn(MsgExchPtrn.ONE_WAY_ACK);
		tcpHdrVO.setMthdType(MthdType.ATHN_COMMCHATHN_DEV_TCP);
		tcpHdrVO.setTrmTransactionId(trmTransactionId);
		//최초인증은 토큰 없음
		tcpHdrVO.setAuthTkn(null);

		//암호화 미사용
		tcpHdrVO.setEcodUseYn(UseYn.NO);
		tcpHdrVO.setEcodType(null);
		
		//압축 미사용
		tcpHdrVO.setCmpreUseYn(UseYn.NO);
		tcpHdrVO.setCmpreType(null);
		//인코딩 JSON

		tcpHdrVO.setEncdngType(EncdngType.JSON);
		tcpHdrVO.setRsltCd( (short) 0);
		return tcpHdrVO;
	}
	
	public String getBody(Short value){
		String strBody = "";
		StdSysTcpCode.MthdType mthcode = MthdType.ATHN_COMMCHATHN_EXTRSYS_TCP;
		
		if(mthcode.equals(value)){
			CommChAthnRqtVO commChAthnRqtVO = new CommChAthnRqtVO();
			
			String athnRqtNo = "";
			String commChId = "";
			String extrSysId = "";
			MsgHeadVO msgHeadVO = new MsgHeadVO();
			
			commChAthnRqtVO.setAthnRqtNo(athnRqtNo);
			commChAthnRqtVO.setCommChId(commChId);
			commChAthnRqtVO.setExtrSysId(extrSysId);
			commChAthnRqtVO.setMsgHeadVO(msgHeadVO);
		
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").setPrettyPrinting().create();
			strBody = gson.toJson(commChAthnRqtVO);
		} else{
			
		}
		
		return strBody;
		
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
