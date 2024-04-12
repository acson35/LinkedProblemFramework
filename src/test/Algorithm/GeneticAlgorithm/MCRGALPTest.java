package test.Algorithm.GeneticAlgorithm;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Example.FLP_PFSPproblems;
import main.algorithm.AlgorithmLP;
import main.algorithm.MultiCriteriaRankingAlgorithm.MCRGALP;
import main.algorithm.MultiObjectiveAlgorithm.NSGALP;
import main.operator.crossover.CrossoverOperator;
import main.operator.crossover.CrossoverClasses.HUXCrossover;
import main.operator.crossover.CrossoverClasses.PMXCrossover;
import main.operator.mutation.MutationOperator;
import main.operator.mutation.MutationClasses.BitFlipMutation;
import main.operator.mutation.MutationClasses.PermutationSwapMutation;
import main.operator.selection.SelectionOperator;
import main.operator.selection.selectionClasses.PairRankingAndCrowdingSelection;
import main.operator.selection.selectionClasses.TopsisSelection;
import main.solution.solution;
import main.solution.solutionPair;
import main.solution.solutionSet;
import main.solution.binarySolution.BinarySolution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.comparator.DominancePairComparator;
import main.util.evaluator.SolutionListEvaluator;
import main.util.evaluator.Implementation.MultiThreadedSolutionListEvaluator;
import main.util.ranking.Criteria;

public class MCRGALPTest {

	@SuppressWarnings({ "unchecked", "rawtypes" })
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
        
		
		List<Criteria> criteria = new ArrayList<>();
		
		criteria.add(new Criteria("flpObj", 0.25, true));
		criteria.add(new Criteria("flpConstraint", 0.25, true));
		criteria.add(new Criteria("pfspObj", 0.25, true));
		criteria.add(new Criteria("pfspConstraint", 0.25, true));
		
		SelectionOperator<List<solutionSet>, List<solutionSet>> selectionOperator = 
	    		new TopsisSelection<solutionSet>(20, criteria);
		
		MCRGALP flp_pfsp = new MCRGALP(linkedproblem, 500, 100, 100, 100, criteria, crossoverOperators, mutationOperators, selectionOperator, evaluator);
		
     
		
		flp_pfsp.run();
		
		List<solutionPair> sol = flp_pfsp.getPopulation();
		
		for(int i=0; i<sol.size(); i++) {
			System.out.println(sol.get(i).getParentSolution() + " " + sol.get(i).getParentSolution().attributes().values());
			System.out.println(sol.get(i).getChildSolution());
			
			System.out.println();
		}
		System.out.println(sol.size());
		
		solutionPair sol2 = flp_pfsp.getResult();
		System.out.println(sol2.getParentSolution() + " " + sol2.getParentSolution().attributes().values());
		System.out.println(sol2.getChildSolution());
		
		
	    assertNotNull(sol);
	}

}
