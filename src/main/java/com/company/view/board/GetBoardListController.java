package com.company.view.board;

import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.company.Spring_MVC_Board.board.BoardDAO;
import com.company.Spring_MVC_Board.board.BoardDO;

public class GetBoardListController implements Controller {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("euc-kr");
		
		System.out.println("전체 게시글 목록 처리!");
		
		String searchField = "";
		String searchText = "";
		
		if(request.getParameter("searchCondition") != "" && request.getParameter("searchKeyword") != "") {
			searchField = request.getParameter("searchCondition");
			searchText = request.getParameter("searchKeyword");
		}
		
		BoardDAO boardDAO = new BoardDAO();
		
		List<BoardDO> boardList = boardDAO.getBoardList(searchField, searchText);
		
		//session 으로 저장하는거 보다 modelandview가 서버에 부담을 줄여준다.
		ModelAndView mav = new ModelAndView();
		mav.addObject("boardList", boardList); //model 정보 저장
		mav.setViewName("getBoardList"); //view 정보 저장
		
		return mav;
	}

}
