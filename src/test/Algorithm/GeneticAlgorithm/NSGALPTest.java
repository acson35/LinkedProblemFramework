package test.Algorithm.GeneticAlgorithm;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Example.FLP_PFSPproblems;
import main.algorithm.AlgorithmLP;
import main.algorithm.MultiObjectiveAlgorithm.NSGALP;
import main.linkedproblem.LinkedProblem;
import main.operator.crossover.CrossoverOperator;
import main.operator.crossover.CrossoverClasses.HUXCrossover;
import main.operator.crossover.CrossoverClasses.PMXCrossover;
import main.operator.mutation.MutationOperator;
import main.operator.mutation.MutationClasses.BitFlipMutation;
import main.operator.mutation.MutationClasses.PermutationSwapMutation;
import main.operator.selection.SelectionOperator;
import main.operator.selection.selectionClasses.PairRankingAndCrowdingSelection;
import main.operator.selection.selectionClasses.TournamentSelection;
import main.solution.solution;
import main.solution.solutionPair;
import main.solution.solutionSet;
import main.solution.binarySolution.BinarySolution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.comparator.DominancePairComparator;
import main.util.evaluator.SolutionListEvaluator;
import main.util.evaluator.Implementation.MultiThreadedSolutionListEvaluator;

public class NSGALPTest {

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	@Test
	public void test() throws IOException {
		
		int numberOfCores = 4;
	    CrossoverOperator<BinarySolution> crossoverOperator1 = 
	    		new HUXCrossover(0.9);
	    
	    MutationOperator<BinarySolution> mutationOperator1 =
	        new BitFlipMutation(0.8);
	    
	    CrossoverOperator<IntegerPermutationSolution> crossoverOperator2 = 
	    		new PMXCrossover(0.1);
	    MutationOperator<IntegerPermutationSolution> mutationOperator2 =
	        new PermutationSwapMutation(0.5);
	    
	    SelectionOperator<List<solutionPair>, solutionPair> selectionOperator = 
	    		new PairRankingAndCrowdingSelection(5, new DominancePairComparator<solutionSet>());
	    
	    SolutionListEvaluator<solution<?>> evaluator = 
	    		new MultiThreadedSolutionListEvaluator<solution<?>>(numberOfCores);
	    
		
		FLP_PFSPproblems linkedproblem = new FLP_PFSPproblems("resources/FLPInstances/cap101","resources/FSPInstances/tai50_10_0.fsp");
		List<AlgorithmLP> algorithmList = new ArrayList<>();
		
		List<CrossoverOperator> crossoverOperators = new ArrayList<>();
		
		crossoverOperators.add(crossoverOperator1);
		crossoverOperators.add(crossoverOperator2);
		
		List<MutationOperator> mutationOperators = new ArrayList<>();
		
		mutationOperators.add(mutationOperator1);
		mutationOperators.add(mutationOperator2);
        
		
		NSGALP flp_pfsp = new NSGALP(linkedproblem, 500, 100, 100, 100, crossoverOperators, mutationOperators, selectionOperator, evaluator);
		
     
		
		flp_pfsp.run();
		
		List<solutionSet> sol = flp_pfsp.getFrontPairs();
		
		for(int i=0; i<sol.size(); i++) {
			System.out.println(sol.get(i).getSolution(1) + " " + sol.get(i).getSolution(1).attributes().values());
			System.out.println(sol.get(i).getSolution(2));
			
			System.out.println();
		}
		System.out.println(sol.size());
		
		List<solutionSet> sol2 = flp_pfsp.getResult();
		System.out.println(sol2.get(0).getSolution(1) + " " + sol2.get(0).getSolution(1).attributes().values());
		System.out.println(sol2.get(0).getSolution(2));
		
		
	    assertNotNull(sol);
	}

}
