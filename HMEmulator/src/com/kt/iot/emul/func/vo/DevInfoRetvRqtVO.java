/**
 * <PRE>
 *  Project : GWCommAgent
 *  Package : com.kt.smcp.gw.ca.gwadaptor.sif.inita.vo
 * </PRE>
 * @file   : SifDevInfoRetvRqtVO.java
 * @date   : 2013. 12. 11. 오후 1:39:59
 * @author : byw
 * @brief  :
 *  변경이력    :
 *        이름     : 일자          : 근거자료   : 변경내용
 *       ------------------------------------
 *        byw  : 2013. 12. 11.       :            : 신규 개발.
 */
package com.kt.iot.emul.func.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.iot.emul.vo.CmdDataInfoVO;
import com.kt.iot.emul.func.vo.ComnRqtVO;

/**
 * <PRE>
 *  ClassName : DevInfoRetvRqtVO
 * </PRE>
 * @version : 1.0
 * @date    : 2013. 12. 11. 오후 1:39:59
 * @author  : byw
 * @brief   : 표준인터페이스 장치정보조회 요청 VO
 */

public class DevInfoRetvRqtVO extends ComnRqtVO{
	/** 외부시스템아이디 */
	private String extrSysId;
	/** M2M서비스번호 */
	private Integer	m2mSvcNo;
	/** 포함장치아이디목록 */
	private List<String> inclDevIds = new ArrayList<String>();
	/** 배타장치아이디목록 */
	private List<String> excluDevIds = new ArrayList<String>();
	/** 명령데이터리스트(31) */
	private List<CmdDataInfoVO> cmdDataInfoVOs = new ArrayList<CmdDataInfoVO>();
	/** 모델명 */
	private String modelNm;
	/** 사용여부 */
	private String useYn;
	/** 생성일시시작 */
	private Date cretDtSt;
	/** 생성일시종료 */
	private Date cretDtFns;
	/** 수정일시시작 */
	private Date amdDtSt;
	/** 수정일시종료 */
	private Date amdDtFns;

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
	 * @return the m2mSvcNo
	 */
	public Integer getM2mSvcNo() {
		return m2mSvcNo;
	}
	/**
	 * @param m2mSvcNo the m2mSvcNo to set
	 */
	public void setM2mSvcNo(Integer m2mSvcNo) {
		this.m2mSvcNo = m2mSvcNo;
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
	/**
	 * @return the modelNm
	 */
	public String getModelNm() {
		return modelNm;
	}
	/**
	 * @param modelNm the modelNm to set
	 */
	public void setModelNm(String modelNm) {
		this.modelNm = modelNm;
	}
	/**
	 * @return the useYn
	 */
	public String getUseYn() {
		return useYn;
	}
	/**
	 * @param useYn the useYn to set
	 */
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	/**
	 * @return the cretDtSt
	 */
	public Date getCretDtSt() {
		return cretDtSt;
	}
	/**
	 * @param cretDtSt the cretDtSt to set
	 */
	public void setCretDtSt(Date cretDtSt) {
		this.cretDtSt = cretDtSt;
	}
	/**
	 * @return the cretDtFns
	 */
	public Date getCretDtFns() {
		return cretDtFns;
	}
	/**
	 * @param cretDtFns the cretDtFns to set
	 */
	public void setCretDtFns(Date cretDtFns) {
		this.cretDtFns = cretDtFns;
	}
	/**
	 * @return the amdDtSt
	 */
	public Date getAmdDtSt() {
		return amdDtSt;
	}
	/**
	 * @param amdDtSt the amdDtSt to set
	 */
	public void setAmdDtSt(Date amdDtSt) {
		this.amdDtSt = amdDtSt;
	}
	/**
	 * @return the amdDtFns
	 */
	public Date getAmdDtFns() {
		return amdDtFns;
	}
	/**
	 * @param amdDtFns the amdDtFns to set
	 */
	public void setAmdDtFns(Date amdDtFns) {
		this.amdDtFns = amdDtFns;
	}

}
