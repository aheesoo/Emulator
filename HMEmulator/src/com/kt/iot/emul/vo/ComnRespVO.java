/**
 * <PRE>
 *  Project : GWCommAgent
 *  Package : com.kt.smcp.gw.ca.gwadaptor.sif.vo
 * </PRE>
 * @file   : SifComnRespVO.java
 * @date   : 2013. 12. 11. 오후 1:39:00
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

/**
 * <PRE>
 *  ClassName : ComnRespVO
 * </PRE>
 * @version : 1.0
 * @date    : 2013. 12. 11. 오후 1:39:00
 * @author  : byw
 * @brief   : 공통적으로 사용되는 응답 VO
 */

public class ComnRespVO
{
	/** 메세지헤더 */
	protected MsgHeadVO msgHeadVO;
	/** 응답코드 */
	protected String respCd;
	/** 응답메시지 */
	protected String respMsg;


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}


	/**
	 * @return the msgHeadVO
	 */
	public MsgHeadVO getMsgHeadVO() {
		return msgHeadVO;
	}
	/**
	 * @param msgHeadVO the msgHeadVO to set
	 */
	public void setMsgHeadVO(MsgHeadVO msgHeadVO) {
		this.msgHeadVO = msgHeadVO;
	}


	/**
	 * @return the respCd
	 */
	public String getRespCd() {
		return respCd;
	}


	/**
	 * @param respCd the respCd to set
	 */
	public void setRespCd(String respCd) {
		this.respCd = respCd;
	}


	/**
	 * @return the respMsg
	 */
	public String getRespMsg() {
		return respMsg;
	}


	/**
	 * @param respMsg the respMsg to set
	 */
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}

}
