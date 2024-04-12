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
import main.util.model.instance.location;
import main.util.model.instance.FLPInstance.Cost;
import main.util.model.instance.FLPInstance.Customer;
import main.util.model.instance.FLPInstance.FLPInstance;
import main.util.model.instance.FLPInstance.Facility;
import main.util.model.instance.IWSPInstance.IWSPInstance;
import main.util.model.instance.IWSPInstance.Inventory;
import main.util.model.instance.IWSPInstance.Vehicle;
import main.util.model.instance.SFLPInstance.EnergyConsumption;
import main.util.model.instance.SFLPInstance.SFLPInstance;

@SuppressWarnings("serial")
public class SFLP extends AbstractBinaryProblem {

	List<Integer> nFacilities;
	EuclideanDistanceBetweenTwoLocations euc = 
			new EuclideanDistanceBetweenTwoLocations(WeightType.CEIL_2D);
	solution<?> solution;
	
	public SFLP(String datafilename) throws IOException {
		setData(readProblem(datafilename));
		setnVariables(((SFLPInstance) getData()).getnFacilities());
		setnObjectives(2);
		setnConstraints(2);
		setName("SFLP");
		setFilepath(datafilename);
		nFacilities = new ArrayList<>();
		
		
		for(int i=0; i<getnVariables(); i++)
			this.nFacilities.add(((SFLPInstance) getData()).getnCustomers());
	}
	
	public SFLP(String datafilename, solution<?> solution) throws IOException {
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
		double fitness1=0;
		
		fitness += totalFixedCost(solution);
		fitness += totalTransportCost(solution);
		
		fitness1 += transportEmission(solution);
		fitness1 += energyConsumptionCost(solution);
		evaluateConstraint(solution);
		solution.getFitness()[0] = Math.round(fitness * 100.0) / 100.0;
		solution.getFitness()[1] = Math.round(fitness1 * 100.0) / 100.0;
	}
	
	public double totalFixedCost(BinarySolution solution) {
		double facilityCost = 0.0;
		for(int i = 0; i<solution.variables().size(); i++) {
			if(solution.variables().get(i).cardinality()>0){
				facilityCost += ((SFLPInstance) getData()).getFacility(i).getFixedCost().getCost();
			}
		}
		return facilityCost;
	}
	
	public double energyConsumptionCost(BinarySolution solution) {
		double facilityCost = 0.0;
		for(int i = 0; i<solution.variables().size(); i++) {
			if(solution.variables().get(i).cardinality()>0){
				facilityCost += ((SFLPInstance) getData()).getFacility(i).getEnergyConsumption().getEnergyCost().getCost();
			}
		}
		return facilityCost;
	}

	@Override
	public void evaluateConstraint(BinarySolution solution) {
		
		int[] demandConstraint = new int[((SFLPInstance) getData()).getnCustomers()];
		double[] capacityConstraint = new double[((SFLPInstance) getData()).getnFacilities()];
		
		for(int i = 0; i<solution.variables().get(0).getNumberOfBits(); i++) {
			for(int j = 0; j<solution.variables().size();j++) {
				if(solution.variables().get(j).get(i)) {
			 		capacityConstraint[j]+= ((SFLPInstance) getData()).getCustomers().get(i).getDemand();
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
			unfitness2 += Math.max(0, capacityConstraint[i] - ((SFLPInstance) getData()).getFacilities().get(i).getCapacity());
		}
		solution.getConstraints()[0]=unfitness;
		solution.getConstraints()[1]=unfitness2;
	}
	
	public double transportEmission(BinarySolution solution) {
		
		double fitness=0;
		for(int i = 0; i<solution.variables().size(); i++) {
			int count=0;
			double facilityCost = 0.0;
			for(int j = 0; j<solution.variables().get(i).getNumberOfBits();j++) {
				if(solution.variables().get(i).get(j)) {
					facilityCost += ((SFLPInstance) getData()).getTransportEmissionCost(i,j);
			 		count++;
				}
			}
			fitness += facilityCost;
		}
		
		return fitness;
	}
	
	public void setParentSolution(solution<?> solution) {
		setParentsolution(solution);
	}
	
	public double totalTransportCost(BinarySolution solution) {
		
		double fitness=0;
		for(int i = 0; i<solution.variables().size(); i++) {
			int count=0;
			double facilityCost = 0.0;
			for(int j = 0; j<solution.variables().get(i).getNumberOfBits();j++) {
				if(solution.variables().get(i).get(j)) {
					facilityCost += ((SFLPInstance) getData()).getFacility(i).getDemandCost(((SFLPInstance) getData()).getCustomer(j)).getCost();
			 		count++;
				}
			}
			fitness += facilityCost;
		}
		return fitness;
	}
	
	@SuppressWarnings("unused")
	public SFLPInstance loadSFLP(String datafilename) throws IOException {
		
		euc = new EuclideanDistanceBetweenTwoLocations(WeightType.CEIL_2D);
		
		InputStream in = new FileInputStream(datafilename);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        
		String[] tokens = br.readLine().trim().split(" ");
		int nfacilities = Integer.parseInt(tokens[0]);
		tokens = br.readLine().trim().split(" ");
		int ncustomers = Integer.parseInt(tokens[0]);
		//int nVehicles = Integer.parseInt(tokens[2]);
		//System.out.println(nfacilities);
		//System.out.println(ncustomers);
		
		List<location> facilityloc = new ArrayList<>();
		for (int i = 0; i < nfacilities; i++) {
        	tokens = br.readLine().trim().split(" ");
        	facilityloc.add(new location(i, Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1])));
        }
		
		List<location> custloc = new ArrayList<>();
		for (int i = 0; i < ncustomers; i++) {
        	tokens = br.readLine().trim().split(" ");
        	custloc.add(new location(i, Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1])));
        }
		
		//List<Facility> warehouses = new ArrayList<>();
		
		List<Cost> fixedcost = new ArrayList<>();
		tokens = br.readLine().trim().split(" ");
		for (int i = 0; i < nfacilities; i++) {
			fixedcost.add(new Cost(Double.parseDouble(tokens[i])));
        }
		
		
		List<Double> capacity = new ArrayList<>();
		tokens = br.readLine().trim().split(" ");
		for (int i = 0; i < nfacilities; i++) {
			capacity.add(Double.parseDouble(tokens[i]));
        }
		
		//System.out.println(capacity.toString());
		
		List<Customer> customerdemand = new ArrayList<>();
		
        int elementsRead = 0;
		while (elementsRead < ncustomers) {
			tokens = br.readLine().trim().split(" ");
			if(tokens.length==0)continue;
			for (int t = 0; t < tokens.length; t++) {
				if(!tokens[t].isEmpty()) {
					customerdemand.add(new Customer(custloc.get(elementsRead), Double.parseDouble(tokens[t]), Integer.toString(elementsRead)));
					elementsRead++;
				}
			}
		}
        
		List<EnergyConsumption> energy = new ArrayList<>();
		Map<Customer, Cost> demandcost = new HashMap<Customer, Cost>();
		Map<Customer, Cost> transportEmmisionCost = new HashMap<Customer, Cost>();
		List<Facility> facilities = new ArrayList<>();
		
		tokens = br.readLine().trim().split(" ");
        for (int j = 0; j < tokens.length; j++) {
        	//energy.add(new EnergyConsumption(new Cost(Double.parseDouble(tokens[j]))));
        	facilities.add(new Facility(facilityloc.get(j), fixedcost.get(j), capacity.get(j), demandcost, transportEmmisionCost, new EnergyConsumption(new Cost(Double.parseDouble(tokens[j]))), Integer.toString(j)));	
        }	
        
        SFLPInstance ins = new SFLPInstance(facilities, customerdemand);
        
        for (int i = 0; i < ncustomers; i++) {
        	
        	tokens = br.readLine().trim().split(" ");
			for (int j = 0; j < tokens.length; j++) {
				
				Facility fac = facilities.get(j);
				if(fac.getCustomersDemandCost().isEmpty() && fac.getTransportEmission().isEmpty()) {
					demandcost = new HashMap<Customer, Cost>();
					transportEmmisionCost = new HashMap<Customer, Cost>();
					demandcost.put(customerdemand.get(i), new Cost(ins.getDistance(j, i)));
					transportEmmisionCost.put(customerdemand.get(i), new Cost(Double.parseDouble(tokens[j])));
					
				}else {
					demandcost = fac.getCustomersDemandCost();
					transportEmmisionCost = fac.getTransportEmission();
					demandcost.put(customerdemand.get(i), new Cost(ins.getDistance(j, i)));
					transportEmmisionCost.put(customerdemand.get(i), new Cost(Double.parseDouble(tokens[j])));
				}
				
				fac.setDemandCost(demandcost);
				fac.setTransportEmissionCost(transportEmmisionCost);
				facilities.set(j, fac);	
			}
        }
        
        ins = new SFLPInstance(facilities, customerdemand);
		return ins;

	}
	
	public SFLPInstance readProblem(String datafilename) throws IOException {
		if(datafilename==null) {
			System.out.print("Filepath is not found");
			return null;
		}else {
			return loadSFLP(datafilename);
		}
	}

	@Override
	public List<Integer> getnVariableBits() {
		// TODO Auto-generated method stub
		return nFacilities;
	}
	
	
}
