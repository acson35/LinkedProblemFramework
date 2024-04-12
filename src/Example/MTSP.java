package Example;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.problem.permutationProblem.Implementation.AbstractIntegerPermutationProblem;
import main.solution.solution;
import main.solution.integerSolution.IntegerSolution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.distance.DistImplementation.EuclideanDistanceBetweenTwoLocations;
import main.util.distance.DistImplementation.EuclideanDistanceBetweenTwoLocations.WeightType;
import main.util.model.instance.QAPInstance;
import main.util.model.instance.location;
import main.util.model.instance.FLPInstance.Cost;
import main.util.model.instance.FLPInstance.Customer;
import main.util.model.instance.FLPInstance.Facility;
import main.util.model.instance.IWSPInstance.IWSPInstance;
import main.util.model.instance.IWSPInstance.Inventory;
import main.util.model.instance.IWSPInstance.Vehicle;

public class MTSP extends AbstractIntegerPermutationProblem{

	
	private List<Integer> nCities;
	WeightType edgeWeightType;
	EuclideanDistanceBetweenTwoLocations euc;
	
	
	@SuppressWarnings("unchecked")
	public MTSP (String datafilename)  throws IOException {
		setnVariables(1);
		setnObjectives(1);
		setName("MTSP");
		setFilepath(datafilename);
		setData(readProblem(datafilename));
		this.nCities = new ArrayList<>();
		this.nCities.add(((IWSPInstance) getData()).getnCustomers());
	}
	
	public MTSP ()  throws IOException {
		
	}
	
	public MTSP (String datafilename, solution<?> solution)  throws IOException {
		this(datafilename);
		setParentsolution(solution);
		//this.updatedSolution = updatesolution(solution);	
	}
	
	
	@Override
	public void evaluate(IntegerPermutationSolution solution) {
		double fitness =0;
		EuclideanDistanceBetweenTwoLocations euc = 
				new EuclideanDistanceBetweenTwoLocations(WeightType.CEIL_2D);
		
		if(parentsolution()!=null) {
			
			if(parentsolution().problemname().equals("VAP")){
				IntegerSolution sol =  (IntegerSolution) parentsolution();
				//double dist=0.0;
				for(int i = 0; i<sol.variables().size(); i++) {
					if(null==sol.variables().get(i))continue;
					Set<Integer> vehicles = new HashSet<Integer>(sol.variables().get(i));
					if(vehicles.contains(0))vehicles.remove(0);
					for (Integer vehicle : vehicles){
						for(int j = 0; j < solution.variables().get(vehicle-1).size()-1; j++) {
							fitness += ((IWSPInstance) solution.getObject()).getCustDistance((int)solution.variables().get(vehicle-1).get(j), (int)solution.variables().get(vehicle-1).get(j+1));
						}	
						int last = solution.variables().get(vehicle-1).size()-1;
						fitness += ((IWSPInstance) solution.getObject()).getDistance(i, (int)solution.variables().get(vehicle-1).get(0));
						fitness += ((IWSPInstance) solution.getObject()).getDistance(i, (int)solution.variables().get(vehicle-1).get(last));
			        }
				//	Iterator<Integer> it = vehicles.iterator();
				//	while(it.hasNext()) {	
				//	}
				}
			}
		}else {
			for(int i=0; i<solution.variables().size(); i++) {
				if(null==solution.variables().get(i))continue;
				if(solution.variables().get(i).isEmpty())continue;
				List<Integer> subcities = solution.variables().get(i);
				
				for (int j = 0; j < subcities.size()-1; j++) {
					//double value = 0.0;
				    int x = (int)solution.variables().get(i).get(j) ;
				    int y = (int)solution.variables().get(i).get(j+1) ;
				    fitness += euc.distance(((List<location>) getData()).get(x), ((List<location>) getData()).get(y)) ;
				  //  fitness +=value;
				   // subfitness.add(value);
				} 
				//solution.attributes().put(i, subfitness);
				int firstLocation = (int)solution.variables().get(i).get(0);
				int lastLocation = (int)solution.variables().get(i).get(subcities.size()-1);
			    fitness += euc.distance(((List<location>) getData()).get(lastLocation), ((List<location>) getData()).get(firstLocation));
			}
		}
	    solution.getFitness()[0] = fitness;		
	}

	@Override
	public void evaluateConstraint(IntegerPermutationSolution solution) {
		// TODO Auto-generated method stub
		
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
	public List<Integer> getnSubVariables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IntegerPermutationSolution createsolution(Map<Integer, List<Integer>> gurobiSol) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
