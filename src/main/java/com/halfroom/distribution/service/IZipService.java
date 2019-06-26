package com.halfroom.distribution.service;

import javax.servlet.http.HttpServletResponse;

/**
 * 压缩包
 * @author tingyunjava
 *
 */
public interface IZipService {
	/**
	 * 导出用户二维码列表
	 * @param response
	 * @param userid
	 * @return -1 用户不存在
	 */
	int  exportSalrQRcode(HttpServletResponse response,Integer userid);
	
	/**
	 * 通过生产记录
	 * 导出实体卡二维码
	 * @param response
	 * @param id
	 */
	void exportPicture(HttpServletResponse response,Integer id);
	
	/**
	 * 通过接收划拨记录
	 * 导出半月卡二维码
	 * @param response
	 * @param id
	 */
	void exportHalfPicture(HttpServletResponse response,Integer id);

	/**
	 * 通过卡号
	 * 导出卡二维码
	 * @param response
	 * @param card_no
	 */
	void exportCardPicture(HttpServletResponse response,Integer card_no);
}
