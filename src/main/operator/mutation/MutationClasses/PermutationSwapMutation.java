package main.operator.mutation.MutationClasses;

import java.util.List;

import main.operator.mutation.MutationOperator;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.createRandomGeneration.LinkedRandom;

@SuppressWarnings("serial")
public class PermutationSwapMutation implements MutationOperator<IntegerPermutationSolution> {
	  private double mutationProbability;
	  private LinkedRandom mutationRandomGenerator;
	  private LinkedRandom positionRandomGenerator;

	  public PermutationSwapMutation(double mutationProbability) {
	    this(mutationProbability, LinkedRandom.getInstance(),LinkedRandom.getInstance());
	  }

	  public PermutationSwapMutation(
	      double mutationProbability, LinkedRandom randomGenerator) {
	    this(mutationProbability, randomGenerator, LinkedRandom.getInstance());
	  }

	  public PermutationSwapMutation(
	    double mutationProbability,
	    LinkedRandom mutationRandomGenerator,
	    LinkedRandom positionRandomGenerator) {
	    probabilityIsValid(mutationProbability);
	    
	    this.mutationProbability = mutationProbability;
	    this.mutationRandomGenerator = mutationRandomGenerator;
	    this.positionRandomGenerator = positionRandomGenerator;
	  }

	  
	  public static void probabilityIsValid(double value) {
		    if ((value < 0.0) || (value > 1.0)) {
		      throw new ArithmeticException("The parameter " + value + " is not a valid probability value") ;
		    }
	  }
	  
	  @Override
	  public double getMutationProbability() {
	    return mutationProbability;
	  }

	  public void setMutationProbability(double mutationProbability) {
	    this.mutationProbability = mutationProbability;
	    
	  }

	  @Override
	  public IntegerPermutationSolution execute(IntegerPermutationSolution solution) {
		  if (null == solution)
				throw new NullPointerException("Null parameter");
	    doMutation(solution);
	    return solution;
	  }

	  public void doMutation(IntegerPermutationSolution solution) {
	    int permutationLength;
	    
	    for(int i=0; i<solution.variables().size(); i++) {
	    	if(null == solution.variables().get(i))continue;
	    	permutationLength = solution.variables().get(i).size();
	    	if ((permutationLength != 0) && (permutationLength != 1)) {
	   	      if (mutationRandomGenerator.nextDouble() < mutationProbability) {
	   	        int pos1 = positionRandomGenerator.nextInt(0, permutationLength - 1);
	   	        int pos2 = positionRandomGenerator.nextInt(0, permutationLength - 1);

	   	        while (pos1 == pos2) {
	   	          if (pos1 == (permutationLength - 1))
	   	            pos2 = positionRandomGenerator.nextInt(0, permutationLength - 2);
	   	          else pos2 = positionRandomGenerator.nextInt(pos1, permutationLength - 1);
	   	        }
	   	        List<Integer> templist = solution.variables().get(i);
	   	        Integer temp = templist.get(pos1);
	   	        templist.set(pos1, solution.variables().get(i).get(pos2));
	   	        templist.set(pos2, temp);
	   	        solution.variables().set(i, templist);
	   	      }
	   	   }
	    }
	  }

}
