package hu.bme.mit.yakindu.analysis.workhere;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import hu.bme.mit.yakindu.analysis.RuntimeService;
import hu.bme.mit.yakindu.analysis.TimerService;
import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;
import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;



public class RunStatechart {
	
	public static void main(String[] args) throws IOException {
		ExampleStatemachine s = new ExampleStatemachine();
		s.setTimer(new TimerService());
		RuntimeService.getInstance().registerStatemachine(s, 200);
		s.init();
		s.enter();
		String read = new String("");
		InputStreamReader streamReader = new InputStreamReader(System.in);
		BufferedReader buffer = new BufferedReader(streamReader);
		read = buffer.readLine();
		while (read != "exit") {
			switch(read) {
			case "start":
				s.raiseStart();
				s.runCycle();
				print(s);
				read = buffer.readLine();
				break;
			case "black":
				s.raiseBlack();
				s.runCycle();
				print(s);
				read = buffer.readLine();
				break;
			case "white":
				s.raiseWhite();
				s.runCycle();
				print(s);
				read = buffer.readLine();
				break;
			case "exit":
				print(s);
				System.exit(0);
			default:
				print(s);
				read = buffer.readLine();
				break;
			}
		}
		/*s.init();
		s.enter();
		s.runCycle();
		print(s);
		s.raiseStart();
		s.runCycle();
		System.in.read();
		s.raiseWhite();
		s.runCycle();*/
		//print(s);
		//System.exit(0);
	}

	public static void print(IExampleStatemachine s) {
		System.out.println("W = " + s.getSCInterface().getWhiteTime());
		System.out.println("B = " + s.getSCInterface().getBlackTime());
	}
}
