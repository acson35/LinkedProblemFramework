package main.operator.crossover.CrossoverClasses;

import java.util.ArrayList;
import java.util.List;

import main.operator.crossover.CrossoverOperator;
import main.solution.binarySolution.BinarySolution;
import main.util.createBinary.createBinary;
import main.util.createRandomGeneration.LinkedRandom;

public class SinglePointCrossover implements CrossoverOperator<BinarySolution> {
	  private double crossoverProbability;
	  private LinkedRandom crossoverRandomGenerator;
	  private LinkedRandom pointRandomGenerator;

	  public SinglePointCrossover(double crossoverProbability) {
	    this(crossoverProbability, LinkedRandom.getInstance(), LinkedRandom.getInstance());
	  }

	  public SinglePointCrossover(
	      double crossoverProbability, LinkedRandom randomGenerator) {
	    this(crossoverProbability, randomGenerator.getInstance(), LinkedRandom.getInstance());
	  }

	  public SinglePointCrossover(
	      double crossoverProbability, LinkedRandom crossoverRandomGenerator, LinkedRandom pointRandomGenerator) {
	    if (crossoverProbability < 0) {
	      throw new ArithmeticException("Crossover probability is negative: " + crossoverProbability);
	    }
	    this.crossoverProbability = crossoverProbability;
	    this.crossoverRandomGenerator = crossoverRandomGenerator;
	    this.pointRandomGenerator = pointRandomGenerator;
	  }

	  @Override
	  public double getCrossoverProbability() {
	    return crossoverProbability;
	  }

	  public void setCrossoverProbability(double crossoverProbability) {
	    this.crossoverProbability = crossoverProbability;
	  }

	  @Override
	  public List<BinarySolution> execute(List<BinarySolution> parents) {
	   
		if (null == parents) {
			throw new NullPointerException("Null parameter") ;
		} else if (parents.size() != 2) {
			throw new ArithmeticException("There must be two parents instead of " + parents.size()) ;
		}
	    
	    return doCrossover(crossoverProbability, parents.get(0), parents.get(1));
	  }

	  
	  public List<BinarySolution> doCrossover(
	    double probability, BinarySolution parent1, BinarySolution parent2) {
	    List<BinarySolution> offspring = new ArrayList<>(2);
	    offspring.add((BinarySolution) parent1.copy());
	    offspring.add((BinarySolution) parent2.copy());

	    if (crossoverRandomGenerator.nextDouble() < probability) {
	      
	      int totalNumberOfBits = parent1.getTotalNumberOfBits();

	      int crossoverPoint = pointRandomGenerator.nextInt(0, totalNumberOfBits - 1);

	      int variable = 0;
	      int bitsAccount = parent1.variables().get(variable).getNumberOfBits();
	      while (bitsAccount < (crossoverPoint + 1)) {
	        variable++;
	        bitsAccount += parent1.variables().get(variable).getNumberOfBits();
	      }

	      // 4. Compute the bit into the selected variable
	      int diff = bitsAccount - crossoverPoint;
	      int intoVariableCrossoverPoint = parent1.variables().get(variable).getNumberOfBits() - diff;

	      // 5. Apply the crossover to the variable;
	      createBinary offspring1, offspring2;
	      offspring1 = (createBinary) parent1.variables().get(variable).clone();
	      offspring2 = (createBinary) parent2.variables().get(variable).clone();

	      for (int i = intoVariableCrossoverPoint; i < offspring1.getNumberOfBits(); i++) {
	        boolean swap = offspring1.get(i);
	        offspring1.set(i, offspring2.get(i));
	        offspring2.set(i, swap);
	      }

	      offspring.get(0).variables().set(variable, offspring1);
	      offspring.get(1).variables().set(variable, offspring2);

	      for (int i = variable + 1; i < parent1.variables().size(); i++) {
	        offspring.get(0).variables().set(i, (createBinary) parent2.variables().get(i).clone());
	        offspring.get(1).variables().set(i, (createBinary) parent1.variables().get(i).clone());
	      }
	    }
	    return offspring;
	  }

	  @Override
	  public int getNumberOfRequiredParents() {
	    return 2;
	  }

	  @Override
	  public int getNumberOfGeneratedChildren() {
	    return 2;
	  }
}
