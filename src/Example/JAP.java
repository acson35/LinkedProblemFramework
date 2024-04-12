package Example;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.problem.integerProblem.Implementation.AbstractIntegerProblem;
import main.solution.solution;
import main.solution.integerSolution.IntegerSolution;
import main.util.model.instance.JAPInstance;

@SuppressWarnings("serial")
public class JAP extends AbstractIntegerProblem {

	
	//IntegerSolution updatedSolution;
	//solution<?> parentsolution;
	
	public JAP(String datafilename) throws IOException {
		setData(readProblem(datafilename));
		setnVariables(((JAPInstance) getData()).getJobsNum());
		setnObjectives(1);
		setnConstraints(1);
		setName("JAP");
		setFilepath(datafilename);
		
		List<Integer> lowerR = new ArrayList<>(getnVariables());
		List<Integer> upperR = new ArrayList<>(getnVariables());
		
		for (int i = 0; i < getnVariables(); i++) {
		   lowerR.add(1);
		   upperR.add(((JAPInstance) getData()).getAgentsNum());
		}
		setVariableRanges(lowerR, upperR);
	}
	
	public JAP(String datafilename, solution<?> solution) throws IOException {
		this(datafilename);
		setParentsolution(solution);
		//this.updatedSolution = updatesolution(solution);
	}
	
//	public IntegerSolution getUpdatedSolution() {
//		return updatedSolution;
//	}
	
	@Override
	public void evaluate(IntegerSolution solution) {
		
		double fitness = 0;
		//double unfitness = 0;
		int[][] c = ((JAPInstance) getData()).getCosts();
		for (int i = 0; i < solution.variables().size(); i++) {
			if(null==solution.variables().get(i))continue;
			for (int j = 0; j < solution.variables().get(i).size(); j++) {
				if(0==solution.variables().get(i).get(j))continue;
				fitness += c[solution.variables().get(i).get(j)-1][j];
				//solution.attributes().put(i, c[solution.variables().get(i)-1][i]);
			}
		}
		
		solution.getFitness()[0]=fitness;
		evaluateConstraint(solution);
	}
	@Override
	public void evaluateConstraint(IntegerSolution solution) {
		
		double unfitness=0;
		int[][] r = ((JAPInstance) getData()).getResoucesNeeded();
		int[] b = ((JAPInstance) getData()).getAgentsCapacity();
		int[] rSum = new int[((JAPInstance) getData()).getAgentsNum()];

		for (int j = 0; j < ((JAPInstance) getData()).getAgentsNum(); j++) {
			rSum[j] = 0;
		}

		for (int i = 0; i < solution.variables().size(); i++) {
			if(null==solution.variables().get(i))continue;
			for(int j = 0; j < solution.variables().get(i).size(); j++) {
				if(solution.variables().get(i).get(j)!=0) {
					rSum[solution.variables().get(i).get(j)-1] += r[solution.variables().get(i).get(j)-1][j];
				}
			}
			
		}

		for (int j = 0; j < ((JAPInstance) getData()).getAgentsNum(); j++) {
			unfitness += Math.max(0, rSum[j] - b[j]);
			
		}
		solution.getConstraints()[0]= unfitness;


	}
	
	public JAPInstance loadJAP(String datafilename) throws IOException {
		
		InputStream in = new FileInputStream(datafilename);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        
		String[] tokens = br.readLine().trim().split(" ");

		int m = Integer.parseInt(tokens[0]);
		int n = Integer.parseInt(tokens[1]);

		int[][] costs = new int[m][n];
		int[][] resourcesNeeded = new int[m][n];
		
		int[] agentsCapacity = new int[m];
		
		for (int a = 0; a < m; a++) {
			int elementsRead = 0;
			while (elementsRead < n) {
				tokens = br.readLine().trim().split(" ");
				for (int t = 0; t < tokens.length; t++) {
					if(tokens[t]=="")continue;
					if(tokens[t].isEmpty())continue;
					costs[a][elementsRead] = Integer.parseInt(tokens[t]);
					elementsRead++;
				}
			}
		}
		
		for (int a = 0; a < m; a++) {
			int elementsRead = 0;
			while (elementsRead < n) {
				tokens = br.readLine().trim().split(" ");
				for (int t = 0; t < tokens.length; t++) {
					if(tokens[t]=="")continue;
					if(tokens[t].isEmpty())continue;
					resourcesNeeded[a][elementsRead] = Integer.parseInt(tokens[t]);
					elementsRead++;
				}
			}
		}
		int elementsRead = 0;
		while (elementsRead < m) {
			tokens = br.readLine().trim().split(" ");
			for (int t = 0; t < tokens.length; t++) {
				if(tokens[t]=="")continue;
				if(tokens[t].isEmpty())continue;
				agentsCapacity[elementsRead] = Integer.parseInt(tokens[t]);
				elementsRead++;
			}
		}

		JAPInstance ins = new JAPInstance(datafilename, m, n, costs, resourcesNeeded, agentsCapacity);
			
		
		return ins;

	}
	
	public JAPInstance readProblem(String datafilename) throws IOException {
		if(datafilename==null) {
			System.out.print("Filepath is not found");
			return null;
		}else {
			return loadJAP(datafilename);
		}
	}

	@Override
	public IntegerSolution createsolution(Map<Integer, List<Integer>> gurobiSol) {
		// TODO Auto-generated method stub
		return null;
	}

}
