package test.Algorithm.GeneticAlgorithm;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Example.QAP;
import Example.TSP;
import main.algorithm.Algorithm;
import main.algorithm.EvolutionaryAlgorithms.GeneticAlgorithm.GeneticAlgorithmBuilder;
import main.operator.crossover.CrossoverOperator;
import main.operator.crossover.CrossoverClasses.PMXCrossover;
import main.operator.mutation.MutationOperator;
import main.operator.mutation.MutationClasses.PermutationSwapMutation;
import main.operator.selection.SelectionOperator;
import main.operator.selection.selectionClasses.TournamentSelection;
import main.problem.Problem;
import main.solution.integerSolution.IntegerSolution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.comparator.ObjectiveComparator;
import main.algorithm.GeneticAlgorithmVariant.GeneticAlgorithmVariant;

public class GeneticAlgorithmTSPTest {

	@Test
	public void shouldTheAlgorithmReturnANumberOfSolutionsWhenSolvingASimpleProblem() throws IOException {
		//int numberOfCores =3;
		Problem<IntegerPermutationSolution> problem;
	    Algorithm<IntegerPermutationSolution, IntegerPermutationSolution> algorithm;
	    problem = new TSP("resources/tspInstances/kroA100.tsp");
	   
	   
	    CrossoverOperator<IntegerPermutationSolution> crossoverOperator = new PMXCrossover(0.1);
	    MutationOperator<IntegerPermutationSolution> mutationOperator =
	        new PermutationSwapMutation(0.5);
	
		SelectionOperator<List<IntegerPermutationSolution>, IntegerPermutationSolution> selectionOperator =
	        new TournamentSelection<IntegerPermutationSolution>(new ObjectiveComparator(), 20);

	    GeneticAlgorithmBuilder<IntegerPermutationSolution, IntegerPermutationSolution> builder =
	        new GeneticAlgorithmBuilder<IntegerPermutationSolution, IntegerPermutationSolution>(problem, crossoverOperator, mutationOperator)
	            .setPopulationSize(100)
	            .setMaxEvaluations(25000)
	            .setSelectionOperator(selectionOperator)
	            .setVariant(GeneticAlgorithmVariant.STEADY_STATE);
	    algorithm = builder.build();
	   
	    algorithm.run();
	    
	    IntegerPermutationSolution solution = algorithm.getResult();
	    List<IntegerSolution> population = new ArrayList<>(1);
	    
	   System.out.println(solution.toString());


	    assertTrue(population.size() < 99);

	}

}
