package main.util.createRandomGeneration;

import java.io.Serializable;

import main.util.createRandomGeneration.RandomGeneration.RandomGenerator;

@SuppressWarnings("serial")
public class LinkedRandom implements Serializable{
	 private static LinkedRandom myinstance ;
	 private LinkedProblemRandomGenerator generator ;
	  
	 public LinkedRandom() {
		 generator = new RandomGenerator();
	 }
	 
	 public static LinkedRandom getInstance() {
	   if (myinstance == null) {
		   myinstance = new LinkedRandom() ;
		 }
		return myinstance ;
	}
	 
	public void setRandomGenerator(LinkedProblemRandomGenerator generator) {
	    this.generator = generator;
	}

	public LinkedProblemRandomGenerator getRandomGenerator() {
	    return generator ;
	}

	public int nextInt(int lowerBound, int upperBound) {
	   return generator.nextInt(lowerBound, upperBound) ;
	}

	public double nextDouble() {
	  return generator.nextDouble() ;
	}

	public double nextDouble(double lowerBound, double upperBound) {
	  return generator.nextDouble(lowerBound, upperBound) ;
	}

	public void setSeed(long seed) {
	  generator.setSeed(seed);
	}

	public long getSeed() {
	  return generator.getSeed() ;
	}

	public String getGeneratorName() {
	  return generator.getName() ;
	}
		 
}
