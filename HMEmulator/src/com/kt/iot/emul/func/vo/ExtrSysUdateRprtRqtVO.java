/**
 * <PRE>
 *  Project : GWCommAgent
 *  Package : com.kt.smcp.gw.ca.gwadaptor.sif.inita.vo
 * </PRE>
 * @file   : SifLowSysUpdRprtRqtVO.java
 * @date   : 2013. 12. 11. 오후 1:31:10
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

import com.kt.iot.emul.func.vo.CommChDtlVO;
import com.kt.iot.emul.func.vo.ExtrSysDtlVO;
import com.kt.iot.emul.func.vo.ComnRqtVO;

/**
 * <PRE>
 *  ClassName : ExtrSysUdateRprtRqtVO
 * </PRE>
 * @version : 1.0
 * @date    : 2013. 12. 11. 오후 1:31:10
 * @author  : byw
 * @brief   : 표준인터페이스 하위시스템정보 갱신보고 요청 VO
 */

public class ExtrSysUdateRprtRqtVO extends ComnRqtVO{

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
	/** 외부시스템아이디 */
	private String	extrSysId;
	/** 정보갱신유형 */
	private String	infoUpdTypeCd;
	/** 서비스대상일련번호 */
	private Long	svcTgtSeq;	// 삭제예정
	/** 레벨번호 */
	private Integer	lvlNo;
	/** 상위연결아이디 */
	private String	upCnctId;
	/** IP주소 */
	private String	ipadr;
	/** 상태코드 */
	private String	sttusCd;
	/** 인증방식코드(그룹: 1005) */
	private String	athnFormlCd;
	/** 인증요청번호 */
	private String	athnRqtNo;
	/** 인증번호 */
	private String	athnNo;
	/** 사용여부 */
	private String	useYn;

	/** 외부시스템상세목록 */
	private List<ExtrSysDtlVO> extrSysDtlVOs = new ArrayList<ExtrSysDtlVO>();

	/** 통신채널정보리스트 */
	private List<CommChDtlVO> commChDtlVOs = new ArrayList<CommChDtlVO>();

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
	 * @return the svcTgtSeq
	 */
	public Long getSvcTgtSeq() {
		return svcTgtSeq;
	}

	/**
	 * @param svcTgtSeq the svcTgtSeq to set
	 */
	public void setSvcTgtSeq(Long svcTgtSeq) {
		this.svcTgtSeq = svcTgtSeq;
	}

	/**
	 * @return the lvlNo
	 */
	public Integer getLvlNo() {
		return lvlNo;
	}

	/**
	 * @param lvlNo the lvlNo to set
	 */
	public void setLvlNo(Integer lvlNo) {
		this.lvlNo = lvlNo;
	}

	/**
	 * @return the upCnctId
	 */
	public String getUpCnctId() {
		return upCnctId;
	}

	/**
	 * @param upCnctId the upCnctId to set
	 */
	public void setUpCnctId(String upCnctId) {
		this.upCnctId = upCnctId;
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
	 * @return the sttusCd
	 */
	public String getSttusCd() {
		return sttusCd;
	}

	/**
	 * @param sttusCd the sttusCd to set
	 */
	public void setSttusCd(String sttusCd) {
		this.sttusCd = sttusCd;
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
	 * @return the extrSysDtlVOs
	 */
	public List<ExtrSysDtlVO> getExtrSysDtlVOs() {
		return extrSysDtlVOs;
	}

	/**
	 * @param extrSysDtlVOs the extrSysDtlVOs to set
	 */
	public void setExtrSysDtlVOs(List<ExtrSysDtlVO> extrSysDtlVOs) {
		this.extrSysDtlVOs = extrSysDtlVOs;
	}

	/**
	 * @return the commChDtlVOs
	 */
	public List<CommChDtlVO> getCommChDtlVOs() {
		return commChDtlVOs;
	}

	/**
	 * @param commChDtlVOs the commChDtlVOs to set
	 */
	public void setCommChDtlVOs(List<CommChDtlVO> commChDtlVOs) {
		this.commChDtlVOs = commChDtlVOs;
	}

}
