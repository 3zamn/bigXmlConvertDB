package com.king.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class GenXml {
 public static void main(String[] args) throws Exception{
	 String xpath=System.getProperty("user.dir")+"/src/test/java/CarDataBig.xml";
	 System.out.println(System.getProperty("user.dir"));
	 File file = new File(xpath);
	 if(file.exists()){
		 file.delete();
	 }
	 int len =10000000;
	 file.createNewFile();
	 BufferedWriter bw = null;  
	 FileWriter fw = new FileWriter(file);  
     bw = new BufferedWriter(fw);  
     bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
     bw.newLine();
     bw.write("<Cars>");
     bw.newLine();
     String el="<Car><type>本田</type><product>中国</product><price>22.5</price><carEngine><core>16</core><type>Intel</type></carEngine><carEngine><core>16</core><type>Intel</type></carEngine></Car>";
     int icount=100;
     StringBuffer sbf=new StringBuffer();
     for(int i=0; i<icount;i++){
    	 sbf.append(el);
     }
     for(int i=0; i<len;i++){
    	 bw.write(sbf.toString());
    	 bw.newLine();
     }
    
     bw.write("</Cars>");
     bw.flush();
     bw.close();
     
 }
}
