package test.Algorithm.GeneticAlgorithm;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Example.FLP_PFSPproblems;
import Example.IWSP;
import Example.IWSP_VAP_MTSP_Problems;
import main.algorithm.AlgorithmLP;
import main.algorithm.AlgorithmLPs;
import main.algorithm.MultiCriteriaRankingAlgorithm.MCRGALP;
import main.algorithm.MultiCriteriaRankingAlgorithm.MCRGALPs;
import main.operator.crossover.CrossoverOperator;
import main.operator.crossover.CrossoverClasses.HUXCrossover;
import main.operator.crossover.CrossoverClasses.IntegerSBXCrossover;
import main.operator.crossover.CrossoverClasses.PMXCrossover;
import main.operator.mutation.MutationOperator;
import main.operator.mutation.MutationClasses.BitFlipMutation;
import main.operator.mutation.MutationClasses.IntegerPolynomialMutation;
import main.operator.mutation.MutationClasses.PermutationSwapMutation;
import main.operator.selection.SelectionOperator;
import main.operator.selection.selectionClasses.TopsisSelection;
import main.operator.selection.selectionClasses.TournamentSelection;
import main.solution.solution;
import main.solution.solutionPair;
import main.solution.solutionSet;
import main.solution.binarySolution.BinarySolution;
import main.solution.integerSolution.IntegerSolution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.comparator.ObjectiveComparator;
import main.util.evaluator.SolutionListEvaluator;
import main.util.evaluator.Implementation.MultiThreadedSolutionListEvaluator;
import main.util.evaluator.Implementation.SequentialSolutionListEvaluator;
import main.util.ranking.Criteria;

public class MCRGALP_IWSP_VAP_MTSP {

	@Test
	public void test () throws IOException {
		
		//int numberOfCores = 4;
		List<solutionSet> sol = new ArrayList<>();
		
			CrossoverOperator<BinarySolution> crossoverOperator1 = 
		    		new HUXCrossover(0.5);
		    
		    MutationOperator<BinarySolution> mutationOperator1 =
		        new BitFlipMutation(0.2);
		    
		    CrossoverOperator<IntegerSolution> crossoverOperator2 = new IntegerSBXCrossover(0.5, 25);
		    
		    MutationOperator<IntegerSolution> mutationOperator2 =
		        new IntegerPolynomialMutation();
		    
		    CrossoverOperator<IntegerPermutationSolution> crossoverOperator3 = 
		    		new PMXCrossover(0.5);
		    MutationOperator<IntegerPermutationSolution> mutationOperator3 =
		        new PermutationSwapMutation(0.2);
		    
		    SolutionListEvaluator<solution<?>> evaluator = 
		    		new SequentialSolutionListEvaluator<solution<?>>();
		    
			
			
			List<AlgorithmLPs> algorithmList = new ArrayList<>();
			
			List<CrossoverOperator> crossoverOperators = new ArrayList<>();
			
			crossoverOperators.add(0,crossoverOperator1);
			crossoverOperators.add(1,crossoverOperator2);
			crossoverOperators.add(2,crossoverOperator3);
			
			List<MutationOperator> mutationOperators = new ArrayList<>();
			
			mutationOperators.add(0,mutationOperator1);
			mutationOperators.add(1,mutationOperator2);
			mutationOperators.add(2,mutationOperator3);
	        
			
			List<Criteria> criteria = new ArrayList<>();
			
			criteria.add(new Criteria("iwspObj", 0.25, true));
			criteria.add(new Criteria("iwspConstraint1", 0.05, true));
			criteria.add(new Criteria("iwspConstraint2", 0.05, true));
			criteria.add(new Criteria("vapObj", 0.25, true));
			criteria.add(new Criteria("vapConstraint", 0.15, true));
			criteria.add(new Criteria("mtspObj", 0.25, true));
			
			SelectionOperator<List<solutionSet>, List<solutionSet>> selectionOperator = 
		    		new TopsisSelection<solutionSet>(10, criteria);
		    		
		    		
		  		
		    		
		  for(int c=0; c<4; c++) {
		    	
			IWSP_VAP_MTSP_Problems linkedproblem = new IWSP_VAP_MTSP_Problems("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/IWSP_VAP_MTSP/p50_10_10_0.6_1.txt");
				 
				
			MCRGALPs iwsp_vap_mtsp = new MCRGALPs(linkedproblem, 5000, 50, 50, 50, criteria, crossoverOperators, mutationOperators, selectionOperator, evaluator);
			
	     
			
			iwsp_vap_mtsp.run();
			
			sol = iwsp_vap_mtsp.getPopulation();
			
			for(int i=0; i<sol.size(); i++) {
				//System.out.println(sol.get(i).getParentSolution() + " " + sol.get(i).getParentSolution().attributes().values());
			//	System.out.println(sol.get(i).getSolutionSet().toString());
				
			//	System.out.println();
			}
			//System.out.println(sol.size());
			IWSP iwsp = (IWSP) iwsp_vap_mtsp.getProblem().getLinkedProblems().get(1);
			
			solutionSet sol2 = iwsp_vap_mtsp.getResult();
			
			System.out.println(sol2.getSolutionSet().get(1).toString()+ " " + sol2.getSolutionSet().get(1).attributes().values()+ " "+  iwsp.inventoryPolicy((BinarySolution) sol2.getSolutionSet().get(1)) + " "+ iwsp.totalFixedCost((BinarySolution) sol2.getSolutionSet().get(1))+ " "+ iwsp.totalTransportCost((BinarySolution) sol2.getSolutionSet().get(1)));
			System.out.println(sol2.getSolution(2));
			System.out.println(sol2.getSolution(3));
			
			iwsp.evaluate((BinarySolution) sol2.getSolutionSet().get(1));
			System.out.println(sol2.getSolutionSet().get(1).toString()+ " " + sol2.getSolutionSet().get(1).attributes().values()+ " "+  iwsp.inventoryPolicy((BinarySolution) sol2.getSolutionSet().get(1)) + " "+ iwsp.totalFixedCost((BinarySolution) sol2.getSolutionSet().get(1))+ " "+ iwsp.totalTransportCost((BinarySolution) sol2.getSolutionSet().get(1)));
			
		}
	    
		
		
		
	    assertNotNull(sol);
	}

}
