<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!-- 자바 클래스 import -->
<%@page import="com.company.Spring_MVC_Board.board.BoardDO"%>
<%@page import="com.company.Spring_MVC_Board.board.BoardDAO"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>게시글 상세보기 페이지 => getBoard.jsp</title>
<style>
	#div_box {
		position:absolute;
		top:10%;
		left:20%;
	}
</style>
</head>
<body>
	<div id="div_box">
		<h1>게시글 상세보기</h1>
		<a href="logout.do">로그아웃</a>
		<hr>
		<form name="getBoardForm" method="post" action="updateBoard.do">
			<input type="hidden" name="seq" value="${board.getSeq() }" />
			<table border="1" cellpadding="0" cellspacing="0">
				<tr>
					<td bgcolor="orange" width="70">제목</td>
					<td align="left"><input name="title" type="text" value="${board.title}"></td>
				</tr>
				<tr>
					<td bgcolor="orange">작성자</td>
					<td align="left"><input type="hidden" name ="writer" value="${board.writer}" />${board.writer}</td>
				</tr>
				<tr>
					<td bgcolor="orange">내용</td>
					<td align="left"><textarea name="content" 
					rows="10" cols="40">${board.content}</textarea></td>
				</tr>
				<tr>
					<td bgcolor="orange">작성일자</td>
					<td align="left">${board.regdate}</td>
				</tr>
				<tr>
					<td bgcolor="orange">조회수</td>
					<td align="left">${board.cnt}</td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit" value="수정하기"></td>
				</tr>
			</table>
		</form>
		<hr>
		<a href="insertBoard.jsp">새 게시글 등록</a>&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="deleteBoard.do?seq=${board.seq }">게시글 삭제</a>&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="getBoardList.do">전체 게시글 목록보기</a>
	</div>
</body>
</html>