package com.bit2016.emaillist.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit2016.emaillist.dao.EmailListDao;
import com.bit2016.emaillist.vo.EmailListVo;

@WebServlet("/el")
public class EmailListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// action name 가져오기
		String actionName = request.getParameter("a");
		if ("form".equals(actionName)) {
			// form 요청에 대한 처리
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/form.jsp");
			rd.forward(request, response);
		} else if ("insert".equals(actionName)) {
			// insert 요청에 대한 처리
			String firstName = request.getParameter("fn");
			String lastName = request.getParameter("ln");
			String email = request.getParameter("email");
			
			EmailListVo vo = new EmailListVo();
			vo.setEmail(email);
			vo.setFirstName(firstName);
			vo.setLastName(lastName);
			
			EmailListDao dao = new EmailListDao();
			dao.insert(vo);
			
			response.sendRedirect("/emaillist2/el");
		} else {
			// default action 처리(리스트 처리)
			EmailListDao dao = new EmailListDao();
			List<EmailListVo> list = dao.getList();
			
			// request 범위에 모델데이터 저장
			request.setAttribute("list", list);
			
			// forwarding(request 연장, request dispatch)
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/index.jsp");
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
