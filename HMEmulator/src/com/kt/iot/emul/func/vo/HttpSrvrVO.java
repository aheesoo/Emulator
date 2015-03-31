package com.kt.iot.emul.func.vo;

import java.util.ArrayList;
import java.util.List;

public class HttpSrvrVO
{
	/** 서버주소 */
	private String srvrAdr;
	/** 메서드명 */
	private String mthdNm;
	/** 연결파라미터 */
	private List<Param> cnctParams = new ArrayList<HttpSrvrVO.Param>();

	public String getSrvrAdr() {
		return srvrAdr;
	}

	public void setSrvrAdr(String srvrAdr) {
		this.srvrAdr = srvrAdr;
	}

	public String getMthdNm() {
		return mthdNm;
	}

	public void setMthdNm(String mthdNm) {
		this.mthdNm = mthdNm;
	}

	public List<Param> getCnctParams() {
		return cnctParams;
	}

	public void setCnctParams(List<Param> cnctParams) {
		this.cnctParams = cnctParams;
	}

	public static class Param
	{
		/** Key */
		private String key;
		/** Value */
		private String value;
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}
}
