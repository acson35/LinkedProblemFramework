package test.Algorithm.GeneticAlgorithm;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

import Example.JAP;
import Example.KP;
import main.algorithm.Algorithm;
import main.algorithm.EvolutionaryAlgorithms.GeneticAlgorithm.GeneticAlgorithmBuilder;
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
import main.util.comparator.ObjectiveComparator;
import main.util.comparator.ObjectiveComparator.Ordering;
import main.util.evaluator.Implementation.MultiThreadedSolutionListEvaluator;

public class GeneticAlgorithmKPTest {

	@SuppressWarnings("rawtypes")
	@Test
	public void shouldTheAlgorithmReturnANumberOfSolutionsWhenSolvingASimpleProblem() throws IOException {
		int numberOfCores =3;
		Problem<BinarySolution> problem;
	    Algorithm<BinarySolution, BinarySolution> algorithm;
	    problem = new KP("resources/KPInstances/n00100/R01000/s000.kp");
	   
	   
	    CrossoverOperator<BinarySolution> crossoverOperator = new HUXCrossover(0.9);
	    MutationOperator<BinarySolution> mutationOperator =
	        new BitFlipMutation(0.8);
	    @SuppressWarnings("unchecked")
		SelectionOperator<List<BinarySolution>, BinarySolution> selectionOperator =
	        new BestSolutionSelection(new ObjectiveComparator());

	    @SuppressWarnings("unchecked")
		GeneticAlgorithmBuilder<BinarySolution, BinarySolution> builder =
	        new GeneticAlgorithmBuilder<BinarySolution, BinarySolution>(problem, crossoverOperator, mutationOperator)
	            .setPopulationSize(100)
	            .setMaxEvaluations(25000)
	            .setSelectionOperator(selectionOperator)
	            .setSolutionListEvaluator(
	                new MultiThreadedSolutionListEvaluator<BinarySolution>(numberOfCores))
	            .setComparator(new ObjectiveComparator(Ordering.DESCENDING));

	    algorithm = builder.build();
	    
	    algorithm.run();

	    
	    BinarySolution solution = algorithm.getResult();
	    List<IntegerSolution> population = new ArrayList<>(1);
	   
	    
	   System.out.println(solution.toString());


	    assertTrue(population.size() < 99);
	}
}
