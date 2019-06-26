package com.halfroom.distribution.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;

public class ProvinceCityBranchMapping extends Model<ProvinceCityBranchMapping> {
    private static final long serialVersionUID = 1L;
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String province;

    /**
     * 
     */
    private String city;

    /**
     * 
     */
    private Integer branchSaleId;

    /**
     * 
     * @return id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return province 
     */
    public String getProvince() {
        return province;
    }

    /**
     * 
     * @param province 
     */
    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    /**
     * 
     * @return city 
     */
    public String getCity() {
        return city;
    }

    /**
     * 
     * @param city 
     */
    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    /**
     * 
     * @return branch_sale_id 
     */
    public Integer getBranchSaleId() {
        return branchSaleId;
    }

    /**
     * 
     * @param branchSaleId 
     */
    public void setBranchSaleId(Integer branchSaleId) {
        this.branchSaleId = branchSaleId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}