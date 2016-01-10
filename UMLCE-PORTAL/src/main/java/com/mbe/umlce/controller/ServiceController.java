package com.mbe.umlce.controller;


import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fyp.umce.Service;
import com.mbe.umlce.beans.ServiceBean;

@Controller
public class ServiceController {
	
	private static final Logger logger = Logger
			.getLogger(ServiceController.class);
	
	@RequestMapping(value = "/portal/service", method = RequestMethod.GET)
	public ModelAndView getService(Model model) throws MalformedURLException, URISyntaxException {
		String serviceParts="";
		ServiceBean servicebean=new ServiceBean();
		logger.info(getClass().getClassLoader().getResource(""));
			serviceParts=serviceParts+Service.getURL();
			URL url = new URL(serviceParts);
			servicebean.setHost(url.getHost());
			servicebean.setPort(""+url.getPort());
			logger.info(url.getPath());
			if(url.getPath().split("/").length>1){
				servicebean.setService(url.getPath().split("/")[1]);
			}
		
		model.addAttribute("servicebean", servicebean);
		return new ModelAndView("changeService");
	}
	
	@RequestMapping(value = "/portal/service", method = RequestMethod.POST)
	public ModelAndView updateService(
			@ModelAttribute("servicebean") @Valid ServiceBean servicebean,
			BindingResult result, ModelMap model)  {
		try{
	
		URL url=new URL("http", servicebean.getHost(), Integer.parseInt(servicebean.getPort()), "/"+servicebean.getService());
		logger.info(url.toURI().toString());
		Service.setURL(url.toURI().toString());
		}
		catch(Exception e)
		{
			model.addAttribute("syntax", "*Service URL Syntex Incorrect");
			logger.info(e);
		}
		model.addAttribute("servicebean", servicebean);
		return new ModelAndView("changeService");
	}

}
