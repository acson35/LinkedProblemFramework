package main.operator.crossover.CrossoverClasses;

import java.util.ArrayList;
import java.util.List;

import main.operator.crossover.CrossoverOperator;
import main.solution.solution;
import main.util.createRandomGeneration.LinkedRandom;

@SuppressWarnings("serial")
public class NPointCrossover<T> implements CrossoverOperator<solution<T>> {
	private final LinkedRandom randomNumberGenerator = LinkedRandom.getInstance();
	private final double probability;
	private final int crossovers;
	
	public NPointCrossover(double probability, int crossovers) {
	  if (probability < 0.0) throw new ArithmeticException("Probability can't be negative");
	  if (crossovers < 1) throw new ArithmeticException("Number of crossovers is less than one");
	  this.probability = probability;
	  this.crossovers = crossovers;
	}

	public NPointCrossover(int crossovers) {
	  this.crossovers = crossovers;
	  this.probability = 1.0;
	}
	
	@Override
	public List<solution<T>> execute(List<solution<T>> solution) {
		
		try {
			if(getNumberOfRequiredParents() == solution.size()) {
				throw new ArithmeticException("Point Crossover requires + "
			            + getNumberOfRequiredParents()
			            + " parents, but got "
			            + solution.size());
			}
			if (randomNumberGenerator.nextDouble() < probability) {
				return doCrossover(solution);
			}else {
		    return solution;
			}
		} catch (Exception e) {
				e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public double getCrossoverProbability() {
		return probability;
	}
	
	@Override
	public int getNumberOfRequiredParents() {
		return 2;
	}
	
	@Override
	public int getNumberOfGeneratedChildren() {
		return 2;
	}
	
	@SuppressWarnings("unused")
	private List<solution<T>> doCrossover(List<solution<T>> s) throws Exception {
	    solution<T> mom = s.get(0);
	    solution<T> dad = s.get(1);
	    if(mom.variables().size() != dad.variables().size()) {
	    	throw new Exception("The 2 parents doesn't have the same number of variables");
	    }
	    if(mom.variables().size() < crossovers) {
	    	throw new Exception("The number of crossovers is higher than the number of variables");
	    }
		List<Integer> crossoverPoints = new ArrayList<>(); 
		for (int i = 0; i < crossoverPoints.size(); i++) {
		   crossoverPoints.add(randomNumberGenerator.nextInt(0, mom.variables().size() - 1));
		}
		solution<T> girl = mom.copy();
		solution<T> boy = dad.copy();
		boolean swap = false;

		for (int i = 0; i < mom.variables().size(); i++) {
			   if (swap) {
			     boy.variables().set(i, mom.variables().get(i));
			     girl.variables().set(i, dad.variables().get(i));
			    }

			    if (crossoverPoints.contains(i)) {
			      swap = !swap;
			    }
		}
		
		List<solution<T>> result = new ArrayList<>();
		result.add(girl);
		result.add(boy);
		return result;
	}
	
}
