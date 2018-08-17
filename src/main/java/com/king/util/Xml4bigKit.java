package com.king.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.king.annotation.Root;
import com.king.base.Range;
import com.king.base.XmlBase;
import com.king.handler.XmlHandler;
/**
 * 高性能解析超大xml文件
 * 大文件逐步读取--避免内存溢出
 * @author King chen
 * @emai 396885563@qq.com
 * @data 2018年8月16日
 */
public class Xml4bigKit {
	
	public final static int MAX=50;
	public final static int READ_LEN=600000;//字符长度
	private static BufferedReader in;
	
	/**
	 * 
	 * @param xpath
	 * @param dataStartTag
	 * @param cls
	 * @param handle
	 * @param readLen
	 */
	
	@SuppressWarnings("unchecked")
	public static void parseXml(String xpath,String dataStartTag,List<Class<? extends XmlBase>> cls,XmlHandler handle,long readLen){
		
		 Map<String,List<Object>> reMap =new  HashMap<String,List<Object>>();
		  File file = new File(xpath);
		  long len=0; //MB
		  len=file.length()/1000000;
		  
	    try {
				
		  if(len<MAX){// small file
			 
					FileInputStream in = new FileInputStream(file);
					Reader reader=new InputStreamReader(in,"utf-8");
					
					SAXReader saxReader = new SAXReader();
				    Document doc = saxReader.read(reader);
				    Element  dataElement =doc.getRootElement();
				    if(dataStartTag!=null){
				    	dataElement=getNodeElement(dataElement,dataStartTag);
				    }
				    
				    for(Class<? extends XmlBase> cl:cls){
				    	
				    	  Root rt=cl.getAnnotation(Root.class);
				    	  String rtTag="";
				    	  if(rt!=null){
				    		  rtTag=rt.name();
				    		  
				    	  }else{
				    		  
				    		  rtTag=cl.getSimpleName();
				    		  
				    	  }
				    	  List<Element> listElement = dataElement.elements(rtTag);
				    	  if(listElement!=null){
				    		  List<Object> list = new ArrayList<Object>();
				    		  for(Element element:listElement){
				    			  Object obj =Class.forName(cl.getName()).newInstance();
						    	  String proccessMethodName = "parseXml";
						    	  Method  proccessMethod = obj.getClass().getMethod(proccessMethodName, Element.class);
						    	  proccessMethod.invoke(obj, element);
						    	  list.add(obj);
				    		  }
				    		  reMap.put(rtTag, list);
				    	  }
				    	 
				    }
				    handle.hande(reMap);
				
		  }else{
			  // big file
			  Reader reader=new InputStreamReader(new FileInputStream(file),"utf-8");
			  in = new BufferedReader(reader);
			  StringBuffer cache = new StringBuffer();
			  List<String> tagsList = new ArrayList<String>();
			  
			  for(Class<? extends XmlBase> cl:cls){
				  Root rt=cl.getAnnotation(Root.class);
		    	  String rtTag="";
		    	  if(rt!=null){
		    		  rtTag=rt.name();
		    		  
		    	  }else{
		    		  
		    		  rtTag=cl.getSimpleName();
		    		  
		    	  }
		    	  tagsList.add(rtTag);
			  }
			  
			  
			  char[] cbuf =new char[READ_LEN];
			  int count =0;
			  int rlen=0;
			  while ((rlen=in.read(cbuf))!=-1){
				   if(rlen!=READ_LEN){
					   char[] tcbuf =new char[rlen];
					   System.arraycopy(cbuf, 0, tcbuf, 0, rlen);
					   cache.append(tcbuf);
				   }else{
					   cache.append(cbuf);
				   }
				   
				   Range range =Strkit.getRange(cache, tagsList);
				  if(range.getFrom()>0){
					  String elementText="";
					  try{
						  
						elementText=cache.substring(range.getFrom(), range.getTo());
					    Document doc = DocumentHelper.parseText("<Root>"+elementText+"</Root>");  
					    
				        Element elem = doc.getRootElement(); 
				        reMap.clear();
				        
				        for(Class<? extends XmlBase> cl:cls){
					    	
					    	  Root rt=cl.getAnnotation(Root.class);
					    	  String rtTag="";
					    	  if(rt!=null){
					    		  rtTag=rt.name();
					    		  
					    	  }else{
					    		  
					    		  rtTag=cl.getSimpleName();
					    		  
					    	  }
					    	  List<Element> listElement = elem.elements(rtTag);
					    	  if(listElement!=null){
					    		  List<Object> list =  reMap.get(rtTag);
					    		  if(list == null){
					    			  list = new ArrayList<Object>();
					    			  
					    		  }  
					    		  for(Element element:listElement){
					    			  Object obj =Class.forName(cl.getName()).newInstance();
							    	  String proccessMethodName = "parseXml";
							    	  Method  proccessMethod = obj.getClass().getMethod(proccessMethodName, Element.class);
							    	  proccessMethod.invoke(obj, element);
							    	  list.add(obj);
					    		  }
					    		  reMap.put(rtTag, list);
					    	  }
					    	 
					    }
				        handle.hande(reMap);
					  }catch(Exception e){
						  e.printStackTrace();
						 
						  System.out.println(elementText);
					  }
					  
				  
					  cache= cache.delete(0,  range.getTo());
				  }else{
					  cache= cache.delete(0, cache.length());
				  }
				
				  count++;
				  if(count%1000==0){
					  System.out.println("handle count:"+count);
				  }
			  }
			  
			 
			 
			  
			  
		  }
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void parseXml(String xpath,String dataStartTag,List<Class<? extends XmlBase>> cls,XmlHandler handle){
		 parseXml(xpath,dataStartTag,cls,handle,READ_LEN);
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
	

}
