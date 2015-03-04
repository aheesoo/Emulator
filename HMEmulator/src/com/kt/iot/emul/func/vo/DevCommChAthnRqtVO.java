package com.kt.iot.emul.func.vo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.iot.emul.func.vo.ComnRqtVO;

public class DevCommChAthnRqtVO extends ComnRqtVO
{
	/** 외부시스템아이디 */
	private String	extrSysId;
	/** 통신채널아이디 */
	private String	commChId;
	/** 장치아이디 */
	private String	devId;
	/** 인증요청번호 */
	private String	athnRqtNo;
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}

	public final String getExtrSysId() {
		return extrSysId;
	}

	public final void setExtrSysId(String extrSysId) {
		this.extrSysId = extrSysId;
	}

	public final String getCommChId() {
		return commChId;
	}

	public final void setCommChId(String commChId) {
		this.commChId = commChId;
	}

	public final String getDevId() {
		return devId;
	}

	public final void setDevId(String devId) {
		this.devId = devId;
	}

	public final String getAthnRqtNo() {
		return athnRqtNo;
	}

	public final void setAthnRqtNo(String athnRqtNo) {
		this.athnRqtNo = athnRqtNo;
	}
	
}

