package com.mbe.umlce.maper;

import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.expr.ObjectCreationExpr;
import japa.parser.ast.expr.VariableDeclarationExpr;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.BreakStmt;
import japa.parser.ast.stmt.ExpressionStmt;
import japa.parser.ast.stmt.ForStmt;
import japa.parser.ast.stmt.ForeachStmt;
import japa.parser.ast.stmt.IfStmt;
import japa.parser.ast.stmt.ReturnStmt;
import japa.parser.ast.stmt.Statement;
import japa.parser.ast.stmt.SwitchEntryStmt;
import japa.parser.ast.stmt.SwitchStmt;
import japa.parser.ast.stmt.SynchronizedStmt;
import japa.parser.ast.stmt.ThrowStmt;
import japa.parser.ast.stmt.TryStmt;
import japa.parser.ast.stmt.TypeDeclarationStmt;
import japa.parser.ast.stmt.WhileStmt;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.codehaus.jackson.map.ObjectMapper;

import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.SD;
import com.mbe.umlce.dataobject.classDiagram.ClassMethod;
import com.mbe.umlce.dataobject.classDiagram.ClassStructure;
import com.mbe.umlce.dataobject.result.MappingErrors;
import com.mbe.umlce.dataobject.result.Result;
import com.mbe.umlce.dataobject.sequenceDiagram.SequenceBehavior;
import com.mbe.umlce.dataobject.sequenceDiagram.SequenceCombinedFragment;
import com.mbe.umlce.dataobject.sequenceDiagram.SequenceMessage;
import com.mbe.umlce.reader.SequenceDiagramReader;

public class SequenceDiagramMaper {

	SequenceDiagramReader reader = new SequenceDiagramReader();
	private ArrayList<MappingErrors> results = new ArrayList<MappingErrors>();

	public static void main(String[] args) throws Exception {
		SequenceDiagramMaper diagramIdentifier = new SequenceDiagramMaper();
		FileInputStream fileInputStream = new FileInputStream(
				"C:\\Users\\Muhammad\\Desktop\\SequenceModels\\Blank Package.uml");

		FileInputStream fileInputStream2 = new FileInputStream(
				"C:\\Users\\Muhammad\\Desktop\\FYP\\sequenceMappingCode.zip");

		diagramIdentifier.mapSequenceToCode(new ModelFile(fileInputStream),
				new ModelFile(fileInputStream2));
	}

	public Result mapSequenceToCode(ModelFile model, ModelFile code)
			throws Exception {
		SD sd = reader.getRefModelDetails(model);
		ObjectMapper mapper = new ObjectMapper();
		// System.out.println(mapper.writeValueAsString(sd));
		ZipEntry entry;
		CompilationUnit cu;
		for (ClassStructure classStructure : sd.getClasses()) {
			System.out.println("Class Name: " + classStructure.getClassName()
					+ "\n");

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len;
			while ((len = code.getModel().read(buffer)) > -1) {
				baos.write(buffer, 0, len);
			}
			baos.flush();
			code.setModel(new BufferedInputStream(new ByteArrayInputStream(baos
					.toByteArray())));
			ZipInputStream directory = new ZipInputStream(
					new BufferedInputStream(new ByteArrayInputStream(
							baos.toByteArray())));

			boolean found = false;
			while ((entry = directory.getNextEntry()) != null) {
				if (!entry.isDirectory() && entry.getName().endsWith(".java")) {
					if (entry.getName()
							.substring(0, entry.getName().length() - 5)
							.equals(classStructure.getClassName())) {
						found = true;
						cu = JavaParser.parse(directory);
						for (ClassMethod classMethod : classStructure
								.getClassMethods()) {
							boolean methodFound = false;
							/** Code Methods */
							for (TypeDeclaration typeDec : cu.getTypes()) {
								List<BodyDeclaration> members = typeDec
										.getMembers();
								for (BodyDeclaration member : members) {

									if (member instanceof MethodDeclaration) {
										MethodDeclaration method = (MethodDeclaration) member;
										if (classMethod.getmethodName().equals(
												method.getName())) {
											methodFound = true;
											System.out
													.println("Code and UML Method Name: "
															+ method.getName()
															+ "\n");
											for (SequenceBehavior behavior : sd
													.getBehaviors()) {

												if (behavior
														.getStart()
														.getMessageName()
														.equals(method
																.getName())) {

													/** match behavior calls */
													for (SequenceMessage message : behavior
															.getCalls()) {
														addMethodMistake(
																classStructure,
																behavior,
																method, message);
													}

													/** match behvior fragments */
													for (SequenceCombinedFragment combinedFragment : behavior
															.getFragments()) {
														addFragmentMistake(
																classStructure,
																behavior,
																method,
																combinedFragment);
													}

												}

											}

											break;
										}
									}
								}
							}

							if (!methodFound) {
								results.add(new MappingErrors("Method ["
										+ classMethod.getmethodName()
										+ "] in Class ["
										+ classStructure.getClassName() + "]",
										"Method not Found in Code"));

								System.out.println("Method ["
										+ classMethod.getmethodName()
										+ "] in Class ["
										+ classStructure.getClassName() + "]"
										+ " Method not Found in Code");

							}
						}

						break;
					}
				}
			}

			if (!found) {
				results.add(new MappingErrors("Class ["
						+ classStructure.getClassName() + "]",
						"Class not Found"));
				System.out.println("Class [" + classStructure.getClassName()
						+ "]" + " Class not Found");
			}

			System.out
					.println("\n -------------- Class End Here -------------- \n");

		}

		Result result = new Result();
		result.setMappingErrors(results);
		System.out.println(mapper.writeValueAsString(results));
		return result;
	}

	public void addMethodMistake(ClassStructure classStructure,
			SequenceBehavior behavior, MethodDeclaration method,
			SequenceMessage message) {
		System.out.println("Call Method: " + message.getMessageName());

		BlockStmt blockStmt = method.getBody();
		boolean callFound = false;
		for (Statement statement : blockStmt.getStmts()) {
			if (matchMessage(statement, message)) {
				callFound = true;
				break;
			}
		}

		if (!callFound) {
			results.add(new MappingErrors("Method Call["
					+ message.getMessageName() + "] in Method ["
					+ behavior.getStart().getMessageName() + "]",
					" Method Call not Found In Code in Class ["
							+ classStructure.getClassName() + "]"));

			System.out.println("Method Call [" + message.getMessageName()
					+ "] in Method [" + behavior.getStart().getMessageName()
					+ "]" + " Method Call not Found In Code in Class ["
					+ classStructure.getClassName() + "]");

		}
		System.out.println("\n--- Call End ----\n");

	}

	public void addFragmentMistake(ClassStructure classStructure,
			SequenceBehavior behavior, MethodDeclaration method,
			SequenceCombinedFragment combinedFragment) {
		System.out.println("Fragment Opertion: "
				+ combinedFragment.getOperation());

		System.out.println("Fragment Condition: "
				+ combinedFragment.getCondition());

		BlockStmt blockStmt = method.getBody();
		boolean callFound = false;
		for (Statement statement : blockStmt.getStmts()) {

			Statement body = matchFragment(statement,
					combinedFragment.getOperation(),
					combinedFragment.getCondition());

			if (body != null) {
				callFound = true;
				break;
			}
		}
		if (callFound) {
			/** If Fragment Found then check call methods */
			for (SequenceMessage message : combinedFragment.getCalls()) {
				addMethodMistake(classStructure, behavior, method, message);
			}
		} else {
			results.add(new MappingErrors(combinedFragment.getOperation()
					+ " Fragment [" + combinedFragment.getCondition()
					+ "] in Method [" + behavior.getStart().getMessageName()
					+ "]", " Fragment not Found In Code in Class ["
					+ classStructure.getClassName() + "]"));

			System.out.println(combinedFragment.getOperation() + " Fragment ["
					+ combinedFragment.getCondition() + "] in Method ["
					+ behavior.getStart().getMessageName() + "]"
					+ " Fragment not Found In Code in Class ["
					+ classStructure.getClassName() + "]");
		}

	}

	public Boolean matchMessage(Statement statement, SequenceMessage message) {
		if (statement instanceof BreakStmt) {
			BreakStmt stmt = (BreakStmt) statement;
			stmt.getId();

		} else if (statement instanceof BlockStmt) {
			BlockStmt stmt = (BlockStmt) statement;
			for (Statement smt : stmt.getStmts()) {
				try {
					boolean result = matchMessage(smt, message);
					if (result) {
						return result;
					}
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
			}
		} else if (statement instanceof ExpressionStmt) {
			ExpressionStmt stmt = (ExpressionStmt) statement;

			// stmt.getExpression(); expression
			if (stmt.getExpression() instanceof MethodCallExpr) {
				MethodCallExpr callExpr = (MethodCallExpr) stmt.getExpression();
				if (callExpr.getName().equals(message.getMessageName())) {
					System.out.println("Expression Method call Match Method: "
							+ callExpr);
					return true;
				}
			} else if (stmt.getExpression() instanceof VariableDeclarationExpr) {
				VariableDeclarationExpr variableDeclarationExpr = (VariableDeclarationExpr) stmt
						.getExpression();

				for (VariableDeclarator expr : variableDeclarationExpr
						.getVars()) {

					if (expr.getInit() != null) {

						if (expr.getInit() instanceof ObjectCreationExpr) {
							ObjectCreationExpr creationExpr = (ObjectCreationExpr) expr
									.getInit();

							if (creationExpr.getType().getName()
									.equals(message.getMessageName())) {
								System.out
										.println("Expression VariableDeclarator ObjectCreation  Match Method: "
												+ creationExpr);
								return true;
							}

						} else if (expr.getInit() instanceof MethodCallExpr) {
							MethodCallExpr callExpr = (MethodCallExpr) expr
									.getInit();
							if (callExpr.getName().equals(
									message.getMessageName())) {
								System.out
										.println("Expression VariableDeclarator Method call Match Method: "
												+ callExpr);
								return true;
							}
						}
					}
				}

			}

		} else if (statement instanceof ForeachStmt) {
			ForeachStmt stmt = (ForeachStmt) statement;
			stmt.getIterable();
			stmt.getVariable();
			// System.out.println(stmt.getIterable());
			// System.out.println(stmt.getVariable());
			try {
				return matchMessage(stmt.getBody(), message);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		} else if (statement instanceof ForStmt) {
			ForStmt stmt = (ForStmt) statement;
			try {
				return matchMessage(stmt.getBody(), message);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}

		} else if (statement instanceof IfStmt) {
			IfStmt stmt = (IfStmt) statement;
			try {
				return matchMessage(stmt.getThenStmt(), message);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			try {
				return matchMessage(stmt.getElseStmt(), message);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}

		} else if (statement instanceof ReturnStmt) {
			ReturnStmt stmt = (ReturnStmt) statement;

			// stmt.getExpr() // expression
			if (stmt.getExpr() instanceof MethodCallExpr) {
				MethodCallExpr callExpr = (MethodCallExpr) stmt.getExpr();
				if (callExpr.getName().equals(message.getMessageName())) {
					System.out.println("Return Method Call Match Method: "
							+ callExpr);
					return true;
				}
			}

		} else if (statement instanceof WhileStmt) {
			WhileStmt stmt = (WhileStmt) statement;
			try {
				return matchMessage(stmt.getBody(), message);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}

		} else if (statement instanceof TryStmt) {
			TryStmt stmt = (TryStmt) statement;

			try {
				return matchMessage(stmt.getTryBlock(), message);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			try {
				return matchMessage(stmt.getFinallyBlock(), message);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}

			stmt.getCatchs();

		} else if (statement instanceof ThrowStmt) {
			ThrowStmt stmt = (ThrowStmt) statement;

			// stmt.getExpr() // expression
			if (stmt.getExpr() instanceof MethodCallExpr) {
				MethodCallExpr callExpr = (MethodCallExpr) stmt.getExpr();
				if (callExpr.getName().equals(message.getMessageName())) {
					System.out.println("Throw Method call " + callExpr);
					return true;
				}
			}

		} else if (statement instanceof TypeDeclarationStmt) {
			TypeDeclarationStmt stmt = (TypeDeclarationStmt) statement;
			stmt.getTypeDeclaration();

		} else if (statement instanceof SwitchEntryStmt) {
			SwitchEntryStmt stmt = (SwitchEntryStmt) statement;
			for (Statement smt : stmt.getStmts()) {
				try {
					return matchMessage(smt, message);
				} catch (NullPointerException e) {
					e.printStackTrace();
				}

			}

		} else if (statement instanceof SwitchStmt) {
			SwitchStmt stmt = (SwitchStmt) statement;
			stmt.getSelector(); // expression
			// System.out.println("Swithch Selector" + stmt.getSelector());

			for (Statement smt : stmt.getEntries()) {
				try {
					boolean result = matchMessage(smt, message);
					if (result) {
						return result;
					}
				} catch (NullPointerException e) {
					e.printStackTrace();
				}

			}

		} else if (statement instanceof SynchronizedStmt) {
			SynchronizedStmt stmt = (SynchronizedStmt) statement;

			// stmt.getExpr() // expression
			if (stmt.getExpr() instanceof MethodCallExpr) {
				MethodCallExpr callExpr = (MethodCallExpr) stmt.getExpr();
				if (callExpr.getName().equals(message.getMessageName())) {
					System.out
							.println("Synchronized Method call Match Method: "
									+ callExpr);
					return true;
				}
			}

			try {
				return matchMessage(stmt.getBlock(), message);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}

		}
		return false;
	}

	public Statement matchFragment(Statement statement, String type,
			String condition) {

		if (statement instanceof BlockStmt) {
			BlockStmt stmt = (BlockStmt) statement;
			for (Statement smt : stmt.getStmts()) {
				try {
					Statement stat = matchFragment(smt, type, condition);
					if (stat != null) {
						return stat;
					}
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
			}
	
		} else if (statement instanceof ForeachStmt) {
			ForeachStmt stmt = (ForeachStmt) statement;
			stmt.getIterable();
			stmt.getVariable();
			try {
				return matchFragment(stmt.getBody(), type, condition);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		} else if (statement instanceof ForStmt) {
			ForStmt stmt = (ForStmt) statement;
			
/*			if (type.equals("loop")
					&& (condition.equals(stmt.getCompare().toString()) || condition
							.replaceAll("\\s+", "").equals(
									stmt.getCompare().toString()
											.replaceAll("\\s+", "")))) {
*/			if (type.equals("loop")){
			return stmt.getBody();
			}
			try {
				return matchFragment(stmt.getBody(), type, condition);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}

		} else if (statement instanceof IfStmt) {
			IfStmt stmt = (IfStmt) statement;

			if (type.equals("alt")
					&& (condition.equals(stmt.getCondition().toString()) || condition
							.replaceAll("\\s+", "").equals(
									stmt.getCondition().toString()
											.replaceAll("\\s+", "")))) {
				return stmt.getThenStmt();
			}
			try {
				return matchFragment(stmt.getThenStmt(), type, condition);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			try {
				return matchFragment(stmt.getElseStmt(), type, condition);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}

		} else if (statement instanceof WhileStmt) {
			WhileStmt stmt = (WhileStmt) statement;
//			if (type.equals("loop")
//					&& (condition.equals(stmt.getCondition().toString()) || condition
//							.replaceAll("\\s+", "").equals(
//									stmt.getCondition().toString()
//											.replaceAll("\\s+", "")))) {
			if (type.equals("loop")){
				return stmt.getBody();
			}
			try {
				return matchFragment(stmt.getBody(), type, condition);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}

		} else if (statement instanceof TryStmt) {
			TryStmt stmt = (TryStmt) statement;

			try {
				return matchFragment(stmt.getTryBlock(), type, condition);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			try {
				return matchFragment(stmt.getFinallyBlock(), type, condition);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}

			stmt.getCatchs();

		} else if (statement instanceof TypeDeclarationStmt) {
			TypeDeclarationStmt stmt = (TypeDeclarationStmt) statement;
			stmt.getTypeDeclaration();

		} else if (statement instanceof SwitchEntryStmt) {
			SwitchEntryStmt stmt = (SwitchEntryStmt) statement;
			for (Statement smt : stmt.getStmts()) {
				try {
					return matchFragment(smt, type, condition);
				} catch (NullPointerException e) {
					e.printStackTrace();
				}

			}

		} else if (statement instanceof SwitchStmt) {
			SwitchStmt stmt = (SwitchStmt) statement;
			stmt.getSelector(); // expression
			// System.out.println("Swithch Selector" + stmt.getSelector());

			for (Statement smt : stmt.getEntries()) {
				try {
					Statement stat = matchFragment(smt, type, condition);
					if (stat != null) {
						return stat;
					}
				} catch (NullPointerException e) {
					e.printStackTrace();
				}

			}
		
		} else if (statement instanceof SynchronizedStmt) {
			SynchronizedStmt stmt = (SynchronizedStmt) statement;
			try {
				return matchFragment(stmt.getBlock(), type, condition);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
