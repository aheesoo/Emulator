package com.kt.iot.emul;

//import io.netty.util.internal.StringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.kt.iot.emul.func.vo.FrmwrUdateSttusTrmRqtVO;
import com.kt.iot.emul.func.vo.ItgCnvyDataVO;
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
import com.kt.iot.emul.func.vo.PkgInfoVO;
import com.kt.iot.emul.util.ConvertUtil;
import com.kt.iot.emul.util.StringUtil;
import com.kt.iot.emul.util.Util;
import com.kt.iot.emul.vo.BinDataInfoVO;
import com.kt.iot.emul.vo.BinSetupDataInfoVO;
import com.kt.iot.emul.vo.CmdDataInfoVO;
import com.kt.iot.emul.vo.DevBasVO;
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
	
	public static String getBody(Short value, int devNum, int funNum){
		String	infoUpdTypeCd = "";
		String snsnTag = "";
		String strBody = "";

		String athnRqtNo = Main.athnRqtNo;
		String athnNo = Main.athnNo;
		String extrSysId = Main.extrSystemId;
		String devId = Main.devId;
		String commChId = "GiGA_Home_IoT_TCP";
		
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
			cmdDataInfoVOs.add(getCmdData("2501"));
			
			String modelNm = "ZWAVE002";/** 모델명 */
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
			Integer	m2mSvcNo = 0;/** M2M서비스번호 */
			/** 장치정보목록 */
			DevBasVO devBasVO = new DevBasVO();
			List<DevBasVO> devBasVOs = new ArrayList<DevBasVO>();
			devBasVO.setAthnRqtNo(athnRqtNo);
			devBasVO.setAthnNo(athnNo);
			devBasVO.setDevId(devId);
			devBasVO.setM2mSvcNo(m2mSvcNo);
			devBasVO.setExtrSysId(extrSysId); 
			System.out.println(" devNum -> "+devNum+ " / funNum -> "+funNum);
			if(devNum == 0 && funNum == 0){//IoT GW repair 진행 후 연결상태 전달
				infoUpdTypeCd = "13";
				snsnTag = "82000001";
			}else if(devNum == 0 && funNum == 1){//IoT GW 공장초기화
				infoUpdTypeCd = "11";
			}else if(devNum == 1 && funNum == 0){//도어락 등록
				infoUpdTypeCd = "02";
			}else if(devNum == 1 && funNum == 1){//도어락 삭제(추가)
				infoUpdTypeCd = "21";
			}else if(devNum == 1 && funNum == 2){//사용자 등록 통보 
				infoUpdTypeCd = "13";
				snsnTag = "6303";
			}else if(devNum == 1 && funNum == 3){//사용자 삭제 통보
				infoUpdTypeCd = "13";
				snsnTag = "6303";
			}else if(devNum == 1 && funNum == 9){//도어락 초기화 통보
				infoUpdTypeCd = "11";
				snsnTag = "5A01";
			}else if(devNum == 1 && funNum == 12){//도어락 방범 보드 설정 / 해제 통보
				infoUpdTypeCd = "13";
				snsnTag = "7100";
			}else if(devNum == 2 && funNum == 0){//open/close sensor 등록
				infoUpdTypeCd = "02";
			}else if(devNum == 2 && funNum == 1){//open/close sensor 삭제
				infoUpdTypeCd = "21";
			}else if(devNum == 2 && funNum == 5){//open/close sensor 초기화 통보(추가)
				infoUpdTypeCd = "11";
				snsnTag = "5A01";
			}else if(devNum == 3 && funNum == 0){// gas valve 등록
				infoUpdTypeCd = "02";
			}else if(devNum == 3 && funNum == 1){// gas valve 삭제
				infoUpdTypeCd = "21";
			}else if(devNum == 3 && funNum == 4){//Gav valve 초기화 통보(추가)
				infoUpdTypeCd = "11";
				snsnTag = "5A01";
			}else if(devNum == 3 && funNum == 5){//Timeout 보고
				infoUpdTypeCd = "13";
				snsnTag = "7006";
			}else if(devNum == 3 && funNum == 6){//Remainning 보고
				infoUpdTypeCd = "13";
				snsnTag = "7006";
			}
			
			if(snsnTag != ""){
				devBasVO.getBinSetupDataInfoVOs().add(getBinSetupData(snsnParam, snsnTag));
//				List<BinSetupDataInfoVO> binSetupDataInfoVOs = new ArrayList<BinSetupDataInfoVO>();
//				binSetupDataInfoVOs.add(getBinSetupData(snsnParam, snsnTag));
//				devBasVO.setBinSetupDataInfoVOs(binSetupDataInfoVOs);
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
			
			if(devNum == 1 && funNum == 4){//도어출입 통보(unlock)
				snsnTag = "7105";
			}else if(devNum == 1 && funNum == 5){//도어 출입 통보(lock)
				snsnTag = "7105";
			}else if(devNum == 1 && funNum == 6){//도어락 상태 통보
				snsnTag = "6203";
			}else if(devNum == 1 && funNum == 7){//도어락 비상 통보1
				snsnTag = "7105";
			}else if(devNum == 1 && funNum == 8){//도어락 비상 통보2
				snsnTag = "7105";
			}else if(devNum == 1 && funNum == 10){//도어락 PW 입력 오류 통보
				snsnTag = "7105";
			}else if(devNum == 1 && funNum == 11){//도어락 장시간 문열림 통보
				snsnTag = "7100";
			}else if(devNum == 1 && funNum == 13){// row battery 통보
				snsnTag = "8003";
			}else if(devNum == 2 && funNum == 2){// open/close sensor 상태 통보
				snsnTag = "8003";
			}else if(devNum == 2 && funNum == 3){// open/close sensor 감지 통보
				snsnTag = "7105";
			}else if(devNum == 2 && funNum == 4){// row battery 통보
				snsnTag = "8003";
			}else if(devNum == 3 && funNum == 2){// gas valve 상태 통보
				snsnTag = "2503";
			}else if(devNum == 3 && funNum == 3){// gas valuve 동작 통보
				snsnTag = "2503";
			}else if(devNum == 3 && funNum == 7){// 과열(overheat) 보고
				snsnTag = "7105";
			}
			
			colecRowVOs.add(getReqColecRowVO(colecRowVO, snsnTag, snsnParam));
			
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
			
			/*frmwrUdateSttusTrmRqtVO.setExtrSysId(extrSysId);
			frmwrUdateSttusTrmRqtVO.setDevId(devId);
			frmwrUdateSttusTrmRqtVO.setFrmwrSeq(frmwrSeq);
			frmwrUdateSttusTrmRqtVO.setFrmwrFilePathNm(frmwrFilePathNm);
			frmwrUdateSttusTrmRqtVO.setOccDt(occDt);
			frmwrUdateSttusTrmRqtVO.setSttusCd(sttusCd);
			
			pkgInfoVO.setPkgSeq(pkgSeq);
			pkgInfoVO.setPkgFilePathNm(pkgFilePathNm);
			pkgInfoVO.setPkgNm(pkgNm);
			pkgInfoVO.setPkgVer(pkgVer);
			pkgInfoVO.setPkgVerNo(pkgVerNo);
			pkgInfoVO.setPkgSize(pkgSize);*/
			frmwrUdateSttusTrmRqtVO.getPkgInfoVOs().add(pkgInfoVO);
			
			strBody = gson.toJson(frmwrUdateSttusTrmRqtVO);
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
					binSetupDataInfoVOs.add(getBinSetupData(snsnTagValue, dataTypeCd));
				}else if("7005".equals(dataTypeCd)){//Timeout 조회, Remainning 조회
					snsnTagValue = Main.tag7005;
					binSetupDataInfoVOs.add(getBinSetupData(dataTypeCd, snsnTagValue));
				}
			}
			
			List<DevBasVO> devBasVOs = new ArrayList<DevBasVO>();
			for(String devId : devInfoRetvRqtVO.getInclDevIds()){
				DevBasVO devBasVO = new DevBasVO();
				devBasVO.setDevId(devId);
				devBasVO.setBinSetupDataInfoVOs(binSetupDataInfoVOs);
				devBasVO.setExtrSysId(devInfoRetvRqtVO.getExtrSysId());
				devBasVO.setM2mSvcNo(devInfoRetvRqtVO.getM2mSvcNo());
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

			for(CmdDataInfoVO cmdDataInfoVO : lastValQueryRqtVO.getCmdDataInfoVOs()){
				String dataTypeCd = cmdDataInfoVO.getDataTypeCd();
				ColecRowVO colecRowVO = new ColecRowVO();
				System.out.println("dataTypeCd -----> "+dataTypeCd);
				if("50000008".equals(dataTypeCd)){//wifi 상태 조회
					snsnTagValue = Main.tag50000008;
					colecRowVOs.add(getColecRowVO(colecRowVO, dataTypeCd, snsnTagValue));
				}else if("31996202".equals(dataTypeCd) ||"6202".equals(dataTypeCd)){//도어락 상태 확인- 장치상태
					snsnTagValue = Main.tag6202;
					colecRowVOs.add(getColecRowVO(colecRowVO, "6203",snsnTagValue));
				}else if("31998002".equals(dataTypeCd) || "8002".equals(dataTypeCd)){//도어락 상태 확인 - 배터리 / Gas valve 상태 확인 - 배터리
					snsnTagValue = Main.tag8002;
					colecRowVOs.add(getColecRowVO(colecRowVO, "8003", snsnTagValue));
				}else if("2502".equals(dataTypeCd)){//Gas valve 상태 확인 - 장치상태
					snsnTagValue = Main.tag2502;
					colecRowVOs.add(getColecRowVO(colecRowVO, "2503", snsnTagValue));
				}else{
					
				}
			}
			
//			devColecDataVO.setDevId("HGD_00112233_KT_IOT_GATEWAY1");
			devColecDataVO.setColecRowVOs(colecRowVOs);
			devColecDataVOs.add(devColecDataVO);

			lastValQueryRespVO.setExtrSysId(lastValQueryRqtVO.getExtrSysId());
//			lastValQueryRespVO.setCmdDataInfoVOs(lastValQueryRqtVO.getCmdDataInfoVOs());
//			lastValQueryRespVO.setCmdDataInfoVOs(cmdDataInfoVOs);
			lastValQueryRespVO.setDevColecDataVOs(devColecDataVOs);
			
			strBody = gson.toJson(lastValQueryRespVO);
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
	
	private static ColecRowVO getColecRowVO(ColecRowVO colecRowVO, String snsnTagValue, String snsnParam){
		
		List<BinDataInfoVO> binDataInfoVOs = new ArrayList<BinDataInfoVO>();
		binDataInfoVOs.add(getBinData(snsnTagValue, snsnParam));
		
		colecRowVO.setBinDataInfoVOs(binDataInfoVOs);
		
		return colecRowVO;
	}
	
private static ColecRowVO getReqColecRowVO(ColecRowVO colecRowVO, String snsnTag, String snsnParam){
		
	/*SnsnDataInfoVO snsnDataInfoVO = new SnsnDataInfoVO();
		List<SnsnDataInfoVO> snsnDataInfoVOs =  new ArrayList<SnsnDataInfoVO>();
		String dataTypeCd_Snsn = "10001003";
		Double snsnVal = 0.7;
		snsnDataInfoVO.setDataTypeCd(dataTypeCd_Snsn);
		snsnDataInfoVO.setSnsnVal(snsnVal);
		snsnDataInfoVOs.add(snsnDataInfoVO);*/
		
		/*SttusDataInfoVO sttusDataInfoVO = new SttusDataInfoVO();
		List<SttusDataInfoVO> sttusDataInfoVOs = new ArrayList<SttusDataInfoVO>();
		String dataTypeCd_sttus = "20001003";
		Double sttusVal = 1.0;
		sttusDataInfoVO.setDataTypeCd(dataTypeCd_sttus);
		sttusDataInfoVO.setSttusVal(sttusVal);
		sttusDataInfoVOs.add(sttusDataInfoVO);*/
		
		/*LoDataInfoVO loDataInfoVO = new LoDataInfoVO();
		String dataTypeCd_Lo = "30001003";
		Double latit = 1.0;
		Double lngit = 1.0;
		loDataInfoVO.setDataTypeCd(dataTypeCd_Lo);
		loDataInfoVO.setLatit(latit);
		loDataInfoVO.setLngit(lngit);*/
		
		List<BinDataInfoVO> binDataInfoVOs = new ArrayList<BinDataInfoVO>();
//		binDataInfoVOs.add(getBinData(snsnTag, snsnParam));
		if(snsnParam.equals("22")){
			for(int i=0; i<2; i++){
				binDataInfoVOs.add(getBinData22(snsnTag, String.valueOf(i)));
			}
		}else{
			binDataInfoVOs.add(getBinData(snsnTag, snsnParam));
		}
		
		/*StrDataInfoVO strDataInfoVO = new StrDataInfoVO();
		List<StrDataInfoVO> strDataInfoVOs = new ArrayList<StrDataInfoVO>();
		String snsnTagCd = "60001003";
		String strVal = "";
		strDataInfoVO.setSnsnTagCd(snsnTagCd);
		strDataInfoVO.setStrVal(strVal);
		strDataInfoVOs.add(strDataInfoVO);*/
		
		/*DtDataInfoVO dtDataInfoVO = new DtDataInfoVO();
		List<DtDataInfoVO> dtDataInfoVOs = new ArrayList<DtDataInfoVO>();
		String snsnTagCd_Dt = "61000837";
		Date dtVal = new Date();
		dtDataInfoVO.setSnsnTagCd(snsnTagCd_Dt);
		dtDataInfoVO.setDtVal(dtVal);
		dtDataInfoVOs.add(dtDataInfoVO);
		
		EvDataInfoVO evDataInfoVO = new EvDataInfoVO();
		String evOccSysId = "GiGA_Home_IoT";
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
		String snsnTagCd_Gen = "80000739";
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
		sclgSetupDataInfoVOs.add(sclgSetupDataInfoVO);*/
		
		List<BinSetupDataInfoVO> binSetupDataInfoVos = new ArrayList<BinSetupDataInfoVO>();
		binSetupDataInfoVos.add(getBinSetupData("", ""));
		
//		colecRowVO.setSnsnDataInfoVOs(snsnDataInfoVOs);
//		colecRowVO.setSttusDataInfoVOs(sttusDataInfoVOs);
//		colecRowVO.setLoDataInfoVO(loDataInfoVO);
		colecRowVO.setBinDataInfoVOs(binDataInfoVOs);
//		colecRowVO.setStrDataInfoVOs(strDataInfoVOs);
//		colecRowVO.setDtDataInfoVOs(dtDataInfoVOs);
//		colecRowVO.setEvDataInfoVO(evDataInfoVO);
//		colecRowVO.setGenlSetupDataInfoVOs(genlSetupDataInfoVOs);
//		colecRowVO.setSclgSetupDataInfoVOs(sclgSetupDataInfoVOs);
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
		
		return cnvyRow;
	}
	
	public static BinDataInfoVO getBinData(String cd, String snsnParam){
		StringUtil stringUtil = new StringUtil();
		BinDataInfoVO binDataInfoVO = new BinDataInfoVO();
		byte[] binData;
		
		if("14".equals(snsnParam)){ //도어 출입 통보(unlock)
			binData = new byte[1];
//			binData[0] = 0x71;//Command Class = COMMAND_CLASS_NOTIFICATION
//			binData[1] = 0x05;//Command = NOTIFICATION_REPORT
//			binData[2] = 0x00;//V1 Alarm Type
//			binData[3] = 0x00;//V1 Alarm Level
//			binData[4] = 0x00;//Reserved 
//			binData[5] = 0x00;//Notification Status
//			binData[6] = 0x06;//Notification Type -> Access Control
			binData[0] = getHextoByteDoor(Main.isDoorLock);//0x16;//Event -> Window/Door is open
			
			binDataInfoVO.setBinData(binData);
		}else if("15".equals(snsnParam)){ //도어 출입 통보(lock)
			binData = new byte[1];
//			binData[0] = 0x71;//Command Class = COMMAND_CLASS_NOTIFICATION
//			binData[1] = 0x05;//Command = NOTIFICATION_REPORT
//			binData[2] = 0x00;//V1 Alarm Type
//			binData[3] = 0x00;//V1 Alarm Level
//			binData[4] = 0x00;//Reserved 
//			binData[5] = 0x00;//Notification Status
//			binData[6] = 0x06;//Notification Type -> Access Control
			binData[0] = getHextoByteDoor(Main.isDoorLock);//0x17;//Event -> Window/Door is open
			
			binDataInfoVO.setBinData(binData);
		}else if("17".equals(snsnParam)){ // 도어락 비상통보1
			binData = new byte[1];
//			binData[0] = 0x71;//Command Class = COMMAND_CLASS_NOTIFICATION
//			binData[1] = 0x05;//Command = NOTIFICATION_REPORT
//			binData[2] = 0x00;//V1 Alarm Type
//			binData[3] = 0x00;//V1 Alarm Level
//			binData[4] = 0x00;//Reserved 
//			binData[5] = 0x00;//Notification Status
//			if(Main.isDoorEc1 == 2){
//				binData[6] = 0x0A;
//			}else{
//				binData[6] = 0x07;
//			}
			binData[0] = getEc2HexByte(Main.isDoorEc1);//Event -> Tampering, Product covering removed
			
			binDataInfoVO.setBinData(binData);
		}else if("18".equals(snsnParam)){ // 도어락 비상통보2
			binData = new byte[1];
//			binData[0] = 0x71;//Command Class = COMMAND_CLASS_NOTIFICATION
//			binData[1] = 0x05;//Command = NOTIFICATION_REPORT
//			binData[2] = 0x00;//V1 Alarm Type
//			binData[3] = 0x00;//V1 Alarm Level
//			binData[4] = 0x00;//Reserved 
//			binData[5] = 0x00;//Notification Status
//			binData[6] = 0x07;//Notification Type -> Home Security
			binData[0] = getEc2HexByte(Main.isDoorEc2);//Event -> Unknow Location
			
			binDataInfoVO.setBinData(binData);
		}else if("110".equals(snsnParam)){ // 도어락 PW 입력 오류 통보
			binData = new byte[1];
//			binData[0] = 0x71;//Command Class = COMMAND_CLASS_NOTIFICATION
//			binData[1] = 0x05;//Command = NOTIFICATION_REPORT
//			binData[2] = 0x00;//V1 Alarm Type
//			binData[3] = 0x00;//V1 Alarm Level
//			binData[4] = 0x00;//Reserved 
//			binData[5] = 0x00;//Notification Status
//			binData[6] = 0x07;//Notification Type -> Home Security
			binData[0] = 0x04;//Event -> Invalid Code
			
			binDataInfoVO.setBinData(binData);
		}else if("113".equals(snsnParam)){ // row battery 통보
			binData = new byte[1];
//			binData[0] = (byte)((1 >>> 16) & 0x80);//Command Class = COMMAND_CLASS_BATTERY
//			binData[1] = 0x03;//Command = BATTERY_REPORT
			binData[0] = getHexByte(Main.batteryLev);//0x64;//battery level
			
			binDataInfoVO.setBinData(binData);
		}else if("22".equals(snsnParam)){ // open/close sensor 상태 통보
			binData = new byte[1];
//			binData[0] = 0x71;//Command Class = COMMAND_CLASS_NOTIFICATION
//			binData[1] = 0x05;//Command = NOTIFICATION_REPORT
//			binData[2] = 0x00;//V1 Alarm Type
//			binData[3] = 0x00;//V1 Alarm Level
//			binData[4] = 0x00;//Reserved 
//			binData[5] = 0x00;//Notification Status
//			binData[6] = 0x06;//Notification Type -> Home Security
			binData[0] = getHextoByteDoor(Main.isDoorLock);//0x17;//Event -> closed
			
			binDataInfoVO.setBinData(binData);
		}else if("23".equals(snsnParam)){ // open/close sensor 감지 통보
			binData = new byte[1];
//			binData[0] = 0x71;//Command Class = COMMAND_CLASS_NOTIFICATION
//			binData[1] = 0x05;//Command = NOTIFICATION_REPORT
//			binData[2] = 0x00;//V1 Alarm Type
//			binData[3] = 0x00;//V1 Alarm Level
//			binData[4] = 0x00;//Reserved 
//			binData[5] = 0x00;//Notification Status
//			binData[6] = 0x08;//Notification Type -> Power Namangement
			binData[0] = getHextoByteDoor(Main.isDoorLock);//0x16;//Event -> open
			
			binDataInfoVO.setBinData(binData);
		}else if("24".equals(snsnParam)){ // row battery 통보
			binData = new byte[1];
//			binData[0] = (byte)((1 >>> 16) & 0x80);//Command Class = COMMAND_CLASS_BATTERY
//			binData[1] = 0x03;//Command = BATTERY_REPORT
			binData[0] = getHexByte(Main.batteryLev);//betLev;//battery level
			
			binDataInfoVO.setBinData(binData);
		}else if("32".equals(snsnParam)){ // gas valve 상태 통보
			binData = new byte[1];
//			binData[0] = 0x25;//Command Class = COMMAND_CLASS_SWITCH_BINARY
//			binData[1] = 0x03;//Command = SWITCH_BINARY_SET
			binData[0] = getHextoByteGas(Main.isGasSt);//value
			
			binDataInfoVO.setBinData(binData);
		}else if("33".equals(snsnParam)){ // gas valve 동작 통보
			binData = new byte[1];
//			binData[0] = 0x25;//Command Class = COMMAND_CLASS_SWITCH_BINARY
//			binData[1] = 0x03;//Command = SWITCH_BINARY_SET
			binData[0] = getHextoByteGas(Main.isGasSt);//0x00;//value
			binDataInfoVO.setBinData(binData);
		}else if("50000008".equals(cd)){ //요청에 대한 응답-  wifi 상태 조회
//			binData = new byte[1];aaa
//			binData[0] = 0x62;//Command Class = COMMAND_CLASS_DOOR_LOCK
//			binData[1] = 0x03;//Command = DOOR_LOCK_OPERATION_SET
//			binData[0] = stringUtil.hexToByteArray(cd);//Door Lock Mode
			binData = stringUtil.hexToByteArray(snsnParam);
			binDataInfoVO.setBinData(binData);
		}else if("6203".equals(cd)){ // 요청에 대한 응답 - 도어락 상태 확인- 장치상태
//			binData = new byte[1];
//			binData[0] = (byte)((1 >>> 16) & 0x80);//Command Class = COMMAND_CLASS_DOOR_LOCK
//			binData[1] = 0x03;//Command = DOOR_LOCK_OPERATION_SET
//			binData[0] = getHexByte(Main.batteryLev);
			binData = stringUtil.hexToByteArray(snsnParam);
			binDataInfoVO.setBinData(binData);
		}else if("8003".equals(cd)){ // 도어락 상태 확인 - 배터리 / Gas valve 상태 확인 - 배터리
//			binData = new byte[1];
//			binData[0] = 0x25;//Command Class = COMMAND_CLASS_SWITCH_BINARY
//			binData[1] = 0x03;//Command = SWITCH_BINARY_SET
//			binData[0] = 0x00;//value
			binData = stringUtil.hexToByteArray(snsnParam);
			binDataInfoVO.setBinData(binData);
		}else if("2503".equals(cd)){ // 요청에 대한 응답 - Gas valve 상태 확인 - 장치상태
//			binData = new byte[1];
//			binData[0] = 0x25;//Command Class = COMMAND_CLASS_SWITCH_BINARY
//			binData[1] = 0x03;//Command = SWITCH_BINARY_SET
//			binData[0] = 0x00;//value
			binData = stringUtil.hexToByteArray(snsnParam);
			binDataInfoVO.setBinData(binData);
		}else{//16, 111, 37, res01
			binData = new byte[0];
		}

		binDataInfoVO.setDataTypeCd(cd);

		getMainTagVal(cd, binData);
		
		return binDataInfoVO;
	}
	
	public static BinDataInfoVO getBinData22(String cd, String snsnParam){
		BinDataInfoVO binDataInfoVO = new BinDataInfoVO();
		byte[] binData;
		
		if("0".equals(snsnParam)){ // open/close sensor 상태 통보
			cd = "7105";
			binData = new byte[1];
//			binData[0] = 0x71;//Command Class = COMMAND_CLASS_NOTIFICATION
//			binData[1] = 0x05;//Command = NOTIFICATION_REPORT
//			binData[2] = 0x00;//V1 Alarm Type
//			binData[3] = 0x00;//V1 Alarm Level
//			binData[4] = 0x00;//Reserved 
//			binData[5] = 0x00;//Notification Status
//			binData[6] = 0x06;//Notification Type -> Home Security
			binData[0] = getHextoByteDoor(Main.isDoorLock);//0x17;//Event -> closed
			
			binDataInfoVO.setBinData(binData);
		}else if("1".equals(snsnParam)){ // row battery 통보
			binData = new byte[1];
//			binData[0] = (byte)((1 >>> 16) & 0x80);//Command Class = COMMAND_CLASS_BATTERY
//			binData[1] = 0x03;//Command = BATTERY_REPORT
			binData[0] = getHexByte(Main.batteryLev);//betLev;//battery level
			
			binDataInfoVO.setBinData(binData);
		}else{
			binData = new byte[0];
		}

		binDataInfoVO.setDataTypeCd(cd);

		getMainTagVal(cd, binData);
		
		return binDataInfoVO;
	}
	
	public static BinSetupDataInfoVO getBinSetupData(String snsnParam, String snsnTag){
		BinSetupDataInfoVO binSetupDataInfoVO = new BinSetupDataInfoVO();

		if(snsnTag != ""){
			binSetupDataInfoVO.setSnsnTagCd(snsnTag);
		}
		byte[] setupVal;
		
		if("12".equals(snsnParam)){ // set parameter : userId, userCode, userStatus 사용자 등록 통보
			String userId = Main.userId;
			String userPW = Main.userPW;
			int userPwLen = userPW.length();
			
			setupVal = new byte[4+userPwLen];
			/*setupVal[0] = 0x63;//Command Class = COMMAND_CLASS_USER_CODE
			setupVal[1] = 0x03;//Command = USER_CODE_SET
			setupVal[2] = 0x01;//User Identifier
			setupVal[3] = 0x00;//User ID Status
			setupVal[4] = 0x30;//USER_CODE1 0
			setupVal[5] = 0x31;//USER_CODE2 1
			setupVal[6] = 0x32;//USER_CODE3 2
			setupVal[7] = 0x33;//USER_CODE4 3
*/
			setupVal = setUserByte(userId, userPW, userPwLen);
			binSetupDataInfoVO.setSetupVal(setupVal);
		}else if("13".equals(snsnParam)){ // set parameter : userId, userCode, userStatus 사용자 삭제 통보
			String userId = Main.userId;
			String userPW = Main.userPW;
			int userPwLen = userPW.length();
			setupVal = new byte[4+userPwLen];
			setupVal = setUserByte(userId, userPW, userPwLen);
			binSetupDataInfoVO.setSetupVal(setupVal);
		}else if("31000008".equals(snsnTag)){ // Iot단말 연결상태 조회
			binSetupDataInfoVO.setSnsnTagCd(snsnTag);
			setupVal = StringUtil.hexToByteArray(snsnParam);
			binSetupDataInfoVO.setSetupVal(setupVal);
		}else if("7005".equals(snsnTag)){ // Timeout 조회
			binSetupDataInfoVO.setSnsnTagCd(snsnTag);
			setupVal = StringUtil.hexToByteArray(snsnParam);
			binSetupDataInfoVO.setSetupVal(setupVal);
		}else{		//00, 01, 10, 11, 19, 112, 20, 21, 25, 30, 31, 34, 35, 36, res05 -> parameter 없음
			setupVal = new byte[0];
		}
		
		getMainTagVal(snsnTag, setupVal);

		return binSetupDataInfoVO;
	}
	
	public static CmdDataInfoVO getCmdData(String cd){
		CmdDataInfoVO cmdDataInfoVO = new CmdDataInfoVO();
		List<CmdDataInfoVO> cmdDataInfoVOs = new ArrayList<CmdDataInfoVO>();/** 명령데이터리스트(31) */
		String dataTypeCd = cd;
		byte[] cmdData = new byte[0];
		cmdData = dataTypeCd.getBytes();
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
	
	public static byte getHextoByteDoor(int code){
		byte result = 0;
		if(code == 0){
			result = 0x16;
		}else if(code == 1){
			result = 0x17;
		}
		return result;
	}
	public static byte[] setUserByte(String userId, String userPW, int byteArrayLen){
		
		byte[] setVal = new byte[2+byteArrayLen];
		
//		setVal[0] = 0x63;//Command Class = COMMAND_CLASS_USER_CODE
//		setVal[1] = 0x03;//Command = USER_CODE_SET
//		setVal[2] = stringToHex0x(Main.userId).getBytes();//0x01;//User Identifier
		System.arraycopy(stringToHex0x(Main.userId).getBytes(), 0, setVal, 0, 1);
		setVal[1] = 0x00;//User ID Status	
		System.arraycopy(stringToHex0x(Main.userPW).getBytes(), 0, setVal, 2, byteArrayLen);
		
		return setVal;
		
	}
	public static String stringToHex0x(String s) {
	    String result = "";

	    for (int i = 0; i < s.length(); i++) {
	      result += String.format("0x%02X ", (int) s.charAt(i));
	    }

	    return result;
	  }
	
	public static byte getEc1HexByte(int code){
		byte result = 0;
		if(code == 0){
			result = 0x02;
		}else if(code == 1){
			result = 0x03;
		}else if(code == 2){
			result = 0x02;
		}
		return result;
	}
	public static byte getEc2HexByte(int code){
		byte result = 0;
		if(code == 0){
			result = 0x02;
		}else if(code == 1){
			result = 0x03;
		}else if(code == 2){
			result = 0x04;
		}
		return result;
	}
	public static byte getHextoByteGas(int code){
		byte result = 0;
		if(code == 0){
			result = 0x00;
		}else if(code == 1){
			result = (byte)((1 >>> 0) & 0xFF);
		}
		return result;
	}
}
