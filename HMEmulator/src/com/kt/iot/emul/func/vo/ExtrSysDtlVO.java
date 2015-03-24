/**
 * <PRE>
 *  Project GWCommAgent2
 *  Package com.kt.smcp.gw.ca.gwadaptor.std.athn.vo
 * </PRE>
 * @brief
 * @file ExtrSysDtlVO.java
 * @date 2014. 3. 5. 오전 11:15:45
 * @author byw
 *  변경이력
 *        이름     : 일자          : 근거자료   : 변경내용
 *       ------------------------------------
 *        byw  : 2014. 3. 5.       :            : 신규 개발.
 *
 * Copyright © 2013 kt corp. all rights reserved.
 */
package com.kt.iot.emul.func.vo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * <PRE>
 *  ClassName : ExtrSysDtlVO
 * </PRE>
 * @version : 1.0
 * @date    : 2013. 12. 11. 오전 10:53:25
 * @author  : byw
 * @brief   : 외부시스템상세 VO
 */

public class ExtrSysDtlVO {
	/** 속성명 */
	private String atribNm;
	/** 속성값 */
	private String atribVal;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
	/**
	 * @return the atribNm
	 */
	public String getAtribNm() {
		return atribNm;
	}
	/**
	 * @param atribNm the atribNm to set
	 */
	public void setAtribNm(String atribNm) {
		this.atribNm = atribNm;
	}
	/**
	 * @return the atribVal
	 */
	public String getAtribVal() {
		return atribVal;
	}
	/**
	 * @param atribVal the atribVal to set
	 */
	public void setAtribVal(String atribVal) {
		this.atribVal = atribVal;
	}

}
