package test.linkedProblem;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import Example.JAP;
import Example.KP;
import Example.TSP;
import main.solution.binarySolution.BinarySolution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.constraintHandling.ConstraintHandling;
import main.util.factory.GetProblemFactory;

public class KP_TSPTest {

	@Test
	public void isSolutionofProblemNotNull() throws IOException {
		KP kp = new KP("resources/KPInstances/n00100/R01000/s000.kp");
		BinarySolution sol = kp.createsolution();
		kp.evaluate(sol);
		TSP tsp = new TSP("resources/tspInstances/kroA100.tsp");
		GetProblemFactory fac =  new GetProblemFactory(tsp, sol);
		
		TSP tsp2  =(TSP) fac.getProblemInstance();
		IntegerPermutationSolution sol2 = tsp2.createsolution();
		tsp2.evaluate(sol2);
		System.out.println(sol.toString()+ ConstraintHandling.isFeasible(sol));
		System.out.println(sol2.toString()+ ConstraintHandling.isFeasible(sol2));
 		
 		
		assertNotNull(sol2.variables());
	}
}
