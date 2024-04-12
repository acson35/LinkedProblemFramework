package test.Algorithm.GeneticAlgorithm;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Example.FLP;
import main.algorithm.Algorithm;
import main.algorithm.EvolutionaryAlgorithms.GeneticAlgorithm.GeneticAlgorithmBuilder;
import main.algorithm.GeneticAlgorithmVariant.GeneticAlgorithmVariant;
import main.operator.crossover.CrossoverOperator;
import main.operator.crossover.CrossoverClasses.HUXCrossover;
import main.operator.crossover.CrossoverClasses.UniformCrossover;
import main.operator.mutation.MutationOperator;
import main.operator.mutation.MutationClasses.BitFlipMutation;
import main.operator.selection.SelectionOperator;
import main.operator.selection.selectionClasses.TournamentSelection;
import main.problem.Problem;
import main.solution.binarySolution.BinarySolution;
import main.util.comparator.ObjectiveComparator;
import main.util.evaluator.Implementation.MultiThreadedSolutionListEvaluator;

public class GeneticAlgorithmFLPTest {

	@Test
	public void shouldTheAlgorithmReturnANumberOfSolutionsWhenSolvingASimpleProblem() throws IOException {
		int numberOfCores =3;
		Problem<BinarySolution> problem;
	    Algorithm<BinarySolution, BinarySolution> algorithm;
	    problem = new FLP("resources/FLPInstances/cap71");
	   
	   
	    CrossoverOperator<BinarySolution> crossoverOperator = new UniformCrossover(0.9);
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
		
		//for(int i=0; i<pop.size();i++) {
			//System.out.println(pop.get(i).toString());
		//}
		
		// System.out.println(solution.toString());

		// System.out.println(pop.get(1).toString());
		 
	    assertTrue(pop.size() == 100);
	}
	
	@Test
	public void DoesSpecificAlgorithmWork() throws IOException {
		
		Problem<BinarySolution> problem;
	    
	    problem = new FLP("resources/FLPInstances/cap71");
	    
		//GeneticAlgorithmFLP<BinarySolution> algorithm = new GeneticAlgorithmFLP<BinarySolution>();
		//algorithm.setProblem(problem);
		//algorithm.run();
	    List<BinarySolution> pop = new ArrayList<>();
	    for(int i=0; i<2; i++) {
	    	pop.add(problem.createsolution());
	    }
		//BinarySolution solution = problem.createsolution();
		
		CrossoverOperator<BinarySolution> crossoverOperator = new HUXCrossover(0.9);
		
		List<BinarySolution> pop2 = crossoverOperator.execute(pop);
		
		for(int i=0; i< 2; i++) {
			System.out.println(pop.get(i).toString());
			//System.out.println(pop2.get(i).toString());
		}
		
		
		for(int i=0; i< 2; i++) {
			//System.out.println(pop.get(i).toString());
			System.out.println(pop2.get(i).toString());
		}
		
		
		assertNotNull(pop.get(0).variables());
	}

}
