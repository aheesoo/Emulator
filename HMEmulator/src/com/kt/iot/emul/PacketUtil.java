package com.kt.iot.emul;

//import io.netty.util.internal.StringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kt.iot.emul.code.StdSysTcpCode;
import com.kt.iot.emul.code.GwCode.UseYn;
import com.kt.iot.emul.code.StdSysTcpCode.EncdngType;
import com.kt.iot.emul.code.StdSysTcpCode.HdrType;
import com.kt.iot.emul.code.StdSysTcpCode.MsgExchPtrn;
import com.kt.iot.emul.code.StdSysTcpCode.MsgType;
import com.kt.iot.emul.code.StdSysTcpCode.MthdType;
import com.kt.iot.emul.func.vo.CommChAthnRespVO;
import com.kt.iot.emul.func.vo.CommChDtlVO;
import com.kt.iot.emul.func.vo.ComnRespVO;
import com.kt.iot.emul.func.vo.DevCommChAthnRqtVO;
import com.kt.iot.emul.func.vo.DevInfoRetvRespVO;
import com.kt.iot.emul.func.vo.DevInfoRetvRqtVO;
import com.kt.iot.emul.func.vo.DevInfoUdateRprtRqtVO;
import com.kt.iot.emul.func.vo.DevLoginRqtVO;
import com.kt.iot.emul.func.vo.ExtrSysDtlVO;
import com.kt.iot.emul.func.vo.ExtrSysUdateRprtRqtVO;
import com.kt.iot.emul.func.vo.FrmwrUdateNtfyRqtVO;
import com.kt.iot.emul.func.vo.FrmwrUdateSttusTrmRqtVO;
import com.kt.iot.emul.func.vo.ItgCnvyDataVO;
import com.kt.iot.emul.func.vo.ItgLogDataVO;
import com.kt.iot.emul.func.vo.ItgCnvyDataVO.CnvyRowVO;
import com.kt.iot.emul.func.vo.ItgCnvyDataVO.DevCnvyDataVO;
import com.kt.iot.emul.func.vo.ItgCnvyDataVO.SysCnvyDataVO;
import com.kt.iot.emul.func.vo.ItgCnvyRprtRqtVO;
//import com.kt.iot.emul.func.vo.ItgCnvyRprtRqtVO.CnvyRowVO;
import com.kt.iot.emul.func.vo.ItgColecDataVO;
import com.kt.iot.emul.func.vo.LastValQueryRespVO;
import com.kt.iot.emul.func.vo.LastValQueryRqtVO;
import com.kt.iot.emul.func.vo.ItgColecDataVO.ColecRowVO;
import com.kt.iot.emul.func.vo.ItgColecDataVO.DevColecDataVO;
import com.kt.iot.emul.func.vo.ItgColecDataVO.SysColecDataVO;
import com.kt.iot.emul.func.vo.ItgLogDataVO.DevLogDataVO;
import com.kt.iot.emul.func.vo.PkgInfoVO;
import com.kt.iot.emul.util.ConvertUtil;
import com.kt.iot.emul.util.StringUtil;
import com.kt.iot.emul.util.Util;
import com.kt.iot.emul.vo.BinDataInfoVO;
import com.kt.iot.emul.vo.BinSetupDataInfoVO;
import com.kt.iot.emul.vo.CmdDataInfoVO;
import com.kt.iot.emul.vo.DevBasVO;
import com.kt.iot.emul.vo.DevDtlVO;
import com.kt.iot.emul.vo.DtDataInfoVO;
import com.kt.iot.emul.vo.EvDataInfoVO;
import com.kt.iot.emul.vo.GenlSetupDataInfoVO;
import com.kt.iot.emul.vo.IntDataInfoVO;
import com.kt.iot.emul.vo.LoDataInfoVO;
import com.kt.iot.emul.vo.MsgHeadVO;
import com.kt.iot.emul.vo.SclgDataInfoVO;
import com.kt.iot.emul.vo.SclgSetupDataInfoVO;
import com.kt.iot.emul.vo.SclgTimeDataInfoVO;
import com.kt.iot.emul.vo.SnsnDataInfoVO;
import com.kt.iot.emul.vo.StrDataInfoVO;
import com.kt.iot.emul.vo.SttusDataInfoVO;
import com.kt.iot.emul.vo.TcpHdrVO;

public class PacketUtil {

	public static String athnNo;
	private static TcpHdrVO tcpHdrVO = new TcpHdrVO();
	
//	private static String extrSysId = "GiGA_Home_IoT";
	
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
			Main.report(e.toString(), true);
        	e.printStackTrace();
		}
		return tcpHdrVO;
		
	}
	
	public static TcpHdrVO getResRepHeader(StdSysTcpCode.MthdType mthdType, int isRequest, Long trmTrnId){
		String header = "";
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").setPrettyPrinting().create();
		Long trmTransactionId = trmTrnId;
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
			Main.report(e.toString(), true);
        	e.printStackTrace();
		}
		return tcpHdrVO;
		
	}
	public String getTest(){
		System.out.println("123456");
		return "test";
	}
	public static String getBody(Short value, int devNum, int funNum, String snsnTag, String snsnValue){
		
		if(snsnTag == null || snsnValue ==  null){
			snsnTag = "";
			snsnValue = "";
		}
		
		String	infoUpdTypeCd = "";
//		String snsnTag = "";
		String strBody = "";
		String athnRqtNo = Main.athnRqtNo;
		String athnNo = Main.athnNo;
		String extrSysId = Main.extrSystemId;
		String devId = Main.devId;
		String commChId = "GiGA_Home_IoT_TCP";
		System.out.println(" devnum / funnum : "+devNum+ " / "+funNum);
		String snsnParam = String.valueOf(devNum)+String.valueOf(funNum); //parameter to set snsnValue
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").setPrettyPrinting().create();
		MsgHeadVO msgHeadVO = new MsgHeadVO();
		msgHeadVO.setCommChAthnNo(athnNo);
		msgHeadVO.setMethodType("Request");
		

		if(MthdType.ATHN_COMMCHATHN_DEV_TCP.equals(value)){
//			DevCommChAthnRqtVO commChAthnRqtVO = new DevCommChAthnRqtVO();
			DevLoginRqtVO devLoginRqtVO = new DevLoginRqtVO();
			
			
			/*commChAthnRqtVO.setAthnRqtNo(athnRqtNo);
			commChAthnRqtVO.setCommChId(commChId);
			commChAthnRqtVO.setExtrSysId(extrSysId);
			commChAthnRqtVO.setDevId(devId);;
			commChAthnRqtVO.setMsgHeadVO(msgHeadVO);*/
			
			devLoginRqtVO.setAthnRqtNo(athnRqtNo);
			devLoginRqtVO.setDeviceId(devId);
			devLoginRqtVO.setExtrSysId(extrSysId);
			devLoginRqtVO.setMsgHeadVO(msgHeadVO);
			devLoginRqtVO.setDevFrmwrVer("3.0");
			devLoginRqtVO.setDevFrmwrVerNo(3);
			
			strBody = gson.toJson(devLoginRqtVO);
		} 
		else if(MthdType.KEEP_ALIVE_COMMCHATHN_TCP.equals(value)){
			CommChAthnRespVO commChAthnRqtVO = new CommChAthnRespVO();
			strBody = gson.toJson(commChAthnRqtVO);
		} 
		else if(MthdType.INITA_DEV_RETV.equals(value)){ //331 조회
			DevInfoRetvRqtVO devInfoRetvRqtVO = new DevInfoRetvRqtVO();
			
//			String extrSysId = "GiGA_Home_IoT";/** 외부시스템아이디 */
			Integer	m2mSvcNo = 0;/** M2M서비스번호 */
			List<String> inclDevIds = new ArrayList<String>();/** 포함장치아이디목록 */
			inclDevIds.add(Main.devId);
			List<String> excluDevIds = new ArrayList<String>();/** 배타장치아이디목록 */
			excluDevIds.add(Main.devId);
			
			List<CmdDataInfoVO> cmdDataInfoVOs = new ArrayList<CmdDataInfoVO>();/** 명령데이터리스트(31) */
			cmdDataInfoVOs.add(getCmdData(snsnTag, snsnValue));
			
			String modelNm = "ZWAVE002";/** 모델명 */
			String useYn = "Y";/** 사용여부 */
			Date cretDtSt = new Date();/** 생성일시시작 */
			Date cretDtFns = new Date();/** 생성일시종료 */
			Date amdDtSt = new Date();/** 수정일시시작 */
			Date amdDtFns = new Date();/** 수정일시종료 */
			
			devInfoRetvRqtVO.setExtrSysId(extrSysId);
//			devInfoRetvRqtVO.setM2mSvcNo(m2mSvcNo);
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
			Integer	m2mSvcNo = 0;/** M2M서비스번호 */
			/** 장치정보목록 */
			DevBasVO devBasVO = new DevBasVO();
			List<DevBasVO> devBasVOs = new ArrayList<DevBasVO>();
//			devBasVO.setAthnRqtNo(athnRqtNo);
//			devBasVO.setAthnNo(athnNo);
			devBasVO.setDevId(devId);
//			devBasVO.setM2mSvcNo(m2mSvcNo);
			devBasVO.setExtrSysId(extrSysId); 
			System.out.println(" devNum -> "+devNum+ " / funNum -> "+funNum);
			if(devNum == 0 && funNum == 0){//IoT GW repair 진행 후 연결상태 전달
				infoUpdTypeCd = "13";
			}else if(devNum == 0 && funNum == 1){//IoT GW 공장초기화
				infoUpdTypeCd = "11";
			}else if(devNum == 1 && funNum == 0){//도어락 등록
				infoUpdTypeCd = "02";
				devBasVO.setDevId("W_B479A717108702");
				devBasVO.setDevNm("");
				devBasVO.setModelNm("000000030002");
//				devBasVO.setFrmwrVerNo("0304");
				
				Map<String, String> map = new HashMap<String,String>();
//				map.put("HW_VER", "ff");
				map.put("GEN_DEV_CLASS", "10");
				map.put("SPCF_DEV_CLASS", "01");
//				map.put("IST_ICON_TY", "0300");
//				map.put("USER_ICON_TY", "0300");
				
				Iterator<String> iterator = map.keySet().iterator();
				while(iterator.hasNext()){
					String key = (String)iterator.next();
					String val = map.get(key);
					DevDtlVO devDtlVO = new DevDtlVO();
					devDtlVO.setAtribNm(key);
					devDtlVO.setAtribVal(val);
					devBasVO.getDevDtlVOs().add(devDtlVO);
				}
			}else if(devNum == 1 && funNum == 1){//도어락 삭제(추가)
				infoUpdTypeCd = "21";
			}/*else if(devNum == 1 && funNum == 2){//사용자 등록 통보 
				infoUpdTypeCd = "13";
			}else if(devNum == 1 && funNum == 3){//사용자 삭제 통보
				infoUpdTypeCd = "13";
			}else if(devNum == 1 && funNum == 9){//도어락 초기화 통보
				infoUpdTypeCd = "11";
			}*/else if(devNum == 1 && funNum == 9){//도어락 방범 보드 설정 / 해제 통보
				infoUpdTypeCd = "13";
			}else if(devNum == 2 && funNum == 0){//open/close sensor 등록
				infoUpdTypeCd = "02";
				
				devBasVO.setDevId("sensor_test_01");
				devBasVO.setDevNm("Sensor - Notification_01");
				devBasVO.setModelNm("KTH00003");
				devBasVO.setFrmwrVerNo("0304");
				
				Map<String, String> map = new HashMap<String,String>();
				map.put("HW_VER", "ff");
				map.put("GEN_DEV_CLASS", "07");
				map.put("SPCF_DEV_CLASS", "01");
				map.put("IST_ICON_TY", "0c06");
				map.put("USER_ICON_TY", "0c06");
				
				Iterator<String> iterator = map.keySet().iterator();
				while(iterator.hasNext()){
					String key = (String)iterator.next();
					String val = map.get(key);
					DevDtlVO devDtlVO = new DevDtlVO();
					devDtlVO.setAtribNm(key);
					devDtlVO.setAtribVal(val);
					devBasVO.getDevDtlVOs().add(devDtlVO);
				}
				
			}else if(devNum == 2 && funNum == 1){//open/close sensor 삭제
				infoUpdTypeCd = "21";
			}/*else if(devNum == 2 && funNum == 5){//open/close sensor 초기화 통보(추가)
				infoUpdTypeCd = "11";
			}*/else if(devNum == 3 && funNum == 0){// gas valve 등록
				infoUpdTypeCd = "02";
				
				devBasVO.setDevId("valve_test_02");
				devBasVO.setDevNm("Valve - open/close_02");
				devBasVO.setModelNm("KTH00004");
				devBasVO.setFrmwrVerNo("0304");
				
				Map<String, String> map = new HashMap<String,String>();
				map.put("HW_VER", "ff");
				map.put("GEN_DEV_CLASS", "10");
				map.put("SPCF_DEV_CLASS", "06");
				map.put("IST_ICON_TY", "1500");
				map.put("USER_ICON_TY", "1500");
				
				Iterator<String> iterator = map.keySet().iterator();
				while(iterator.hasNext()){
					String key = (String)iterator.next();
					String val = map.get(key);
					DevDtlVO devDtlVO = new DevDtlVO();
					devDtlVO.setAtribNm(key);
					devDtlVO.setAtribVal(val);
					devBasVO.getDevDtlVOs().add(devDtlVO);
				}
				
			}else if(devNum == 3 && funNum == 1){// gas valve 삭제
				infoUpdTypeCd = "21";
			}/*else if(devNum == 3 && funNum == 4){//Gav valve 초기화 통보(추가)
				infoUpdTypeCd = "11";
			}else if(devNum == 3 && funNum == 5){//Timeout 보고
				infoUpdTypeCd = "13";
			}else if(devNum == 3 && funNum == 6){//Remainning 보고
				infoUpdTypeCd = "13";
			}*/
			
			if(!"".equals(snsnTag) && snsnTag != null){
				devBasVO.getBinSetupDataInfoVOs().add(getBinSetupData(snsnTag, snsnValue));
				devBasVOs.add(devBasVO);
			}else{
				devBasVOs.add(devBasVO);
			}

			devInfoUpdateRprtRqtVO.setExtrSysId(extrSysId);
			devInfoUpdateRprtRqtVO.setInfoUpdTypeCd(infoUpdTypeCd);
			devInfoUpdateRprtRqtVO.setDevBasVOs(devBasVOs);
			devInfoUpdateRprtRqtVO.setMsgHeadVO(msgHeadVO);
			strBody = gson.toJson(devInfoUpdateRprtRqtVO);
		}
		else if(MthdType.COLEC_ITGDATA_RECV.equals(value)){//411 데이터수집 request
			ItgColecDataVO itgColecDataVO = new ItgColecDataVO();
			
			/** 외부시스템아이디 */
//			String extrSysId = "EXAMPLE_LOWSYSTEM";
			/** 헤더확장필드 */
			HashMap<String, Object> mapHeaderExtension = new HashMap<String, Object>();
			
			/** rowColData **/
			ColecRowVO colecRowVO = new ColecRowVO();
			List<ColecRowVO> colecRowVOs = new ArrayList<ColecRowVO>();
			
			colecRowVOs.add(getReqColecRowVO(colecRowVO, snsnTag, snsnValue));
			
			/** 시스템수집데이터 **/
			SysColecDataVO sysColecDataVO = new SysColecDataVO();
			sysColecDataVO.setColecRowVOs(colecRowVOs);
			/** 장비수집데이터리스트 */
			DevColecDataVO devColecDataVO = new DevColecDataVO();
			List<DevColecDataVO> devColecDataVOs = new ArrayList<DevColecDataVO>();
//			String devId = "";
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
		else if(MthdType.FRMWR_UDATE_STTUS.equals(value)){//813 펌웨어 업데이트상태 전송
			FrmwrUdateSttusTrmRqtVO frmwrUdateSttusTrmRqtVO = new FrmwrUdateSttusTrmRqtVO();
			PkgInfoVO pkgInfoVO = new PkgInfoVO();
			String sttusCd = "";
			String sttusVal = "1";
			frmwrUdateSttusTrmRqtVO.setExtrSysId(extrSysId);
			frmwrUdateSttusTrmRqtVO.setDevId(devId);
			frmwrUdateSttusTrmRqtVO.setSttusCd(sttusCd);
			frmwrUdateSttusTrmRqtVO.setSttusVal(sttusVal);
			/*frmwrUdateSttusTrmRqtVO.setFrmwrSeq(frmwrSeq);
			frmwrUdateSttusTrmRqtVO.setFrmwrFilePathNm(frmwrFilePathNm);
			frmwrUdateSttusTrmRqtVO.setOccDt(occDt);
			
			pkgInfoVO.setPkgSeq(pkgSeq);
			pkgInfoVO.setPkgFilePathNm(pkgFilePathNm);
			pkgInfoVO.setPkgNm(pkgNm);
			pkgInfoVO.setPkgVer(pkgVer);
			pkgInfoVO.setPkgVerNo(pkgVerNo);
			pkgInfoVO.setPkgSize(pkgSize);*/
			frmwrUdateSttusTrmRqtVO.getPkgInfoVOs().add(pkgInfoVO);
			
			strBody = gson.toJson(frmwrUdateSttusTrmRqtVO);
		}
		else if(MthdType.LOG_ITG_LOG.equals(value)){//821 로그파일 전송
			ItgLogDataVO itgLogDataVO = new ItgLogDataVO();
			itgLogDataVO.setExtrSysId(extrSysId);
			
			
			Date occDt = new Date();
			BinDataInfoVO binDataInfoVO = new BinDataInfoVO();
			List<BinDataInfoVO> binDataInfoVOs = new ArrayList<BinDataInfoVO>();
			byte[] binData = new byte[0];
			binData = StringUtil.hexToByteArray("00000000070900");
			binDataInfoVO.setDataTypeCd("7015");
			binDataInfoVO.setBinData(binData);
			binDataInfoVOs.add(binDataInfoVO);
			
			List<IntDataInfoVO> intDataInfoVOs = new ArrayList<IntDataInfoVO>();
			
			Map<String, Integer> intMap = new HashMap<String,Integer>();
			intMap.put("11", 1544);
			intMap.put("-30", 1537);
			intMap.put("-40", 1541);
			intMap.put("3", 1540);
			intMap.put("-50", 1539);
			intMap.put("0", 1538);
			
			Iterator<String> intIterator = intMap.keySet().iterator();
			while(intIterator.hasNext()){
				String key = (String)intIterator.next();
				Integer val = intMap.get(key);
				IntDataInfoVO intDataInfoVO = new IntDataInfoVO();
				intDataInfoVO.setDataTypeCd(key);
				intDataInfoVO.setIntVal(val);
				intDataInfoVOs.add(intDataInfoVO);
			}
			
			List<StrDataInfoVO> strDataInfoVOs = new ArrayList<StrDataInfoVO>();
			
			Map<String, String> strMap = new HashMap<String,String>();
			strMap.put("1563", "test_ssid");
			strMap.put("1542", "test_bssid");
			strMap.put("1543", "test_gw_firm_ver");
			strMap.put("1545", "test_secure-type");
			strMap.put("60000007", "test_gw_firm_ver");
			
			Iterator<String> strIterator = strMap.keySet().iterator();
			while(strIterator.hasNext()){
				String key = (String)strIterator.next();
				String val = strMap.get(key);
				StrDataInfoVO strDataInfoVO = new StrDataInfoVO();
				strDataInfoVO.setSnsnTagCd(key);
				strDataInfoVO.setStrVal(val);
				strDataInfoVOs.add(strDataInfoVO);
			}
			
			ItgLogDataVO.SysLogDataVO sysLogDataVO = new ItgLogDataVO.SysLogDataVO();
			ItgLogDataVO.LogRowVO sysLogRowVO= new ItgLogDataVO.LogRowVO();
			List<ItgLogDataVO.LogRowVO> sysLogRowVOs = new ArrayList<ItgLogDataVO.LogRowVO>();
			
			sysLogRowVO.setOccDt(occDt);
			sysLogRowVO.setBinDataInfoVOs(binDataInfoVOs);
			sysLogRowVO.setIntDataInfoVOs(intDataInfoVOs);
			sysLogRowVOs.add(sysLogRowVO);
			sysLogDataVO.setLogRowVOs(sysLogRowVOs);
			
			ItgLogDataVO.DevLogDataVO devLogDataVO = new ItgLogDataVO.DevLogDataVO();
			List<ItgLogDataVO.DevLogDataVO> devLogDataVOs = new ArrayList<ItgLogDataVO.DevLogDataVO>();
			ItgLogDataVO.LogRowVO devLogRowVO= new ItgLogDataVO.LogRowVO();
			List<ItgLogDataVO.LogRowVO> devLogRowVOs = new ArrayList<ItgLogDataVO.LogRowVO>();
			
//			devLogRowVO.setBinDataInfoVOs(binDataInfoVOs);
			devLogRowVO.setStrDataInfoVOs(strDataInfoVOs);
			devLogRowVO.setIntDataInfoVOs(intDataInfoVOs);
			devLogRowVO.setOccDt(occDt);
			devLogRowVO.setLogType("");
			HashMap<String, Object> mapRowExtension = new HashMap<String, Object>();
			mapRowExtension.put("test", "test");
			devLogRowVO.setMapRowExtension(mapRowExtension);
			devLogRowVOs.add(devLogRowVO);
			devLogDataVO.setDevId(devId);
			devLogDataVO.setM2mSvcNo(12345678);
			devLogDataVO.setLogRowVOs(devLogRowVOs);
			devLogDataVOs.add(devLogDataVO);
			
//			itgLogDataVO.setSysLogDataVO(sysLogDataVO);
			itgLogDataVO.setDevLogDataVOs(devLogDataVOs);
			
			strBody = gson.toJson(itgLogDataVO);
		}
		return strBody;
	}
	
	public static String getResBody(Short value, byte[] data){
		String snsnTagValue = "";
		String respCd = "100";
		String respMsg = "SUCCESS";
		
		String strBody = "";
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").setPrettyPrinting().create();
		if(value == 333){
			DevInfoRetvRqtVO devInfoRetvRqtVO = gson.fromJson(new String(data), DevInfoRetvRqtVO.class);
			DevInfoRetvRespVO devInfoRetvRespVO = new DevInfoRetvRespVO();
			
			//** 장치정보목록 */
			
			
			List<BinSetupDataInfoVO> binSetupDataInfoVOs = new ArrayList<BinSetupDataInfoVO>();
			for(CmdDataInfoVO cmdDataInfoVO : devInfoRetvRqtVO.getCmdDataInfoVOs()){
				String dataTypeCd = cmdDataInfoVO.getDataTypeCd();
				
				if("31000008".equals(dataTypeCd)){//Iot 단말 연결 상태 조회
					snsnTagValue = Main.tag31000008;
					binSetupDataInfoVOs.add(getBinSetupData(dataTypeCd, snsnTagValue));
				}/*else if("7005".equals(dataTypeCd)){//Timeout 조회, Remainning 조회
					snsnTagValue = Main.tag7005;
					binSetupDataInfoVOs.add(getBinSetupData(dataTypeCd, snsnTagValue));
				}*/
			}
			
			List<DevBasVO> devBasVOs = new ArrayList<DevBasVO>();
			for(String devId : devInfoRetvRqtVO.getInclDevIds()){
				DevBasVO devBasVO = new DevBasVO();
				devBasVO.setDevId(devId);
				devBasVO.setBinSetupDataInfoVOs(binSetupDataInfoVOs);
				devBasVO.setExtrSysId(devInfoRetvRqtVO.getExtrSysId());
//				devBasVO.setM2mSvcNo(devInfoRetvRqtVO.getM2mSvcNo());
				devBasVOs.add(devBasVO);
			}
			
			devInfoRetvRespVO.setMsgHeadVO(devInfoRetvRqtVO.getMsgHeadVO());
			devInfoRetvRespVO.setRespCd(respCd);
			devInfoRetvRespVO.setRespMsg(respMsg);
			devInfoRetvRespVO.setDevBasVOs(devBasVOs);
			devInfoRetvRespVO.setCmdDataInfoVOs(devInfoRetvRqtVO.getCmdDataInfoVOs());
			strBody = gson.toJson(devInfoRetvRespVO);
		} 
		else if(value == 334){
			DevInfoUdateRprtRqtVO devInfoUdateRprtRqtVO = gson.fromJson(new String(data), DevInfoUdateRprtRqtVO.class);
//			ComnRespVO comnRespVO = gson.fromJson(new String(data), ComnRespVO.class);
			ComnRespVO comnRespVO = new ComnRespVO();
			
			comnRespVO.setRespCd(respCd);
			comnRespVO.setRespMsg(respMsg);
			comnRespVO.setMsgHeadVO(devInfoUdateRprtRqtVO.getMsgHeadVO());
			
			strBody = gson.toJson(comnRespVO);
		}
		else if(MthdType.CONTL_ITGCNVY_DATA.equals(value)){//525 response
			ItgCnvyDataVO itgCnvyDataVO = gson.fromJson(new String(data), ItgCnvyDataVO.class);
			ComnRespVO comnRespVO = new ComnRespVO();
			
			comnRespVO.setRespCd(respCd);
			comnRespVO.setRespMsg(respMsg);
			comnRespVO.setMsgHeadVO(itgCnvyDataVO.getMsgHeadVO());
			
			strBody = gson.toJson(comnRespVO);
		}
		else if(MthdType.QUERY_LASTVAL.equals(value)){ //711
			LastValQueryRqtVO lastValQueryRqtVO = gson.fromJson(new String(data), LastValQueryRqtVO.class);
			LastValQueryRespVO lastValQueryRespVO = new LastValQueryRespVO();
			/*CmdDataInfoVO cmdDataInfoVO = new CmdDataInfoVO();
			List<CmdDataInfoVO> cmdDataInfoVOs = lastValQueryRqtVO.getCmdDataInfoVOs();
			String dataTypeCd = "8002";
			byte[] cmdData = new byte[0];
			cmdData = dataTypeCd.getBytes();
			cmdDataInfoVO.setDataTypeCd(dataTypeCd);
			cmdDataInfoVO.setCmdData(cmdData);
			cmdDataInfoVOs.add(cmdDataInfoVO);*/

			DevColecDataVO devColecDataVO = new DevColecDataVO();
			List<DevColecDataVO> devColecDataVOs = new ArrayList<DevColecDataVO>();
			
			List<ColecRowVO> colecRowVOs = new ArrayList<ColecRowVO>();
			
			List<CmdDataInfoVO> cmdDataInfoVOs = new ArrayList<CmdDataInfoVO>();
			
			for(CmdDataInfoVO cmdDataInfoVO : lastValQueryRqtVO.getCmdDataInfoVOs()){
				String dataTypeCd = cmdDataInfoVO.getDataTypeCd();
				ColecRowVO colecRowVO = new ColecRowVO();
				
				System.out.println("dataTypeCd -----> "+dataTypeCd);
				if("31996202".equals(dataTypeCd) ||"6202".equals(dataTypeCd)){//도어락 상태 확인- 장치상태
					snsnTagValue = Main.tag6202;
					colecRowVOs.add(getColecRowVO(colecRowVO, "6203",snsnTagValue));
				}else if("31998002".equals(dataTypeCd) || "8002".equals(dataTypeCd)){//도어락 상태 확인 - 배터리 / Gas valve 상태 확인 - 배터리
					snsnTagValue = Main.tag8002;
					colecRowVOs.add(getColecRowVO(colecRowVO, "8003", snsnTagValue));
				}else if("2502".equals(dataTypeCd)){//Gas valve 상태 확인 - 장치상태
					snsnTagValue = Main.tag2502;
					colecRowVOs.add(getColecRowVO(colecRowVO, "2503", snsnTagValue));
				}else {
					
				}
			}
			
			for(String devId : lastValQueryRqtVO.getInclDevIds()){
				devColecDataVO.setDevId(devId);
				devColecDataVOs.add(devColecDataVO);
			}
			
//			devColecDataVO.setDevId("HGD_00112233_KT_IOT_GATEWAY1");
			devColecDataVO.setColecRowVOs(colecRowVOs);
//			devColecDataVOs.add(devColecDataVO);

			lastValQueryRespVO.setExtrSysId(lastValQueryRqtVO.getExtrSysId());
			lastValQueryRespVO.setCmdDataInfoVOs(lastValQueryRqtVO.getCmdDataInfoVOs());
			lastValQueryRespVO.setCmdDataInfoVOs(cmdDataInfoVOs);
			lastValQueryRespVO.setDevColecDataVOs(devColecDataVOs);
			
			strBody = gson.toJson(lastValQueryRespVO);
		}
		else if(812 == value){
			FrmwrUdateNtfyRqtVO frmwrUdateNtfyRqtVO = gson.fromJson(new String(data), FrmwrUdateNtfyRqtVO.class);
			ComnRespVO comnRespVO = new ComnRespVO();
			
			comnRespVO.setRespCd(respCd);
			comnRespVO.setRespMsg(respMsg);
			comnRespVO.setMsgHeadVO(frmwrUdateNtfyRqtVO.getMsgHeadVO());
			
			strBody = gson.toJson(comnRespVO);
		}
		else if(821 == value){
			ItgLogDataVO itgLogDataVO = gson.fromJson(new String(data), ItgLogDataVO.class);
			ComnRespVO comnRespVO = new ComnRespVO();
			
			comnRespVO.setRespCd(respCd);
			comnRespVO.setRespMsg(respMsg);
			comnRespVO.setMsgHeadVO(itgLogDataVO.getMsgHeadVO());
			
			strBody = gson.toJson(comnRespVO);
		}
		
		return strBody;
	}
	
	public static String getReptBody(Short value, byte[] data){
		
		String respCd = "100";
		String respMsg = "SUCCESS";
		
		String strBody = "";
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").setPrettyPrinting().create();
		
		if(value == 525){// 수신 데이터(센싱태그 포함) 다시 송신(반환)
			ItgCnvyDataVO itgCnvyDataVO = gson.fromJson(new String(data), ItgCnvyDataVO.class);
			ItgCnvyRprtRqtVO itgCnvyRprtRqtVO = new ItgCnvyRprtRqtVO(); //report object
			
			itgCnvyRprtRqtVO.setExtrSysId(itgCnvyDataVO.getExtrSysId());
			com.kt.iot.emul.func.vo.ItgCnvyRprtRqtVO.SysCnvyDataVO sysCnvyDataVOrept = new com.kt.iot.emul.func.vo.ItgCnvyRprtRqtVO.SysCnvyDataVO();
			//system 
			SysCnvyDataVO sysCnvyDataVO = itgCnvyDataVO.getSysCnvyDataVO();			
			if(sysCnvyDataVO != null){
				for(CnvyRowVO cnvyRowVO : sysCnvyDataVO.getCnvyRowVOs()){
					ItgCnvyRprtRqtVO.CnvyRowVO cnvyRow = toCnvyRow(cnvyRowVO);
					sysCnvyDataVOrept.getCnvyRowVOs().add(cnvyRow);
				}
				itgCnvyRprtRqtVO.setSysCnvyDataVO(sysCnvyDataVOrept);
			}
			
			for(DevCnvyDataVO devyCnvyDataVO : itgCnvyDataVO.getDevCnvyDataVOs()){
				
				ItgCnvyRprtRqtVO.DevCnvyDataVO repDevyCnvyDataVO = new ItgCnvyRprtRqtVO.DevCnvyDataVO();
				
				List<com.kt.iot.emul.func.vo.ItgCnvyRprtRqtVO.CnvyRowVO> devCnvyRows = new ArrayList<com.kt.iot.emul.func.vo.ItgCnvyRprtRqtVO.CnvyRowVO>();
				for(CnvyRowVO devCnvyRowVO : devyCnvyDataVO.getCnvyRowVOs()){
					com.kt.iot.emul.func.vo.ItgCnvyRprtRqtVO.CnvyRowVO devCnvyRow = toCnvyRow(devCnvyRowVO);
					devCnvyRows.add(devCnvyRow);
				}
				repDevyCnvyDataVO.setcnvyRowVOs(devCnvyRows);
				repDevyCnvyDataVO.setDevId(devyCnvyDataVO.getDevId());
				itgCnvyRprtRqtVO.getDevCnvyDataVOs().add(repDevyCnvyDataVO);
			}
			
			strBody = gson.toJson(itgCnvyRprtRqtVO);
		} 
		
		return strBody;
	}
	
	private static ColecRowVO getColecRowVO(ColecRowVO colecRowVO, String snsnTag, String snsnValue){
		
		List<BinDataInfoVO> binDataInfoVOs = new ArrayList<BinDataInfoVO>();
		if(snsnTag != null && !"".equals(snsnTag)){
			if(snsnTag.indexOf("/") > -1){
				String[] snsnTagArray = snsnTag.split("/");
				for(int i=0; i< snsnTagArray.length; i++){
					if(snsnValue.indexOf("/") > -1){
						String[] snsnValueArray = snsnValue.split("/");
						for(int j=0; j<snsnValueArray.length; j++){
							if(i == j){
								binDataInfoVOs.add(getBinData(snsnTagArray[i], snsnValueArray[j]));
							}
						}
					}else{
						binDataInfoVOs.add(getBinData(snsnTagArray[i], snsnValue));
					}
				}
			}
			else{
				binDataInfoVOs.add(getBinData(snsnTag, snsnValue));
			}
		}
//		binDataInfoVOs.add(getBinData(snsnTag, snsnValue));
		colecRowVO.setBinDataInfoVOs(binDataInfoVOs);
		
		return colecRowVO;
	}
	
private static ColecRowVO getReqColecRowVO(ColecRowVO colecRowVO, String snsnTag, String snsnValue){
		
		List<BinDataInfoVO> binDataInfoVOs = new ArrayList<BinDataInfoVO>();
//		binDataInfoVOs.add(getBinData(snsnTag, snsnParam));
		if(snsnTag != null && !"".equals(snsnTag)){
			if(snsnTag.indexOf("/") > -1){
				String[] snsnTagArray = snsnTag.split("/");
				for(int i=0; i< snsnTagArray.length; i++){
					if(snsnValue.indexOf("/") > -1){
						String[] snsnValueArray = snsnValue.split("/");
						for(int j=0; j<snsnValueArray.length; j++){
							if(i == j){
								binDataInfoVOs.add(getBinData(snsnTagArray[i], snsnValueArray[j]));
							}
						}
					}else{
						binDataInfoVOs.add(getBinData(snsnTagArray[i], snsnValue));
					}
//					binDataInfoVOs.add(getBinData(snsnTagArray[i], snsnValue));
				}
			}
			else{
				binDataInfoVOs.add(getBinData(snsnTag, snsnValue));
			}
		}
		
		List<BinSetupDataInfoVO> binSetupDataInfoVos = new ArrayList<BinSetupDataInfoVO>();
		binSetupDataInfoVos.add(getBinSetupData("", ""));
		
		colecRowVO.setBinDataInfoVOs(binDataInfoVOs);
		colecRowVO.setBinSetupDataInfoVOs(binSetupDataInfoVos);
		
		return colecRowVO;
	}
	
	private static com.kt.iot.emul.func.vo.ItgCnvyRprtRqtVO.CnvyRowVO toCnvyRow(CnvyRowVO cnvyRowVO){
		
		com.kt.iot.emul.func.vo.ItgCnvyRprtRqtVO.CnvyRowVO cnvyRow = new com.kt.iot.emul.func.vo.ItgCnvyRprtRqtVO.CnvyRowVO();
		
		List<SnsnDataInfoVO> snsnDataInfoVOs =  cnvyRowVO.getSnsnDataInfoVOs();
		cnvyRow.setSnsnDataInfoVOs(snsnDataInfoVOs);
		
		List<SttusDataInfoVO> sttusDataInfoVOs = cnvyRowVO.getSttusDataInfoVOs();
		cnvyRow.setSttusDataInfoVOs(sttusDataInfoVOs);
		
		LoDataInfoVO loDataInfoVO = cnvyRowVO.getLoDataInfoVO();
		cnvyRow.setLoDataInfoVO(loDataInfoVO);
		
		List<BinDataInfoVO> binDataInfoVOs = cnvyRowVO.getBinDataInfoVOs();
		cnvyRow.setBinDataInfoVOs(binDataInfoVOs);
		
		List<StrDataInfoVO> strDataInfoVOs = cnvyRowVO.getStrDataInfoVOs();
		cnvyRow.setStrDataInfoVOs(strDataInfoVOs);
		
		List<DtDataInfoVO> dtDataInfoVOs = cnvyRowVO.getDtDataInfoVOs();
		cnvyRow.setDtDataInfoVOs(dtDataInfoVOs);
		
		EvDataInfoVO evDataInfoVO = cnvyRowVO.getEvDataInfoVO();
		cnvyRow.setEvDataInfoVO(evDataInfoVO);
		
		List<GenlSetupDataInfoVO> genlSetupDataInfoVOs = cnvyRowVO.getGenlSetupDataInfoVOs();
		cnvyRow.setGenlSetupDataInfoVOs(genlSetupDataInfoVOs);
		
		List<SclgSetupDataInfoVO> sclgSetupDataInfoVOs = cnvyRowVO.getSclgSetupDataInfoVOs();
		cnvyRow.setSclgSetupDataInfoVOs(sclgSetupDataInfoVOs);
		
		List<CmdDataInfoVO> cmdDataInfoVOs = cnvyRowVO.getCmdDataInfoVOs();
		cnvyRow.setCmdDataInfoVOs(cmdDataInfoVOs);
		
		return cnvyRow;
	}
	
	public static BinDataInfoVO getBinData(String snsnTag, String snsnValue){
		StringUtil stringUtil = new StringUtil();
		BinDataInfoVO binDataInfoVO = new BinDataInfoVO();
		byte[] binData;
		
		if(snsnValue != null && !"".equals(snsnValue)){
			binData = stringUtil.hexToByteArray(snsnValue);
			binDataInfoVO.setBinData(binData);
		}else{//16, 111, 37, res01
			binData = new byte[0];
		}

		if(snsnTag == null){
			snsnTag = "";
		}
		binDataInfoVO.setDataTypeCd(snsnTag);

		getMainTagVal(snsnTag, binData);
		
		return binDataInfoVO;
	}
	
	
	public static BinSetupDataInfoVO getBinSetupData(String snsnTag, String snsnValue){
		BinSetupDataInfoVO binSetupDataInfoVO = new BinSetupDataInfoVO();

		byte[] setupVal;
		
		if(snsnTag == null){
			snsnTag = "";
		}
		
		if(!"".equals(snsnValue) && snsnValue != null){
			binSetupDataInfoVO.setSnsnTagCd(snsnTag);
			setupVal = StringUtil.hexToByteArray(snsnValue);
			binSetupDataInfoVO.setSetupVal(setupVal);
		}else{		//00, 01, 10, 11, 19, 112, 20, 21, 25, 30, 31, 34, 35, 36, res05 -> parameter 없음
			setupVal = new byte[0];
		}
		
		getMainTagVal(snsnTag, setupVal);

		return binSetupDataInfoVO;
	}
	
	public static CmdDataInfoVO getCmdData(String snsnTag, String snsnValue){
		CmdDataInfoVO cmdDataInfoVO = new CmdDataInfoVO();
		List<CmdDataInfoVO> cmdDataInfoVOs = new ArrayList<CmdDataInfoVO>();/** 명령데이터리스트(31) */
		String dataTypeCd = snsnTag;
		byte[] cmdData;
		if(snsnValue != null && !"".equals(snsnValue)){
			cmdData = StringUtil.hexToByteArray(snsnValue);
		}else{
			cmdData = new byte[0];
		}
		cmdDataInfoVO.setDataTypeCd(dataTypeCd);
		cmdDataInfoVO.setCmdData(cmdData);

		return cmdDataInfoVO;
	}
	
	public static void getMainTagVal(String tag, byte[] val){
		Main.setTagVal(tag, val);
	}
	
	public static byte getHexByte(int code){
		byte result = 0;
		if(code == 0){
			result = 0x00;
		}else if(code == 1){
			result = 0x0A;
		}else if(code == 2){
			result = 0x14;
		}else if(code == 3){
			result = 0x1E;
		}else if(code == 4){
			result = 0x28;
		}else if(code == 5){
			result = 0x32;
		}else if(code == 6){
			result = 0x3C;
		}else if(code == 7){
			result = 0x46;
		}else if(code == 8){
			result = 0x50;
		}else if(code == 9){
			result = 0x5A;
		}else if(code == 10){
			result = 0x64;
		}
		return result;
	}
	
}
