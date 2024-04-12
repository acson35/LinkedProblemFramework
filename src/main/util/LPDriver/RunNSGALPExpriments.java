package main.util.LPDriver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Example.FLP;
import Example.FLP_PFSPproblems;
import Example.PFSP_Makespan;
import main.algorithm.AlgorithmLP;
import main.algorithm.MultiObjectiveAlgorithm.NSGALP;
import main.linkedproblem.LinkedProblem;
import main.operator.crossover.CrossoverOperator;
import main.operator.crossover.CrossoverClasses.HUXCrossover;
import main.operator.crossover.CrossoverClasses.PMXCrossover;
import main.operator.mutation.MutationOperator;
import main.operator.mutation.MutationClasses.BitFlipMutation;
import main.operator.mutation.MutationClasses.PermutationSwapMutation;
import main.operator.selection.SelectionOperator;
import main.operator.selection.selectionClasses.PairRankingAndCrowdingSelection;
import main.solution.solution;
import main.solution.solutionPair;
import main.solution.solutionSet;
import main.solution.binarySolution.BinarySolution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.comparator.DominancePairComparator;
import main.util.evaluator.SolutionListEvaluator;
import main.util.evaluator.Implementation.MultiThreadedSolutionListEvaluator;
import main.util.model.instance.FLPInstance.FLPInstance;

public class RunNSGALPExpriments<S extends solution<?>> {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AlgorithmLP<S, solutionSet> getAlgorithms(LinkedProblem linkedproblem){
		
		int numberOfCores = 4;
		CrossoverOperator<BinarySolution> crossoverOperator1 = 
	    		new HUXCrossover(0.9);
	    
	    MutationOperator<BinarySolution> mutationOperator1 =
	        new BitFlipMutation(0.8);
	    
	    CrossoverOperator<IntegerPermutationSolution> crossoverOperator2 = 
	    		new PMXCrossover(0.1);
	    MutationOperator<IntegerPermutationSolution> mutationOperator2 =
	        new PermutationSwapMutation(0.5);
	    
	    SelectionOperator<List<solutionSet>, solutionSet> selectionOperator = 
	    		new PairRankingAndCrowdingSelection(5, new DominancePairComparator<solutionSet>());
	    
	    SolutionListEvaluator<solution<?>> evaluator = 
	    		new MultiThreadedSolutionListEvaluator<solution<?>>(numberOfCores);
	    
		
		List<CrossoverOperator> crossoverOperators = new ArrayList<>();
		
		crossoverOperators.add(crossoverOperator1);
		crossoverOperators.add(crossoverOperator2);
		
		List<MutationOperator> mutationOperators = new ArrayList<>();
		
		mutationOperators.add(mutationOperator1);
		mutationOperators.add(mutationOperator2);
        
		
		AlgorithmLP<S, solutionSet> flp_pfsp = new NSGALP(linkedproblem, 1000, 500, 500, 500, crossoverOperators, mutationOperators, selectionOperator, evaluator);
		
		return flp_pfsp;
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void NSGALP_FlpPfspExperiments() throws IOException {
		
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
            		experiment = new LinkedProblemRunnerBuilder("NSGALPBased_"+parentproblem.getName()+"_"+childproblem.getName()+"_"+files[i].getName()+"_"+files2[j].getName())
            			       .setProblemList(linkedproblem)
            			       .setOutputLinkedProblemFileName("_Results")
            			       .setIndependentRuns(10)
            			       .build();   
            		
            		
            		AlgorithmLP<S, solutionSet> NSGALP_Algo = getAlgorithms(linkedproblem);
            		
            		ExecuteAlgorithms<S> execute = new ExecuteAlgorithms<S>(experiment, NSGALP_Algo);
            		execute.run();
        		}
        	}
        } 
	}
}
