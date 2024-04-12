package main.operator.crossover.CrossoverClasses;

import java.util.ArrayList;
import java.util.List;

import main.operator.crossover.CrossoverOperator;
import main.solution.binarySolution.BinarySolution;
import main.util.createBinary.createBinary;
import main.util.createRandomGeneration.LinkedRandom;

@SuppressWarnings("serial")
public class HUXCrossover implements CrossoverOperator<BinarySolution> {

	private double crossoverProbability;
	private LinkedRandom randomGenerator;

	  
	public HUXCrossover(double crossoverProbability) {
	  this(crossoverProbability, LinkedRandom.getInstance());
	}

	public HUXCrossover(double crossoverProbability, LinkedRandom randomGenerator) {
	  probabilityIsValid(crossoverProbability) ;

	  this.crossoverProbability = crossoverProbability;
	  this.randomGenerator = randomGenerator;
	}
	
	public static void probabilityIsValid(double value) {
	    if ((value < 0.0) || (value > 1.0)) {
	      throw new ArithmeticException("The parameter " + value + " is not a valid probability value") ;
	    }
	  }
	
	@Override
	public List<BinarySolution> execute(List<BinarySolution> parents) {
		
		try {
			if(parents.size() == 2)
				return doCrossover(crossoverProbability, parents.get(0), parents.get(1));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	
	}

	@Override
	public double getCrossoverProbability() {
		return crossoverProbability;
	}
	
	public void setCrossoverProbability(double crossoverProbability) {
	  this.crossoverProbability = crossoverProbability;
	}

	@Override
	public int getNumberOfRequiredParents() {
		return 2;
	}

	@Override
	public int getNumberOfGeneratedChildren() {
		// TODO Auto-generated method stub
		return 2;
	}
	
	public List<BinarySolution> doCrossover(
		double probability, BinarySolution parent1, BinarySolution parent2) throws Exception {
		List<BinarySolution> offspring = new ArrayList<>();
		
		if(parent1.variables().size()==1) {
			offspring.add((BinarySolution) parent1.copy());
			offspring.add((BinarySolution) parent2.copy());
			if (randomGenerator.nextDouble() < probability) {
				for (int var = 0; var < parent1.variables().size(); var++) {
			       createBinary p1 = parent1.variables().get(var);
			       createBinary p2 = parent2.variables().get(var);

			       for (int bit = 0; bit < p1.size(); bit++) {
			         if (p1.get(bit) != p2.get(bit)) {
			           if (randomGenerator.nextDouble() < 0.5) {
			             offspring.get(0).variables().get(var).set(bit, p2.get(bit));
			             offspring.get(1).variables().get(var).set(bit, p1.get(bit));
			           }
			         }
			       }
				}
			}
		}else {
			createBinary bin1 = getBinary(parent1);
			createBinary bin2 = getBinary(parent2);
			offspring.add((BinarySolution) parent1.copy());
			offspring.add((BinarySolution) parent2.copy());
			for (int bit = 0; bit < bin1.size(); bit++) {
		         if (bin1.get(bit) != bin2.get(bit)) {
		           if (randomGenerator.nextDouble() < 0.5) {
		        	   bin1.set(bit, bin2.get(bit));
		        	   bin2.set(bit, bin1.get(bit));
		           }
		         }
		       }
			List<createBinary> custToFacilityVar = new ArrayList<createBinary>();
			List<createBinary> custToFacilityVar2 = new ArrayList<createBinary>();
			if(parent1.problemname().equalsIgnoreCase("FLP")) {
			  custToFacilityVar = offspring.get(0).CustomerAllocationToFacility(bin1);
			  custToFacilityVar2 = offspring.get(1).CustomerAllocationToFacility(bin2);
			}else if(parent1.problemname().equalsIgnoreCase("IWSP")){
				custToFacilityVar = offspring.get(0).CustomerAllocationToFacility2(bin1);
				custToFacilityVar2 = offspring.get(1).CustomerAllocationToFacility2(bin2);
			}else if(parent1.problemname().equalsIgnoreCase("SFLP")){
				custToFacilityVar = offspring.get(0).CustomerAllocationToFacility3(bin1);
				custToFacilityVar2 = offspring.get(1).CustomerAllocationToFacility3(bin2);
			}
			
			for(int i = 0; i<offspring.get(0).variables().size();i++) {
				createBinary bits = new createBinary(custToFacilityVar.size());
				for(int j = 0; j<custToFacilityVar.size();j++) {
					if(custToFacilityVar.get(j).get(i)) {
						bits.set(j);
					}
				}
				offspring.get(0).variables().set(i, bits);
			}
			for(int i = 0; i<offspring.get(1).variables().size();i++) {
				createBinary bits = new createBinary(custToFacilityVar2.size());
				for(int j = 0; j<custToFacilityVar2.size();j++) {
					if(custToFacilityVar2.get(j).get(i)) {
						bits.set(j);
					}
				}
				offspring.get(1).variables().set(i, bits);
			}
			
		}
		
		
		
		//custToFacilityVar(createBinary createbinary)

		

		return offspring;
	}
	
	public createBinary getBinary(BinarySolution solution) {
		
		createBinary bin = new createBinary(solution.variables().size());
		for(int i=0; i< solution.variables().size(); i++) {
			  if(solution.variables().get(i).cardinality()>0) {
				  bin.set(i);
			  } 
		  }
		return bin;
	}

}
