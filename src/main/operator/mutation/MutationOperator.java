package main.operator.mutation;

import main.operator.Operator;

public interface MutationOperator<Solution> extends Operator<Solution, Solution> {
	
	double getMutationProbability() ;
}
