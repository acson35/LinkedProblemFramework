package test.Algorithm.GeneticAlgorithm;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

import Example.JAP;
import main.algorithm.Algorithm;
import main.algorithm.EvolutionaryAlgorithms.GeneticAlgorithm.GeneticAlgorithmBuilder;
import main.algorithm.GeneticAlgorithmVariant.GeneticAlgorithmVariant;
import main.operator.crossover.CrossoverOperator;
import main.operator.crossover.CrossoverClasses.IntegerSBXCrossover;
import main.operator.crossover.CrossoverClasses.SinglePointCrossover;
import main.operator.mutation.MutationOperator;
import main.operator.mutation.MutationClasses.IntegerPolynomialMutation;
import main.operator.selection.SelectionOperator;
import main.operator.selection.selectionClasses.BestSolutionSelection;
import main.operator.selection.selectionClasses.TournamentSelection;
import main.problem.Problem;
import main.solution.integerSolution.IntegerSolution;
import main.util.comparator.ObjectiveComparator;
import main.util.evaluator.Implementation.MultiThreadedSolutionListEvaluator;

public class GeneticAlgorithmJAPTest {

	@Test
	public void shouldTheAlgorithmReturnANumberOfSolutionsWhenSolvingASimpleProblem() throws IOException {
		int numberOfCores =3;
		Problem<IntegerSolution> problem;
	    Algorithm<IntegerSolution, IntegerSolution> algorithm;
	    problem = new JAP("resources/JAPInstances/a10100");
	   
	   
	    CrossoverOperator<IntegerSolution> crossoverOperator = new IntegerSBXCrossover(0.9, 20);
	    MutationOperator<IntegerSolution> mutationOperator =
	        new IntegerPolynomialMutation();
	    SelectionOperator<List<IntegerSolution>, IntegerSolution> selectionOperator =
	        new TournamentSelection<IntegerSolution>(new ObjectiveComparator(), 20);

	    GeneticAlgorithmBuilder<IntegerSolution, IntegerSolution> builder =
	        new GeneticAlgorithmBuilder<IntegerSolution, IntegerSolution>(problem, crossoverOperator, mutationOperator)
	            .setPopulationSize(100)
	            .setMaxEvaluations(100000)
	            .setSelectionOperator(selectionOperator)
	            .setVariant(GeneticAlgorithmVariant.GENERATIONAL);

	    algorithm = builder.build();
	    
	    algorithm.run();

	    IntegerSolution solution = algorithm.getResult();
	    List<IntegerSolution> population = new ArrayList<>(1);
	   
	    
	   System.out.println(solution.toString());


	    assertTrue(population.size() < 99);
	}

}
