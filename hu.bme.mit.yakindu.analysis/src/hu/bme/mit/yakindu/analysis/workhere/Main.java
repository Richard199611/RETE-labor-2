package hu.bme.mit.yakindu.analysis.workhere;

import java.awt.print.PrinterAbortException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.stext.stext.EventDefinition;
import org.yakindu.sct.model.stext.stext.VariableDefinition;
import hu.bme.mit.model2gml.Model2GML;
import hu.bme.mit.yakindu.analysis.modelmanager.ModelManager;

public class Main {
	@Test
	public void test() {
		main(new String[0]);
	}
	
	public static void printVariables(List<String> variables, List<String> events) {
		System.out.println("package hu.bme.mit.yakindu.analysis.workhere;\r\n" + 
				"\r\n" + 
				"import java.io.IOException;\r\n" + 
				"import java.io.InputStreamReader;\r\n" + 
				"import java.io.BufferedReader;\r\n" + 
				"import hu.bme.mit.yakindu.analysis.RuntimeService;\r\n" + 
				"import hu.bme.mit.yakindu.analysis.TimerService;\r\n" + 
				"import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;\r\n" + 
				"import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"public class RunStatechart {\r\n" + 
				"\r\n" + 
				"public static void main(String[] args) throws IOException {\r\n" + 
				"ExampleStatemachine s = new ExampleStatemachine();\r\n" + 
				"s.setTimer(new TimerService());\r\n" + 
				"RuntimeService.getInstance().registerStatemachine(s, 200);\r\n" + 
				"s.init();\r\n" + 
				"s.enter();\r\n" + 
				"String read = new String(\"\");\r\n" + 
				"InputStreamReader streamReader = new InputStreamReader(System.in);\r\n" + 
				"BufferedReader buffer = new BufferedReader(streamReader);\r\n" + 
				"read = buffer.readLine();\r\n" + "while (read != \"exit\") {\r\n" + "switch(read) {");
				
		int current = 0;
		while(current<events.size()) {
			System.out.println("case \"" + events.get(current) + "\":\r\n" + 
					"s.raise" + events.get(current) + "();\r\n" + 
					"s.runCycle();\r\n" + 
					"print(s);\r\n" + 
					"read = buffer.readLine();\r\n" + 
					"break;");
			current++;
		}
		System.out.println("case \"exit\":\r\n" + 
				"print(s);\r\n" + 
				"System.exit(0);\r\n" + 
				"default:\r\n" + 
				"print(s);\r\n" + 
				"read = buffer.readLine();\r\n" + 
				"break;\r\n" + 
				"}\r\n" + 
				"}"+ "}");
		System.out.println("public static void print(IExampleStatemachine s){");
		current = 0;
		while(current < variables.size()) {
			System.out.println("System.out.println(\"" + variables.get(current).charAt(0) +
					" = \"" + "+ s.getSCInterface().get"+variables.get(current)+("());"));
			current++;
		}
		current = 0;
		while(current < events.size()) {
			System.out.println("System.out.println(\"" + events.get(current).charAt(0)+
					" =\"" + "s.getSCInterface().get"+events.get(current)+"())");
			current++;
		}
		System.out.println("}");
		System.out.println("}");
	}
	
	public static void main(String[] args) {
		ModelManager manager = new ModelManager();
		Model2GML model2gml = new Model2GML();
		List<String> varNames = new ArrayList<String>();
		List<String> eventNames = new ArrayList<String>();
		
		// Loading model
		EObject root = manager.loadModel("model_input/example.sct");
		
		// Reading model
		Statechart s = (Statechart) root;
		TreeIterator<EObject> iterator = s.eAllContents();
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof VariableDefinition) {
				VariableDefinition def = (VariableDefinition) content;
				//System.out.println(def.getName());
				varNames.add(def.getName());
			}
			if(content instanceof EventDefinition) {
				EventDefinition event = (EventDefinition) content;
				//System.out.println(event.getName());
				eventNames.add(event.getName());
			}
			if(content instanceof State) {
				
				//State state = (State) content;
				//int counter = 0;
				
				//System.out.println(state.getIncomingTransitions().get(0).getSpecification());
				/*System.out.println(state.getName());
				int counter = 0;
				if(state.getName() == null || state.getName() == "") {
					System.out.println("This state has no name, changed to suggested: Grey");
					state.setName("Grey");
				}
				while(counter < state.getOutgoingTransitions().size()) {
					System.out.println(state.getOutgoingTransitions().get(counter).getSource().getName() 
							+ "->" + state.getOutgoingTransitions().get(counter).getTarget().getName());
					counter++;
				}
				if (state.getOutgoingTransitions().size() == 0) {
					System.out.println("deadlock in:" + state.getName());
				}*/
			
			}
		}
		printVariables(varNames, eventNames);
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
		
		
		
	}
}
