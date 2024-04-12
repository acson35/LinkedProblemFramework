package main.util.distance.DistImplementation;

import main.solution.solution;
import main.util.distance.Distance;

public class EuclideanDistanceBetweenTwoSolutions implements Distance<solution<?>, solution<?>>{

	
	public enum WeightType {CEIL_2D, EUC_2D};
	public final WeightType edgeWeightType;
	
	public EuclideanDistanceBetweenTwoSolutions(WeightType edgeWeightType) {
		this.edgeWeightType = edgeWeightType;
	}
	@Override
	public double distance(solution<?> sol1, solution<?> sol2) {
		double distance=0;
		for(int i=0; i<sol1.getFitness().length;i++) {
			distance += Math.sqrt(Math.pow(sol1.getFitness()[i], 2) + Math.pow(sol2.getFitness()[i], 2));
		}
		if (this.edgeWeightType == WeightType.CEIL_2D){
			return (int)Math.ceil(distance);
		}else if (this.edgeWeightType == WeightType.EUC_2D){
			return (int)Math.rint(distance);
		}else{
			throw new RuntimeException("Unsupported type of edge weight.");
		}
		
	}

}
