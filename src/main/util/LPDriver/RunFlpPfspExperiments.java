package main.util.LPDriver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import Example.FLP;
import Example.FLP_PFSPproblems;
import Example.PFSP_Makespan;
import main.algorithm.Algorithm;
import main.algorithm.AlgorithmLP;
import main.algorithm.EvolutionaryAlgorithms.GeneticAlgorithm.GeneticAlgorithmBuilder;
import main.algorithm.GeneticAlgorithmVariant.*;
import main.algorithm.SequentialAlgorithm.SequentialAlgorithm;
import main.operator.crossover.CrossoverOperator;
import main.operator.crossover.CrossoverClasses.HUXCrossover;
import main.operator.crossover.CrossoverClasses.PMXCrossover;
import main.operator.mutation.MutationOperator;
import main.operator.mutation.MutationClasses.BitFlipMutation;
import main.operator.mutation.MutationClasses.PermutationSwapMutation;
import main.operator.selection.SelectionOperator;
import main.operator.selection.selectionClasses.TournamentSelection;
import main.problem.Problem;
import main.solution.solution;
import main.solution.solutionPair;
import main.solution.solutionSet;
import main.solution.binarySolution.BinarySolution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.comparator.ObjectiveComparator;
import main.util.evaluator.SolutionListEvaluator;
import main.util.evaluator.Implementation.MultiThreadedSolutionListEvaluator;
import main.util.model.instance.FLPInstance.FLPInstance;

public class RunFlpPfspExperiments<S extends solution<?>> {

	
		public RunFlpPfspExperiments() {
			
		}   
	   
		@SuppressWarnings({ "unchecked", "rawtypes", "hiding" })
		public <S> Algorithm<S, S> BuildAlgorithm(Problem<S> problem, int populationSize, int maxEvaluations,
                CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator,
                SelectionOperator<List<S>, S> selectionOperator, SolutionListEvaluator<S> evaluator, Comparator<S> comparator, GeneticAlgorithmVariant variant) {
			
			 GeneticAlgorithmBuilder builder =
			        new GeneticAlgorithmBuilder(problem, crossoverOperator, mutationOperator)
			            .setPopulationSize(populationSize)
			            .setMaxEvaluations(maxEvaluations)
			            .setSelectionOperator(selectionOperator)
			            .setSolutionListEvaluator(evaluator)
			            .setVariant(variant);
			
			return builder.build();
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public List<Algorithm<S, S>> getAlgorithms(Problem parentproblem, Problem childproblem){
			List<Algorithm<S, S>> algorithmList = new ArrayList<>();
			int numberOfCores = 4;
		    Algorithm<BinarySolution, BinarySolution> algorithm1 = BuildAlgorithm(parentproblem, 10000, 100,
		    		new HUXCrossover(0.9),  new BitFlipMutation(0.8),
		    		new TournamentSelection<BinarySolution>(new ObjectiveComparator(), 20), 
		    		new MultiThreadedSolutionListEvaluator<BinarySolution>(numberOfCores), 
		    		new ObjectiveComparator(), 
		    		GeneticAlgorithmVariant.GENERATIONAL);
		    
		    algorithmList.add((Algorithm<S, S>) algorithm1);
		      
		    Algorithm<IntegerPermutationSolution, IntegerPermutationSolution> algorithm2 = BuildAlgorithm(childproblem, 10000, 100,
		    		new PMXCrossover(0.1),  new PermutationSwapMutation(0.5),
		    		new TournamentSelection<IntegerPermutationSolution>(new ObjectiveComparator(), 20), 
		    		new MultiThreadedSolutionListEvaluator<IntegerPermutationSolution>(numberOfCores), 
		    		new ObjectiveComparator(), 
		    		GeneticAlgorithmVariant.GENERATIONAL);  
		   
		    algorithmList.add((Algorithm<S, S>) algorithm2);
			   
			return algorithmList;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public void FlpPfspExperiments() throws IOException {
			
			File file = new File("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/InputData/FLP");
	        File[] files = file.listFiles();
	        
	        File file2 = new File("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/InputData/PFSP");
	        File[] files2 = file2.listFiles();

	        for(int i = 0; i < files.length; i++) {
	        	String flp_filename = files[i].toString();
	        
	        	for(int j = 0; j < files2.length; j++) {
	        		String pfsp_filename = files2[j].toString();
	        		FLP_PFSPproblems linkedproblem = new FLP_PFSPproblems(flp_filename,pfsp_filename);
	        		
	        		FLP parentproblem = (FLP) linkedproblem.getLinkedProblems().get(1);
	        		int problemsize1 = ((FLPInstance) parentproblem.getData()).getnCustomers();
	        		
	        		PFSP_Makespan childproblem = (PFSP_Makespan) linkedproblem.getLinkedProblems().get(2);
	        		int problemsize2 = childproblem.getTotalNumberOfVariables();
	        		
	        		if(problemsize1==problemsize2) {	
	        			LinkedProblemRunner experiment;
	            		experiment = new LinkedProblemRunnerBuilder("SequenceBased_"+parentproblem.getName()+"_"+childproblem.getName()+"_"+files[i].getName()+"_"+files2[j].getName())
	            			       .setProblemList(linkedproblem)
	            			       .setAlgorithmList(getAlgorithms(parentproblem, childproblem))
	            			       .setOutputLinkedProblemFileName("_Results")
	            			       .setIndependentRuns(100)
	            			       .build();   
	            		
	            		AlgorithmLP<S, solutionSet> sequentialAlgo = new SequentialAlgorithm<S, solutionSet>(experiment);
	            		
	            		ExecuteAlgorithms<S> execute = new ExecuteAlgorithms<S>(experiment, sequentialAlgo);
	            		execute.run();
	        		}
	        	}
	        } 
		}
}
