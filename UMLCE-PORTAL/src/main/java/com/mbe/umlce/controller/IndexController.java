package com.mbe.umlce.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbe.umlce.beans.CalendarBean;
import com.mbe.umlce.beans.PasswordBean;
import com.mbe.umlce.beans.UpdateUserBean;
import com.mbe.umlce.model.Assignment;
import com.mbe.umlce.model.AssignmentSubmission;
import com.mbe.umlce.model.User;
import com.mbe.umlce.service.AssignmentService;
import com.mbe.umlce.service.AssignmentSubmissionService;
import com.mbe.umlce.service.UserService;

@Controller
public class IndexController {
	private static final Logger logger = Logger
			.getLogger(IndexController.class);
	@Autowired
	ServletContext servletContext;
	@Autowired
	AssignmentService assignmentService;
	@Autowired
	AssignmentSubmissionService assignmentSubmissionService;
	@Autowired
	UserService userService;

	@RequestMapping(value = "/portal", method = RequestMethod.GET)
	public ModelAndView welcome() {
		return new ModelAndView("welcomePage");
	}

	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public Error handleException(MethodArgumentNotValidException exception) {
		logger.error("Method Argument Not Valid Exception", exception);
		return null;
	}

	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public Error handleException(Exception exception) {
		logger.error("All Exception ", exception);
		return null;
	}

	@RequestMapping(value = "/portal/profile", method = RequestMethod.GET)
	public ModelAndView profile(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		user = userService.findByUsername(user.getUsername());
		List<AssignmentSubmission> submissions = assignmentSubmissionService
				.findByOwner(user);
		double marks = 0.0;
		int total = 0;
		for (AssignmentSubmission submission : submissions) {
			if (submission.getAssignment().getTotalMarks() != 0
					|| submission.getMarks() != 0) {
				marks = marks
						+ ((submission.getMarks() / submission.getAssignment()
								.getTotalMarks()) * 100);
				total++;
			}
		}
		marks = marks / total;
		UpdateUserBean userbean = new UpdateUserBean();
		model.addAttribute("userbean", userbean);
		model.addAttribute("passbean", new PasswordBean());
		model.addAttribute("userdata", user);
		model.addAttribute("assignmentcount", assignmentService.findAll()
				.size());
		model.addAttribute("submissioncount", submissions.size());
		model.addAttribute("marks", marks);
		return new ModelAndView("profilePage");
	}

	@RequestMapping(value = "/portal/profile", method = RequestMethod.POST)
	public ModelAndView updateprofile(HttpSession session, ModelMap model,
			@ModelAttribute("userbean") @Valid UpdateUserBean userbean,
			BindingResult result) {
		if (result.hasErrors()) {
			User user = (User) session.getAttribute("user");
			user = userService.findByUsername(user.getUsername());
			List<AssignmentSubmission> submissions = assignmentSubmissionService
					.findByOwner(user);
			double marks = 0.0;
			int total = 0;
			for (AssignmentSubmission submission : submissions) {
				if (submission.getAssignment().getTotalMarks() != 0
						|| submission.getMarks() != 0) {
					marks = marks
							+ ((submission.getMarks() / submission
									.getAssignment().getTotalMarks()) * 100);
					total++;
				}
			}
			marks = marks / total;
			model.addAttribute("passbean", new PasswordBean());
			model.addAttribute("userdata", user);
			model.addAttribute("assignmentcount", assignmentService.findAll()
					.size());
			model.addAttribute("submissioncount", submissions.size());
			model.addAttribute("marks", marks);
			return new ModelAndView("profilePage");
		}
		userService.updateProfile(userbean);
		User user=userService.findByUsername(userbean.getUsername());
		session.setAttribute("user", user);
		List<AssignmentSubmission> submissions = assignmentSubmissionService
				.findByOwner(user);
		double marks = 0.0;
		int total = 0;
		for (AssignmentSubmission submission : submissions) {
			if (submission.getAssignment().getTotalMarks() != 0
					|| submission.getMarks() != 0) {
				marks = marks
						+ ((submission.getMarks() / submission.getAssignment()
								.getTotalMarks()) * 100);
				total++;
			}
		}
		marks = marks / total;

		model.addAttribute("passbean", new PasswordBean());
		model.addAttribute("userdata", user);
		model.addAttribute("assignmentcount", assignmentService.findAll()
				.size());
		model.addAttribute("submissioncount", submissions.size());
		model.addAttribute("marks", marks);

		return new ModelAndView("profilePage");
	}

	@RequestMapping(value = "/portal/profile/changePassword", method = RequestMethod.POST)
	public ModelAndView updatePassword(HttpSession session, Model model,
			HttpServletResponse res,
			@ModelAttribute("passbean") @Valid PasswordBean passbean,
			BindingResult result) {
		User user = (User) session.getAttribute("user");
		user = userService.findByUsername(user.getUsername());
		if (result.hasErrors()) {
			if (!passbean.getNewpass().equals(passbean.getRenewpass())) {
				model.addAttribute("fieldMatch",
						"* The password fields must match");
			}
			
			User user1 = (User) session.getAttribute("user");
			user1 = userService.findByUsername(user.getUsername());
			List<AssignmentSubmission> submissions = assignmentSubmissionService
					.findByOwner(user);
			double marks = 0.0;
			int total = 0;
			for (AssignmentSubmission submission : submissions) {
				if (submission.getAssignment().getTotalMarks() != 0
						|| submission.getMarks() != 0) {
					marks = marks
							+ ((submission.getMarks() / submission
									.getAssignment().getTotalMarks()) * 100);
					total++;
				}
			}
			marks = marks / total;
			model.addAttribute("userbean", new UpdateUserBean());
			model.addAttribute("userdata", user1);
			model.addAttribute("assignmentcount", assignmentService.findAll()
					.size());
			model.addAttribute("submissioncount", submissions.size());
			model.addAttribute("marks", marks);
			return new ModelAndView("profilePage");

		}

		if (user.getPassword().equals(passbean.getOldpass()))

		{
			userService.updatePassword(passbean.getNewpass(), user.getUsername());
		}
		else
			model.addAttribute("currentPassword",
					"* Current Password Incorrect");
		model.addAttribute("passbean", new PasswordBean());
		session.setAttribute("user", user);
		List<AssignmentSubmission> submissions = assignmentSubmissionService
				.findByOwner(user);
		double marks = 0.0;
		int total = 0;
		for (AssignmentSubmission submission : submissions) {
			if (submission.getAssignment().getTotalMarks() != 0
					|| submission.getMarks() != 0) {
				marks = marks
						+ ((submission.getMarks() / submission.getAssignment()
								.getTotalMarks()) * 100);
				total++;
			}

		}
		marks = marks / total;
		model.addAttribute("userbean", new UpdateUserBean());

		model.addAttribute("userdata", user);
		model.addAttribute("assignmentcount", assignmentService.findAll()
				.size());
		model.addAttribute("submissioncount", submissions.size());
		model.addAttribute("marks", marks);

		return new ModelAndView("profilePage");
	}

	@RequestMapping(value = "/portal/profile/changePicture", method = RequestMethod.POST)
	public ModelAndView updatePicture(HttpSession session, Model model,
			HttpServletResponse res,
			@RequestParam("picture") MultipartFile picture) {
		User user=(User) session.getAttribute("user");
		user=userService.findByUsername(user.getUsername());
		try {
			user.setPicture(new SerialBlob(picture.getBytes()));
			user=userService.save(user);
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("passbean", new PasswordBean());
		session.setAttribute("user", user);
		List<AssignmentSubmission> submissions = assignmentSubmissionService
				.findByOwner(user);
		double marks = 0.0;
		int total = 0;
		for (AssignmentSubmission submission : submissions) {
			if (submission.getAssignment().getTotalMarks() != 0
					|| submission.getMarks() != 0) {
				marks = marks
						+ ((submission.getMarks() / submission.getAssignment()
								.getTotalMarks()) * 100);
				total++;
			}

		}
		marks = marks / total;
		model.addAttribute("userbean", new UpdateUserBean());

		model.addAttribute("userdata", user);
		model.addAttribute("assignmentcount", assignmentService.findAll()
				.size());
		model.addAttribute("submissioncount", submissions.size());
		model.addAttribute("marks", marks);
		return new ModelAndView("profilePage");
	}
	
	@RequestMapping(value = "/portal/profile_pic", method = RequestMethod.GET)
	public void getPicture(HttpServletResponse response, HttpServletRequest request, HttpSession session) {
		User user = (User) session.getAttribute("user");
		user = userService.findByUsername(user.getUsername());
		InputStream in;
		try {
			if(user.getPicture()==null)
			{
				URL url = getClass().getClassLoader().getResource("profile/default_profile_4.png");
				in =new FileInputStream(url.getPath());
			}
			else
			in = user.getPicture().getBinaryStream();
			ServletOutputStream out = response.getOutputStream();
			response.setHeader("Content-Disposition", "attachment; filename=");
			FileCopyUtils.copy(in, out);
			out.flush();
			out.close();
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@RequestMapping(value = "/portal/calendar", method = RequestMethod.GET)
	public ModelAndView calendar(Model model, HttpSession session) {

		List<Assignment> assignments = assignmentService.findAll();
		List<CalendarBean> calendarList = new ArrayList<CalendarBean>();
		for (Assignment assignment : assignments) {
			CalendarBean calendar = new CalendarBean();
			calendar.setTitle("'" + assignment.getTitle() + "'");

			User user = (User) session.getAttribute("user");
			AssignmentSubmission submission = assignmentSubmissionService
					.findByAssignmentOwner(assignment, user);

			if (submission == null) {

				if ((new Date()).compareTo(assignment.getEndDate()) > 0) {

					calendar.setBackgroundColor("Metronic.getBrandColor('red')");
					Date date = assignment.getEndDate();
					final Calendar c = Calendar.getInstance();
					c.setTime(date);
					calendar.setStart("new Date(" + c.get(Calendar.YEAR) + ","
							+ c.get(Calendar.MONTH) + ","
							+ c.get(Calendar.DATE) + ", "
							+ c.get(Calendar.HOUR) + ", "
							+ c.get(Calendar.MINUTE) + ")");

				} else {

					logger.info(assignment.getTitle());

					calendar.setBackgroundColor("Metronic.getBrandColor('yellow')");
					Date date = assignment.getEndDate();
					final Calendar c = Calendar.getInstance();
					c.setTime(date);
					calendar.setStart("new Date(" + c.get(Calendar.YEAR) + ","
							+ c.get(Calendar.MONTH) + ","
							+ c.get(Calendar.DATE) + ", "
							+ c.get(Calendar.HOUR) + ", "
							+ c.get(Calendar.MINUTE) + ")");
				}
			} else {
				calendar.setBackgroundColor("Metronic.getBrandColor('green')");
				Date date = submission.getSubmissionDate();
				final Calendar c = Calendar.getInstance();
				c.setTime(date);
				calendar.setStart("new Date(" + c.get(Calendar.YEAR) + ","
						+ c.get(Calendar.MONTH) + "," + c.get(Calendar.DATE)
						+ ", " + c.get(Calendar.HOUR) + ", "
						+ c.get(Calendar.MINUTE) + ")");
			}
			calendar.setUrl("'./assignment/submit?query=" + assignment.getId()
					+ "'");
			calendarList.add(calendar);
		}

		ObjectMapper mapper = new ObjectMapper();
		try {
			String json = mapper.writeValueAsString(calendarList);
			logger.info(json.replace("\"", ""));
			// model.addAttribute("calendarData"," [{ title: 'All Day Event', start: new Date(y, m, 1),backgroundColor: Metronic.getBrandColor('yellow')}]");
			model.addAttribute("calendarData", json.replace("\"", ""));
		} catch (JsonProcessingException e) {
			logger.error(e);
		}

		return new ModelAndView("calendarPage");
	}

}
