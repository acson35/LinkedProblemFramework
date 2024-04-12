package test.Algorithm.GeneticAlgorithm;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Example.FLP;
import Example.JAP;
import Example.TSP;
import main.algorithm.Algorithm;
import main.algorithm.EvolutionaryAlgorithms.GeneticAlgorithm.GeneticAlgorithmBuilder;
import main.algorithm.GeneticAlgorithmVariant.GeneticAlgorithmVariant;
import main.operator.crossover.CrossoverOperator;
import main.operator.crossover.CrossoverClasses.HUXCrossover;
import main.operator.crossover.CrossoverClasses.IntegerSBXCrossover;
import main.operator.crossover.CrossoverClasses.PMXCrossover;
import main.operator.mutation.MutationOperator;
import main.operator.mutation.MutationClasses.BitFlipMutation;
import main.operator.mutation.MutationClasses.IntegerPolynomialMutation;
import main.operator.mutation.MutationClasses.PermutationSwapMutation;
import main.operator.selection.SelectionOperator;
import main.operator.selection.selectionClasses.TournamentSelection;
import main.problem.Problem;
import main.solution.binarySolution.BinarySolution;
import main.solution.integerSolution.IntegerSolution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.comparator.ObjectiveComparator;
import main.util.evaluator.Implementation.MultiThreadedSolutionListEvaluator;
import main.util.factory.GetProblemFactory;

public class GeneticAlgorithmFLP_JAPTest {

	@Test
	public void shouldTheAlgorithmReturnANumberOfSolutionsWhenSolvingASimpleProblem() throws IOException {
		int numberOfCores =3;
		Problem<BinarySolution> problem;
	    Algorithm<BinarySolution, BinarySolution> algorithm;
	    problem = new FLP("resources/FLPInstances/p54");
	   
	   
	    CrossoverOperator<BinarySolution> crossoverOperator = new HUXCrossover(0.9);
	    MutationOperator<BinarySolution> mutationOperator =
	        new BitFlipMutation(0.8);
	    SelectionOperator<List<BinarySolution>, BinarySolution> selectionOperator =
	        new TournamentSelection<BinarySolution>(new ObjectiveComparator(), 20);

	    GeneticAlgorithmBuilder<BinarySolution, BinarySolution> builder =
	        new GeneticAlgorithmBuilder<BinarySolution, BinarySolution>(problem, crossoverOperator, mutationOperator)
	            .setPopulationSize(100)
	            .setMaxEvaluations(100000)
	            .setSelectionOperator(selectionOperator)
	            .setSolutionListEvaluator(
	                new MultiThreadedSolutionListEvaluator<BinarySolution>(numberOfCores))
	            .setVariant(GeneticAlgorithmVariant.GENERATIONAL);

	    algorithm = builder.build();
	    
	    algorithm.run();

	    
	    BinarySolution solution = algorithm.getResult();
	   
		List<BinarySolution> pop = algorithm.getPopulation();
		
		
		
		Problem<IntegerSolution> problem1;
	    Algorithm<IntegerSolution, IntegerSolution> algorithm1;
	    
	    problem1 = new JAP("resources/JAPInstances/a10100");
	   
	   
	    GetProblemFactory fac =  new GetProblemFactory(problem1, solution);
		
		problem1 = (JAP)fac.getProblemInstance();
	    
	    CrossoverOperator<IntegerSolution> crossoverOperator1 = new IntegerSBXCrossover(0.9, 20);
	    MutationOperator<IntegerSolution> mutationOperator1 =
	        new IntegerPolynomialMutation();
	    SelectionOperator<List<IntegerSolution>, IntegerSolution> selectionOperator1 =
	        new TournamentSelection<IntegerSolution>(new ObjectiveComparator(), 20);

	    GeneticAlgorithmBuilder<IntegerSolution, IntegerSolution> builder1 =
	        new GeneticAlgorithmBuilder<IntegerSolution, IntegerSolution>(problem1, crossoverOperator1, mutationOperator1)
	            .setPopulationSize(100)
	            .setMaxEvaluations(100000)
	            .setSelectionOperator(selectionOperator1)
	            .setVariant(GeneticAlgorithmVariant.GENERATIONAL);

	    algorithm1 = builder1.build();
	    
	    algorithm1.run();

	    IntegerSolution solution1 = algorithm1.getResult();
	   // List<IntegerSolution> population = new ArrayList<>(1);
	    
	    Problem<IntegerPermutationSolution> problem3;
	    Algorithm<IntegerPermutationSolution, IntegerPermutationSolution> algorithm3;
	    problem3 = new TSP("resources/tspInstances/kroA100.tsp");
	     
	    GetProblemFactory fac2 =  new GetProblemFactory(problem3, solution1);
		
		problem3 = (TSP)fac2.getProblemInstance();
	    
	    CrossoverOperator<IntegerPermutationSolution> crossoverOperator3 = new PMXCrossover(0.1);
	    MutationOperator<IntegerPermutationSolution> mutationOperator3 =
	        new PermutationSwapMutation(0.5);
	
		SelectionOperator<List<IntegerPermutationSolution>, IntegerPermutationSolution> selectionOperator3 =
	        new TournamentSelection<IntegerPermutationSolution>(new ObjectiveComparator(), 20);

	    GeneticAlgorithmBuilder<IntegerPermutationSolution, IntegerPermutationSolution> builder3 =
	        new GeneticAlgorithmBuilder<IntegerPermutationSolution, IntegerPermutationSolution>(problem3, crossoverOperator3, mutationOperator3)
	            .setPopulationSize(100)
	            .setMaxEvaluations(100000)
	            .setSelectionOperator(selectionOperator3)
	            .setVariant(GeneticAlgorithmVariant.STEADY_STATE);
	    algorithm3 = builder3.build();
	   
	    algorithm3.run();
	    
	    IntegerPermutationSolution solution3 = algorithm3.getResult();  
	   
	    System.out.println(solution.toString());
		
		System.out.println(solution1.toString());
		
		//IntegerPermutationSolution solution3 = (IntegerPermutationSolution) problem3.createsolution();
		//problem3.evaluate(solution3);
		
		System.out.println(solution3.toString());

		//System.out.println(pop.get(1).toString());
		 
	    assertTrue(pop.size() == 100);
	}

}
