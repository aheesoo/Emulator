/**
 * <PRE>
 *  Project : GWCommAgent
 *  Package : com.kt.smcp.gw.ca.gwadaptor.sif.qry.vo
 * </PRE>
 * @file   : SifLastValQueryRqtVO.java
 * @date   : 2013. 12. 11. 오후 3:59:28
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

import com.kt.iot.emul.vo.CmdDataInfoVO;
import com.kt.iot.emul.func.vo.ComnRqtVO;

/**
 * <PRE>
 *  ClassName : LastValQueryRqtVO
 * </PRE>
 * @version : 1.0
 * @date    : 2013. 12. 11. 오후 3:59:28
 * @author  : byw
 * @brief   : 표준인터페이스 최종값쿼리 요청 VO
 */

public class LastValQueryRqtVO extends ComnRqtVO{
	/** 외부시스템아이디*/
	private String extrSysId;
	/** 포함장치아이디목록 */
	private List<String> inclDevIds = new ArrayList<String>();
	/** 배타장치아이디목록 */
	private List<String> excluDevIds = new ArrayList<String>();
	/** 명령데이터리스트(31) */
	private List<CmdDataInfoVO> cmdDataInfoVOs = new ArrayList<CmdDataInfoVO>();

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
	 * @return the inclDevIds
	 */
	public List<String> getInclDevIds() {
		return inclDevIds;
	}
	/**
	 * @param inclDevIds the inclDevIds to set
	 */
	public void setInclDevIds(List<String> inclDevIds) {
		this.inclDevIds = inclDevIds;
	}
	/**
	 * @return the excluDevIds
	 */
	public List<String> getExcluDevIds() {
		return excluDevIds;
	}

	public List<CmdDataInfoVO> getCmdDataInfoVOs() {
		return cmdDataInfoVOs;
	}
	public void setCmdDataInfoVOs(List<CmdDataInfoVO> cmdDataInfoVOs) {
		this.cmdDataInfoVOs = cmdDataInfoVOs;
	}
	/**
	 * @param excluDevIds the excluDevIds to set
	 */
	public void setExcluDevIds(List<String> excluDevIds) {
		this.excluDevIds = excluDevIds;
	}
}
