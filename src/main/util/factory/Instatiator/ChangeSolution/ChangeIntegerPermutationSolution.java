package main.util.factory.Instatiator.ChangeSolution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.solution.solution;
import main.solution.binarySolution.BinarySolution;
import main.solution.integerSolution.IntegerSolution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.createBinary.createBinary;
import main.util.factory.Instatiator.AbstractSolutionInstatiator;
import main.util.factory.Instatiator.SolutionInstantiator;

public class ChangeIntegerPermutationSolution extends AbstractSolutionInstatiator<List<Integer>> implements SolutionInstantiator<List<Integer>> {
	solution<?> solution;
	
	public ChangeIntegerPermutationSolution(int nVariables) {
		super(nVariables);
	}
	
	public ChangeIntegerPermutationSolution(solution<?> solution) {
		this(size(solution).size());
		this.solution = solution;
		setsolution();
	}
	
	@Override
	public void setsolution() {
		if(solution.getSolutionType().equalsIgnoreCase("BINARY")) {
			BinarySolution sol = (BinarySolution) solution;
			List<List<Integer>> selectedVar = new ArrayList<>();
			for(int i= 0; i<sol.variables().size();i++) {
				List<Integer> subselectedVar = new ArrayList<>();
				for(int j=0; j<sol.variables().get(i).getNumberOfBits(); j++) {
					if(sol.variables().get(i).get(j)) {
						subselectedVar.add(j);
					}
				}
				java.util.Collections.shuffle(subselectedVar);
				selectedVar.add(subselectedVar);
			}
			for (int i = 0; i < selectedVar.size(); i++) {
				variables().set(i, selectedVar.get(i));
			}	
			
		}else if(solution.getSolutionType().equalsIgnoreCase("INTEGERPERMUTATION")) {
			IntegerPermutationSolution sol = (IntegerPermutationSolution) solution;
			List<List<Integer>> selectedVar = new ArrayList<>();
			
			if(sol.problemname().equalsIgnoreCase("QAP")) {
				for(int i=0; i<  ((List<Integer>) sol.variables().get(0)).size(); i++) {
					List<Integer> subselectedVar = new ArrayList<>();
					subselectedVar.add(((List<Integer>) sol.variables().get(0)).get(i));
					subselectedVar.add(i);
					selectedVar.add(subselectedVar);
				}
			}else if(sol.problemname().equalsIgnoreCase("PFSP_Makespan") || sol.problemname().equalsIgnoreCase("PFSP_TotalFlowTime")) {
				for(int i= 0; i<sol.variables().size();i++) {
					List<Integer> subselectedVar = new ArrayList<>();
					for(int j=0; j<((List<Integer>) sol.variables().get(i)).size(); j++) {
						subselectedVar.add(((List<Integer>) sol.variables().get(i)).get(j));
					}
					selectedVar.add(subselectedVar);
				}
			}else {
				for(int i= 0; i<sol.variables().size();i++) {
					List<Integer> subselectedVar = new ArrayList<>();
					for(int j=0; j<((List<Integer>) sol.variables().get(i)).size(); j++) {
						subselectedVar.add(((List<Integer>) sol.variables().get(i)).get(j));
					}
					java.util.Collections.shuffle(subselectedVar);
					selectedVar.add(subselectedVar);
				}
			}
			variables().clear();
			java.util.Collections.shuffle(selectedVar);
			for(int i=0; i<selectedVar.size(); i++) {
				variables().add(selectedVar.get(i));
			}
			
			
		}else if(solution.getSolutionType().equalsIgnoreCase("INTEGER")) {
			IntegerSolution sol = (IntegerSolution) solution;
			Map<Integer, List<Integer>> subSolutions = new HashMap<Integer, List<Integer>>();
			
			for(int i=0; i<sol.variables().size(); i++) {
				if(null==sol.variables().get(i))continue;
				for(int j=0; j<sol.variables().get(i).size(); j++) {
					if(sol.variables().get(i).get(j)==0)continue;
					int val = sol.variables().get(i).get(j);
					if(subSolutions.containsKey(val)) {
						List<Integer> pos = subSolutions.get(val);
						pos.add(j);
						subSolutions.put(val, pos);
					}else {
						List<Integer> pos = new ArrayList<>();
						pos.add(j);
						subSolutions.put(val, pos);
					}
				}
			}
			int nAgents = sol.getUpperRange(0);
			variables().clear();
			for(int i = 1; i <= nAgents; i++) {
				if(subSolutions.containsKey(i)) {
					java.util.Collections.shuffle(subSolutions.get(i));
					variables().add(subSolutions.get(i));
				}else {
					List<Integer> emptyList = new ArrayList<Integer>();
					variables().add(emptyList);
				}
			}
		/**	for(Integer agent: subSolutions.keySet()) {
				//if(subSolutions.containsKey(agent))
				java.util.Collections.shuffle(subSolutions.get(agent));
				variables().add(subSolutions.get(agent));
			}
		**/
			
			
			
			
		}else if(solution.getSolutionType().equalsIgnoreCase("SEQUENCE")) {
				
		}
	}

}
