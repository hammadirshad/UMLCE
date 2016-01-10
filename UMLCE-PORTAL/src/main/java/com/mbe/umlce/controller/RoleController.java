package com.mbe.umlce.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mbe.umlce.beans.RoleBean;
import com.mbe.umlce.model.Permission;
import com.mbe.umlce.model.Role;
import com.mbe.umlce.model.User;
import com.mbe.umlce.service.PermissionService;
import com.mbe.umlce.service.RoleService;
import com.mbe.umlce.service.UserService;

@Controller
public class RoleController {
	private static final Logger logger = Logger.getLogger(RoleController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;

	@RequestMapping(value = "/portal/role/", method = RequestMethod.GET)
	public String welcome() {
		return "redirect:/portal";
	}

	@RequestMapping(value = "/portal/role/create", method = RequestMethod.GET)
	public ModelAndView create(Model model) {
		model.addAttribute("rolebean", new RoleBean());
		List<Permission> permissionList = permissionService.findAll();
		model.addAttribute("permissionList", permissionList);
		return new ModelAndView("createRole");
	}

	@RequestMapping(value = "/portal/role/create", method = RequestMethod.POST)
	public String createUser(@ModelAttribute("rolebean") RoleBean roleBean,
			ModelMap model) {
		try {
			Role role = new Role();
			role.setName(roleBean.getName());
			role.setPermissions(roleBean.getPermissions());

			logger.info("Role " + role.getName());
			for (Permission p : role.getPermissions()) {
				logger.info(p.getName() + " " + role.getId());
			}

			role = roleService.save(role);

		} catch (Exception e) {
			logger.error("Expetion", e);
		}

		return "redirect:/portal";
	}

	@RequestMapping(value = "/portal/role/add", method = RequestMethod.GET)
	public ModelAndView add(Model model) {
		model.addAttribute("rolebean", new RoleBean());

		List<Role> roleList = roleService.findAll();
		model.addAttribute("roleList", roleList);
		List<Permission> permissionList = permissionService.findAll();
		model.addAttribute("permissionList", permissionList);
		return new ModelAndView("addRole");
	}

	@RequestMapping(value = "/portal/role/add", method = RequestMethod.POST)
	public ModelAndView addUser(@ModelAttribute("rolebean") RoleBean roleBean,
			ModelMap model, HttpSession session) {
		try {
			Role role = roleService.findById(roleBean.getId());
			role.getPermissions().addAll(roleBean.getPermissions());
			role = roleService.save(role);
			User user = (User) session.getAttribute("user");
			user = userService.findByUsername(user.getUsername());
			session.setAttribute("user", user);
		} catch (Exception e) {
			logger.error("Expetion", e);
		}
		return new ModelAndView("userIndex");
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Set.class, "permissions",
				new CustomCollectionEditor(Set.class) {
					@Override
					protected Object convertElement(Object element) {
						Integer id = null;
						if (element instanceof String
								&& !((String) element).equals("")) {
							try {
								id = Integer.parseInt((String) element);
							} catch (Exception e) {
								logger.error("Number Formate Expection", e);
							}
						} else if (element instanceof Integer) {
							id = (Integer) element;
						}
						return id != null ? permissionService.findById(id)
								: null;
					}
				});
	}

}
