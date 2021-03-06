/**
 * <PRE>
 *  Project : GWCommAgent
 *  Package : com.kt.smcp.gw.ca.gwadaptor.sif.colec.vo
 * </PRE>
 * @file   : BinDataInfoVO.java
 * @date   : 2013. 12. 11. 오후 2:48:23
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

public class CmdDataInfoVO
{
	/** 데이터유형코드 */
	private String dataTypeCd;
	/** 명령데이터 */
	private byte[] cmdData;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}

	public String getDataTypeCd() {
		return dataTypeCd;
	}

	public void setDataTypeCd(String dataTypeCd) {
		this.dataTypeCd = dataTypeCd;
	}

	public byte[] getCmdData() {
		return cmdData;
	}

	public void setCmdData(byte[] cmdData) {
		this.cmdData = cmdData;
	}


}
