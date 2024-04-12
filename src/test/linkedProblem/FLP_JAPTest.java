package test.linkedProblem;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import Example.FLP;
import Example.JAP;
import Example.TSP;
import main.solution.binarySolution.BinarySolution;
import main.solution.integerSolution.IntegerSolution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.constraintHandling.ConstraintHandling;
import main.util.factory.GetProblemFactory;

public class FLP_JAPTest {

	@Test
	public void isSolutionofProblemNotNull() throws IOException {
		FLP flp = new FLP("resources/FLPInstances/p54");
		BinarySolution sol = flp.createsolution();
		flp.evaluate(sol);
		
	
		JAP jap = new JAP("resources/JAPInstances/a10100");
		GetProblemFactory fac =  new GetProblemFactory(jap, sol);
		
		JAP prob2 = (JAP)fac.getProblemInstance();
		IntegerSolution sol2 = (IntegerSolution) prob2.createsolution();
		IntegerSolution sol4 = (IntegerSolution) prob2.createsolution();
		prob2.evaluate(sol2);
		prob2.evaluate(sol4);
		
		TSP tsp = new TSP("resources/tspInstances/kroA100.tsp");
		GetProblemFactory fac1 =  new GetProblemFactory(tsp, sol2);
		
		TSP prob3 = (TSP)fac1.getProblemInstance();
		IntegerPermutationSolution sol3 = (IntegerPermutationSolution) prob3.createsolution();
		prob3.evaluate(sol3);
		
		System.out.println(sol.toString() + ConstraintHandling.isFeasible(sol));
		System.out.println(sol2.toString() + ConstraintHandling.isFeasible(sol2));
		//System.out.println(sol4.toString() + ConstraintHandling.isFeasible(sol4));
		System.out.println(sol3.toString() + ConstraintHandling.isFeasible(sol3));
		assertNotNull(sol.variables());
	}

}
