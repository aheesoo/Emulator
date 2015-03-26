package com.kt.iot.emul.func.vo;


/**
 * 패키지정보
 * @since	: 2015. 1. 17.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2015. 1. 17. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
public class PkgInfoVO
{
	/** 패키지일련번호 */
	private Long pkgSeq;
	/** 패키지파일경로명 */
	private String pkgFilePathNm;
	/** 패키지명 */
	private String pkgNm;
	/** 패키지버전 */
	private String pkgVer;
	/** 패키지번호 */
	private Integer pkgVerNo;
	/**  패키지사이즈 */
	private Integer pkgSize;
	public Long getPkgSeq() {
		return pkgSeq;
	}
	public void setPkgSeq(Long pkgSeq) {
		this.pkgSeq = pkgSeq;
	}
	public String getPkgFilePathNm() {
		return pkgFilePathNm;
	}
	public void setPkgFilePathNm(String pkgFilePathNm) {
		this.pkgFilePathNm = pkgFilePathNm;
	}
	public String getPkgNm() {
		return pkgNm;
	}
	public void setPkgNm(String pkgNm) {
		this.pkgNm = pkgNm;
	}
	public String getPkgVer() {
		return pkgVer;
	}
	public void setPkgVer(String pkgVer) {
		this.pkgVer = pkgVer;
	}
	public Integer getPkgVerNo() {
		return pkgVerNo;
	}
	public void setPkgVerNo(Integer pkgVerNo) {
		this.pkgVerNo = pkgVerNo;
	}
	public Integer getPkgSize() {
		return pkgSize;
	}
	public void setPkgSize(Integer pkgSize) {
		this.pkgSize = pkgSize;
	}

}
