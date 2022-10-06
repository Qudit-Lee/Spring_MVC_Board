package com.company.view.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.company.Spring_MVC_Board.user.UserDAO;
import com.company.Spring_MVC_Board.user.UserDO;

public class LoginController implements Controller {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("===> 로그인 처리 완료!!!");
		
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		UserDO userDO = new UserDO();
		userDO.setId(id);
		userDO.setPassword(password);
		
		UserDAO userDAO = new UserDAO();
		UserDO user = userDAO.getUser(userDO);
		
		//MVC FW 개발 방법과 다른 부분
		ModelAndView mav = new ModelAndView(); //객체 생성
		
		if(user != null) { //로그인 성공
			HttpSession session= request.getSession();
			session.setAttribute("idkey", id);
			mav.setViewName("redirect:getBoardList.do"); //view 정보 저장
			System.out.println("로그인 성공!!");
		}else { //로그인 실패시
			mav.setViewName("redirect:login.jsp");
			System.out.println("로그인 실패!!!");
		}
		
		return mav;
	}
	
}
