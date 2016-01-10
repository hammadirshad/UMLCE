package com.mbe.umlce.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class AuthSuccessHandler extends
		SavedRequestAwareAuthenticationSuccessHandler {
	private static final Logger logger = Logger
			.getLogger(AuthSuccessHandler.class);
	@Override
	protected String determineTargetUrl(HttpServletRequest request,
			HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String role = auth.getAuthorities().toString();
		String targetUrl = "";
		targetUrl = "/portal";
		logger.info("CRUENT USER ROLE : " + role);
		return targetUrl;
	}
}