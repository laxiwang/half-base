package com.halfroom.distribution.core.util;
import java.awt.image.BufferedImage;  
import java.io.File;  
import java.io.OutputStream;  
import java.util.Hashtable;  
import java.util.Random;  
  
import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;

import com.google.zxing.BarcodeFormat;  
import com.google.zxing.BinaryBitmap;  
import com.google.zxing.DecodeHintType;  
import com.google.zxing.EncodeHintType;  
import com.google.zxing.MultiFormatReader;  
import com.google.zxing.MultiFormatWriter;  
import com.google.zxing.Result;  
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;  
import com.google.zxing.common.BitMatrix;  
import com.google.zxing.common.HybridBinarizer;  
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
/**
 * 二维码工具类 
 * @author tingyunjava
 *
 */
public class QRCodeUtil {  
    private static final String CHARSET = "utf-8";  
    private static final String FORMAT = "JPG";  
    // 二维码尺寸  
    private static final int QRCODE_SIZE = 500;  
    
    public static final String APP_URL="http://m.half-room.com/h5/#/appUpLoad?type=appShare&user_id=";//分享下载app
    
    public static final String BOOK_URL="http://m.half-room.com/h5/#/course?approom=appTest&";// 体验码
    public static final String BOOK_URL_PAY="http://m.half-room.com/h5/#/course?approom=appPay&"; // 付费码

    public static String CARD_URL_PAY="http://m.half-room.com/h5/#/entitycard";//付费卡
    public static String CARD_URL_half="http://m.half-room.com/h5/#/halfcard";//半月卡
    
    
     /**
      * 
      * @param content  二维码扫码结果
      * @param logoPath  中央logo（头像）
      * @param needCompress   是否压缩 
      * @return
      * @throws Exception
      */
    public  static BufferedImage createImage(String content, String logoPath, boolean needCompress) throws Exception {  
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();  
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);  
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);  
        hints.put(EncodeHintType.MARGIN, 1);  
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE,  
                hints);  
        int width = bitMatrix.getWidth();  
        int height = bitMatrix.getHeight();  
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
        for (int x = 0; x < width; x++) {  
            for (int y = 0; y < height; y++) {  
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);  
            }  
        }  
        if (StringUtils.isBlank(logoPath)) {  
            return image;  
        }  
        return image;  
    }  
  
    /** 
     * 生成二维码(内嵌LOGO) 
     * 二维码文件名随机，文件名可能会有重复 
     *  
     * @param content 
     *            内容 
     * @param logoPath 
     *            LOGO地址 
     * @param destPath 
     *            存放目录 
     * @param needCompress 
     *            是否压缩LOGO 
     * @throws Exception 
     */  
    public static String encode(String content, String logoPath, String destPath, boolean needCompress) throws Exception {  
        BufferedImage image = QRCodeUtil.createImage(content, logoPath, needCompress);  
        mkdirs(destPath);  
        String fileName = new Random().nextInt(99999999) + "." + FORMAT.toLowerCase();  
        ImageIO.write(image, FORMAT, new File(destPath + "/" + fileName));  
        return fileName;  
    }  
      
    /** 
     * 生成二维码(内嵌LOGO) 
     * 调用者指定二维码文件名 
     *  
     * @param content 
     *            内容 
     * @param logoPath 
     *            LOGO地址 
     * @param destPath 
     *            存放目录 
     * @param fileName 
     *            二维码文件名 
     * @param needCompress 
     *            是否压缩LOGO 
     * @throws Exception 
     */  
    public static String encode(String content, String logoPath, String destPath, String fileName, boolean needCompress) throws Exception {  
        BufferedImage image = QRCodeUtil.createImage(content, logoPath, needCompress);  
        mkdirs(destPath);  
        fileName = fileName.substring(0, fileName.indexOf(".")>0?fileName.indexOf("."):fileName.length())   
                + "." + FORMAT.toLowerCase();  
        ImageIO.write(image, FORMAT, new File(destPath + "/" + fileName));  
        return fileName;  
    }  
  
    /** 
     * 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir． 
     * (mkdir如果父目录不存在则会抛出异常) 
     * @param destPath 
     *            存放目录 
     */  
    public static void mkdirs(String destPath) {  
        File file = new File(destPath);  
        if (!file.exists() && !file.isDirectory()) {  
            file.mkdirs();  
        }  
    }  
  
    /** 
     * 生成二维码(内嵌LOGO) 
     *  
     * @param content 
     *            内容 
     * @param logoPath 
     *            LOGO地址 
     * @param destPath 
     *            存储地址 
     * @throws Exception 
     */  
    public static String encode(String content, String logoPath, String destPath) throws Exception {  
        return QRCodeUtil.encode(content, logoPath, destPath, false);  
    }  
  
    /** 
     * 生成二维码 
     *  
     * @param content 
     *            内容 
     * @param destPath 
     *            存储地址 
     * @param needCompress 
     *            是否压缩LOGO 
     * @throws Exception 
     */  
    public static String encode(String content, String destPath, boolean needCompress) throws Exception {  
        return QRCodeUtil.encode(content, null, destPath, needCompress);  
    }  
  
    /** 
     * 生成二维码 
     *  
     * @param content 
     *            内容 
     * @param destPath 
     *            存储地址 
     * @throws Exception 
     */  
    public static String encode(String content, String destPath) throws Exception {  
        return QRCodeUtil.encode(content, null, destPath, false);  
    }  
  
    /** 
     * 生成二维码(内嵌LOGO) 
     *  
     * @param content 
     *            内容 
     * @param logoPath 
     *            LOGO地址 
     * @param output 
     *            输出流 
     * @param needCompress 
     *            是否压缩LOGO 
     * @throws Exception 
     */  
    public static void encode(String content, String logoPath, OutputStream output, boolean needCompress)  
            throws Exception {  
        BufferedImage image = QRCodeUtil.createImage(content, logoPath, needCompress);  
        ImageIO.write(image, FORMAT, output);  
    }  
  
    /** 
     * 生成二维码 
     *  
     * @param content 
     *            内容 
     * @param output 
     *            输出流 
     * @throws Exception 
     */  
    public static void encode(String content, OutputStream output) throws Exception {  
        QRCodeUtil.encode(content, null, output, false);  
    }  
  
    /** 
     * 解析二维码 
     *  
     * @param file 
     *            二维码图片 
     * @return 
     * @throws Exception 
     */  
    public static String decode(File file) throws Exception {  
        BufferedImage image;  
        image = ImageIO.read(file);  
        if (image == null) {  
            return null;  
        }  
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);  
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));  
        Result result;  
        Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();  
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);  
        result = new MultiFormatReader().decode(bitmap, hints);  
        String resultStr = result.getText();  
        return resultStr;  
    }  
  
    /** 
     * 解析二维码 
     *  
     * @param path 
     *            二维码图片地址 
     * @return 
     * @throws Exception 
     */  
    public static String decode(String path) throws Exception {  
        return QRCodeUtil.decode(new File(path));  
    }  
    public static void main(String[] args) throws Exception {  
    	
    	         //生成二维码流
    			BufferedImage qRcodebufferedImage=	QRCodeUtil.createImage("aaa", null, true);
    }  
    

} 
