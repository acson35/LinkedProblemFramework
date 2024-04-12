package main.util.LPDriver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Example.JAP;
import Example.JAP_TSP_Problems;
import Example.TSP;
import main.algorithm.AlgorithmLP;
import main.algorithm.MultiCriteriaRankingAlgorithm.MCRGALP;
import main.linkedproblem.LinkedProblem;
import main.operator.crossover.CrossoverOperator;
import main.operator.crossover.CrossoverClasses.IntegerSBXCrossover;
import main.operator.crossover.CrossoverClasses.PMXCrossover;
import main.operator.mutation.MutationOperator;
import main.operator.mutation.MutationClasses.IntegerPolynomialMutation;
import main.operator.mutation.MutationClasses.PermutationSwapMutation;
import main.operator.selection.SelectionOperator;
import main.operator.selection.selectionClasses.TopsisSelection;
import main.solution.solution;
import main.solution.solutionPair;
import main.solution.solutionSet;
import main.solution.integerSolution.IntegerSolution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.evaluator.SolutionListEvaluator;
import main.util.evaluator.Implementation.MultiThreadedSolutionListEvaluator;
import main.util.model.instance.JAPInstance;
import main.util.ranking.Criteria;

public class RunMCRGALPjaptspExperiments<S extends solution<?>> {
	

	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AlgorithmLP<S, solutionSet> getAlgorithms(LinkedProblem linkedproblem){
		
		int numberOfCores = 4;
		CrossoverOperator<IntegerSolution> crossoverOperator1 = 
				new IntegerSBXCrossover(0.9, 20);
	    
		MutationOperator<IntegerSolution> mutationOperator1 =
		        new IntegerPolynomialMutation();
	    
	    CrossoverOperator<IntegerPermutationSolution> crossoverOperator2 = 
	    		new PMXCrossover(0.1);
	    MutationOperator<IntegerPermutationSolution> mutationOperator2 =
	        new PermutationSwapMutation(0.5);
	    
	    List<Criteria> criteria = new ArrayList<>();
		
		criteria.add(new Criteria("japObj", 0.35, true));
		criteria.add(new Criteria("japConstraint", 0.30, true));
		criteria.add(new Criteria("tspObj", 0.35, true));
		//criteria.add(new Criteria("tspConstraint", 0.25, true));
		
		SelectionOperator<List<solutionSet>, List<solutionSet>> selectionOperator = 
	    		new TopsisSelection<solutionSet>(100, criteria);
	    
	    SolutionListEvaluator<solution<?>> evaluator = 
	    		new MultiThreadedSolutionListEvaluator<solution<?>>(numberOfCores);
	    
		
		List<CrossoverOperator> crossoverOperators = new ArrayList<>();
		
		crossoverOperators.add(crossoverOperator1);
		crossoverOperators.add(crossoverOperator2);
		
		List<MutationOperator> mutationOperators = new ArrayList<>();
		
		mutationOperators.add(mutationOperator1);
		mutationOperators.add(mutationOperator2);
        
		
		AlgorithmLP<S, solutionSet> flp_pfsp = new MCRGALP(linkedproblem, 10000, 100, 100, 100, criteria, crossoverOperators, mutationOperators, selectionOperator, evaluator);
		
		 
		return flp_pfsp;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void MCRGALP_JapTspExperiments() throws IOException {
		
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
            		experiment = new LinkedProblemRunnerBuilder("MCRGALPBased_"+parentproblem.getName()+"_"+childproblem.getName()+"_"+files[i].getName()+"_"+files2[j].getName())
            			       .setProblemList(linkedproblem)
            			       .setOutputLinkedProblemFileName("_Results")
            			       .setIndependentRuns(3)
            			       .build();   
            		
            		
            		AlgorithmLP<S, solutionSet> MCRGALP_Algo = getAlgorithms(linkedproblem);
            		
            		ExecuteAlgorithms<S> execute = new ExecuteAlgorithms<S>(experiment, MCRGALP_Algo);
            		execute.run();
        		}
        	}
        } 
	}
	
	

}
