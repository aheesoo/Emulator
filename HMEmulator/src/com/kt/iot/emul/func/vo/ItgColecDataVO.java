/**
 * <PRE>
 *  Project : GWCommAgent
 *  Package : com.kt.smcp.gw.ca.gwadaptor.std.vo
 * </PRE>
 * @file   : ItgColecDataVO.java
 * @date   : 2014. 2. 28. 오후 12:03:37
 * @author : 占쌩븝옙占쏙옙
 * @brief  :
 *  변경이력    :
 *        이름     : 일자          : 근거자료   : 변경내용
 *       ------------------------------------
 *        占쌩븝옙占쏙옙  : 2014. 2. 28.       :            : 신규 개발.
 */
package com.kt.iot.emul.func.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.iot.emul.vo.BinDataInfoVO;
import com.kt.iot.emul.vo.BinSetupDataInfoVO;
import com.kt.iot.emul.vo.DtDataInfoVO;
import com.kt.iot.emul.vo.EvDataInfoVO;
import com.kt.iot.emul.vo.GenlSetupDataInfoVO;
import com.kt.iot.emul.vo.LoDataInfoVO;
import com.kt.iot.emul.vo.SclgSetupDataInfoVO;
import com.kt.iot.emul.vo.SnsnDataInfoVO;
import com.kt.iot.emul.vo.StrDataInfoVO;
import com.kt.iot.emul.vo.SttusDataInfoVO;

/**
 * <PRE>
 *  ClassName : ItgColecDataVO
 * </PRE>
 * @version : 1.0
 * @date    : 2014. 2. 28. 오후 12:03:37
 * @author  : 추병조
 * @brief   :
 */
public class ItgColecDataVO extends ComnRqtVO
{
	/** 외부시스템아이디 */
	private String extrSysId;
	/** 헤더확장필드 */
	private HashMap<String, Object> mapHeaderExtension = new HashMap<String, Object>();
	/** 시스템수집데이터리스트 */
	private SysColecDataVO sysColecDataVO;
	/** 장비수집데이터리스트 */
	private List<DevColecDataVO> devColecDataVOs = new ArrayList<DevColecDataVO>();

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
	 * @return the sysColecDataVO
	 */
	public SysColecDataVO getSysColecDataVO() {
		return sysColecDataVO;
	}
	/**
	 * @param sysColecDataVO the sysColecDataVO to set
	 */
	public void setSysColecDataVO(SysColecDataVO sysColecDataVO) {
		this.sysColecDataVO = sysColecDataVO;
	}
	/**
	 * @return the devColecDataVOs
	 */
	public List<DevColecDataVO> getDevColecDataVOs() {
		return devColecDataVOs;
	}

	/**
	 * @param devColecDataVOs the devColecDataVOs to set
	 */
	public void setDevColecDataVOs(List<DevColecDataVO> devColecDataVOs) {
		this.devColecDataVOs = devColecDataVOs;
	}

	public static class SysColecDataVO
	{
		/** 수집행 리스트 */
		private List<ColecRowVO> colecRowVOs = new ArrayList<ColecRowVO>();
		/**
		 * @return the colecRowVOs
		 */
		public List<ColecRowVO> getColecRowVOs() {
			return colecRowVOs;
		}
		/**
		 * @param colecRowVOs the colecRowVOs to set
		 */
		public void setColecRowVOs(List<ColecRowVO> colecRowVOs) {
			this.colecRowVOs = colecRowVOs;
		}
	}

	public static class DevColecDataVO
	{
		/** M2M 서비스 번호 */
		private Integer m2mSvcNo;
		/** 현장장치아이디 */
		private String devId;

		/** 수집행 리스트 */
		private List<ColecRowVO> colecRowVOs = new ArrayList<ColecRowVO>();
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
		 * @return the colecRowVOs
		 */
		public List<ColecRowVO> getColecRowVOs() {
			return colecRowVOs;
		}
		/**
		 * @param colecRowVOs the colecRowVOs to set
		 */
		public void setColecRowVOs(List<ColecRowVO> colecRowVOs) {
			this.colecRowVOs = colecRowVOs;
		}


	}

	public static class ColecRowVO
	{
		/** 발생일시 */
		private Date occDt;
		/** 계측데이터리스트 */
		private List<SnsnDataInfoVO> snsnDataInfoVOs = new ArrayList<SnsnDataInfoVO>();
		/** 상태데이터리스트 */
		private List<SttusDataInfoVO> sttusDataInfoVOs = new ArrayList<SttusDataInfoVO>();
		/** 위치데이터 */
		private LoDataInfoVO loDataInfoVO;
		/** 이진데이터(50) 이전 */
		private BinDataInfoVO binDataInfoVO;
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
		public LoDataInfoVO getLoDataInfoVO() {
			return loDataInfoVO;
		}
		public void setLoDataInfoVO(LoDataInfoVO loDataInfoVO) {
			this.loDataInfoVO = loDataInfoVO;
		}
		public BinDataInfoVO getBinDataInfoVO() {
			return binDataInfoVO;
		}
		public void setBinDataInfoVO(BinDataInfoVO binDataInfoVO) {
			this.binDataInfoVO = binDataInfoVO;
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
