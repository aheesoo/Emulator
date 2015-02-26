/**
 * <PRE>
 *  Project : GWCommAgent
 *  Package : com.kt.smcp.gw.ca.gwadaptor.sif.colec.vo
 * </PRE>
 * @file   : EvDataInfoVO.java
 * @date   : 2013. 12. 11. 오후 2:48:23
 * @author : byw
 * @brief  :
 *  변경이력    :
 *        이름     : 일자          : 근거자료   : 변경내용
 *       ------------------------------------
 *        byw  : 2013. 12. 11.       :            : 신규 개발.
 */
package com.kt.iot.emul.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * <PRE>
 *  ClassName : EvDataInfoVO
 * </PRE>
 * @version : 1.0
 * @date    : 2013. 12. 11. 오후 2:48:23
 * @author  : byw
 * @brief   : 이벤트데이터 정보 VO
 */

public class EvDataInfoVO
{
	/** 이벤트발생시스템아이디 EV_OCC_SYS_ID */
	private String evOccSysId;
	/** 이벤트유형코드 EV_TYPE_CD */
	private String evTypeCd;
	/** 이벤트구분아이디 EV_DIV_ID */
	private String evDivId;
	/** 이벤트등급코드 EV_CLAS_CD */
	private String evClasCd;
	/** 이벤트발생아이디 EV_OCC_ID */
	private String evOccId;
	/** 이벤트처리상태 EV_TRT_STTUS */
	private String evTrtSttus;
	/** 복합이벤트여부 COMPN_EV_YN */
	private String compnEvYn;
	/** 발생원인이벤트리스트 OCC_CAUSE_EV_LIST */
	private List<String> occCauseEvs = new ArrayList<String>();
	/** 발생장소명 OCC_PLC_NM */
	private String occPlcNm;
	/** 이벤트내용 EV_SBST */
	private String evSbst;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
	/**
	 * @return the evOccSysId
	 */
	public String getEvOccSysId() {
		return evOccSysId;
	}
	/**
	 * @param evOccSysId the evOccSysId to set
	 */
	public void setEvOccSysId(String evOccSysId) {
		this.evOccSysId = evOccSysId;
	}
	/**
	 * @return the evTypeCd
	 */
	public String getEvTypeCd() {
		return evTypeCd;
	}
	/**
	 * @param evTypeCd the evTypeCd to set
	 */
	public void setEvTypeCd(String evTypeCd) {
		this.evTypeCd = evTypeCd;
	}
	/**
	 * @return the evDivId
	 */
	public String getEvDivId() {
		return evDivId;
	}
	/**
	 * @param evDivId the evDivId to set
	 */
	public void setEvDivId(String evDivId) {
		this.evDivId = evDivId;
	}
	/**
	 * @return the evClasCd
	 */
	public String getEvClasCd() {
		return evClasCd;
	}
	/**
	 * @param evClasCd the evClasCd to set
	 */
	public void setEvClasCd(String evClasCd) {
		this.evClasCd = evClasCd;
	}
	/**
	 * @return the evOccId
	 */
	public String getEvOccId() {
		return evOccId;
	}
	/**
	 * @param evOccId the evOccId to set
	 */
	public void setEvOccId(String evOccId) {
		this.evOccId = evOccId;
	}
	/**
	 * @return the evTrtSttus
	 */
	public String getEvTrtSttus() {
		return evTrtSttus;
	}
	/**
	 * @param evTrtSttus the evTrtSttus to set
	 */
	public void setEvTrtSttus(String evTrtSttus) {
		this.evTrtSttus = evTrtSttus;
	}
	/**
	 * @return the compnEvYn
	 */
	public String getCompnEvYn() {
		return compnEvYn;
	}
	/**
	 * @param compnEvYn the compnEvYn to set
	 */
	public void setCompnEvYn(String compnEvYn) {
		this.compnEvYn = compnEvYn;
	}
	/**
	 * @return the occCauseEvs
	 */
	public List<String> getOccCauseEvs() {
		return occCauseEvs;
	}
	/**
	 * @param occCauseEvs the occCauseEvs to set
	 */
	public void setOccCauseEvs(List<String> occCauseEvs) {
		this.occCauseEvs = occCauseEvs;
	}
	/**
	 * @return the occPlcNm
	 */
	public String getOccPlcNm() {
		return occPlcNm;
	}
	/**
	 * @param occPlcNm the occPlcNm to set
	 */
	public void setOccPlcNm(String occPlcNm) {
		this.occPlcNm = occPlcNm;
	}
	/**
	 * @return the evSbst
	 */
	public String getEvSbst() {
		return evSbst;
	}
	/**
	 * @param evSbst the evSbst to set
	 */
	public void setEvSbst(String evSbst) {
		this.evSbst = evSbst;
	}



}
