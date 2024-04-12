package test.problem;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;

import Example.OneMax;
import Example.OneMaxTSP;
import Example.TSP;
import main.problem.Problem;
import main.solution.solution;
import main.solution.binarySolution.BinarySolution;
import main.solution.holisticSolution.OverallSolution;
import main.util.factory.GetProblemFactory;

public class OneMaxTSPTest {

	@Test
	public void test() throws IOException {
		OneMaxTSP onemaxtsp = new OneMaxTSP();
		
		OneMax pro = (OneMax) onemaxtsp.getProblem(1);
		BinarySolution sol = pro.createsolution();
		pro.evaluate(sol);

		GetProblemFactory fac =  new GetProblemFactory(onemaxtsp.getProblem(2), sol);
		
		//TSP prob2 = new TSP ("resources/tspInstances/kroA100.tsp", sol);
		TSP prob2 = (TSP)fac.getProblemInstance();
		solution<?> sol2 = (solution<?>) prob2.createsolution();
		
		System.out.println(sol);
		System.out.println(sol2);
		assertNotNull(sol.variables());
	}

}
