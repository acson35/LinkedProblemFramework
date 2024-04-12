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

import main.problem.permutationProblem.Implementation.AbstractIntegerPermutationProblem;
import main.solution.solution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.model.instance.JAPInstance;
import main.util.model.instance.QAPInstance;
import main.util.model.instance.FLPInstance.Cost;
import main.util.model.instance.FLPInstance.Customer;
import main.util.model.instance.FLPInstance.Facility;
import main.util.permutationType.PermutationType;
import main.util.problemName.ProblemName;

@SuppressWarnings("serial")
public class QAP extends AbstractIntegerPermutationProblem{
	
	List<Integer> size;
	
	//IntegerPermutationSolution updatedSolution;
	
	public QAP(String datafilename) throws IOException {
		setData(readProblem(datafilename));
		size = new ArrayList<>();
		size.add(((QAPInstance) getData()).getFlows()[0].length);
		setnVariables(1);
		setnObjectives(1);
		setName("QAP");
		setFilepath(datafilename);
		

	}
	
	public QAP(String datafilename, solution<?> solution) throws IOException {
		this(datafilename);
		setParentsolution(solution);
		//this.updatedSolution = updatesolution(solution);
	}

	@Override
	public void evaluate(IntegerPermutationSolution solution) {
		int total = 0;
		int[][] f = ((QAPInstance) getData()).getFlows();
		int[][] d = ((QAPInstance) getData()).getDistances();
		for(int k=0; k<solution.variables().size(); k++) {
			int size = solution.variables().get(k).size();
			//int[][] permutationMatrix = permutationMatrix(solution.variables().get(k));
			for (int i=0; i<size; i++){
				total += f[i][solution.variables().get(k).get(i)]*d[i][solution.variables().get(k).get(i)];
				//for (int j=0; j<size; j++){
					
					//total += this.cost[i][j] * permutationMatrix[i][j];
				//}
			}
		}
		double fitness = (double)total;
		solution.getFitness()[0] = fitness;
	}
	
	public QAPInstance readProblem(String datafilename) throws IOException{
		InputStream in = new FileInputStream(datafilename);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        
        String line;
        
        String[] tokens = br.readLine().trim().split(" ");

		int nLocations = Integer.parseInt(tokens[0]);
		List<List<Integer>> flowarr = new ArrayList<>();
		for (int j = 0; j < nLocations; j++) {
			int elementsRead1 = 0;
			List<Integer> costline = new ArrayList<>();
			while(elementsRead1<nLocations) {
				tokens = br.readLine().trim().split(" ");
				for (int i = 0; i < tokens.length; i++) {
					if(!tokens[i].isEmpty()) {
						costline.add(Integer.valueOf(tokens[i]));
						elementsRead1++;
					//	System.out.print(tokens[i]+"\t");
					}
				}
			}       
			flowarr.add(costline); 
        }
		
		List<List<Integer>> distancearr = new ArrayList<>();
		for (int j = 0; j < nLocations; j++) {
			int elementsRead2 = 0;
			List<Integer> costline = new ArrayList<>();
			while(elementsRead2<nLocations) {
				tokens = br.readLine().trim().split(" ");
				for (int i = 0; i < tokens.length; i++) {
					if(!tokens[i].isEmpty()) {
						costline.add(Integer.valueOf(tokens[i]));
						elementsRead2++;
					//	System.out.print(tokens[i]+"\t");
					}
				}
			}       
			distancearr.add(costline); 
        }
     
		
        int[][] flow = new int[flowarr.size()][flowarr.get(0).size()];
        for(int i=0; i<flowarr.size(); i++) {
        	List<Integer> proc = flowarr.get(i);
        	for(int j=0; j<proc.size(); j++) {
        		flow[i][j]= proc.get(j);
        	}
        }
        
        int[][] distance = new int[distancearr.size()][distancearr.get(0).size()];
        for(int i=0; i<distancearr.size(); i++) {
        	List<Integer> proc = distancearr.get(i);
        	for(int j=0; j<proc.size(); j++) {
        		distance[i][j]= proc.get(j);
        	}
        }
        
        QAPInstance ins = new QAPInstance(datafilename, nLocations, flow, distance);
        
		return ins;
	}
	
	
	

	
	public int[][] permutationMatrix(List<Integer> solution){
		int[][] matrixB = new int[solution.size()][solution.size()];
		for(int i=0; i<matrixB.length; i++) {
			for(int j=0; j<matrixB.length; j++) {
				if(solution.get(i)==j) {
					matrixB[i][j]=1;
				}else {
					matrixB[i][j]=0;
				}
			}
		}
		return matrixB;
	}
	
	@Override
	public int getPermutationLength(int index) {
		if(index>size.size()-1) {
			return (Integer) null;
		}else {
			return getnSubVariables().get(index);
		}
	}
	
	@Override
	public List<Integer> getnSubVariables(){
		return this.size;
	}
	
	//@Override
	//public IntegerPermutationSolution createsolution() {
	//	 return new IntegerPermutationSolution(getnSubVariables(), getName());
   //}

	//@Override
	//public IntegerPermutationSolution getUpdatedSolution() {
	//	return updatedSolution;
	//}

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
