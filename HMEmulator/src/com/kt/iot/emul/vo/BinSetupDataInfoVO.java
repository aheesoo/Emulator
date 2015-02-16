package com.kt.iot.emul.vo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 일반셋팅데이터 클래스
 * @since	: 2014. 11. 2.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2014. 11. 2. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
public class BinSetupDataInfoVO implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = -868013428310913928L;

	/** 센싱태그코드 */
	private String snsnTagCd;
	/** 설정값 */
	private byte[] setupVal;

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

	public byte[] getSetupVal() {
		return setupVal;
	}

	public void setSetupVal(byte[] setupVal) {
		this.setupVal = setupVal;
	}


}
