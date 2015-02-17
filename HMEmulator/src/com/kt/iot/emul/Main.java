package com.kt.iot.emul;

import java.awt.color.CMMException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import com.kt.iot.emul.util.Util;
import com.kt.iot.emul.code.StdSysTcpCode;
import com.kt.iot.emul.code.StdSysTcpCode.HdrType;
import com.kt.iot.emul.code.StdSysTcpCode.MsgExchPtrn;
import com.kt.iot.emul.code.StdSysTcpCode.MsgType;
import com.kt.iot.emul.code.StdSysTcpCode.MthdType;
import com.kt.iot.emul.code.GwCode.UseYn;
import com.kt.iot.emul.code.StdSysTcpCode.EncdngType;
import com.kt.iot.emul.vo.CmdDataInfoVO;
import com.kt.iot.emul.vo.CommChAthnRespVO;
import com.kt.iot.emul.vo.ComnRqtVO;
import com.kt.iot.emul.vo.DevBasVO;
import com.kt.iot.emul.vo.DevInfoUdateRprtRqtVO;
import com.kt.iot.emul.vo.MsgHeadVO;
import com.kt.iot.emul.vo.TcpHdrVO;
import com.kt.iot.emul.vo.CommChAthnRqtVO;
import com.kt.iot.emul.vo.DevInfoRetvRqtVO;

public class Main {
	private static Client client = new Client();
	
	private static Display display;
	
	private static Combo comboVersion;
	private Combo combo;
	private Combo comboDev;
	
	private static Text textName;
	
//	private Combo textHost;
	private Text textHost;
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
	
	public static String athnNo;
	
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
//		textHost = new Combo(groupProxy, SWT.BORDER);
		textHost = new Text(groupProxy, SWT.BORDER);
		textHost.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textHost.setText("211.42.137.221");
//		textHost.add("127.0.0.1", 0);
//		textHost.add("211.42.137.221", 0);
//		textHost.add("121.156.46.132", 1);
//		textHost.select(0);
		
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
		groupDevice.setText("");
		groupDevice.setLayout(new GridLayout(2, false));
		groupDevice.setLayoutData(new GridData(615, 50));
		
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
						short methType = 0;
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
    	            	} 
						
						byte[] header = getHeader(methcode).toPacket();
//						byte[] header = getHeader(methcode).getBytes();

						String strBody = getBody(methType);
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
	
	private void setDevice(){

		Control[] controls = groupDevice.getChildren();
		for(int i = 0; i < controls.length; i++) {
			if(controls[i].getVisible()) {
				controls[i].dispose();
			}
		}
		
		groupDevice.setText("Detail Function");
		groupDevice.setLayout(new GridLayout(2, false));
		
		new Label(groupDevice, SWT.NULL).setText("Function");
		comboDev = new Combo(groupDevice, SWT.BORDER);
		comboDev.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		comboDev.add("KeepAlive", 0);
		comboDev.add("장치정보 조회", 1);
		comboDev.add("장치정보 갱신보고", 2);
		comboDev.select(0);
		
		comboDev.setVisible(true);
		
	}
		
	public void initSendData(){
		StdSysTcpCode.MthdType mthType = MthdType.ATHN_COMMCHATHN_DEV_TCP; 
		try {
			byte[] header = getHeader(MthdType.ATHN_COMMCHATHN_DEV_TCP).toPacket();
//			byte[] header = getHeader(MthdType.ATHN_COMMCHATHN_DEV_TCP).getBytes();
			
			StdSysTcpCode.MthdType mthdType = MthdType.ATHN_COMMCHATHN_DEV_TCP;
			String strBody = getBody(mthdType.getValue());
			byte[] body = strBody.getBytes();
			
			System.out.println(" body : "+ new String(body) +" \n header : "+ new String(header));
    		client.sendData(header, body, mthdType.getValue());

		} catch(Exception e) {
			report(e.toString(), true);
        	e.printStackTrace();
        }
	}
	
	private static TcpHdrVO tcpHdrVO = new TcpHdrVO();
	public static TcpHdrVO getHeader(StdSysTcpCode.MthdType mthdType){
//	public static String getHeader(StdSysTcpCode.MthdType mthdType){
		String header = "";
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").setPrettyPrinting().create();
		Long trmTransactionId = System.currentTimeMillis();
		try {
			tcpHdrVO.setMainVer( (byte) 1 );
			tcpHdrVO.setSubVer( (byte) 1 );
			tcpHdrVO.setHdrType(HdrType.BASIC);
			tcpHdrVO.setMsgType(MsgType.REQUEST);
			tcpHdrVO.setMsgExchPtrn(MsgExchPtrn.ONE_WAY_ACK);
			tcpHdrVO.setMthdType(mthdType);
			tcpHdrVO.setTrmTransactionId(trmTransactionId);
			
			//최초인증은 토큰 없음
			if(athnNo != null){
				tcpHdrVO.setAuthTkn(UUID.fromString(athnNo));
			}else{
				tcpHdrVO.setAuthTkn(null);
			}
			
			//암호화 미사용
			tcpHdrVO.setEcodUseYn(UseYn.NO);
			tcpHdrVO.setEcodType(null);
			
			//압축 미사용
			tcpHdrVO.setCmpreUseYn(UseYn.NO);
			tcpHdrVO.setCmpreType(null);
			//인코딩 JSON
			
			tcpHdrVO.setEncdngType(EncdngType.JSON);
			tcpHdrVO.setRsltCd( (short) 0);
		} catch (Exception e) {
			// TODO: handle exception
			report(e.toString(), true);
        	e.printStackTrace();
		}
//		header = gson.toJson(tcpHdrVO);
//		return header;
		return tcpHdrVO;
		
	}
	
	public static String getBody(Short value){
		String strBody = "";
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").setPrettyPrinting().create();
		
		if(MthdType.ATHN_COMMCHATHN_DEV_TCP.equals(value)){
			CommChAthnRqtVO commChAthnRqtVO = new CommChAthnRqtVO();
			
			String athnRqtNo = "12345678";
			String commChId = "MPU_INTERGRATION_CHANNEL";
			String extrSysId = "EXAMPLE_LOWSYSTEM";
			MsgHeadVO msgHeadVO = new MsgHeadVO();
			
			commChAthnRqtVO.setAthnRqtNo(athnRqtNo);
			commChAthnRqtVO.setCommChId(commChId);
			commChAthnRqtVO.setExtrSysId(extrSysId);
			commChAthnRqtVO.setMsgHeadVO(msgHeadVO);
		
			strBody = gson.toJson(commChAthnRqtVO);
		} 
		else if(MthdType.KEEP_ALIVE_COMMCHATHN_TCP.equals(value)){
			CommChAthnRespVO commChAthnRqtVO = new CommChAthnRespVO();
			strBody = gson.toJson(commChAthnRqtVO);
		} 
		else if(MthdType.INITA_DEV_RETV.equals(value)){
			DevInfoRetvRqtVO devInfoRetvRqtVO = new DevInfoRetvRqtVO();
			
			String extrSysId = "EXAMPLE_LOWSYSTEM";/** 외부시스템아이디 */
			Integer	m2mSvcNo = 0;/** M2M서비스번호 */
			List<String> inclDevIds = new ArrayList<String>();/** 포함장치아이디목록 */
			List<String> excluDevIds = new ArrayList<String>();/** 배타장치아이디목록 */
			List<CmdDataInfoVO> cmdDataInfoVOs = new ArrayList<CmdDataInfoVO>();/** 명령데이터리스트(31) */
			String modelNm = "";/** 모델명 */
			String useYn = "";/** 사용여부 */
			Date cretDtSt = new Date();/** 생성일시시작 */
			Date cretDtFns = new Date();/** 생성일시종료 */
			Date amdDtSt = new Date();/** 수정일시시작 */
			Date amdDtFns = new Date();/** 수정일시종료 */
			
			devInfoRetvRqtVO.setExtrSysId(extrSysId);
			devInfoRetvRqtVO.setM2mSvcNo(m2mSvcNo);
			devInfoRetvRqtVO.setModelNm(modelNm);
			devInfoRetvRqtVO.setUseYn(useYn);
			devInfoRetvRqtVO.setCretDtSt(cretDtSt);
			devInfoRetvRqtVO.setCretDtFns(cretDtFns);
			devInfoRetvRqtVO.setAmdDtSt(amdDtSt);
			devInfoRetvRqtVO.setAmdDtFns(amdDtFns);
			devInfoRetvRqtVO.setInclDevIds(inclDevIds);
			devInfoRetvRqtVO.setExcluDevIds(excluDevIds);
			devInfoRetvRqtVO.setCmdDataInfoVOs(cmdDataInfoVOs);
			
			strBody = gson.toJson(devInfoRetvRqtVO);
			
		}
		else if(MthdType.INITA_DEV_UDATERPRT.equals(value)){
			DevInfoUdateRprtRqtVO devInfoUpdateRprtRqtVO = new DevInfoUdateRprtRqtVO();
			
			/** 외부시스템아이디 */
			String extrSysId = "";
			/** 정보갱신유형 */
			String	infoUpdTypeCd = "";
			/** 장치정보목록 */
			List<DevBasVO> devBasVOs = new ArrayList<DevBasVO>();
			
			devInfoUpdateRprtRqtVO.setExtrSysId(extrSysId);
			devInfoUpdateRprtRqtVO.setInfoUpdTypeCd(infoUpdTypeCd);
			devInfoUpdateRprtRqtVO.setDevBasVOs(devBasVOs);
			
			strBody = gson.toJson(devInfoUpdateRprtRqtVO);
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
				buttonInit.setText("TCP 채널 인즏 Connect");
				buttonSend.setEnabled(false);
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
					byte[] header = getHeader(mthdType).toPacket();
//					byte[] header = getHeader(mthdType).getBytes();
					
					String strBody = getBody(mthdType.getValue());
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
}
