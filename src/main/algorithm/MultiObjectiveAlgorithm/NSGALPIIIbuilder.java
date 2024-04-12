package main.algorithm.MultiObjectiveAlgorithm;

import java.util.List;

import main.algorithm.AlgorithmBuilder;
import main.algorithm.AlgorithmBuilderLP;
import main.linkedproblem.LinkedProblem;
import main.operator.crossover.CrossoverOperator;
import main.operator.mutation.MutationOperator;
import main.operator.selection.SelectionOperator;
import main.solution.solution;
import main.solution.solutionSet;
import main.util.evaluator.SolutionListEvaluator;
import main.util.evaluator.Implementation.SequentialSolutionListEvaluator;

public class NSGALPIIIbuilder<S extends solutionSet> implements AlgorithmBuilderLP<NSGALPIII<S>> {

	  private LinkedProblem problem;
	  private int maxIterations ;
	  private int populationSize ;
	  private int numberOfDivisions;
	  private List<CrossoverOperator<solution<?>>> crossoverOperator ;
	  private List<MutationOperator<solution<?>>> mutationOperator ;
	  private SelectionOperator<List<S>, S> selectionOperator ;
	  private SolutionListEvaluator<S> evaluator ;
	
	
	  /** Builder constructor */
	  public NSGALPIIIbuilder(LinkedProblem problem) {
	    this.problem = problem ;
	    maxIterations = 250 ;
	    populationSize = 100 ;
	    numberOfDivisions = 12;
	    evaluator = new SequentialSolutionListEvaluator<S>() ;
	  }
	  
	  public NSGALPIIIbuilder<S> setMaxIterations(int maxIterations) {
		    this.maxIterations = maxIterations ;

		    return this ;
	  }
	  
	  public NSGALPIIIbuilder<S> setPopulationSize(int populationSize) {
		    this.populationSize = populationSize ;

		    return this ;
	  }

	  public NSGALPIIIbuilder<S> setNumberOfDivisions(int numberOfDivisions){
		    this.numberOfDivisions = numberOfDivisions ;

		    return this ;
	  }

	  public NSGALPIIIbuilder<S> setCrossoverOperator(List<CrossoverOperator<solution<?>>> crossoverOperator) {
		    this.crossoverOperator = crossoverOperator ;

		    return this ;
	  }	
	  public NSGALPIIIbuilder<S> setMutationOperator(List<MutationOperator<solution<?>>> mutationOperator) {
		    this.mutationOperator = mutationOperator ;

		    return this ;
	  }
	  
	  public NSGALPIIIbuilder<S> setSelectionOperator(SelectionOperator<List<S>, S> selectionOperator) {
		    this.selectionOperator = selectionOperator ;

		    return this ;
	  }
	  
	  public NSGALPIIIbuilder<S> setSolutionListEvaluator(SolutionListEvaluator<S> evaluator) {
		    this.evaluator = evaluator ;

		    return this ;
	  }
	  
	  public SolutionListEvaluator<S> getEvaluator() {
		    return evaluator;
	  }
	  
	  public LinkedProblem getProblem() {
		    return problem;
	  }
	
	  public int getMaxIterations() {
		    return maxIterations;
	  }

	  public int getPopulationSize() {
		    return populationSize;
	  }
	  
	  public int getNumberOfDivisions() { return numberOfDivisions; }

	  public List<CrossoverOperator<solution<?>>> getCrossoverOperator() {
		    return crossoverOperator;
	  }
	
	  public List<MutationOperator<solution<?>>> getMutationOperator() {
		    return mutationOperator;
	  }

	  public SelectionOperator<List<S>, S> getSelectionOperator() {
		    return selectionOperator;
	  }
	  
	@Override
	public NSGALPIII<S> build() {
		return new NSGALPIII<>(this);
	}

}
