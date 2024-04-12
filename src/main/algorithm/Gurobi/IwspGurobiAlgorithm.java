package main.algorithm.Gurobi;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Example.IWSP;
import gurobi.*;
import main.problem.Problem;
import main.solution.solution;
import main.solution.solutionPair;
import main.util.distance.DistImplementation.EuclideanDistanceBetweenTwoLocations;
import main.util.distance.DistImplementation.EuclideanDistanceBetweenTwoLocations.WeightType;
import main.util.model.instance.location;
import main.util.model.instance.FLPInstance.Cost;
import main.util.model.instance.FLPInstance.Customer;
import main.util.model.instance.FLPInstance.FLPInstance;
import main.util.model.instance.FLPInstance.Facility;
import main.util.model.instance.IWSPInstance.IWSPInstance;
import main.util.model.instance.IWSPInstance.Inventory;
import main.util.model.instance.IWSPInstance.Vehicle;

public class IwspGurobiAlgorithm<S extends solution<?>, R> extends AbstractGurobiAlgorithm<S, R> {
	
	//IWSP iwsp;
	Problem<S> problem;
	//S solution;

	public IwspGurobiAlgorithm(Problem<S> problem){
		super(problem);
		this.problem = problem;
		//this.iwsp = iwsp;
	}
	
	public double[] getDemands() {	
		double Demand[] = new double[((IWSPInstance) problem.getData()).getnCustomers()];
	    for(int i = 0; i<Demand.length; i++) {
	      Demand[i] = ((IWSPInstance) problem.getData()).getCustomer(i).getDemand();
		}  
	    return Demand;
	}
	
	public double[] getCapacity() {
		double Capacity[] = new double[((IWSPInstance) problem.getData()).getnFacilities()];
	      for(int i = 0; i<Capacity.length; i++) {
	    	  Capacity[i] = ((IWSPInstance) problem.getData()).getFacility(i).getCapacity();	
	      }
		return Capacity;
	}
	
	public double[] getFixedCosts() {
		double FixedCosts[] = new double[((IWSPInstance) problem.getData()).getnFacilities()];
	      for(int i = 0; i<FixedCosts.length; i++) {
	    	  FixedCosts[i] = ((IWSPInstance) problem.getData()).getFacility(i).getFixedCost().getCost();		
	    }   
	    return FixedCosts;	
	}
	
	public double[][] getTransportCost(){
		
		int nPlants = ((IWSPInstance) problem.getData()).getnFacilities();
	     int nWarehouses = ((IWSPInstance) problem.getData()).getnCustomers();
		 double transCosts[][] = new double[nWarehouses][nPlants];
	      for(int i = 0; i<transCosts.length; i++) {
	    	  for(int j = 0; j<transCosts[i].length; j++) {
	    		  transCosts[i][j] = ((IWSPInstance) problem.getData()).getFacility(j).getDemandCost(((IWSPInstance) problem.getData()).getCustomer(i)).getCost();	
		      }	
	      }
		return transCosts;
	}
	
	public String getName() {
		return "GUROBI" ;
	}

	public String getDescription() {
		return "Exact Solution by Gurobi";
	}
	
	@Override
	public R getResult() {
		// TODO Auto-generated method stub
		return getExactSolution();
	}

	@Override
	public List<S> getPopulation() {
		// TODO Auto-generated method stub
		return null;
	}

}
