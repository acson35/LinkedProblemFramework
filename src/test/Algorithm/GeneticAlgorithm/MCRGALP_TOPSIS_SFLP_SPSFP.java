package test.Algorithm.GeneticAlgorithm;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Example.FLP_PFSPproblems;
import Example.SFLP_SPFSP_Problems;
import main.algorithm.AlgorithmLP;
import main.algorithm.MultiCriteriaRankingAlgorithm.MCRGALP;
import main.algorithm.MultiCriteriaRankingAlgorithm.MCRGALPs;
import main.operator.crossover.CrossoverOperator;
import main.operator.crossover.CrossoverClasses.HUXCrossover;
import main.operator.crossover.CrossoverClasses.PMXCrossover;
import main.operator.crossover.CrossoverClasses.UniformCrossover;
import main.operator.mutation.MutationOperator;
import main.operator.mutation.MutationClasses.BitFlipMutation;
import main.operator.mutation.MutationClasses.PermutationSwapMutation;
import main.operator.selection.SelectionOperator;
import main.operator.selection.selectionClasses.PrometheeIISelection;
import main.operator.selection.selectionClasses.TopsisSelection;
import main.solution.solution;
import main.solution.solutionPair;
import main.solution.solutionSet;
import main.solution.binarySolution.BinarySolution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.evaluator.SolutionListEvaluator;
import main.util.evaluator.Implementation.MultiThreadedSolutionListEvaluator;
import main.util.evaluator.Implementation.SequentialSolutionListEvaluator;
import main.util.ranking.Criteria;
import main.util.ranking.AHP.FitnessRanking;
import main.util.ranking.promethee.Promethee_II;
import main.util.ranking.topsis.TopsisIncompleteAlternativeDataException;

public class MCRGALP_TOPSIS_SFLP_SPSFP {

	@SuppressWarnings("unchecked")
	@Test
	public void test() throws IOException, TopsisIncompleteAlternativeDataException {

		
	    CrossoverOperator<BinarySolution> crossoverOperator1 = 
	    		new UniformCrossover(0.9);
	    		
	    		//UniformCrossover(0.9);
	    //HUXCrossover
	    MutationOperator<BinarySolution> mutationOperator1 =
	        new BitFlipMutation(0.8);
	    
	    CrossoverOperator<IntegerPermutationSolution> crossoverOperator2 = 
	    		new PMXCrossover(0.1);
	    MutationOperator<IntegerPermutationSolution> mutationOperator2 =
	        new PermutationSwapMutation(0.5);
	    
	    SolutionListEvaluator<solution<?>> evaluator = 
	    		new SequentialSolutionListEvaluator<solution<?>>();
	    
	    String file1 = "C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/InputData/SFLP/SFLP_10/SFLP_10_1000_10.txt";
		String file2 = "C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/InputData/SPFSP/SPFSP_10_10/SPFSP_10_10_1000_8.txt";
		
		SFLP_SPFSP_Problems linkedproblem = new SFLP_SPFSP_Problems(file1, file2);
		
		//List<AlgorithmLP> algorithmList = new ArrayList<>();
		
		List<CrossoverOperator> crossoverOperators = new ArrayList<>();
		
		crossoverOperators.add(crossoverOperator1);
		crossoverOperators.add(crossoverOperator2);
		
		List<MutationOperator> mutationOperators = new ArrayList<>();
		
		mutationOperators.add(mutationOperator1);
		mutationOperators.add(mutationOperator2);
        
		
		List<Criteria> criteria = new ArrayList<>();
		
		criteria.add(new Criteria("sflp_Obj_1", true));
		criteria.add(new Criteria("sflp_Obj_2", true));
		criteria.add(new Criteria("sflp_Constraint_1", true));
		criteria.add(new Criteria("sflp_Constraint_2", true));
		criteria.add(new Criteria("spfsp_Obj_1", true));
		criteria.add(new Criteria("spfsp_Obj_2", true));
		
		SelectionOperator<List<solutionSet>, List<solutionSet>> selectionOperator = 
	    		new PrometheeIISelection<solutionSet>(20, criteria);
		
		MCRGALPs sflp_spfsp = new MCRGALPs(linkedproblem, 500, 50, 50, 50, criteria, crossoverOperators, mutationOperators, selectionOperator, evaluator);
		
		//linkedproblem.createsolution();
		
		sflp_spfsp.run();
		
		List<solutionSet> sol = sflp_spfsp.getPopulation();
		
		//List<solutionSet> sol = sflp_spfsp.createInitialPopulation();
		
		//sol = sflp_spfsp.evaluatePopulation(sol);
		solutionSet sol2 = sflp_spfsp.getResult();
		
		System.out.println(sol2.getSolution(1).toString());
		System.out.println(sol2.getSolution(2));
		//FitnessRanking ftr = new FitnessRanking(sol, criteria);
		//ftr.optimalCriteriaWeightings();
		//Promethee_II pro = new Promethee_II(sol, criteria);
		
		
		//System.out.println(pro.calculateOptimalSolution().getSolution(1).attributes());
		//pro.calculateOptimalSolution();
		//pro.calculateOptimalSolutionSortedList();
		//sol = pro.calculateOptimalSolutionSortedList();
		for(int i=0; i<sol.size(); i++) {
			System.out.println(sol.get(i).getSolutionSet().get(1).attributes());
		}
		
	    assertNotNull(sol);
		
	}

}
