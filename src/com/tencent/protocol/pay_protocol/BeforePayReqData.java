/**
 *
 */
package com.tencent.protocol.pay_protocol;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.tencent.common.Configure;
import com.tencent.common.Signature;

/**
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年10月13日下午1:19:03
 * 修改日期:2015年10月13日下午1:19:03
 */
public class BeforePayReqData {

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	public String getFee_type() {
		return fee_type;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	public String getGoods_tag() {
		return goods_tag;
	}

	public void setGoods_tag(String goods_tag) {
		this.goods_tag = goods_tag;
	}

	public String getLimit_pay() {
		return limit_pay;
	}

	public void setLimit_pay(String limit_pay) {
		this.limit_pay = limit_pay;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}

	public String getTime_expire() {
		return time_expire;
	}

	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}

	public String getTime_start() {
		return time_start;
	}

	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}

	public int getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	private String appid;
	/**
	 * @param appid
	 * @param attach
	 * @param body
	 * @param detail
	 * @param device_info
	 * @param fee_type
	 * @param goods_tag
	 * @param limit_pay
	 * @param mch_id
	 * @param nonce_str
	 * @param notify_url
	 * @param openid
	 * @param out_trade_no
	 * @param product_id
	 * @param sign
	 * @param spbill_create_ip
	 * @param time_expire
	 * @param time_start
	 * @param total_fee
	 * @param trade_type
	 */
	public BeforePayReqData(String attach, String body,
			String detail, String device_info, 
			String goods_tag, String limit_pay, 
			String nonce_str, String notify_url, String openid,
			String out_trade_no, String product_id,
			String spbill_create_ip, String time_expire, String time_start,
			int total_fee) {
		this.appid = Configure.getAppid();
		this.attach = attach;
		this.body = body;
		this.detail = detail;
		this.device_info = device_info;
		this.fee_type = "CNY";
		this.goods_tag = goods_tag;
		this.limit_pay = limit_pay;
		this.mch_id = Configure.getMchid();
		this.nonce_str = nonce_str;
		this.notify_url = notify_url;
		this.openid = openid;
		this.out_trade_no = out_trade_no;
		this.product_id = product_id;
		this.spbill_create_ip = spbill_create_ip;
		this.time_expire = time_expire;
		this.time_start = time_start;
		this.total_fee = total_fee;
		this.trade_type = "JSAPI";
		
        //根据API给的签名规则进行签名
        String sign = Signature.getSign(toMap());
        setSign(sign);//把签名数据设置到Sign这个属性中
	}

	private String attach;
	private String body;
	private String detail;
	private String device_info;
	private String fee_type;
	private String goods_tag;
	private String limit_pay;
	private String mch_id;
	private String nonce_str;
	private String notify_url;
	private String openid;
	private String out_trade_no;
	private String product_id;
	private String spbill_create_ip;
	private String time_expire;
	private String time_start;
	private int total_fee;
	private String trade_type;
	private String sign;
	
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
    
    
    public static void main(String[] args) {
    	Field[] fields = BeforePayReqData.class.getDeclaredFields();
    	String[] fieldNames = new String[fields.length];
    	int i=0;
        for (Field field : fields) {
        	fieldNames[i++] = field.getName();
        }
        Arrays.sort(fieldNames, String.CASE_INSENSITIVE_ORDER);
        for (String fieldName : fieldNames) {
        	System.out.println("private String " + fieldName + ";");
        }
	}
}
