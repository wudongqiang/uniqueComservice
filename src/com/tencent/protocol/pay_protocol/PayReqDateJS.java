/**
 *
 */
package com.tencent.protocol.pay_protocol;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.tencent.common.SHA1;
import com.tencent.common.Signature;

/**
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年9月28日下午4:32:19
 * 修改日期:2015年9月28日下午4:32:19
 */
public class PayReqDateJS {

	
    /**
	 * @param banktype
	 * @param body
	 * @param fee_type
	 * @param input_charset
	 * @param notify_url
	 * @param out_trade_no
	 * @param partner
	 * @param spbill_create_ip
	 * @param total_fee
	 */
	public PayReqDateJS(String body,String notify_url, String out_trade_no,
			String partner, String spbill_create_ip, int total_fee) {
		
		this.banktype = "WX";
		this.fee_type = "1";//费用类型，这里1为默认的人民币
		this.input_charset = "UTF-8";//字符集，这里将统一使用GBK
		
		this.body = body;
		this.notify_url = notify_url;
		this.out_trade_no = out_trade_no;
		this.partner = partner;
		this.spbill_create_ip = spbill_create_ip;
		this.total_fee = total_fee;
        //根据API给的签名规则进行签名
        String sign = Signature.getSign4Json(toMap());
        setSign(sign);//把签名数据设置到Sign这个属性中
	}

	public String getBanktype() {
		return banktype;
	}

	public void setBanktype(String banktype) {
		this.banktype = banktype;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getFee_type() {
		return fee_type;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	public String getInput_charset() {
		return input_charset;
	}

	public void setInput_charset(String input_charset) {
		this.input_charset = input_charset;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}

	public int getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}
    public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}

	private String banktype = "WX";
    private String  body;//商品名称信息，这里由测试网页填入。
    private String  fee_type = "1";//费用类型，这里1为默认的人民币
    private String  input_charset = "UTF-8";//字符集，这里将统一使用GBK
    private String  notify_url;//支付成功后将通知该地址
    private String  out_trade_no;//订单号，商户需要保证该字段对于本商户的唯一性
    private String  partner;//测试商户号
    private String  spbill_create_ip;//用户浏览器的ip，这个需要在前端获取。这里使用127.0.0.1测试值
    private int  total_fee;//总金额。
    private String  sign; ///签名后的字符串

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
