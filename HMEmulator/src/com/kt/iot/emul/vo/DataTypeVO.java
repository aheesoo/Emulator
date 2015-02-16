/**
 * <PRE>
 *  Project : GWCommAgent
 *  Package : com.kt.smcp.gw.ca.gwadaptor.sif.inita.vo
 * </PRE>
 * @file   : DataTypeListVO.java
 * @date   : 2013. 12. 11. 오후 1:13:40
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
 * <PRE>
 *  ClassName : DataTypeListVO
 * </PRE>
 * @version : 1.0
 * @date    : 2013. 12. 11. 오후 1:13:40
 * @author  : byw
 * @brief   : 데이터유형 VO
 */

public class DataTypeVO {
	/** 데이터유형분류코드(그룹: 1104) */
	private String dataTypeCtgCd;
	/** 데이터유형코드 */
	private String dataTypeCd;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
	/**
	 * @return the dataTypeCtgCd
	 */
	public String getDataTypeCtgCd() {
		return dataTypeCtgCd;
	}
	/**
	 * @param dataTypeCtgCd the dataTypeCtgCd to set
	 */
	public void setDataTypeCtgCd(String dataTypeCtgCd) {
		this.dataTypeCtgCd = dataTypeCtgCd;
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
}
