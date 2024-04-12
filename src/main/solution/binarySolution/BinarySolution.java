package main.solution.binarySolution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.solution.AbstractSolution;
import main.solution.solution;
import main.solution.integerSolution.IntegerSolution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.binaryType.BinaryProblemType;
import main.util.createBinary.createBinary;
import main.util.createRandomGeneration.LinkedRandom;
import main.util.distance.DistImplementation.EuclideanDistanceBetweenTwoLocations.WeightType;
import main.util.model.instance.FLPInstance.FLPInstance;
import main.util.model.instance.IWSPInstance.IWSPInstance;
import main.util.model.instance.SFLPInstance.SFLPInstance;
import main.util.problemName.ProblemName;

/**
 * This defines an implementation of a binary solution. These solutions are composed of a number
 * of variables containing {@link createBinary} objects.
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 * 

 * @author Akinola Ogunsemi <a.ogunsemi@rgu.ac.uk>
 * 
 */
//AUG2021 - Akinola - Modified to include attributes defined for a linked optimisation.

@SuppressWarnings("serial")
public class BinarySolution extends AbstractSolution<createBinary> implements solution<createBinary>{

	protected List<Integer> bitsNum;
	protected String solutionType = "BINARY";
	protected static Object object;
	protected int size;

	public BinarySolution(List<Integer> bitsNum,int nObj, int nConstraints, String name, Object object) {
		super(bitsNum.size(), nObj, nConstraints, name);
		this.bitsNum = bitsNum;
		this.object = object;
		initializeBinarySolutions(LinkedRandom.getInstance());
	}
	
	
	public BinarySolution(List<Integer> bitsNum, String name, Object object) {
		this(bitsNum, 0, 0, name, object);
		this.object = object;
	}
	
	public BinarySolution(int nObj, int nConstraints, String name, List<createBinary> variables, Object object) {
		this(variableSize(variables), nObj, nConstraints, name, object);
		//updateSolutions(solution, LinkedRandom.getInstance());
		this.object = object;
		for (int i = 0; i < variables().size(); i++) {
		     variables().set(i, (createBinary) variables.get(i).clone());
		}
	}
	
	public BinarySolution(List<Integer> var, int nObj, int nConstraints, String name, Object object, Map<Integer, List<Integer>> gurobiSol) {
	//	this(((IWSPInstance) object).getnFacilities(), nObj, nConstraints, name, object);
		super(var.size(), nObj, nConstraints, name);
		this.size = var.size();
		this.object = object;
		createSolutionFromGurobi(gurobiSol);
	}
	
	public BinarySolution(BinarySolution solution) {
	   super(solution.variables().size(), solution.getFitness().length,  solution.getConstraints().length, solution.problemname());

	   this.bitsNum = solution.bitsNum;
	   this.object = solution.object;

	   for (int i = 0; i < variables().size(); i++) {
	     variables().set(i, (createBinary) solution.variables().get(i).clone());
	   }
	   
	   for (int i = 0; i < getFitness().length; i++) {
		   getFitness()[i]=solution.getFitness()[i];
		}
	   
	   for (int i = 0; i < getConstraints().length; i++) {
		  getConstraints()[i]=solution.getConstraints()[i];
	   }
	   
	   problemname = solution.problemname();
	 }

	


	public void createSolutionFromGurobi(Map<Integer, List<Integer>> gurobiSol) {
		int nCustomers = ((IWSPInstance) object).getnCustomers();
		int nFacilities = ((IWSPInstance) object).getnFacilities();
		//List<createBinary> sol = (List<createBinary>) new createBinary(bitsNum.size());
		for(int i = 0; i<nFacilities;i++) {
			createBinary bits = new createBinary(nCustomers);
			if(gurobiSol.containsKey(i)) {
				List<Integer> cust = gurobiSol.get(i);
				for(int j=0; j<bits.size(); j++) {
					if(cust.contains(j)) {
						bits.set(j);
					}
				}
			}
			
			variables().set(i, bits);
		}	
		
	}
	
	 public static createBinary createBinarySet(int numberOfBits, LinkedRandom generator) {
	    createBinary binaryset = new createBinary(numberOfBits);

	    for (int i = 0; i < numberOfBits; i++) {
	      double randomvalue = generator.nextDouble();
	      if (randomvalue < 0.5) {
	        binaryset.set(i);
	      } else {
	        binaryset.clear(i);
	      }
	    }
	    return binaryset;
	  }
	 
	
	
	@Override
	public solution<createBinary> copy() {
		 return new BinarySolution(this);
	}

	
	public int getBitSize(int index) {
		return variables().get(index).getNumberOfBits();
	}

	
	public int getTotalNumberOfBits() {
		int total = 0;
	    for (int i = 0; i < variables().size(); i++) {
	      total += variables().get(i).getNumberOfBits();
	    }

	    return total;
	}
	
	
	private void initializeBinarySolutions(LinkedRandom generator) {
		
		createBinary createbinary = createBinarySet(bitsNum.size(), generator);
		
	    if (problemname.equalsIgnoreCase("KP")){
	    	for(int i=0; i<bitsNum.size(); i++) {
	    		variables().set(i, createBinarySet(bitsNum.get(i), generator));
	    	}
		}else if (problemname.equalsIgnoreCase("FLP")){
			//custToFacilityVar(createbinary);
			List<createBinary> custToFacilityVar = CustomerAllocationToFacility(createbinary);
			for(int i = 0; i<bitsNum.size();i++) {
				createBinary bits = new createBinary(custToFacilityVar.size());
				for(int j = 0; j<custToFacilityVar.size();j++) {
					if(custToFacilityVar.get(j).get(i)) {
						bits.set(j);
					}
				}
				variables().set(i, bits);
			}	
		}else if (problemname.equalsIgnoreCase("IWSP")){
			//custToFacilityVar(createbinary);
			List<createBinary> custToFacilityVar = CustomerAllocationToFacility2(createbinary);
			for(int i = 0; i<bitsNum.size();i++) {
				createBinary bits = new createBinary(custToFacilityVar.size());
				for(int j = 0; j<custToFacilityVar.size();j++) {
					if(custToFacilityVar.get(j).get(i)) {
						bits.set(j);
					}
				}
				variables().set(i, bits);
			}	
		}else if (problemname.equalsIgnoreCase("SFLP")){
			//custToFacilityVar(createbinary);
			List<createBinary> custToFacilityVar = CustomerAllocationToFacility3(createbinary);
			for(int i = 0; i<bitsNum.size();i++) {
				createBinary bits = new createBinary(custToFacilityVar.size());
				for(int j = 0; j<custToFacilityVar.size();j++) {
					if(custToFacilityVar.get(j).get(i)) {
						bits.set(j);
					}
				}
				variables().set(i, bits);
			}	
		}else{
			for(int i=0; i<bitsNum.size(); i++) {
	    		variables().set(i, createBinarySet(bitsNum.get(i), generator));
	    	}	//throw new RuntimeException("Unsupported type of binary problem.");
		} 
	 }
	
	public List<createBinary> CustomerAllocationToFacility(createBinary selectedFacilities) {
		List<createBinary> custToFacilityVar = new ArrayList<>();
		int nCustomers = ((FLPInstance) object).getnCustomers();
		int nFacilities = ((FLPInstance) object).getnFacilities();
		for(int i=0;i<nCustomers;i++) {
			int temp = 0;
			double custCost = Double.MAX_VALUE;
			createBinary bin = new createBinary(nFacilities);
			for(int j=0;j<nFacilities;j++) {
				if(!selectedFacilities.get(j))continue;
				//System.out.println(custCost);
				double custCost1 = ((FLPInstance) object).getFacility(j).getDemandCost(((FLPInstance) object).getCustomer(i)).getCost();
				if(custCost>custCost1) {
					custCost = custCost1;
					temp = j;
				}
			}
			bin.set(temp);	
			custToFacilityVar.add(bin);
		}
		return custToFacilityVar;
	}
	
	public List<createBinary> CustomerAllocationToFacility2(createBinary selectedFacilities) {
		List<createBinary> custToFacilityVar = new ArrayList<>();
		int nCustomers = ((IWSPInstance) object).getnCustomers();
		int nFacilities = ((IWSPInstance) object).getnFacilities();
		for(int i=0;i<nCustomers;i++) {
			int temp = 0;
			double custCost = Double.MAX_VALUE;
			createBinary bin = new createBinary(nFacilities);
			for(int j=0;j<nFacilities;j++) {
				if(!selectedFacilities.get(j))continue;
				//System.out.println(custCost);
				double custCost1 = ((IWSPInstance) object).getDistance(j, i);
				if(custCost>custCost1) {
					custCost = custCost1;
					temp = j;
				}
			}
			bin.set(temp);	
			custToFacilityVar.add(bin);
		}
		return custToFacilityVar;
	}
	
	public List<createBinary> CustomerAllocationToFacility3(createBinary selectedFacilities) {
		List<createBinary> custToFacilityVar = new ArrayList<>();
		int nCustomers = ((SFLPInstance) object).getnCustomers();
		int nFacilities = ((SFLPInstance) object).getnFacilities();
		for(int i=0;i<nCustomers;i++) {
			int temp = 0;
			double custCost = Double.MAX_VALUE;
			createBinary bin = new createBinary(nFacilities);
			for(int j=0;j<nFacilities;j++) {
				if(!selectedFacilities.get(j))continue;
				//System.out.println(custCost);
				double custCost1 = ((SFLPInstance) object).getDistance(j, i);
				if(custCost>custCost1) {
					custCost = custCost1;
					temp = j;
				}
			}
			bin.set(temp);	
			custToFacilityVar.add(bin);
		}
		return custToFacilityVar;
	}
	
	public void custToFacilityVar(createBinary createbinary){
		
		List<createBinary> custToFacilityVar = new ArrayList<>();
		for(int i = 0; i<bitsNum.get(0); i++) {
			int nBits = bitsNum.get(0);
			createBinary bin = new createBinary(nBits);
			double randomvalue = LinkedRandom.getInstance().nextDouble();
			int position = (int)(randomvalue*nBits);
			while(!createbinary.get(position)) {
				  randomvalue = LinkedRandom.getInstance().nextDouble();
				  position = (int)(randomvalue*nBits);
			}
			bin.set(position);	
			custToFacilityVar.add(bin);
		}
		for(int i = 0; i<bitsNum.size();i++) {
			createBinary bits = new createBinary(custToFacilityVar.size());
			for(int j = 0; j<custToFacilityVar.size();j++) {
				if(custToFacilityVar.get(j).get(i)) {
					bits.set(j);
				}
			}
			variables().set(i, bits);
		}	
	}
	
	
	@Override
	 public String getSolutionType() {
		return solutionType;
	 }
	
	public static List<Integer> variableSize(List<createBinary> variables) {
		List<Integer>size = new ArrayList<>();
		for(int i=0; i<variables.size(); i++) {
			createBinary binaryset = (createBinary) variables.get(i);
			size.add(binaryset.length());
		}
		return size;
	}
	
	public Object getObject(){
		return object;
	}
	
}
