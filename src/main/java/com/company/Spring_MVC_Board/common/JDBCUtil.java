package com.company.Spring_MVC_Board.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/*
 * JBDC�� ������ �� ���� �������� ����ϴ� Ŭ�����̴�.
 */
public class JDBCUtil {
	//H2 DB ������ ���� �ҽ�  => ���� ����ڵ鿡�� �����Ҷ� ����� DB�� ���� ����ȴ�!!
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
	 * CRUD �۾��� ������ �ڿ�(�޸𸮿� �ö�� ��ü)�� �����ؾ� �Ѵ�.
	 */
	//�ڹ��� ������ => �� �޼ҵ� �����ε�(overload)
	//select�۾� ����� �ڿ� �����ϴ� �޼ҵ� ����
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
	
	//DML(insert, update, delete)�۾� ����� �ڿ� �����ϴ� �޼ҵ� ����
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
