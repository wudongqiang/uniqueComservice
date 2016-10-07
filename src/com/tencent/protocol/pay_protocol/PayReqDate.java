/**
 *
 */
package com.tencent.protocol.pay_protocol;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.tencent.common.Configure;
import com.tencent.common.RandomStringGenerator;
import com.tencent.common.Signature;

/**
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年9月28日下午4:32:19
 * 修改日期:2015年9月28日下午4:32:19
 */
public class PayReqDate {
	/**
	 * @param appid
	 * @param mch_id
	 * @param device_info
	 * @param nonce_str
	 * @param sign
	 * @param body
	 * @param attach
	 * @param out_trade_no
	 * @param total_fee
	 * @param spbill_create_ip
	 * @param iP
	 * @param time_start
	 * @param time_expire
	 * @param goods_tag
	 * @param notify_url
	 * @param trade_type
	 * @param openid
	 * @param product_id
	 */
	public PayReqDate(String device_info,String body, String attach,
			String out_trade_no, int total_fee, String spbill_create_ip,
			String time_start, String time_expire, String goods_tag,
			String notify_url, String trade_type, String openid,
			String product_id) {
	     //微信分配的公众号ID（开通公众号之后可以获取到）
	    setAppid(Configure.getAppid());
	     //微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
	    setMch_id(Configure.getMchid());

		this.device_info = device_info;
        //随机字符串，不长于32 位
        setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
		this.body = body;
		this.attach = attach;
		this.out_trade_no = out_trade_no;
		this.total_fee = total_fee;
		this.spbill_create_ip = spbill_create_ip;
		this.time_start = time_start;
		this.time_expire = time_expire;
		this.goods_tag = goods_tag;
		this.notify_url = notify_url;
		this.trade_type = trade_type;
		this.openid = openid;
		this.product_id = product_id;
		
        //根据API给的签名规则进行签名
        String sign = Signature.getSign(toMap());
        setSign(sign);//把签名数据设置到Sign这个属性中
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getDevice_info() {
		return device_info;
	}
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public int getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}
	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}
	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}
	public String getTime_start() {
		return time_start;
	}
	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}
	public String getTime_expire() {
		return time_expire;
	}
	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}
	public String getGoods_tag() {
		return goods_tag;
	}
	public void setGoods_tag(String goods_tag) {
		this.goods_tag = goods_tag;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	private String appid; ///公众账号 ID 是 String(32) 微信分配的公众账号 ID 
	private String mch_id; ///商户号 mch_id 是 String(32) 微信支付分配的商户号 
	private String device_info; ///设备号 device_info 否 String(32) 微信支付分配的终端设备号 
	private String nonce_str; ///随机字符串 nonce_str 是 String(32) 随机字符串，不长于 32 位 
	private String sign; ///签名 sign 是 String(32) 签名,详细签名方法见3.2节 
	private String body;///商品描述 body 是 String(127) 商品描述 
	private String attach; ///附加数据 attach 否 String(127) 附加数据，原样返回 
	private String out_trade_no; ///商户订单号 out_trade_no 是 String(32) 商户系统内部的订单号,32 个字符内、可包含字母,确保 在商户系统唯一,详细说明
	private int total_fee; ///总金额  是 Int 订单总金额，单位为分，不 能带小数点 
	private String spbill_create_ip; ///终端 IP spbill_create_ip 是 String(16) 订单生成的机器 IP  
	private String time_start;///交易起始时间 time_start 否 String(14) 订 单 生 成 时 间 ， 格 式 为 yyyyMMddHHmmss，如 2009 年 12 月25日 9点 10分 10 秒表 示为 20091225091010。时区 为 GMT+8 beijing。该时间取 自商户服务器 
	private String time_expire;///交易结束时间 time_expire 否 String(14) 订 单 失 效 时 间 ， 格 式 为 yyyyMMddHHmmss，如 2009 年 12 月27日 9点 10分 10 秒表 示为 20091227091010。时区 为 GMT+8 beijing。该时间取 自商户服务器 
	private String goods_tag;///商品标记 goods_tag 否 String(32) 商品标记，该字段不能随便 填，不使用请填空，使用说 明详见第 5 节 
	private String notify_url; ///通知地址 notify_url 是 String(256) 接收微信支付成功通知 
	private String trade_type; ///交易类型 trade_type 是 String(16) JSAPI、NATIVE、APP 
	private String openid;///用户标识 openid 否 String(128) 用户在商户 appid 下的唯一 标识，trade_type 为 JSAPI 时，此参数必传，获取方式 见表头说明。 
	private String product_id; /// 否 String(32) 只在 trade_type 为 NATIVE 时需要填写。此 id 为二维码 中包含的商品 ID，商户自行 维护。 返

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
