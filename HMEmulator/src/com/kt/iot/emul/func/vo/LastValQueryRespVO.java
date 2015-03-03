/**
 * <PRE>
 *  Project : GWCommAgent
 *  Package : com.kt.smcp.gw.ca.gwadaptor.sif.qry.vo
 * </PRE>
 * @file   : SifLastValQueryRespVO.java
 * @date   : 2013. 12. 11. 오후 3:59:40
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
import com.kt.iot.emul.func.vo.ComnRespVO;
import com.kt.iot.emul.func.vo.ItgColecDataVO.DevColecDataVO;

/**
 * <PRE>
 *  ClassName : LastValQueryRespVO
 * </PRE>
 * @version : 1.0
 * @date    : 2013. 12. 11. 오후 3:59:40
 * @author  : byw
 * @brief   : 표준인터페이스 최종값쿼리 응답 VO
 */

public class LastValQueryRespVO extends ComnRespVO{
	/** 외부시스템아이디 */
	private String extrSysId;
	/** 명령데이터리스트(31) */
	private List<CmdDataInfoVO> cmdDataInfoVOs = new ArrayList<CmdDataInfoVO>();
	/** 장비수집데이터리스트 */
	private List<DevColecDataVO> devColecDataVOs = new ArrayList<DevColecDataVO>();

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

	public List<CmdDataInfoVO> getCmdDataInfoVOs() {
		return cmdDataInfoVOs;
	}
	public void setCmdDataInfoVOs(List<CmdDataInfoVO> cmdDataInfoVOs) {
		this.cmdDataInfoVOs = cmdDataInfoVOs;
	}
	/**
	 * @return the devColecDataVOs
	 */
	public List<DevColecDataVO> getDevColecDataVOs() {
		return devColecDataVOs;
	}
	/**
	 * @param devColecDataVOs the devColecDataVOs to set
	 */
	public void setDevColecDataVOs(List<DevColecDataVO> devColecDataVOs) {
		this.devColecDataVOs = devColecDataVOs;
	}

}
