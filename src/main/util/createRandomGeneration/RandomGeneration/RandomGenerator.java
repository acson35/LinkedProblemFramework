package main.util.createRandomGeneration.RandomGeneration;

import java.util.Random;

import main.util.createRandomGeneration.LinkedProblemRandomGenerator;

@SuppressWarnings("serial")
public class RandomGenerator implements LinkedProblemRandomGenerator {
	private Random rnd ;
	private long seed ;
	private static final String NAME = "RandomGenerator" ;
	
	public RandomGenerator() {
	    this(System.currentTimeMillis());
	 }

	public RandomGenerator(long seed) {
	    this.seed = seed ;
	    rnd = new Random(seed) ;
	}
	
	@Override
	public int nextInt(int lowerBound, int upperBound) {
		return lowerBound + rnd.nextInt((upperBound - lowerBound + 1)) ;
	}

	@Override
	public double nextDouble(double lowerBound, double upperBound) {
		return lowerBound + rnd.nextDouble()*(upperBound - lowerBound) ;
	}

	@Override
	public double nextDouble() {
		return nextDouble(0.0, 1.0);
	}

	@Override
	public void setSeed(long seed) {
		this.seed = seed ;
	    rnd.setSeed(seed);
	}

	@Override
	public long getSeed() {
		return seed;
	}

	@Override
	public String getName() {
		return NAME;
	}

}
