package com.mbe.umlce.security;

import org.apache.log4j.Logger;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.Authentication;

public class MyMethodSecurityExpressionRoot extends SecurityExpressionRoot {

	private static Logger logger = Logger
			.getLogger(MyMethodSecurityExpressionRoot.class);

	public MyMethodSecurityExpressionRoot(Authentication authentication) {
		super(authentication);
	}

	public boolean hasEntitlement(String expression) {
		logger.info(expression);
		return hasRole(expression);
	}
}
