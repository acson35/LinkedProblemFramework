package main.util.factory.Instatiator.ChangeSolution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.solution.solution;
import main.solution.binarySolution.BinarySolution;
import main.solution.integerSolution.IntegerSolution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.adjacencymatrix.AdjacencyMatrixLinkedProblem;
import main.util.createBinary.createBinary;
import main.util.createRandomGeneration.LinkedRandom;
import main.util.factory.Instatiator.AbstractSolutionInstatiator;
import main.util.factory.Instatiator.SolutionInstantiator;
import main.util.range.Ranges;

public class ChangeIntegerSolution extends AbstractSolutionInstatiator<List<Integer>> implements SolutionInstantiator<List<Integer>> {
	solution<?> solution;
	List<Ranges<Integer>> ranges;
	public ChangeIntegerSolution(int nVariables) {
		super(nVariables);
	}
	
	public ChangeIntegerSolution(solution<?> solution, List<Ranges<Integer>> rangesList) {
		this(size(solution).size());
		this.solution = solution;
		this.ranges = rangesList;
		setsolution();
	}

	@Override
	public void setsolution() {
		if(solution.getSolutionType().equalsIgnoreCase("BINARY")) {
			BinarySolution sol = (BinarySolution) solution;
			if(sol.problemname().equalsIgnoreCase("FLP")){
				
				List<Integer> agentsToFacility = agentsToFacility(sol);
				
				for(int i = 0; i<sol.variables().size(); i++) {
					agentsToCustomer(i, sol.variables().get(i), agentsToFacility);
				}
			}else if(sol.problemname().equalsIgnoreCase("IWSP")){
				
				List<Integer> agentsToFacility = agentsToFacility(sol);
				
				for(int i = 0; i<sol.variables().size(); i++) {
					agentsToCustomer(i, sol.variables().get(i), agentsToFacility);
				}
			}else {
				for (int i = 0; i < sol.variables().get(0).getNumberOfBits(); i++) {
					if(sol.variables().get(0).get(i)) {
						variables().get(0).set(i, createSolution(getRange(i)));
					}
				}
			}	
		}else if(solution.getSolutionType().equalsIgnoreCase("INTEGERPERMUTATION")) {
			IntegerPermutationSolution sol = (IntegerPermutationSolution) solution;
			List<List<Integer>> temp = variables();
			for(int i=0; i<((List<Integer>) sol.variables().get(0)).size(); i++) {
				int val = (int) sol.variables().get(0).get(i);
				variables().get(0).set(i, (Integer) temp.get(0).get(val));
			}
		}else if(solution.getSolutionType().equalsIgnoreCase("INTEGER")) {
			IntegerSolution sol = (IntegerSolution) solution;
			for(int i=0; i <sol.variables().size(); i++) {
				variables().set(i, sol.variables().get(i));
			}
		}else if(solution.getSolutionType().equalsIgnoreCase("SEQUENCE")) {
			
		}
	}
	
	public Integer getLowerRange(int ind) {
		return this.ranges.get(ind).getLowerRange();
	}


	public Integer getUpperRange(int ind) {
		return this.ranges.get(ind).getUpperRange();
	}
	
	public Ranges<Integer> getRange(int index) {
	    Integer lowerRange = this.getLowerRange(index);
	    Integer upperRange = this.getUpperRange(index);
	    return Ranges.create(lowerRange, upperRange);
	}
	
	public int createSolution(Ranges<Integer> range) {
		int agent = LinkedRandom.getInstance().nextInt(range.getLowerRange(), range.getUpperRange());
		return agent;
	}
	
	

	public List<Integer> agentsToFacility(BinarySolution sol){
		
		List<Integer> agents = new ArrayList<>();
		List<Integer> agentFacility = new ArrayList<>();
		
		for(int i=0; i<ranges.get(0).getUpperRange(); i++)
			agentFacility.add(null);
		
		for(int i=0; i<ranges.get(0).getUpperRange(); i++)
			agents.add(i);
		
		List<Integer> openedFacility = new ArrayList<>();
		
		for(int i=0; i<sol.variables().size(); i++) {
			if(sol.variables().get(i).cardinality()>0) {
				openedFacility.add(i);
			}
		}
	/**	Ranges<Integer> range = new Ranges<Integer>(0, openedFacility.size()-1);
		while(!agents.isEmpty()) {
			Ranges<Integer> rangeAgents = new Ranges<Integer>(0, agents.size()-1);
			int facility = createSolution(range);
			int agent = createSolution(rangeAgents);
			agentFacility.set(agents.get(agent), openedFacility.get(facility));
			agents.remove(agent);
		}	**/
		java.util.Collections.shuffle(openedFacility);
		while(!agents.isEmpty()) {
			for(int i=0; i<openedFacility.size(); i++) {
				Ranges<Integer> rangeAgents = new Ranges<Integer>(0, agents.size()-1);
				if(agents.isEmpty())break;
				int agent = createSolution(rangeAgents);
				agentFacility.set(agents.get(agent), openedFacility.get(i));
				agents.remove(agent);
			} 
		}
		
		
		//Ranges<Integer> range = new Ranges<Integer>(0, sol.variables().size()-1);
		//createBinary createbinary = sol.createBinarySet(getUpperRange(0), LinkedRandom.getInstance());
		//List<Boolean> assigned = new ArrayList<Boolean>();
		//for(int i=0; i<ranges.get(0).getUpperRange(); i++) 
		//	assigned.add(false);
		
	//	Ranges<Integer> range = new Ranges<Integer>(0, 1);
	/**	for(int i=0; i<sol.variables().size(); i++) {
			if(sol.variables().get(i).cardinality()<1)continue;
			for(int j=0; j<agentsToFacility.size(); j++) {
				if(agentsToFacility.get(j)!=null)continue;
				int agent = createSolution(range);
				if(agent==1) {
					agentsToFacility.set(j, i);
				}	
			}	
		}  **/
		
	/**	for(int i=0; i<agentsToFacility.size(); i++) {
			for(int j=0; j<sol.variables().size(); j++) {
				if(agentsToFacility.get(i)!=null)break;
				if(sol.variables().get(j).cardinality()<1)continue;
				int agent = createSolution(range);
				if(agent==1) {
					agentsToFacility.set(i, j);
				}	
			}	
		}  **/
		
		//System.out.println(agentFacility);
	//	for(int i=0; i<ranges.get(0).getUpperRange(); i++) {
	//		int facility = createSolution(range);
	//		while(sol.variables().get(facility).cardinality()<1) {
	//			facility = createSolution(range);
	//		}
	//		agentsToFacility.add(facility);
	//	}
		return agentFacility;
	}
	
	public void agentsToCustomer(int i, createBinary binaryset, List<Integer> agentsToFacility) {
		
		List<Integer> agents = new ArrayList<>();
		for(int j = 0; j < agentsToFacility.size(); j++) {
			if(agentsToFacility.get(j)==null)continue;
			if(agentsToFacility.get(j)==i)
				agents.add(j+1);
		}
		int size = agents.size();
		Ranges<Integer> range = new Ranges<Integer>(0, size-1);
		List<Integer> customers = new ArrayList<>();
		if(size!=0) {
			for(int k = 0; k < binaryset.getNumberOfBits(); k++) {
				if(binaryset.get(k)) {
					int agentPosition = createSolution(range);
					customers.add(agents.get(agentPosition));
				}else {
					customers.add(0);
				}
			}
			variables().set(i, customers);
		}else {
			variables().set(i, customers);
		}
		
		
	}
	
	
}
