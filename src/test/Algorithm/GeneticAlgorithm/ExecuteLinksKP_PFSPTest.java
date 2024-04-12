package test.Algorithm.GeneticAlgorithm;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Example.FLP_PFSPproblems;
import Example.KP_PFSP_Problem;
import main.algorithm.Algorithm;
import main.algorithm.EvolutionaryAlgorithms.GeneticAlgorithm.GeneticAlgorithmBuilder;
import main.algorithm.GeneticAlgorithmVariant.GeneticAlgorithmVariant;
import main.operator.crossover.CrossoverOperator;
import main.operator.crossover.CrossoverClasses.HUXCrossover;
import main.operator.crossover.CrossoverClasses.PMXCrossover;
import main.operator.mutation.MutationOperator;
import main.operator.mutation.MutationClasses.BitFlipMutation;
import main.operator.mutation.MutationClasses.PermutationSwapMutation;
import main.operator.selection.SelectionOperator;
import main.operator.selection.selectionClasses.BestSolutionSelection;
import main.operator.selection.selectionClasses.TournamentSelection;
import main.problem.Problem;
import main.solution.binarySolution.BinarySolution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.LPDriver.ExecuteAlgorithms;
import main.util.LPDriver.LinkedProblemRunner;
import main.util.LPDriver.LinkedProblemRunnerBuilder;
import main.util.comparator.ObjectiveComparator;
import main.util.comparator.ObjectiveComparator.Ordering;
import main.util.evaluator.Implementation.MultiThreadedSolutionListEvaluator;

public class ExecuteLinksKP_PFSPTest {

	@SuppressWarnings({ "unused", "rawtypes" })
	@Test
	public void test() throws IOException {
		
		KP_PFSP_Problem linkedproblem = new KP_PFSP_Problem("resources/KPInstances/n00100/R01000/s000.kp", "resources/FSPInstances/tai100_10_0.fsp");
		List<Algorithm<?, ?>> algorithmList = new ArrayList<>();
	
		int numberOfCores = 4;
		Algorithm<BinarySolution, BinarySolution> algorithm1;
		CrossoverOperator<BinarySolution> crossoverOperator = new HUXCrossover(0.9);
	    MutationOperator<BinarySolution> mutationOperator =
	        new BitFlipMutation(0.8);
	    @SuppressWarnings("unchecked")
		SelectionOperator<List<BinarySolution>, BinarySolution> selectionOperator =
	        new BestSolutionSelection(new ObjectiveComparator());

	    @SuppressWarnings("unchecked")
		GeneticAlgorithmBuilder<BinarySolution, BinarySolution> builder =
	        new GeneticAlgorithmBuilder<BinarySolution, BinarySolution>((Problem<BinarySolution>) linkedproblem.getProblem(1), crossoverOperator, mutationOperator)
	            .setPopulationSize(10)
	            .setMaxEvaluations(5)
	            .setSelectionOperator(selectionOperator)
	            .setSolutionListEvaluator(
	                new MultiThreadedSolutionListEvaluator<BinarySolution>(numberOfCores))
	            .setComparator(new ObjectiveComparator(Ordering.DESCENDING));

	    algorithm1 = builder.build();
	    algorithmList.add(algorithm1);
	    
	    Algorithm<IntegerPermutationSolution, IntegerPermutationSolution> algorithm2;  
		   
	    CrossoverOperator<IntegerPermutationSolution> crossoverOperator1 = new PMXCrossover(0.1);
	    MutationOperator<IntegerPermutationSolution> mutationOperator1 =
	        new PermutationSwapMutation(0.5);
		SelectionOperator<List<IntegerPermutationSolution>, IntegerPermutationSolution> selectionOperator1 =
	        new TournamentSelection<IntegerPermutationSolution>(new ObjectiveComparator(), 20);

	    GeneticAlgorithmBuilder<IntegerPermutationSolution, IntegerPermutationSolution> builder1 =
	        new GeneticAlgorithmBuilder<IntegerPermutationSolution, IntegerPermutationSolution>((Problem<IntegerPermutationSolution>) linkedproblem.getProblem(2), crossoverOperator1, mutationOperator1)
	            .setPopulationSize(10)
	            .setMaxEvaluations(5)
	            .setSelectionOperator(selectionOperator1)
	            .setVariant(GeneticAlgorithmVariant.STEADY_STATE);
	    algorithm2 = builder1.build();
	    algorithmList.add(algorithm2);
		
	    LinkedProblemRunner experiment;
	    experiment = new LinkedProblemRunnerBuilder("KP_PFSP_")
	        .setAlgorithmList(algorithmList)
	        .setProblemList(linkedproblem)
	        .setOutputLinkedProblemFileName("Results")
	        .setIndependentRuns(1000)
	        .build();
		
	    ExecuteAlgorithms execute = new ExecuteAlgorithms(experiment);
	    execute.run();
		
		
		
		
		
		fail("Not yet implemented");
	}

}
