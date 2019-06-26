package com.halfroom.distribution.core.util;

import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import com.halfroom.distribution.persistence.vo.ZipPictureEntryVo;

public class ZipUtil {
	
	
	/**
	 * 图片压缩包
	 * @param response
	 * @param packageName  包名
	 * @param pictures   图片组
	 * @throws Exception
	 */
	public static void packagePicture(HttpServletResponse response, String packageName, List<ZipPictureEntryVo> pictures)
			throws Exception {
		response.setContentType("application/zip");
		response.setHeader("Content-disposition",
				"attachment; filename=" + new String((packageName).getBytes(), "ISO-8859-1") + ".zip");
		OutputStream outputStream = response.getOutputStream();
		ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
		for (ZipPictureEntryVo zipEntry : pictures) {
			BufferedImage buffImg = QRCodeUtil.createImage(zipEntry.getUrl(), null, true);
			ZipEntry entry = zipEntry.getEntry();
			zipOutputStream.putNextEntry(entry);
			ImageIO.write(buffImg, "jpg", zipOutputStream);
			zipOutputStream.flush();
		}
		zipOutputStream.close();
		outputStream.flush();
		outputStream.close();
	}
}
