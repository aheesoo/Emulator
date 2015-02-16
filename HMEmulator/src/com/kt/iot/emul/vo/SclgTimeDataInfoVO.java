package com.kt.iot.emul.vo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 스케줄시간데이터 클래스
 * @since	: 2014. 11. 2.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2014. 11. 2. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
public class SclgTimeDataInfoVO implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = -6919932060242171227L;

	/** 시작시간(HHMMSS) */
	private String stTime;
	/** 종료시간(HHMMSS) */
	private String fnsTime;

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

	public String getStTime() {
		return stTime;
	}

	public void setStTime(String stTime) {
		this.stTime = stTime;
	}

	public String getFnsTime() {
		return fnsTime;
	}

	public void setFnsTime(String fnsTime) {
		this.fnsTime = fnsTime;
	}


}
