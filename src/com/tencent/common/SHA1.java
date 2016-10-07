/**
 *
 */
package com.tencent.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年10月12日下午5:27:04
 * 修改日期:2015年10月12日下午5:27:04
 */
public class SHA1 {
    public static String SHA1(String decript) {
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
 
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
