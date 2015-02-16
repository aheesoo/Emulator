/**
 * <PRE>
 *  Project : GWCommAgent
 *  Package : com.kt.smcp.gw.ca.gwadaptor.sif.inita.vo
 * </PRE>
 * @file   : SifDevInfoRetvRespVO.java
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

import com.kt.iot.emul.vo.CmdDataInfoVO;
import com.kt.iot.emul.vo.ComnRespVO;

/**
 * <PRE>
 *  ClassName : DevInfoRetvRespVO
 * </PRE>
 * @version : 1.0
 * @date    : 2013. 12. 11. 오후 1:43:41
 * @author  : byw
 * @brief   : 표준인터페이스 장치정보조회 응답 VO
 */

public class DevInfoRetvRespVO extends ComnRespVO
{
	/** 명령데이터리스트(31) */
	private List<CmdDataInfoVO> cmdDataInfoVOs = new ArrayList<CmdDataInfoVO>();
	/** 장치정보목록 */
	private List<DevBasVO> devBasVOs = new ArrayList<DevBasVO>();

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}

	public List<CmdDataInfoVO> getCmdDataInfoVOs() {
		return cmdDataInfoVOs;
	}

	public void setCmdDataInfoVOs(List<CmdDataInfoVO> cmdDataInfoVOs) {
		this.cmdDataInfoVOs = cmdDataInfoVOs;
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
