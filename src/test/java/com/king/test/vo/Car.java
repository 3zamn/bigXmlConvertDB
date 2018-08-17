package com.king.test.vo;
import java.util.List;

import com.king.base.XmlBase;
public class Car extends XmlBase{
	public String type;
	public String product;
	public Double price;
     public List<CarEngine> carEngine;
	
	
	
	public List<CarEngine> getCarEngine() {
		return carEngine;
	}
	public void setCarEngine(List<CarEngine> carEngine) {
		this.carEngine = carEngine;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	

}
