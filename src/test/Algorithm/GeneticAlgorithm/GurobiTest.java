package test.Algorithm.GeneticAlgorithm;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Example.IWSP;
import main.algorithm.Algorithm;
import main.algorithm.Gurobi.IwspGurobiAlgorithm;
import main.solution.solution;
import main.solution.binarySolution.BinarySolution;

public class GurobiTest {

	@SuppressWarnings("rawtypes")
	@Test
	public void test() throws IOException {
		
		IWSP problem = new IWSP("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/IWSP_VAP_MTSP/p50_10_10_0.6_1.txt");
		
		Algorithm<?, ?> algorithm = new IwspGurobiAlgorithm(problem);
		
		algorithm.run();
		
		solution<?> sol = (BinarySolution)algorithm.getResult();
		
		System.out.print(sol.toString());
	
		assertNotNull(sol);
	}

}
