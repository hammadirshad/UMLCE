package com.mbe.umlce.security;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.util.MethodInvocationUtils;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

public class MyMethodSecurityExpressionHandler extends
		DefaultMethodSecurityExpressionHandler {

/*
	@Override
	public EvaluationContext createEvaluationContext(Authentication auth,
			MethodInvocation mi) {
		StandardEvaluationContext ctx = (StandardEvaluationContext) super
				.createEvaluationContext(auth, mi);
		ctx.setRootObject(new MyMethodSecurityExpressionRoot(auth));
		return ctx;
	}*/

}