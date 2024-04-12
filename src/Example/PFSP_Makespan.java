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

@SuppressWarnings("serial")
public class PFSP_Makespan extends AbstractIntegerPermutationProblem{
	
	private List<int[][]> processingTimes;
	private List<Integer> nJobs;
	//IntegerPermutationSolution updatedSolution;
	//solution<?> parentsolution;
	//ProblemName parentproblemname;
	
	@SuppressWarnings("unchecked")
	public PFSP_Makespan(String datafilename) throws IOException {
		setData(readProblem(datafilename));
		nJobs = new ArrayList<>();
		processingTimes = (List<int[][]>)getData();
		this.nJobs.add(processingTimes.get(0)[0].length);
		setnVariables(1);
		setnObjectives(1);
		setName("PFSP_Makespan");
		setFilepath(datafilename);
	}
	
	public PFSP_Makespan(String datafilename, solution<?> solution) throws IOException {
		this(datafilename);
		setParentsolution(solution);
		//this.updatedSolution = updatesolution(solution);
	}


	@Override
	public void evaluate(IntegerPermutationSolution solution) {
		List<int[][]> multiCompletionTimes = new ArrayList<>();
		double fitness = 0.0;
		int nMachines =  processingTimes.get(0).length;
		int[][] completionTimes= new int[nMachines][processingTimes.get(0)[0].length];
		boolean[][] completionTimesEvaluated= new boolean[nMachines][processingTimes.get(0)[0].length];
		for(int k=0; k<solution.variables().size(); k++) {
			if(solution.variables().get(k).size()==0)continue;
			int[][]completionTimesperFac = processingTimes.get(0);
			int nJobs = solution.variables().get(k).size();
			int completionTimeperJob = getCompletionTimeJobOnMachineWithCache(completionTimesperFac,nJobs-1, nMachines-1, solution.variables().get(k), completionTimesEvaluated, completionTimes);
			//fitness +=  getCompletionTimeJobOnMachineWithCache(completionTimesperFac,nJobs-1, nMachines-1, solution.variables().get(k), completionTimesEvaluated, completionTimes);
			fitness = Math.max(fitness, completionTimeperJob);
			multiCompletionTimes.add(completionTimes);
			//solution.attributes().put(k, completionTimes);
		}
		
		solution.getFitness()[0]= fitness;
	
	}
	
	public int getCompletionTimeJobOnMachineWithCache(int[][] completionTimesperFac, int pos, int machine, List<Integer> solution, boolean[][] completionTimesEvaluated, int[][] completionTimes){
		int c = 0;
		int c1 = 0;
		int c2 = 0;
		if(completionTimesEvaluated[machine][solution.get(pos)]){
			c = completionTimes[machine][solution.get(pos)];
		}
		else{
			if(pos == 0 && machine == 0){
				c = completionTimesperFac[0][solution.get(pos)];
			}
			else{
				if(pos != 0){
					c1 = getCompletionTimeJobOnMachineWithCache(completionTimesperFac,pos-1,machine,solution, completionTimesEvaluated, completionTimes);
				} 
				else{
					c1 = 0;
				}
				if(machine != 0){
					c2 = getCompletionTimeJobOnMachineWithCache(completionTimesperFac,pos,machine-1,solution, completionTimesEvaluated, completionTimes);
				}
				else{
					c2 = 0;	
				}
				if (c1 >c2){
					c = c1+completionTimesperFac[machine][solution.get(pos)];
				}else{
					c = c2+completionTimesperFac[machine][solution.get(pos)];	
				}
			}
			completionTimesEvaluated[machine][solution.get(pos)] = true;
			completionTimes[machine][solution.get(pos)]  = c;
		}
		return c;
	}

	
	public List<int[][]> readProblem(String datafilename) throws IOException{
		List<int[][]> processingtimesall = new ArrayList<>();
        for(int i=0; i<1; i++) {
        	int x = i+1;
        	String file = datafilename;
        	//file = file+"_"+ x + ".txt";
        	InputStream in = new FileInputStream(file);
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
            		for(int a=0; a<proc.length; a++) {
            			if(proc[a].isEmpty())continue;
            			int val = Integer.valueOf(proc[a]);
            			machines.add(val);
            		}
            		
            		processTimes.add(machines);
            		
            	}
            	count++;
            }  
            
            int[][] processingtimes = new int[processTimes.size()][processTimes.get(0).size()];
            for(int j=0; j<processTimes.size(); j++) {
            	List<Integer> proc = processTimes.get(j);
            	for(int k=0; k<proc.size(); k++) {
            		processingtimes[j][k]= proc.get(k);
            	}
            }
            processingtimesall.add(processingtimes);
        }
        
		return processingtimesall;
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
