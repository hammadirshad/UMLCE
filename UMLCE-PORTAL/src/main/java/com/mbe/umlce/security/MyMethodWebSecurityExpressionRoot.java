package com.mbe.umlce.security;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;

public class MyMethodWebSecurityExpressionRoot extends
		WebSecurityExpressionRoot {

	private static Logger logger = Logger
			.getLogger(MyMethodSecurityExpressionRoot.class);

	public MyMethodWebSecurityExpressionRoot(Authentication authentication,
			FilterInvocation invocation) {
		super(authentication, invocation);
	}

	public boolean hasEntitlement(String expression) {
		logger.info(expression);
		return super.request.getSession().getAttribute("user") != null;
	}
}
