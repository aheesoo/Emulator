/**
 * <PRE>
 *  Project : GWCommAgent
 *  Package : com.kt.smcp.gw.ca.gwadaptor.sif.colec.vo
 * </PRE>
 * @file   : LoDataInfoVO.java
 * @date   : 2013. 12. 11. 오후 2:42:07
 * @author : byw
 * @brief  :
 *  변경이력    :
 *        이름     : 일자          : 근거자료   : 변경내용
 *       ------------------------------------
 *        byw  : 2013. 12. 11.       :            : 신규 개발.
 */
package com.kt.iot.emul.vo;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * <PRE>
 *  ClassName : LoDataInfoVO
 * </PRE>
 * @version : 1.0
 * @date    : 2013. 12. 11. 오후 2:42:07
 * @author  : byw
 * @brief   : 위치데이터 정보 VO
 */

public class LoDataInfoVO {
	/** 데이터유형코드 */
	private String dataTypeCd;
	/** 위도 */
	private Double latit;
	/** 경도 */
	private Double lngit;
	/** 고도 */
	private Double altt;
	/** GPS상태 */
	private Integer gpsSttus;
	/** 수신위성개수 */
	private Integer rcvSatelCnt;
	/** 수평오차 */
	private Double hriznDfrn;
	/** 수직오차 */
	private Double vrtclDfrn;
	/** 방향 */
	private Integer drect;
	/** 속도 */
	private Double	speed;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
	/**
	 * @return the dataTypeCd
	 */
	public String getDataTypeCd() {
		return dataTypeCd;
	}
	/**
	 * @param dataTypeCd the dataTypeCd to set
	 */
	public void setDataTypeCd(String dataTypeCd) {
		this.dataTypeCd = dataTypeCd;
	}
	/**
	 * @return the latit
	 */
	public Double getLatit() {
		return latit;
	}
	/**
	 * @param latit the latit to set
	 */
	public void setLatit(Double latit) {
		this.latit = latit;
	}
	/**
	 * @return the lngit
	 */
	public Double getLngit() {
		return lngit;
	}
	/**
	 * @param lngit the lngit to set
	 */
	public void setLngit(Double lngit) {
		this.lngit = lngit;
	}

	/**
	 * @return the altt
	 */
	public Double getAltt() {
		return altt;
	}
	/**
	 * @param altt the altt to set
	 */
	public void setAltt(Double altt) {
		this.altt = altt;
	}
	/**
	 * @return the gpsSttus
	 */
	public Integer getGpsSttus() {
		return gpsSttus;
	}
	/**
	 * @param gpsSttus the gpsSttus to set
	 */
	public void setGpsSttus(Integer gpsSttus) {
		this.gpsSttus = gpsSttus;
	}
	/**
	 * @return the rcvSatelCnt
	 */
	public Integer getRcvSatelCnt() {
		return rcvSatelCnt;
	}
	/**
	 * @param rcvSatelCnt the rcvSatelCnt to set
	 */
	public void setRcvSatelCnt(Integer rcvSatelCnt) {
		this.rcvSatelCnt = rcvSatelCnt;
	}
	/**
	 * @return the hriznDfrn
	 */
	public Double getHriznDfrn() {
		return hriznDfrn;
	}
	/**
	 * @param hriznDfrn the hriznDfrn to set
	 */
	public void setHriznDfrn(Double hriznDfrn) {
		this.hriznDfrn = hriznDfrn;
	}
	/**
	 * @return the vrtclDfrn
	 */
	public Double getVrtclDfrn() {
		return vrtclDfrn;
	}
	/**
	 * @param vrtclDfrn the vrtclDfrn to set
	 */
	public void setVrtclDfrn(Double vrtclDfrn) {
		this.vrtclDfrn = vrtclDfrn;
	}
	/**
	 * @return the drect
	 */
	public Integer getDrect() {
		return drect;
	}
	/**
	 * @param drect the drect to set
	 */
	public void setDrect(Integer drect) {
		this.drect = drect;
	}
	/**
	 * @return the speed
	 */
	public Double getSpeed() {
		return speed;
	}
	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(Double speed) {
		this.speed = speed;
	}


}
