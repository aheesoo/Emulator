package com.kt.iot.emul;

import java.awt.color.CMMException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
	private Combo combo;
	private Combo comboDev;
	
	private static Text textName;
	
//	private Combo textHost;
	private Text textHost;
	private Text textPort; 
	private static Text textRes;
	
	private static Group groupDevice;
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
		textHost.setText("192.168.0.117");//192.168.0.63으로 하면 response는 내려와
//		textHost.setText("192.168.0.63");
//		textHost.add("127.0.0.1", 0);
//		textHost.add("121.156.46.132", 1);
//		textHost.select(0);
		
		new Label(groupProxy, SWT.NULL).setText("Port");
		textPort = new Text(groupProxy, SWT.SINGLE | SWT.BORDER);
		textPort.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textPort.setText("9075");
		
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
						
//						initSendData();
//						setDevice();
//						groupDevice.layout();
						buttonInit.setText("TCP 채널 인증");
//						buttonSend.setEnabled(true);
					} else if("TCP 채널 인증".equals(buttonInit.getText())){
						initSendData();
						setDevice();
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
						
						byte[] header = getHeader(methcode, isRequest).toPacket();
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
		
		if(groupDevice.getVisible() == false){
			groupDevice.setVisible(true);
		}
		
		groupDevice.setText("Detail Function");
		groupDevice.setLayout(new GridLayout(2, false));
		
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
		
	public static void initSendData(){
		StdSysTcpCode.MthdType mthType = MthdType.ATHN_COMMCHATHN_DEV_TCP; 
		try {
			byte[] header = getHeader(MthdType.ATHN_COMMCHATHN_DEV_TCP, 0).toPacket();
			
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
	public static TcpHdrVO getHeader(StdSysTcpCode.MthdType mthdType, int isRequest){
		String header = "";
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").setPrettyPrinting().create();
		Long trmTransactionId = System.currentTimeMillis();
		try {
			tcpHdrVO.setMainVer( (byte) 1 );
			tcpHdrVO.setSubVer( (byte) 1 );
			tcpHdrVO.setHdrType(HdrType.BASIC);
			if(isRequest == 0){
				tcpHdrVO.setMsgType(MsgType.REQUEST);
			}else if(isRequest == 1){
				tcpHdrVO.setMsgType(MsgType.RESPONSE);
			}else if(isRequest == 2){
				tcpHdrVO.setMsgType(MsgType.REPORT);
			} 
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
		return tcpHdrVO;
		
	}
	
	public static String getBody(Short value){
		String strBody = "";
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").setPrettyPrinting().create();
		MsgHeadVO msgHeadVO = new MsgHeadVO();
//		msgHeadVO.setCmpreType("1");
//		msgHeadVO.setCommChAthnNo("2");
//		msgHeadVO.setEcodType("3");
//		msgHeadVO.setEncdngType("4");
//		msgHeadVO.setHdrLen((short)0);
//		msgHeadVO.setHdrType("5");
//		msgHeadVO.setMapHeaderExtension(null);
		msgHeadVO.setMethodType("test");
//		msgHeadVO.setMsgExchPtrn("6");
//		msgHeadVO.setMsgType("7");
//		msgHeadVO.setProtVer("8");
		msgHeadVO.setTrmTransacId("20140505195220_EXAMPLE_LOWSYSTEM");
		
		if(MthdType.ATHN_COMMCHATHN_DEV_TCP.equals(value)){
			DevCommChAthnRqtVO commChAthnRqtVO = new DevCommChAthnRqtVO();
			
			String athnRqtNo = "100001";
			String commChId = "GiGA_Home_IoT_TCP";
			String extrSysId = "GiGA_Home_IoT";
			String devId = "ZW140900005";
			
			commChAthnRqtVO.setAthnRqtNo(athnRqtNo);
			commChAthnRqtVO.setCommChId(commChId);
			commChAthnRqtVO.setExtrSysId(extrSysId);
			commChAthnRqtVO.setDevId(devId);;
			commChAthnRqtVO.setMsgHeadVO(msgHeadVO);
			
			strBody = gson.toJson(commChAthnRqtVO);
		} 
		else if(MthdType.KEEP_ALIVE_COMMCHATHN_TCP.equals(value)){
			CommChAthnRespVO commChAthnRqtVO = new CommChAthnRespVO();
			strBody = gson.toJson(commChAthnRqtVO);
		} 
		else if(MthdType.INITA_DEV_RETV.equals(value)){ //331 조회
			DevInfoRetvRqtVO devInfoRetvRqtVO = new DevInfoRetvRqtVO();
			
			String extrSysId = "EXAMPLE_LOWSYSTEM";/** 외부시스템아이디 */
			Integer	m2mSvcNo = 0;/** M2M서비스번호 */
			List<String> inclDevIds = new ArrayList<String>();/** 포함장치아이디목록 */
			inclDevIds.add("D901CCTV01");
			List<String> excluDevIds = new ArrayList<String>();/** 배타장치아이디목록 */
			excluDevIds.add("testDevice");
			CmdDataInfoVO cmdDataInfoVO = new CmdDataInfoVO();
			List<CmdDataInfoVO> cmdDataInfoVOs = new ArrayList<CmdDataInfoVO>();/** 명령데이터리스트(31) */
			String dataTypeCd = "";
			byte[] cmdData = new byte[3];
			cmdData[0] = (byte) 0x50;
			cmdData[1] = (byte) 0x51;
			cmdData[2] = (byte) 0x52;
			cmdDataInfoVO.setDataTypeCd(dataTypeCd);
			cmdDataInfoVO.setCmdData(cmdData);
			cmdDataInfoVOs.add(cmdDataInfoVO);
			
			String modelNm = "SNB-6004";/** 모델명 */
			String useYn = "Y";/** 사용여부 */
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
			devInfoRetvRqtVO.setMsgHeadVO(msgHeadVO);
			
			strBody = gson.toJson(devInfoRetvRqtVO);
			
		}
		else if(MthdType.INITA_DEV_UDATERPRT.equals(value)){//332 갱신보고
			DevInfoUdateRprtRqtVO devInfoUpdateRprtRqtVO = new DevInfoUdateRprtRqtVO();
			
			/** 외부시스템아이디 */
			String extrSysId = "EXAMPLE_LOWSYSTEM";
			/** 정보갱신유형 */
			String	infoUpdTypeCd = "";
			/** 장치정보목록 */
			List<DevBasVO> devBasVOs = new ArrayList<DevBasVO>();
			
			devInfoUpdateRprtRqtVO.setExtrSysId(extrSysId);
			devInfoUpdateRprtRqtVO.setInfoUpdTypeCd(infoUpdTypeCd);
			devInfoUpdateRprtRqtVO.setDevBasVOs(devBasVOs);
			devInfoUpdateRprtRqtVO.setMsgHeadVO(msgHeadVO);
			
			strBody = gson.toJson(devInfoUpdateRprtRqtVO);
		}
		else if(MthdType.COLEC_ITGDATA_RECV.equals(value)){//411 데이터수집 request
			ItgColecDataVO itgColecDataVO = new ItgColecDataVO();
			
			/** 외부시스템아이디 */
			String extrSysId = "EXAMPLE_LOWSYSTEM";
			/** 헤더확장필드 */
			HashMap<String, Object> mapHeaderExtension = new HashMap<String, Object>();
			
			/** rowColData **/
			ColecRowVO colecRowVO = new ColecRowVO();
			List<ColecRowVO> colecRowVOs = new ArrayList<ColecRowVO>();
			
			SnsnDataInfoVO snsnDataInfoVO = new SnsnDataInfoVO();
			List<SnsnDataInfoVO> snsnDataInfoVOs =  new ArrayList<SnsnDataInfoVO>();
			String dataTypeCd_Snsn = "10001003";
			Double snsnVal = 0.7;
			snsnDataInfoVO.setDataTypeCd(dataTypeCd_Snsn);
			snsnDataInfoVO.setSnsnVal(snsnVal);
			snsnDataInfoVOs.add(snsnDataInfoVO);
			
			SttusDataInfoVO sttusDataInfoVO = new SttusDataInfoVO();
			List<SttusDataInfoVO> sttusDataInfoVOs = new ArrayList<SttusDataInfoVO>();
			String dataTypeCd_sttus = "20001003";
			Double sttusVal = 1.0;
			sttusDataInfoVO.setDataTypeCd(dataTypeCd_sttus);
			sttusDataInfoVO.setSttusVal(snsnVal);
			sttusDataInfoVOs.add(sttusDataInfoVO);
			
			LoDataInfoVO loDataInfoVO = new LoDataInfoVO();
			String dataTypeCd_Lo = "30001003";
			Double latit = 1.0;
			Double lngit = 1.0;
			loDataInfoVO.setDataTypeCd(dataTypeCd_Lo);
			loDataInfoVO.setLatit(latit);
			loDataInfoVO.setLngit(lngit);
			
			BinDataInfoVO binDataInfoVO = new BinDataInfoVO();
			List<BinDataInfoVO> binDataInfoVOs = new ArrayList<BinDataInfoVO>();
			String dataTypeCd_Bin = "40001003";
			byte[] binData = new byte[3];
			binData[0] = (byte) 0x50;
			binData[1] = (byte) 0x51;
			binData[2] = (byte) 0x52;
			binDataInfoVO.setDataTypeCd(dataTypeCd_Bin);
			binDataInfoVO.setBinData(binData);
			binDataInfoVOs.add(binDataInfoVO);
			
			StrDataInfoVO strDataInfoVO = new StrDataInfoVO();
			List<StrDataInfoVO> strDataInfoVOs = new ArrayList<StrDataInfoVO>();
			String snsnTagCd = "60001003";
			String strVal = "";
			strDataInfoVO.setSnsnTagCd(snsnTagCd);
			strDataInfoVO.setStrVal(strVal);
			strDataInfoVOs.add(strDataInfoVO);
			
			DtDataInfoVO dtDataInfoVO = new DtDataInfoVO();
			List<DtDataInfoVO> dtDataInfoVOs = new ArrayList<DtDataInfoVO>();
			String snsnTagCd_Dt = "61000837";
			Date dtVal = new Date();
			dtDataInfoVO.setSnsnTagCd(snsnTagCd_Dt);
			dtDataInfoVO.setDtVal(dtVal);
			dtDataInfoVOs.add(dtDataInfoVO);
			
			EvDataInfoVO evDataInfoVO = new EvDataInfoVO();
			String evOccSysId = "EXAMPLE_LOWSYSTEM";
			String evTyepCd = "0001";
			String evDivId = "1234";
			String evClasCd = "";
			String evOccId = "EXAM_EV_0001";
			String evTrtSttus = "0002";
			evDataInfoVO.setEvOccSysId(evOccSysId);
			evDataInfoVO.setEvTypeCd(evTyepCd);
			evDataInfoVO.setEvDivId(evDivId);
			evDataInfoVO.setEvClasCd(evClasCd);
			evDataInfoVO.setEvOccId(evOccId);
			evDataInfoVO.setEvTrtSttus(evTrtSttus);
			
			GenlSetupDataInfoVO genlSetupDataInfoVO = new GenlSetupDataInfoVO();
			List<GenlSetupDataInfoVO> genlSetupDataInfoVOs = new ArrayList<GenlSetupDataInfoVO>();
			String snsnTagCd_Gen = "”80000739";
			String setupVal = "ON";
			genlSetupDataInfoVO.setSetupVal(setupVal);
			genlSetupDataInfoVO.setSnsnTagCd(snsnTagCd_Gen);
			genlSetupDataInfoVOs.add(genlSetupDataInfoVO);
			
			SclgSetupDataInfoVO sclgSetupDataInfoVO = new SclgSetupDataInfoVO();
			SclgDataInfoVO sclgDataInfoVO = new SclgDataInfoVO();
			SclgTimeDataInfoVO sclgTimeDataInfoVO = new SclgTimeDataInfoVO();
			List<SclgSetupDataInfoVO> sclgSetupDataInfoVOs = new ArrayList<SclgSetupDataInfoVO>();
			List<SclgDataInfoVO> sclgDataInfoVOs = new ArrayList<SclgDataInfoVO>();
			List<SclgTimeDataInfoVO> sclgTimeDataInfoVOs = new ArrayList<SclgTimeDataInfoVO>();
			String stTime = "120000";
			String fnsTime = "165959";
			sclgTimeDataInfoVO.setFnsTime(fnsTime);
			sclgTimeDataInfoVO.setStTime(stTime);
			sclgTimeDataInfoVOs.add(sclgTimeDataInfoVO);
			String dowCd = "";
			sclgDataInfoVO.setDowCd(dowCd);
			sclgDataInfoVO.setSclgTimeDataInfoVOs(sclgTimeDataInfoVOs);
			sclgDataInfoVOs.add(sclgDataInfoVO);
			String snsnTagCd_Scl = "";
			sclgSetupDataInfoVO.setSnsnTagCd(snsnTagCd_Scl);
			sclgSetupDataInfoVO.setSclgDataInfoVOs(sclgDataInfoVOs);
			sclgSetupDataInfoVOs.add(sclgSetupDataInfoVO);
			
			BinSetupDataInfoVO binSetupDataInfoVO = new BinSetupDataInfoVO();
			List<BinSetupDataInfoVO> binSetupDataInfoVos = new ArrayList<BinSetupDataInfoVO>();
			String snsnTagCd_Bin = "90000739";
			byte[] setupVal_Bin = new byte[3];
			binData[0] = (byte) 0x50;
			binData[1] = (byte) 0x51;
			binData[2] = (byte) 0x52;
			binSetupDataInfoVO.setSnsnTagCd(snsnTagCd_Bin);
			binSetupDataInfoVO.setSetupVal(setupVal_Bin);
			binSetupDataInfoVos.add(binSetupDataInfoVO);
			
			colecRowVO.setSnsnDataInfoVOs(snsnDataInfoVOs);
			colecRowVO.setSttusDataInfoVOs(sttusDataInfoVOs);
			colecRowVO.setLoDataInfoVO(loDataInfoVO);
			colecRowVO.setBinDataInfoVOs(binDataInfoVOs);
			colecRowVO.setStrDataInfoVOs(strDataInfoVOs);
			colecRowVO.setDtDataInfoVOs(dtDataInfoVOs);
			colecRowVO.setEvDataInfoVO(evDataInfoVO);
			colecRowVO.setGenlSetupDataInfoVOs(genlSetupDataInfoVOs);
			colecRowVO.setSclgSetupDataInfoVOs(sclgSetupDataInfoVOs);
			colecRowVO.setBinSetupDataInfoVOs(binSetupDataInfoVos);
			
			colecRowVOs.add(colecRowVO);
			
			/** 시스템수집데이터 **/
			SysColecDataVO sysColecDataVO = new SysColecDataVO();
			sysColecDataVO.setColecRowVOs(colecRowVOs);
			/** 장비수집데이터리스트 */
			DevColecDataVO devColecDataVO = new DevColecDataVO();
			List<DevColecDataVO> devColecDataVOs = new ArrayList<DevColecDataVO>();
			String devId = "";
			devColecDataVO.setDevId(devId);
			devColecDataVO.setColecRowVOs(colecRowVOs);
			devColecDataVOs.add(devColecDataVO);
			
			itgColecDataVO.setExtrSysId(extrSysId);
			itgColecDataVO.setMapHeaderExtension(mapHeaderExtension);
			itgColecDataVO.setSysColecDataVO(sysColecDataVO);
			itgColecDataVO.setDevColecDataVOs(devColecDataVOs);
			itgColecDataVO.setMsgHeadVO(msgHeadVO);
			
			strBody = gson.toJson(itgColecDataVO);
		} 
		
		return strBody;
	}
	
	public static String getResBody(Short value, byte[] data){
		
		String respCd = "100";
		String respMsg = "SUCCESS";
		
		String strBody = "";
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").setPrettyPrinting().create();
		
		if(value == 333){
			DevInfoRetvRqtVO devInfoRetvRqtVO = gson.fromJson(new String(data), DevInfoRetvRqtVO.class);
			DevInfoRetvRespVO devInfoRetvRespVO = new DevInfoRetvRespVO();
			/** 명령데이터리스트(31) */
//			CmdDataInfoVO cmdDataInfoVO = new CmdDataInfoVO();
//			List<CmdDataInfoVO> cmdDataInfoVOs = new ArrayList<CmdDataInfoVO>();
			//** 장치정보목록 */
			DevBasVO devBasVO = new DevBasVO();
			devBasVO.setExtrSysId("EXAMPLE_LOWSYSTEM");
			devBasVO.setDevId("D901CCTV01");
			devBasVO.setM2mSvcNo(1010);
			List<DevBasVO> devBasVOs = new ArrayList<DevBasVO>();
			devBasVOs.add(devBasVO);
			
			
			devInfoRetvRespVO.setMsgHeadVO(devInfoRetvRqtVO.getMsgHeadVO());
			devInfoRetvRespVO.setRespCd(respCd);
			devInfoRetvRespVO.setRespMsg(respMsg);
			devInfoRetvRespVO.setDevBasVOs(devBasVOs);
			devInfoRetvRespVO.setCmdDataInfoVOs(devInfoRetvRqtVO.getCmdDataInfoVOs());
			
			strBody = gson.toJson(devInfoRetvRespVO);
		} 
		else if(value == 334){
			DevInfoUdateRprtRqtVO devInfoUdateRprtRqtVO = gson.fromJson(new String(data), DevInfoUdateRprtRqtVO.class);
			ComnRespVO comnRespVO = gson.fromJson(new String(data), ComnRespVO.class);
			
			comnRespVO.setRespCd(respCd);
			comnRespVO.setRespMsg(respMsg);
			comnRespVO.setMsgHeadVO(devInfoUdateRprtRqtVO.getMsgHeadVO());
			
			strBody = gson.toJson(comnRespVO);
		}
		else if(MthdType.INITA_DEV_UDATERPRT.equals(value)){
			ItgCnvyDataVO itgCnvyDataVO = gson.fromJson(new String(data), ItgCnvyDataVO.class);
			ComnRespVO comnRespVO = gson.fromJson(new String(data), ComnRespVO.class);
			
			comnRespVO.setRespCd(respCd);
			comnRespVO.setRespMsg(respMsg);
			comnRespVO.setMsgHeadVO(itgCnvyDataVO.getMsgHeadVO());
			
			strBody = gson.toJson(comnRespVO);
		}
		else if(MthdType.QUERY_LASTVAL.equals(value)){
			LastValQueryRqtVO lastValQueryRqtVO = gson.fromJson(new String(data), LastValQueryRqtVO.class);
			LastValQueryRespVO lastValQueryRespVO = gson.fromJson(new String(data), LastValQueryRespVO.class);
			
			DevColecDataVO devColecDataVO = new DevColecDataVO();
			List<DevColecDataVO> devColecDataVOs = new ArrayList<DevColecDataVO>();
			
			ColecRowVO colecRowVO = new ColecRowVO();
			List<ColecRowVO> colecRowVOs = new ArrayList<ColecRowVO>();
			
			SnsnDataInfoVO snsnDataInfoVO = new SnsnDataInfoVO();
			List<SnsnDataInfoVO> snsnDataInfoVOs =  new ArrayList<SnsnDataInfoVO>();
			String dataTypeCd_Snsn = "10001003";
			Double snsnVal = 0.7;
			snsnDataInfoVO.setDataTypeCd(dataTypeCd_Snsn);
			snsnDataInfoVO.setSnsnVal(snsnVal);
			snsnDataInfoVOs.add(snsnDataInfoVO);
			
			SttusDataInfoVO sttusDataInfoVO = new SttusDataInfoVO();
			List<SttusDataInfoVO> sttusDataInfoVOs = new ArrayList<SttusDataInfoVO>();
			String dataTypeCd_sttus = "20001003";
			Double sttusVal = 1.0;
			sttusDataInfoVO.setDataTypeCd(dataTypeCd_sttus);
			sttusDataInfoVO.setSttusVal(snsnVal);
			sttusDataInfoVOs.add(sttusDataInfoVO);
			
			LoDataInfoVO loDataInfoVO = new LoDataInfoVO();
			String dataTypeCd_Lo = "30001003";
			Double latit = 1.0;
			Double lngit = 1.0;
			loDataInfoVO.setDataTypeCd(dataTypeCd_Lo);
			loDataInfoVO.setLatit(latit);
			loDataInfoVO.setLngit(lngit);
			
			BinDataInfoVO binDataInfoVO = new BinDataInfoVO();
			List<BinDataInfoVO> binDataInfoVOs = new ArrayList<BinDataInfoVO>();
			String dataTypeCd_Bin = "40001003";
			byte[] binData = new byte[3];
			binData[0] = (byte) 0x50;
			binData[1] = (byte) 0x51;
			binData[2] = (byte) 0x52;
			binDataInfoVO.setDataTypeCd(dataTypeCd_Bin);
			binDataInfoVO.setBinData(binData);
			binDataInfoVOs.add(binDataInfoVO);
			
			StrDataInfoVO strDataInfoVO = new StrDataInfoVO();
			List<StrDataInfoVO> strDataInfoVOs = new ArrayList<StrDataInfoVO>();
			String snsnTagCd = "60001003";
			String strVal = "";
			strDataInfoVO.setSnsnTagCd(snsnTagCd);
			strDataInfoVO.setStrVal(strVal);
			strDataInfoVOs.add(strDataInfoVO);
			
			DtDataInfoVO dtDataInfoVO = new DtDataInfoVO();
			List<DtDataInfoVO> dtDataInfoVOs = new ArrayList<DtDataInfoVO>();
			String snsnTagCd_Dt = "61000837";
			Date dtVal = new Date();
			dtDataInfoVO.setSnsnTagCd(snsnTagCd_Dt);
			dtDataInfoVO.setDtVal(dtVal);
			dtDataInfoVOs.add(dtDataInfoVO);
			
			EvDataInfoVO evDataInfoVO = new EvDataInfoVO();
			String evOccSysId = "EXAMPLE_LOWSYSTEM";
			String evTyepCd = "0001";
			String evDivId = "1234";
			String evClasCd = "";
			String evOccId = "EXAM_EV_0001";
			String evTrtSttus = "0002";
			evDataInfoVO.setEvOccSysId(evOccSysId);
			evDataInfoVO.setEvTypeCd(evTyepCd);
			evDataInfoVO.setEvDivId(evDivId);
			evDataInfoVO.setEvClasCd(evClasCd);
			evDataInfoVO.setEvOccId(evOccId);
			evDataInfoVO.setEvTrtSttus(evTrtSttus);
			
			GenlSetupDataInfoVO genlSetupDataInfoVO = new GenlSetupDataInfoVO();
			List<GenlSetupDataInfoVO> genlSetupDataInfoVOs = new ArrayList<GenlSetupDataInfoVO>();
			String snsnTagCd_Gen = "”80000739";
			String setupVal = "ON";
			genlSetupDataInfoVO.setSetupVal(setupVal);
			genlSetupDataInfoVO.setSnsnTagCd(snsnTagCd_Gen);
			genlSetupDataInfoVOs.add(genlSetupDataInfoVO);
			
			SclgSetupDataInfoVO sclgSetupDataInfoVO = new SclgSetupDataInfoVO();
			SclgDataInfoVO sclgDataInfoVO = new SclgDataInfoVO();
			SclgTimeDataInfoVO sclgTimeDataInfoVO = new SclgTimeDataInfoVO();
			List<SclgSetupDataInfoVO> sclgSetupDataInfoVOs = new ArrayList<SclgSetupDataInfoVO>();
			List<SclgDataInfoVO> sclgDataInfoVOs = new ArrayList<SclgDataInfoVO>();
			List<SclgTimeDataInfoVO> sclgTimeDataInfoVOs = new ArrayList<SclgTimeDataInfoVO>();
			String stTime = "120000";
			String fnsTime = "165959";
			sclgTimeDataInfoVO.setFnsTime(fnsTime);
			sclgTimeDataInfoVO.setStTime(stTime);
			sclgTimeDataInfoVOs.add(sclgTimeDataInfoVO);
			String dowCd = "";
			sclgDataInfoVO.setDowCd(dowCd);
			sclgDataInfoVO.setSclgTimeDataInfoVOs(sclgTimeDataInfoVOs);
			sclgDataInfoVOs.add(sclgDataInfoVO);
			String snsnTagCd_Scl = "";
			sclgSetupDataInfoVO.setSnsnTagCd(snsnTagCd_Scl);
			sclgSetupDataInfoVO.setSclgDataInfoVOs(sclgDataInfoVOs);
			sclgSetupDataInfoVOs.add(sclgSetupDataInfoVO);
			
			BinSetupDataInfoVO binSetupDataInfoVO = new BinSetupDataInfoVO();
			List<BinSetupDataInfoVO> binSetupDataInfoVos = new ArrayList<BinSetupDataInfoVO>();
			String snsnTagCd_Bin = "90000739";
			byte[] setupVal_Bin = new byte[3];
			binData[0] = (byte) 0x50;
			binData[1] = (byte) 0x51;
			binData[2] = (byte) 0x52;
			binSetupDataInfoVO.setSnsnTagCd(snsnTagCd_Bin);
			binSetupDataInfoVO.setSetupVal(setupVal_Bin);
			binSetupDataInfoVos.add(binSetupDataInfoVO);
			
			colecRowVO.setSnsnDataInfoVOs(snsnDataInfoVOs);
			colecRowVO.setSttusDataInfoVOs(sttusDataInfoVOs);
			colecRowVO.setLoDataInfoVO(loDataInfoVO);
			colecRowVO.setBinDataInfoVOs(binDataInfoVOs);
			colecRowVO.setStrDataInfoVOs(strDataInfoVOs);
			colecRowVO.setDtDataInfoVOs(dtDataInfoVOs);
			colecRowVO.setEvDataInfoVO(evDataInfoVO);
			colecRowVO.setGenlSetupDataInfoVOs(genlSetupDataInfoVOs);
			colecRowVO.setSclgSetupDataInfoVOs(sclgSetupDataInfoVOs);
			colecRowVO.setBinSetupDataInfoVOs(binSetupDataInfoVos);
			
			colecRowVOs.add(colecRowVO);
			
			devColecDataVO.setDevId("");
			devColecDataVO.setColecRowVOs(colecRowVOs);
			devColecDataVOs.add(devColecDataVO);

			lastValQueryRespVO.setExtrSysId(lastValQueryRqtVO.getExtrSysId());
			lastValQueryRespVO.setCmdDataInfoVOs(lastValQueryRqtVO.getCmdDataInfoVOs());
			lastValQueryRespVO.setDevColecDataVOs(devColecDataVOs);
			
			strBody = gson.toJson(lastValQueryRespVO);
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
				groupDevice.setVisible(false);
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
					byte[] header = getHeader(mthdType, 0).toPacket();
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
