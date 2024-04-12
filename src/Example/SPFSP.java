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
import main.util.model.instance.SPFSPInstance;
import main.util.model.instance.FLPInstance.Cost;
import main.util.model.instance.SFLPInstance.EnergyConsumption;

public class SPFSP extends AbstractIntegerPermutationProblem{

	private SPFSPInstance processingTimes;
	private List<Integer> nJobs;
	
	@SuppressWarnings("unchecked")
	public SPFSP(String datafilename) throws IOException {
		setData(readProblem(datafilename));
		nJobs = new ArrayList<>();
		processingTimes = (SPFSPInstance)getData();
		this.nJobs.add(processingTimes.getNJobs());
		setnVariables(1);
		setnObjectives(2);
		setName("SPFSP");
		setFilepath(datafilename);
	}
	
	public SPFSP(String datafilename, solution<?> solution) throws IOException {
		this(datafilename);
		setParentsolution(solution);
	}
	
	@Override
	public void evaluate(IntegerPermutationSolution solution) {
		
		List<int[][]> multiCompletionTimes = new ArrayList<>();
		double fitness = 0.0;
		double fitness2 = 0.0;
		int nMachines =  processingTimes.getnMachine();
		int jobs =  processingTimes.getNJobs();
		
		for(int k=0; k<solution.variables().size(); k++) {
			if(solution.variables().get(k).size()==0)continue;
			boolean[][] completionTimesEvaluated= new boolean[nMachines][jobs];
			int[][] completionTimes= new int[nMachines][jobs];
			int[][]completionTimesperFac = processingTimes.getProcessingTimes().get(k);
			int nSubJobs = solution.variables().get(k).size();
			int completionTimeperJob = getCompletionTimeJobOnMachineWithCache(completionTimesperFac, nSubJobs-1, nMachines-1, solution.variables().get(k), completionTimesEvaluated, completionTimes);
			//fitness +=  getCompletionTimeJobOnMachineWithCache(completionTimesperFac,nJobs-1, nMachines-1, solution.variables().get(k), completionTimesEvaluated, completionTimes);
			fitness = Math.max(fitness, completionTimeperJob);
			//System.out.println(completionTimeperJob + " " + processingTimes.getEnergyPower()[k].getEnergyCost().getCost());
			fitness2 += completionTimeperJob*processingTimes.getEnergyPower()[k].getEnergyCost().getCost();
			multiCompletionTimes.add(completionTimes);
			//solution.attributes().put(k, completionTimes);
		}
		
		solution.getFitness()[0]= fitness;
		solution.getFitness()[1]= fitness2;
		
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


	@Override
	public void evaluateConstraint(IntegerPermutationSolution solution) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IntegerPermutationSolution createsolution(Map<Integer, List<Integer>> gurobiSol) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> getnSubVariables() {
		return this.nJobs;
	}
	
	@SuppressWarnings("null")
	@Override
	public int getPermutationLength(int index) {
		if(index>nJobs.size()-1) {
			return (Integer) null;
		}else {
			return getnSubVariables().get(index);
		}
	}
	
	public SPFSPInstance readProblem(String datafilename) throws IOException{
		
		
		List<int[][]> processingtimesall = new ArrayList<>();
		
		String file = datafilename;
    	
    	InputStream in = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
    	//String line;
    	
    	
    	String[] tokens = br.readLine().trim().split(" ");
    	int nJobs = Integer.parseInt(tokens[0]);
    	tokens = br.readLine().trim().split(" ");
		int nMachines = Integer.parseInt(tokens[0]);
		tokens = br.readLine().trim().split(" ");
		int nFacilities = Integer.parseInt(tokens[0]);
		
		EnergyConsumption[]energyPower = new EnergyConsumption[nFacilities];
		
		tokens = br.readLine().trim().split(" ");
		for(int i = 0; i<nFacilities; i++) {
			//System.out.print(Integer.parseInt(tokens[i]));
			energyPower[i] = new EnergyConsumption(new Cost(Integer.parseInt(tokens[i])));
		}
       
        for(int i = 0; i<nFacilities; i++) {
        	
        	 int[][] processTimes = new int[nMachines][nJobs];
        	 
        	 int elementRead = 0;
        	 while(elementRead < nJobs) {
        		 tokens = br.readLine().trim().split(" ");
        		 for(int j=0; j < tokens.length; j++) {
        			 processTimes[j][elementRead] = Integer.parseInt(tokens[j]);
        		 }
        		 elementRead++;
        	 }
        	 processingtimesall.add(processTimes); 	
        }
        SPFSPInstance ins = new SPFSPInstance(file, nJobs, nMachines, nFacilities, energyPower, processingtimesall);
		return ins;
	}

}
