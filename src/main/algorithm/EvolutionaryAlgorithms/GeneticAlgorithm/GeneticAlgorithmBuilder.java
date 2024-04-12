package main.algorithm.EvolutionaryAlgorithms.GeneticAlgorithm;

import java.util.Comparator;
import java.util.List;

import main.algorithm.Algorithm;
import main.algorithm.AlgorithmBuilder;
import main.algorithm.GeneticAlgorithmVariant.GeneticAlgorithmVariant;
import main.operator.crossover.CrossoverOperator;
import main.operator.mutation.MutationOperator;
import main.operator.selection.SelectionOperator;
import main.operator.selection.selectionClasses.BinaryTournamentSelection;
import main.operator.selection.selectionClasses.TournamentSelection;
import main.problem.Problem;
import main.solution.solution;
import main.util.comparator.ObjectiveComparator;
import main.util.comparator.ObjectiveComparator.Ordering;
import main.util.evaluator.SolutionListEvaluator;
import main.util.evaluator.Implementation.SequentialSolutionListEvaluator;

/**
 * Created by ajnebro on 10/12/14.
 */
public class GeneticAlgorithmBuilder<S extends solution<?>, R> implements AlgorithmBuilder<Algorithm<S, R>> {
	  
	
	 GeneticAlgorithmVariant geneticAlgoVariant;

	  
	  private Problem<S> problem;
	  private int maxEvaluations;
	  private int populationSize;
	  private CrossoverOperator<S> crossoverOperator;
	  private MutationOperator<S> mutationOperator;
	  private SelectionOperator<List<S>, S> selectionOperator;
	  private SolutionListEvaluator<S> evaluator;

	  private GeneticAlgorithmVariant variant ;
	 
	  Comparator<S> comparator;
	  private SelectionOperator<List<S>, S> defaultSelectionOperator = new TournamentSelection<S>(comparator, (populationSize/2)) ;

	  /**
	   * Builder constructor
	   */
	  public GeneticAlgorithmBuilder(Problem<S> problem,
	    CrossoverOperator<S> crossoverOperator,
	    MutationOperator<S> mutationOperator) {
	    this.problem = problem;
	    maxEvaluations = 25000;
	    populationSize = 100;
	    this.mutationOperator = mutationOperator ;
	    this.crossoverOperator = crossoverOperator ;
	    this.selectionOperator = defaultSelectionOperator ;

	    evaluator = new SequentialSolutionListEvaluator<S>();

	    this.variant = GeneticAlgorithmVariant.GENERATIONAL ;
	    this.comparator = new ObjectiveComparator<S>(Ordering.ASCENDING);
	  }

	  public GeneticAlgorithmBuilder<S, R> setProblem(Problem<S> problem) {
		    this.problem = problem;

		    return this;
	  }
	  
	  public GeneticAlgorithmBuilder<S, R> setMaxEvaluations(int maxEvaluations) {
	    this.maxEvaluations = maxEvaluations;

	    return this;
	  }

	  public GeneticAlgorithmBuilder<S, R> setPopulationSize(int populationSize) {
	    this.populationSize = populationSize;

	    return this;
	  }

	  public GeneticAlgorithmBuilder<S, R> setSelectionOperator(SelectionOperator<List<S>, S> selectionOperator) {
	    this.selectionOperator = selectionOperator;

	    return this;
	  }

	  public GeneticAlgorithmBuilder<S, R> setSolutionListEvaluator(SolutionListEvaluator<S> evaluator) {
	    this.evaluator = evaluator;

	    return this;
	  }

	  public GeneticAlgorithmBuilder<S, R> setVariant(GeneticAlgorithmVariant variant) {
	    this.variant = variant;

	    return this;
	  }
	  
	  public GeneticAlgorithmBuilder<S, R> setComparator(Comparator<S> comparator) {
		    this.comparator = comparator;

		    return this;
	 }
	  
	  @Override
	  public Algorithm<S, R> build() {
	    if (variant == GeneticAlgorithmVariant.GENERATIONAL) {
	      return new GenerationalGeneticAlgorithm<S, R>(problem, maxEvaluations, populationSize,
	          crossoverOperator, mutationOperator, selectionOperator, evaluator, comparator);
	    } else if (variant == GeneticAlgorithmVariant.STEADY_STATE) {
	      return new SteadyStateGeneticAlgorithm<S, R>(problem, maxEvaluations, populationSize,
	          crossoverOperator, mutationOperator, selectionOperator, comparator);
	    } else {
	      throw new RuntimeException("Unknown variant: " + variant) ;
	    }
	  }

	  /*
	   * Getters
	   */
	  public Problem<S> getProblem() {
	    return problem;
	  }

	  public int getMaxEvaluations() {
	    return maxEvaluations;
	  }

	  public int getPopulationSize() {
	    return populationSize;
	  }

	  public CrossoverOperator<S> getCrossoverOperator() {
	    return crossoverOperator;
	  }

	  public MutationOperator<S> getMutationOperator() {
	    return mutationOperator;
	  }

	  public SelectionOperator<List<S>, S> getSelectionOperator() {
	    return selectionOperator;
	  }

	  public SolutionListEvaluator<S> getEvaluator() {
	    return evaluator;
	  }

	  public GeneticAlgorithmVariant getVariant() {
	    return variant ;
	  }

	  public Comparator<S> getComparator() { 
		return comparator;
	  }
	  
}
