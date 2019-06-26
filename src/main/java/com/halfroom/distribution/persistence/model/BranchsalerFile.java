package com.halfroom.distribution.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

public class BranchsalerFile extends Model<BranchsalerFile> {

    private static final long serialVersionUID = 1L;




    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 文件名称--文件夹名称
     */
    private String fileName;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 0 全部可见  1总部可以见 2省级可见 3 市级可见 4区级可见 5小渠道可见
     */
    private Integer fileRole;

    /**
     * 0  文件 1 文件夹
     */
    private Integer isFolder;

    /**
     * 文件夹id
     */
    private Integer folderId;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 文件地址
     */
    private String fileUrl;

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
     * 文件名称--文件夹名称
     * @return file_name 文件名称--文件夹名称
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 文件名称--文件夹名称
     * @param fileName 文件名称--文件夹名称
     */
    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    /**
     * 文件类型
     * @return file_type 文件类型
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * 文件类型
     * @param fileType 文件类型
     */
    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
    }

    /**
     * 0 全部可见  1总部可以见 2省级可见 3 市级可见 4区级可见 5小渠道可见
     * @return file_role 0 全部可见  1总部可以见 2省级可见 3 市级可见 4区级可见 5小渠道可见
     */
    public Integer getFileRole() {
        return fileRole;
    }

    /**
     * 0 全部可见  1总部可以见 2省级可见 3 市级可见 4区级可见 5小渠道可见
     * @param fileRole 0 全部可见  1总部可以见 2省级可见 3 市级可见 4区级可见 5小渠道可见
     */
    public void setFileRole(Integer fileRole) {
        this.fileRole = fileRole;
    }

    /**
     * 0  文件 1 文件夹
     * @return is_folder 0  文件 1 文件夹
     */
    public Integer getIsFolder() {
        return isFolder;
    }

    /**
     * 0  文件 1 文件夹
     * @param isFolder 0  文件 1 文件夹
     */
    public void setIsFolder(Integer isFolder) {
        this.isFolder = isFolder;
    }

    /**
     * 文件夹id
     * @return folder_id 文件夹id
     */
    public Integer getFolderId() {
        return folderId;
    }

    /**
     * 文件夹id
     * @param folderId 文件夹id
     */
    public void setFolderId(Integer folderId) {
        this.folderId = folderId;
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

    /**
     * 文件地址
     * @return file_url 文件地址
     */
    public String getFileUrl() {
        return fileUrl;
    }

    /**
     * 文件地址
     * @param fileUrl 文件地址
     */
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl == null ? null : fileUrl.trim();
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BranchsalerFile{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", fileRole=" + fileRole +
                ", isFolder=" + isFolder +
                ", folderId=" + folderId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", fileUrl='" + fileUrl + '\'' +
                '}';
    }
}