package test.linkedProblem;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import Example.PFSP_Makespan;
import Example.PFSP_TotalFlowTime;
import Example.QAP;
import Example.TSP;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.factory.GetProblemFactory;

public class PFSP_TSP {

	@Test
	public void isSolutionofProblemNotNull() throws IOException {
		//PFSP_Makespan fsp = new PFSP_Makespan("resources/FSPInstances/tai50_10_0.fsp");
		PFSP_TotalFlowTime fsp = new PFSP_TotalFlowTime("resources/FSPInstances/tai50_10_0.fsp");
		IntegerPermutationSolution sol = fsp.createsolution();
		fsp.evaluate(sol);
		TSP tsp = new TSP("resources/tspInstances/kroA100.tsp");
		GetProblemFactory fac =  new GetProblemFactory(tsp, sol);
		TSP prob2 = (TSP)fac.getProblemInstance();
		
		IntegerPermutationSolution sol2 = (IntegerPermutationSolution) prob2.createsolution();
		prob2.evaluate(sol2);
		System.out.println(sol.toString());
		System.out.println(sol2.toString());
		assertNotNull(sol.variables());
	}
}
