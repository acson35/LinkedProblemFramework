package main.operator.mutation.MutationClasses;

import java.util.ArrayList;
import java.util.List;

import main.operator.mutation.MutationOperator;
import main.solution.binarySolution.BinarySolution;
import main.util.createBinary.createBinary;
import main.util.createRandomGeneration.LinkedRandom;

@SuppressWarnings("serial")
public class BitFlipMutation implements MutationOperator<BinarySolution> {
	  private double mutationProbability;
	  private LinkedRandom randomGenerator;

	  public BitFlipMutation(double mutationProbability) {
	    this(mutationProbability, LinkedRandom.getInstance());
	  }

	  public BitFlipMutation(double mutationProbability, LinkedRandom randomGenerator) {
	    if (mutationProbability < 0) {
	      throw new ArithmeticException("Mutation probability is negative: " + mutationProbability);
	    }
	    this.mutationProbability = mutationProbability;
	    this.randomGenerator = randomGenerator;
	  }

	  @Override
	  public double getMutationProbability() {
	    return mutationProbability;
	  }


	  public void setMutationProbability(double mutationProbability) {
	    this.mutationProbability = mutationProbability;
	  }

	  @Override
	  public BinarySolution execute(BinarySolution solution) {
	    
	    if (null == solution)
			throw new NullPointerException("Null parameter");
	    doMutation(mutationProbability, solution);
	    return solution;
	  }

	  public void doMutation(double probability, BinarySolution solution) {
		  
		  if(solution.problemname().equalsIgnoreCase("FLP")) {
			  createBinary bin = new createBinary(solution.variables().size());
			  for(int i=0; i< solution.variables().size(); i++) {
				  if(solution.variables().get(i).cardinality()>0) {
					  bin.set(i);
				  } 
			  }
			  for(int i=0; i< bin.size(); i++) {
				  if(randomGenerator.nextDouble() <= probability) {
					  bin.flip(i);
				  }
			  }
			  List<createBinary> custToFacilityVar = solution.CustomerAllocationToFacility(bin);
			  for(int i = 0; i<solution.variables().size();i++) {
					createBinary bits = new createBinary(custToFacilityVar.size());
					for(int j = 0; j<custToFacilityVar.size();j++) {
						if(custToFacilityVar.get(j).get(i)) {
							bits.set(j);
						}
					}
					solution.variables().set(i, bits);
				}	
		  }else if(solution.problemname().equalsIgnoreCase("IWSP") || solution.problemname().equalsIgnoreCase("SFLP")) {
			  createBinary bin = new createBinary(solution.variables().size());
			  for(int i=0; i< solution.variables().size(); i++) {
				  if(solution.variables().get(i).cardinality()>0) {
					  bin.set(i);
				  } 
			  }
			  for(int i=0; i< bin.size(); i++) {
				  if(randomGenerator.nextDouble() <= probability) {
					  bin.flip(i);
				  }
			  }
			  List<createBinary> custToFacilityVar = new ArrayList<createBinary>();
			  if(solution.problemname().equalsIgnoreCase("IWSP")) {
				  custToFacilityVar = solution.CustomerAllocationToFacility2(bin);
			  }else {
				  custToFacilityVar = solution.CustomerAllocationToFacility3(bin);
			  }
			  
			  for(int i = 0; i<solution.variables().size();i++) {
					createBinary bits = new createBinary(custToFacilityVar.size());
					for(int j = 0; j<custToFacilityVar.size();j++) {
						if(custToFacilityVar.get(j).get(i)) {
							bits.set(j);
						}
					}
					solution.variables().set(i, bits);
				}	
		  }else {
			  for (int i = 0; i < solution.variables().size(); i++) {
			      for (int j = 0; j < solution.variables().get(i).getNumberOfBits(); j++) {
			        if (randomGenerator.nextDouble() <= probability) {
			          solution.variables().get(i).flip(j);
			        }
			      }
			  }
		  }
	  }

}
