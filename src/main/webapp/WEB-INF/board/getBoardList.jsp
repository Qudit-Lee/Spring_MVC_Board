<%@page import="com.company.Spring_MVC_Board.board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<!-- �ڹ� Ŭ���� import -->
<%@page import="com.company.Spring_MVC_Board.board.BoardDO"%>
<%@page import="com.company.Spring_MVC_Board.board.BoardDAO"%>
<%@page import="java.util.List"%>
<!-- JSTL �����ϱ� ���ؼ� -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%	//��ũ��Ʈ�� -> �ڹ� �ڵ���
    	request.setCharacterEncoding("EUC-KR");//���ؼ� �Ѿ�� �ѱ� ������ ������ �ʰ� �ϱ� ���Ͽ�
    	
    	String searchField = "";
    	String searchText = "";
    	
    	if(request.getParameter("searchCondition") != "" 
    			&& request.getParameter("searchKeyword") != ""){
    		searchField = request.getParameter("searchCondition");
    		searchText = request.getParameter("searchKeyword");
    		}
    	BoardDAO boardDAO = new BoardDAO();
    	List<BoardDO> boardList = boardDAO.getBoardList(searchField, searchText);
    	
    	request.setAttribute("boardList", boardList);
    	int totalList = boardList.size();
    	request.setAttribute("totalList", totalList);
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>���� ��ñ� ��� ���� ������ => getBoardList.jsp</title>
<style>
	#div_box {
		position:absolute;
		top:20%;
		left:40%;
	}
</style>
</head>
<body>
	<div id="div_box">
		<h1>��ü �Խñ� ��� ����</h1>
		<h3>${idkey }�� ȯ���մϴ�.&nbsp;&nbsp;&nbsp;<a href="logout.do">�α׾ƿ�</a></h3>
		<form name="boardListForm" method="post" action="getBoardList.do">
			<p>�� �Խñ�: ${totalList }��</p>
			<table border="1" cellpadding="0" cellspacing = "0" width="700">
			<tr>
				<td align="center">
					<select name="searchCondition">
						<option value="TITLE">����
						<option value="WRITER">�ۼ���
					</select>
					<input type="text" name="searchKeyword"/>
					<input type="submit" value="�˻�"/>
				</td>
			</tr>
			</table>
		</form>
		<table border="1" cellpadding="0" cellspacing = "0" width="700">
			<tr>
				<th bgcolor="orange" width="100">��ȣ</th>
				<th bgcolor="orange" width="200">����</th>
				<th bgcolor="orange" width="150">�ۼ���</th>
				<th bgcolor="orange" width="150">�ۼ�����</th>
				<th bgcolor="orange" width="100">��ȸ��</th>
			</tr>
			
			<c:forEach var="board" items="${boardList}">
				<tr>
					<td align="center">${board.getSeq()}</td>
					<td align="left"><a href="getBoard.do?seq=${board.getSeq() }">${board.getTitle() }</a></td>
					<!-- getBoard.jsp�� �ѱ�� ������� �޾ƿ��� ���� -->
					<td align="center">${board.getWriter() }</td>
					<td align="center">${board.getRegdate()}</td>
					<td align="center">${board.getCnt()}</td>
				</tr>
			</c:forEach>
		</table>
		<br>
		<a href="insertBoard.jsp">�� �Խñ� ���</a>&nbsp;&nbsp;&nbsp;&nbsp;
		<!--  a href="getBoardList.do">��ü �Խñ� ��Ϻ���</a>-->
	</div>
</body>
</html>