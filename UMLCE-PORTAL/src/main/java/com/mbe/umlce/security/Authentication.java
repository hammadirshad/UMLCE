package com.mbe.umlce.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.mbe.umlce.model.Role;
import com.mbe.umlce.model.User;

public class Authentication {
	private static final Logger logger = Logger
			.getLogger(Authentication.class);
	
	public static boolean hasRole(final String userRole) {
		@SuppressWarnings("unchecked")
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder
				.getContext().getAuthentication().getAuthorities();
		for (GrantedAuthority authority : authorities) {
			if (authority.getAuthority().equals(userRole)) {
				logger.info(userRole + " Found");
				return true;
			}
		}
		logger.info(userRole + " Not Found");
		return false;
	}

	public static boolean hasRole(HttpSession session, String userRole) {
		logger.info("Checkin for " + userRole + " Role");
		User user = (User) session.getAttribute("user");
		for (Role role : user.getRoles()) {
			if (role.getName().equals(userRole)) {
				logger.info(userRole + " Found");
				return true;
			}
		}
		logger.info(userRole + " Not Found");
		return false;
	}

	public static boolean hasRoles(HttpSession session, List<String> userRoles) {
		User user = (User) session.getAttribute("user");
		for (Role role : user.getRoles()) {
			if (userRoles.contains(role.getName())) {
				return true;
			}
		}
		return false;
	}

	public static boolean hasRoles(List<String> userRoles) {
		@SuppressWarnings("unchecked")
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder
				.getContext().getAuthentication().getAuthorities();
		ArrayList<String> roles = new ArrayList<String>();
		for (GrantedAuthority authority : authorities) {
			roles.add(authority.getAuthority());
		}
		for (String role : roles) {
			if (userRoles.contains(role)) {
				return true;
			}
		}
		return false;
	}

}
