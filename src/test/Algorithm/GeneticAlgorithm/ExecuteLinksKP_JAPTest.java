package test.Algorithm.GeneticAlgorithm;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Example.KP_JAP_Problems;
import Example.KP_PFSP_Problem;
import main.algorithm.Algorithm;
import main.algorithm.EvolutionaryAlgorithms.GeneticAlgorithm.GeneticAlgorithmBuilder;
import main.algorithm.GeneticAlgorithmVariant.GeneticAlgorithmVariant;
import main.operator.crossover.CrossoverOperator;
import main.operator.crossover.CrossoverClasses.HUXCrossover;
import main.operator.crossover.CrossoverClasses.IntegerSBXCrossover;
import main.operator.mutation.MutationOperator;
import main.operator.mutation.MutationClasses.BitFlipMutation;
import main.operator.mutation.MutationClasses.IntegerPolynomialMutation;
import main.operator.selection.SelectionOperator;
import main.operator.selection.selectionClasses.BestSolutionSelection;
import main.operator.selection.selectionClasses.TournamentSelection;
import main.problem.Problem;
import main.solution.binarySolution.BinarySolution;
import main.solution.integerSolution.IntegerSolution;
import main.util.LPDriver.ExecuteAlgorithms;
import main.util.LPDriver.LinkedProblemRunner;
import main.util.LPDriver.LinkedProblemRunnerBuilder;
import main.util.comparator.ObjectiveComparator;
import main.util.comparator.ObjectiveComparator.Ordering;
import main.util.evaluator.Implementation.MultiThreadedSolutionListEvaluator;

public class ExecuteLinksKP_JAPTest {

	@SuppressWarnings("rawtypes")
	@Test
	public void test() throws IOException {
		
		KP_JAP_Problems linkedproblem = new KP_JAP_Problems("resources/KPInstances/n00100/R01000/s000.kp", "resources/JAPInstances/a10100");
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
	    
	    
	    Algorithm<IntegerSolution, IntegerSolution> algorithm2;  
	    CrossoverOperator<IntegerSolution> crossoverOperator2 = new IntegerSBXCrossover(0.9, 20);
	    MutationOperator<IntegerSolution> mutationOperator2 =
	        new IntegerPolynomialMutation();
	    SelectionOperator<List<IntegerSolution>, IntegerSolution> selectionOperator2 =
	        new TournamentSelection<IntegerSolution>(new ObjectiveComparator(), 20);

	    GeneticAlgorithmBuilder<IntegerSolution, IntegerSolution> builder2 =
	        new GeneticAlgorithmBuilder<IntegerSolution, IntegerSolution>((Problem<IntegerSolution>) linkedproblem.getProblem(2), crossoverOperator2, mutationOperator2)
	            .setPopulationSize(10)
	            .setMaxEvaluations(500)
	            .setSelectionOperator(selectionOperator2)
	            .setVariant(GeneticAlgorithmVariant.STEADY_STATE);

	    algorithm2 = builder2.build();
	    
	    algorithmList.add(algorithm2);
		
	    LinkedProblemRunner experiment;
	    experiment = new LinkedProblemRunnerBuilder("KP_JAP_")
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
