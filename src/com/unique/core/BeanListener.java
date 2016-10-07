package com.unique.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

import javax.servlet.ServletContext;

import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.WebApplicationContext;

import com.unique.core.annotation.HttpMethod;
/**
 * 工具类 - Spring工具类
 */

public class BeanListener implements ApplicationContextAware {
	public static String BASE_PATH = "";
	
	private static ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		
//		File cert = new File(basePath,"apiclient_cert.p12");
		BeanListener.applicationContext = applicationContext;
		ServletContext context = ((WebApplicationContext)applicationContext).getServletContext();
		BASE_PATH = context.getRealPath("/WEB-INF/classes/");
		Map<String, Object> beanMaps = new HashMap<String, Object>();
		///获取所有SERVICE BEAN并加入反射容器
		ClassPool pool = ClassPool.getDefault();
		String[] beans = applicationContext.getBeanDefinitionNames();
		for(String beanName : beans){
			if(beanName.endsWith("Service") || beanName.indexOf("lucence")>=0){
				Object bean = applicationContext.getBean(beanName);
				pool.insertClassPath(new ClassClassPath(bean.getClass()));
				
				Map<String, Object> beanMap = new HashMap<String, Object>();
				
				Class oldClass = bean.getClass();
				
				if(oldClass.getName().indexOf("EnhancerBySpringCGLIB")>=0){
					//被CGLIB代理的类
					oldClass = oldClass.getSuperclass();
				}
				/**************方法循环START**************/
				for(int j=0;j<oldClass.getDeclaredMethods().length;j++){
					try {
						Method oldMethod = oldClass.getDeclaredMethods()[j];
						Method method = null;
						try{
							method = bean.getClass().getMethod(oldMethod.getName(), oldMethod.getParameterTypes());
						}catch(NoSuchMethodException e){
							method = oldMethod;
						}
						
			            //根据注释判断是否装载该接口
			            boolean isHttpMethod =oldMethod.isAnnotationPresent(HttpMethod.class);
			            if(!isHttpMethod)continue;
			            HttpMethod methodAnn = oldMethod.getAnnotation(HttpMethod.class);
						CtClass clazz = pool.get(oldClass.getName());
						CtMethod m = clazz.getDeclaredMethod(method.getName());
						MethodInfo mInfo = m.getMethodInfo();
						CodeAttribute codeAttribute = mInfo.getCodeAttribute();
						LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);  
				        if (attr == null) {  
				                // exception  
				        }
				        /**********************获取参数类型集合************************/
//				        Class[] types = new Class[m.getParameterTypes().length];
//				        int t=0;
//				        for(CtClass ct : m.getParameterTypes()){
//				        	if(ct.isPrimitive()){
//				        		//返回一个基本数据类型
//				        		types[t++] =getPrimitiveClass(ct.getName());
//				        	}else{
//				        		types[t++] = Class.forName(ct.getName());
//				        	}
//				        }
				        
				        /**********************获取参数名字集合************************/
				        String[] paramNames = new String[m.getParameterTypes().length];  
			            int pos = Modifier.isStatic(m.getModifiers()) ? 0 : 1;  
			            for (int i = 0; i < paramNames.length; i++)  
			                paramNames[i] = attr.variableName(i + pos); 
			            /**********************获取方法实例*****************************/

			            HashMap<String, Object> paramterObject = new HashMap<String, Object>();
			            paramterObject.put("paramNames", paramNames);
			            paramterObject.put("instance", method==null?oldMethod:method);
			            paramterObject.put("oldMethod", oldMethod);
			            paramterObject.put("autho", methodAnn.autho());
			            //添加方法
			            beanMap.put(method.getName(), paramterObject);
					} catch (NotFoundException e) {
						//没找到
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					}
				}
				/**************方法循环END**************/
				beanMaps.put(beanName, beanMap);
				context.setAttribute("HttpMethodBean", beanMaps);
			}
		}
		
		pool.clearImportedPackages();
	}

	public static void main(String[] args) {
		System.out.println(Integer.TYPE);
	}
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 根据Bean名称获取实例.
	 * 
	 * @param name
	 *            Bean注册名称
	 * @return bean实例
	 * @throws BeansException
	 */
	public static Object getBean(String name) throws BeansException {
		return applicationContext.getBean(name);
	}

	/**
	 * 根据Bean的名称、Bean类型获取实例,若类型转换错误则抛出异常.
	 * 
	 * @param name
	 *            Bean注册名称
	 * @param requiredType
	 *            返回对象类型
	 * @return bean实例
	 * @throws BeansException
	 */
	public static Object getBean(String name, Class requiredType) throws BeansException {
		return applicationContext.getBean(name, requiredType);
	}

	/**
	 * 根据Bean的名称判断是否包含相应注册Bean.
	 * 
	 * @param Bean注册名称
	 * @return boolean
	 */
	public static boolean containsBean(String name) {
		return applicationContext.containsBean(name);
	}

	/**
	 * 判断注册bean是否为singletton,未找到相应Bean则抛出异常.
	 * 
	 * @param Bean注册名称
	 * @return boolean
	 * @throws NoSuchBeanDefinitionException
	 */
	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.isSingleton(name);
	}

	/**
	 * 根据Bean的名称获取Bean注册类型.
	 * 
	 * @param name
	 *            Bean注册名称
	 * @return Bean注册类型
	 * @throws NoSuchBeanDefinitionException
	 */
	public static Class getType(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.getType(name);
	}

	/**
	 * 根据Bean的名称获取Bean注册别名.
	 * 
	 * @param name
	 *            Bean注册名称
	 * @return 注册别名
	 * @throws NoSuchBeanDefinitionException
	 */
	public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.getAliases(name);
	}
	
	public Class getPrimitiveClass(String name){
		if("int".equals(name)){
			return Integer.TYPE;
		}else if("short".equals(name)){
			return Short.TYPE;
		}else if("long".equals(name)){
			return Long.TYPE;
		}else if("char".equals(name)){
			return Character.TYPE;
		}else if("byte".equals(name)){
			return Byte.TYPE;
		}else if("double".equals(name)){
			return Double.TYPE;
		}else if("float".equals(name)){
			return Float.TYPE;
		}
		return null;
	}
	
	/**
	 * 从代理类获得被代理类
	 * @param proxy
	 * @return
	 * @throws Exception
	 */
    public static Object getTargetObject(Object proxy) throws Exception {  
        if(!AopUtils.isAopProxy(proxy)) { //判断是否是代理类  
            return proxy;  
        }  
        return getTargetObject(getProxyTargetObject(proxy));  
    }  
  
  
    private static Object getProxyTargetObject(Object proxy) throws Exception {  
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");  
        h.setAccessible(true);  
        AopProxy aopProxy = (AopProxy) h.get(proxy);  
        Field advised = aopProxy.getClass().getDeclaredField("advised");  
        advised.setAccessible(true);  
        return  ((AdvisedSupport)advised.get(aopProxy)).getTargetSource().getTarget();  
    }  
}