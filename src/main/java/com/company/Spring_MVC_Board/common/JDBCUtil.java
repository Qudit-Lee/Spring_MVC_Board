package com.company.Spring_MVC_Board.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/*
 * JBDC를 접속할 때 마다 공통으로 사용하는 클래스이다.
 */
public class JDBCUtil {
	//H2 DB 연동에 관한 소스  => 실제 사용자들에게 서비스할때 사용할 DB에 따라 변경된다!!
	static final String driver = "org.h2.Driver";
	static final String url = "jdbc:h2:tcp://localhost/~/test";
	
	public static Connection getConnection() throws Exception{
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, "sa", "");
			return conn;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * CRUD 작업이 끝나면 자원(메모리에 올라온 객체)을 해제해야 한다.
	 */
	//자바의 다형성 => 즉 메소드 오버로딩(overload)
	//select작업 종료시 자원 해제하는 메소드 구현
	public static void close(ResultSet rs,PreparedStatement stmt, Connection conn) {
		if(rs != null) {
			try {
				if(!rs.isClosed()) rs.close();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				rs = null;
			}
		}
		if(stmt != null) {
			try {
				if(!stmt.isClosed()) stmt.close();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				stmt = null;
			}
		}
		if(conn != null) {
			try {
				if(!conn.isClosed()) conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				conn = null;
			}
		}
	}
	
	//DML(insert, update, delete)작업 종료시 자원 해제하는 메소드 구현
		public static void close(PreparedStatement stmt, Connection conn) {
			
			if(stmt != null) {
				try {
					if(!stmt.isClosed()) stmt.close();
				}catch(Exception e) {
					e.printStackTrace();
				}finally {
					stmt = null;
				}
			}
			if(conn != null) {
				try {
					if(!conn.isClosed()) conn.close();
				}catch(Exception e) {
					e.printStackTrace();
				}finally {
					conn = null;
				}
			}
		}
}
