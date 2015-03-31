package com.kt.iot.emul.func.vo;

public class FtpSrvrVO
{
	/** 서버주소 */
	private String srvrAdr;
	/** 포트번호 */
	private Integer portNo;
	/** 아이디 */
	private String id;
	/** 패스워드 */
	private String pwd;

	public String getSrvrAdr() {
		return srvrAdr;
	}
	public void setSrvrAdr(String srvrAdr) {
		this.srvrAdr = srvrAdr;
	}
	public Integer getPortNo() {
		return portNo;
	}
	public void setPortNo(Integer portNo) {
		this.portNo = portNo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}


}
