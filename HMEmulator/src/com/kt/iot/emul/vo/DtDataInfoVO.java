package com.kt.iot.emul.vo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 일시데이터
 * @since	: 2014. 11. 19.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2014. 11. 19. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
public class DtDataInfoVO implements Serializable, Cloneable
{
	/** 직렬화데이터 */
	private static final long serialVersionUID = -8352346576231337794L;
	/** 센싱태그코드 */
	private String snsnTagCd;
	/** 일시값 */
	private Date dtVal;

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

	public Date getDtVal() {
		return dtVal;
	}

	public void setDtVal(Date dtVal) {
		this.dtVal = dtVal;
	}


}