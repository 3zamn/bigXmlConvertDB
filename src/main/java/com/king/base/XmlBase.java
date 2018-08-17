package com.king.base;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import com.king.annotation.Column;
/**
 * 根据Element解析生成实体
 * 
 * @author King chen
 * @emai 396885563@qq.com
 * @data 2018年8月16日
 */
public  class XmlBase {
	
	public void parseXml(Element elm) {
		
		if (elm == null)
			return;
		Field[] fields = this.getClass().getDeclaredFields();
		if (fields == null)
			return;
		try {
		for (Field field : fields) {

			Column an4field = field.getAnnotation(Column.class);
			String xmlNodeName = null;
			if(an4field!=null){
				xmlNodeName = an4field.name();
			}else{
				xmlNodeName = field.getName();
				 
			}
			
			Class<?> fieldType = field.getType();
			
			
			
			
			if (XmlBase.class.isAssignableFrom(fieldType)) {// if it is XmlBase subclass
												// then go recursive
				
				Element targer_elm = getNodeElement(elm,xmlNodeName);
			   if(targer_elm!=null){
				    String className=field.getType().getCanonicalName();
				    Object obj = Class.forName(className).newInstance();
				    
					Method  proccessMethod = obj.getClass().getMethod("parseXml",Element.class);	
					proccessMethod.invoke(obj, targer_elm);
					
					String firstLetter=field.getName().substring(0, 1).toUpperCase();
					String setMethodName = "set" + firstLetter + field.getName().substring(1); 		
					Method setMethod = this.getClass().getMethod(setMethodName, new Class[] {obj.getClass()});
					setMethod.invoke(this, new Object[] {obj});
			   }	
			   

			} else if (fieldType == List.class) {
				
				Type fc = field.getGenericType(); 
				ParameterizedType pt = (ParameterizedType) fc;  
			    Class<?> genericClazz = (Class<?>)pt.getActualTypeArguments()[0];
			    if(genericClazz == String.class || genericClazz == Integer.class || genericClazz == Long.class
					|| genericClazz == Float.class || genericClazz == Double.class){
			
					String firstLetter=field.getName().substring(0, 1).toUpperCase();
					List<Element> ls_elm=getNodeElements(elm,xmlNodeName);
					
					if(ls_elm==null||ls_elm.size()==0){
						continue;
					}
					
					
					String setMethodName = "set" + firstLetter + field.getName().substring(1); 
					
					if(genericClazz == String.class ){
						List<String> list =new ArrayList<String>();

						for(Element item:ls_elm){
							String vl=item.getText();
							if(vl!=null){
								vl=vl.trim();
							}
							vl=parseFilter(vl);
							list.add(vl);
							
						}
						
						
						
						Method setMethod = this.getClass().getMethod(setMethodName,List.class);
						setMethod.invoke(this, list);
						
					  
						
					}else if(fieldType == Integer.class){
						List<Integer> list =new ArrayList<Integer>();

						for(Element item:ls_elm){
							String vl=item.getText();
							if(vl!=null){
								vl=vl.trim();
							}
							vl=parseFilter(vl);
							list.add(Integer.valueOf(vl));
							
						}
						
						
						
						Method setMethod = this.getClass().getMethod(setMethodName,List.class);
						setMethod.invoke(this, list);
						
					  
						
					}else if(fieldType == Long.class){

						List<Long> list =new ArrayList<Long>();

						for(Element item:ls_elm){
							String vl=item.getText();
							if(vl!=null){
								vl=vl.trim();
							}
							vl=parseFilter(vl);
							list.add(Long.valueOf(vl));
							
						}
						
						
						
						Method setMethod = this.getClass().getMethod(setMethodName,List.class);
						setMethod.invoke(this, list);
						
					  
						
					
					}else if(fieldType == Float.class){

						List<Float> list =new ArrayList<Float>();

						for(Element item:ls_elm){
							String vl=item.getText();
							if(vl!=null){
								vl=vl.trim();
							}
							vl=parseFilter(vl);
							list.add(Float.valueOf(vl));
							
						}
						
						
						
						Method setMethod = this.getClass().getMethod(setMethodName,List.class);
						setMethod.invoke(this, list);
						
					  
						
					}else if(fieldType == Double.class){
						List<Double> list =new ArrayList<Double>();

						for(Element item:ls_elm){
							String vl=item.getText();
							if(vl!=null){
								vl=vl.trim();
							}
							vl=parseFilter(vl);
							list.add(Double.valueOf(vl));
							
						}
						
						
						
						Method setMethod = this.getClass().getMethod(setMethodName,List.class);
						setMethod.invoke(this, list);
						
					  
						
					}else{
						List<String> list =new ArrayList<String>();

						for(Element item:ls_elm){
							String vl=item.getText();
							if(vl!=null){
								vl=vl.trim();
							}
							vl=parseFilter(vl);
							list.add(vl);
							
						}
						
						
						
						Method setMethod = this.getClass().getMethod(setMethodName,List.class);
						setMethod.invoke(this, list);
						
					  
						
					}
					
					
					
				
					
				
			    	
			    }else{// list<Object>
			    	
					String firstLetter=field.getName().substring(0, 1).toUpperCase();
					List<Element> ls_elm=getNodeElements(elm,xmlNodeName);
					
					if(ls_elm==null||ls_elm.size()==0){
						continue;
					}
					List<Object> list = new ArrayList<Object>();
					for(Element e:ls_elm){
						Object obj =Class.forName(genericClazz.getName()).newInstance();
						Method  proccessMethod = obj.getClass().getMethod("parseXml", Element.class);	
						proccessMethod.invoke(obj, e);
						list.add(obj);
					}
					String setMethodName = "set" + firstLetter + field.getName().substring(1); 		
					Method setMethod = this.getClass().getMethod(setMethodName,List.class);
					setMethod.invoke(this, list);
					

				  
			    	
			    	
			    	
			    }
				
				

			} else if (fieldType == String.class || fieldType == Integer.class || fieldType == Long.class
					|| fieldType == Float.class || fieldType == Double.class) {
				
				Element targer_elm = getNodeElement(elm,xmlNodeName);
				if (targer_elm != null) {
					String textvalue = targer_elm.getText();
					if (textvalue != null) {
						textvalue = textvalue.trim();
					}
					textvalue = parseFilter(textvalue);
					Object value=textvalue;
					String firstLetter = field.getName().substring(0, 1).toUpperCase();
					String setMethodName = "set" + firstLetter + field.getName().substring(1);
					
					if(fieldType == Integer.class){
						value=Integer.valueOf(textvalue);
						this.getClass().getMethod(setMethodName, Integer.class).invoke(this, value);
					}else if(fieldType == Long.class){
						value=Long.valueOf(textvalue);
						this.getClass().getMethod(setMethodName, Long.class).invoke(this, value);
					}else if(fieldType == Float.class){
						value=Float.valueOf(textvalue);
						this.getClass().getMethod(setMethodName, Float.class).invoke(this, value);
					}else if(fieldType == Double.class){
						value=Double.valueOf(textvalue);
						this.getClass().getMethod(setMethodName, Double.class).invoke(this, value);
					}else{
						this.getClass().getMethod(setMethodName, String.class).invoke(this, value);
					}
					
					
					
				}

			} else {
				// not implements
			}
			
			

		    }
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	private static Element getNodeElement(Element element,String tags){
		if(element==null)return element;
		
		Element tmp = element;

		if (tags.indexOf("/") > 0) {
			String[] arr = tags.split("/");
			for (int j = 0; j < arr.length; j++) {
				String sname = arr[j];
				tmp = tmp.element(sname);
				if (tmp == null)
					break;
			}

		} else {
			tmp = tmp.element(tags);
		}
		return tmp;
	}
	
	
	@SuppressWarnings("unchecked")
	private static List<Element> getNodeElements(Element element,String tags){
		if(element==null)return null;
		
		Element tmp = element;

		if (tags.indexOf("/") > 0) {
			String[] arr = tags.split("/");
			for (int j = 0; j < arr.length; j++) {
				String sname = arr[j];
				tmp = tmp.element(sname);
				if (tmp == null)
					break;
			}
			return tmp.elements();

		} else {
			return tmp.elements(tags);
		}
		
	}

	private static String parseFilter(String s) {
		if (s == null)return null;
		s = s.replaceAll("&quot;", "\"");
		s = s.replaceAll("&apos;", "'");
		s = s.replaceAll("&lt;", "<");
		s = s.replaceAll("&gt;", ">");
		s = s.replaceAll("&amp;", "&");
		return s;
	}

//	private static String formatFilter(String s) {
//		if (s == null)
//			return null;
//		s = s.replaceAll("&", "&amp;");
//		s = s.replaceAll("\"", "&quot;");
//		s = s.replaceAll("'", "&apos;");
//		s = s.replaceAll("<", "&lt;");
//		s = s.replaceAll(">", "&gt;");
//		return s;
//	}

}
