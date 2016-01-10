package com.mbe.umlce.interceptor;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mbe.umlce.model.User;
import com.mbe.umlce.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = Logger
			.getLogger(LoginInterceptor.class);

	@Autowired
	private UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			String username = auth.getName();
			user = userService.findByUsername(username);
			session.setAttribute("user", user);
		}
		logger.info("User Login is " + user.getUsername());
		return super.preHandle(request, response, handler);
	}
}
