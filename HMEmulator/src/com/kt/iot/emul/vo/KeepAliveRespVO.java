package com.kt.iot.emul.vo;

import com.kt.iot.emul.vo.ComnRespVO;


public class KeepAliveRespVO extends ComnRespVO
{
	/** KeepAlive주기 */
	private Integer keepAliveCycl;

	public Integer getKeepAliveCycl() {
		return keepAliveCycl;
	}

	public void setKeepAliveCycl(Integer keepAliveCycl) {
		this.keepAliveCycl = keepAliveCycl;
	}

}
