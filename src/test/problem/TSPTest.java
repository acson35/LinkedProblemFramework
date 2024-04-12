package test.problem;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import Example.TSP;
import main.solution.permutationSolution.IntegerPermutationSolution;

public class TSPTest {

	@Test
	public void test() throws IOException {
		//TSP tsp = new TSP("resources/tspInstances/kroA100.tsp");
	}
	
	@Test
	public void isSolutionofProblemNotNull() throws IOException {
		TSP tsp = new TSP("resources/tspInstances/berlin52.tsp");
		IntegerPermutationSolution sol = tsp.createsolution();
		tsp.evaluate(sol);
		System.out.println(sol.toString());
		assertNotNull(sol.variables());
	}

}
