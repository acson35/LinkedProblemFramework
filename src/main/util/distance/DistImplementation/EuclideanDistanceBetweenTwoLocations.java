package main.util.distance.DistImplementation;

import main.util.distance.Distance;
import main.util.model.instance.location;


public class EuclideanDistanceBetweenTwoLocations implements Distance<location, location> {
	
	public enum WeightType {CEIL_2D, EUC_2D};
	public final WeightType edgeWeightType;
	
	public EuclideanDistanceBetweenTwoLocations(WeightType edgeWeightType) {
		this.edgeWeightType = edgeWeightType;
	}
	
	@Override
	public double distance(location a, location b) {
		double distance = Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
		if (this.edgeWeightType == WeightType.CEIL_2D){
			return (int)Math.ceil(distance);
		}else if (this.edgeWeightType == WeightType.EUC_2D){
			return (int)Math.rint(distance);
		}else{
			throw new RuntimeException("Unsupported type of edge weight.");
		}
	}
	
}
