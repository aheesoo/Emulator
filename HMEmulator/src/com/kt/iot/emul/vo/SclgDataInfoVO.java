package com.kt.iot.emul.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 스케줄데이터 클래스
 * @since	: 2014. 11. 2.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2014. 11. 2. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
public class SclgDataInfoVO implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = -2706104727227233106L;

	/** 스케줄설정요일코드 */
	private String dowCd;
	/** 스케줄설정요일코드 */
	private List<SclgTimeDataInfoVO> sclgTimeDataInfoVOs = new ArrayList<SclgTimeDataInfoVO>();

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

	public String getDowCd() {
		return dowCd;
	}

	public void setDowCd(String dowCd) {
		this.dowCd = dowCd;
	}

	public List<SclgTimeDataInfoVO> getSclgTimeDataInfoVOs() {
		return sclgTimeDataInfoVOs;
	}

	public void setSclgTimeDataInfoVOs(List<SclgTimeDataInfoVO> sclgTimeDatas) {
		this.sclgTimeDataInfoVOs = sclgTimeDatas;
	}


}
