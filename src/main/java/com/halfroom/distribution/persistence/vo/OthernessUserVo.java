package com.halfroom.distribution.persistence.vo;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;

public class OthernessUserVo  extends Model<OthernessUserVo>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String city;
	private Integer sum;
	private Integer fullmembers;
	private Integer  sup;
	private Integer  suped;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Integer getSum() {
		return sum;
	}
	public void setSum(Integer sum) {
		this.sum = sum;
	}

	public Integer getFullmembers() {
		return fullmembers;
	}
	public void setFullmembers(Integer fullmembers) {
		this.fullmembers = fullmembers;
	}
	public Integer getSup() {
		return sup;
	}
	public void setSup(Integer sup) {
		this.sup = sup;
	}
	public Integer getSuped() {
		return suped;
	}
	public void setSuped(Integer suped) {
		this.suped = suped;
	}
	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
