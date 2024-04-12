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
import main.algorithm.MultiObjectiveAlgorithm.NSGALP;
import main.algorithm.MultiObjectiveAlgorithm.NSGALPs;
import main.operator.crossover.CrossoverOperator;
import main.operator.crossover.CrossoverClasses.HUXCrossover;
import main.operator.crossover.CrossoverClasses.IntegerSBXCrossover;
import main.operator.crossover.CrossoverClasses.PMXCrossover;
import main.operator.mutation.MutationOperator;
import main.operator.mutation.MutationClasses.BitFlipMutation;
import main.operator.mutation.MutationClasses.IntegerPolynomialMutation;
import main.operator.mutation.MutationClasses.PermutationSwapMutation;
import main.operator.selection.SelectionOperator;
import main.operator.selection.selectionClasses.BinaryTournamentSelection;
import main.operator.selection.selectionClasses.PairRankingAndCrowdingSelection;
import main.solution.solution;
import main.solution.solutionPair;
import main.solution.solutionSet;
import main.solution.binarySolution.BinarySolution;
import main.solution.integerSolution.IntegerSolution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.comparator.DominancePairComparator;
import main.util.comparator.RankingAndCrowdingDistanceComparator;
import main.util.evaluator.SolutionListEvaluator;
import main.util.evaluator.Implementation.MultiThreadedSolutionListEvaluator;
import main.util.evaluator.Implementation.SequentialSolutionListEvaluator;

public class NSGALP_IWSP_VAP_MTSP {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void test() throws IOException {
		
		//int numberOfCores = 4;
	    CrossoverOperator<BinarySolution> crossoverOperator1 = 
	    		new HUXCrossover(0.9);
	    
	    MutationOperator<BinarySolution> mutationOperator1 =
	        new BitFlipMutation(0.8);
	    
	    CrossoverOperator<IntegerSolution> crossoverOperator2 = new IntegerSBXCrossover(0.9, 20);
	    
	    MutationOperator<IntegerSolution> mutationOperator2 =
	        new IntegerPolynomialMutation();
	    
	    CrossoverOperator<IntegerPermutationSolution> crossoverOperator3 = 
	    		new PMXCrossover(0.8);
	    MutationOperator<IntegerPermutationSolution> mutationOperator3 =
	        new PermutationSwapMutation(0.5);
	    
	    @SuppressWarnings("rawtypes")
		SelectionOperator<List<solutionSet>, solutionSet> selectionOperator = 
	    		new BinaryTournamentSelection(new RankingAndCrowdingDistanceComparator<>());
		
	//	SelectionOperator<List<solutionSet>, solutionSet> selectionOperator = 
	 //   		new PairRankingAndCrowdingSelection(25, new DominancePairComparator<solutionSet>());
		
		
	    
	    SolutionListEvaluator<solution<?>> evaluator = 
	    		new SequentialSolutionListEvaluator<solution<?>>();
	    
		
	    IWSP_VAP_MTSP_Problems linkedproblem = new IWSP_VAP_MTSP_Problems("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/IWSP_VAP_MTSP/p100_10_10_0.6_1.txt");
		
		
		List<CrossoverOperator> crossoverOperators = new ArrayList<>();
		
		crossoverOperators.add(0,crossoverOperator1);
		crossoverOperators.add(1,crossoverOperator2);
		crossoverOperators.add(2,crossoverOperator3);
		
		List<MutationOperator> mutationOperators = new ArrayList<>();
		
		mutationOperators.add(0,mutationOperator1);
		mutationOperators.add(1,mutationOperator2);
		mutationOperators.add(2,mutationOperator3);
        
		NSGALPs iwsp_vap_mtsp = new NSGALPs(linkedproblem, 5000, 50, 50, 50, crossoverOperators, mutationOperators, selectionOperator, evaluator);
		
		iwsp_vap_mtsp.run();
		
		@SuppressWarnings("unchecked")
		//List<solutionSet> sol = iwsp_vap_mtsp.getFrontPairs();
		
		
		//System.out.println(sol.size());
		IWSP iwsp = (IWSP) iwsp_vap_mtsp.getProblem().getLinkedProblems().get(1);
		
		List<solutionSet> sol  =  iwsp_vap_mtsp.getResult();
		
		
		for(int i=0; i<sol.size(); i++) {
			
			System.out.println(sol.get(i).getSolutionSet().get(1).toString()+ " " + sol.get(i).getSolutionSet().get(1).attributes().values()+ " "+  iwsp.inventoryPolicy((BinarySolution) sol.get(i).getSolutionSet().get(1)) + " "+ iwsp.totalFixedCost((BinarySolution) sol.get(i).getSolutionSet().get(1))+ " "+ iwsp.totalTransportCost((BinarySolution) sol.get(i).getSolutionSet().get(1)));
			System.out.println(sol.get(i).getSolution(2));
			System.out.println(sol.get(i).getSolution(3));
			System.out.println();
		}
		
		
	    assertNotNull(sol);
	}

}
