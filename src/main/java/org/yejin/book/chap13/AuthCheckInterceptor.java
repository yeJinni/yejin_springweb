package org.yejin.book.chap13;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * p.373 [리스트 13.22] AuthCheckInterceptor 수정
 * 로그인 안했을 경우 로그인 화면으로 redirect.<br>
 * 
 * @author Yejin
 */
public class AuthCheckInterceptor implements HandlerInterceptor {

	static final Logger logger = LogManager.getLogger();
	
	@Override
	//controller메소드가 적용되기전에 실행되는 핸들 (후에 실행되는건 postHandle메소드이다)
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		HttpSession session = request.getSession();
		Object member = session.getAttribute("MEMBER");
		if (member != null)
			// 세션에 member가 있을 경우 계속 진행
			return true;

		// 세션에 member가 없을 경우 로그인 화면으로 redirect 한다.
		// 로그인 화면으로 redirect 할때 returnUrl을 파라미터로 붙여서
		// 로그인 후에 returnUrl로 돌아갈 수 있도록 한다.
		String requestURL = request.getRequestURL().toString();
		String queryString = request.getQueryString();
		String returnUrl = queryString == null ? requestURL
						: requestURL + "?" + queryString;
		logger.debug("returnUrl = {}", returnUrl);
		response.sendRedirect(request.getContextPath()
						+ "/app/loginForm?returnUrl=" + returnUrl);
		return false;
	}
}