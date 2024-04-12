package test.Algorithm.GeneticAlgorithm;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Example.SFLP_SPFSP_Problems;
import main.algorithm.MultiObjectiveAlgorithm.NSGALPIII;
import main.algorithm.MultiObjectiveAlgorithm.NSGALPIIIbuilder;
import main.linkedproblem.LinkedProblem;
import main.operator.crossover.CrossoverOperator;
import main.operator.crossover.CrossoverClasses.PMXCrossover;
import main.operator.crossover.CrossoverClasses.UniformCrossover;
import main.operator.mutation.MutationOperator;
import main.operator.mutation.MutationClasses.BitFlipMutation;
import main.operator.mutation.MutationClasses.PermutationSwapMutation;
import main.operator.selection.SelectionOperator;
import main.operator.selection.selectionClasses.BinaryTournamentSelection;
import main.solution.solution;
import main.solution.solutionSet;
import main.solution.binarySolution.BinarySolution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.comparator.RankingAndCrowdingDistanceComparator;
import main.util.evaluator.SolutionListEvaluator;
import main.util.evaluator.Implementation.SequentialSolutionListEvaluator;

public class NSGALPIII_SFLP_SPFSP {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void test() throws IOException {
		
		
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
			    
		 @SuppressWarnings("rawtypes")
		 SelectionOperator<List<solutionSet>, solutionSet> selectionOperator = 
		    		new BinaryTournamentSelection(new RankingAndCrowdingDistanceComparator<>());
			
		 String file1 = "C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/InputData/SFLP/SFLP_10/SFLP_10_200_2.txt";
		 String file2 = "C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/InputData/SPFSP/SPFSP_10_10/SPFSP_10_10_200_1.txt";
			
		 SolutionListEvaluator<solution<?>> evaluator = 
			    		new SequentialSolutionListEvaluator<solution<?>>();
			    
					
		 SFLP_SPFSP_Problems linkedproblem = new SFLP_SPFSP_Problems(file1, file2);
		 
		 List<CrossoverOperator> crossoverOperators = new ArrayList<>();
			
		 crossoverOperators.add(0,crossoverOperator1);
		 crossoverOperators.add(1,crossoverOperator2);
				
		 List<MutationOperator> mutationOperators = new ArrayList<>();
				
		 mutationOperators.add(0,mutationOperator1);
		 mutationOperators.add(1,mutationOperator2);
				
	//	 NSGALPIIIbuilder builder = new NSGALPIIIbuilder(linkedproblem);
				
		// builder.setCrossoverOperator(crossoverOperators);
		 
		// builder.setMutationOperator(mutationOperators);
		 
		// builder.setMaxIterations(500);
		 
		// builder.setPopulationSize(50);
		 
		// builder.setSolutionListEvaluator(evaluator);
		 
		// builder.setSelectionOperator(selectionOperator);
		 
		// builder.setNumberOfDivisions(0);
		 
		 NSGALPIII algorithm =
			        new NSGALPIIIbuilder(linkedproblem)
			            .setCrossoverOperator(crossoverOperators)
			            .setMutationOperator(mutationOperators)
			            .setSelectionOperator(selectionOperator)
			            .setMaxIterations(300)
			         //   .setPopulationSize(50)
			            .setNumberOfDivisions(5)
			            .build();
		 
		// NSGALPIII nsgaIII = new NSGALPIII(builder);
		algorithm.run();
		algorithm.getResult();
		
		List<solutionSet> sol  =  algorithm.getResult();
		
		
		for(int i=0; i<sol.size(); i++) {
			
			System.out.println(sol.get(i).getSolutionSet().get(1).toString()+ " " + sol.get(i).getSolutionSet().get(1).attributes().values());
			System.out.println(sol.get(i).getSolution(2));
			System.out.println();
		}
		
		
	}

}
