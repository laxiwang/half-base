package com.halfroom.distribution.persistence.enum_class;

import java.util.HashMap;
import java.util.Map;

public enum CardStatus {
	UN_ACTIVATE(0, "未激活"), ACTIVATE(1, "已激活"), USED(2, "已使用");

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

	private CardStatus(Integer index, String name) {
		this.index = index;
		this.name = name;
	}

	private CardStatus() {
	}
	
	public static CardStatus fromIndex(Integer index){
    	if(index == null) return null;
    	for( CardStatus status : CardStatus.values()){
			if(status.getIndex() == index){
				return status;
			}
        }
    	return null;
    }
	
	public static Map<Integer, String> optionMap(){
    	Map<Integer, String> result = new HashMap<Integer, String>();
    	for( CardStatus status : CardStatus.values()){
    		result.put(status.getIndex(), status.getName());
        }
    	return result;
    }
}
