package com.halfroom.distribution.persistence.enum_class;

import java.util.HashMap;
import java.util.Map;

/**
 * 会员卡划拨状态
 * 
 *
 */
public enum CardWholeSaleStatus {
	WAIT_CONFIRM(0, "待确认"), CONFIRMED(1, "已确认"), CANCELED(2, "已取消");

	private Integer index;
	private String name;

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private CardWholeSaleStatus(Integer index, String name) {
		this.index = index;
		this.name = name;
	}

	private CardWholeSaleStatus() {
	}
	
	public static CardWholeSaleStatus fromIndex(Integer index){
    	if(index == null) return null;
    	for( CardWholeSaleStatus status : CardWholeSaleStatus.values()){
			if(status.getIndex() == index){
				return status;
			}
        }
    	return null;
    }
	
	public static Map<Integer, String> optionMap(){
    	Map<Integer, String> result = new HashMap<Integer, String>();
    	for( CardWholeSaleStatus status : CardWholeSaleStatus.values()){
    		result.put(status.getIndex(), status.getName());
        }
    	return result;
    }
}
