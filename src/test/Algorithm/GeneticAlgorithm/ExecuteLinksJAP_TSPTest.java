package test.Algorithm.GeneticAlgorithm;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Example.JAP;
import Example.JAP_TSP_Problems;
import Example.KP_TSP_Problems;
import main.algorithm.Algorithm;
import main.algorithm.EvolutionaryAlgorithms.GeneticAlgorithm.GeneticAlgorithmBuilder;
import main.algorithm.GeneticAlgorithmVariant.GeneticAlgorithmVariant;
import main.operator.crossover.CrossoverOperator;
import main.operator.crossover.CrossoverClasses.IntegerSBXCrossover;
import main.operator.crossover.CrossoverClasses.PMXCrossover;
import main.operator.mutation.MutationOperator;
import main.operator.mutation.MutationClasses.IntegerPolynomialMutation;
import main.operator.mutation.MutationClasses.PermutationSwapMutation;
import main.operator.selection.SelectionOperator;
import main.operator.selection.selectionClasses.TournamentSelection;
import main.problem.Problem;
import main.solution.integerSolution.IntegerSolution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.LPDriver.ExecuteAlgorithms;
import main.util.LPDriver.LinkedProblemRunner;
import main.util.LPDriver.LinkedProblemRunnerBuilder;
import main.util.comparator.ObjectiveComparator;

public class ExecuteLinksJAP_TSPTest {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void test() throws IOException {
		
		
		JAP_TSP_Problems linkedproblem = new JAP_TSP_Problems("resources/JAPInstances/a10100", "resources/tspInstances/kroA100.tsp");
		List<Algorithm<?, ?>> algorithmList = new ArrayList<>();
		
		int numberOfCores =4;
	    Algorithm<IntegerSolution, IntegerSolution> algorithm1;
	   
	   
	   
	    CrossoverOperator<IntegerSolution> crossoverOperator = new IntegerSBXCrossover(0.9, 20);
	    MutationOperator<IntegerSolution> mutationOperator =
	        new IntegerPolynomialMutation();
	    SelectionOperator<List<IntegerSolution>, IntegerSolution> selectionOperator =
	        new TournamentSelection<IntegerSolution>(new ObjectiveComparator(), 20);

	    GeneticAlgorithmBuilder<IntegerSolution, IntegerSolution> builder =
	        new GeneticAlgorithmBuilder<IntegerSolution, IntegerSolution>((Problem<IntegerSolution>) linkedproblem.getProblem(1), crossoverOperator, mutationOperator)
	            .setPopulationSize(10)
	            .setMaxEvaluations(500)
	            .setSelectionOperator(selectionOperator)
	            .setVariant(GeneticAlgorithmVariant.STEADY_STATE);

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
	            .setMaxEvaluations(50)
	            .setSelectionOperator(selectionOperator1)
	            .setVariant(GeneticAlgorithmVariant.STEADY_STATE);
	    algorithm2 = builder1.build();
	    algorithmList.add(algorithm2);
		
	    LinkedProblemRunner experiment;
	    experiment = new LinkedProblemRunnerBuilder("JAP_TSP_")
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
