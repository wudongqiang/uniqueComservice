/**
 *
 */
package com.unique.core.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.tree.DefaultAttribute;

/**
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年9月17日下午6:06:41
 * 修改日期:2015年9月17日下午6:06:41
 */
public class XmlUtil {
	 private static String xx="<西药处方><处方号>1481714</处方号><处方药品金额>135.4700</处方药品金额><门诊号>20150007516</门诊号><病人ID>274822</病人ID><挂号单>557302</挂号单><开立科室代码>050501</开立科室代码><开立科室>大公馆分院儿科门诊</开立科室><发生时间>2015020213:27:40</发生时间><开单人代码>101599</开单人代码><开单人>王学兰</开单人><结帐ID>王学兰</结帐ID><配药人代码>101528  </配药人代码><配药人>刘朝晖</配药人><审核人ID>101528</审核人ID><审核人>刘朝晖</审核人><患者基本信息><现住址>九龙坡文化三村6-2-1-1</现住址><出生地点/><国籍代码>156</国籍代码><国籍>中国</国籍><年龄/><工作单位/><单位电话/><单位邮编/><户口地址/><户口地址邮编/><籍贯/><职业代码> </职业代码><职业/><现住址邮编/><联系人关系/><联系人地址/><联系人电话/><联系人姓名/><身份/><家庭电话/><身份证号/><姓名>张御沛</姓名><性别代码>2</性别代码><性别>女</性别><出生日期>20120202</出生日期><婚姻状况代码>0</婚姻状况代码><婚姻状况>丧偶</婚姻状况><民族代码/><民族/></患者基本信息><门诊患者就诊信息><挂号ID>557302</挂号ID><接诊医生>王学兰</接诊医生><初诊标识代码>0</初诊标识代码><初诊或复诊/><年龄/><病人ID>274822</病人ID><挂号单号>557302</挂号单号><就诊时间>2015020213:18:12</就诊时间><接收时间/><完成时间>2015020213:24:45</完成时间><诊室>大公馆分院儿科门诊</诊室><执行科室编码>050501</执行科室编码><执行科室名称/></门诊患者就诊信息><诊断信息列表 list=\"true\"><患者诊断信息><疾病ID>J06.90001</疾病ID><诊断描述>上呼吸道感染</诊断描述><疾病编码>J06.90001</疾病编码><疾病名称>上呼吸道感染</疾病名称></患者诊断信息></诊断信息列表><西医处方明细列表 list=\"true\"><西医处方明细><单次用量>4.000</单次用量><执行频次代码>06</执行频次代码><执行频次>[TID]每日三次</执行频次><频率次数>1</频率次数><频率间隔/><间隔单位/><计算单位>ml</计算单位><单位>瓶</单位><序号>6129970</序号><总给予量>1.00</总给予量><药品剂型ID/><药品剂型/><天数>1</天数><药名ID>D333212</药名ID><药品名称>复方福尔可定口服液</药品名称><规格>120ml*1瓶/瓶</规格><相关ID>0</相关ID><医嘱用法ID>03</医嘱用法ID><医嘱用法>口服</医嘱用法></西医处方明细><西医处方明细><单次用量>2.000</单次用量><执行频次代码>06</执行频次代码><执行频次>[TID]每日三次</执行频次><频率次数>1</频率次数><频率间隔/><间隔单位/><计算单位>g</计算单位><单位>盒</单位><序号>6129971</序号><总给予量>18.00</总给予量><药品剂型ID/><药品剂型/><天数>1</天数><药名ID>D763402</药名ID><药品名称>小儿肺咳颗粒</药品名称><规格>2g*18包/盒</规格><相关ID>0</相关ID><医嘱用法ID>03</医嘱用法ID><医嘱用法>口服</医嘱用法></西医处方明细><西医处方明细><单次用量>2.000</单次用量><执行频次代码>05</执行频次代码><执行频次>[BID]每日二次</执行频次><频率次数>1</频率次数><频率间隔/><间隔单位/><计算单位>mg</计算单位><单位>片</单位><序号>6129972</序号><总给予量>5.00</总给予量><药品剂型ID/><药品剂型/><天数>1</天数><药名ID>D525004*</药名ID><药品名称>马来酸氯苯那敏片</药品名称><规格>4mg*100片/瓶</规格><相关ID>0</相关ID><医嘱用法ID>03</医嘱用法ID><医嘱用法>口服</医嘱用法></西医处方明细><西医处方明细><单次用量>10.000</单次用量><执行频次代码>06</执行频次代码><执行频次>[TID]每日三次</执行频次><频率次数>1</频率次数><频率间隔/><间隔单位/><计算单位>ml</计算单位><单位>瓶</单位><序号>6129973</序号><总给予量>2.00</总给予量><药品剂型ID/><药品剂型/><天数>1</天数><药名ID>D767212</药名ID><药品名称>健儿清解液</药品名称><规格>120ml*1瓶/瓶</规格><相关ID>0</相关ID><医嘱用法ID>03</医嘱用法ID><医嘱用法>口服</医嘱用法></西医处方明细><西医处方明细><单次用量>50.000</单次用量><执行频次代码>05</执行频次代码><执行频次>[BID]每日二次</执行频次><频率次数>1</频率次数><频率间隔/><间隔单位/><计算单位>mg</计算单位><单位>盒</单位><序号>6129974</序号><总给予量>12.00</总给予量><药品剂型ID/><药品剂型/><天数>1</天数><药名ID>D426450</药名ID><药品名称>头孢克肟干混悬剂</药品名称><规格>50mg*12包/盒</规格><相关ID>0</相关ID><医嘱用法ID>03</医嘱用法ID><医嘱用法>口服</医嘱用法></西医处方明细></西医处方明细列表></西药处方>";
	 
	public static void main(String[] args) throws DocumentException, IOException {
		
		FileReader reader = new FileReader(new File("C:\\Users\\Angel\\Desktop\\出院小结.xml"));
		StringBuffer temp = new StringBuffer();
		int i=-1;
		while((i=reader.read())>=0){
			temp.append((char)i);
		}
		
		Map<String, Object> result = xml2Bean(temp.toString());
		System.out.println(JSONObject.fromObject(result).toString());
	}
	
	public final static String xmlToJson(String xml) throws DocumentException{
		return JSONObject.fromObject(xml2Bean(xml)).toString();
	}
	
	public static Map<String, Object> xml2Bean(String xml) throws DocumentException{
		Document doc = DocumentHelper.parseText(xml);
		Map<String, Object> map = new HashMap<String, Object>();
		parseElement(doc.getRootElement(),map);
		return map;
	}
	
	/**
	 * 解析一个节点
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月18日上午9:17:47
	 * 修改日期:2015年9月18日上午9:17:47
	 * @param element
	 * @param map
	 */
	private static void parseElement(Element element,Map<String, Object> map){
		//子节点
		List<Element> childs = element.elements();
		//子节点类型
		String attrList = element.attributeValue("list");
		
		if(attrList==null){
			//普通对象
			if (childs.size() == 0) {
				//无子元素
				map.put(element.getName(), element.getText());
				copyAttributes(element,map);
			} else{
				Map<String, Object> childMap = new HashMap<String, Object>();
				for(Element child : childs){
					parseElement(child,childMap);
				}
				copyAttributes(element,map);
				map.put(element.getName(), childMap);
			} 
		}else{
			List<Object> list = new ArrayList<Object>();
			//集合对象
			if (childs.size() == 0) {
				//无子元素
				map.put(element.getName(),list);
			} else{
				for(Element child : childs){
					Map<String, Object> childMap = new HashMap<String, Object>();
					parseElement(child,childMap);
//					childMap.put("eleName", element.getName());
					list.add(childMap);
				}
				map.put(element.getName(), list);
			} 
		}
	}
	
	/**
	 * 复制属性到JSON属性
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月18日上午9:42:47
	 * 修改日期:2015年9月18日上午9:42:47
	 * @param element
	 * @param map
	 */
	public static void copyAttributes(Element element,Map map){
		for(Object attr : element.attributes()){
			DefaultAttribute att = ((DefaultAttribute)attr);
			map.put(att.getName(), att.getValue());
		}
	}
}
