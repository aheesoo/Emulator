package com.kt.iot.emul.func.vo;

import com.kt.iot.emul.func.vo.ComnRqtVO;

public class DevLoginRqtVO extends ComnRqtVO
{
	/** 외부시스템아이디 */
	private String extrSysId;
	/** 장치아이디 */
	private String deviceId;
	/** 인증요청번호 */
	private String athnRqtNo;
	public String getExtrSysId() {
		return extrSysId;
	}
	public void setExtrSysId(String extrSysId) {
		this.extrSysId = extrSysId;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getAthnRqtNo() {
		return athnRqtNo;
	}
	public void setAthnRqtNo(String athnRqtNo) {
		this.athnRqtNo = athnRqtNo;
	}


}
