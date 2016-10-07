/**
 *
 */
package com.unique.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptException;

import com.unique.alipay.sign.Base64;
import com.unique.core.util.FileUtil;

/**
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年8月24日上午10:36:56
 * 修改日期:2015年8月24日上午10:36:56
 */
public class JSTest {
	public static void main(String[] args) throws ScriptException, IOException {
//		ScriptEngineManager factory = new ScriptEngineManager();
//		ScriptEngine engine = factory.getEngineByName ("JavaScript");
//		engine.put("A", 99);
//		engine.put("B", 99);
//		engine.put("C", 99);
//		engine.put("D",199);
//		Object a = engine.eval("A+B+C");
//		Object a2 = engine.eval("A+B+C");
//		System.out.println(a);
//		System.out.println(a2);
		
		InputStream fis = JSTest.class.getClassLoader().getResourceAsStream("apiclient_cert.p12");
		List<Byte> bytes = new ArrayList<Byte>();
		
		int temp = -1;
		while((temp = fis.read())>=0){
			bytes.add((byte) temp);
		}
		
		byte[] byteArray = new byte[bytes.size()];
		for(int i=0;i<bytes.size();i++){
			byteArray[i]=bytes.get(i);
		}
		System.out.println(Base64.encode(byteArray)); 
	}
}
