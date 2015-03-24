/**
 * <PRE>
 *  Project GWCommAgent2
 *  Package com.kt.smcp.gw.ca.gwadaptor.std.athn.vo
 * </PRE>
 * @brief
 * @file CommChDtlVO.java
 * @date 2014. 3. 5. 오후 1:36:59
 * @author byw
 *  변경이력
 *        이름     : 일자          : 근거자료   : 변경내용
 *       ------------------------------------
 *        byw  : 2014. 3. 5.       :            : 신규 개발.
 *
 * Copyright © 2013 kt corp. all rights reserved.
 */
package com.kt.iot.emul.func.vo;

/**
 * <PRE>
 *  ClassName : CommChDtlVO
 * </PRE>
 * @version : 1.0
 * @date    : 2013. 12. 11. 오전 10:53:25
 * @author  : byw
 * @brief   : 통신채널상세 VO
 */

public class CommChDtlVO {
	/** 통신채널아이디 */
	private String	commChId;
	/** 통신채널코드(그룹: 1009) */
	private String	commChCd;
	/** IP주소 */
	private String	ipadr;
	/** 인터페이스유형코드(그룹: 1001) */
	private String	ifTypeCd;
	/** 연결유형코드(그룹: 1004) */
	private String	cnctTypeCd;
	/** 포트번호 */
	private Integer	portNo;
	/** 부URL주소 */
	private String	subUrlAdr;
	/** 연결타임아웃시간 */
	private Long	cnctToutTime;
	/** 프로토콜코드(그룹: 1003) */
	private String	protCd;
	/** 헤더프로토콜코드(그룹: 1010) */
	private String	headProtCd;
	/** 이진변환유형코드(그룹: 1012) */
	private String	binChangeTypeCd;
	/** 헤더길이 */
	private Integer	headLen;
	/** 본문최대길이 */
	private Integer	bdyMaxLen;
	/** 바디길이필드위치값 */
	private Integer	bdyLenFieldLoVal;
	/** 바디길이필드길이 */
	private Integer	bdyLenFieldLen;
	/** 수집주기시간 */
	private Long	colecCyclTime;
	/** 폴링주기시간 */
	private Long	polngCyclTime;
	/** 폴링지연시간 */
	private Long	polngDelayTime;
	/** 유휴판단기준시간 */
	private Long	idleJdgmBaseTime;
	/** 재접속주기시간 */
	private Long	recnCyclTime;
	/** 재접속시도횟수 */
	private Integer	recnTryTmscnt;
	/** 작업처리타임아웃시간 */
	private Long	wrkTrtToutTime;
	/** 인증방식코드(그룹: 1005) */
	private String	athnFormlCd;
	/** 인증요청번호 */
	private String	athnRqtNo;
	/** 인증번호 */
	private String	athnNo;
	/** 수신암호화여부 */
	private String	rcvEcodYn;
	/** 수신암호화유형코드(그룹: 1006) */
	private String	rcvEcodTypeCd;
	/** 수신암호화키값 */
	private String	rcvEcodKeyVal;
	/** 송신암호화여부 */
	private String	sndEcodYn;
	/** 송신암호화유형코드(그룹: 1006) */
	private String	sndEcodTypeCd;
	/** 송신암호화키값 */
	private String	sndEcodKeyVal;
	/**
	 * @return the commChId
	 */
	public String getCommChId() {
		return commChId;
	}
	/**
	 * @param commChId the commChId to set
	 */
	public void setCommChId(String commChId) {
		this.commChId = commChId;
	}
	/**
	 * @return the commChCd
	 */
	public String getCommChCd() {
		return commChCd;
	}
	/**
	 * @param commChCd the commChCd to set
	 */
	public void setCommChCd(String commChCd) {
		this.commChCd = commChCd;
	}
	/**
	 * @return the ipadr
	 */
	public String getIpadr() {
		return ipadr;
	}
	/**
	 * @param ipadr the ipadr to set
	 */
	public void setIpadr(String ipadr) {
		this.ipadr = ipadr;
	}
	/**
	 * @return the ifTypeCd
	 */
	public String getIfTypeCd() {
		return ifTypeCd;
	}
	/**
	 * @param ifTypeCd the ifTypeCd to set
	 */
	public void setIfTypeCd(String ifTypeCd) {
		this.ifTypeCd = ifTypeCd;
	}
	/**
	 * @return the cnctTypeCd
	 */
	public String getCnctTypeCd() {
		return cnctTypeCd;
	}
	/**
	 * @param cnctTypeCd the cnctTypeCd to set
	 */
	public void setCnctTypeCd(String cnctTypeCd) {
		this.cnctTypeCd = cnctTypeCd;
	}
	/**
	 * @return the portNo
	 */
	public Integer getPortNo() {
		return portNo;
	}
	/**
	 * @param portNo the portNo to set
	 */
	public void setPortNo(Integer portNo) {
		this.portNo = portNo;
	}
	/**
	 * @return the subUrlAdr
	 */
	public String getSubUrlAdr() {
		return subUrlAdr;
	}
	/**
	 * @param subUrlAdr the subUrlAdr to set
	 */
	public void setSubUrlAdr(String subUrlAdr) {
		this.subUrlAdr = subUrlAdr;
	}
	/**
	 * @return the cnctToutTime
	 */
	public Long getCnctToutTime() {
		return cnctToutTime;
	}
	/**
	 * @param cnctToutTime the cnctToutTime to set
	 */
	public void setCnctToutTime(Long cnctToutTime) {
		this.cnctToutTime = cnctToutTime;
	}
	/**
	 * @return the protCd
	 */
	public String getProtCd() {
		return protCd;
	}
	/**
	 * @param protCd the protCd to set
	 */
	public void setProtCd(String protCd) {
		this.protCd = protCd;
	}
	/**
	 * @return the headProtCd
	 */
	public String getHeadProtCd() {
		return headProtCd;
	}
	/**
	 * @param headProtCd the headProtCd to set
	 */
	public void setHeadProtCd(String headProtCd) {
		this.headProtCd = headProtCd;
	}
	/**
	 * @return the binChangeTypeCd
	 */
	public String getBinChangeTypeCd() {
		return binChangeTypeCd;
	}
	/**
	 * @param binChangeTypeCd the binChangeTypeCd to set
	 */
	public void setBinChangeTypeCd(String binChangeTypeCd) {
		this.binChangeTypeCd = binChangeTypeCd;
	}
	/**
	 * @return the headLen
	 */
	public Integer getHeadLen() {
		return headLen;
	}
	/**
	 * @param headLen the headLen to set
	 */
	public void setHeadLen(Integer headLen) {
		this.headLen = headLen;
	}
	/**
	 * @return the bdyMaxLen
	 */
	public Integer getBdyMaxLen() {
		return bdyMaxLen;
	}
	/**
	 * @param bdyMaxLen the bdyMaxLen to set
	 */
	public void setBdyMaxLen(Integer bdyMaxLen) {
		this.bdyMaxLen = bdyMaxLen;
	}
	/**
	 * @return the bdyLenFieldLoVal
	 */
	public Integer getBdyLenFieldLoVal() {
		return bdyLenFieldLoVal;
	}
	/**
	 * @param bdyLenFieldLoVal the bdyLenFieldLoVal to set
	 */
	public void setBdyLenFieldLoVal(Integer bdyLenFieldLoVal) {
		this.bdyLenFieldLoVal = bdyLenFieldLoVal;
	}
	/**
	 * @return the bdyLenFieldLen
	 */
	public Integer getBdyLenFieldLen() {
		return bdyLenFieldLen;
	}
	/**
	 * @param bdyLenFieldLen the bdyLenFieldLen to set
	 */
	public void setBdyLenFieldLen(Integer bdyLenFieldLen) {
		this.bdyLenFieldLen = bdyLenFieldLen;
	}
	/**
	 * @return the colecCyclTime
	 */
	public Long getColecCyclTime() {
		return colecCyclTime;
	}
	/**
	 * @param colecCyclTime the colecCyclTime to set
	 */
	public void setColecCyclTime(Long colecCyclTime) {
		this.colecCyclTime = colecCyclTime;
	}
	/**
	 * @return the polngCyclTime
	 */
	public Long getPolngCyclTime() {
		return polngCyclTime;
	}
	/**
	 * @param polngCyclTime the polngCyclTime to set
	 */
	public void setPolngCyclTime(Long polngCyclTime) {
		this.polngCyclTime = polngCyclTime;
	}
	/**
	 * @return the polngDelayTime
	 */
	public Long getPolngDelayTime() {
		return polngDelayTime;
	}
	/**
	 * @param polngDelayTime the polngDelayTime to set
	 */
	public void setPolngDelayTime(Long polngDelayTime) {
		this.polngDelayTime = polngDelayTime;
	}
	/**
	 * @return the idleJdgmBaseTime
	 */
	public Long getIdleJdgmBaseTime() {
		return idleJdgmBaseTime;
	}
	/**
	 * @param idleJdgmBaseTime the idleJdgmBaseTime to set
	 */
	public void setIdleJdgmBaseTime(Long idleJdgmBaseTime) {
		this.idleJdgmBaseTime = idleJdgmBaseTime;
	}
	/**
	 * @return the recnCyclTime
	 */
	public Long getRecnCyclTime() {
		return recnCyclTime;
	}
	/**
	 * @param recnCyclTime the recnCyclTime to set
	 */
	public void setRecnCyclTime(Long recnCyclTime) {
		this.recnCyclTime = recnCyclTime;
	}
	/**
	 * @return the recnTryTmscnt
	 */
	public Integer getRecnTryTmscnt() {
		return recnTryTmscnt;
	}
	/**
	 * @param recnTryTmscnt the recnTryTmscnt to set
	 */
	public void setRecnTryTmscnt(Integer recnTryTmscnt) {
		this.recnTryTmscnt = recnTryTmscnt;
	}
	/**
	 * @return the wrkTrtToutTime
	 */
	public Long getWrkTrtToutTime() {
		return wrkTrtToutTime;
	}
	/**
	 * @param wrkTrtToutTime the wrkTrtToutTime to set
	 */
	public void setWrkTrtToutTime(Long wrkTrtToutTime) {
		this.wrkTrtToutTime = wrkTrtToutTime;
	}
	/**
	 * @return the athnFormlCd
	 */
	public String getAthnFormlCd() {
		return athnFormlCd;
	}
	/**
	 * @param athnFormlCd the athnFormlCd to set
	 */
	public void setAthnFormlCd(String athnFormlCd) {
		this.athnFormlCd = athnFormlCd;
	}
	/**
	 * @return the athnRqtNo
	 */
	public String getAthnRqtNo() {
		return athnRqtNo;
	}
	/**
	 * @param athnRqtNo the athnRqtNo to set
	 */
	public void setAthnRqtNo(String athnRqtNo) {
		this.athnRqtNo = athnRqtNo;
	}
	/**
	 * @return the athnNo
	 */
	public String getAthnNo() {
		return athnNo;
	}
	/**
	 * @param athnNo the athnNo to set
	 */
	public void setAthnNo(String athnNo) {
		this.athnNo = athnNo;
	}
	/**
	 * @return the rcvEcodYn
	 */
	public String getRcvEcodYn() {
		return rcvEcodYn;
	}
	/**
	 * @param rcvEcodYn the rcvEcodYn to set
	 */
	public void setRcvEcodYn(String rcvEcodYn) {
		this.rcvEcodYn = rcvEcodYn;
	}
	/**
	 * @return the rcvEcodTypeCd
	 */
	public String getRcvEcodTypeCd() {
		return rcvEcodTypeCd;
	}
	/**
	 * @param rcvEcodTypeCd the rcvEcodTypeCd to set
	 */
	public void setRcvEcodTypeCd(String rcvEcodTypeCd) {
		this.rcvEcodTypeCd = rcvEcodTypeCd;
	}
	/**
	 * @return the rcvEcodKeyVal
	 */
	public String getRcvEcodKeyVal() {
		return rcvEcodKeyVal;
	}
	/**
	 * @param rcvEcodKeyVal the rcvEcodKeyVal to set
	 */
	public void setRcvEcodKeyVal(String rcvEcodKeyVal) {
		this.rcvEcodKeyVal = rcvEcodKeyVal;
	}
	/**
	 * @return the sndEcodYn
	 */
	public String getSndEcodYn() {
		return sndEcodYn;
	}
	/**
	 * @param sndEcodYn the sndEcodYn to set
	 */
	public void setSndEcodYn(String sndEcodYn) {
		this.sndEcodYn = sndEcodYn;
	}
	/**
	 * @return the sndEcodTypeCd
	 */
	public String getSndEcodTypeCd() {
		return sndEcodTypeCd;
	}
	/**
	 * @param sndEcodTypeCd the sndEcodTypeCd to set
	 */
	public void setSndEcodTypeCd(String sndEcodTypeCd) {
		this.sndEcodTypeCd = sndEcodTypeCd;
	}
	/**
	 * @return the sndEcodKeyVal
	 */
	public String getSndEcodKeyVal() {
		return sndEcodKeyVal;
	}
	/**
	 * @param sndEcodKeyVal the sndEcodKeyVal to set
	 */
	public void setSndEcodKeyVal(String sndEcodKeyVal) {
		this.sndEcodKeyVal = sndEcodKeyVal;
	}


}
