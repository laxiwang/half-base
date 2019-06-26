package com.halfroom.distribution.persistence.vo;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;

public class OthernessOrderVo  extends Model<OthernessOrderVo>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String city;
	private Integer sum;
	private Integer paySource0;
	private Integer paySource1;
	private Integer paySource2;
	private Integer paySource3;
	
	
	public Integer getSum() {
		return sum;
	}


	public void setSum(Integer sum) {
		this.sum = sum;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public Integer getPaySource0() {
		return paySource0;
	}


	public void setPaySource0(Integer paySource0) {
		this.paySource0 = paySource0;
	}


	public Integer getPaySource1() {
		return paySource1;
	}


	public void setPaySource1(Integer paySource1) {
		this.paySource1 = paySource1;
	}


	public Integer getPaySource2() {
		return paySource2;
	}


	public void setPaySource2(Integer paySource2) {
		this.paySource2 = paySource2;
	}


	public Integer getPaySource3() {
		return paySource3;
	}


	public void setPaySource3(Integer paySource3) {
		this.paySource3 = paySource3;
	}


	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
