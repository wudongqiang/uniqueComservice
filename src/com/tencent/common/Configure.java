package com.tencent.common;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashMap;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.unique.alipay.sign.Base64;
import com.unique.core.util.FileUtil;

import net.sf.json.JSONObject;

/**
 * User: rizenguo
 * Date: 2014/10/29
 * Time: 14:40
 * 这里放置各种配置数据
 */
public class Configure {

	//sdk的版本号
	private static final String sdkVersion = "java sdk 1.0.1";

//这个就是自己要保管好的私有Key了（切记只能放在自己的后台代码里，不能放在任何可能被看到源代码的客户端程序中）
	// 每次自己Post数据给API的时候都要用这个key来对所有字段进行签名，生成的签名会放在Sign这个字段，API收到Post数据的时候也会用同样的签名算法对Post过来的数据进行签名和验证
	// 收到API的返回的时候也要用这个key来对返回的数据算下签名，跟API的Sign数据进行比较，如果值不一致，有可能数据被第三方给篡改

	private static String key = FileUtil.readProperties("wxpay.properties", "key");
	//微信分配的公众号ID（开通公众号之后可以获取到）
	private static String appID = FileUtil.readProperties("wxpay.properties", "appID");

	//微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
	private static String mchID = FileUtil.readProperties("wxpay.properties", "mchID");
	
	private static String wxsite = FileUtil.readProperties("wxpay.properties", "wxsite");
	
	//受理模式下给子商户分配的子商户号
	private static String subMchID = "";

	//HTTPS证书的本地路径
	private static String certLocalPath = "";

	//HTTPS证书密码，默认密码等于商户号MCHID
	private static String certPassword =FileUtil.readProperties("wxpay.properties", "mchID");

	//是否使用异步线程的方式来上报API测速，默认为异步模式
	private static boolean useThreadToDoReport = true;

	//机器IP
	private static String ip = "";

	//以下是几个API的路径：
	public static String BUILD_ORDER = wxsite +  "/pay/unifiedorder";
	
	//1）被扫支付API
	public static String PAY_API = wxsite +  "/pay/micropay";

	//2）被扫支付查询API
	public static String PAY_QUERY_API = wxsite +  "/pay/orderquery";

	//3）退款API
	public static String REFUND_API = wxsite +  "/secapi/pay/refund";

	//4）退款查询API
	public static String REFUND_QUERY_API = wxsite +  "/pay/refundquery";

	//5）撤销API
	public static String REVERSE_API = wxsite +  "/secapi/pay/reverse";

	//6）下载对账单API
	public static String DOWNLOAD_BILL_API = wxsite +  "/pay/downloadbill";

	//7) 统计上报API
	public static String REPORT_API = wxsite + "/payitil/report";

	public static boolean isUseThreadToDoReport() {
		return useThreadToDoReport;
	}

	public static void setUseThreadToDoReport(boolean useThreadToDoReport) {
		Configure.useThreadToDoReport = useThreadToDoReport;
	}

	public static String HttpsRequestClassName = "com.tencent.common.HttpsRequest";

	public static void setKey(String key) {
		Configure.key = key;
	}

	public static void setAppID(String appID) {
		Configure.appID = appID;
	}

	public static void setMchID(String mchID) {
		Configure.mchID = mchID;
	}

	public static void setSubMchID(String subMchID) {
		Configure.subMchID = subMchID;
	}

	public static void setCertLocalPath(String certLocalPath) {
		Configure.certLocalPath = certLocalPath;
	}

	public static void setCertPassword(String certPassword) {
		Configure.certPassword = certPassword;
	}

	public static void setIp(String ip) {
		Configure.ip = ip;
	}

	public static String getKey(){
		return key;
	}
	
	public static String getAppid(){
		return appID;
	}
	
	public static String getMchid(){
		return mchID;
	}

	public static String getSubMchid(){
		return subMchID;
	}
	
	public static String getCertLocalPath(){
		return certLocalPath;
	}
	
	public static String getCertPassword(){
		return certPassword;
	}

	public static String getIP(){
		return ip;
	}

	public static void setHttpsRequestClassName(String name){
		HttpsRequestClassName = name;
	}

	public static String getSdkVersion(){
		return sdkVersion;
	}

	 private static HashMap<String, Configure> configs = new HashMap<String, Configure>();
		public static Configure getInstance(String configTxt,String payWayCode){
			Configure config = null;
			if(!configs.containsKey(payWayCode)){
				if(StringUtils.isEmpty(configTxt))return null;
				
				JSONObject jConfig = JSONObject.fromObject(configTxt);
				config = new Configure();
				config.setAppID(jConfig.getString("appId"));
				config.setCertPassword(jConfig.getString("muchId"));
				config.setKey(jConfig.getString("key"));
				config.setMchID(jConfig.getString("muchId"));
				config.setCertbytes(Base64.decode(jConfig.getString("p12")));
				
				try {
					config.init();
				} catch (UnrecoverableKeyException e) {
					e.printStackTrace();
				} catch (KeyManagementException e) {
					e.printStackTrace();
				} catch (KeyStoreException e) {
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				config  = configs.get(payWayCode);
			}
			return config;
		}
	    
		//----------新增------------------//
		
		private byte[] certbytes = null;
		
		public byte[] getCertbytes() {
			return certbytes;
		}

		public void setCertbytes(byte[] certbytes) {
			this.certbytes = certbytes;
		}
		
	    //连接超时时间，默认10秒
		public int socketTimeout = 10000;

	    //传输超时时间，默认30秒
		public int connectTimeout = 30000;
		   //请求器的配置
	    public RequestConfig requestConfig;

	    //HTTP请求器
	    public CloseableHttpClient httpClient;

	    public void init() throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {
	        KeyStore keyStore = KeyStore.getInstance("PKCS12");
	        ByteArrayInputStream is  = new ByteArrayInputStream(getCertbytes());
	        try {
	            keyStore.load(is, getCertPassword().toCharArray());//设置证书密码
	        } catch (CertificateException e) {
	            e.printStackTrace();
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        } finally {
	            is.close();
	        }

	        // Trust own CA and all self-signed certs
	        SSLContext sslcontext = SSLContexts.custom()
	                .loadKeyMaterial(keyStore, getCertPassword().toCharArray())
	                .build();
	        // Allow TLSv1 protocol only
	        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
	                sslcontext,
	                new String[]{"TLSv1"},
	                null,
	                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

	        httpClient = HttpClients.custom()
	                .setSSLSocketFactory(sslsf)
	                .build();

	        //根据默认超时限制初始化requestConfig
	        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
	    }
}
