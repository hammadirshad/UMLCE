package com.mbe.umlce.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mbe.umlce.beans.PermissionBean;
import com.mbe.umlce.model.Permission;
import com.mbe.umlce.model.PermissionLevel;
import com.mbe.umlce.model.User;
import com.mbe.umlce.service.PermissionLevelService;
import com.mbe.umlce.service.PermissionService;
import com.mbe.umlce.service.UserService;

@Controller
public class PermissionController {
	private static final Logger logger = Logger
			.getLogger(PermissionController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private PermissionLevelService permissionLevelService;

	@RequestMapping(value = "/portal/permission/", method = RequestMethod.GET)
	public String welcome() {
		return "redirect:/portal";
	}

	@RequestMapping(value = "/portal/permission/create", method = RequestMethod.GET)
	public ModelAndView create(Model model) {
		model.addAttribute("permissionbean", new PermissionBean());
		return new ModelAndView("createPermission");
	}

	@RequestMapping(value = "/portal/permission/create", method = RequestMethod.POST)
	public String createUser(
			@ModelAttribute("permissionbean") PermissionBean permissionBean,
			ModelMap model) {

		try {

			Set<PermissionLevel> levels = new HashSet<PermissionLevel>();
			for (String level : permissionBean.getLevels()) {

				PermissionLevel permissionLevel = permissionLevelService
						.findByName(level);
				if (permissionLevel == null) {
					permissionLevel = new PermissionLevel(level);
					permissionLevel=permissionLevelService.save(permissionLevel);
				}
				levels.add(permissionLevel);
			}

			Permission permission = new Permission();
			permission.setName(permissionBean.getName());
			permission.setPermissionLevels(levels);
			permissionService.save(permission);
		} catch (Exception e) {
			logger.error("Expetion", e);
		}

		return "redirect:/portal";
	}

	@RequestMapping(value = "/portal/permission/add", method = RequestMethod.GET)
	public ModelAndView add(Model model) {
		model.addAttribute("permissionbean", new PermissionBean());
		List<Permission> permissionList = permissionService.findAll();
		model.addAttribute("permissionList", permissionList);
		return new ModelAndView("addPermission");
	}

	@RequestMapping(value = "/portal/permission/add", method = RequestMethod.POST)
	public ModelAndView addUser(
			@ModelAttribute("permissionbean") PermissionBean permissionBean,
			ModelMap model,HttpSession session) {
		try {
			Permission permission = permissionService.findById(permissionBean
					.getId());
			Set<PermissionLevel> levels = new HashSet<PermissionLevel>();
			for (String level : permissionBean.getLevels()) {

				PermissionLevel permissionLevel = permissionLevelService
						.findByName(level);
				if (permissionLevel == null) {
					permissionLevel = new PermissionLevel(level);
				}
				levels.add(permissionLevel);
			}
			permission.setPermissionLevels(levels);
			permission = permissionService.save(permission);
			User user = (User) session.getAttribute("user");
			user = userService.findByUsername(user.getUsername());
			session.setAttribute("user", user);
		} catch (Exception e) {
			logger.error("Expetion", e);
		}
		return new ModelAndView("userIndex");
	}

}
