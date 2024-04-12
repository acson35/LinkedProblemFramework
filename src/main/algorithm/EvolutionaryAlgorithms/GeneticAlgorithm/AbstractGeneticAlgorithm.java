package main.algorithm.EvolutionaryAlgorithms.GeneticAlgorithm;

import java.util.ArrayList;
import java.util.List;

import main.algorithm.EvolutionaryAlgorithms.AbstractEvolutionaryAlgorithm;
import main.linkedproblem.LinkedProblem;
import main.operator.crossover.CrossoverOperator;
import main.operator.mutation.MutationOperator;
import main.operator.selection.SelectionOperator;
import main.problem.Problem;
import main.solution.solution;
import main.solution.solutionPair;
import main.util.constraintHandling.ConstraintHandling;

/**
 * Abstract class representing a genetic algorithm
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */

public abstract class AbstractGeneticAlgorithm<S, R> extends AbstractEvolutionaryAlgorithm<S, R> {
	  protected int maxPopulationSize ;
	  protected SelectionOperator<List<S>, S> selectionOperator ;
	  protected CrossoverOperator<S> crossoverOperator ;
	  protected MutationOperator<S> mutationOperator ;

	  /* Setters and getters */
	  public void setMaxPopulationSize(int maxPopulationSize) {
	    this.maxPopulationSize = maxPopulationSize ;
	  }
	  
	  public int getMaxPopulationSize() {
	    return maxPopulationSize ;
	  }
	  
	  public SelectionOperator<List<S>, S> getSelectionOperator() {
	    return selectionOperator;
	  }

	  public CrossoverOperator<S> getCrossoverOperator() {
	    return crossoverOperator;
	  }

	  public MutationOperator<S> getMutationOperator() {
	    return mutationOperator;
	  }

	
	  public AbstractGeneticAlgorithm(Problem<S> problem) {
	    setProblem(problem);
	  }


	@SuppressWarnings("unchecked")
	@Override
	  protected List<S> createInitialPopulation() {
	    List<S> population = new ArrayList<>(getMaxPopulationSize());
	    int i=0;
	    while ( i < getMaxPopulationSize()) {
	      S newIndividual = (S) getProblem().createsolution();
	     // getProblem().evaluate(newIndividual);
	   //   if(ConstraintHandling.isFeasible((solution<?>) newIndividual)) {
	    	  population.add(newIndividual);
	    	  i++;
	   //	  System.out.println(newIndividual.toString());
	   //   }
	      
	    }
	    return population;
	  }

	  
	  @Override
	  protected List<S> selection(List<S> population) {
	    List<S> matingPopulation = new ArrayList<>(population.size());
	    for (int i = 0; i < getMaxPopulationSize(); i++) {
	      S solution = selectionOperator.execute(population);
	      matingPopulation.add(solution);
	    }

	    return matingPopulation;
	  }

	 
	  @Override
	  protected List<S> reproduction(List<S> population) {
	    int numberOfParents = crossoverOperator.getNumberOfRequiredParents() ;

	    checkNumberOfParents(population, numberOfParents);

	    List<S> offspringPopulation = new ArrayList<>(getMaxPopulationSize());
	    for (int i = 0; i < getMaxPopulationSize(); i += numberOfParents) {
	      List<S> parents = new ArrayList<>(numberOfParents);
	      for (int j = 0; j < numberOfParents; j++) {
	        parents.add(population.get(i+j));
	      }

	      List<S> offspring = crossoverOperator.execute(parents);

	      for(S s: offspring){
	        mutationOperator.execute(s);
	        offspringPopulation.add(s);
	      }
	    }
	    return offspringPopulation;
	  }

	  
	  protected void checkNumberOfParents(List<S> population, int numberOfParentsForCrossover) {
	    if ((population.size() % numberOfParentsForCrossover) != 0) {
	      throw new ArithmeticException("Wrong number of parents: the remainder if the " +
	              "population size (" + population.size() + ") is not divisible by " +
	              numberOfParentsForCrossover) ;
	    }
	  }

}
