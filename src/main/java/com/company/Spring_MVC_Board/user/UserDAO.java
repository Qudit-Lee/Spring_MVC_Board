package com.company.Spring_MVC_Board.user;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.company.Spring_MVC_Board.common.JDBCUtil;

public class UserDAO {
	//H2 DB 관련 변수 선언
	private Connection	conn = null;
	private PreparedStatement stmt = null;	
	/*statement방식보다 PreparedStatement 방식이 더 보안에 좋다.
	 * 해커들이 SQL문으로 해킹을 하는 경우가 있음
	 */
	private ResultSet	rs = null;	
	//select문에만 사용한다. 나머지 구문(insert,update,delete)구문에는 필요 x
	
	//로그인 인증 처리 SQL 문장
	private final String USER_GET 
		= "select id, password from users where id = ? and password = ?";
	
	public UserDO getUser(UserDO userDO) {
		UserDO user = null;
		
		//자바의 예외처리!!
		try {
			System.out.println("===> JDBC로 getUser() 기능 처리");
			
			conn = JDBCUtil.getConnection(); 
			stmt = conn.prepareStatement(USER_GET);
			stmt.setString(1, userDO.getId());
			stmt.setString(2, userDO.getPassword());
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				//인증 성공시
				user = new UserDO();
				user.setId(rs.getString("ID"));
				user.setPassword(rs.getString("PASSWORD"));
					
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(rs, stmt, conn);
		}
		return user;
	}
}
