package com.mbe.umlce.reader;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;



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
import com.mbe.umlce.dataobjects.stateMachine.*;
import com.mbe.umlce.uml.UMLModelLoader;



public class StateMachineReader {


	public  ArrayList<SM> getRefModelDetails(ModelFile modelFile) throws Exception
	{
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
			
//			EList<Behavior> ownedBehaviors = c.getOwnedBehaviors();
//			for (Behavior beh : ownedBehaviors) {
//				if (beh.eClass() == UMLPackage.Literals.STATE_MACHINE){
//					System.out.println(beh.getName());
//					readBehaviours(beh);
				}
			}
				}
		
		}
		
		return stateMachineDetails;
	}
		
	
	
	

	
	
	public static ArrayList<TransitionDetails> readVertices(Vertex vertex, SM smDetails)
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
public static TransitionDetails transitionDetails(Transition trans)
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
	
	//Trigger yet to read//
	
	//
	transition.setEffect(effect);
	/*		TimeEvent timeEvent=null;
			ChangeEvent changeEvent = null;
	*/
	//Triggers reading
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
			//}
			return transition;
}


public static String removeSquareBrackets(String myString){
	//		print("remove braces : "+myString);
	if(myString.equals(""))
		return myString;
	return myString.substring(1, myString.length()-1);
}

public static void printStateMachine(ArrayList<SM> stateMachineDetails)
{
	for (SM details : stateMachineDetails)
	{
		System.out.println("State Name : "+ details.getName());
		ArrayList<TransitionDetails> trDetails = details.getTransitions();
		for (TransitionDetails trDetail : trDetails )
		{
			
			System.out.println("Transition Name : "+ trDetail.getName());
			System.out.println ("Dest : "+trDetail.getDest());
			System.out.println("Effect name : "+trDetail.getEffect().getName());
			System.out.println("Effect body : "+trDetail.getEffect().getBody());
			System.out.println("Guard name : "+trDetail.getGuard().getName());
			System.out.println("Guard body : "+trDetail.getGuard().getBody());
			
			System.out.println("Trigger Name : "+trDetail.getTrigger().getOpName());
		}
	}
}

}
