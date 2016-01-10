package com.mbe.umlce.controller;

import java.security.Permission;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mbe.umlce.model.Contact;

@Controller
@RequestMapping(value = "/")
public class HomeController {

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST,
			RequestMethod.DELETE, RequestMethod.PUT })
	public String redirect() {
		return "redirect:/portal";

	}

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST,
			RequestMethod.DELETE, RequestMethod.PUT }, produces = "application/json")
	public ResponseEntity<?> doGetAjax() {
		return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
	}

}
