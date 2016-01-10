package com.mbe.umlce.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fyp.umce.ActivityDiagram;
import com.fyp.umce.ClassDiagram;
import com.fyp.umce.DomainModel;
import com.fyp.umce.SequenceDiagram;
import com.fyp.umce.StateMachineDiagram;
import com.fyp.umce.SystemSequenceDiagram;
import com.fyp.umce.UsecaseDiagram;
import com.fyp.umce.dataobject.ModelFile;
import com.fyp.umce.dataobject.result.Plagiarism;
import com.mbe.umlce.controller.EvaluationController;
import com.mbe.umlce.model.Assignment;
import com.mbe.umlce.model.Plagarism;
import com.mbe.umlce.model.SubmissionResource;
import com.mbe.umlce.model.User;

public class PlagarismFinder implements Job {

	private ClassDiagram classChecker = new ClassDiagram();
	private UsecaseDiagram usecaseChecker = new UsecaseDiagram();
	private DomainModel domainChecker = new DomainModel();
	private SystemSequenceDiagram systemSequenceChecker = new SystemSequenceDiagram();
	private SequenceDiagram sequenceChecker = new SequenceDiagram();
	private ActivityDiagram activityChecker = new ActivityDiagram();
	private StateMachineDiagram statemachine = new StateMachineDiagram();
	private static final Logger logger =
			 Logger.getLogger(EvaluationController.class);

	@Override
	public void execute(JobExecutionContext arg) throws JobExecutionException {
		JobDataMap jdMap = arg.getJobDetail().getJobDataMap();
		ArrayList<Plagiarism> plagarisms = null;
		int id = (int) jdMap.get("id");
		ApplicationContext applicationContext = WebApplicationContextUtils
				.getWebApplicationContext(ContextLoaderListener
						.getCurrentWebApplicationContext().getServletContext());
		AssignmentService assignmentService = (AssignmentService) applicationContext
				.getBean(AssignmentService.class);
		Assignment assignment = assignmentService.findById(id);
		SubmissionResourceService submissionResourceService = (SubmissionResourceService) applicationContext
				.getBean(SubmissionResourceService.class);
		List<SubmissionResource> resources = submissionResourceService
				.findByAssignment(assignment);
		File filee = new File("compressed.zip");
		ZipOutputStream zipOutputStream = null;
		try {
			zipOutputStream = new ZipOutputStream(new FileOutputStream(filee));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			for (SubmissionResource r : resources) {
				ZipEntry zipEntry = new ZipEntry(r.getAssignmentSub().getOwner().getId()+"_"+r.getAssignmentSub().getId()+"_"+r.getFileName());
				byte[] file = FileCopyUtils.copyToByteArray(r.getFile()
						.getBinaryStream());
				zipEntry.setSize(file.length);
				System.out.println(zipEntry.getSize());
				zipOutputStream.putNextEntry(zipEntry);
				zipOutputStream.write(file);
			}
			zipOutputStream.flush();
			zipOutputStream.close();

			switch (assignment.getDiagram()) {
			case "Class Diagram":
				classChecker.checkPlagiarism(
						new ModelFile(new FileInputStream(filee)))
						.getPlagiarism();
				break;
			case "Domain Model":
				plagarisms = domainChecker.checkPlagiarism(
						new ModelFile(new FileInputStream(filee)))
						.getPlagiarism();
				break;
			case "Activity Diagram":
				plagarisms = activityChecker.checkPlagiarism(
						new ModelFile(new FileInputStream(filee)))
						.getPlagiarism();
				break;
			case "Sequence Diagram":
				plagarisms = sequenceChecker.checkPlagiarism(
						new ModelFile(new FileInputStream(filee)))
						.getPlagiarism();
				break;
			case "System Sequence Diagram":
				plagarisms = systemSequenceChecker.checkPlagiarism(
						new ModelFile(new FileInputStream(filee)))
						.getPlagiarism();
				break;
			case "Usecase Diagram":
				plagarisms = usecaseChecker.checkPlagiarism(
						new ModelFile(new FileInputStream(filee)))
						.getPlagiarism();
				break;
			case "State Machine Diagram":
				plagarisms = statemachine.checkPlagiarism(
						new ModelFile(new FileInputStream(filee)))
						.getPlagiarism();
				break;

			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		HashMap<String,Double> submissionPlag=new HashMap<String,Double>();
		for (Plagiarism p : plagarisms) {
			logger.info(p.getFile1()+" "+p.getFile2()+" "+p.getPlagPersentage());
			if(submissionPlag.containsKey(p.getFile1().split("_")[1]))
			{
				if(submissionPlag.get(p.getFile1().split("_")[1])<p.getPlagPersentage())
						{
					submissionPlag.put(p.getFile1().split("_")[1], (double) p.getPlagPersentage());
						}
			}
			else
			{
				submissionPlag.put(p.getFile1().split("_")[1], (double) p.getPlagPersentage());
			}
			if(submissionPlag.containsKey(p.getFile2().split("_")[1]))
			{
				if(submissionPlag.get(p.getFile2().split("_")[1])<p.getPlagPersentage())
						{
					submissionPlag.put(p.getFile2().split("_")[1], (double) p.getPlagPersentage());
						}
			}
			else
			{
				submissionPlag.put(p.getFile2().split("_")[1], (double) p.getPlagPersentage());
			}
			
			User user1=new User();
			user1.setId(Integer.parseInt(p.getFile1().split("_")[0]));
			User user2=new User();
			user2.setId(Integer.parseInt(p.getFile2().split("_")[0]));
			Plagarism plagarism = new Plagarism(user1, user2,
					p.getPlagPersentage(), assignment);
			PlagarismService plagarismService = (PlagarismService) applicationContext
					.getBean(PlagarismService.class);
			plagarismService.save(plagarism);
			
		}
		
		AssignmentSubmissionService submissionService = (AssignmentSubmissionService) applicationContext
				.getBean(AssignmentSubmissionService.class);
		for (Entry<String, Double> entry : submissionPlag
				.entrySet()) {
			submissionService.updatePlagarism(Integer.parseInt(entry.getKey()), entry.getValue());
		}

	}
}
