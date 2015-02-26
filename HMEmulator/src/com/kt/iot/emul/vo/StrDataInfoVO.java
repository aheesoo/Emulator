package com.kt.iot.emul.vo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 문자열데이터 클래스
 * @since	: 2014. 11. 2.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2014. 11. 2. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
public class StrDataInfoVO implements Serializable, Cloneable
{
	/** 직렬화데이터 */
	private static final long serialVersionUID = -3786632152709193405L;

	/** 센싱태그코드 */
	private String snsnTagCd;
	/** 문자열값 */
	private String strVal;

	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}

	public String getSnsnTagCd() {
		return snsnTagCd;
	}

	public void setSnsnTagCd(String snsnTagCd) {
		this.snsnTagCd = snsnTagCd;
	}

	public String getStrVal() {
		return strVal;
	}

	public void setStrVal(String strVal) {
		this.strVal = strVal;
	}


}
