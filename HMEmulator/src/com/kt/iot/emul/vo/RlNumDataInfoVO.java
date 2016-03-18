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

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

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
public class RlNumDataInfoVO
{
	/** 데이터유형코드 */
	private String dataTypeCd;
	/** 실수값 */
	private Double rlNumVal;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}

	public String getDataTypeCd() {
		return dataTypeCd;
	}

	public void setDataTypeCd(String dataTypeCd) {
		this.dataTypeCd = dataTypeCd;
	}

	public Double getRlNumVal() {
		return rlNumVal;
	}

	public void setRlNumVal(Double rlNumVal) {
		this.rlNumVal = rlNumVal;
	}


}
