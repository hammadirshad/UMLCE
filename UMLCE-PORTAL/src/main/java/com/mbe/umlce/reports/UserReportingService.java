package com.mbe.umlce.reports;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mbe.umlce.beans.MistakeReportBean;
import com.mbe.umlce.beans.UserEvaluationReportBean;
import com.mbe.umlce.beans.UserPlagarismReportBean;
import com.mbe.umlce.model.AssignmentSubmission;
import com.mbe.umlce.model.Mistake;
import com.mbe.umlce.model.User;
import com.mbe.umlce.service.AssignmentSubmissionService;
import com.mbe.umlce.service.MistakeService;
import com.mbe.umlce.service.UserService;

@Service
public class UserReportingService {

	private static final Logger logger = Logger
			.getLogger(UserReportingService.class);
	@Autowired
	MistakeService mistakeService;
	@Autowired
	UserService userService;
	@Autowired
	AssignmentSubmissionService assignmentSubmissionService;
	
	public List<MistakeReportBean> getUserMistakesReport(String username) {
		User user = userService.findByUsername(username);
		List<Mistake> mistakes = mistakeService.findByMistaker(user);
		List<MistakeReportBean> mistakesreport = new ArrayList<MistakeReportBean>();
		HashMap<String, MistakeReportBean> mistakesreport1 = new HashMap<String, MistakeReportBean>();
		String key = null;
		for (Mistake mistake : mistakes) {
			key = mistake.getErrorName() + "," + mistake.getType() + ","
					+ mistake.getModelType() + "," + mistake.getElementName();
			if (mistakesreport1.containsKey(key)) {
				mistakesreport1.get(key).setCount(
						mistakesreport1.get(key).getCount() + 1);
			} else {
				MistakeReportBean mistakereportbean = new MistakeReportBean(
						mistake.getErrorName(), mistake.getType(),
						mistake.getModelType(), mistake.getElementName(), 1);
				mistakesreport1.put(key, mistakereportbean);
			}
		}
		mistakesreport = new ArrayList<MistakeReportBean>();
		for (Entry<String, MistakeReportBean> entry : mistakesreport1
				.entrySet()) {
			mistakesreport.add(entry.getValue());
		}
		Collections.sort(mistakesreport, new Comparator<MistakeReportBean>() {
			public int compare(MistakeReportBean m1, MistakeReportBean m2) {
				return m2.getCount().compareTo(m1.getCount());
			}
		});
		return mistakesreport;
	}

	public DefaultCategoryDataset getUserMistakesReportGraph(String username) {
		List<MistakeReportBean> mistakesreport = getUserMistakesReport(username);
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (MistakeReportBean mistakeReportBean : mistakesreport) {
			dataset.addValue(mistakeReportBean.getCount(),
					mistakeReportBean.getErrorName(),
					mistakeReportBean.getElementName());
		}
		return dataset;
	}

	public List<UserEvaluationReportBean> getUserEvaluationReport() {
		List<User> users = userService.findAll();
		List<UserEvaluationReportBean> reportBeans = new ArrayList<UserEvaluationReportBean>();

		for (User user : users) {
			List<AssignmentSubmission> submissions = assignmentSubmissionService
					.findByOwner(user);
			if (submissions.size() <= 0) {
				continue;
			}
			double min = 100000, max = 0, marks = 0;
			for (AssignmentSubmission submission : submissions) {
				if (!submission.getAssignment().isEvaluation()) {
					continue;
				}
				if (submission.getAssignment().getTotalMarks() >= 0) {
					if ((submission.getMarks() / submission.getAssignment()
							.getTotalMarks()) * 100 > max) {
						max = (submission.getMarks() / submission
								.getAssignment().getTotalMarks()) * 100;
					}
					if ((submission.getMarks() / submission.getAssignment()
							.getTotalMarks()) * 100 < min) {
						min = (submission.getMarks() / submission
								.getAssignment().getTotalMarks()) * 100;
					}
					marks = marks
							+ (submission.getMarks() / submission
									.getAssignment().getTotalMarks()) * 100;
				}
			}

			logger.info(marks);
			logger.info(submissions.size());
			logger.info(marks / submissions.size());
			reportBeans.add(new UserEvaluationReportBean(user.getId(), user
					.getFname(), user.getLname(), marks / submissions.size(),
					max, min, submissions.size()));
		}

		Collections.sort(reportBeans,
				new Comparator<UserEvaluationReportBean>() {
					public int compare(UserEvaluationReportBean m1,
							UserEvaluationReportBean m2) {
						return m2.getMarks().compareTo(m1.getMarks());
					}
				});
		return reportBeans;
	}

	public List<UserPlagarismReportBean> getUserPlagarismReport() {
		List<User> users = userService.findAll();
		List<UserPlagarismReportBean> plagarismbeans = new ArrayList<UserPlagarismReportBean>();
		for (User user : users) {
			List<AssignmentSubmission> submissions = assignmentSubmissionService
					.findByOwner(user);
			int above80 = 0, above50 = 0, aboveaverage = 0, less50 = 0;

			for (AssignmentSubmission submission : submissions) {
				double average = 0;
				List<AssignmentSubmission> submissions1 = assignmentSubmissionService
						.findByAssignment(submission.getAssignment());
				for (AssignmentSubmission submission1 : submissions1) {
					average = average + submission1.getPlagarism();
				}
				average = average / submissions1.size();
				if (submission.getPlagarism() >= 80)
					above80++;
				else if (submission.getPlagarism() >= 50)
					above50++;
				else if (submission.getPlagarism() < 50)
					less50++;
				if (submission.getPlagarism() > average)
					aboveaverage++;
			}
			UserPlagarismReportBean userbean = new UserPlagarismReportBean(
					user.getId(), user.getFname() + " " + user.getLname(),
					user.getUsername(), submissions.size(), above80, above50,
					aboveaverage, less50);
			plagarismbeans.add(userbean);
		}
		return plagarismbeans;
	}

	public DefaultCategoryDataset getUserPlagarismReportGraph() {

		List<UserPlagarismReportBean> plagarismReportBeans = getUserPlagarismReport();
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	
		
		for (UserPlagarismReportBean reportBean : plagarismReportBeans) {
			dataset.addValue(reportBean.getAbove50(), "Above 50",
					reportBean.getName());
			dataset.addValue(reportBean.getAbove80(), "Above 80",
					reportBean.getName());
			dataset.addValue(reportBean.getLess50(), "Below 50",
					reportBean.getName());
			dataset.addValue(reportBean.getAboveaverage(), "Above Avrage",
					reportBean.getName());
		}
		return dataset;

	}

}
