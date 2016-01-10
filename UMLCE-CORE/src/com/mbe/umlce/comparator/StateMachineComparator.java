package com.mbe.umlce.comparator;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.CallEvent;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.uml2.uml.Vertex;

import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.SM;
import com.mbe.umlce.dataobject.result.Plagiarism;
import com.mbe.umlce.dataobject.result.Result;
import com.mbe.umlce.dataobjects.stateMachine.Effect;
import com.mbe.umlce.dataobjects.stateMachine.TransitionDetails;
import com.mbe.umlce.uml.UMLModelLoader;

public class StateMachineComparator implements Serializable,Comparator {
	private static final long serialVersionUID = 1L;
	
	public Result checkPlagiarism(String[] files, byte[][] model)
			throws Exception {
		Result result = null;
		ArrayList<String> studentsName = new ArrayList<String>();
		ArrayList<ArrayList<SM>> studentModelDetails = new ArrayList<ArrayList<SM>>();
		if (model != null) {
			int i = 0;
			ArrayList<SM> details = new ArrayList<SM>();
			for (String name : files) {
				InputStream file = new BufferedInputStream(
						new ByteArrayInputStream(model[i]));
				details = getModelDetails(new ModelFile(file));
				studentModelDetails.add(details);
				studentsName.add(name);
			}
			result = plagiarism(studentModelDetails, studentsName);
		}
		return result;
	}
	
	
	
	public Result checkPlagiarism(ModelFile model) throws Exception {
		Result result = null;
		ArrayList<String> studentsName = new ArrayList<String>();
		ArrayList<ArrayList<SM>> studentModelDetails = new ArrayList<ArrayList<SM>>();

		if (model != null) {
			ZipInputStream directory = new ZipInputStream(
					new BufferedInputStream(model.getModel()));
			ZipEntry entry;

			// if (codeFiles != null) {
			// reading each file in specified directory
			while ((entry = directory.getNextEntry()) != null) {
				if (!entry.isDirectory()) {
					ArrayList<SM> details = new ArrayList<SM>();
					// make list of students model object details

					System.out.println(entry.getName());
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					byte[] buffer = new byte[1024];
					int len;
					while ((len = directory.read(buffer)) > -1) {
						baos.write(buffer, 0, len);
					}
					baos.flush();
					InputStream file = new BufferedInputStream(
							new ByteArrayInputStream(baos.toByteArray()));

					details = getModelDetails(new ModelFile(file));
					studentModelDetails.add(details);
					studentsName.add(entry.getName().replace(".uml", " ")
							.trim());
				}
			}

			result = plagiarism(studentModelDetails, studentsName);
		}
		return result;
	}

	
	
	
	
	
	
	
	
	
	
	
	private Result plagiarism(ArrayList<ArrayList<SM>> studentModelDetails,
			ArrayList<String> studentsName) {
		ArrayList<Plagiarism> plagReport = new ArrayList<Plagiarism>();

		int[] maxCounts = getMaxCount(studentModelDetails);
		/*
		 * Plagiarism checking is started.
		 */

		for (int i = 0; i < studentModelDetails.size(); i++) {
			/*
			 * Get first student's details
			 */
			ArrayList<SM> student_1 = studentModelDetails.get(i);
			/*
			 * Get second student's details and compare to check similarity
			 */
			for (int j = i + 1; j < studentModelDetails.size(); j++) {
				
				float totalPalgiarism = 0.0f;
				ArrayList<SM> student_2 = studentModelDetails.get(j);
				if (student_1.size() >= student_2.size()) {
					for (SM s1 : student_1) {
						for (SM s2 : student_2) {
							if (s1.getName().equalsIgnoreCase(
									s2.getName())) {
								totalPalgiarism += compareTransitionDetails(s1, s2,
										maxCounts);
						}
						}
					}
			
			}
				else {
					for (SM s2 : student_2) {
						for (SM s1 : student_1) {
							if (s2.getName().equalsIgnoreCase(
									s1.getName())) {
								totalPalgiarism += compareTransitionDetails(s1, s2,
										maxCounts);
							}
						}
					}
				}
				
				if (totalPalgiarism > 40) {
					if (totalPalgiarism > 100) {
						float difference = totalPalgiarism - 100;
						plagReport.add(new Plagiarism(studentsName.get(i),
								studentsName.get(j),
								(totalPalgiarism - difference)));
					} else
						plagReport.add(new Plagiarism(studentsName.get(i),
								studentsName.get(j), totalPalgiarism));
				}
		
			
		}
		}
		Result result = new Result();
		result.setPlagiarism(plagReport);
		return result;
	}
	
	private ArrayList<SM> getModelDetails(ModelFile modelFile) throws Exception {
		ArrayList<SM> stateMachineDetails = new ArrayList<>();
		UMLModelLoader umlModel = new UMLModelLoader();
		EList<PackageableElement> packageableElements;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		while ((len = modelFile.getModel().read(buffer)) > -1) {
			baos.write(buffer, 0, len);
		}
		baos.flush();
		modelFile.setModel(new BufferedInputStream(new ByteArrayInputStream(
				baos.toByteArray())));
		Model _model = umlModel.loadModel(new ModelFile(
				new BufferedInputStream(new ByteArrayInputStream(baos
						.toByteArray()))));

		if (_model == null) {
			Package _package = umlModel.loadPackage(modelFile);
			packageableElements = _package.getPackagedElements();
		} else {
			packageableElements = _model.getPackagedElements();
		}
		
		for (PackageableElement element : packageableElements) 
		{
			if (element.eClass() == UMLPackage.Literals.CLASS)
			{
				Class c = (Class) element;


				
				EList<Behavior> ownedBehaviors = c.getOwnedBehaviors();
				for (Behavior beh : ownedBehaviors) {
					if (beh.eClass() == UMLPackage.Literals.STATE_MACHINE){
				
				//System.out.println("Class Name : "+parentClassName);
			//if (element.eClass() == UMLPackage.Literals.STATE_MACHINE) {
				StateMachine stateMachine = (StateMachine) beh;

				//System.out.println("Parent  :  "+parentClassName);
			
				EList<Region> regions = stateMachine.getRegions();
				for (Region reg : regions) {
					//System.out.println("Region : " + reg.getLabel());
					EList<Vertex> vertices = reg.getSubvertices();

					for (Vertex vertex : vertices) {
						SM smDetails = new SM();
						if (vertex.eClass() == UMLPackage.Literals.STATE) {
					//		System.out.println("Vertex : "+vertex.getName());
							
							smDetails.setName(vertex.getLabel());
							ArrayList<TransitionDetails> transition = new ArrayList<>();
							transition = readVertices(vertex, smDetails);
							smDetails.setTransitions(transition);
							stateMachineDetails.add(smDetails);
							
						}
					}

				}
			

				}
			}
				}
		
		}
		
		return stateMachineDetails;
	}

		
	private ArrayList<TransitionDetails> readVertices(Vertex vertex, SM smDetails)
	{
		State state = (State) vertex;
		ArrayList<TransitionDetails> transition = new ArrayList<>();
		EList<Transition> outgoingTransitions = state.getOutgoings();
		for (Transition trans : outgoingTransitions) {
			
			if (state.eClass() != UMLPackage.Literals.PSEUDOSTATE) 
			{
				//System.out.println("Source : "+trans.getSource().getLabel());
				//System.out.println("Dest : "+trans.getTarget().getLabel());
				TransitionDetails temp = transitionDetails(trans);
				temp.setName(trans.getLabel());
				temp.setDest(trans.getTarget().getLabel());
				 transition.add(temp);
				
			}
		}
		return transition;
	}
private TransitionDetails transitionDetails(Transition trans)
{
	String condition = "";
	TransitionDetails transition = new TransitionDetails();
	
	///****** guard condition ***********************
			EList<Constraint> ownedRules = trans.getOwnedRules();
			com.mbe.umlce.dataobjects.stateMachine.Guard guard = new com.mbe.umlce.dataobjects.stateMachine.Guard();
			for (Constraint Rule : ownedRules) {
				
				ValueSpecification Specifications = Rule.getSpecification();
				guard.setName(Rule.getLabel());
				OpaqueExpression expr = (OpaqueExpression) Specifications;

				condition += expr.getBodies().toString();
				guard.setBody(removeSquareBrackets(condition));
				//condition = expr.getLanguages();
			//	System.out.println("Condition : "+condition);
			}
			transition.setGuard(guard);
			
			Effect effect = new Effect();
	String methodBody = null;
	if ((OpaqueBehavior) trans.getEffect() != null) {
		methodBody = "";
		effect.setName(trans.getEffect().getLabel());
		//methodBody += ("\nif ( "+removeSquareBrackets(condition)+" ){\n");
		methodBody += removeSquareBrackets(((OpaqueBehavior) trans.getEffect()).getBodies().toString());
		//System.out.println("Effect : "+methodBody);
		effect.setBody(methodBody);

	}
	
	
	transition.setEffect(effect);
	

	CallEvent callEvent=null;
	Operation operation = null;
			EList<Trigger> trigger = trans.getTriggers();
			com.mbe.umlce.dataobjects.stateMachine.Trigger trig = new com.mbe.umlce.dataobjects.stateMachine.Trigger();
			for (Trigger triger : trigger) {
				//System.out.println("Triger : "+triger.getQualifiedName());
				//if(triger.getEvent().getName().contains("CallEvent")){
					callEvent = (CallEvent) (triger.getEvent());
					operation = callEvent.getOperation();
					
					if(operation != null){

						//System.out.println("Operation : "+operation.getLabel());
						EList<Parameter> parameters = operation.getOwnedParameters();
						ArrayList<String> param =  new ArrayList<>();
						ArrayList<String> paramClass = new ArrayList<>();
						
						
						for (Parameter pm : parameters)
						{
							param.add(pm.getLabel());
							paramClass.add(pm.getClass().getName());
							//System.out.println("Parameters : "+pm.getLabel());
						}
						trig.setOpName(operation.getLabel());
						trig.setOpParameters(param);
						trig.setParametersClass(paramClass);
					}
					
				
			}
			transition.setTrigger(trig);
		
			return transition;
}


private String removeSquareBrackets(String myString){
	//		print("remove braces : "+myString);
	if(myString.equals(""))
		return myString;
	return myString.substring(1, myString.length()-1);
}

		
		
		
private int[] getMaxCount(ArrayList<ArrayList<SM>> studentModels) {
	int[] maxCount = new int[2];
	int stateCount = 1, transCount = 1;
	for (ArrayList<SM> detailList : studentModels) {
		if (detailList.size() > stateCount)
			stateCount = detailList.size();
		for (SM detail : detailList) {
			if (detail.getTransitions().size() > transCount)
				transCount = detail.getTransitions().size();
		}
	}
	maxCount[0] = stateCount;
	maxCount[1] = transCount;
	return maxCount;
}


private float compareTransitionDetails(SM s1, SM s2, int[] maxCounts) {
	float totalPalgiarism = 0.0f;
	ArrayList<TransitionDetails> transitions_s1 = s1.getTransitions();
	ArrayList<TransitionDetails> transitions_s2 = s2.getTransitions();
	
	float plagInState = (float) (50.0 / maxCounts[0]);
	float plagInTransition = (float) (50.0 / maxCounts[1]);
	
	totalPalgiarism += plagInState;
	
	
	/*
	 * compare class attributes
	 */
	if (transitions_s1.size() >= transitions_s2.size()) {
		for (TransitionDetails trans_1 : transitions_s1) {
			for (TransitionDetails trans_2 : transitions_s2) {
				if (trans_1 != null && trans_2 != null)
					if (trans_1.getName().equalsIgnoreCase(trans_2.getName())) {
						totalPalgiarism += compareGET(trans_1, trans_2, plagInTransition);
					}
			}
		}
	} else {
		for (TransitionDetails trans_2 : transitions_s2) {
			for (TransitionDetails trans_1 : transitions_s2) {
				if (trans_1 != null && trans_2 != null)
					if (trans_2.getName().equalsIgnoreCase(trans_1.getName())) {
						totalPalgiarism += compareGET(trans_1, trans_2, plagInTransition);
					}
			}
		}
	}
	
	
	return totalPalgiarism;
}

private float compareGET (TransitionDetails s1, TransitionDetails s2, float plagInTransitions)
{
	float plag = (float) (plagInTransitions / 3);
	float totalPlagTransition = 0.0f;

	if (s1.getEffect().getName() != null && s2.getEffect().getName() !=null &&
			s1.getEffect().getBody() != null && s2.getEffect().getBody() != null)
	{
	if (s1.getEffect().getName().equalsIgnoreCase(s2.getEffect().getName()))
	{
		if (s1.getEffect().getBody() != null && s2.getEffect().getBody() != null)
		{
		if (s1.getEffect().getBody().equalsIgnoreCase(s2.getEffect().getBody()))
		{
			totalPlagTransition += plag;
		}
		}
		else
			totalPlagTransition += plag;
	}
	}
	
	
	if (s1.getGuard().getName() != null && s2.getGuard().getName() != null &&
			s1.getGuard().getBody() != null && s2.getGuard().getBody() != null)
	{
	if (s1.getGuard().getName().equalsIgnoreCase(s2.getGuard().getName()))
	{
		if (s1.getGuard().getBody() != null && s2.getGuard().getBody() != null)
		{
			if (s1.getGuard().getBody().equalsIgnoreCase(s2.getGuard().getBody()))
				totalPlagTransition += plag;
		}
		else
		{totalPlagTransition += plag;}
	}
	
	}
	
	
	if (s1.getTrigger().getOpName() != null && s2.getTrigger().getOpName() != null)
	{
	if (s1.getTrigger().getOpName().equalsIgnoreCase(s2.getTrigger().getOpName()))
	{
		totalPlagTransition+= plag;
	}
	}
	
	return totalPlagTransition;
}


}
	

