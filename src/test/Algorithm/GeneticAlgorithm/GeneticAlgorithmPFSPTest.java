package test.Algorithm.GeneticAlgorithm;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Example.JAP;
import Example.PFSP_Makespan;
import Example.TSP;
import main.algorithm.Algorithm;
import main.algorithm.EvolutionaryAlgorithms.GeneticAlgorithm.GeneticAlgorithmBuilder;
import main.algorithm.GeneticAlgorithmVariant.GeneticAlgorithmVariant;
import main.operator.crossover.CrossoverOperator;
import main.operator.crossover.CrossoverClasses.IntegerSBXCrossover;
import main.operator.crossover.CrossoverClasses.PMXCrossover;
import main.operator.mutation.MutationOperator;
import main.operator.mutation.MutationClasses.PermutationSwapMutation;
import main.operator.selection.SelectionOperator;
import main.operator.selection.selectionClasses.TournamentSelection;
import main.problem.Problem;
import main.solution.integerSolution.IntegerSolution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.comparator.ObjectiveComparator;
import main.util.evaluator.Implementation.MultiThreadedSolutionListEvaluator;

public class GeneticAlgorithmPFSPTest {

	@Test
	public void shouldTheAlgorithmReturnANumberOfSolutionsWhenSolvingASimpleProblem() throws IOException {
		int numberOfCores =3;
		Problem<IntegerPermutationSolution> problem;
	    Algorithm<IntegerPermutationSolution,IntegerPermutationSolution> algorithm;
	    problem = new PFSP_Makespan("resources/FSPInstances/tai50_10_0.fsp");
	   
	   
	    CrossoverOperator<IntegerPermutationSolution> crossoverOperator = new PMXCrossover(0.1);
	    MutationOperator<IntegerPermutationSolution> mutationOperator =
	        new PermutationSwapMutation(0.8);
		SelectionOperator<List<IntegerPermutationSolution>, IntegerPermutationSolution> selectionOperator =
	        new TournamentSelection<IntegerPermutationSolution>(new ObjectiveComparator(), 20);

	    GeneticAlgorithmBuilder<IntegerPermutationSolution, IntegerPermutationSolution> builder =
	        new GeneticAlgorithmBuilder<IntegerPermutationSolution, IntegerPermutationSolution>(problem, crossoverOperator, mutationOperator)
	            .setPopulationSize(100)
	            .setMaxEvaluations(10000)
	            .setSelectionOperator(selectionOperator)
	            .setVariant(GeneticAlgorithmVariant.GENERATIONAL);
	    algorithm = builder.build();
	    
	    algorithm.run();
	    
	    IntegerPermutationSolution solution = algorithm.getResult();
	    List<IntegerSolution> population = new ArrayList<>(1);
	    
	    problem = new TSP("resources/tspInstances/kroA100.tsp");
	    
	    algorithm.setProblem(problem);
	    
	    algorithm.run();
	    
	   IntegerPermutationSolution solution2 = algorithm.getResult();
	    
	   System.out.println(solution.toString());
	   
	   System.out.println(solution2.toString());


	    assertTrue(population.size() < 99);

	}

}
