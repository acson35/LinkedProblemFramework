package main.util.LPDriver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


import Example.JAP;
import Example.JAP_TSP_Problems;
import Example.TSP;
import main.algorithm.Algorithm;
import main.algorithm.AlgorithmLP;
import main.algorithm.EvolutionaryAlgorithms.GeneticAlgorithm.GeneticAlgorithmBuilder;
import main.algorithm.GeneticAlgorithmVariant.GeneticAlgorithmVariant;
import main.algorithm.SequentialAlgorithm.SequentialAlgorithm;
import main.operator.crossover.CrossoverOperator;
import main.operator.crossover.CrossoverClasses.IntegerSBXCrossover;
import main.operator.crossover.CrossoverClasses.PMXCrossover;
import main.operator.mutation.MutationOperator;
import main.operator.mutation.MutationClasses.IntegerPolynomialMutation;
import main.operator.mutation.MutationClasses.PermutationSwapMutation;
import main.operator.selection.SelectionOperator;
import main.operator.selection.selectionClasses.TournamentSelection;
import main.problem.Problem;
import main.solution.solution;
import main.solution.solutionPair;
import main.solution.solutionSet;
import main.solution.integerSolution.IntegerSolution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.comparator.ObjectiveComparator;
import main.util.evaluator.SolutionListEvaluator;
import main.util.evaluator.Implementation.MultiThreadedSolutionListEvaluator;
import main.util.model.instance.JAPInstance;

public class RunSEQUENCEjaptspExperiments<S extends solution<?>> {

	public RunSEQUENCEjaptspExperiments() {
		
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
	    Algorithm<IntegerSolution, IntegerSolution> algorithm1 = BuildAlgorithm(parentproblem, 10000, 100,
	    		new IntegerSBXCrossover(0.9, 20),  new IntegerPolynomialMutation(),
	    		new TournamentSelection<IntegerSolution>(new ObjectiveComparator(), 20), 
	    		new MultiThreadedSolutionListEvaluator<IntegerSolution>(numberOfCores), 
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
	public void SEQUENCE_JapTspExperiments() throws IOException {
		
		File file = new File("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/InputData/JAP");
        File[] files = file.listFiles();
        
        File file2 = new File("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/InputData/TSP");
        File[] files2 = file2.listFiles();

        for(int i = 0; i < files.length; i++) {
        	String jap_filename = files[i].toString();
        
        	for(int j = 0; j < files2.length; j++) {
        		String tsp_filename = files2[j].toString();
        		JAP_TSP_Problems linkedproblem = new JAP_TSP_Problems(jap_filename,tsp_filename);
        		
        		JAP parentproblem = (JAP) linkedproblem.getLinkedProblems().get(1);
        		int problemsize1 = ((JAPInstance) parentproblem.getData()).getJobsNum();
        		
        		TSP childproblem = (TSP) linkedproblem.getLinkedProblems().get(2);
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
