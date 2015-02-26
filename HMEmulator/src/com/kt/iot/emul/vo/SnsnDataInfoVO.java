/**
 * <PRE>
 *  Project : GWCommAgent
 *  Package : com.kt.smcp.gw.ca.gwadaptor.sif.colec.vo
 * </PRE>
 * @file   : SnsnDataInfoVO.java
 * @date   : 2013. 12. 11. 오후 2:38:01
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
 *  ClassName : SnsnDataInfoVO
 * </PRE>
 * @version : 1.0
 * @date    : 2013. 12. 11. 오후 2:38:01
 * @author  : byw
 * @brief   : 센싱데이터 정보 VO
 */

public class SnsnDataInfoVO
{
	/** 데이터유형코드 */
	private String dataTypeCd;
	/** 센싱값 */
	private Double snsnVal;

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
	 * @return the snsnVal
	 */
	public Double getSnsnVal() {
		return snsnVal;
	}
	/**
	 * @param snsnVal the snsnVal to set
	 */
	public void setSnsnVal(Double snsnVal) {
		this.snsnVal = snsnVal;
	}
}
