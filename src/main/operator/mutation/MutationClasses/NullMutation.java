package main.operator.mutation.MutationClasses;

import main.operator.mutation.MutationOperator;

public class NullMutation<S>  implements MutationOperator<S> {

	  @Override
	  public S execute(S solution) {
	    return solution;
	  }

	  @Override
	  public double getMutationProbability() {
	    return 1.0;
	  }

}
