package com.kt.iot.emul.func.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import com.kt.iot.emul.vo.BinDataInfoVO;
import com.kt.iot.emul.vo.BinSetupDataInfoVO;
import com.kt.iot.emul.vo.CmdDataInfoVO;
import com.kt.iot.emul.vo.ContlDataInfoVO;
import com.kt.iot.emul.vo.DtDataInfoVO;
import com.kt.iot.emul.vo.EvDataInfoVO;
import com.kt.iot.emul.vo.GenlSetupDataInfoVO;
import com.kt.iot.emul.vo.LoDataInfoVO;
import com.kt.iot.emul.vo.SclgSetupDataInfoVO;
import com.kt.iot.emul.vo.SnsnDataInfoVO;
import com.kt.iot.emul.vo.StrDataInfoVO;
import com.kt.iot.emul.vo.SttusDataInfoVO;

/**
 *  통합전달리포트 요청객체
 * @since	: 2014. 11. 5.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2014. 11. 5. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
public class ItgCnvyRprtRqtVO
{
	/** 외부시스템아이디 */
	private String extrSysId;
	/** 헤더확장필드 */
	private HashMap<String, Object> mapHeaderExtension = new HashMap<String, Object>();
	/** 시스템수집데이터리스트 */
	private SysCnvyDataVO sysCnvyDataVO;
	/** 장비수집데이터리스트 */
	private List<DevCnvyDataVO> devCnvyDataVOs = new ArrayList<DevCnvyDataVO>();
	
	/**
	 * @return the extrSysId
	 */
	public String getExtrSysId() {
		return extrSysId;
	}

	/**
	 * @param extrSysId the extrSysId to set
	 */
	public void setExtrSysId(String extrSysId) {
		this.extrSysId = extrSysId;
	}


	/**
	 * @return the mapHeaderExtension
	 */
	public HashMap<String, Object> getMapHeaderExtension() {
		return mapHeaderExtension;
	}

	/**
	 * @param mapHeaderExtension the mapHeaderExtension to set
	 */
	public void setMapHeaderExtension(HashMap<String, Object> mapHeaderExtension) {
		this.mapHeaderExtension = mapHeaderExtension;
	}

	/**
	 * @return the sysCnvyDataVO
	 */
	public SysCnvyDataVO getSysCnvyDataVO() {
		return sysCnvyDataVO;
	}
	/**
	 * @param sysCnvyDataVO the sysCnvyDataVO to set
	 */
	public void setSysCnvyDataVO(SysCnvyDataVO sysCnvyDataVO) {
		this.sysCnvyDataVO = sysCnvyDataVO;
	}
	/**
	 * @return the devCnvyDataVOs
	 */
	public List<DevCnvyDataVO> getDevCnvyDataVOs() {
		return devCnvyDataVOs;
	}

	/**
	 * @param devCnvyDataVOs the devCnvyDataVOs to set
	 */
	public void setDevCnvyDataVOs(List<DevCnvyDataVO> devCnvyDataVOs) {
		this.devCnvyDataVOs = devCnvyDataVOs;
	}

	public static class SysCnvyDataVO
	{
		/** 수집행 리스트 */
		private List<CnvyRow> cnvyRows = new ArrayList<CnvyRow>();
		/**
		 * @return the cnvyRows
		 */
		public List<CnvyRow> getCnvyRows() {
			return cnvyRows;
		}
		/**
		 * @param cnvyRows the cnvyRows to set
		 */
		public void setCnvyRows(List<CnvyRow> cnvyRows) {
			this.cnvyRows = cnvyRows;
		}
	}

	public static class DevCnvyDataVO
	{
		/** M2M 서비스 번호 */
		private Integer m2mSvcNo;
		/** 현장장치아이디 */
		private String devId;

		/** 수집행 리스트 */
		private List<CnvyRow> cnvyRows = new ArrayList<CnvyRow>();
		/**
		 * @return the m2mSvcNo
		 */
		public Integer getM2mSvcNo() {
			return m2mSvcNo;
		}
		/**
		 * @param m2mSvcNo the m2mSvcNo to set
		 */
		public void setM2mSvcNo(Integer m2mSvcNo) {
			this.m2mSvcNo = m2mSvcNo;
		}
		/**
		 * @return the devId
		 */
		public String getDevId() {
			return devId;
		}
		/**
		 * @param devId the devId to set
		 */
		public void setDevId(String devId) {
			this.devId = devId;
		}
		/**
		 * @return the cnvyRows
		 */
		public List<CnvyRow> getcnvyRows() {
			return cnvyRows;
		}
		/**
		 * @param cnvyRows the cnvyRows to set
		 */
		public void setcnvyRows(List<CnvyRow> cnvyRows) {
			this.cnvyRows = cnvyRows;
		}
	}

	public static class CnvyRow
	{
		/** 발생일시 */
		private Date occDt;
		/** 계측데이터리스트(10) */
		private List<SnsnDataInfoVO> snsnDataInfoVOs = new ArrayList<SnsnDataInfoVO>();
		/** 상태데이터리스트(20) */
		private List<SttusDataInfoVO> sttusDataInfoVOs = new ArrayList<SttusDataInfoVO>();
		/** 제어데이터리스트(30) */
		private List<ContlDataInfoVO> contlDataInfoVOs = new ArrayList<ContlDataInfoVO>();
		/** 명령데이터리스트(31) */
		private List<CmdDataInfoVO> cmdDataInfoVOs = new ArrayList<CmdDataInfoVO>();
		/** 위치데이터(40) */
		private LoDataInfoVO loDataInfoVO;
		/** 이진데이터(50) */
		private List<BinDataInfoVO> binDataInfoVOs = new ArrayList<BinDataInfoVO>();
		/** 문자열데이터(60) */
		private List<StrDataInfoVO> strDataInfoVOs = new ArrayList<StrDataInfoVO>();
		/** 일시데이터(61) */
		private List<DtDataInfoVO> dtDataInfoVOs = new ArrayList<DtDataInfoVO>();
		/** 이벤트데이터 */
		private EvDataInfoVO evDataInfoVO;
		/** 일반설정데이터(80) */
		private List<GenlSetupDataInfoVO> genlSetupDataInfoVOs = new ArrayList<GenlSetupDataInfoVO>();
		/** 스케줄설정데이터(81) */
		private List<SclgSetupDataInfoVO> sclgSetupDataInfoVOs = new ArrayList<SclgSetupDataInfoVO>();
		/** 이진설정데이터(82) */
		private List<BinSetupDataInfoVO> binSetupDataInfoVOs = new ArrayList<BinSetupDataInfoVO>();
		/** 행확장필드 */
		private HashMap<String, Object> mapRowExtension = new HashMap<String, Object>();
		public Date getOccDt() {
			return occDt;
		}
		public void setOccDt(Date occDt) {
			this.occDt = occDt;
		}
		public List<SnsnDataInfoVO> getSnsnDataInfoVOs() {
			return snsnDataInfoVOs;
		}
		public void setSnsnDataInfoVOs(List<SnsnDataInfoVO> snsnDataInfoVOs) {
			this.snsnDataInfoVOs = snsnDataInfoVOs;
		}
		public List<SttusDataInfoVO> getSttusDataInfoVOs() {
			return sttusDataInfoVOs;
		}
		public void setSttusDataInfoVOs(List<SttusDataInfoVO> sttusDataInfoVOs) {
			this.sttusDataInfoVOs = sttusDataInfoVOs;
		}
		public List<ContlDataInfoVO> getContlDataInfoVOs() {
			return contlDataInfoVOs;
		}
		public void setContlDataInfoVOs(List<ContlDataInfoVO> contlDataInfoVOs) {
			this.contlDataInfoVOs = contlDataInfoVOs;
		}
		public List<CmdDataInfoVO> getCmdDataInfoVOs() {
			return cmdDataInfoVOs;
		}
		public void setCmdDataInfoVOs(List<CmdDataInfoVO> cmdDataInfoVOs) {
			this.cmdDataInfoVOs = cmdDataInfoVOs;
		}
		public LoDataInfoVO getLoDataInfoVO() {
			return loDataInfoVO;
		}
		public void setLoDataInfoVO(LoDataInfoVO loDataInfoVO) {
			this.loDataInfoVO = loDataInfoVO;
		}
		public List<BinDataInfoVO> getBinDataInfoVOs() {
			return binDataInfoVOs;
		}
		public void setBinDataInfoVOs(List<BinDataInfoVO> binDataInfoVOs) {
			this.binDataInfoVOs = binDataInfoVOs;
		}
		public List<StrDataInfoVO> getStrDataInfoVOs() {
			return strDataInfoVOs;
		}
		public void setStrDataInfoVOs(List<StrDataInfoVO> strDataInfoVOs) {
			this.strDataInfoVOs = strDataInfoVOs;
		}
		public List<DtDataInfoVO> getDtDataInfoVOs() {
			return dtDataInfoVOs;
		}
		public void setDtDataInfoVOs(List<DtDataInfoVO> dtDataInfoVOs) {
			this.dtDataInfoVOs = dtDataInfoVOs;
		}
		public EvDataInfoVO getEvDataInfoVO() {
			return evDataInfoVO;
		}
		public void setEvDataInfoVO(EvDataInfoVO evDataInfoVO) {
			this.evDataInfoVO = evDataInfoVO;
		}
		public List<GenlSetupDataInfoVO> getGenlSetupDataInfoVOs() {
			return genlSetupDataInfoVOs;
		}
		public void setGenlSetupDataInfoVOs(
				List<GenlSetupDataInfoVO> genlSetupDataInfoVOs) {
			this.genlSetupDataInfoVOs = genlSetupDataInfoVOs;
		}
		public List<SclgSetupDataInfoVO> getSclgSetupDataInfoVOs() {
			return sclgSetupDataInfoVOs;
		}
		public void setSclgSetupDataInfoVOs(
				List<SclgSetupDataInfoVO> sclgSetupDataInfoVOs) {
			this.sclgSetupDataInfoVOs = sclgSetupDataInfoVOs;
		}

		public List<BinSetupDataInfoVO> getBinSetupDataInfoVOs() {
			return binSetupDataInfoVOs;
		}
		public void setBinSetupDataInfoVOs(List<BinSetupDataInfoVO> binSetupDataInfoVOs) {
			this.binSetupDataInfoVOs = binSetupDataInfoVOs;
		}
		public HashMap<String, Object> getMapRowExtension() {
			return mapRowExtension;
		}
		public void setMapRowExtension(HashMap<String, Object> mapRowExtension) {
			this.mapRowExtension = mapRowExtension;
		}
	}

}
