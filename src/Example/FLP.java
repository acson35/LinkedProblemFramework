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
import main.util.binaryType.BinaryProblemType;
import main.util.createBinary.createBinary;
import main.util.createRandomGeneration.LinkedRandom;
import main.util.model.instance.FLPInstance.Cost;
import main.util.model.instance.FLPInstance.Customer;
import main.util.model.instance.FLPInstance.FLPInstance;
import main.util.model.instance.FLPInstance.Facility;
import main.util.problemName.ProblemName;

@SuppressWarnings("serial")
public class FLP extends AbstractBinaryProblem{

	List<Integer> nFacilities;
	//BinarySolution updatedSolution;
	//List<createBinary> custToFacilityVar;
	//solution<?> parentsolution;
	
	
	
	public FLP(String datafilename) throws IOException {
		setData(readProblem(datafilename));
		setnVariables(((FLPInstance) getData()).getnFacilities());
		setnObjectives(1);
		setnConstraints(2);
		setName("FLP");
		setFilepath(datafilename);
		nFacilities = new ArrayList<>();
		
		
		for(int i=0; i<getnVariables(); i++)
			this.nFacilities.add(((FLPInstance) getData()).getnCustomers());
	}
	
	public FLP(String datafilename, solution<?> solution) throws IOException {
		this(datafilename);
		setParentsolution(solution);
		//this.updatedSolution = updatesolution(solution);
	}

	
	
	
	@Override
	public void evaluate(BinarySolution solution) {
	
		double fitness=0;
		for(int i = 0; i<solution.variables().size(); i++) {
			int count=0;
			double facilityCost = 0.0;
			for(int j = 0; j<solution.variables().get(i).getNumberOfBits();j++) {
				if(solution.variables().get(i).get(j)) {
					facilityCost += ((FLPInstance) getData()).getFacility(i).getDemandCost(((FLPInstance) getData()).getCustomer(j)).getCost();
			 		count++;
				}
			}
			if(count>0) {
				facilityCost += ((FLPInstance) getData()).getFacility(i).getFixedCost().getCost();
				//solution.attributes().put(i, facilityCost);
			}
			fitness += facilityCost;
		}
		//evaluateConstraint(solution);
		solution.getFitness()[0] = Math.round(fitness * 100.0) / 100.0;
	}
	
	@Override
	public void evaluateConstraint(BinarySolution solution) {
		
	
		int[] demandConstraint = new int[((FLPInstance) getData()).getnCustomers()];
		double[] capacityConstraint = new double[((FLPInstance) getData()).getnFacilities()];
		
		for(int i = 0; i<solution.variables().get(0).getNumberOfBits(); i++) {
			for(int j = 0; j<solution.variables().size();j++) {
				if(solution.variables().get(j).get(i)) {
			 		capacityConstraint[j]+= ((FLPInstance) getData()).getCustomers().get(i).getDemand();
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
			unfitness2 += Math.max(0, capacityConstraint[i] - ((FLPInstance) getData()).getFacilities().get(i).getCapacity());
		}
		solution.getConstraints()[0]=unfitness;
		solution.getConstraints()[1]=unfitness2;
	}
	
	@SuppressWarnings("unused")
	public FLPInstance loadFLP(String datafilename) throws IOException {
		
		InputStream in = new FileInputStream(datafilename);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        
		String[] tokens = br.readLine().trim().split(" ");

		int nfacilities = Integer.parseInt(tokens[0]);
		//System.out.print(tokens[0]+"\t");
		int ncustomers = Integer.parseInt(tokens[1]);
		//System.out.println(tokens[1]);
		List<Facility> facilities = new ArrayList<>();
		Map<Customer, Cost> demandcost = new HashMap<Customer, Cost>();
        for (int j = 0; j < nfacilities; j++) {
        	tokens = br.readLine().trim().split(" ");
            facilities.add(new Facility(Double.parseDouble(tokens[0]), new Cost(Double.parseDouble(tokens[1])), demandcost, Integer.toString(j)));
        //    System.out.print(tokens[0]+"\t"+tokens[1]);
        }		
		//tokens = br.readLine().trim().split(" ");
		List<Customer> customers = new ArrayList();
		
		
		int elementsRead = 0;
		while (elementsRead < ncustomers) {
			tokens = br.readLine().trim().split(" ");
			if(tokens.length==0)continue;
			for (int t = 0; t < tokens.length; t++) {
				if(!tokens[t].isEmpty()) {
					customers.add(new Customer(Double.parseDouble(tokens[t]), Integer.toString(elementsRead)));
					elementsRead++;
				//	System.out.print(tokens[t]+"\t");
				}
			}
		}	
		for (int j = 0; j < nfacilities; j++) {
			int elementsRead2 = 0;
			demandcost = new HashMap<Customer, Cost>(); 
			Facility fac = facilities.get(j);
			while(elementsRead2<ncustomers) {
				tokens = br.readLine().trim().split(" ");
				for (int i = 0; i < tokens.length; i++) {
					if(!tokens[i].isEmpty()) {
						demandcost.put(customers.get(elementsRead2), new Cost(Double.parseDouble(tokens[i])));
						elementsRead2++;
					//	System.out.print(tokens[i]+"\t");
					}
				}
			}
			fac.setDemandCost(demandcost);
            
            facilities.set(j, fac);
            
        }
		
		FLPInstance ins = new FLPInstance(facilities, customers);
		return ins;

	}
	
	public FLPInstance readProblem(String datafilename) throws IOException {
		if(datafilename==null) {
			System.out.print("Filepath is not found");
			return null;
		}else {
			return loadFLP(datafilename);
		}
	}
	
//	@Override
//	public BinarySolution createsolution() {
//		 return new BinarySolution(getnVariableBits(), getnConstraints(), getName());
//   }
	
	@Override
	public List<Integer> getnVariableBits() {
		return nFacilities;
	}

//	@Override
//	public BinarySolution getUpdatedSolution() {
		
//		return updatedSolution;
//	}

}
