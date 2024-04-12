package test.linkedProblem;

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;

import Example.FLP;
import Example.PFSP_Makespan;
import Example.SFLP;
import Example.SFLP_SPFSP_Problems;
import Example.SPFSP;
import main.solution.binarySolution.BinarySolution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.constraintHandling.ConstraintHandling;
import main.util.factory.GetProblemFactory;

@SuppressWarnings("unused")
public class SFLP_SPFSP_Test {

	@SuppressWarnings("unused")
	@Test
	public void test() throws IOException {

		String file1 = "C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/InputData/SFLP/SFLP_10/SFLP_10_1000_1.txt";
		String file2 = "C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/InputData/SPFSP/SPFSP_10_15/SPFSP_10_15_1000_8.txt";
		SFLP_SPFSP_Problems linkedproblem = new SFLP_SPFSP_Problems(file1, file2);
		
		SFLP sflp = (SFLP) linkedproblem.getProblem(1);
		
		BinarySolution sol = sflp.createsolution();
		
		IntegerPermutationSolution sol1 = (IntegerPermutationSolution) linkedproblem.getProblem(2).createsolution();
		
		sflp.evaluate(sol);
		
		GetProblemFactory fac =  new GetProblemFactory(linkedproblem.getProblem(2), sol);
		
		SPFSP prob2 = (SPFSP)fac.getProblemInstance();
		
		prob2.evaluate(sol1);
		
		IntegerPermutationSolution sol2 = prob2.createsolution();
		
		IntegerPermutationSolution sol3 = prob2.createsolution();
		
		prob2.evaluate(sol2);
		
		prob2.evaluate(sol3);
		
		System.out.println(sol.toString() + ConstraintHandling.isFeasible(sol));
		
		System.out.println(sol1.toString() + ConstraintHandling.isFeasible(sol));
		
		System.out.println(sol2.toString() + ConstraintHandling.isFeasible(sol2));
		
		System.out.println(sol3.toString() + ConstraintHandling.isFeasible(sol2));
		
		assertNotNull(sol2.variables());
		
	}

}