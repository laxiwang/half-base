package com.halfroom.distribution.persistence.vo;

import java.util.zip.ZipEntry;
/**
 * 打包实体  url 二维码地址  entry 压缩包中的节点
 * @author tingyunjava
 *
 */
public class ZipPictureEntryVo {
	private String  url;
	private ZipEntry entry;
	
	public ZipPictureEntryVo() {
	}
	
	public ZipPictureEntryVo(String  url,ZipEntry entry) {
		this.url=url;
		this.entry=entry;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public ZipEntry getEntry() {
		return entry;
	}
	public void setEntry(ZipEntry entry) {
		this.entry = entry;
	}
	
	
	
}
