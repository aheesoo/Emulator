package com.kt.iot.emul.func.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kt.iot.emul.func.vo.ComnRqtVO;

public class FrmwrUdateSttusTrmRqtVO extends ComnRqtVO
{
	/** 외부시스템아이디 */
	private String extrSysId;
	/** 장치아이디 */
	private String devId;
	/** 업데이트트랜잭션아이디 */
	private String udateTransacId;
	/** 펌웨어일련번호 */
	private String frmwrSeq;
	/** 펌웨어파일경로명 */
	private String frmwrFilePathNm;
	/** 패키지정보 */
	private List<PkgInfoVO> pkgInfoVOs = new ArrayList<PkgInfoVO>();
	/** 발생일시 */
	private Date occDt;
	/** 상태코드 */
	private String sttusCd;
	/** 상태값 */
	private String sttusVal;

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
	public String getUdateTransacId() {
		return udateTransacId;
	}
	public void setUdateTransacId(String udateTransacId) {
		this.udateTransacId = udateTransacId;
	}
	public String getFrmwrSeq() {
		return frmwrSeq;
	}
	public void setFrmwrSeq(String frmwrSeq) {
		this.frmwrSeq = frmwrSeq;
	}
	public String getFrmwrFilePathNm() {
		return frmwrFilePathNm;
	}
	public void setFrmwrFilePathNm(String frmwrFilePathNm) {
		this.frmwrFilePathNm = frmwrFilePathNm;
	}
	public List<PkgInfoVO> getPkgInfoVOs() {
		return pkgInfoVOs;
	}
	public void setPkgInfoVOs(List<PkgInfoVO> pkgInfoVOs) {
		this.pkgInfoVOs = pkgInfoVOs;
	}
	public Date getOccDt() {
		return occDt;
	}
	public void setOccDt(Date occDt) {
		this.occDt = occDt;
	}
	public String getSttusCd() {
		return sttusCd;
	}
	public void setSttusCd(String sttusCd) {
		this.sttusCd = sttusCd;
	}
	public String getSttusVal() {
		return sttusVal;
	}
	public void setSttusVal(String sttusVal) {
		this.sttusVal = sttusVal;
	}

}
