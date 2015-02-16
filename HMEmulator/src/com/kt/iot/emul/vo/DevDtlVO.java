package com.kt.iot.emul.vo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class DevDtlVO 
{
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
