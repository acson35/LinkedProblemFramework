package main.algorithm.EvolutionaryAlgorithms.GeneticAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import main.operator.crossover.CrossoverOperator;
import main.operator.mutation.MutationOperator;
import main.operator.selection.SelectionOperator;
import main.problem.Problem;
import main.solution.solution;
import main.util.comparator.ObjectiveComparator;
import main.util.comparator.ObjectiveComparator.Ordering;
import main.util.constraintHandling.ConstraintHandling;
import main.util.evaluator.SolutionListEvaluator;

public class GenerationalGeneticAlgorithm<S extends solution<?>, R> extends AbstractGeneticAlgorithm<S, R> {
	  private Comparator<S> comparator;
	  private int maxEvaluations;
	  private int evaluations;

	  private SolutionListEvaluator<S> evaluator;

	  /**
	   * Constructor
	   */
	  public GenerationalGeneticAlgorithm(Problem<S> problem, int maxEvaluations, int populationSize,
	                                      CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator,
	                                      SelectionOperator<List<S>, S> selectionOperator, SolutionListEvaluator<S> evaluator, Comparator<S> comparator) {
	    super(problem);
	    this.maxEvaluations = maxEvaluations;
	    this.setMaxPopulationSize(populationSize);

	    this.crossoverOperator = crossoverOperator;
	    this.mutationOperator = mutationOperator;
	    this.selectionOperator = selectionOperator;

	    this.evaluator = evaluator;

	    this.comparator = comparator;
	  }

	  @Override public boolean isStoppingConditionReached() {
	    return (evaluations >= maxEvaluations);
	  }

	  @Override protected List<S> replacement(List<S> population, List<S> offspringPopulation) {
	    Collections.sort(population, comparator);
	    offspringPopulation.add(population.get(0));
	    offspringPopulation.add(population.get(1));
	    Collections.sort(offspringPopulation, comparator) ;
	    offspringPopulation.remove(offspringPopulation.size() - 1);
	    offspringPopulation.remove(offspringPopulation.size() - 1);

	    return offspringPopulation;
	  }

	  @Override protected List<S> evaluatePopulation(List<S> population) {
	    population = evaluator.evaluate(population, getProblem());
	    
	    return population;
	  }

	  @Override public R getResult() {
	    Collections.sort(getPopulation(), comparator) ;
	    return (R) population.get(0);
	  }
	  

	  @Override public void initProgress() {
	    evaluations = getMaxPopulationSize();
	  }

	  @Override public void updateProgress() {
		 // System.out.println(evaluations);
	    evaluations += getMaxPopulationSize();
	  }

	  public String getName() {
	    return "gGA" ;
	  }

	  public String getDescription() {
	    return "Generational Genetic Algorithm" ;
	  }

}
