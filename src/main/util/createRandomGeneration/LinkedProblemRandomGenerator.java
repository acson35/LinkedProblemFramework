package main.util.createRandomGeneration;

import java.io.Serializable;

public interface LinkedProblemRandomGenerator extends Serializable {
	 public int nextInt(int lowerBound, int upperBound) ;
	 public double nextDouble(double lowerBound, double upperBound) ;
	 public double nextDouble() ;
	 public void setSeed(long seed) ;
	 public long getSeed() ;
	 public String getName() ;
}
