package test.linkedProblem;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import Example.FLP;
import Example.FLP_PFSPproblems;
import Example.PFSP_Makespan;
import Example.PFSP_TotalFlowTime;
import main.solution.solution;
import main.solution.binarySolution.BinarySolution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.constraintHandling.ConstraintHandling;
import main.util.factory.GetProblemFactory;

public class FLP_PFSPTest {

	@Test
	public void isSolutionofProblemNotNull() throws IOException {
		FLP flp = new FLP("resources/FLPInstances/cap64");
		BinarySolution sol = flp.createsolution();
		flp.evaluate(sol);
		//PFSP_Makespan fsp = new PFSP_Makespan("resources/FSPInstances/tai50_10_0.fsp");
		PFSP_TotalFlowTime fsp = new PFSP_TotalFlowTime("resources/FSPInstances/tai50_5_0.fsp");
		GetProblemFactory fac =  new GetProblemFactory(fsp, sol);
		PFSP_TotalFlowTime prob2 = (PFSP_TotalFlowTime)fac.getProblemInstance();
		solution<?> sol2 = (solution<?>) prob2.createsolution();
	//	System.out.println(sol.toString() + ConstraintHandling.isFeasible(sol));
	//	System.out.println(sol2.toString() + ConstraintHandling.isFeasible(sol));
		assertNotNull(sol.variables());
	}
	
	@Test
	public void islinkedProblemNotNull() throws IOException {
		
		FLP_PFSPproblems flp_pfsp = new FLP_PFSPproblems("resources/FLPInstances/cap101","resources/FSPInstances/tai50_10_0.fsp");
		
		FLP flp = (FLP) flp_pfsp.getProblem(1);
		
		BinarySolution sol = flp.createsolution();
		
		IntegerPermutationSolution sol1 = (IntegerPermutationSolution) flp_pfsp.getProblem(2).createsolution();
		
		flp.evaluate(sol);
		
		GetProblemFactory fac =  new GetProblemFactory(flp_pfsp.getProblem(2), sol);
		
		PFSP_Makespan prob2 = (PFSP_Makespan)fac.getProblemInstance();
		
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
