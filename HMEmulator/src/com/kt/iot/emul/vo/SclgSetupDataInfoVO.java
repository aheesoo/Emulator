package com.kt.iot.emul.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 스케줄셋팅데이터
 * @since	: 2014. 11. 2.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2014. 11. 2. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
public class SclgSetupDataInfoVO implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = 1412612946825389914L;

	/** 센싱태그코드 */
	private String snsnTagCd;
	/** 스케줄데이터리스트 */
	private List<SclgDataInfoVO> sclgDataInfoVOs = new ArrayList<SclgDataInfoVO>();

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

	public List<SclgDataInfoVO> getSclgDataInfoVOs() {
		return sclgDataInfoVOs;
	}

	public void setSclgDataInfoVOs(List<SclgDataInfoVO> sclgDatas) {
		this.sclgDataInfoVOs = sclgDatas;
	}


}
