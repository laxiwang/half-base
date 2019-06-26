package com.halfroom.distribution.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

public class BookChapterPartComment  extends Model<BookChapterPartComment> {
    private static final long serialVersionUID = 1L;
    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private Integer bookId;

    /**
     * 
     */
    private Integer chapterId;

    /**
     * 
     */
    private Integer partId;

    /**
     * 
     */
    private String content;

    /**
     * 
     */
    private Long userId;

    /**
     * 
     */
    private Long atCommentId;

    /**
     * 0文字评论  1语音评论
     */
    private Byte type;

    /**
     * 该评论为语音评论时，文件的时长
     */
    private Integer timeSum;

    /**
     * 是否为热门评论，默认为0不是热门， 1为热门
     */
    private Byte isHotComment;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 
     * @return id 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     * @return book_id 
     */
    public Integer getBookId() {
        return bookId;
    }

    /**
     * 
     * @param bookId 
     */
    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    /**
     * 
     * @return chapter_id 
     */
    public Integer getChapterId() {
        return chapterId;
    }

    /**
     * 
     * @param chapterId 
     */
    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }

    /**
     * 
     * @return part_id 
     */
    public Integer getPartId() {
        return partId;
    }

    /**
     * 
     * @param partId 
     */
    public void setPartId(Integer partId) {
        this.partId = partId;
    }

    /**
     * 
     * @return content 
     */
    public String getContent() {
        return content;
    }

    /**
     * 
     * @param content 
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 
     * @return user_id 
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 
     * @param userId 
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 
     * @return at_comment_id 
     */
    public Long getAtCommentId() {
        return atCommentId;
    }

    /**
     * 
     * @param atCommentId 
     */
    public void setAtCommentId(Long atCommentId) {
        this.atCommentId = atCommentId;
    }

    /**
     * 0文字评论  1语音评论
     * @return type 0文字评论  1语音评论
     */
    public Byte getType() {
        return type;
    }

    /**
     * 0文字评论  1语音评论
     * @param type 0文字评论  1语音评论
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * 该评论为语音评论时，文件的时长
     * @return time_sum 该评论为语音评论时，文件的时长
     */
    public Integer getTimeSum() {
        return timeSum;
    }

    /**
     * 该评论为语音评论时，文件的时长
     * @param timeSum 该评论为语音评论时，文件的时长
     */
    public void setTimeSum(Integer timeSum) {
        this.timeSum = timeSum;
    }

    /**
     * 是否为热门评论，默认为0不是热门， 1为热门
     * @return is_hot_comment 是否为热门评论，默认为0不是热门， 1为热门
     */
    public Byte getIsHotComment() {
        return isHotComment;
    }

    /**
     * 是否为热门评论，默认为0不是热门， 1为热门
     * @param isHotComment 是否为热门评论，默认为0不是热门， 1为热门
     */
    public void setIsHotComment(Byte isHotComment) {
        this.isHotComment = isHotComment;
    }

    /**
     * 
     * @return create_time 
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * @param createTime 
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * @return update_time 
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * @param updateTime 
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}