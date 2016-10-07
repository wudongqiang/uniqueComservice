/**
 *
 */
package com.tencent.protocol.pay_protocol;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.tencent.common.Configure;
import com.tencent.common.MD5;

/**
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年10月12日下午5:27:57
 * 修改日期:2015年10月12日下午5:27:57
 */
public class PayReqDateJSMain {
    public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getTime_stamp() {
		return time_stamp;
	}
	public void setTime_stamp(String time_stamp) {
		this.time_stamp = time_stamp;
	}
	
	public String getPrepayid() {
		return prepayid;
	}
	public void setPrepayid(String prepayid) {
		this.prepayid = prepayid;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	/**
	 * @param app_id
	 * @param app_key
	 * @param nonce_str
	 * @param package_string
	 * @param time_stamp
	 */
	public PayReqDateJSMain(String app_id, String nonce_str,
			String prepayid, String time_stamp,String signType,String tradeType) {
		this.app_id = app_id;
		this.nonce_str = nonce_str;
		this.prepayid = prepayid;
		this.time_stamp = time_stamp;
		this.signType = signType;
		
		this.tradeType=tradeType;
        //根据API给的签名规则进行签名  这里没有用到
//        String sign = Signature.getSign(toMap());
//        setSign(sign);
	}
	private String app_id;
    private String nonce_str;
    private String prepayid;
    private String time_stamp;
    private String signType;
    
    private String tradeType;//微信支付用：JSAPI、NATIVE、APP  这里不参与加密
    
    public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	private String sign;
    
    
	public String getSign() {
		//这里要区分一下 微信公众号 和APP支付
		String keyvaluestring = "";
		if("APP".equals(tradeType)){
			//app支付
			keyvaluestring = "appid="+app_id+"&noncestr="+nonce_str+"&package=Sign=WXPay&partnerid="+signType+"&prepayid="+prepayid+"&timestamp="+time_stamp+ "&key=" + Configure.getKey();
		}else{
			keyvaluestring = "appId="+app_id+"&nonceStr="+nonce_str+"&package=prepay_id="+prepayid+"&signType="+signType+"&timeStamp="+time_stamp+ "&key=" + Configure.getKey();
		}
        System.out.println(keyvaluestring);
        sign = MD5.MD5Encode(keyvaluestring).toUpperCase();
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
    private String packAge;
    

	public String getPackAge() {
		return packAge;
	}

	public void setPackAge(String packAge) {
		this.packAge = packAge;
	}
	public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<String, Object>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(this);
                if(obj!=null){
                    map.put(field.getName(), obj);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
