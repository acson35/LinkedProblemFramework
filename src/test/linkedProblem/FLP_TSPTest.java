package test.linkedProblem;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import Example.FLP;
import Example.PFSP_TotalFlowTime;
import Example.TSP;
import main.solution.solution;
import main.solution.binarySolution.BinarySolution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.constraintHandling.ConstraintHandling;
import main.util.factory.GetProblemFactory;

public class FLP_TSPTest {

	@Test
	public void isSolutionofProblemNotNull() throws IOException {
		FLP flp = new FLP("resources/FLPInstances/cap71");
		BinarySolution sol = flp.createsolution();
		flp.evaluate(sol);
		TSP tsp = new TSP("resources/tspInstances/kroA100.tsp");
		GetProblemFactory fac =  new GetProblemFactory(tsp, sol);
		TSP prob2 = (TSP)fac.getProblemInstance();
		IntegerPermutationSolution sol2 = (IntegerPermutationSolution) prob2.createsolution();
		prob2.evaluate(sol2);
		System.out.println(sol.toString() + ConstraintHandling.isFeasible(sol));
		System.out.println(sol2.toString() + ConstraintHandling.isFeasible(sol2));
		assertNotNull(sol.variables());
	}

}
