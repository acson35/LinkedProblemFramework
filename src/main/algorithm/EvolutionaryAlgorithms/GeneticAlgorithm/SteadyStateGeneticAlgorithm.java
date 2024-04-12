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

public class SteadyStateGeneticAlgorithm<S extends solution<?>, R> extends AbstractGeneticAlgorithm<S, R> {
	  private Comparator<S> comparator;
	  private int maxEvaluations;
	  private int evaluations;

	  /**
	   * Constructor
	   */
	  public SteadyStateGeneticAlgorithm(Problem<S> problem, int maxEvaluations, int populationSize,
	                                     CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator,
	                                     SelectionOperator<List<S>, S> selectionOperator, Comparator<S> comparator) {
	    super(problem);
	    setMaxPopulationSize(populationSize);
	    this.maxEvaluations = maxEvaluations;

	    this.crossoverOperator = crossoverOperator;
	    this.mutationOperator = mutationOperator;
	    this.selectionOperator = selectionOperator;

	    this.comparator = comparator;
	  }

	  @Override protected boolean isStoppingConditionReached() {
	    return (evaluations >= maxEvaluations);
	  }

	  @Override protected List<S> replacement(List<S> population, List<S> offspringPopulation) {
	    Collections.sort(population, comparator) ;
	    int worstSolutionIndex = population.size() - 1;
	    if (comparator.compare(population.get(worstSolutionIndex), offspringPopulation.get(0)) > 0) {
	      population.remove(worstSolutionIndex);
	      population.add(offspringPopulation.get(0));
	    }

	    return population;
	  }

	  @Override protected List<S> reproduction(List<S> matingPopulation) {
	    List<S> offspringPopulation = new ArrayList<>(1);

	    List<S> parents = new ArrayList<>(2);
	    parents.add(matingPopulation.get(0));
	    parents.add(matingPopulation.get(1));

	    List<S> offspring = crossoverOperator.execute(parents);
	    mutationOperator.execute(offspring.get(0));

	    offspringPopulation.add(offspring.get(0));
	    return offspringPopulation;
	  }

	  @Override protected List<S> selection(List<S> population) {
	    List<S> matingPopulation = new ArrayList<>(2);
	    for (int i = 0; i < 2; i++) {
	      S solution = selectionOperator.execute(population);
	      matingPopulation.add(solution);
	    }

	    return matingPopulation;
	  }

	  @Override protected List<S> evaluatePopulation(List<S> population) {
	    for (S solution : population) {
	      getProblem().evaluate(solution);
	    }

	    return population;
	  }

	  @Override public R getResult() {
		Collections.sort(getPopulation(), comparator) ;
		return (R) population.get(0);
	  }
	  
	  @Override public void initProgress() {
	    evaluations = 0;
	  }

	  @Override public void updateProgress() {
		 //System.out.println(evaluations);
	    evaluations++;
	  }

	  public String getName() {
	    return "ssGA" ;
	  }

	  public String getDescription() {
	    return "Steady-State Genetic Algorithm" ;
	  }

}
