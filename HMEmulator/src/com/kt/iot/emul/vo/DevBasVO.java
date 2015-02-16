/**
 * <PRE>
 *  Project : GWCommAgent
 *  Package : com.kt.smcp.gw.ca.gwadaptor.sif.inita.vo
 * </PRE>
 * @file   : DevInfoVO.java
 * @date   : 2013. 12. 11. 오후 1:44:28
 * @author : byw
 * @brief  :
 *  변경이력    :
 *        이름     : 일자          : 근거자료   : 변경내용
 *       ------------------------------------
 *        byw  : 2013. 12. 11.       :            : 신규 개발.
 */
package com.kt.iot.emul.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.iot.emul.vo.DevDtlVO;
import com.kt.iot.emul.vo.BinSetupDataInfoVO;
import com.kt.iot.emul.vo.DataTypeVO;
import com.kt.iot.emul.vo.GenlSetupDataInfoVO;
import com.kt.iot.emul.vo.SclgSetupDataInfoVO;

/**
 * <PRE>
 *  ClassName : DevInfoVO
 * </PRE>
 *
 * @version : 1.0
 * @date : 2013. 12. 11. 오후 1:44:28
 * @author : byw
 * @brief : 장치정보 VO
 */

public class DevBasVO {

	/** 외부시스템아이디 */
	private String	extrSysId;	//AK
	/** M2M서비스번호 */
	private Integer	m2mSvcNo;	//AK
	/** 장치아이디 */
	private String	devId;		//AK
	/** 상위외부시스템아이디 */
	private String	upExtrSysId;
	/** 상위M2M서비스번호 */
	private Integer	upM2mSvcNo;
	/** 상위장치아이디 */
	private String	upDevId;
	/** 장치명 */
	private String	devNm;
	/** IP주소 */
	private String	ipadr;
	/** 상태코드(그룹: 1201) */
	private String	sttusCd;
	/** 제조사명 */
	private String	makrNm;
	/** 모델명 */
	private String	modelNm;
	/** 상품일련번호 */
	private String	prodSeq;
	/** 등록일련번호 */
	private String	regSeq;
	/** 펌웨어버전번호 */
	private String	frmwrVerNo;
	/** 인증방식코드(그룹: 1005) */
	private String	athnFormlCd;
	/** 인증요청번호 */
	private String	athnRqtNo;
	/** 인증번호 */
	private String	athnNo;
	/** 설치위치내용 */
	private String	eqpLoSbst;
	/** 생성일시 */
	private Date	cretDt;
	/** 수정일시 */
	private Date	amdDt;
	/** 최종동작일시 */
	private Date	lastMtnDt;
	/** 사용여부 */
	private String	useYn;
	/** 삭제여부 */
	private String delYn;

	/** 데이터유형정보목록 */
	List<DataTypeVO> dataTypeVOs = new ArrayList<DataTypeVO>();
	/** 현장장치상세정보 */
	List<DevDtlVO> devDtlVOs = new ArrayList<DevDtlVO>();
	/** 채널정보리스트 */
	List<DevCommChDtlVO> devCommChDtlVOs = new ArrayList<DevCommChDtlVO>();
	/** 일반설정데이터(80) */
	private List<GenlSetupDataInfoVO> genlSetupDataInfoVOs = new ArrayList<GenlSetupDataInfoVO>();
	/** 스케줄설정데이터(81) */
	private List<SclgSetupDataInfoVO> sclgSetupDataInfoVOs = new ArrayList<SclgSetupDataInfoVO>();
	/** 이진설정데이터(82) */
	private List<BinSetupDataInfoVO> binSetupDataInfoVOs = new ArrayList<BinSetupDataInfoVO>();


	public List<DevDtlVO> getDevDtlVOs() {
		return devDtlVOs;
	}
	public void setDevDtlVOs(List<DevDtlVO> devDtlVOs) {
		this.devDtlVOs = devDtlVOs;
	}
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
	 * @return the devId
	 */
	public String getDevId() {
		return devId;
	}

	/**
	 * @param devId the devId to set
	 */
	public void setDevId(String devId) {
		this.devId = devId;
	}

	/**
	 * @return the upExtrSysId
	 */
	public String getUpExtrSysId() {
		return upExtrSysId;
	}

	/**
	 * @param upExtrSysId the upExtrSysId to set
	 */
	public void setUpExtrSysId(String upExtrSysId) {
		this.upExtrSysId = upExtrSysId;
	}

	/**
	 * @return the upM2mSvcNo
	 */
	public Integer getUpM2mSvcNo() {
		return upM2mSvcNo;
	}

	/**
	 * @param upM2mSvcNo the upM2mSvcNo to set
	 */
	public void setUpM2mSvcNo(Integer upM2mSvcNo) {
		this.upM2mSvcNo = upM2mSvcNo;
	}

	/**
	 * @return the upDevId
	 */
	public String getUpDevId() {
		return upDevId;
	}

	/**
	 * @param upDevId the upDevId to set
	 */
	public void setUpDevId(String upDevId) {
		this.upDevId = upDevId;
	}

	/**
	 * @return the devNm
	 */
	public String getDevNm() {
		return devNm;
	}

	/**
	 * @param devNm the devNm to set
	 */
	public void setDevNm(String devNm) {
		this.devNm = devNm;
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
	 * @return the makrNm
	 */
	public String getMakrNm() {
		return makrNm;
	}

	/**
	 * @param makrNm the makrNm to set
	 */
	public void setMakrNm(String makrNm) {
		this.makrNm = makrNm;
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
	 * @return the prodSeq
	 */
	public String getProdSeq() {
		return prodSeq;
	}

	/**
	 * @param prodSeq the prodSeq to set
	 */
	public void setProdSeq(String prodSeq) {
		this.prodSeq = prodSeq;
	}

	/**
	 * @return the regSeq
	 */
	public String getRegSeq() {
		return regSeq;
	}

	/**
	 * @param regSeq the regSeq to set
	 */
	public void setRegSeq(String regSeq) {
		this.regSeq = regSeq;
	}

	/**
	 * @return the frmwrVerNo
	 */
	public String getFrmwrVerNo() {
		return frmwrVerNo;
	}

	/**
	 * @param frmwrVerNo the frmwrVerNo to set
	 */
	public void setFrmwrVerNo(String frmwrVerNo) {
		this.frmwrVerNo = frmwrVerNo;
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
	 * @return the eqpLoSbst
	 */
	public String getEqpLoSbst() {
		return eqpLoSbst;
	}

	/**
	 * @param eqpLoSbst the eqpLoSbst to set
	 */
	public void setEqpLoSbst(String eqpLoSbst) {
		this.eqpLoSbst = eqpLoSbst;
	}

	/**
	 * @return the cretDt
	 */
	public Date getCretDt() {
		return cretDt;
	}

	/**
	 * @param cretDt the cretDt to set
	 */
	public void setCretDt(Date cretDt) {
		this.cretDt = cretDt;
	}

	/**
	 * @return the amdDt
	 */
	public Date getAmdDt() {
		return amdDt;
	}

	/**
	 * @param amdDt the amdDt to set
	 */
	public void setAmdDt(Date amdDt) {
		this.amdDt = amdDt;
	}

	/**
	 * @return the lastMtnDt
	 */
	public Date getLastMtnDt() {
		return lastMtnDt;
	}

	/**
	 * @param lastMtnDt the lastMtnDt to set
	 */
	public void setLastMtnDt(Date lastMtnDt) {
		this.lastMtnDt = lastMtnDt;
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



	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	/**
	 * @return the dataTypeVOs
	 */
	public List<DataTypeVO> getDataTypeVOs() {
		return dataTypeVOs;
	}

	/**
	 * @param dataTypeVOs the dataTypeVOs to set
	 */
	public void setDataTypeVOs(List<DataTypeVO> dataTypeVOs) {
		this.dataTypeVOs = dataTypeVOs;
	}

	/**
	 * @return the devCommChDtlVOs
	 */
	public List<DevCommChDtlVO> getDevCommChDtlVOs() {
		return devCommChDtlVOs;
	}

	/**
	 * @param devCommChDtlVOs the devCommChDtlVOs to set
	 */
	public void setDevCommChDtlVOs(List<DevCommChDtlVO> devCommChDtlVOs) {
		this.devCommChDtlVOs = devCommChDtlVOs;
	}
	public List<GenlSetupDataInfoVO> getGenlSetupDataInfoVOs() {
		return genlSetupDataInfoVOs;
	}
	public void setGenlSetupDataInfoVOs(
			List<GenlSetupDataInfoVO> genlSetupDataInfoVOs) {
		this.genlSetupDataInfoVOs = genlSetupDataInfoVOs;
	}
	public List<SclgSetupDataInfoVO> getSclgSetupDataInfoVOs() {
		return sclgSetupDataInfoVOs;
	}
	public void setSclgSetupDataInfoVOs(
			List<SclgSetupDataInfoVO> sclgSetupDataInfoVOs) {
		this.sclgSetupDataInfoVOs = sclgSetupDataInfoVOs;
	}
	public List<BinSetupDataInfoVO> getBinSetupDataInfoVOs() {
		return binSetupDataInfoVOs;
	}
	public void setBinSetupDataInfoVOs(List<BinSetupDataInfoVO> binSetupDataInfoVOs) {
		this.binSetupDataInfoVOs = binSetupDataInfoVOs;
	}





}
