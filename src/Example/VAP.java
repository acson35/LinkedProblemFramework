package Example;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import main.problem.integerProblem.Implementation.AbstractIntegerProblem;
import main.solution.solution;
import main.solution.binarySolution.BinarySolution;
import main.solution.integerSolution.IntegerSolution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.createBinary.createBinary;
import main.util.distance.DistImplementation.EuclideanDistanceBetweenTwoLocations;
import main.util.distance.DistImplementation.EuclideanDistanceBetweenTwoLocations.WeightType;
import main.util.model.instance.JAPInstance;
import main.util.model.instance.location;
import main.util.model.instance.FLPInstance.Cost;
import main.util.model.instance.FLPInstance.Customer;
import main.util.model.instance.FLPInstance.Facility;
import main.util.model.instance.IWSPInstance.IWSPInstance;
import main.util.model.instance.IWSPInstance.Inventory;
import main.util.model.instance.IWSPInstance.Vehicle;

@SuppressWarnings("serial")
public class VAP extends AbstractIntegerProblem{

	EuclideanDistanceBetweenTwoLocations euc = 
			new EuclideanDistanceBetweenTwoLocations(WeightType.CEIL_2D);
	solution<?> solution;
	
	public VAP(String datafilename) throws IOException{
		setData(readProblem(datafilename));
		setnVariables(((IWSPInstance) getData()).getnCustomers());
		setnObjectives(1);
		setnConstraints(1);
		setName("VAP");
		setFilepath(datafilename);
		
		List<Integer> lowerR = new ArrayList<>(getnVariables());
		List<Integer> upperR = new ArrayList<>(getnVariables());
		
		for (int i = 0; i < getnVariables(); i++) {
		   lowerR.add(1);
		   upperR.add(((IWSPInstance) getData()).getnVehicles());
		}
		setVariableRanges(lowerR, upperR);
	}
	
	
	public VAP(String datafilename, solution<?> solution) throws IOException{
		this(datafilename);
		setParentsolution(solution);
	}
	
	
	@Override
	public void evaluate(IntegerSolution solution) {
		double fitness = 0.0;
		fitness += vFixedCost(solution);
		fitness += vVariableCost(solution);
		solution.getFitness()[0] = fitness;
		evaluateConstraint(solution);
	}
	
	@SuppressWarnings("unchecked")
	public double vFixedCost(IntegerSolution solution) {
		double vFixedCost = 0.0;
		for(int i = 0; i<solution.variables().size(); i++) {
			if(null==solution.variables().get(i))continue;
			
			Set<Integer> vehicles = new HashSet<Integer>(solution.variables().get(i));
			
			if(vehicles.contains(0)){
				vehicles.remove(0);
			}
			//System.out.println(solution.variables().get(i).toString());
			Iterator<Integer> it = vehicles.iterator();
			while(it.hasNext()) {
				vFixedCost += ((IWSPInstance) getData()).getVehicle(it.next()-1).getFixedCost().getCost();
			}
		}
		return vFixedCost;
	}
	
	
	public void setParentSolution(solution<?> solution) {
		setParentsolution(solution);
	}
	
	@SuppressWarnings("unchecked")
	public double vVariableCost(IntegerSolution solution) {
		double vVariableCost = 0.0;
		if(null!=parentsolution()) {
			if(parentsolution().problemname().equalsIgnoreCase("MTSP")) {
				IntegerPermutationSolution sol = (IntegerPermutationSolution) parentsolution();
				for(int i = 0; i<solution.variables().size(); i++) {
					if(null==solution.variables().get(i))continue;
					
					Set<Integer> vehicles = new HashSet<Integer>(solution.variables().get(i));
					if(vehicles.contains(0)){
						vehicles.remove(0);
					}
					if(vehicles.size()==0)continue;
					for (Integer vehicle : vehicles){
						double dist=0.0;
						for(int j = 0; j < sol.variables().get(vehicle-1).size()-1; j++) {
							
							dist += ((IWSPInstance) getData()).getCustDistance((int)sol.variables().get(vehicle-1).get(j), (int)sol.variables().get(vehicle-1).get(j+1));
						}
									
					//	dist += ((IWSPInstance) getData()).getDistance(i, (int)sol.variables().get(vehicle-1).get(0));
						
						vVariableCost += ((IWSPInstance) getData()).getVehicles().get(vehicle-1).getVariableCost().getCost()*dist;
						
			        }
					
				/**	
					Iterator<Integer> it = vehicles.iterator();
					while(it.hasNext()) {
						
						double dist=0.0;
						for(int j = 0; j < sol.variables().get(it.next()).size()-1; j++) {
							
							dist += ((IWSPInstance) getData()).getCustDistance((int)sol.variables().get(it.next()).get(j), (int)sol.variables().get(it.next()).get(j+1));
						}
									
						dist += ((IWSPInstance) getData()).getDistance(i, (int)sol.variables().get(it.next()).get(0));
						
						vVariableCost += ((IWSPInstance) getData()).getVehicles().get(it.next()).getVariableCost().getCost()*dist;
						
					}**/
				}
			}
		}
		return vVariableCost;
	}

	@Override
	public void evaluateConstraint(IntegerSolution solution) {
		
		double unfitness=0;
		double[] capacityConstraint = new double[((IWSPInstance) getData()).getnVehicles()];
		
		for(int i = 0; i<solution.variables().size(); i++) {
			if(null==solution.variables().get(i))continue;
			for(int j = 0; j<solution.variables().get(i).size();j++) {
				if(0!=solution.variables().get(i).get(j)) {
			 		capacityConstraint[solution.variables().get(i).get(j)-1]+= ((IWSPInstance) getData()).getCustomers().get(j).getDemand();
				}
			}
		}
		
		for(int i = 0; i<capacityConstraint.length; i++) {
			unfitness += Math.max(0, capacityConstraint[i] - ((IWSPInstance) getData()).getVehicle(i).getCapacity());
		}
		solution.getConstraints()[0]= unfitness;
	}
	
	
	@SuppressWarnings("unused")
	public IWSPInstance loadIWSP(String datafilename) throws IOException {
		
		euc = new EuclideanDistanceBetweenTwoLocations(WeightType.CEIL_2D);
		InputStream in = new FileInputStream(datafilename);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        
		String[] tokens = br.readLine().trim().split(" ");
		
		int ncustomers = Integer.parseInt(tokens[0]);
		int nwarehouses = Integer.parseInt(tokens[1]);
		int nVehicles = Integer.parseInt(tokens[2]);
		
		//System.out.println(tokens[1]);
		
		List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < ncustomers; i++) {
        	tokens = br.readLine().trim().split(" ");
            customers.add(new Customer(new location(i, Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1])), Double.parseDouble(tokens[2]), Double.parseDouble(tokens[3]), Integer.toString(i)));
        
        }		
		
		
		List<Facility> warehouses = new ArrayList<>();
		Map<Customer, Cost> distances = new HashMap<Customer, Cost>();
		Map<Vehicle, Cost> vehiclecost = new HashMap<Vehicle, Cost>();
        for (int j = 0; j < nwarehouses; j++) {
        	tokens = br.readLine().trim().split(" ");
        	Inventory inv = new Inventory(new Cost(Double.parseDouble(tokens[4])), new Cost(Double.parseDouble(tokens[5])), Integer.parseInt(tokens[6]), Double.parseDouble(tokens[7]));
            warehouses.add(new Facility(new location(j, Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1])), new Cost(Double.parseDouble(tokens[2])), Double.parseDouble(tokens[3]), distances, vehiclecost,  inv,  Integer.toString(j)));
        }		
		
		
        List<Vehicle> vehicles = new ArrayList<>();
        Map<Customer, Cost> deliverycost = new HashMap<Customer, Cost>();
        for (int k = 1; k <= nVehicles; k++) {
        	tokens = br.readLine().trim().split(" ");
        	vehicles.add(new Vehicle(new Cost(Double.parseDouble(tokens[0])), new Cost(Double.parseDouble(tokens[1])), deliverycost, Double.parseDouble(tokens[2]), Integer.toString(k)));
        }		
		
		for (int i = 0; i < nwarehouses; i++) {
			distances = new HashMap<Customer, Cost>(); 
			Facility fac = warehouses.get(i);
			for (int j = 0; j < ncustomers; j++) {
				distances.put(customers.get(j), new Cost(euc.distance(fac.getLocation(), customers.get(j).getLocation())));
			}
			fac.setDemandCost(distances);
			warehouses.set(i, fac);
        }
		
		IWSPInstance ins = new IWSPInstance(warehouses, customers, vehicles);
		return ins;

	}
	
	public IWSPInstance readProblem(String datafilename) throws IOException {
		if(datafilename==null) {
			System.out.print("Filepath is not found");
			return null;
		}else {
			return loadIWSP(datafilename);
		}
	}


	@Override
	public IntegerSolution createsolution(Map<Integer, List<Integer>> gurobiSol) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
