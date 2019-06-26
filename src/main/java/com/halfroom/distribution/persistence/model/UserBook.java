package com.halfroom.distribution.persistence.model;

import java.util.Date;

import com.halfroom.distribution.core.util.DateTimeUtil;



public class UserBook {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private Integer userId;

    /**
     * 
     */
    private Integer bookId;

    /**
     * 1 七天 2永久
     */
    private Integer type;

    /**
     * 开始日期
     */
    private Date startTime;

    /**
     * 到期日期
     */
    private Date endTime;
    
    public UserBook(){}
    public UserBook(int userid,int bookid,int type){
    	this.userId=userid;
    	this.bookId=bookid;
    	this.type=type;
    	this.startTime=new Date();
    	switch (type) {
		case 1:
			this.endTime=DateTimeUtil.addDay(startTime, 7);
			break;
		case 2:
			this.endTime=DateTimeUtil.addYear(startTime, 100);
			break;
		default:
			this.endTime=DateTimeUtil.addDay(startTime, 7);
			break;
		}
    }
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
     * @return userid 
     */
    public Integer getUserid() {
        return userId;
    }

    /**
     * 
     * @param userid 
     */
    public void setUserid(Integer userid) {
        this.userId = userid;
    }

    /**
     * 
     * @return bookid 
     */
    public Integer getBookid() {
        return bookId;
    }

    /**
     * 
     * @param bookid 
     */
    public void setBookid(Integer bookid) {
        this.bookId = bookid;
    }

    /**
     * 1 三天 2永久
     * @return type 1 三天 2永久
     */
    public Integer getType() {
        return type;
    }

    /**
     * 1 三天 2永久
     * @param type 1 三天 2永久
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 开始日期
     * @return starttime 开始日期
     */
    public Date getStarttime() {
        return startTime;
    }

    /**
     * 开始日期
     * @param endTime 开始日期
     */
    public void setStarttime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 到期日期
     * @return endtime 到期日期
     */
    public Date getEndtime() {
        return endTime;
    }

    /**
     * 到期日期
     * @param endTime 到期日期
     */
    public void setEndtime(Date endTime) {
        this.endTime = endTime;
    }
}