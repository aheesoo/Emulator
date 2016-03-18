package com.kt.iot.emul.func.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.iot.emul.vo.BinDataInfoVO;
import com.kt.iot.emul.vo.BinSetupDataInfoVO;
import com.kt.iot.emul.func.vo.ComnRqtVO;
import com.kt.iot.emul.vo.DtDataInfoVO;
import com.kt.iot.emul.vo.EvDataInfoVO;
import com.kt.iot.emul.vo.GenlSetupDataInfoVO;
import com.kt.iot.emul.vo.IntDataInfoVO;
import com.kt.iot.emul.vo.LoDataInfoVO;
import com.kt.iot.emul.vo.RlNumDataInfoVO;
import com.kt.iot.emul.vo.SclgSetupDataInfoVO;
import com.kt.iot.emul.vo.SnsnDataInfoVO;
import com.kt.iot.emul.vo.StrDataInfoVO;
import com.kt.iot.emul.vo.SttusDataInfoVO;

/**
 *
 * @since	: 2015. 4. 24.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2015. 4. 24. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
public class ItgLogDataVO extends ComnRqtVO
{
	/** 외부시스템아이디 */
	private String extrSysId;
	/** 헤더확장필드 */
	private HashMap<String, Object> mapHeaderExtension = new HashMap<String, Object>();
	/** 시스템로그데이터리스트 */
	private SysLogDataVO sysLogDataVO;
	/** 장비로그데이터리스트 */
	private List<DevLogDataVO> devLogDataVOs = new ArrayList<DevLogDataVO>();

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
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
	 * @return the sysLogDataVO
	 */
	public SysLogDataVO getSysLogDataVO() {
		return sysLogDataVO;
	}
	/**
	 * @param sysLogDataVO the sysLogDataVO to set
	 */
	public void setSysLogDataVO(SysLogDataVO sysLogDataVO) {
		this.sysLogDataVO = sysLogDataVO;
	}
	/**
	 * @return the devLogDataVOs
	 */
	public List<DevLogDataVO> getDevLogDataVOs() {
		return devLogDataVOs;
	}

	/**
	 * @param devLogDataVOs the devLogDataVOs to set
	 */
	public void setDevLogDataVOs(List<DevLogDataVO> devLogDataVOs) {
		this.devLogDataVOs = devLogDataVOs;
	}

	public static class SysLogDataVO
	{
		/** 로그행 리스트 */
		private List<LogRowVO> colecRowVOs = new ArrayList<LogRowVO>();
		/**
		 * @return the colecRowVOs
		 */
		public List<LogRowVO> getLogRowVOs() {
			return colecRowVOs;
		}
		/**
		 * @param colecRowVOs the colecRowVOs to set
		 */
		public void setLogRowVOs(List<LogRowVO> colecRowVOs) {
			this.colecRowVOs = colecRowVOs;
		}
	}

	public static class DevLogDataVO
	{
		/** M2M 서비스 번호 */
		private Integer m2mSvcNo;
		/** 현장장치아이디 */
		private String devId;
		/** 장치처리상태 */
		private String devTrtSttusCd;

		/** 로그행 리스트 */
		private List<LogRowVO> colecRowVOs = new ArrayList<LogRowVO>();
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

		public String getDevTrtSttusCd() {
			return devTrtSttusCd;
		}
		public void setDevTrtSttusCd(String devTrtSttusCd) {
			this.devTrtSttusCd = devTrtSttusCd;
		}
		/**
		 * @return the colecRowVOs
		 */
		public List<LogRowVO> getLogRowVOs() {
			return colecRowVOs;
		}
		/**
		 * @param colecRowVOs the colecRowVOs to set
		 */
		public void setLogRowVOs(List<LogRowVO> colecRowVOs) {
			this.colecRowVOs = colecRowVOs;
		}


	}

	public static class LogRowVO
	{
		/** 발생일시 */
		private Date occDt;
		/** 로그유형 */
		private String logType;
		/** 정수값데이터 */
		private List<IntDataInfoVO> intDataInfoVOs = new ArrayList<IntDataInfoVO>();
		/** 실수값데이터 */
		private List<RlNumDataInfoVO> rlNumDataInfoVOs = new ArrayList<RlNumDataInfoVO>();
		/** 이진데이터(50) */
		private List<BinDataInfoVO> binDataInfoVOs = new ArrayList<BinDataInfoVO>();
		/** 문자열데이터(60) */
		private List<StrDataInfoVO> strDataInfoVOs = new ArrayList<StrDataInfoVO>();
		/** 일시데이터(61) */
		private List<DtDataInfoVO> dtDataInfoVOs = new ArrayList<DtDataInfoVO>();

		/** 행확장필드 */
		private HashMap<String, Object> mapRowExtension = new HashMap<String, Object>();
		public Date getOccDt() {
			return occDt;
		}
		public void setOccDt(Date occDt) {
			this.occDt = occDt;
		}
		public String getLogType() {
			return logType;
		}
		public void setLogType(String logType) {
			this.logType = logType;
		}
		public List<IntDataInfoVO> getIntDataInfoVOs() {
			return intDataInfoVOs;
		}
		public void setIntDataInfoVOs(List<IntDataInfoVO> intDataInfoVOs) {
			this.intDataInfoVOs = intDataInfoVOs;
		}
		public List<RlNumDataInfoVO> getRlNumDataInfoVOs() {
			return rlNumDataInfoVOs;
		}
		public void setRlNumDataInfoVOs(List<RlNumDataInfoVO> rlNumDataInfoVOs) {
			this.rlNumDataInfoVOs = rlNumDataInfoVOs;
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
		public HashMap<String, Object> getMapRowExtension() {
			return mapRowExtension;
		}
		public void setMapRowExtension(HashMap<String, Object> mapRowExtension) {
			this.mapRowExtension = mapRowExtension;
		}


	}
}
