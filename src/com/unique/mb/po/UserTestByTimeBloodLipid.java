package com.unique.mb.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 测试实体
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年9月2日上午11:26:31
 * 修改日期:2015年9月2日上午11:26:31
 */
public class UserTestByTimeBloodLipid extends UserTestByTime{
	public BigDecimal getTg() {
		return tg;
	}
	public void setTg(BigDecimal tg) {
		this.tg = tg;
	}
	public BigDecimal getTc() {
		return tc;
	}
	public void setTc(BigDecimal tc) {
		this.tc = tc;
	}
	public BigDecimal getHdlC() {
		return hdlC;
	}
	public void setHdlC(BigDecimal hdlC) {
		this.hdlC = hdlC;
	}
	public BigDecimal getLdlC() {
		return ldlC;
	}
	public void setLdlC(BigDecimal ldlC) {
		this.ldlC = ldlC;
	}
	public BigDecimal getTcDivisionHdlC() {
		return tcDivisionHdlC;
	}
	public void setTcDivisionHdlC(BigDecimal tcDivisionHdlC) {
		this.tcDivisionHdlC = tcDivisionHdlC;
	}
	private BigDecimal tg;
    private BigDecimal tc;
    private BigDecimal hdlC;
    private BigDecimal ldlC;
    private BigDecimal tcDivisionHdlC;
}