package com.tencent.service;

import com.tencent.common.*;
import com.tencent.protocol.pay_protocol.BeforePayReqData;
import com.tencent.protocol.pay_protocol.ScanPayReqData;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

/**
 * 统一生成预支付订单
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年10月13日下午1:26:26
 * 修改日期:2015年10月13日下午1:26:26
 */
public class BuildPayOrderService extends BaseService{

    public BuildPayOrderService() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        super(Configure.BUILD_ORDER);
    }

    /**
     * 请求支付服务
     * @param scanPayReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的数据
     * @throws Exception
     */
    public String request(BeforePayReqData ReqData) throws Exception {

        //--------------------------------------------------------------------
        //发送HTTPS的Post请求到API地址
        //--------------------------------------------------------------------
        String responseString = sendPost(ReqData);

        return responseString;
    }
}
