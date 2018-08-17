package com.king.test;

import java.util.ArrayList;
import java.util.List;

import com.king.base.XmlBase;
import com.king.test.vo.Car;
import com.king.util.Xml4bigKit;

public class BigXmlImportToDB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String xpath=System.getProperty("user.dir")+"/src/test/java/CarData.xml";
		System.out.println(System.getProperty("user.dir"));
		long  startTime = System.currentTimeMillis();	//开始时间
		String dataStartTag =null;
		List<Class<? extends XmlBase>> clslist = new ArrayList<Class<? extends XmlBase>>();
		clslist.add(Car.class);
		Xml4bigKit.parseXml(xpath, dataStartTag,clslist,new XmlHandler1());
		long stopTime = System.currentTimeMillis();		//写文件时间
		System.out.println("finish time: " + (stopTime - startTime)/1000 + "m");
	}

}
