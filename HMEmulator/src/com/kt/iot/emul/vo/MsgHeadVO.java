/**
 * <PRE>
 *  Project : GWCommAgent
 *  Package : com.kt.smcp.gw.ca.gwfrwk.stdsys.vo
 * </PRE>
 * @file   : MsgHeadVO.java
 * @date   : 2014. 4. 30. 오후 2:09:32
 * @author : CBJ
 * @brief  :
 *  변경이력    :
 *        이름     : 일자          : 근거자료   : 변경내용
 *       ------------------------------------
 *        CBJ  : 2014. 4. 30.       :            : 신규 개발.
 */
package com.kt.iot.emul.vo;

import java.util.HashMap;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * <PRE>
 *  ClassName : MsgHeadVO
 * </PRE>
 * @version : 1.0
 * @date    : 2014. 4. 30. 오후 2:09:32
 * @author  : CBJ
 * @brief   :
 */
public class MsgHeadVO
{
	/** 프로토콜 버전 */
	private String protVer;
	/** 헤더 타입 */
	private String hdrType;
	/** 헤더 길이 */
	private Short hdrLen;
	/** 메시지 유형 */
	private String msgType;
	/** 메시지 교환 패턴 */
	private String msgExchPtrn;
	/** 기능 유형 */
	private String methodType;
	/** 전송트랜잭션아이디 */
	private String trmTransacId;
	/** 통신채널인증토큰 */
	private String commChAthnNo;
	/** 암호화 유형 */
	private String ecodType;
	/** 압축 유형 */
	private String cmpreType;
	/** 인코딩 타입 */
	private String encdngType;
	/** 헤더확장필드 */
	private HashMap<String, Object> mapHeaderExtension = new HashMap<String, Object>();


	public String getProtVer() {
		return protVer;
	}
	public void setProtVer(String protVer) {
		this.protVer = protVer;
	}
	public String getHdrType() {
		return hdrType;
	}
	public void setHdrType(String hdrType) {
		this.hdrType = hdrType;
	}
	public Short getHdrLen() {
		return hdrLen;
	}
	public void setHdrLen(Short hdrLen) {
		this.hdrLen = hdrLen;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getMsgExchPtrn() {
		return msgExchPtrn;
	}
	public void setMsgExchPtrn(String msgExchPtrn) {
		this.msgExchPtrn = msgExchPtrn;
	}
	public String getMethodType() {
		return methodType;
	}
	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}
	public String getEcodType() {
		return ecodType;
	}
	public void setEcodType(String ecodType) {
		this.ecodType = ecodType;
	}
	public String getCmpreType() {
		return cmpreType;
	}
	public void setCmpreType(String cmpreType) {
		this.cmpreType = cmpreType;
	}
	public String getEncdngType() {
		return encdngType;
	}
	public void setEncdngType(String encdngType) {
		this.encdngType = encdngType;
	}
	public HashMap<String, Object> getMapHeaderExtension() {
		return mapHeaderExtension;
	}
	public void setMapHeaderExtension(HashMap<String, Object> mapHeaderExtension) {
		this.mapHeaderExtension = mapHeaderExtension;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
	/**
	 * @return the trmTransacId
	 */
	public String getTrmTransacId() {
		return trmTransacId;
	}
	/**
	 * @param trmTransacId the trmTransacId to set
	 */
	public void setTrmTransacId(String trmTransacId) {
		this.trmTransacId = trmTransacId;
	}
	/**
	 * @return the commChAthnNo
	 */
	public String getCommChAthnNo() {
		return commChAthnNo;
	}
	/**
	 * @param commChAthnNo the commChAthnNo to set
	 */
	public void setCommChAthnNo(String commChAthnNo) {
		this.commChAthnNo = commChAthnNo;
	}



}
