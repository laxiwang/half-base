package com.halfroom.distribution.core.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 分会类型枚举
 * @author tingyunjava
 * @date   2018年11月23日11:00:59
 *
 */
public enum BranchSalerLevel {
	
	//0:总部  1:省级分会、2：市级分会、3：区县级分会 、4： 自由分会
	
	HQ_SALER("总会", 0),PROVINCE_SALER("省级分会", 1), CITY_SALER("市级分会", 2), COUNTY_SALER("区县级分会", 3), UNIT_SALER("自由分会", 4);
	private int index;
    private String name;
    
    private BranchSalerLevel(String name , int index){
        this.name = name ;
        this.index = index ;
    }
    
    public String getName() {
        return name;
    }
    
    public int getIndex() {
        return index;
    }
    
    public static BranchSalerLevel fromIndex(Integer index){
    	if(index == null) return null;
    	for( BranchSalerLevel type : BranchSalerLevel.values()){
			if(type.getIndex() == index){
				return type;
			}
        }
    	return null;
    }
    
    public static Map<Integer, String> optionMap(){
    	Map<Integer, String> result = new HashMap<Integer, String>();
    	for( BranchSalerLevel type : BranchSalerLevel.values()){
    		result.put(type.getIndex(), type.getName());
        }
    	return result;
    }
    
    public static Map<Integer, String> optionMap(BranchSalerLevel[] types){
    	Map<Integer, String> result = new HashMap<Integer, String>();
    	for( BranchSalerLevel type : types){
    		result.put(type.getIndex(), type.getName());
        }
    	return result;
    }
}
