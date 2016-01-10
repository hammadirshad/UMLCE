package com.mbe.umlce.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mbe.umlce.beans.UserBean;
import com.mbe.umlce.model.Role;
import com.mbe.umlce.model.User;
import com.mbe.umlce.service.RoleService;
import com.mbe.umlce.service.UserService;
import com.mbe.umlce.vo.UserListVO;

@Controller
public class UserController {
	private static final Logger logger = Logger.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "/portal/user/", method = RequestMethod.GET)
	public ModelAndView welcome() {
		logger.info("User Welcome Page");
		return new ModelAndView("userIndex");
	}

	@PreAuthorize("hasPermission('user', 'Create User') and hasRole('Admin')")
	@RequestMapping(value = "/portal/user/create", method = RequestMethod.GET)
	public ModelAndView create(Model model) {
		model.addAttribute("userbean", new UserBean());
		Map<String, String> gender = new LinkedHashMap<String, String>();
		gender.put("M", "Male");
		gender.put("F", "Female");
		model.addAttribute("genderList", gender);
		List<Role> roleList = roleService.findAll();
		model.addAttribute("roleList", roleList);
		model.addAttribute("fieldMatch", "");
		return new ModelAndView("createUser");
	}

	@PreAuthorize("hasPermission('user', 'Create User') and hasRole('Admin')")
	@RequestMapping(value = "/portal/user/create", method = RequestMethod.POST)
	public ModelAndView createUser(
			@ModelAttribute("userbean") @Valid UserBean userBean,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			if (!userBean.getPassword().equals(userBean.getConfirmpass())) {
				model.addAttribute("fieldMatch",
						"* The password fields must match");
			}
			Map<String, String> gender = new LinkedHashMap<String, String>();
			gender.put("M", "Male");
			gender.put("F", "Female");
			model.addAttribute("genderList", gender);
			List<Role> roleList = roleService.findAll();
			model.addAttribute("roleList", roleList);
			return new ModelAndView("createUser");
		}
		try {
			if (userBean.getPassword().equals(userBean.getConfirmpass())) {
				User user = new User(userBean.getFname(), userBean.getLname(),
						userBean.getGender(), "TRUE", userBean.getUsername(),
						userBean.getPassword(), userBean.getEmail(),
						userBean.getPhoneno(), userBean.getAddress(),
						userBean.getRoles());
						user = userService.save(user);
			} else {
				logger.error("Password not match");
			}
		} catch (Exception e) {
			logger.error("Expetion", e);
		}

		List<User> userList = userService.findAll();
		model.addAttribute("userList", userList);
		return new ModelAndView("userList");
	}

	@RequestMapping(value = "/portal/user/list", method = RequestMethod.GET)
	public ModelAndView list(Model model) {
		model.addAttribute("userbean", new UserBean());
		List<User> userList = userService.findAll();
		model.addAttribute("userList", userList);
		return new ModelAndView("userList");
	}

	@RequestMapping(value = "/portal/user/add", method = RequestMethod.GET)
	public ModelAndView add(Model model) {
		model.addAttribute("userbean", new UserBean());
		List<User> userList = userService.findAll();
		model.addAttribute("userList", userList);
		List<Role> roleList = roleService.findAll();
		model.addAttribute("roleList", roleList);
		return new ModelAndView("addUser");
	}

	@RequestMapping(value = "/portal/user/add", method = RequestMethod.POST)
	public ModelAndView addUser(@ModelAttribute("userbean") UserBean userBean,
			ModelMap model,HttpSession session) {
		try {

			User user = userService.findByUsername(userBean.getUsername());
			user.getRoles().addAll(userBean.getRoles());
			user=userService.save(user);
	//		user = userService.update(user,userBean.getRoles());
			user = userService.findByUsername(userBean.getUsername());
			session.setAttribute("user", user);
		} catch (Exception e) {
			logger.error("Expetion", e);
		}
		return new ModelAndView("userIndex");
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		
		binder.registerCustomEditor(Set.class, "roles",
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
						return id != null ? roleService.findById(id) : null;
					}
				});
	}

	@RequestMapping(value = "/portal/user/list", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> list() {
		UserListVO vo = new UserListVO();
		vo.setUsers(userService.findAll());
		return new ResponseEntity<UserListVO>(vo, HttpStatus.OK);
	}
}
