package org.yejin.book.chap13;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

/**
 * p.373 [리스트 13.22] AuthCheckInterceptor 수정
 * 
 * @author Jacob
 */
public class AuthCheckInterceptor implements HandlerInterceptor {

	@Override
	//controller메소드가 적용되기전에 실행되는 핸들 (후에 실행되는건 postHandle메소드이다)
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		HttpSession session = request.getSession();
		Object member = session.getAttribute("MEMBER");
		if (member != null)
			// 세션에 member가 있을 경우 계속 진행
			return true;

		// 세션에 member가 없을 경우 로그인 화면으로
		response.sendRedirect(request.getContextPath() + "/app/loginForm");
		return false;
	}
}