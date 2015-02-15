/**
 * <PRE>
 *  Project : GWCommAgent
 *  Package : com.kt.smcp.gw.ca.gwadaptor.sif.vo
 * </PRE>
 * @file   : SifCommChAthnRpyVO.java
 * @date   : 2013. 12. 11. 오전 10:53:25
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

import com.kt.iot.emul.vo.ComnRespVO;
import com.kt.iot.emul.vo.MsgHeadVO;

/**
 * <PRE>
 *  ClassName : CommChAthnRespVO
 * </PRE>
 * @version : 1.0
 * @date    : 2013. 12. 11. 오전 10:53:25
 * @author  : byw
 * @brief   : 표준인터페이스 통신채널인증 응답 VO
 */

public class CommChAthnRespVO extends ComnRespVO
{
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
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
