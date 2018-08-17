package com.king.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.king.handler.XmlHandler;
import com.king.test.vo.Car;

public class XmlHandler1 implements XmlHandler {

	public void hande(Map<String, List<Object>> reMap) {
		Connection conn=null;
		PreparedStatement pst=null;
		try {
			List<Object> list = reMap.get("Car");
			System.out.println(list.size());
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String url = "jdbc:mysql://localhost:3306/king_smp?characterEncoding=UTF-8&useSSL=false&rewriteBatchedStatements=true";
			String user = "root";
			String password = "123456";
			// 获取数据库连接
			 conn = DriverManager.getConnection(url, user, password);
			String prefix = "INSERT INTO test (type,product,price) VALUES ";
			StringBuffer suffix = new StringBuffer();
			// 设置事务为非自动提交
			conn.setAutoCommit(false);
			// Statement st = conn.createStatement();
			// 比起st，pst会更好些
			 pst = conn.prepareStatement("");
			String sql="";
			for (Object obj : list) {
				Car car = (Car) obj;
				suffix.append("(" + "'"+car.getType()+"'" + "," + "'"+car.getProduct()+"'" + "," + car.getPrice() + "),");
		
			}
			sql = prefix + suffix.substring(0, suffix.length() - 1);
			pst.addBatch(sql);
			// 执行操作
			pst.executeBatch();
			// 提交事务
			conn.commit();
			// 清空上一次添加的数据
			suffix = new StringBuffer();
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			 try {
				pst.close();
				conn.close();  
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	            
		}

	}

}
