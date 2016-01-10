package com.mbe.umlce.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mbe.umlce.beans.AssignmentBean;
import com.mbe.umlce.beans.AssignmentSubmissionBean;
import com.mbe.umlce.model.Assignment;
import com.mbe.umlce.model.AssignmentResource;
import com.mbe.umlce.model.AssignmentSubmission;
import com.mbe.umlce.model.SubmissionResource;
import com.mbe.umlce.model.User;
import com.mbe.umlce.reports.ResultService;
import com.mbe.umlce.service.AssignmentResourceService;
import com.mbe.umlce.service.AssignmentService;
import com.mbe.umlce.service.AssignmentSubmissionService;
import com.mbe.umlce.service.SubmissionResourceService;

@Controller
public class AssignmentController {

	private static final Logger logger = Logger
			.getLogger(AssignmentController.class);
	@Autowired
	AssignmentService assignmentService;
	@Autowired
	AssignmentResourceService assignmentResourceService;
	@Autowired
	AssignmentSubmissionService assignmentSubmissionService;
	@Autowired
	SubmissionResourceService submissionResourceService;
	@Autowired
	ResultService resultService;

	@RequestMapping(value = "/portal/assignment/", method = RequestMethod.GET)
	public String welcome() {
		return "redirect:/portal";
	}

	@RequestMapping(value = "/portal/assignment/create", method = RequestMethod.GET)
	public ModelAndView create(Model model) {
		ArrayList<String> diagram = new ArrayList<String>();
		diagram.add("Class Diagram");
		diagram.add("Sequence Diagram");
		diagram.add("Package Diagram");
		diagram.add("Domain Model");
		diagram.add("Activity Diagram");
		diagram.add("System Sequence Diagram");
		diagram.add("State Machine Diagram");
		model.addAttribute("assignmentbean", new AssignmentBean());
		model.addAttribute("diagramType", diagram);
		return new ModelAndView("createAssignment");
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

	@RequestMapping(value = "/portal/assignment/create", method = RequestMethod.POST)
	public ModelAndView createAssignment(
			@ModelAttribute("assignmentbean") @Valid AssignmentBean assignmentbean,
			BindingResult result, HttpServletResponse response, ModelMap model,
			HttpSession session) {

		if (result.hasErrors()) {
			ArrayList<String> diagram = new ArrayList<String>();
			diagram.add("Class Diagram");
			diagram.add("Sequence Diagram");
			diagram.add("Package Diagram");
			diagram.add("Domain Model");
			diagram.add("Activity Diagram");
			diagram.add("System Sequence Diagram");
			diagram.add("State Machine Diagram");
			model.addAttribute("diagramType", diagram);
			return new ModelAndView("createAssignment");
		}

		User user = (User) session.getAttribute("user");
		int size = assignmentbean.getFiles().size();
		for (MultipartFile file : assignmentbean.getFiles()) {
			if (file.getOriginalFilename().equals("")) {
				size--;
			}
		}
		SerialBlob blobreferencemodel = null;
		if (!assignmentbean.getReferencemodel().get(0).getOriginalFilename()
				.equals("")) {
			byte[] Bytes;
			try {
				Bytes = assignmentbean.getReferencemodel().get(0).getBytes();

				try {
					blobreferencemodel = new SerialBlob(Bytes);
					logger.info(assignmentbean.getReferencemodel().get(0)
							.getOriginalFilename());
				} catch (SerialException e) {

					logger.error("Error", e);
				} catch (SQLException e) {

					logger.error("Error", e);
				}
			} catch (IOException e) {
				logger.error("Error", e);
			}
		}
		SerialBlob blobsourcecode = null;
		if (!assignmentbean.getSourcecode().get(0).getOriginalFilename()
				.equals("")) {
			byte[] Bytes;
			try {
				Bytes = assignmentbean.getSourcecode().get(0).getBytes();

				try {
					blobsourcecode = new SerialBlob(Bytes);
					logger.info(assignmentbean.getSourcecode().get(0)
							.getOriginalFilename());
				} catch (SerialException e) {

					logger.error("Error", e);
				} catch (SQLException e) {

					logger.error("Error", e);
				}
			} catch (IOException e) {
				logger.error("Error", e);
			}
		}

		Assignment assignment = new Assignment(assignmentbean.getTitle(),
				assignmentbean.getDiagram(), assignmentbean.getDescription(),
				new Date(), assignmentbean.getEndDate(), user, size,
				assignmentbean.isMistakes(), assignmentbean.isPlagarism(),
				assignmentbean.isCodemapping(), assignmentbean.isEvaluation(),
				blobsourcecode, assignmentbean.getSourcecode().get(0)
						.getOriginalFilename(), blobreferencemodel,
				assignmentbean.getReferencemodel().get(0).getOriginalFilename());
		assignment = assignmentService.save(assignment);

		for (MultipartFile file : assignmentbean.getFiles()) {
			if (!file.getOriginalFilename().equals("")) {
				byte[] Bytes;
				try {
					Bytes = file.getBytes();
					SerialBlob blobfile;
					try {
						blobfile = new SerialBlob(Bytes);

						AssignmentResource assignmentResource = new AssignmentResource(
								file.getOriginalFilename(), blobfile,
								assignment);
						assignmentResourceService.save(assignmentResource);
					} catch (SerialException e) {

						logger.error("Error", e);
					} catch (SQLException e) {

						logger.error("Error", e);
					}
				} catch (IOException e) {
					logger.error("Error", e);
				}
			}
		}
		if (assignment.isEvaluation()) {
			try {
				response.sendRedirect("../assignment/evaluation?assignment="
						+ assignment.getId());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		resultService.plagtriger(assignmentbean.getEndDate(),
				assignment.getId());

		User owner = (User) session.getAttribute("user");
		List<Assignment> assignments = assignmentService.findByOwner(owner);
		model.addAttribute("assignmentList", assignments);
		model.addAttribute("assignmentbean", new AssignmentBean());
		return new ModelAndView("viewMyAssignments");
	}

	@RequestMapping(value = "/portal/assignment/submit", method = RequestMethod.GET)
	public ModelAndView submit(
			@RequestParam(value = "query", required = false) String id,
			Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		Assignment assignment = assignmentService
				.findById(Integer.parseInt(id));
		ArrayList<AssignmentResource> resource = assignmentResourceService
				.findByAssignment(assignment);
		AssignmentSubmission assignmentSubmission = assignmentSubmissionService
				.findByAssignmentOwner(assignment, user);
		List<SubmissionResource> submissionResource = null;
		int submit = 0;
		int status = 0;
		if (assignmentSubmission != null) {
			submit = 1;
			status = 1;
			submissionResource = submissionResourceService
					.findByAssignmentSubmission(assignmentSubmission);
		} else {
			if (assignment.getEndDate().compareTo(new Date()) < 0) {
				status = 2;
			}
		}
		if (assignment.getEndDate().compareTo(new Date()) < 0) {
			submit = 1;
		}
		model.addAttribute("status", status);
		model.addAttribute("submit", submit);
		model.addAttribute("assignmentsubmission", assignmentSubmission);
		model.addAttribute("submissionresource", submissionResource);
		AssignmentBean assignmentbean = new AssignmentBean(assignment.getId(),
				assignment.getTitle(), assignment.getDiagram(),
				assignment.getDescription(), assignment.getStartDate(),
				assignment.getEndDate(), assignment.getOwner(), resource);
		model.addAttribute("assignmentbean", assignmentbean);
		model.addAttribute("resource", resource);
		model.addAttribute("submissionbean", new AssignmentSubmissionBean());
		return new ModelAndView("submitAssignment");
	}

	@RequestMapping(value = "/portal/assignment/submit", method = RequestMethod.POST)
	public ModelAndView submitAssignment(
			@ModelAttribute("submissionbean") @Valid AssignmentSubmissionBean submissionbean,
			BindingResult result, ModelMap model, HttpSession session) {

		int fileCount = 0;
		for (MultipartFile file : submissionbean.getFiles()) {
			if (!file.getOriginalFilename().equals("")) {
				fileCount++;
			}
		}

		if (result.hasErrors() || fileCount <= 0) {

			if (fileCount <= 0) {
				result.rejectValue("files","submissionbean.files",
						"* At least one diagram is required!");

			}

			User user = (User) session.getAttribute("user");
			Assignment assignment = assignmentService.findById(Integer
					.parseInt(submissionbean.getQuery()));
			ArrayList<AssignmentResource> resource = assignmentResourceService
					.findByAssignment(assignment);
			AssignmentSubmission assignmentSubmission = assignmentSubmissionService
					.findByAssignmentOwner(assignment, user);
			List<SubmissionResource> submissionResource = null;
			int submit = 0;
			int status = 0;
			if (assignmentSubmission != null) {
				submit = 1;
				status = 1;
				submissionResource = submissionResourceService
						.findByAssignmentSubmission(assignmentSubmission);
			} else {
				if (assignment.getEndDate().compareTo(new Date()) < 0) {
					status = 2;
				}
			}
			if (assignment.getEndDate().compareTo(new Date()) < 0) {
				submit = 1;
			}
			model.addAttribute("status", status);
			model.addAttribute("submit", submit);
			model.addAttribute("assignmentsubmission", assignmentSubmission);
			model.addAttribute("submissionresource", submissionResource);
			AssignmentBean assignmentbean = new AssignmentBean(
					assignment.getId(), assignment.getTitle(),
					assignment.getDiagram(), assignment.getDescription(),
					assignment.getStartDate(), assignment.getEndDate(),
					assignment.getOwner(), resource);
			model.addAttribute("resource", resource);
			model.addAttribute("assignmentbean", assignmentbean);
			return new ModelAndView("submitAssignment");
		}

		User user = (User) session.getAttribute("user");
		int size = submissionbean.getFiles().size();
		for (MultipartFile file : submissionbean.getFiles()) {
			if (file.getOriginalFilename().equals("")) {
				size--;
			}
		}
		AssignmentSubmission assignmentSubmission = new AssignmentSubmission(
				submissionbean.getDescription(), new Date(), user,
				assignmentService.findById(Integer.parseInt(submissionbean
						.getQuery())), size);
		assignmentSubmission = assignmentSubmissionService
				.save(assignmentSubmission);
		for (MultipartFile file : submissionbean.getFiles()) {
			if (!file.getOriginalFilename().equals("")) {
				byte[] Bytes;
				try {
					Bytes = file.getBytes();
					SerialBlob blobfile;
					try {
						blobfile = new SerialBlob(Bytes);
						SubmissionResource submissionResource = new SubmissionResource(
								file.getOriginalFilename(), blobfile,
								assignmentSubmission,
								assignmentSubmission.getAssignment());
						submissionResourceService.save(submissionResource);
						logger.info(file.getOriginalFilename());

					} catch (SerialException e) {

						logger.error("Error", e);
					} catch (SQLException e) {

						logger.error("Error", e);
					}
				} catch (IOException e) {
					logger.error("Error", e);
				}
			}
		}

		resultService.performResults(assignmentSubmission, submissionbean
				.getFiles().get(1));
		return new ModelAndView("viewAssignments");
	}

	@RequestMapping(value = "/portal/assignment/view", method = RequestMethod.GET)
	public ModelAndView view(Model model) {

		List<Assignment> assignments = assignmentService.findAll();
		model.addAttribute("assignmentList", assignments);
		model.addAttribute("assignmentbean", new AssignmentBean());
		return new ModelAndView("viewAssignments");
	}

	@RequestMapping(value = "/portal/assignment/my_assignments", method = RequestMethod.GET)
	public ModelAndView viewMyAssignments(Model model, HttpSession session) {

		User owner = (User) session.getAttribute("user");
		List<Assignment> assignments = assignmentService.findByOwner(owner);
		model.addAttribute("assignmentList", assignments);
		model.addAttribute("assignmentbean", new AssignmentBean());
		return new ModelAndView("viewMyAssignments");
	}

	@RequestMapping(value = "/portal/assignment/submissions", method = RequestMethod.GET)
	public ModelAndView viewSubmissions(Model model,
			@RequestParam(value = "assignment", required = false) String id) {

		Assignment assignment = new Assignment();
		assignment.setId(Integer.parseInt(id));
		List<AssignmentSubmission> submissions = assignmentSubmissionService
				.findByAssignment(assignment);
		model.addAttribute("submissions", submissions);

		return new ModelAndView("viewSubmissions");
	}

	@RequestMapping(value = "/portal/assignment/download", method = RequestMethod.GET)
	public void getFile(
			@RequestParam(value = "query", required = false) String id,
			HttpServletResponse response, HttpServletRequest request)
			throws IOException, SQLException {

		AssignmentResource assignmentResource = assignmentResourceService
				.findById(Integer.parseInt(id));

		InputStream in = assignmentResource.getFile().getBinaryStream();
		ServletOutputStream out = response.getOutputStream();
		response.setHeader("Content-Disposition", "attachment; filename="
				+ assignmentResource.getSourceName());
		FileCopyUtils.copy(in, out);

		out.flush();
		out.close();
	}

	@RequestMapping(value = "/portal/assignment/submission/download", method = RequestMethod.GET)
	public void getSubmissionFile(
			@RequestParam(value = "submission", required = false) String id,
			HttpServletResponse response, HttpServletRequest request)
			throws IOException, SQLException {

		SubmissionResource assignmentResource = submissionResourceService
				.findById(Integer.parseInt(id));

		InputStream in = assignmentResource.getFile().getBinaryStream();
		ServletOutputStream out = response.getOutputStream();
		response.setHeader("Content-Disposition", "attachment; filename="
				+ assignmentResource.getFileName());
		FileCopyUtils.copy(in, out);

		out.flush();
		out.close();
	}

	@RequestMapping(value = "/portal/assignment/submissions/downloads", method = RequestMethod.GET)
	public void getSubmitedFiles(
			@RequestParam(value = "submission", required = false) String id,
			HttpServletResponse response, HttpServletRequest request)
			throws IOException, SQLException {

		AssignmentSubmission submission = new AssignmentSubmission();
		submission.setId(Integer.parseInt(id));
		List<SubmissionResource> assignmentResource = submissionResourceService
				.findByAssignmentSubmission(submission);

		// ServletOutputStream out=response.getOutputStream();
		InputStream in;
		ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
		response.setHeader("Content-Disposition", "attachment; filename="
				+ assignmentResource.get(0).getAssignmentSub().getAssignment()
						.getTitle()
				+ "_"
				+ assignmentResource.get(0).getAssignmentSub().getOwner()
						.getFname()
				+ " "
				+ assignmentResource.get(0).getAssignmentSub().getOwner()
						.getLname()
				+ "_"
				+ assignmentResource.get(0).getAssignmentSub().getOwner()
						.getId() + ".zip");
		for (SubmissionResource r : assignmentResource) {

			in = r.getFile().getBinaryStream();
			ZipEntry a = new ZipEntry(r.getFileName());
			out.putNextEntry(a);
			byte[] file = FileCopyUtils.copyToByteArray(in);
			logger.info(r.getFileName());
			out.write(file);
		}

		out.flush();
		out.close();
	}

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"dd MMMM yyyy - HH:mm");
		dateFormat.setLenient(false);
		webDataBinder.registerCustomEditor(Date.class, "endDate",
				new CustomDateEditor(dateFormat, true));

		webDataBinder.registerCustomEditor(MultipartFile.class, "sourcecode",
				new CustomCollectionEditor(ArrayList.class));

		webDataBinder.registerCustomEditor(MultipartFile.class, "files",
				new CustomCollectionEditor(ArrayList.class));

		webDataBinder.registerCustomEditor(MultipartFile.class,
				"referencemodel", new CustomCollectionEditor(ArrayList.class));

	}

}
