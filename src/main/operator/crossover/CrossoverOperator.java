package main.operator.crossover;

import java.util.List;

import main.operator.Operator;

public interface CrossoverOperator<Solution> extends Operator<List<Solution>,List<Solution>> {
	double getCrossoverProbability() ;

	int getNumberOfRequiredParents() ;
	int getNumberOfGeneratedChildren() ;
}
