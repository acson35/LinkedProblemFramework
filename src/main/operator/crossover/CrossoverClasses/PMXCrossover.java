package main.operator.crossover.CrossoverClasses;

import java.util.ArrayList;
import java.util.List;

import main.operator.crossover.CrossoverOperator;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.createRandomGeneration.LinkedProblemRandomGenerator;
import main.util.createRandomGeneration.LinkedRandom;

public class PMXCrossover implements
	CrossoverOperator<IntegerPermutationSolution> {
	private static double crossoverProbability = 1.0;
	private LinkedRandom cuttingPointRandomGenerator ;
	private LinkedRandom crossoverRandomGenerator ;

	public PMXCrossover() {
		this(crossoverProbability, LinkedRandom.getInstance(), LinkedRandom.getInstance());
	}

	public PMXCrossover(double crossoverProbability) {
		this(crossoverProbability, LinkedRandom.getInstance(), LinkedRandom.getInstance());
	}

	public PMXCrossover(double crossoverProbability, LinkedRandom randomGenerator) {
		this(crossoverProbability, randomGenerator.getInstance(), LinkedRandom.getInstance());
	}


	public PMXCrossover(double crossoverProbability, LinkedRandom crossoverRandomGenerator, LinkedRandom cuttingPointRandomGenerator) {
		if ((crossoverProbability < 0) || (crossoverProbability > 1)) {
			throw new ArithmeticException("Crossover probability value invalid: " + crossoverProbability) ;
		}
		this.crossoverProbability = crossoverProbability;
		this.crossoverRandomGenerator = crossoverRandomGenerator ;
		this.cuttingPointRandomGenerator = cuttingPointRandomGenerator ;
	}


	@Override
	public double getCrossoverProbability() {
		return crossoverProbability;
	}


	public void setCrossoverProbability(double crossoverProbability) {
		this.crossoverProbability = crossoverProbability;
	}


	public List<IntegerPermutationSolution> execute(List<IntegerPermutationSolution> parents) {
		if (null == parents) {
			throw new NullPointerException("Null parameter") ;
		} else if (parents.size() != 2) {
			throw new ArithmeticException("There must be two parents instead of " + parents.size()) ;
		}

		return doCrossover(crossoverProbability, parents) ;
	}

	public List<IntegerPermutationSolution> doCrossover(double probability, List<IntegerPermutationSolution> parents) {
		
		List<IntegerPermutationSolution> offspring = new ArrayList<>(2);
		offspring.add((IntegerPermutationSolution) parents.get(0).copy()) ;
		offspring.add((IntegerPermutationSolution) parents.get(1).copy()) ;

		for(int i=0; i<parents.get(0).variables().size(); i++) {

			if(null == parents.get(0).variables().get(i))
				continue;
			
			int permutationLength = parents.get(0).variables().get(i).size() ;
			
			
			if(permutationLength<=1)
				continue;

			if (crossoverRandomGenerator.nextDouble() < probability) {
				int cuttingPoint1;
				int cuttingPoint2;

				cuttingPoint1 = cuttingPointRandomGenerator.nextInt(0, permutationLength - 1);
				cuttingPoint2 = cuttingPointRandomGenerator.nextInt(0, permutationLength - 1);
				while (cuttingPoint2 == cuttingPoint1)
					cuttingPoint2 = cuttingPointRandomGenerator.nextInt(0, permutationLength - 1);

				if (cuttingPoint1 > cuttingPoint2) {
					int swap;
					swap = cuttingPoint1;
					cuttingPoint1 = cuttingPoint2;
					cuttingPoint2 = swap;
				}

				
				List<Integer> replacement1 = new ArrayList<>(permutationLength);
				List<Integer> replacement2 = new ArrayList<>(permutationLength);
				
				for (int j = 0; j < permutationLength; j++) {
					replacement1.add(null);
					replacement2.add(null);
				}	

				List<Integer> list1 = parents.get(0).variables().get(i);
				List<Integer> list2 = parents.get(1).variables().get(i);
				
				for (int j = cuttingPoint1; j <= cuttingPoint2; j++) {
					replacement1.set(j, list1.get(j));
					replacement2.set(j, list2.get(j));
				}
				
				
				for (int j = cuttingPoint1; j <= cuttingPoint2; j++) {
					
					list1.set(j, replacement2.get(j));	
					list2.set(j, replacement1.get(j));			
				
				}
				
				for(int j=0; j<permutationLength; j++) {
					if(j>=cuttingPoint1 && j<=cuttingPoint2)
						continue;
					int val = list1.get(j);
					if(replacement2.contains(val)) {
						int n = replacement2.indexOf(val);
						int m = replacement1.get(n);
						while(replacement2.contains(m)) {
							 n = replacement2.indexOf(m);
							 m = replacement1.get(n);
						}
						list1.set(j, m);
					}
					
				}
				for(int j=0; j<permutationLength; j++) {
					if(j>=cuttingPoint1 && j<=cuttingPoint2)
						continue;
					int val = list2.get(j);
					if(replacement1.contains(val)) {
						int n = replacement1.indexOf(val);
						int m = replacement2.get(n);
						while(replacement1.contains(m)) {
							 n = replacement1.indexOf(m);
							 m = replacement2.get(n);
						}
						list2.set(j, m);
					}
				}
			
				offspring.get(0).variables().set(i, list1);
				offspring.get(1).variables().set(i, list2);
			}
		
		}

		return offspring;
	}

	@Override
	public int getNumberOfRequiredParents() {
		return 2 ;
	}

	@Override
	public int getNumberOfGeneratedChildren() {
		return 2;
	}
}
