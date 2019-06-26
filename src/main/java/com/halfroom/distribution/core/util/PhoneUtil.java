package com.halfroom.distribution.core.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.alibaba.fastjson.JSONObject;

public class PhoneUtil {

	private static final String host = "http://mobsec-dianhua.baidu.com";
	private static final String path = "/dianhua_api/open/location";
	private static final String method = "GET";

	public static void main(String[] args) {
		System.out.println(getProvinceAndCityByPhone("17606195777").get("province"));
	}


	private static final String host1 = "http://apis.juhe.cn/mobile/get";
	private static final String appcode1 = "dcf3235077a4bbb57ae5f400da22e59f";
	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 30000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

	public static Map<String, String> getProvinceAndCityByPhone(String phone) {
		String result = null;
		String url = host1;// 请求接口地址
		Map params = new HashMap();// 请求参数
		params.put("phone", phone);// 需要查询的手机号码
		params.put("key", appcode1);// 应用APPKEY(应用详细页查询)
		Map<String, String> resMap = new HashMap<>();
		try {
			result = net(url, params, "GET");
			JSONObject object = JSONObject.parseObject(result);
			if (object.getInteger("error_code") == 0) {
				JSONObject codeJson = (JSONObject) object.get("result");
				resMap = object.parseObject(codeJson.toJSONString(), Map.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resMap;
	}

	/**
	 *
	 * @param strUrl
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param method
	 *            请求方法
	 * @return 网络请求字符串
	 * @throws Exception
	 */
	public static String net(String strUrl, Map params, String method) throws Exception {
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		String rs = null;
		try {
			StringBuffer sb = new StringBuffer();
			if (method == null || method.equals("GET")) {
				strUrl = strUrl + "?" + urlencode(params);
			}
			URL url = new URL(strUrl);
			conn = (HttpURLConnection) url.openConnection();
			if (method == null || method.equals("GET")) {
				conn.setRequestMethod("GET");
			} else {
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
			}
			conn.setRequestProperty("User-agent", userAgent);
			conn.setUseCaches(false);
			conn.setConnectTimeout(DEF_CONN_TIMEOUT);
			conn.setReadTimeout(DEF_READ_TIMEOUT);
			conn.setInstanceFollowRedirects(false);
			conn.connect();
			if (params != null && method.equals("POST")) {
				try {
					DataOutputStream out = new DataOutputStream(conn.getOutputStream());
					out.writeBytes(urlencode(params));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			InputStream is = conn.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sb.append(strRead);
			}
			rs = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return rs;
	}

	// 将map型转为请求参数型
	public static String urlencode(Map<String, String> data) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry i : data.entrySet()) {
			try {
				sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/***
	 * 中国电信号段 133、149、153、173、177、180、181、189、199 中国联通号段
	 * 130、131、132、145、155、156、166、175、176、185、186 中国移动号段
	 * 134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188、198
	 * 其他号段 14号段以前为上网卡专属号段，如中国联通的是145，中国移动的是147等等。 虚拟运营商 电信：1700、1701、1702
	 * 移动：1703、1705、1706 联通：1704、1707、1708、1709、171 卫星通信：1349
	 */
	public static boolean isPhone(String phone) {
		String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
		if (phone.length() != 11) {
			return false;
		} else {
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(phone);
			boolean isMatch = m.matches();
			return isMatch;
		}
	}

}
