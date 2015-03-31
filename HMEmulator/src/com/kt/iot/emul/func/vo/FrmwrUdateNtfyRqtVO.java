/**
 * <PRE>
 *  Project : GWCommAgent
 *  Package : com.kt.smcp.gw.ca.gwadaptor.sif.contl.vo
 * </PRE>
 * @file   : SifFrmwrUdateRqtVO.java
 * @date   : 2013. 12. 11. 오후 3:26:07
 * @author : byw
 * @brief  :
 *  변경이력    :
 *        이름     : 일자          : 근거자료   : 변경내용
 *       ------------------------------------
 *        byw  : 2013. 12. 11.       :            : 신규 개발.
 */
package com.kt.iot.emul.func.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.iot.emul.func.vo.ComnRqtVO;

public class FrmwrUdateNtfyRqtVO extends ComnRqtVO
{
	/** 외부시스템아이디 */
	private String extrSysId;
	/** 장치아이디 */
	private String devId;
	/** 업데이트트랜잭션아이디 */
	private String udateTransacId;
	/** 업데이트타임아웃시간(초) */
	private Integer udateToutTime;
	/** 펌웨어일련번호 */
	private Long frmwrSeq;
	/** 펌웨어파일경로명 */
	private String frmwrFilePathNm;
	/** 펌웨어버전 */
	private String frmwrVer;
	/** 펌웨어버전번호 */
	private Integer frmwrVerNo;
	/** 펌웨어크기 */
	private Integer frmwrSize;
	/** 패키지정보 */
	private List<PkgInfoVO> pkgInfoVOs = new ArrayList<PkgInfoVO>();
	/** HTTP 서버정보 */
	private HttpSrvrVO httpSrvrVO;
	/** TCP 서버정보 */
	private TcpSrvrVO tcpSrvrVO;
	/** FTP 서버정보 */
	private FtpSrvrVO ftpSrvrVO;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}

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

	public Integer getUdateToutTime() {
		return udateToutTime;
	}

	public void setUdateToutTime(Integer udateToutTime) {
		this.udateToutTime = udateToutTime;
	}

	public Long getFrmwrSeq() {
		return frmwrSeq;
	}

	public void setFrmwrSeq(Long frmwrSeq) {
		this.frmwrSeq = frmwrSeq;
	}

	public String getFrmwrFilePathNm() {
		return frmwrFilePathNm;
	}

	public void setFrmwrFilePathNm(String frmwrFilePathNm) {
		this.frmwrFilePathNm = frmwrFilePathNm;
	}

	public String getFrmwrVer() {
		return frmwrVer;
	}

	public void setFrmwrVer(String frmwrVer) {
		this.frmwrVer = frmwrVer;
	}

	public Integer getFrmwrVerNo() {
		return frmwrVerNo;
	}

	public void setFrmwrVerNo(Integer frmwrVerNo) {
		this.frmwrVerNo = frmwrVerNo;
	}

	public Integer getFrmwrSize() {
		return frmwrSize;
	}

	public void setFrmwrSize(Integer frmwrSize) {
		this.frmwrSize = frmwrSize;
	}

	public List<PkgInfoVO> getPkgInfoVOs() {
		return pkgInfoVOs;
	}

	public void setPkgInfoVOs(List<PkgInfoVO> pkgInfoVOs) {
		this.pkgInfoVOs = pkgInfoVOs;
	}

	public HttpSrvrVO getHttpSrvrVO() {
		return httpSrvrVO;
	}

	public void setHttpSrvrVO(HttpSrvrVO httpSrvrVO) {
		this.httpSrvrVO = httpSrvrVO;
	}

	public TcpSrvrVO getTcpSrvrVO() {
		return tcpSrvrVO;
	}

	public void setTcpSrvrVO(TcpSrvrVO tcpSrvrVO) {
		this.tcpSrvrVO = tcpSrvrVO;
	}

	public FtpSrvrVO getFtpSrvrVO() {
		return ftpSrvrVO;
	}

	public void setFtpSrvrVO(FtpSrvrVO ftpSrvrVO) {
		this.ftpSrvrVO = ftpSrvrVO;
	}


}
