package com.company.Spring_MVC_Board.board;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.company.Spring_MVC_Board.common.JDBCUtil;

public class BoardDAO {
	//H2 DB 관련 변수 선언
		private Connection	conn = null;
		private PreparedStatement stmt = null;	
		/*statement방식보다 PreparedStatement 방식이 더 보안에 좋다.
		 * 해커들이 SQL문으로 해킹을 하는 경우가 있음
		 */
		private ResultSet	rs = null;	
		
		String where = "";
		//[중요] 전체 게시글 목록 조회 처리 메소드
		public List<BoardDO> getBoardList(String searchField, String searchText){
			System.out.println("===> JDBC로 getBoardList() 기능 처리");
			
			//ArratList 가변 배열 객체 생성	=> 기본 10개의 공간 마련
			//부족하면 자동으로 10개의 객체 공간을 자동으로 생성
			List<BoardDO> boardList = new ArrayList<BoardDO>();
			try {
				conn = JDBCUtil.getConnection();
				
				if(searchField != null && searchText != null) {
					where = "where "+searchField+" like '%"+searchText+"%'";
				}
				String BOARD_CONDITION_LIST = "select * from board "+ where +" order by seq desc";
				stmt = conn.prepareStatement(BOARD_CONDITION_LIST);
				rs = stmt.executeQuery();
				while(rs.next()) {
					BoardDO board = new BoardDO();
					
					board.setSeq(rs.getInt("SEQ"));
					board.setTitle(rs.getString("TITLE"));
					board.setWriter(rs.getString("WRITER"));
					board.setContent(rs.getString("CONTENT"));
					board.setRegdate(rs.getDate("REGDATE"));
					board.setCnt(rs.getInt("CNT"));
					
					boardList.add(board);
				}
			}catch(Exception e) {
				System.out.println("getBoardList()"+e);
			}finally {
				JDBCUtil.close(rs, stmt, conn);
			}
			return boardList;
		}
		
		//end getBoardList()--------------------------------------------------------------------
		
		//사용자가 [제목]을 클릭하면 선택한 게시글 상세보기 메소드 구현
		public BoardDO getBoard(BoardDO boardDO) {
			System.out.println("===> JDBC로 getBoard() 기능 처리");
			
			BoardDO board = null;
			 try {
				 conn = JDBCUtil.getConnection();
				 
				 //1.어느 게시글의 제목을 클릭시 조회수가 1 증가되는 갱신(update) 작업을 먼저 처리해야한다!!!
				 String UPDATE_CNT = "update board set cnt=cnt+1 where seq=?";
				 stmt = conn.prepareStatement(UPDATE_CNT);
				 stmt.setInt(1,boardDO.getSeq());
				 stmt.executeUpdate();	//SQL문 실행(DNL문일때는 executeUpdate())
				 
				 //2.해당 게시글 가져오기
				 String BOARD_GET = "select * from board where seq = ?";
				 stmt = conn.prepareStatement(BOARD_GET);
				 stmt.setInt(1,boardDO.getSeq());
				 rs = stmt.executeQuery();
				 
				 if(rs.next()) {
					 board = new BoardDO();
					 board.setSeq(rs.getInt("SEQ"));
					 board.setTitle(rs.getString("TITLE"));
					 board.setWriter(rs.getString("WRITER"));
					 board.setContent(rs.getString("CONTENT"));
					 board.setRegdate(rs.getDate("REGDATE"));
					 board.setCnt(rs.getInt("CNT"));
				 }
				 
			 }catch(Exception e) {
				 System.out.println("getBoard()"+e);
			 }finally {
				 JDBCUtil.close(rs, stmt, conn);
			 }
			 return board;
		}
		//end getBoard----------------------------------------------------------------
		
	//게시글 수정 메소드
		
		public void updateBoard(BoardDO boardDO) {
			System.out.println("===> JDBC로 updateBoard() 기능 처리");
			
			try {
				//JDBC 연결 메소드선언
				conn = JDBCUtil.getConnection();
				
				//SQL문 선언
				String UPDATE_BOARD = "update board set title = ?, content = ? where seq = ?";
				
				//PreparedStatement 객체 선언
				stmt = conn.prepareStatement(UPDATE_BOARD); 
				
				//boardDO 객체에서 내용 가져오기
				stmt.setString(1, boardDO.getTitle());
				stmt.setString(2,boardDO.getContent());
				stmt.setInt(3, boardDO.getSeq());
				stmt.executeUpdate();
				
			}catch(Exception e) {
				System.out.println("updateBoard()"+e);
			}finally {
				JDBCUtil.close(stmt, conn);
			}
		}
		//end updateBoard----------------------------------------------------------------
		
	//게시판 삭제 메소드
		
		public void deleteBoard(BoardDO boardDO) {
			System.out.println("===> JDBC로 deleteBoard() 기능 처리");
			
			try {
				//JDBC 연결 메소드 실행
				conn = JDBCUtil.getConnection();
				
				//sql문 선언
				String DELETE_BOARD = "delete from board where seq = ?";
				
				//PreparedStatement 선언
				stmt = conn.prepareStatement(DELETE_BOARD);
				stmt.setInt(1, boardDO.getSeq());
				stmt.executeUpdate();
				
				
			}catch(Exception e) {
				System.out.println("deleteBoard()"+e);
			}finally {
				JDBCUtil.close(stmt, conn);
			}
		}
		//end deleteBoard----------------------------------------------------------------
		
	//게시판 새 글 추가 메소드
		public void insertBoard(BoardDO boardDO) {
			System.out.println("===> JDBC로 insertBoard() 기능 처리");
			
			try {
				//JDBC 연결 메소드 실행
				conn = JDBCUtil.getConnection();
				
				//sql문 선언
				String INSERT_BOARD 
				= "insert into board(seq, title, writer, content) values((select nvl(max(seq),0)+1 from board),?,?,?)";
				//nvl함수를 사용, 현재 게시글 번호중 제일 높은 값에서 1을 추가해라.
				//작성일자와 조회수는 default값으로 들어간다.
				stmt = conn.prepareStatement(INSERT_BOARD);
				stmt.setString(1, boardDO.getTitle());
				stmt.setString(2, boardDO.getWriter());
				stmt.setString(3, boardDO.getContent());
				stmt.executeUpdate();
				
			}catch(Exception e) {
				System.out.println("insertBoard()"+e);
			}finally {
				JDBCUtil.close(stmt, conn);
			}
		}
		
}
