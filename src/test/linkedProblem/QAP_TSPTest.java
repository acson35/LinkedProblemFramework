package test.linkedProblem;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import Example.PFSP_TotalFlowTime;
import Example.QAP;
import Example.TSP;
import main.solution.solution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.factory.GetProblemFactory;

public class QAP_TSPTest {

	@Test
	public void isSolutionofProblemNotNull() throws IOException {
		QAP qap = new QAP("resources/QAPInstances/tai50a.dat");
		solution<?> sol = qap.createsolution();
		qap.evaluate((IntegerPermutationSolution) sol);
		//System.out.println(sol.getData());
		System.out.println(sol.toString());
		TSP tsp = new TSP("resources/tspInstances/kroA100.tsp");
		
		
		GetProblemFactory fac =  new GetProblemFactory(tsp, sol);
		//System.out.println(sol.getData());
		TSP prob2 = (TSP)fac.getProblemInstance();
		
		
		IntegerPermutationSolution sol2 = (IntegerPermutationSolution) prob2.createsolution();
		prob2.evaluate(sol2);
		System.out.println(sol2.toString());
		assertNotNull(sol.variables());
	}

}
