package main.operator.mutation.MutationClasses;

import java.util.ArrayList;
import java.util.List;

import main.operator.mutation.MutationOperator;
import main.problem.integerProblem.IntegerProblem;
import main.solution.integerSolution.IntegerSolution;
import main.util.createRandomGeneration.LinkedRandom;
import main.util.range.Ranges;

public class IntegerPolynomialMutation implements MutationOperator<IntegerSolution> {
	  private static final double DEFAULT_PROBABILITY = 0.01 ;
	  private static final double DEFAULT_DISTRIBUTION_INDEX = 20.0 ;

	  private double distributionIndex ;
	  private double mutationProbability ;
	  private LinkedRandom randomGenerator ;

	  public IntegerPolynomialMutation() {
	    this(DEFAULT_PROBABILITY, DEFAULT_DISTRIBUTION_INDEX) ;
	  }

	  public IntegerPolynomialMutation(IntegerProblem problem, double distributionIndex) {
	    this(1.0/problem.getRanges().size(), distributionIndex) ;
	  }

	  public IntegerPolynomialMutation(double mutationProbability, double distributionIndex) {
		  this(mutationProbability, distributionIndex, LinkedRandom.getInstance());
	  }

	  public IntegerPolynomialMutation(double mutationProbability, double distributionIndex,
			  LinkedRandom randomGenerator) {
	    if (mutationProbability < 0) {
	      throw new ArithmeticException("Mutation probability is negative: " + mutationProbability) ;
	    } else if (distributionIndex < 0) {
	      throw new ArithmeticException("Distribution index is negative: " + distributionIndex);
	    }
	    this.mutationProbability = mutationProbability;
	    this.distributionIndex = distributionIndex;
	    this.randomGenerator = randomGenerator ;
	  }

	  @Override
	  public double getMutationProbability() {
	    return mutationProbability;
	  }

	  public double getDistributionIndex() {
	    return distributionIndex;
	  }

	  /* Setters */
	  public void setDistributionIndex(double distributionIndex) {
	    this.distributionIndex = distributionIndex;
	  }

	  public void setMutationProbability(double mutationProbability) {
	    this.mutationProbability = mutationProbability;
	  }

	  public IntegerSolution execute(IntegerSolution solution) {
	    if (null == solution) {
	      throw new NullPointerException("Null parameter") ;
	    }

	    doMutation(mutationProbability, solution);
	    return solution;
	  }

	  private void doMutation(double probability, IntegerSolution solution) {
	    Double rnd, delta1, delta2, mutPow, deltaq;
	    double y, yl, yu, val, xy;
	    
	  //  System.out.println(solution);

	    for (int i = 0; i < solution.variables().size(); i++) {
	    	if(null == solution.variables().get(i))continue;
	    	if(null==solution.getRange(i))continue;
	    	List<Integer> solVal = new ArrayList<Integer>();
	    	for(int j = 0; j < solution.variables().get(i).size(); j++){
	    		if(solution.variables().get(i).get(j)>0) {
	    			solVal.add(solution.variables().get(i).get(j));
	    		}
	    	}
	    	for(int j = 0; j < solution.variables().get(i).size(); j++) {
	    		if(0 == solution.variables().get(i).get(j))continue;
	    		if (randomGenerator.nextDouble() <= probability) {
		    		y = (double)solution.variables().get(i).get(j);
			        //Ranges<Integer> ranges = solution.getRange(j);
			        int min=solVal.get(0);
	    			int max=solVal.get(0);
	    			  for (int k = 1; k < solVal.size(); k++) {
	    				min = Math.min(min, solVal.get(k));
		    			max = Math.max(max, solVal.get(k)); 
	    			  }
			        yl = (double)min;
			        yu = (double)max;
			        if (yl == yu) {
			          y = yl ;
			        } else {
			          delta1 = (y - yl) / (yu - yl);
			          delta2 = (yu - y) / (yu - yl);
			          rnd = randomGenerator.nextDouble();
			          mutPow = 1.0 / (distributionIndex + 1.0);
			          if (rnd <= 0.5) {
			            xy = 1.0 - delta1;
			            val = 2.0 * rnd + (1.0 - 2.0 * rnd) * (Math.pow(xy, distributionIndex + 1.0));
			            deltaq = Math.pow(val, mutPow) - 1.0;
			          } else {
			            xy = 1.0 - delta2;
			            val = 2.0 * (1.0 - rnd) + 2.0 * (rnd - 0.5) * (Math.pow(xy, distributionIndex + 1.0));
			            deltaq = 1.0 - Math.pow(val, mutPow);
			          }
			          y = y + deltaq * (yu - yl);
			          y = repairSolutionVariableValue(y, yl, yu);
			        }
			        if(solVal.contains((int)y)) {
			        	solution.variables().get(i).set(j, (int) y);
			        }
			        
		    	}
		   
    		}
	    }
	  }
	  
	  public double repairSolutionVariableValue(double value, double lowerBound, double upperBound) {
		    
		    if(lowerBound > upperBound) {
		    	throw new ArithmeticException("The lower bound (" + lowerBound + ") is greater than the "
				        + "upper bound (" + upperBound+")");
		    }
		    
		    double result = value ;
		    if (value < lowerBound) {
		      result = randomGenerator.nextDouble(lowerBound, upperBound) ;
		    }
		    if (value > upperBound) {
		      result = randomGenerator.nextDouble(lowerBound, upperBound) ;
		    }

		    return result ;
	}

}
