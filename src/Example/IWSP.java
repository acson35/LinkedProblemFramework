package Example;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.problem.binaryProblem.BinaryImplementation.AbstractBinaryProblem;
import main.solution.solution;
import main.solution.binarySolution.BinarySolution;
import main.solution.integerSolution.IntegerSolution;
import main.util.distance.DistImplementation.EuclideanDistanceBetweenTwoLocations;
import main.util.distance.DistImplementation.EuclideanDistanceBetweenTwoLocations.WeightType;
import main.util.model.instance.QAPInstance;
import main.util.model.instance.location;
import main.util.model.instance.FLPInstance.Cost;
import main.util.model.instance.FLPInstance.Customer;
import main.util.model.instance.FLPInstance.FLPInstance;
import main.util.model.instance.FLPInstance.Facility;
import main.util.model.instance.IWSPInstance.IWSPInstance;
import main.util.model.instance.IWSPInstance.Inventory;
import main.util.model.instance.IWSPInstance.Vehicle;

public class IWSP extends AbstractBinaryProblem {

	List<Integer> nWarehouses;
	EuclideanDistanceBetweenTwoLocations euc = 
			new EuclideanDistanceBetweenTwoLocations(WeightType.CEIL_2D);
	solution<?> solution;
	
	public IWSP(String datafilename) throws IOException {
		setData(readProblem(datafilename));
		setnVariables(((IWSPInstance) getData()).getnFacilities());
		setnObjectives(1);
		setnConstraints(2);
		setName("IWSP");
		setFilepath(datafilename);
		nWarehouses = new ArrayList<>();
		
		
		for(int i=0; i<getnVariables(); i++)
			this.nWarehouses.add(((IWSPInstance) getData()).getnCustomers());
	}
	
	public IWSP(String datafilename, solution<?> solution) throws IOException {
		this(datafilename);
		setParentsolution(solution);
		//this.updatedSolution = updatesolution(solution);
	}
	
	//public IWSP(String datafilename, BinarySolution sol, solution<?> solution) throws IOException {
		//this(datafilename);
		//setParentsolution(solution);
		//this.updatedSolution = updatesolution(solution);
	//}
	
	@Override
	public void evaluate(BinarySolution solution) {

		double fitness=0;
		
		fitness += totalFixedCost(solution);
		fitness += inventoryPolicy(solution);
		fitness += totalTransportCost(solution);
		evaluateConstraint(solution);
		solution.getFitness()[0] = Math.round(fitness * 100.0) / 100.0;
		
	}
	
	public double totalFixedCost(BinarySolution solution) {
		double facilityCost = 0.0;
		for(int i = 0; i<solution.variables().size(); i++) {
			if(solution.variables().get(i).cardinality()>0){
				facilityCost += ((IWSPInstance) getData()).getFacility(i).getFixedCost().getCost();
			}
		}
		return facilityCost;
	}

	@Override
	public void evaluateConstraint(BinarySolution solution) {
		
		int[] demandConstraint = new int[((IWSPInstance) getData()).getnCustomers()];
		double[] capacityConstraint = new double[((IWSPInstance) getData()).getnFacilities()];
		
		for(int i = 0; i<solution.variables().get(0).getNumberOfBits(); i++) {
			for(int j = 0; j<solution.variables().size();j++) {
				if(solution.variables().get(j).get(i)) {
			 		capacityConstraint[j]+= ((IWSPInstance) getData()).getCustomers().get(i).getDemand();
			 		demandConstraint[i]+= 1;
				}
			}
		}
		double unfitness=0;
		double unfitness2=0;
		for(int i = 0; i<demandConstraint.length; i++) {
			unfitness += Math.max(0, demandConstraint[i] - 1);
		}
		for(int i = 0; i<capacityConstraint.length; i++) {
			unfitness2 += Math.max(0, capacityConstraint[i] - ((IWSPInstance) getData()).getFacilities().get(i).getCapacity());
		}
		solution.getConstraints()[0]=unfitness;
		solution.getConstraints()[1]=unfitness2;
	}
	
	public double inventoryPolicy(BinarySolution solution) {
		
		double inventorycost = 0.0;
		for(int i = 0; i<solution.variables().size(); i++) {
			if(solution.variables().get(i).cardinality()==0)continue;
			double inventoryCostperWarehouse = 0.0;
			double totalDemands=0.0;
			double totalDemandVariance=0.0;
			for(int j = 0; j<solution.variables().get(i).getNumberOfBits();j++) {
				if(solution.variables().get(i).get(j)) {
					totalDemands += ((IWSPInstance) getData()).getCustomer(j).getDemand();
					totalDemandVariance += ((IWSPInstance) getData()).getCustomer(j).getDemandVariance();
				}
			}
			Inventory inv = ((IWSPInstance) getData()).getFacility(i).getInventory();
			
			double holdingCost = 2*(totalDemands)*(inv.getHoldingCost().getCost())*inv.getOrderingCost().getCost();
			double replenishmentCost = inv.getZScore()*(inv.getHoldingCost().getCost())*(Math.sqrt(inv.getLeadTime()))*(Math.sqrt(totalDemandVariance));
			inventoryCostperWarehouse = Math.sqrt(holdingCost) + replenishmentCost;
			inventorycost += inventoryCostperWarehouse;
			//System.out.println(inv.getZScore() + inv.getHoldingCost().getCost() + totalDemandVariance + inv.getLeadTime());
		}
		
		return inventorycost;
	}
	
	//public void setParentSolution(solution<?> solution) {
	//	setParentsolution(solution);
	//}
	
	public double totalTransportCost(BinarySolution solution) {
		
		double fitness=0;
		if(null!=parentsolution()) {
			if(parentsolution().problemname().equalsIgnoreCase("VAP")) {
				IntegerSolution sol = (IntegerSolution) parentsolution();
				fitness = sol.getFitness()[0];
			}
		}
		return fitness;
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
        //	System.out.println(Double.parseDouble(tokens[4]) +" "+ Double.parseDouble(tokens[5]) +" "+ Integer.parseInt(tokens[6]) +" "+ Double.parseDouble(tokens[7]));
            warehouses.add(new Facility(new location(j, Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1])), new Cost(Double.parseDouble(tokens[2])), Double.parseDouble(tokens[3]), distances, vehiclecost,  inv,  Integer.toString(j)));
        }		
		
		
        List<Vehicle> vehicles = new ArrayList<>();
        Map<Customer, Cost> deliverycost = new HashMap<Customer, Cost>();
        for (int k = 1; k <= nwarehouses; k++) {
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
	public List<Integer> getnVariableBits() {
		// TODO Auto-generated method stub
		return nWarehouses;
	}

	
}
