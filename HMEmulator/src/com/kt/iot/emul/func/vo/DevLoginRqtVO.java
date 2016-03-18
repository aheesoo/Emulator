package com.kt.iot.emul.func.vo;

import com.kt.iot.emul.func.vo.ComnRqtVO;

public class DevLoginRqtVO extends ComnRqtVO
{
	/** 외부시스템아이디 */
	private String extrSysId;
	/** 장치아이디 */
	private String devId;
	/** 장치아이디_임시 */
	private String deviceId;
	/** 인증유형코드 */
	private String	athnFormlCd;
	/** 인증세션아이디 */
	private String	athnSessnId;
	/** 인증요청번호 */
	private String	athnRqtNo;
	/** 장치펌웨어버전 */
	private String devFrmwrVer;
	/** 장치펌웨어버전번호 */
	private Integer devFrmwrVerNo;
	/** 장치프로토콜버전 */
	private String devProtVer;
	/** 장치프로토콜버전번호 */
	private Integer devProtVerNo;

	public String getExtrSysId() {
		return extrSysId;
	}
	public void setExtrSysId(String extrSysId) {
		this.extrSysId = extrSysId;
	}
	public String getDevId() {
		return devId;
	}
	public void setDevId(String devId) {
		this.devId = devId;
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
	public String getAthnFormlCd() {
		return athnFormlCd;
	}
	public void setAthnFormlCd(String athnFormlCd) {
		this.athnFormlCd = athnFormlCd;
	}
	public String getAthnSessnId() {
		return athnSessnId;
	}
	public void setAthnSessnId(String athnSessnId) {
		this.athnSessnId = athnSessnId;
	}
	public String getDevFrmwrVer() {
		return devFrmwrVer;
	}
	public void setDevFrmwrVer(String devFrmwrVer) {
		this.devFrmwrVer = devFrmwrVer;
	}
	public Integer getDevFrmwrVerNo() {
		return devFrmwrVerNo;
	}
	public void setDevFrmwrVerNo(Integer devFrmwrVerNo) {
		this.devFrmwrVerNo = devFrmwrVerNo;
	}
	public String getDevProtVer() {
		return devProtVer;
	}
	public void setDevProtVer(String devProtVer) {
		this.devProtVer = devProtVer;
	}
	public Integer getDevProtVerNo() {
		return devProtVerNo;
	}
	public void setDevProtVerNo(Integer devProtVerNo) {
		this.devProtVerNo = devProtVerNo;
	}

}
