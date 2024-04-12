package Example;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.problem.permutationProblem.Implementation.AbstractIntegerPermutationProblem;
import main.solution.solution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.problemName.ProblemName;


@SuppressWarnings("serial")
public class PFSP_TotalFlowTime extends AbstractIntegerPermutationProblem  {

	private int[][] processingTimes;
	private List<Integer> nJobs;
	//IntegerPermutationSolution updatedSolution;
	//solution<?> parentsolution;
	//ProblemName parentproblemname;
	
	public PFSP_TotalFlowTime(String datafilename) throws IOException {
		setData(readProblem(datafilename));
		this.processingTimes = (int[][]) getData();
		nJobs = new ArrayList<>();
		this.nJobs.add(processingTimes[0].length);
		setnVariables(1);
		setnObjectives(1);
		setName("PFSP_TotalFlowTime");
		setFilepath(datafilename);
	}
	
	public PFSP_TotalFlowTime(String datafilename, solution<?> solution) throws IOException {
		this(datafilename);
		setParentsolution(solution);
		//this.updatedSolution = updatesolution(solution);
	}


	@Override
	public void evaluate(IntegerPermutationSolution solution) {
		//System.out.println(solution.toString());
		double totalfitness=0;
		for(int k=0; k<solution.variables().size(); k++) {
			if(solution.variables().get(k).size()==0)continue;
			int nMachines = processingTimes.length;
			int nJobs = solution.variables().get(k).size();
			int[] MachineTimes = new int[nMachines];
			
			for (int i = 0; i < nMachines; i++) {
				MachineTimes[i] = 0;
			}	
			int j, z, job;
			int machine;
			int prev_machine = 0;

			int firstjob = solution.variables().get(k).get(0);

			MachineTimes[0] = processingTimes[0][firstjob];
			for (j = 1; j < nMachines; j++) {
				MachineTimes[j] = MachineTimes[j - 1] + processingTimes[j][firstjob];
			}

			double fitness = MachineTimes[nMachines - 1];
			for (z = 1; z < nJobs; z++) {
				job = solution.variables().get(k).get(z);

				MachineTimes[0] += processingTimes[0][job];
				prev_machine = MachineTimes[0];
				for (machine = 1; machine < nMachines; machine++) {
					MachineTimes[machine] = Math.max(prev_machine, MachineTimes[machine]) + processingTimes[machine][job];
					prev_machine = MachineTimes[machine];
				}
				fitness += MachineTimes[nMachines - 1];
			}
			totalfitness += fitness;
		}
		solution.getFitness()[0]= totalfitness;
	}
	
	public int[][] readProblem(String datafilename) throws IOException {
		InputStream in = new FileInputStream(datafilename);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        
        String line;
        int count = 0;
        List<List<Integer>> processTimes = new ArrayList();
        while ((line = br.readLine()) != null) {
        	line.trim();
        	if(count<=2) {	
        	}else {
        		List<Integer> machines = new ArrayList();
        		String[] proc = line.split(" ");
        		for(int i=0; i<proc.length; i++) {
        			if(proc[i].isEmpty())continue;
        			int val = Integer.valueOf(proc[i]);
        			machines.add(val);
        		}
        		
        		processTimes.add(machines);
        		
        	}
        	count++;
        }  
        
        int[][] processingtimes = new int[processTimes.size()][processTimes.get(0).size()];
        for(int i=0; i<processTimes.size(); i++) {
        	List<Integer> proc = processTimes.get(i);
        	for(int j=0; j<proc.size(); j++) {
        		processingtimes[i][j]= proc.get(j);
        	}
        }
		return processingtimes;
	}
	
	@Override
	public int getPermutationLength(int index) {
		if(index>nJobs.size()-1) {
			return (Integer) null;
		}else {
			return getnSubVariables().get(index);
		}
	}
	
	@Override
	public List<Integer> getnSubVariables(){
		return this.nJobs;
	}
	
//	@Override
//	public IntegerPermutationSolution createsolution() {
//		 return new IntegerPermutationSolution(getnSubVariables(), getName());
//   }

//	@Override
//	public IntegerPermutationSolution getUpdatedSolution() {
//		return updatedSolution;
//	}

	@Override
	public void evaluateConstraint(IntegerPermutationSolution solution) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IntegerPermutationSolution createsolution(Map<Integer, List<Integer>> gurobiSol) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
