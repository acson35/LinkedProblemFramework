package main.operator.crossover.CrossoverClasses;

import java.util.List;

import main.operator.crossover.CrossoverOperator;
import main.solution.solution;

@SuppressWarnings("serial")
public class TwoPointCrossover<T> implements CrossoverOperator<solution<T>> {
	  
	NPointCrossover<T> operator;

	  public TwoPointCrossover(double probability) {
	    this.operator = new NPointCrossover<>(probability, 2);
	  }

	  @Override
	  public List<solution<T>> execute(List<solution<T>> solutions) {
	    return operator.execute(solutions);
	  }

	  @Override
	  public double getCrossoverProbability() {
	    return operator.getCrossoverProbability() ;
	  }

	  @Override
	  public int getNumberOfRequiredParents() {
	    return operator.getNumberOfRequiredParents();
	  }

	  @Override
	  public int getNumberOfGeneratedChildren() {
	    return 2;
	  }

}
