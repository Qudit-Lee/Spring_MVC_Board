package com.company.Spring_MVC_Board.board;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.company.Spring_MVC_Board.common.JDBCUtil;

public class BoardDAO {
	//H2 DB ���� ���� ����
		private Connection	conn = null;
		private PreparedStatement stmt = null;	
		/*statement��ĺ��� PreparedStatement ����� �� ���ȿ� ����.
		 * ��Ŀ���� SQL������ ��ŷ�� �ϴ� ��찡 ����
		 */
		private ResultSet	rs = null;	
		
		String where = "";
		//[�߿�] ��ü �Խñ� ��� ��ȸ ó�� �޼ҵ�
		public List<BoardDO> getBoardList(String searchField, String searchText){
			System.out.println("===> JDBC�� getBoardList() ��� ó��");
			
			//ArratList ���� �迭 ��ü ����	=> �⺻ 10���� ���� ����
			//�����ϸ� �ڵ����� 10���� ��ü ������ �ڵ����� ����
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
		
		//����ڰ� [����]�� Ŭ���ϸ� ������ �Խñ� �󼼺��� �޼ҵ� ����
		public BoardDO getBoard(BoardDO boardDO) {
			System.out.println("===> JDBC�� getBoard() ��� ó��");
			
			BoardDO board = null;
			 try {
				 conn = JDBCUtil.getConnection();
				 
				 //1.��� �Խñ��� ������ Ŭ���� ��ȸ���� 1 �����Ǵ� ����(update) �۾��� ���� ó���ؾ��Ѵ�!!!
				 String UPDATE_CNT = "update board set cnt=cnt+1 where seq=?";
				 stmt = conn.prepareStatement(UPDATE_CNT);
				 stmt.setInt(1,boardDO.getSeq());
				 stmt.executeUpdate();	//SQL�� ����(DNL���϶��� executeUpdate())
				 
				 //2.�ش� �Խñ� ��������
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
		
	//�Խñ� ���� �޼ҵ�
		
		public void updateBoard(BoardDO boardDO) {
			System.out.println("===> JDBC�� updateBoard() ��� ó��");
			
			try {
				//JDBC ���� �޼ҵ弱��
				conn = JDBCUtil.getConnection();
				
				//SQL�� ����
				String UPDATE_BOARD = "update board set title = ?, content = ? where seq = ?";
				
				//PreparedStatement ��ü ����
				stmt = conn.prepareStatement(UPDATE_BOARD); 
				
				//boardDO ��ü���� ���� ��������
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
		
	//�Խ��� ���� �޼ҵ�
		
		public void deleteBoard(BoardDO boardDO) {
			System.out.println("===> JDBC�� deleteBoard() ��� ó��");
			
			try {
				//JDBC ���� �޼ҵ� ����
				conn = JDBCUtil.getConnection();
				
				//sql�� ����
				String DELETE_BOARD = "delete from board where seq = ?";
				
				//PreparedStatement ����
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
		
	//�Խ��� �� �� �߰� �޼ҵ�
		public void insertBoard(BoardDO boardDO) {
			System.out.println("===> JDBC�� insertBoard() ��� ó��");
			
			try {
				//JDBC ���� �޼ҵ� ����
				conn = JDBCUtil.getConnection();
				
				//sql�� ����
				String INSERT_BOARD 
				= "insert into board(seq, title, writer, content) values((select nvl(max(seq),0)+1 from board),?,?,?)";
				//nvl�Լ��� ���, ���� �Խñ� ��ȣ�� ���� ���� ������ 1�� �߰��ض�.
				//�ۼ����ڿ� ��ȸ���� default������ ����.
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
