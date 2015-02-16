/**
 * <PRE>
 *  Project : GWCommAgent
 *  Package : com.kt.smcp.gw.ca.gwadaptor.sif.inita.vo
 * </PRE>
 * @file   : SifDevInfoUdateRprtRqtVO.java
 * @date   : 2013. 12. 11. 오후 1:43:41
 * @author : byw
 * @brief  :
 *  변경이력    :
 *        이름     : 일자          : 근거자료   : 변경내용
 *       ------------------------------------
 *        byw  : 2013. 12. 11.       :            : 신규 개발.
 */
package com.kt.iot.emul.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.iot.emul.vo.ComnRqtVO;

/**
 * <PRE>
 *  ClassName : DevInfoUdateRprtRqtVO
 * </PRE>
 * @version : 1.0
 * @date    : 2013. 12. 11. 오후 1:43:41
 * @author  : byw
 * @brief   : 표준인터페이스 장치갱신보고 요청 VO
 */

public class DevInfoUdateRprtRqtVO extends ComnRqtVO
{
	/** 외부시스템아이디 */
	private String extrSysId;
	/** 정보갱신유형 */
	private String	infoUpdTypeCd;
	/** 장치정보목록 */
	private List<DevBasVO> devBasVOs = new ArrayList<DevBasVO>();

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

	public String getInfoUpdTypeCd() {
		return infoUpdTypeCd;
	}
	public void setInfoUpdTypeCd(String infoUpdTypeCd) {
		this.infoUpdTypeCd = infoUpdTypeCd;
	}
	/**
	 * @return the devBasVOs
	 */
	public List<DevBasVO> getDevBasVOs() {
		return devBasVOs;
	}
	/**
	 * @param devBasVOs the devBasVOs to set
	 */
	public void setDevBasVOs(List<DevBasVO> devBasVOs) {
		this.devBasVOs = devBasVOs;
	}


}
