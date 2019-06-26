package com.halfroom.distribution.persistence.vo;

import com.halfroom.distribution.core.util.QRCodeUtil;

public class ShareBookUrlVo {
	private  String url = "";
	private Integer bookId;
	private Integer user_id;
	
	
	public ShareBookUrlVo() {
	
	}
	// type 0 体验 ，1 付费  //bookId=1&user_id=93
	public ShareBookUrlVo(Integer bookId,Integer user_id,Integer type) {
		if(type==0){
			url=QRCodeUtil.BOOK_URL+"bookId="+bookId+"&user_id="+user_id;
		}else if(type==1){
			url=QRCodeUtil.BOOK_URL_PAY+"bookId="+bookId+"&user_id="+user_id;
		}
		
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getBookId() {
		return bookId;
	}
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	
	
}
