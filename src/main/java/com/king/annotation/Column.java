package com.king.annotation;
import java.lang.annotation.ElementType; 

import java.lang.annotation.Retention; 
import java.lang.annotation.RetentionPolicy; 
import java.lang.annotation.Target; 
/**
 * 定义需要解析的实体属性
 * @author King chen
 * @emai 396885563@qq.com
 * @data 2018年8月16日
 */
@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.FIELD) 
public @ interface Column {
  public String name();
  public String required() default "1";//1:required which will generate the node of xml whatever the element value is null,0:not required which will generate the node of xml if  the element value is not null
}
