package com.halfroom.distribution.core.util;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import com.halfroom.distribution.persistence.vo.Bucket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;

/**
 * 七牛工具类
 * @author tingyunjava
 *
 */
@SuppressWarnings({ "all"})
@Service
public class QiniuUtil {

    private static Logger logger = LoggerFactory.getLogger(QiniuUtil.class);



	/**
	 *
	 * 数据流上传
	 *
	 *   fileName 文件名
	 *   inputStream 文件字节数组
	 *
	 * 返回资源地址
	 * @throws UnsupportedEncodingException
	 */
	public static   String  upLoadForInputStream( Bucket bucket,String fileName,  byte[]  inputStream,String token) {
		//byte[] uploadBytes =inputStream.getBytes("utf-8");
		ByteArrayInputStream byteInputStream=new ByteArrayInputStream(inputStream);
		Auth auth = Auth.create(bucket.getAccessKey(), bucket.getSecretKey());
		UploadManager uploadManager = new UploadManager(bucket.getCfg());
		try {
			Response response = uploadManager.put(byteInputStream,fileName,token,null, null);
			//解析上传成功的结果
			DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
			return getRestUrl(bucket,putRet.key,3600*2);
		} catch (QiniuException ex) {
			Response r = ex.response;
			System.err.println(r.toString());
			try {
				System.err.println(r.bodyString());
			} catch (QiniuException ex2) {
			}
			return "";
		}
	}
	/**
	 * 删除文件
	 * @param fileName
	 */
	public  static void delFile(Bucket bucket,String fileName){
		Auth auth = Auth.create(bucket.getAccessKey(),bucket.getSecretKey());
		BucketManager bucketManager = new BucketManager(auth, bucket.getCfg());
		if(getFileInfoByKey(bucket,fileName)!=null){
			try {
				bucketManager.delete(bucket.getBucket(), fileName);
			} catch (QiniuException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("删除文件"+fileName);
		}
	}


	/**
	 * 获取七牛token
	 */
	public static String  getToken(Bucket bucket){
		String token="";
		Auth auth = Auth.create(bucket.getAccessKey(),bucket.getSecretKey());
		BucketManager bucketManager = new BucketManager(auth, bucket.getCfg());
		try {
			token=auth.uploadToken(bucket.getBucket());
		} catch (Exception e) {
			
		}
		return token;
	}
	
	
	/**
	 * 通过资源名称返回地址
	 * 
	 * @param fileName 文件名
	 * @param expireInSeconds 3600=1小时，可以自定义链接过期时间   
	 * @throws Exception 
	 * 
	 */
	public static String  getRestUrl(Bucket bucket,String fileName,long expireInSeconds)   {
		
		if(fileName==null||fileName.isEmpty()||fileName.contains("http")||fileName.contains("https")){
			return fileName;
		}
		if(getFileInfoByKey(bucket,fileName)==null){
			return null;
		}
		String domainOfBucket = bucket.getUrl();
		String encodedFileName=null;
		try {
			encodedFileName = URLEncoder.encode(fileName, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String publicUrl = String.format("%s/%s", domainOfBucket, encodedFileName);
		Auth auth = Auth.create(bucket.getAccessKey(),bucket.getSecretKey());
		String finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
		return finalUrl;
	}
	
	public static FileInfo getFileInfoByKey(Bucket bucket,String key)  {
		Auth auth = Auth.create(bucket.getAccessKey(),bucket.getSecretKey());
		BucketManager bucketManager = new BucketManager(auth, bucket.getCfg());
		FileInfo info = null;
		try {
			info = bucketManager.stat(bucket.getBucket(), key);
		} catch (QiniuException e) {
		}
		return info;
	}

	public static void  httpUrlupLoadFile(HttpServletResponse response, String fileUrl, String fileName){
		try{
			HttpURLConnection httpUrl = null;
			URL url = new URL(fileUrl);
			httpUrl = (HttpURLConnection) url.openConnection();
			httpUrl.connect();
			InputStream ins = httpUrl.getInputStream();
			/* 设置文件ContentType类型，这样设置，会自动判断下载文件类型 */
			response.setContentType("multipart/form-data");
			fileName= URLEncoder.encode(fileName, "UTF-8");
			/* 设置文件头：最后一个参数是设置下载文件名 */
			response.setHeader("Content-Disposition", "attachment;filename="+fileName);
			try{
				OutputStream os = response.getOutputStream();
				byte[] b = new byte[1024];
				int len;
				while((len = ins.read(b)) > 0){
					os.write(b,0,len);
				}
				os.flush();
				os.close();
				ins.close();
			}catch (IOException ioe){
				ioe.printStackTrace();
			}
		}catch (Exception e){

		}

	}
}
