/**
 * <PRE>
 *  Project : GWCommAgent
 *  Package : com.kt.smcp.gw.ca.gwadaptor.sif.vo
 * </PRE>
 * @file   : SifCommChAthnRqtVO.java
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

import com.kt.iot.emul.vo.ComnRqtVO;

/**
 * <PRE>
 *  ClassName : CommChAthnRqtVO
 * </PRE>
 * @version : 1.0
 * @date    : 2013. 12. 11. 오전 10:53:25
 * @author  : byw
 * @brief   : 표준인터페이스 통신채널인증 요청 VO
 */

public class CommChAthnRqtVO extends ComnRqtVO
{
	/** 외부시스템아이디 */
	private String	extrSysId;
	/** 통신채널아이디 */
	private String	commChId;
	/** 인증요청번호 */
	private String	athnRqtNo;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
	/**
	 * @return the extrSysId
	 */
	public String getExtrSysId() {
		return extrSysId;
	}
	/**
	 * @param extrSysId the extrSysId to set
	 */
	public void setExtrSysId(String extrSysId) {
		this.extrSysId = extrSysId;
	}
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

}
